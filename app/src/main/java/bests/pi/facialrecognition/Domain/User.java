package bests.pi.facialrecognition.Domain;

public class User {

    private Integer id;
    private String email;
    private String password;
    private Byte[] image;

    public User(){}
    public User(Integer id, String email, String password, Byte[] image){
        this.id = id;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Byte[] getImage() {
        return image;
    }
    public void setImage(Byte[] image) {
        this.image = image;
    }
}
