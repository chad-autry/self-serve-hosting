package hosting.serve.self.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.appengine.api.users.UserService;
import com.google.cloud.compute.AttachedDisk;
import com.google.cloud.compute.Compute;
import com.google.cloud.compute.ComputeOptions;
import com.google.cloud.compute.ImageId;
import com.google.cloud.compute.Instance;
import com.google.cloud.compute.InstanceInfo;
import com.google.cloud.compute.InstanceId;
import com.google.cloud.compute.MachineTypeId;
import com.google.cloud.compute.NetworkId;
import com.google.cloud.compute.NetworkInterface;
import com.google.cloud.compute.Operation;
import hosting.serve.self.services.ServersService;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * This controller is for instance manipulation
 * Creating, Starting, Stopping, Listing, Deleteing
 * It also is responsible for executing commands on the instances related to the games hosted there
 */
@Controller
@RequestMapping("/servers")
public class ServersController {
    private static final Logger log = Logger.getLogger(ServersController.class.getName());
    private ServersService serversService = null;
    private UserService userService = null;

    @RequestMapping(value="/createServer", method=RequestMethod.POST)
    @ResponseBody
    public String createServer(@RequestParam("serverName") String serverName,
                               @RequestParam("zone") String zoneName,
                               @RequestParam("type") String instanceType,
                               @RequestParam("disk") long diskSize) {

        //TODO Check serverName for special characters, check if qualifed server name exists
        log.info("serverName: "+serverName);
        //TODO Check zoneName is in the list of zone names
        log.info("zoneName: "+zoneName);
        //TODO Check instanceType is in the list of instance types
        log.info("instanceType: "+instanceType);
        //TODO Check diskSize is in the limited range allowed
        log.info("diskSize: "+diskSize);
        
        String ownerId = userService.getCurrentUser().getUserId();
        //TODO Make sure the user has privilege/funds to create a server of the desired sizes
        
        Operation operation = serversService.createServer(serverName, zoneName, instanceType, diskSize, ownerId);

        try {
            //TODO Double check the instance creation timeout is < the GAE timeout
            //The operation itself is Async, wait for it
            operation = operation.waitFor();
        } catch ( InterruptedException e) {
            //TODO Return an error to the user
            return "InterruptedException";
        } catch (TimeoutException e) {
            //TODO Return an error to the user
            return "TimeoutException";
        }
        if (operation.errors() == null) {
          //TODO Copy over selected MC version
          //Start the host server
          //Write CoreOS scripts to run the MC server
          //Instance instance = compute.getInstance(instanceId);
          return "Instance Created";
        } else {
          return "Failure";
        }
    }

    @RequestMapping("/startServer")
    @ResponseBody
    public String startServer() throws IOException {
        Compute compute = ComputeOptions.defaultInstance().service();
        //TODO Take the instance name and location from the parameters (qualify it to the user-id)
        Instance instance = compute.getInstance(InstanceId.of("us-central1-a", "instance-name"));
        Operation operation = null;
        if (instance != null) {
            operation = instance.start();
        } else {
            return "Could not retrieve the test instance";
        }

        try {
            operation = operation.waitFor();
        } catch ( InterruptedException e) {
            //TODO Return an error to the user
            return "InterruptedException";
        } catch (TimeoutException e) {
            //TODO Return an error to the user
            return "TimeoutException";
        }

        if (operation.errors() == null) {
            //TODO How to keep track when opened to public? Don't want consistency problems. Can it be queried asynchronously elsewhere?
            return "Instance Started";
        } else {
            return operation.errors().toString();
        }
    }

    @RequestMapping("/stopServer")
    @ResponseBody
    public String stopServer() throws IOException {
        Compute compute = ComputeOptions.defaultInstance().service();
        //TODO Take the instance name and location from the parameters (qualify it to the user-id)
        Instance instance = compute.getInstance(InstanceId.of("us-central1-a", "instance-name"));
        Operation operation = null;
        if (instance != null) {
            operation = instance.stop();
        } else {
            return "Could not retrieve the test instance";
        }

        try {
            operation = operation.waitFor();
        } catch ( InterruptedException e) {
            //TODO Return an error to the user
            return "InterruptedException";
        } catch (TimeoutException e) {
            //TODO Return an error to the user
            return "TimeoutException";
        }

        if (operation.errors() == null) {
            //TODO How to keep track when opened to public? Don't want consistency problems. Can it be queried asynchronously elsewhere?
            return "Instance Stopped";
        } else {
            return operation.errors().toString();
        }
    }

    @RequestMapping("/deleteServer")
    @ResponseBody
    public String deleteServer() throws IOException {
        Compute compute = ComputeOptions.defaultInstance().service();
        //TODO Take the instance name and location from the parameters (qualify it to the user-id)
        Instance instance = compute.getInstance(InstanceId.of("us-central1-a", "instance-name"));
        Operation operation = null;
        if (instance != null) {
            operation = instance.delete();
        } else {
            return "Could not retrieve the test instance";
        }

        try {
            operation = operation.waitFor();
        } catch ( InterruptedException e) {
            //TODO Return an error to the user
            return "InterruptedException";
        } catch (TimeoutException e) {
            //TODO Return an error to the user
            return "TimeoutException";
        }

        if (operation.errors() == null) {
            //TODO How to keep track when opened to public? Don't want consistency problems. Can it be queried asynchronously elsewhere?
            return "Instance Deleted";
        } else {
            return operation.errors().toString();
        }
    }

    /*
     * Returns the list of servers as JSON, all parameters requred, POST used for security
     */
    @RequestMapping(value="/getServers", method=RequestMethod.POST)
    @ResponseBody
    public String getServers() {
        //Who are we getting servers for? Are they authorized
        //Whose servers can they see?
        //Are there any optional filters/sort order on the request?
        //What about paging parameters?
        
        //First pass: Return all servers only for the logged on user
        return "";
    }
    
    public void setServersService(ServersService serversService) {
        this.serversService = serversService;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
