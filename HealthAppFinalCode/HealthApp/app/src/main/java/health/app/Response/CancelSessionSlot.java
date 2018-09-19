package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 11/16/2017.
 */

public class CancelSessionSlot {

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

    public SessionSlotsData getSessionSlotData() {
        return sessionSlotData;
    }

    public void setSessionSlotData(SessionSlotsData sessionSlotData) {
        this.sessionSlotData = sessionSlotData;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public SessionSlotsData sessionSlotData;


    public class SessionSlotsData {

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTrainerId() {
            return trainerId;
        }

        public void setTrainerId(String trainerId) {
            this.trainerId = trainerId;
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

        public String getSessionTime() {
            return sessionTime;
        }

        public void setSessionTime(String sessionTime) {
            this.sessionTime = sessionTime;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public List<CancelSlot> getCancelSlotList() {
            return cancelSlotList;
        }

        public void setCancelSlotList(List<CancelSlot> cancelSlotList) {
            this.cancelSlotList = cancelSlotList;
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

        @SerializedName("Status")
        @Expose
        public String status;

        @SerializedName("TrainerId")
        @Expose
        public String trainerId;

        @SerializedName("first_name")
        @Expose
        public String firstName;

        @SerializedName("last_name")
        @Expose
        public String lastName;

        @SerializedName("profile_image")
        @Expose
        public String profileImage;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("SessionTime")
        @Expose
        public String sessionTime;

        @SerializedName("base_url")
        @Expose
        public String baseUrl;

        @SerializedName("slots")
        @Expose
        List<CancelSlot> cancelSlotList;

        public class CancelSlot {

            @SerializedName("SessionSlotId")
            @Expose
            public String sessionSlotId;

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

        }
    }
}
