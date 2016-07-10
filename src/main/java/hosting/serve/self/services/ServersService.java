package hosting.serve.self.services;

import hosting.serve.self.daos.ServersDAO;

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
 * The service layer class responsible for co-ordinating services/daos for server manipulation
 */
public class ServersService {
    
    //The GCE compute service
    //TODO Is this service thread safe?
    private Compute compute = null;
    
    private ServersDAO serversDAO = null;
    /**
     * Creates a new server instance for games to be hosted on
     * Returns the asynch GCE Operation
     */
    public Operation createServer(String serverName, String zoneName, String instanceType, long diskSize, String owner) {
        //TODO Enable charging 3rd party users. Use an around annotation? Can attempt to edit DB on failure
        this.serversDAO.createServer(serverName, zoneName, instanceType, diskSize, owner);
        
                
        //TODO Make my own minecraft image which has the sftp, minecraft, and rdiff-backup docker containers pre-downloaded,
        //TODO Cache the imageId at the class level, it will be static
        ImageId imageId = ImageId.of("debian-cloud", "debian-8-jessie-v20160329");
        
        //Create the attached disk with the specified size
        AttachedDisk attachedDisk = AttachedDisk.of(AttachedDisk.CreateDiskConfiguration.builder(imageId)
            .autoDelete(true).diskSizeGb(diskSize).build());

        //TODO Qualify the instance name to the user.
        InstanceId instanceId = InstanceId.of(zoneName, serverName);
        MachineTypeId machineTypeId = MachineTypeId.of(zoneName, instanceType);

        //Default is the only network, bit of required boilerplate here.
        //TODO Can I cache this boilerplate at the class level?
        NetworkId networkId = NetworkId.of("default");
        NetworkInterface networkInterface = NetworkInterface.of(networkId);

        Operation operation = compute.create(InstanceInfo.of(instanceId, machineTypeId, attachedDisk, networkInterface));
        return operation;
    }
    
    public void setCompute(Compute compute) {
        this.compute = compute;
    }
    
    public void setServersDAO(ServersDAO serversDAO) {
        this.serversDAO = serversDAO;
    }
    
}