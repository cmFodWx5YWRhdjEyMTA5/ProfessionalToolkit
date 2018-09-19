package health.app.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 10/17/2017.
 */

public class PackageSlots {
    @SerializedName("SessionDate")
    String packageDate;

    public PackageSlots() {

    }

    @SerializedName("StartTime")
    String startTime;

    @SerializedName("EndTime")
    String endTime;

    public String getPackageDate() {
        return packageDate;
    }

    public void setPackageDate(String packageDate) {
        this.packageDate = packageDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
