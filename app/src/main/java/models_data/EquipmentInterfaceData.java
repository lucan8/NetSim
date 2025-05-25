package models_data;

public class EquipmentInterfaceData extends Entity{
    private final String ip;
    private final String mac_addr;
    private final Integer mask;
    private final Integer equipment_id;

    public EquipmentInterfaceData() {
        this.ip = null;
        this.mac_addr = null;
        this.mask = null;
        this.equipment_id = null;
    }

    public EquipmentInterfaceData(Integer id, String ip, String mac_addr, Integer mask, Integer equipment_id) {
        super(id);
        this.ip = ip;
        this.mac_addr = mac_addr;
        this.mask = mask;
        this.equipment_id = equipment_id;
    }
    
    public void print(){
        System.out.println("id: " + this.id);
        System.out.println("ip: " + this.ip + "/" + this.mask);
        System.out.println("mac_addr: " + this.mac_addr);
    }    
    
    public String getIp() {
        return ip;
    }

    public String getMacAddr() {
        return mac_addr;
    }

    public Integer getMask() {
        return mask;
    }

    public Integer getEquipmentId() {
        return equipment_id;
    }

    // public void setIp(String ip) {
    //     this.ip = ip;
    // }

    // public void setMask(Integer mask) {
    //     this.mask = mask;
    // }
}