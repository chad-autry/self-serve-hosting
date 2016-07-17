package hosting.serve.self.daos;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import hosting.serve.self.model.Server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServersDAOTest {
    
    //Use provided test helper to transparentlly mock GAE DB capabillity
    private final LocalServiceTestHelper gaeServicesHelper
        = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private ServersDAO serversDAO = null;
    
    @Before
    public void setUp() {
        gaeServicesHelper.setUp();
        serversDAO = new ServersDAO();
    }

    @After
    public void tearDown() {
        gaeServicesHelper.tearDown();
    }

    @Test
    /*
     * Test that a created server is inserted into the DB for searches and retrieval
     */
    public void testCreatedServerIsInDatabase() throws com.google.appengine.api.datastore.EntityNotFoundException {
        serversDAO.createServer("serverName", "serverType", "instanceType", 20, "testUser");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        
        //Assert there is an entry
        Assert.assertEquals(1, ds.prepare(new Query("server")).countEntities());
        
        Key key = KeyFactory.createKey("server", "testUser:serverName");
        Entity retrievedServer = ds.get(key);
        Assert.assertEquals("serverName", retrievedServer.getProperty("serverName"));
    }

    @Test
    /*
     * Test that a userer servers are retrieved, but other user's servers are not
     */
    public void testGetServers() throws com.google.appengine.api.datastore.EntityNotFoundException {
        
        serversDAO.createServer("serverName", "serverType", "instanceType", 20, "testUser");
        serversDAO.createServer("serverName2", "serverType", "instanceType", 20, "testUser");
        serversDAO.createServer("serverName", "serverType", "instanceType", 20, "testUser2");
        
        List<Server> serverList = serversDAO.getServersByOwner("testUser");
        Assert.assertNotNull(serverList);
        
        Assert.assertEquals(2, serverList.size());
    }
}