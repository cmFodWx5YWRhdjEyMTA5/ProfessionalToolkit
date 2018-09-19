package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 9/29/2017.
 */

public class NotificationCount {
    @SerializedName("msg")
    @Expose
    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("data")
    @Expose
    public int data;
}
