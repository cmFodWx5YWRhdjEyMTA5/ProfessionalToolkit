package health.app.Model;

import java.util.ArrayList;

/**
 * Created by Developer Six on 10/5/2017.
 */

public class AvailabilityModel {
    String sessionTime;

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getSessionAmount() {
        return sessionAmount;
    }

    public void setSessionAmount(String sessionAmount) {
        this.sessionAmount = sessionAmount;
    }

    public ArrayList<String> getDateList() {
        return dateList;
    }

    public void setDateList(ArrayList<String> dateList) {
        this.dateList = dateList;
    }

    String sessionAmount;
    ArrayList<String> dateList=new ArrayList<>();
}
