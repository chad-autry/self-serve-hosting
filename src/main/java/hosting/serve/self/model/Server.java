package hosting.serve.self.model;

public class Server {
    private String serverName = null;
    private String zoneName = null;
    private String instanceType = null;
    private long diskSize = 0;
    private String ownerName = null;
    private String status = null;
    
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
    
    public String getZoneName() {
        return this.zoneName;
    }
    
    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }
    
    public String getInstanceType() {
        return this.instanceType;
    }
    
    public void setDiskSize(long diskSize) {
        this.diskSize = diskSize;
    }
    
    public long getDiskSize() {
        return this.diskSize;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
}