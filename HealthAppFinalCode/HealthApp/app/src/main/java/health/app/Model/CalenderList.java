package health.app.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import health.app.Response.TimeSlotResponse;
import health.app.Response.TrainerListResponse;

/**
 * Created by Developer Six on 8/9/2017.
 */

public class CalenderList {

    public List<CalenderTimeSlot> getCalenderTimeSlots() {
        return calenderTimeSlots;
    }

    public void setCalenderTimeSlots(List<CalenderTimeSlot> calenderTimeSlots) {
        this.calenderTimeSlots = calenderTimeSlots;
    }

    public CalenderUser getCalenderUser() {
        return calenderUser;
    }

    public void setCalenderUser(CalenderUser calenderUser) {
        this.calenderUser = calenderUser;
    }

    public List<CalenderTimeSlot> calenderTimeSlots;

    public CalenderUser calenderUser;

    public static class CalenderTimeSlot {

        public String SlotId;


        public String TrainerId;


        public String SlotDate;


        public String StartTime;

        public String EndTime;


        public String SlotTitle;


        public String Address;


        public String SlotDescription;


        public String IsOccupied;

        public String CustomerId;

        public String CreatedOn;

        public String getSlotId() {
            return SlotId;
        }

        public void setSlotId(String slotId) {
            SlotId = slotId;
        }

        public String getTrainerId() {
            return TrainerId;
        }

        public void setTrainerId(String trainerId) {
            TrainerId = trainerId;
        }

        public String getSlotDate() {
            return SlotDate;
        }

        public void setSlotDate(String slotDate) {
            SlotDate = slotDate;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getSlotTitle() {
            return SlotTitle;
        }

        public void setSlotTitle(String slotTitle) {
            SlotTitle = slotTitle;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getSlotDescription() {
            return SlotDescription;
        }

        public void setSlotDescription(String slotDescription) {
            SlotDescription = slotDescription;
        }

        public String getIsOccupied() {
            return IsOccupied;
        }

        public void setIsOccupied(String isOccupied) {
            IsOccupied = isOccupied;
        }

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String customerId) {
            CustomerId = customerId;
        }

        public String getCreatedOn() {
            return CreatedOn;
        }

        public void setCreatedOn(String createdOn) {
            CreatedOn = createdOn;
        }

        public String getUpdatedOn() {
            return UpdatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            UpdatedOn = updatedOn;
        }

        public String getIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            IsDeleted = isDeleted;
        }

        public String UpdatedOn;

        public String IsDeleted;
    }

    public static class CalenderUser {


        public String id;


        public String user_type;

        public String email;


        public String first_name;

        public String last_name;


        public String phone;

        public String age;


        public String fat;

        public String weight;

        public String height;


        public String gender;


        public String daily_activity;

        public String birthday;

        public String profile_image;


        public String short_bio;


        public String training_type;


        public String android_token;


        public String latitude;


        public String longitude;


        public String created_at;

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

        public String updated_at;


        public String is_deleted;

    }
}

