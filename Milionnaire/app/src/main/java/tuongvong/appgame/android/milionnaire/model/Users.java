package tuongvong.appgame.android.milionnaire.model;

public class Users {
    public String name;
    public String email;
    public String url_image;
    public String diem;
    public String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public Users(String name, String url_image, String diem) {
        this.name = name;
        this.url_image = url_image;
        this.diem = diem;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users() {
        // trống
    }

    public Users(String name, String email, String url_image, String diem) {
        this.name = name;
        this.email = email;
        this.url_image = url_image;
        this.diem = diem;
    }
}
