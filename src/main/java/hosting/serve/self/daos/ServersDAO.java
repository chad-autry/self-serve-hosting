package hosting.serve.self.daos;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import hosting.serve.self.model.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The DAO responsible for manipulating the Server entities
 */
public class ServersDAO {
    
    private static String SERVER = "server";
    private static String SERVER_OWNER = "owner";
    private static String SERVER_NAME = "serverName";
    private static String SERVER_ZONE_NAME = "zoneName";
    private static String SERVER_INSTANCE_TYPE = "instanceType";
    private static String SERVER_DISK_SIZE = "diskSize";
    private static String SERVER_STATUS = "status";
    
    //Could/Should dependency inject this.
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    /**
     * Creates the new server entity that can be queried
     */
    public void createServer(String serverName, String zoneName, String instanceType, long diskSize, String owner) {
        
        Entity server = new Entity(SERVER, owner + ":" + serverName);
        server.setProperty(SERVER_OWNER, owner);
        server.setProperty(SERVER_NAME, serverName);
        server.setProperty(SERVER_ZONE_NAME, zoneName);
        server.setProperty(SERVER_INSTANCE_TYPE, instanceType);
        server.setProperty(SERVER_DISK_SIZE, diskSize);
        server.setProperty(SERVER_STATUS, "creating"); //Our initial status is creating
        datastore.put(server);
    }
    
    public List<Server> getServersByOwner(String owner) {
        List<Server> results = new ArrayList();
        Query q = new Query(SERVER).setFilter(new FilterPredicate(SERVER_OWNER, FilterOperator.EQUAL, owner));
        PreparedQuery pq = datastore.prepare(q);
        for (Entity result : pq.asIterable()) {
            Server server = new Server();
            server.setOwnerName((String)result.getProperty(SERVER_OWNER));
            server.setServerName((String)result.getProperty(SERVER_NAME));
            server.setZoneName((String)result.getProperty(SERVER_ZONE_NAME));
            server.setInstanceType((String)result.getProperty(SERVER_INSTANCE_TYPE));
            server.setDiskSize((Long)result.getProperty(SERVER_DISK_SIZE));
            server.setStatus((String)result.getProperty(SERVER_STATUS));
            
            results.add(server);
        }
        return results;
    }
    
}