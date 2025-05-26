package models_data;

public class RoutingTableData extends Entity{
    private final String ip;
    private final Integer mask;
    private final Integer r_interface_id;

    public RoutingTableData() {
        this.ip = null;
        this.mask = null;
        this.r_interface_id = null;
    }
    
    public RoutingTableData(Integer id, String ip, Integer mask, Integer r_interface_id){
        super(id);
        this.ip = ip;
        this.mask = mask;
        this.r_interface_id = r_interface_id;
    }

     public String getIp(){
        return ip;
    }

    public Integer getMask(){
        return mask;
    }

    public Integer getRInterfaceId(){
        return r_interface_id;
    }

    public void print(){
        System.out.println("id: " + id);
        System.out.println("ip: " + ip + "/" + mask);
        System.out.println("router interface id: " + r_interface_id);
    }
}