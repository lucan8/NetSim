package models_data;

public class MacAddressTableData extends Entity{
    private final Integer sw_interface_id;
    private final String mac_address;

    public MacAddressTableData() {
        this.sw_interface_id = null;
        this.mac_address = null;
    }

    public MacAddressTableData(Integer id, Integer sw_interface_id, String mac_address){
        super(id);
        this.sw_interface_id = sw_interface_id;
        this.mac_address = mac_address;
    }

    public Integer getSwInterfaceId(){
        return sw_interface_id;
    }

    public String getMacAddress(){
        return mac_address;
    }
}