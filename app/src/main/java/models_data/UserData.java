package models_data;
public class UserData extends Entity{
    private final String username;
    private final String password;
    private final String email;

    public UserData() {
        this.username = null;
        this.password = null;
        this.email = null;
    }

    public UserData(Integer id, String username, String password, String email){
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
}