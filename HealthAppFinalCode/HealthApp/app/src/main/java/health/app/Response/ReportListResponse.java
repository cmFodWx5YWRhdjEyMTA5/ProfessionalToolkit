package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 10/6/2017.
 */

public class ReportListResponse {

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("data")
    @Expose
    List<ReportData> reportDataList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ReportData> getReportDataList() {
        return reportDataList;
    }

    public void setReportDataList(List<ReportData> reportDataList) {
        this.reportDataList = reportDataList;
    }

    public class ReportData {

        @SerializedName("MeasurementId")
        @Expose
        public String MeasurementId;

        public String getMeasurementId() {
            return MeasurementId;
        }

        public void setMeasurementId(String measurementId) {
            MeasurementId = measurementId;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String weight) {
            Weight = weight;
        }

        public String getHeight() {
            return Height;
        }

        public void setHeight(String height) {
            Height = height;
        }

        public String getWaist() {
            return Waist;
        }

        public void setWaist(String waist) {
            Waist = waist;
        }

        public String getNeck() {
            return Neck;
        }

        public void setNeck(String neck) {
            Neck = neck;
        }

        public String getBMI() {
            return BMI;
        }

        public void setBMI(String BMI) {
            this.BMI = BMI;
        }

        public String getHips() {
            return Hips;
        }

        public void setHips(String hips) {
            Hips = hips;
        }

        public String getIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            IsDeleted = isDeleted;
        }

        public String getCreatedOn() {
            return CreatedOn;
        }

        public void setCreatedOn(String createdOn) {
            CreatedOn = createdOn;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @SerializedName("UserId")
        @Expose
        public String UserId;

        @SerializedName("Weight")
        @Expose
        public String Weight;

        @SerializedName("Height")
        @Expose
        public String Height;

        @SerializedName("Waist")
        @Expose
        public String Waist;

        @SerializedName("Neck")
        @Expose
        public String Neck;

        @SerializedName("BMI")
        @Expose
        public String BMI;

        @SerializedName("Hips")
        @Expose
        public String Hips;

        @SerializedName("IsDeleted")
        @Expose
        public String IsDeleted;

        @SerializedName("CreatedOn")
        @Expose
        public String CreatedOn;

        @SerializedName("Date")
        @Expose
        public String date;

    }
}
