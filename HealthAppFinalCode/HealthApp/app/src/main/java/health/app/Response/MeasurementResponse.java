package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 10/5/2017.
 */

public class MeasurementResponse implements Parcelable {

    @SerializedName("msg")
    @Expose
    public String msg;

    protected MeasurementResponse(Parcel in) {
        msg = in.readString();
        status = in.readByte() != 0;
        measureDataList = in.createTypedArrayList(MeasureData.CREATOR);
    }

    public static final Creator<MeasurementResponse> CREATOR = new Creator<MeasurementResponse>() {
        @Override
        public MeasurementResponse createFromParcel(Parcel in) {
            return new MeasurementResponse(in);
        }

        @Override
        public MeasurementResponse[] newArray(int size) {
            return new MeasurementResponse[size];
        }
    };

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

    public List<MeasureData> getMeasureDataList() {
        return measureDataList;
    }

    public void setMeasureDataList(List<MeasureData> measureDataList) {
        this.measureDataList = measureDataList;
    }

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("data")
    @Expose
    List<MeasureData> measureDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msg);
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeTypedList(measureDataList);
    }

    public static class MeasureData implements Parcelable {

        @SerializedName("MeasurementId")
        @Expose
        public String MeasurementId;

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

        @SerializedName("Arm")
        @Expose
        public String Arm;

        protected MeasureData(Parcel in) {
            MeasurementId = in.readString();
            UserId = in.readString();
            Weight = in.readString();
            Height = in.readString();
            Waist = in.readString();
            Neck = in.readString();
            BMI = in.readString();
            Hips = in.readString();
            Arm = in.readString();
            Calf = in.readString();
            Chest = in.readString();
            Thigh = in.readString();
            IsDeleted = in.readString();
            CreatedOn = in.readString();
            date = in.readString();
        }

        public static final Creator<MeasureData> CREATOR = new Creator<MeasureData>() {
            @Override
            public MeasureData createFromParcel(Parcel in) {
                return new MeasureData(in);
            }

            @Override
            public MeasureData[] newArray(int size) {
                return new MeasureData[size];
            }
        };

        public String getArm() {
            return Arm;
        }

        public void setArm(String arm) {
            Arm = arm;
        }

        public String getCalf() {
            return Calf;
        }

        public void setCalf(String calf) {
            Calf = calf;
        }

        public String getChest() {
            return Chest;
        }

        public void setChest(String chest) {
            Chest = chest;
        }

        public String getThigh() {
            return Thigh;
        }

        public void setThigh(String thigh) {
            Thigh = thigh;
        }

        @SerializedName("Calf")
        @Expose
        public String Calf;

        @SerializedName("Chest")
        @Expose
        public String Chest;

        @SerializedName("Thigh")
        @Expose
        public String Thigh;

        @SerializedName("IsDeleted")
        @Expose
        public String IsDeleted;

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

        @SerializedName("CreatedOn")
        @Expose
        public String CreatedOn;

        @SerializedName("Date")
        @Expose
        public String date;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(MeasurementId);
            parcel.writeString(UserId);
            parcel.writeString(Weight);
            parcel.writeString(Height);
            parcel.writeString(Waist);
            parcel.writeString(Neck);
            parcel.writeString(BMI);
            parcel.writeString(Hips);
            parcel.writeString(Arm);
            parcel.writeString(Calf);
            parcel.writeString(Chest);
            parcel.writeString(Thigh);
            parcel.writeString(IsDeleted);
            parcel.writeString(CreatedOn);
            parcel.writeString(date);
        }
    }
}
