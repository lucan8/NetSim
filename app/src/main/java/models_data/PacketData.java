package models_data;

public class PacketData extends Entity{
    // The connection the packet passed through
    private final Integer connection_id;
    
    private final String src_ip_addr;
    private final String dest_ip_addr;

    private final String src_mac_addr;
    private final String dest_mac_addr;

    // Keeping the data as a string for now
    private final String data;

    public PacketData() {
        this.connection_id = null;
        this.src_ip_addr = null;
        this.dest_ip_addr = null;
        this.src_mac_addr = null;
        this.dest_mac_addr = null;
        this.data = null;
    }

    public PacketData(Integer id, Integer connection_id, String src_ip_addr, String dest_ip_addr,
                      String src_mac_addr, String dest_mac_addr, String data){
        super(id);
        this.connection_id = connection_id;
        this.src_ip_addr = src_ip_addr;
        this.dest_ip_addr = dest_ip_addr;
        this.src_mac_addr = src_mac_addr;
        this.dest_mac_addr = dest_mac_addr;
        this.data = data;
    }

     public String getSrcIpAddr(){
        return src_ip_addr;
    }

    public String getDestIpAddr(){
        return dest_ip_addr;
    }

    public String getSrcMacAddr(){
        return src_mac_addr;
    }

    public String getDestMacAddr(){
        return dest_mac_addr;
    }

    public String getData(){
        return data;
    }

    public Integer getConnectionId(){
        return connection_id;
    }
}