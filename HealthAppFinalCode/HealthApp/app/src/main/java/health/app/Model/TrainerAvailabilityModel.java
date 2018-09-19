package health.app.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer Six on 10/10/2017.
 */

public class TrainerAvailabilityModel {
    String selectDuration;
    String selectNumber;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    String packageName;

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    String slotId;

    public String getSelectDays() {
        return selectDays;
    }

    public void setSelectDays(String selectDays) {
        this.selectDays = selectDays;
    }

    String selectDays;

    public String getSelectDuration() {
        return selectDuration;
    }

    public void setSelectDuration(String selectDuration) {
        this.selectDuration = selectDuration;
    }

    public String getSelectNumber() {
        return selectNumber;
    }

    public void setSelectNumber(String selectNumber) {
        this.selectNumber = selectNumber;
    }

    public String getSelectAmount() {
        return selectAmount;
    }

    public void setSelectAmount(String selectAmount) {
        this.selectAmount = selectAmount;
    }

    public List<String> getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(List<String> selectDay) {
        this.selectDay = selectDay;
    }

    String selectAmount;
    List<String> selectDay=new ArrayList<>();
}
