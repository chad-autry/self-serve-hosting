package hosting.serve.self.services;

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
    
    /**
     * Creates a new server instance for games to be hosted on
     */
    public void createServer(String serverName, String zoneName, String instanceType, long diskSize) {
        //TODO Enable charging 3rd party users. Use an around annotation? Can attempt to edit DB on failure
    }
    
}