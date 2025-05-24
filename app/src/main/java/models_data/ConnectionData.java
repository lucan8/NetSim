package models_data;
public class ConnectionData extends Entity{
    private final Integer interface_id1;
    private final Integer interface_id2;

    public ConnectionData() {
        this.interface_id1 = null;
        this.interface_id2 = null;
    }

    public ConnectionData(Integer id, Integer interface_id1, Integer interface_id2) {
        super(id);
        this.interface_id1 = interface_id1;
        this.interface_id2 = interface_id2;
    }

    public Integer getInterfaceId1(){
        return interface_id1;
    }

    public Integer getInterfaceId2(){
        return interface_id2;
    }
}