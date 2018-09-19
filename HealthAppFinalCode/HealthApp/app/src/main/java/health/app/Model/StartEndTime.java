package health.app.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Developer Six on 10/24/2017.
 */

public class StartEndTime implements Parcelable {
    String startTime;
    String SessionSlotId;
    String CustomerId;
    String email;
    String profileImage;
    String PackageId;
    String RequestId;
    String SessionDate;
    String StartTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(String bodyFat) {
        this.bodyFat = bodyFat;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSessionSlotId() {
        return SessionSlotId;
    }

    public void setSessionSlotId(String sessionSlotId) {
        SessionSlotId = sessionSlotId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public String getSessionDate() {
        return SessionDate;
    }

    public void setSessionDate(String sessionDate) {
        SessionDate = sessionDate;
    }

    String EndTime;
    String IsDeleted;
    String Status;
    String endTime;
    String firstName;
    String lastName;
    String age;
    String gender;
    String height;
    String weight;
    String bodyFat;
    String neck;
    String waist;
    String phone;

    public StartEndTime() {

    }


    protected StartEndTime(Parcel in) {
        startTime = in.readString();
        SessionSlotId = in.readString();
        CustomerId = in.readString();
        email = in.readString();
        profileImage = in.readString();
        PackageId = in.readString();
        RequestId = in.readString();
        SessionDate = in.readString();
        StartTime = in.readString();
        EndTime = in.readString();
        IsDeleted = in.readString();
        Status = in.readString();
        endTime = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        age = in.readString();
        gender = in.readString();
        height = in.readString();
        weight = in.readString();
        bodyFat = in.readString();
        neck = in.readString();
        waist = in.readString();
        phone = in.readString();
    }

    public static final Creator<StartEndTime> CREATOR = new Creator<StartEndTime>() {
        @Override
        public StartEndTime createFromParcel(Parcel in) {
            return new StartEndTime(in);
        }

        @Override
        public StartEndTime[] newArray(int size) {
            return new StartEndTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(startTime);
        parcel.writeString(SessionSlotId);
        parcel.writeString(CustomerId);
        parcel.writeString(email);
        parcel.writeString(profileImage);
        parcel.writeString(PackageId);
        parcel.writeString(RequestId);
        parcel.writeString(SessionDate);
        parcel.writeString(StartTime);
        parcel.writeString(EndTime);
        parcel.writeString(IsDeleted);
        parcel.writeString(Status);
        parcel.writeString(endTime);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(age);
        parcel.writeString(gender);
        parcel.writeString(height);
        parcel.writeString(weight);
        parcel.writeString(bodyFat);
        parcel.writeString(neck);
        parcel.writeString(waist);
        parcel.writeString(phone);
    }
}

