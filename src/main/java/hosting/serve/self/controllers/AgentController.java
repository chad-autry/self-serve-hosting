/**
 * This Controller communicates with the agent running on individual servers to control the applications
 */

package hosting.serve.self;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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


@Controller
@RequestMapping("/agent")
public class AgentController {

    @RequestMapping("/createServer")
    @ResponseBody
    public String createServer() throws IOException {
        //TODO Make sure the user has privilege/funds to create a server
        Compute compute = ComputeOptions.defaultInstance().service();
        //TODO Make my own minecraft image which has the sftp, minecraft, and rdiff-backup docker containers pre-downloaded,
        ImageId imageId = ImageId.of("debian-cloud", "debian-8-jessie-v20160329");
        NetworkId networkId = NetworkId.of("default");
        //TODO take and use disk size parameters (validate funds and not too small)
        AttachedDisk attachedDisk = AttachedDisk.of(AttachedDisk.CreateDiskConfiguration.builder(imageId).autoDelete(true).build());
        NetworkInterface networkInterface = NetworkInterface.of(networkId);
        //TODO Take the location from the parameters (verify they are correct)
        //TODO Take the instance name from the parameters (qualify it to the user-id)
        InstanceId instanceId = InstanceId.of("us-central1-a", "instance-name");
        //TODO Take the machine type from the paramenters (verify they are correct)
        MachineTypeId machineTypeId = MachineTypeId.of("us-central1-a", "f1-micro");

        Operation operation = compute.create(InstanceInfo.of(instanceId, machineTypeId, attachedDisk, networkInterface));
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
}
