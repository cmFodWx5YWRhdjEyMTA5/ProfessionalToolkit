package health.app.Response;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 10/16/2017.
 */

public class TrainerSlotsResponse {
    public List<MySlots> getMySlotsList() {
        return mySlotsList;
    }

    public void setMySlotsList(List<MySlots> mySlotsList) {
        this.mySlotsList = mySlotsList;
    }

    @SerializedName("slots")
    @Expose
    public List<MySlots> mySlotsList;

    public class MySlots {

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        @SerializedName("start")
        @Expose
        public String start;

        @SerializedName("end")
        @Expose
        public String end;
    }
}
