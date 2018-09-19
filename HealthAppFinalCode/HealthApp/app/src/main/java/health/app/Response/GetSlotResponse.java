package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 11/1/2017.
 */

public class GetSlotResponse {

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

    public int getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(int notificationsCount) {
        this.notificationsCount = notificationsCount;
    }

    public List<SlotData> getSlotDataList() {
        return slotDataList;
    }

    public void setSlotDataList(List<SlotData> slotDataList) {
        this.slotDataList = slotDataList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("notifications_count")
    @Expose
    public int notificationsCount;

    @SerializedName("data")
    @Expose
    public List<SlotData> slotDataList;

    public class SlotData {

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

        @SerializedName("EndTime")
        @Expose
        public String endTime;

        @SerializedName("IsDeleted")
        @Expose
        public String isDeleted;

        @SerializedName("Status")
        @Expose
        public String status;

    }
}
