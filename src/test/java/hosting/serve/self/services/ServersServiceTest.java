package hosting.serve.self.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServersServiceTest {

    private ServersService serversService = null;
    
    @Before
    public void setUp() {
        serversService = new ServersService();
    }

    @Test
    /*
     * Initial test is only does the method exist
     * Future Tests: Is the method annotated to handle errors?
     *    Are the expected service/dao methods called in the correct order?
     */
    public void testCreateServer() {
        serversService.createServer("serverName", "serverType", "instanceType", 20);
    }
}