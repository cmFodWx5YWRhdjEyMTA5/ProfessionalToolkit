package health.app.Model;

/**
 * Created by Developer Six on 9/13/2017.
 */

public class DateItem extends ListItem {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}
