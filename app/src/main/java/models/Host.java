package models;
public class Host extends Equipment {
    public Host(){
        super();
    }

    public Host(Integer id, String name, Integer max_interface_count, Integer company_id) {
        super(id, name, max_interface_count, company_id, EquipmentType.HOST);
    }
}