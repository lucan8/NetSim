package models_data;

public class EquipmentData extends Entity{
    public enum EquipmentType {
        ROUTER,
        SWITCH,
        HOST,
        OTHER;

        public static EquipmentType fromString(String type){
            return switch (type.toUpperCase()) {
                case "ROUTER" -> EquipmentType.ROUTER;
                case "SWITCH" -> EquipmentType.SWITCH;
                case "HOST" -> EquipmentType.HOST;
                default -> EquipmentType.OTHER;
            };
        } 
    }

    protected final String name;
    protected final Integer max_interface_count;
    protected final Integer company_id;
    protected final String type;

    public EquipmentData() {
        this.name = null;
        this.max_interface_count = null;
        this.company_id = null;
        this.type = null;
    }

    public EquipmentData(Integer id, String name, Integer max_interface_count, Integer company_id,
                         EquipmentType type) {
        super(id);
        this.name = name;
        this.max_interface_count = max_interface_count;
        this.company_id = company_id;
        this.type = type.toString();
    }

    public String getName() {
        return name;
    }

    public Integer getMaxInterfaceCount() {
        return max_interface_count;
    }

    public Integer getCompanyId() {
        return company_id;
    }

    public String getType() {
        return type;
    }
}