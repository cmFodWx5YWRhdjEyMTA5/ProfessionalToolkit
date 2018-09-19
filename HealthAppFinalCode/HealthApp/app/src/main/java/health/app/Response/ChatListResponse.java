package health.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Developer Six on 9/28/2017.
 */

public class ChatListResponse {

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

    public List<ChatList> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatList> chatList) {
        this.chatList = chatList;
    }

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("data")
    @Expose
    public List<ChatList> chatList;


    public int getNext_Page() {
        return Next_Page;
    }

    public void setNext_Page(int next_Page) {
        Next_Page = next_Page;
    }

    @SerializedName("page_no")
    @Expose
    public int Next_Page;

    public class ChatList {

        @SerializedName("MsgId")
        @Expose
        public String msgId;

        @SerializedName("Msg")
        @Expose
        public String messageTxt;

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getMessageTxt() {
            return messageTxt;
        }

        public void setMessageTxt(String messageTxt) {
            this.messageTxt = messageTxt;
        }

        public String getMsgFrom() {
            return msgFrom;
        }

        public void setMsgFrom(String msgFrom) {
            this.msgFrom = msgFrom;
        }

        public String getMsgTo() {
            return msgTo;
        }

        public void setMsgTo(String msgTo) {
            this.msgTo = msgTo;
        }

        public String getDeletedBy() {
            return deletedBy;
        }

        public void setDeletedBy(String deletedBy) {
            this.deletedBy = deletedBy;
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

        public String getTrainingType() {
            return trainingType;
        }

        public void setTrainingType(String trainingType) {
            this.trainingType = trainingType;
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

        @SerializedName("MsgFrom")
        @Expose
        public String msgFrom;

        @SerializedName("MsgTo")
        @Expose
        public String msgTo;

        @SerializedName("DeletedBy")
        @Expose
        public String deletedBy;

        @SerializedName("CreatedOn")
        @Expose
        public String createdOn;

        @SerializedName("UpdatedOn")
        @Expose
        public String updatedOn;

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

        @SerializedName("training_type")
        @Expose
        public String trainingType;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("latitude")
        @Expose
        public String latitude;

        @SerializedName("longitude")
        @Expose
        public String longitude;

    }
}
