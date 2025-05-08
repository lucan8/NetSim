package models;

public class Switch extends Equipment {
    // New default constructor
    public Switch() {
        super(); // Calls Equipment's default constructor
    }

    public Switch(Integer id, String name, Integer max_interface_count, Integer company_id) {
        super(id, name, max_interface_count, company_id, EquipmentType.SWITCH);
    }

    // @Override
    // public void forwardPacket(Packet packet, EquipmentInterface equipmentInterface) { // Updated parameter types
    //     // Check equipmentInterface is actually this switch's interface

    //     // Learn: 
    //     this.learn();
    //     // Get the interface associated with the dest_mac_addr of the packet
    //         // if none exists flood
    //     // Get the interface to which the fetched interface is connected

    //     // Insert into the packets table a new packet that is a copy of the previous with the interface changed
    // }

    private void flood(){}
    private void forward(){}
    private void learn(){}

}