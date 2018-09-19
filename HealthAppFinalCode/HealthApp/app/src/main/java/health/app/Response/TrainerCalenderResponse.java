package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 11/6/2017.
 */

public class TrainerCalenderResponse implements Parcelable{

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

    public CalenderData getCalenderData() {
        return calenderData;
    }

    public void setCalenderData(CalenderData calenderData) {
        this.calenderData = calenderData;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public CalenderData calenderData;

    protected TrainerCalenderResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        calenderData = in.readParcelable(CalenderData.class.getClassLoader());
    }

    public static final Creator<TrainerCalenderResponse> CREATOR = new Creator<TrainerCalenderResponse>() {
        @Override
        public TrainerCalenderResponse createFromParcel(Parcel in) {
            return new TrainerCalenderResponse(in);
        }

        @Override
        public TrainerCalenderResponse[] newArray(int size) {
            return new TrainerCalenderResponse[size];
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
        parcel.writeParcelable(calenderData, i);
    }

    public static class CalenderData implements Parcelable {

        public List<Customers> getCustomersList() {
            return customersList;
        }

        public void setCustomersList(List<Customers> customersList) {
            this.customersList = customersList;
        }

        @SerializedName("customers")
        @Expose
        List<Customers> customersList;

        protected CalenderData(Parcel in) {
            customersList = in.createTypedArrayList(Customers.CREATOR);
        }

        public static final Creator<CalenderData> CREATOR = new Creator<CalenderData>() {
            @Override
            public CalenderData createFromParcel(Parcel in) {
                return new CalenderData(in);
            }

            @Override
            public CalenderData[] newArray(int size) {
                return new CalenderData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(customersList);
        }

        public static class Customers implements Parcelable {

            @SerializedName("SessionSlotId")
            @Expose
            public String aessionSlotId;

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

            public String getAessionSlotId() {
                return aessionSlotId;
            }

            public void setAessionSlotId(String aessionSlotId) {
                this.aessionSlotId = aessionSlotId;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @SerializedName("IsDeleted")
            @Expose
            public String isDeleted;

            @SerializedName("Status")
            @Expose
            public String status;

            protected Customers(Parcel in) {
                aessionSlotId = in.readString();
                customerId = in.readString();
                packageId = in.readString();
                requestId = in.readString();
                sessionDate = in.readString();
                startTime = in.readString();
                endTime = in.readString();
                isDeleted = in.readString();
                status = in.readString();
            }

            public static final Creator<Customers> CREATOR = new Creator<Customers>() {
                @Override
                public Customers createFromParcel(Parcel in) {
                    return new Customers(in);
                }

                @Override
                public Customers[] newArray(int size) {
                    return new Customers[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(aessionSlotId);
                parcel.writeString(customerId);
                parcel.writeString(packageId);
                parcel.writeString(requestId);
                parcel.writeString(sessionDate);
                parcel.writeString(startTime);
                parcel.writeString(endTime);
                parcel.writeString(isDeleted);
                parcel.writeString(status);
            }
        }
    }
}
