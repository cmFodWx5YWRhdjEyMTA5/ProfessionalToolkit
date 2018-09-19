package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 9/7/2017.
 */

public class ProfileDataResponse {

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

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public ProfileData profileData;

    public class ProfileData {
        public SignUpResponse.SignUpData.User getUser() {
            return user;
        }

        public void setUser(SignUpResponse.SignUpData.User user) {
            this.user = user;
        }

        @SerializedName("user")
        @Expose
        public SignUpResponse.SignUpData.User user;

    }
}
