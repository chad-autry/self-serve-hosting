package hosting.serve.self.daos;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.util.logging.Logger;

/**
 * The DAO responsible for manipulating the Server entities
 */
public class ServersDAO {
    
    //Could/Should dependency inject this.
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    /**
     * Creates the new server entity that can be queried
     */
    public void createServer(String serverName, String zoneName, String instanceType, long diskSize, String owner) {
        
        Entity server = new Entity("server", owner + ":" + serverName);
        server.setProperty("owner", diskSize);
        server.setProperty("serverName", serverName);
        server.setProperty("zoneName", zoneName);
        server.setProperty("instanceType", instanceType);
        server.setProperty("diskSize", diskSize);
        server.setProperty("status", "creating"); //Our initial status is creating
        datastore.put(server);
    }
    
}