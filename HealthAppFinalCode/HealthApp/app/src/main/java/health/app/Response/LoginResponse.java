package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer Six on 8/8/2017.
 */

public class LoginResponse {
    @SerializedName("data")
    @Expose
    public SignUpResponse.SignUpData data;

    public SignUpResponse.SignUpData getData() {
        return data;
    }

    public void setData(SignUpResponse.SignUpData data) {
        this.data = data;
    }

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


    public class SignUpData {

        @SerializedName("user")
        @Expose
        public SignUpResponse.SignUpData.User user;

        public SignUpResponse.SignUpData.User getUser() {
            return user;
        }

        public void setUser(SignUpResponse.SignUpData.User user) {
            this.user = user;
        }

        public List<TimeSlots> getTimeSlotsList() {
            return timeSlotsList;
        }

        public void setTimeSlotsList(List<TimeSlots> timeSlotsList) {
            this.timeSlotsList = timeSlotsList;
        }

        @SerializedName("time_slots")
        @Expose
        public List<TimeSlots> timeSlotsList = new ArrayList<>();


        public class User {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("user_type")
            @Expose
            public String user_type;

            @SerializedName("email")
            @Expose
            public String email;

            @SerializedName("first_name")
            @Expose
            public String first_name;

            @SerializedName("last_name")
            @Expose
            public String last_name;

            @SerializedName("phone")
            @Expose
            public String phone;

            @SerializedName("age")
            @Expose
            public String age;

            @SerializedName("fat")
            @Expose
            public String fat;

            @SerializedName("weight")
            @Expose
            public String weight;

            @SerializedName("height")
            @Expose
            public String height;

            @SerializedName("gender")
            @Expose
            public String gender;

            @SerializedName("daily_activity")
            @Expose
            public String daily_activity;

            @SerializedName("birthday")
            @Expose
            public String birthday;


            @SerializedName("profile_image")
            @Expose
            public String profile_image;

            @SerializedName("short_bio")
            @Expose
            public String short_bio;

            @SerializedName("training_type")
            @Expose
            public String training_type;


            @SerializedName("android_token")
            @Expose
            public String android_token;

            @SerializedName("latitude")
            @Expose
            public String latitude;

            @SerializedName("longitude")
            @Expose
            public String longitude;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getFat() {
                return fat;
            }

            public void setFat(String fat) {
                this.fat = fat;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getDaily_activity() {
                return daily_activity;
            }

            public void setDaily_activity(String daily_activity) {
                this.daily_activity = daily_activity;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getShort_bio() {
                return short_bio;
            }

            public void setShort_bio(String short_bio) {
                this.short_bio = short_bio;
            }

            public String getTraining_type() {
                return training_type;
            }

            public void setTraining_type(String training_type) {
                this.training_type = training_type;
            }

            public String getAndroid_token() {
                return android_token;
            }

            public void setAndroid_token(String android_token) {
                this.android_token = android_token;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(String is_deleted) {
                this.is_deleted = is_deleted;
            }

            @SerializedName("created_at")
            @Expose
            public String created_at;

            @SerializedName("updated_at")
            @Expose
            public String updated_at;

            @SerializedName("is_deleted")
            @Expose
            public String is_deleted;






        }

        private class TimeSlots {
        }
    }
}






