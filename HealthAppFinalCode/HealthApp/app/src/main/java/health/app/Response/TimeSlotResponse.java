package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 9/4/2017.
 */

public class TimeSlotResponse implements Parcelable{
    @SerializedName("status")
    @Expose
    public boolean status;

    protected TimeSlotResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        timeSlotData = in.readParcelable(TimeSlotData.class.getClassLoader());
    }

    public static final Creator<TimeSlotResponse> CREATOR = new Creator<TimeSlotResponse>() {
        @Override
        public TimeSlotResponse createFromParcel(Parcel in) {
            return new TimeSlotResponse(in);
        }

        @Override
        public TimeSlotResponse[] newArray(int size) {
            return new TimeSlotResponse[size];
        }
    };

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

    public TimeSlotData getTimeSlotData() {
        return timeSlotData;
    }

    public void setTimeSlotData(TimeSlotData timeSlotData) {
        this.timeSlotData = timeSlotData;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public TimeSlotData timeSlotData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeParcelable(timeSlotData, i);
    }

    public static class TimeSlotData implements Parcelable{

        protected TimeSlotData(Parcel in) {
            timeSlotList = in.createTypedArrayList(TimeSlot.CREATOR);
            user = in.readParcelable(TrainerListResponse.TrainerData.Trainer.class.getClassLoader());
        }

        public static final Creator<TimeSlotData> CREATOR = new Creator<TimeSlotData>() {
            @Override
            public TimeSlotData createFromParcel(Parcel in) {
                return new TimeSlotData(in);
            }

            @Override
            public TimeSlotData[] newArray(int size) {
                return new TimeSlotData[size];
            }
        };

        public List<TimeSlot> getTimeSlotList() {
            return timeSlotList;
        }

        public void setTimeSlotList(List<TimeSlot> timeSlotList) {
            this.timeSlotList = timeSlotList;
        }

        public TrainerListResponse.TrainerData.Trainer getUser() {
            return user;
        }

        public void setUser(TrainerListResponse.TrainerData.Trainer user) {
            this.user = user;
        }

        @SerializedName("time_slots")
        @Expose
        public List<TimeSlot> timeSlotList;

        @SerializedName("user")
        @Expose
        public TrainerListResponse.TrainerData.Trainer user;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(timeSlotList);
            parcel.writeParcelable(user, i);
        }

        public static class TimeSlot implements Parcelable{

            @SerializedName("SlotId")
            @Expose
            public String SlotId;

            @SerializedName("TrainerId")
            @Expose
            public String TrainerId;

            @SerializedName("SlotDate")
            @Expose
            public String SlotDate;

            @SerializedName("StartTime")
            @Expose
            public String StartTime;

            @SerializedName("EndTime")
            @Expose
            public String EndTime;

            @SerializedName("SlotTitle")
            @Expose
            public String SlotTitle;

            @SerializedName("Address")
            @Expose
            public String Address;

            @SerializedName("SlotDescription")
            @Expose
            public String SlotDescription;

            @SerializedName("IsOccupied")
            @Expose
            public String IsOccupied;

            @SerializedName("CustomerId")
            @Expose
            public String CustomerId;

            @SerializedName("CreatedOn")
            @Expose
            public String CreatedOn;

            protected TimeSlot(Parcel in) {
                SlotId = in.readString();
                TrainerId = in.readString();
                SlotDate = in.readString();
                StartTime = in.readString();
                EndTime = in.readString();
                SlotTitle = in.readString();
                Address = in.readString();
                SlotDescription = in.readString();
                IsOccupied = in.readString();
                CustomerId = in.readString();
                CreatedOn = in.readString();
                UpdatedOn = in.readString();
                IsDeleted = in.readString();
            }

            public static final Creator<TimeSlot> CREATOR = new Creator<TimeSlot>() {
                @Override
                public TimeSlot createFromParcel(Parcel in) {
                    return new TimeSlot(in);
                }

                @Override
                public TimeSlot[] newArray(int size) {
                    return new TimeSlot[size];
                }
            };

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

            @SerializedName("UpdatedOn")
            @Expose
            public String UpdatedOn;

            @SerializedName("IsDeleted")
            @Expose
            public String IsDeleted;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(SlotId);
                parcel.writeString(TrainerId);
                parcel.writeString(SlotDate);
                parcel.writeString(StartTime);
                parcel.writeString(EndTime);
                parcel.writeString(SlotTitle);
                parcel.writeString(Address);
                parcel.writeString(SlotDescription);
                parcel.writeString(IsOccupied);
                parcel.writeString(CustomerId);
                parcel.writeString(CreatedOn);
                parcel.writeString(UpdatedOn);
                parcel.writeString(IsDeleted);
            }
        }
    }
}
