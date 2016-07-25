package hosting.serve.self.controllers;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import hosting.serve.self.services.ServersService;
import hosting.serve.self.model.Server;

import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ServersControllerTest {
    
    private ServersController serversController = null;
    private ServersService mockServersService = null;
    private UserService mockUserService = null;
    private User user = null;
    
    @Before
    public void setUp() {
        serversController = new ServersController();
        mockServersService = Mockito.mock(ServersService.class);
        serversController.setServersService(mockServersService);
        mockUserService = Mockito.mock(UserService.class);
        serversController.setUserService(mockUserService);
    }

    @Test
    /*
     * Test that get servers is annotated as POST only (keeps the parameters securely private over ssh)
     */
    public void testGetServerListAnnotation() {
        Method[] methods = ServersController.class.getMethods();
        Method getServers = null;
        
        for (int i = 0; i < methods.length; i++) {
            if ("getServers".equals(methods[i].getName())) {
                getServers = methods[i];
            }
        }
        Annotation[][] parameterAnnotations = getServers.getParameterAnnotations();
        Assert.assertNotNull(getServers);
        
        RequestMapping requestMappingAnnotation = getServers.getAnnotation(RequestMapping.class);
        Assert.assertNotNull(requestMappingAnnotation);
        RequestMethod[] mappedMethods = requestMappingAnnotation.method();
        Assert.assertEquals(1, mappedMethods.length);
        Assert.assertEquals(RequestMethod.POST, mappedMethods[0]);
    }
    
    @Test
    /*
     * Parse the String output of getServersList to verify it is correct JSON
     */
    public void testGetServerListJSONSerialization() {
        Mockito.when(mockUserService.getCurrentUser()).thenReturn(new User("testerBob@gmail.com","gmail.com","testerBob"));
        List<Server> results = new ArrayList(2);
        Server testServer1 = new Server();
        testServer1.setOwnerName("testOwner1");
        testServer1.setServerName("testServer1");
        testServer1.setZoneName("testZoneName1");
        testServer1.setInstanceType("testInstanceType1");
        testServer1.setDiskSize(42);
        testServer1.setStatus("BeingTested1");
        results.add(testServer1);
        Server testServer2 = new Server();
        testServer2.setOwnerName("testOwner2");
        testServer2.setServerName("testServer2");
        testServer2.setZoneName("testZoneName2");
        testServer2.setInstanceType("testInstanceType2");
        testServer2.setDiskSize(42);
        testServer2.setStatus("BeingTested2");
        results.add(testServer2);
        Mockito.when(mockServersService.getServers("testerBob")).thenReturn(results);
        
        String controllerOutput = serversController.getServers();
        JsonReader jsonReader = Json.createReader(new StringReader(controllerOutput));
        JsonArray jsonArray = jsonReader.readArray();
        jsonReader.close();
        
        Assert.assertEquals(2, jsonArray.size());
        JsonObject jsonObject = jsonArray.getJsonObject(0);
        
        Assert.assertEquals("testOwner1", jsonObject.getString("ownerName"));
        
        Assert.assertEquals("testServer1", jsonObject.getString("serverName"));
        Assert.assertEquals("testZoneName1", jsonObject.getString("zoneName"));
        Assert.assertEquals("testInstanceType1", jsonObject.getString("instanceType"));
        Assert.assertEquals(42, jsonObject.getInt("diskSize"));
        Assert.assertEquals("BeingTested1", jsonObject.getString("status"));
        
        jsonObject = jsonArray.getJsonObject(1);
        
        Assert.assertEquals("testOwner2", jsonObject.getString("ownerName"));
        
        Assert.assertEquals("testServer2", jsonObject.getString("serverName"));
        Assert.assertEquals("testZoneName2", jsonObject.getString("zoneName"));
        Assert.assertEquals("testInstanceType2", jsonObject.getString("instanceType"));
        Assert.assertEquals(42, jsonObject.getInt("diskSize"));
        Assert.assertEquals("BeingTested2", jsonObject.getString("status"));
    }

    @Test
    /*
     * Test that all parameters annotated as RequestParameters
     * are required (this is equivalent to testing our null handleing logic)
     */
    public void testGetServerListParametersAreRequired() {
        Method[] methods = ServersController.class.getMethods();
        Method getServers = null;
        
        for (int i = 0; i < methods.length; i++) {
            if ("getServers".equals(methods[i].getName())) {
                getServers = methods[i];
            }
        }
        Annotation[][] parameterAnnotations = getServers.getParameterAnnotations();
        Assert.assertNotNull(getServers);
        
        for(Annotation[] annotations : parameterAnnotations) {

            for(Annotation annotation : annotations) {

            }
        }
    }
    
    @Test
    /*
     * Test that all parameters annotated as RequestParameters
     * are required (this is equivalent to testing our null handleing logic)
     */
    public void testCreateServerParametersAreRequired() {
        Method[] methods = ServersController.class.getMethods();
        Method createServer = null;
        
        for (int i = 0; i < methods.length; i++) {
            if ("createServer".equals(methods[i].getName())) {
                createServer = methods[i];
            }
        }
        Annotation[][] parameterAnnotations = createServer.getParameterAnnotations();
        Assert.assertNotNull(createServer);
        
        for(Annotation[] annotations : parameterAnnotations) {

            for(Annotation annotation : annotations) {

            }
        }
    }
    
    @Test
    /*
     * Test that createServer is annotated as POST only (keeps the parameters securely private over ssh)
     */
    public void testCreateServerAnnotation() {
        Method[] methods = ServersController.class.getMethods();
        Method createServer = null;
        
        for (int i = 0; i < methods.length; i++) {
            if ("createServer".equals(methods[i].getName())) {
                createServer = methods[i];
            }
        }
        Annotation[][] parameterAnnotations = createServer.getParameterAnnotations();
        Assert.assertNotNull(createServer);
        
        RequestMapping requestMappingAnnotation = createServer.getAnnotation(RequestMapping.class);
        Assert.assertNotNull(requestMappingAnnotation);
        RequestMethod[] mappedMethods = requestMappingAnnotation.method();
        Assert.assertEquals(1, mappedMethods.length);
        Assert.assertEquals(RequestMethod.POST, mappedMethods[0]);
    }
}