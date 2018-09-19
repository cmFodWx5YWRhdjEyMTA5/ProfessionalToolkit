package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 10/11/2017.
 */

public class TrainerPackageResponse implements Parcelable{
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

    public List<PackageData> getPackageDataList() {
        return packageDataList;
    }

    public void setPackageDataList(List<PackageData> packageDataList) {
        this.packageDataList = packageDataList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public List<PackageData> packageDataList;

    public static class PackageData  implements Parcelable{

        @SerializedName("SlotId")
        @Expose
        public String slotId;

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

        public List<OccupiedDates> getOccupiedDatesList() {
            return occupiedDatesList;
        }

        public void setOccupiedDatesList(List<OccupiedDates> occupiedDatesList) {
            this.occupiedDatesList = occupiedDatesList;
        }

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

        @SerializedName("occupied_dates")
        @Expose
        List<OccupiedDates> occupiedDatesList;

        protected PackageData(Parcel in) {
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
        }

        public static final Creator<PackageData> CREATOR = new Creator<PackageData>() {
            @Override
            public PackageData createFromParcel(Parcel in) {
                return new PackageData(in);
            }

            @Override
            public PackageData[] newArray(int size) {
                return new PackageData[size];
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
        }


        public static class OccupiedDates implements Parcelable{
            @SerializedName("SessionSlotId")
            @Expose
            public String sessionSlotId;

            public String getSessionSlotId() {
                return sessionSlotId;
            }

            public void setSessionSlotId(String sessionSlotId) {
                this.sessionSlotId = sessionSlotId;
            }

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
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

            public String getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                this.isDeleted = isDeleted;
            }

            @SerializedName("CustomerId")
            @Expose
            public String customerId;

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

            @SerializedName("IsDeleted")
            @Expose
            public String isDeleted;

            protected OccupiedDates(Parcel in) {
                sessionSlotId = in.readString();
                customerId = in.readString();
                packageId = in.readString();
                requestId = in.readString();
                sessionDate = in.readString();
                startTime = in.readString();
                endTime = in.readString();
                isDeleted = in.readString();
            }

            public static final Creator<OccupiedDates> CREATOR = new Creator<OccupiedDates>() {
                @Override
                public OccupiedDates createFromParcel(Parcel in) {
                    return new OccupiedDates(in);
                }

                @Override
                public OccupiedDates[] newArray(int size) {
                    return new OccupiedDates[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(sessionSlotId);
                parcel.writeString(customerId);
                parcel.writeString(packageId);
                parcel.writeString(requestId);
                parcel.writeString(sessionDate);
                parcel.writeString(startTime);
                parcel.writeString(endTime);
                parcel.writeString(isDeleted);
            }
        }

    }

    protected TrainerPackageResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        packageDataList = in.createTypedArrayList(PackageData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(msg);
        dest.writeTypedList(packageDataList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrainerPackageResponse> CREATOR = new Creator<TrainerPackageResponse>() {
        @Override
        public TrainerPackageResponse createFromParcel(Parcel in) {
            return new TrainerPackageResponse(in);
        }

        @Override
        public TrainerPackageResponse[] newArray(int size) {
            return new TrainerPackageResponse[size];
        }
    };
}
