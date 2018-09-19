package health.app.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 8/29/2017.
 */

public class MessageListResponse implements Parcelable {
    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("msg")
    @Expose
    public String msg;

    protected MessageListResponse(Parcel in) {
        status = in.readByte() != 0;
        msg = in.readString();
        messageDataList = in.createTypedArrayList(MessageData.CREATOR);
    }

    public static final Creator<MessageListResponse> CREATOR = new Creator<MessageListResponse>() {
        @Override
        public MessageListResponse createFromParcel(Parcel in) {
            return new MessageListResponse(in);
        }

        @Override
        public MessageListResponse[] newArray(int size) {
            return new MessageListResponse[size];
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

    public List<MessageData> getMessageDataList() {
        return messageDataList;
    }

    public void setMessageDataList(List<MessageData> messageDataList) {
        this.messageDataList = messageDataList;
    }

    @SerializedName("data")
    @Expose
    public List<MessageData> messageDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(msg);
        parcel.writeTypedList(messageDataList);
    }

    public static class MessageData implements Parcelable {
        @SerializedName("MsgId")
        @Expose
        public String MsgId;

        @SerializedName("Msg")
        @Expose
        public String Msg;

        @SerializedName("MsgFrom")
        @Expose
        public String MsgFrom;

        @SerializedName("MsgTo")
        @Expose
        public String MsgTo;


        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected;

        @SerializedName("DeletedBy")
        @Expose
        public String DeletedBy;

        @SerializedName("CreatedOn")
        @Expose
        public String CreatedOn;

        @SerializedName("UpdatedOn")
        @Expose
        public String UpdatedOn;

        @SerializedName("first_name")
        @Expose
        public String first_name;

        @SerializedName("last_name")
        @Expose
        public String last_name;

        @SerializedName("profile_image")
        @Expose
        public String profile_image;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("training_type")
        @Expose
        public String training_type;

        @SerializedName("email")
        @Expose
        public String email;

        protected MessageData(Parcel in) {
            MsgId = in.readString();
            Msg = in.readString();
            MsgFrom = in.readString();
            MsgTo = in.readString();
            DeletedBy = in.readString();
            CreatedOn = in.readString();
            UpdatedOn = in.readString();
            first_name = in.readString();
            last_name = in.readString();
            profile_image = in.readString();
            phone = in.readString();
            training_type = in.readString();
            email = in.readString();
            latitude = in.readString();
            longitude = in.readString();
        }

        public static final Creator<MessageData> CREATOR = new Creator<MessageData>() {
            @Override
            public MessageData createFromParcel(Parcel in) {
                return new MessageData(in);
            }

            @Override
            public MessageData[] newArray(int size) {
                return new MessageData[size];
            }
        };

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            MsgId = msgId;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String msg) {
            Msg = msg;
        }

        public String getMsgFrom() {
            return MsgFrom;
        }

        public void setMsgFrom(String msgFrom) {
            MsgFrom = msgFrom;
        }

        public String getMsgTo() {
            return MsgTo;
        }

        public void setMsgTo(String msgTo) {
            MsgTo = msgTo;
        }

        public String getDeletedBy() {
            return DeletedBy;
        }

        public void setDeletedBy(String deletedBy) {
            DeletedBy = deletedBy;
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

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTraining_type() {
            return training_type;
        }

        public void setTraining_type(String training_type) {
            this.training_type = training_type;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @SerializedName("latitude")
        @Expose
        public String latitude;

        @SerializedName("longitude")
        @Expose
        public String longitude;

        public int getUnread() {
            return unread;
        }

        public void setUnread(int unread) {
            this.unread = unread;
        }

        @SerializedName("unread")
        @Expose
        public int unread;
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(MsgId);
            parcel.writeString(Msg);
            parcel.writeString(MsgFrom);
            parcel.writeString(MsgTo);
            parcel.writeString(DeletedBy);
            parcel.writeString(CreatedOn);
            parcel.writeString(UpdatedOn);
            parcel.writeString(first_name);
            parcel.writeString(last_name);
            parcel.writeString(profile_image);
            parcel.writeString(phone);
            parcel.writeString(training_type);
            parcel.writeString(email);
            parcel.writeString(latitude);
            parcel.writeString(longitude);
        }
    }
}
