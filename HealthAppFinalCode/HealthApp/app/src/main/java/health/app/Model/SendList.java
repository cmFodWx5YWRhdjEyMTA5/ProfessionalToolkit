package health.app.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Developer Six on 11/6/2017.
 */

public class SendList {
    public ArrayList<Date> getSendList() {
        return sendList;
    }

    public void setSendList(ArrayList<Date> sendList) {
        this.sendList = sendList;
    }

    ArrayList<Date> sendList=new ArrayList<>();
}
