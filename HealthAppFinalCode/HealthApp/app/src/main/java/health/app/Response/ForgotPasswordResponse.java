package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 8/8/2017.
 */

public class ForgotPasswordResponse {

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

    public ForgetData getData() {
        return data;
    }

    public void setData(ForgetData data) {
        this.data = data;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("data")
    @Expose
    public ForgetData data;

    public class ForgetData {

        @SerializedName("email")
        @Expose
        public String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @SerializedName("id")
        @Expose
        public String id;
    }
}
