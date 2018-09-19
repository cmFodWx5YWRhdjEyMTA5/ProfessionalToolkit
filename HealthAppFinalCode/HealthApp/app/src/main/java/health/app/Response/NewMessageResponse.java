package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 8/29/2017.
 */

public class NewMessageResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("msg")
    @Expose
    public String msg;
}
