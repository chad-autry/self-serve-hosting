package hosting.serve.self.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
        //TODO Make sure the user has privilege/funds to create a server of the desired sizes
        
        //TODO Make my own minecraft image which has the sftp, minecraft, and rdiff-backup docker containers pre-downloaded,
        //TODO Cache the imageId at the class level, it will be static
        ImageId imageId = ImageId.of("debian-cloud", "debian-8-jessie-v20160329");
        
        //Create the attached disk with the specified size
        AttachedDisk attachedDisk = AttachedDisk.of(AttachedDisk.CreateDiskConfiguration.builder(imageId).autoDelete(true).diskSizeGb(diskSize).build());

        //TODO Qualify the instance name to the user.
        InstanceId instanceId = InstanceId.of(zoneName, serverName);
        MachineTypeId machineTypeId = MachineTypeId.of(zoneName, instanceType);

        //Default is the only network, bit of required boilerplate here.
        //TODO Can I cache this boilerplate at the class level?
        NetworkId networkId = NetworkId.of("default");
        NetworkInterface networkInterface = NetworkInterface.of(networkId);
        
        //Create the desired GCE instance
        //TODO Should/Can this service be cached at the class level?
        Compute compute = ComputeOptions.defaultInstance().service();
        Operation operation = compute.create(InstanceInfo.of(instanceId, machineTypeId, attachedDisk, networkInterface));
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
          Instance instance = compute.getInstance(instanceId);
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
        return "";
    }
}
