package models_data;

public class CompanyData extends Entity{
    private final String name;

    public CompanyData(){
        this.name = null;
    }
    
    public CompanyData(Integer id, String name){
        super(id);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}