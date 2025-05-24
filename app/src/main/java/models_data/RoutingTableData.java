package models_data;

public class RoutingTableData extends Entity{
    private final String ip;
    private final Integer mask;
    private final Integer interface_id;

    public RoutingTableData() {
        this.ip = null;
        this.mask = null;
        this.interface_id = null;
    }
    
    public RoutingTableData(Integer id, String ip, Integer mask, Integer interface_id){
        super(id);
        this.ip = ip;
        this.mask = mask;
        this.interface_id = interface_id;
    }

     public String getIp(){
        return ip;
    }

    public Integer getMask(){
        return mask;
    }

    public Integer getInterfaceId(){
        return interface_id;
    }
}