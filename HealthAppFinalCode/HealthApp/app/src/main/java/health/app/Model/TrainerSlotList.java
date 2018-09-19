package health.app.Model;

/**
 * Created by Developer Six on 8/29/2017.
 */

public class TrainerSlotList {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TrainerSlotList(String title, String location, String phone, String date) {
        this.title = title;
        this.location = location;
        this.phone = phone;
        this.date = date;
    }

    String location;
    String phone;
    String date;
}
