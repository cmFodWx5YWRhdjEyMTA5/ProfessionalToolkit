package health.app.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Developer Six on 9/1/2017.
 */

public class CompletedSessionList implements Parcelable{

    String firstName;
    String lastName;

    public CompletedSessionList(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        imageUrl = in.readString();
        phone = in.readString();
        profileImage = in.readString();
        sessionDays = in.readString();
        sessionTime = in.readString();
        sessionPrice = in.readString();
        noOfSlots = in.readString();
        sessionDate = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        status = in.readString();
    }
    public CompletedSessionList(){

    }

    public static final Creator<CompletedSessionList> CREATOR = new Creator<CompletedSessionList>() {
        @Override
        public CompletedSessionList createFromParcel(Parcel in) {
            return new CompletedSessionList(in);
        }

        @Override
        public CompletedSessionList[] newArray(int size) {
            return new CompletedSessionList[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String imageUrl;

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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSessionDays() {
        return sessionDays;
    }

    public void setSessionDays(String sessionDays) {
        this.sessionDays = sessionDays;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(String sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public String getNoOfSlots() {
        return noOfSlots;
    }

    public void setNoOfSlots(String noOfSlots) {
        this.noOfSlots = noOfSlots;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String phone;
    String profileImage;
    String sessionDays;
    String sessionTime;
    String sessionPrice;
    String noOfSlots;
    String sessionDate;
    String startTime;
    String endTime;
    String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(imageUrl);
        parcel.writeString(phone);
        parcel.writeString(profileImage);
        parcel.writeString(sessionDays);
        parcel.writeString(sessionTime);
        parcel.writeString(sessionPrice);
        parcel.writeString(noOfSlots);
        parcel.writeString(sessionDate);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeString(status);
    }
}
