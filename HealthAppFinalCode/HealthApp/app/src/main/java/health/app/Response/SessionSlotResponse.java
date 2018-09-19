package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 11/7/2017.
 */

public class SessionSlotResponse implements Parcelable {
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

    public SessionSlotData getSessionSlotData() {
        return sessionSlotData;
    }

    public void setSessionSlotData(SessionSlotData sessionSlotData) {
        this.sessionSlotData = sessionSlotData;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public SessionSlotData sessionSlotData;

    protected SessionSlotResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        sessionSlotData = in.readParcelable(SessionSlotData.class.getClassLoader());
    }

    public static final Creator<SessionSlotResponse> CREATOR = new Creator<SessionSlotResponse>() {
        @Override
        public SessionSlotResponse createFromParcel(Parcel in) {
            return new SessionSlotResponse(in);
        }

        @Override
        public SessionSlotResponse[] newArray(int size) {
            return new SessionSlotResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeParcelable(sessionSlotData, i);
    }

    public static class SessionSlotData implements Parcelable{

        @SerializedName("img_url")
        @Expose
        public String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public List<SessionsList> getSessionsListList() {
            return sessionsListList;
        }

        public void setSessionsListList(List<SessionsList> sessionsListList) {
            this.sessionsListList = sessionsListList;
        }

        @SerializedName("sessions")
        @Expose
        public List<SessionsList> sessionsListList;

        protected SessionSlotData(Parcel in) {
            imgUrl = in.readString();
            sessionsListList = in.createTypedArrayList(SessionsList.CREATOR);
        }

        public static final Creator<SessionSlotData> CREATOR = new Creator<SessionSlotData>() {
            @Override
            public SessionSlotData createFromParcel(Parcel in) {
                return new SessionSlotData(in);
            }

            @Override
            public SessionSlotData[] newArray(int size) {
                return new SessionSlotData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(imgUrl);
            parcel.writeTypedList(sessionsListList);
        }


        public static class SessionsList implements Parcelable{

            @SerializedName("SlotId")
            @Expose
            public String slotId;

            @SerializedName("TrainerId")
            @Expose
            public String trainerId;

            @SerializedName("SessionTime")
            @Expose
            public String sessionTime;

            @SerializedName("SessionPrice")
            @Expose
            public String sessionPrice;

            @SerializedName("SlotTitle")
            @Expose
            public String slotTitle;

            @SerializedName("IsOccupied")
            @Expose
            public String isOccupied;

            @SerializedName("CustomerId")
            @Expose
            public String customerId;

            @SerializedName("CreatedOn")
            @Expose
            public String createdOn;

            @SerializedName("UpdatedOn")
            @Expose
            public String updatedOn;

            @SerializedName("IsDeleted")
            @Expose
            public String isDeleted;

            @SerializedName("NoOfSlots")
            @Expose
            public String noOfSlots;
            @SerializedName("SessionDays")
            @Expose
            public String sessionDays;
            @SerializedName("NoOfDays")
            @Expose
            public String noOfDays;


            @SerializedName("SessionSlotId")
            @Expose
            public String sessionSlotId;

            @SerializedName("PackageId")
            @Expose
            public String packageId;

            @SerializedName("RequestId")
            @Expose
            public String requestId;

            @SerializedName("SessionDate")
            @Expose
            public String sessionDate;
            @SerializedName("StartTime")
            @Expose
            public String startTime;
            @SerializedName("EndTime")
            @Expose
            public String endTime;

            @SerializedName("Status")
            @Expose
            public String status;

            @SerializedName("first_name")
            @Expose
            public String firstName;

            @SerializedName("last_name")
            @Expose
            public String lastName;


            public String getSlotId() {
                return slotId;
            }

            public void setSlotId(String slotId) {
                this.slotId = slotId;
            }

            public String getTrainerId() {
                return trainerId;
            }

            public void setTrainerId(String trainerId) {
                this.trainerId = trainerId;
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

            public String getSlotTitle() {
                return slotTitle;
            }

            public void setSlotTitle(String slotTitle) {
                this.slotTitle = slotTitle;
            }

            public String getIsOccupied() {
                return isOccupied;
            }

            public void setIsOccupied(String isOccupied) {
                this.isOccupied = isOccupied;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(String createdOn) {
                this.createdOn = createdOn;
            }

            public String getUpdatedOn() {
                return updatedOn;
            }

            public void setUpdatedOn(String updatedOn) {
                this.updatedOn = updatedOn;
            }

            public String getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getNoOfSlots() {
                return noOfSlots;
            }

            public void setNoOfSlots(String noOfSlots) {
                this.noOfSlots = noOfSlots;
            }

            public String getSessionDays() {
                return sessionDays;
            }

            public void setSessionDays(String sessionDays) {
                this.sessionDays = sessionDays;
            }

            public String getNoOfDays() {
                return noOfDays;
            }

            public void setNoOfDays(String noOfDays) {
                this.noOfDays = noOfDays;
            }

            public String getSessionSlotId() {
                return sessionSlotId;
            }

            public void setSessionSlotId(String sessionSlotId) {
                this.sessionSlotId = sessionSlotId;
            }

            public String getPackageId() {
                return packageId;
            }

            public void setPackageId(String packageId) {
                this.packageId = packageId;
            }

            public String getRequestId() {
                return requestId;
            }

            public void setRequestId(String requestId) {
                this.requestId = requestId;
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

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            @SerializedName("profile_image")
            @Expose
            public String profileImage;

            @SerializedName("phone")
            @Expose
            public String phone;

            protected SessionsList(Parcel in) {
                slotId = in.readString();
                trainerId = in.readString();
                sessionTime = in.readString();
                sessionPrice = in.readString();
                slotTitle = in.readString();
                isOccupied = in.readString();
                customerId = in.readString();
                createdOn = in.readString();
                updatedOn = in.readString();
                isDeleted = in.readString();
                noOfSlots = in.readString();
                sessionDays = in.readString();
                noOfDays = in.readString();
                sessionSlotId = in.readString();
                packageId = in.readString();
                requestId = in.readString();
                sessionDate = in.readString();
                startTime = in.readString();
                endTime = in.readString();
                status = in.readString();
                firstName = in.readString();
                lastName = in.readString();
                profileImage = in.readString();
                phone = in.readString();
            }

            public static final Creator<SessionsList> CREATOR = new Creator<SessionsList>() {
                @Override
                public SessionsList createFromParcel(Parcel in) {
                    return new SessionsList(in);
                }

                @Override
                public SessionsList[] newArray(int size) {
                    return new SessionsList[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(slotId);
                parcel.writeString(trainerId);
                parcel.writeString(sessionTime);
                parcel.writeString(sessionPrice);
                parcel.writeString(slotTitle);
                parcel.writeString(isOccupied);
                parcel.writeString(customerId);
                parcel.writeString(createdOn);
                parcel.writeString(updatedOn);
                parcel.writeString(isDeleted);
                parcel.writeString(noOfSlots);
                parcel.writeString(sessionDays);
                parcel.writeString(noOfDays);
                parcel.writeString(sessionSlotId);
                parcel.writeString(packageId);
                parcel.writeString(requestId);
                parcel.writeString(sessionDate);
                parcel.writeString(startTime);
                parcel.writeString(endTime);
                parcel.writeString(status);
                parcel.writeString(firstName);
                parcel.writeString(lastName);
                parcel.writeString(profileImage);
                parcel.writeString(phone);
            }
        }
    }
}
