package models;

public class Router extends Equipment {
    public Router() {
        super(); // Calls Equipment's default constructor
    }

    public Router(Integer id, String name, Integer max_interface_count, Integer company_id) {
        super(id, name, max_interface_count, company_id, EquipmentType.ROUTER);
    }

    // @Override
    // public void forwardPacket(Packet packet, EquipmentInterface equipmentInterface) {
    //     // TODO: Implement router packet forwarding logic
    //
    // }
}
