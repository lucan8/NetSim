package models_data;

public class MacAddressTableData extends Entity{
    private final Integer sw_interface_id;
    private final String mac_addr;

    public MacAddressTableData() {
        this.sw_interface_id = null;
        this.mac_addr = null;
    }

    public MacAddressTableData(Integer id, Integer sw_interface_id, String mac_addr){
        super(id);
        this.sw_interface_id = sw_interface_id;
        this.mac_addr = mac_addr;
    }

    public Integer getSwInterfaceId(){
        return sw_interface_id;
    }

    public String getMacAddress(){
        return mac_addr;
    }

    public void print(){
        System.out.println("id: " + id);
        System.out.println("switch interface id: " + sw_interface_id);
        System.out.println("mac address: " + mac_addr);
    }
}