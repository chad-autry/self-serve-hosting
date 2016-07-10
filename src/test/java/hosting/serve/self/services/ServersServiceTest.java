package hosting.serve.self.services;

import com.google.cloud.compute.Compute;
import com.google.cloud.compute.InstanceInfo;

import hosting.serve.self.daos.ServersDAO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ServersServiceTest {

    private ServersService serversService = null;
    private Compute compute = null;
    ServersDAO serversDAO = null;
    
    @Before
    public void setUp() {
        serversService = new ServersService();
        compute = Mockito.mock(Compute.class);
        serversDAO = Mockito.mock(ServersDAO.class);
        serversService.setCompute(compute);
        serversService.setServersDAO(serversDAO);
    }

    @Test
    /*
     * Initial test is only does the method exist
     * Future Tests: Is the method annotated to handle errors?
     *    Are the expected service/dao methods called in the correct order?
     */
    public void testCreateServer() {
        serversService.createServer("serverName", "serverType", "instanceType", 20, "owner");
        //It is expected the DAO will be utilized first, then the GCE service.
        //We don't have 2 phase commit, so we retain the DS entity in case of Compute problems
        //The Mocked DAO method should have been called
        //Then the mocked GCE service to create the instance
        InOrder inOrder = Mockito.inOrder(serversDAO, compute);
        inOrder.verify(serversDAO).createServer(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), Matchers.anyString());
        inOrder.verify(compute).create(Matchers.any(InstanceInfo.class));
    }
}