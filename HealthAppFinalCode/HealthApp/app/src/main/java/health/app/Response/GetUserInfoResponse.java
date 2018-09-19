package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 10/26/2017.
 */

public class GetUserInfoResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("data")
    @Expose
    public UserData userData;

    public class UserData {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("user_type")
        @Expose
        public String user_type;

        @SerializedName("UniqueId")
        @Expose
        public String UniqueId;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("password")
        @Expose
        public String password;

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

        @SerializedName("Waist")
        @Expose
        public String Waist;

        @SerializedName("Neck")
        @Expose
        public String Neck;

        @SerializedName("Hips")
        @Expose
        public String Hips;

        @SerializedName("Calf")
        @Expose
        public String Calf;

        @SerializedName("Thigh")
        @Expose
        public String Thigh;

        public String getCalf() {
            return Calf;
        }

        public void setCalf(String calf) {
            Calf = calf;
        }

        public String getThigh() {
            return Thigh;
        }

        public void setThigh(String thigh) {
            Thigh = thigh;
        }

        public String getChest() {
            return Chest;
        }

        public void setChest(String chest) {
            Chest = chest;
        }

        public String getArm() {
            return Arm;
        }

        public void setArm(String arm) {
            Arm = arm;
        }

        @SerializedName("Chest")
        @Expose
        public String Chest;

        @SerializedName("Arm")
        @Expose
        public String Arm;

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

        @SerializedName("ios_token")
        @Expose
        public String ios_token;

        @SerializedName("latitude")
        @Expose
        public String latitude;

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

        public String getUniqueId() {
            return UniqueId;
        }

        public void setUniqueId(String uniqueId) {
            UniqueId = uniqueId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getWaist() {
            return Waist;
        }

        public void setWaist(String waist) {
            Waist = waist;
        }

        public String getNeck() {
            return Neck;
        }

        public void setNeck(String neck) {
            Neck = neck;
        }

        public String getHips() {
            return Hips;
        }

        public void setHips(String hips) {
            Hips = hips;
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

        public String getIos_token() {
            return ios_token;
        }

        public void setIos_token(String ios_token) {
            this.ios_token = ios_token;
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

        @SerializedName("longitude")
        @Expose
        public String longitude;

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
}
