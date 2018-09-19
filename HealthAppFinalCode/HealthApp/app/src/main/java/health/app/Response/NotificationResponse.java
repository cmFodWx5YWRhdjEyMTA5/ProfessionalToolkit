package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 8/30/2017.
 */

public class NotificationResponse  {

    @SerializedName("status")
    @Expose
    public boolean status;

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

    public List<NotificationData> getNotificationDataList() {
        return notificationDataList;
    }

    public void setNotificationDataList(List<NotificationData> notificationDataList) {
        this.notificationDataList = notificationDataList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public List<NotificationData> notificationDataList;


    public static class NotificationData{

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("user_type")
        @Expose
        public String userType;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("password")
        @Expose
        public String password;

        @SerializedName("first_name")
        @Expose
        public String firstName;

        @SerializedName("last_name")
        @Expose
        public String lastName;

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
        public String dailyActivity;

        @SerializedName("birthday")
        @Expose
        public String birthday;

        @SerializedName("profile_image")
        @Expose
        public String profileImage;

        @SerializedName("short_bio")
        @Expose
        public String shortBio;

        @SerializedName("training_type")
        @Expose
        public String trainingType;

        @SerializedName("android_token")
        @Expose
        public String androidToken;

        @SerializedName("latitude")
        @Expose
        public String latitude;

        @SerializedName("longitude")
        @Expose
        public String longitude;

        @SerializedName("created_at")
        @Expose
        public String createdAt;

        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

        @SerializedName("is_deleted")
        @Expose
        public String isDeleted;

        @SerializedName("N_Id")
        @Expose
        public String NId;

        @SerializedName("N_Title")
        @Expose
        public String NTitle;

        @SerializedName("N_Type")
        @Expose
        public String NType;

        @SerializedName("N_By")
        @Expose
        public String NBy;

        @SerializedName("N_FromId")
        @Expose
        public String NFromId;

        @SerializedName("N_ToId")
        @Expose
        public String NToId;

        @SerializedName("PackageId")
        @Expose
        public String PackageId;

        @SerializedName("RequestId")
        @Expose
        public String RequestId;

        @SerializedName("SessionSlotId")
        @Expose
        public String SessionSlotId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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

        public String getDailyActivity() {
            return dailyActivity;
        }

        public void setDailyActivity(String dailyActivity) {
            this.dailyActivity = dailyActivity;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getShortBio() {
            return shortBio;
        }

        public void setShortBio(String shortBio) {
            this.shortBio = shortBio;
        }

        public String getTrainingType() {
            return trainingType;
        }

        public void setTrainingType(String trainingType) {
            this.trainingType = trainingType;
        }

        public String getAndroidToken() {
            return androidToken;
        }

        public void setAndroidToken(String androidToken) {
            this.androidToken = androidToken;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getCreatedOn() {
            return CreatedOn;
        }

        public void setCreatedOn(String createdOn) {
            CreatedOn = createdOn;
        }

        public String getNId() {
            return NId;
        }

        public void setNId(String NId) {
            this.NId = NId;
        }

        public String getNTitle() {
            return NTitle;
        }

        public void setNTitle(String NTitle) {
            this.NTitle = NTitle;
        }

        public String getNType() {
            return NType;
        }

        public void setNType(String NType) {
            this.NType = NType;
        }

        public String getNBy() {
            return NBy;
        }

        public void setNBy(String NBy) {
            this.NBy = NBy;
        }

        public String getNFromId() {
            return NFromId;
        }

        public void setNFromId(String NFromId) {
            this.NFromId = NFromId;
        }

        public String getNToId() {
            return NToId;
        }

        public void setNToId(String NToId) {
            this.NToId = NToId;
        }

        public String getPackageId() {
            return PackageId;
        }

        public void setPackageId(String packageId) {
            PackageId = packageId;
        }

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String requestId) {
            RequestId = requestId;
        }

        public String getSessionSlotId() {
            return SessionSlotId;
        }

        public void setSessionSlotId(String sessionSlotId) {
            SessionSlotId = sessionSlotId;
        }

        public String getNFor() {
            return NFor;
        }

        public void setNFor(String NFor) {
            this.NFor = NFor;
        }

        @SerializedName("N_For")
        @Expose
        public String NFor;

        @SerializedName("IsDeleted")
        @Expose
        public String IsDeleted;

        @SerializedName("CreatedOn")
        @Expose
        public String CreatedOn;


    }
}
