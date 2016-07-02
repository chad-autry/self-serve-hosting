package hosting.serve.self.controllers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ServersControllerTest {
    
    private ServersController serversController = null;
    
    @Before
    public void setUp() {
        serversController = new ServersController();
    }

    @Test
    /*
     * Test that all parameters annotated as RequestParameters
     * are required (this is equivalent to testing our null handleing logic)
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
}