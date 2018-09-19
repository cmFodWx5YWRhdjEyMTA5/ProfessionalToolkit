package health.app.Model;

/**
 * Created by Developer Six on 9/27/2017.
 */

public class ChatModal {
    private String msgId;
    private String msgBy;
    private String msgTo;
    private String fromid;
    private String toID;
    private String msgText;
    private String isDeleted;
    private String createdOn;
    private String byUserName;
    private String byUserImage;
    public static final int SENDTYPE = 0;
    public static final int RECVICE_TYPE = 1;
    private int mType;

    public ChatModal(String msgText, String createdOn, int type) {
        this.msgText = msgText;
        this.createdOn = createdOn;
        this.mType = type;
    }
    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }



  /*  public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }*/

  /*  public String getMsgBy() {
        return msgBy;
    }

    public void setMsgBy(String msgBy) {
        this.msgBy = msgBy;
    }*/

   /* public String getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }*/

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

  /*  public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }*/

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

  /*  public String getByUserName() {
        return byUserName;
    }

    public void setByUserName(String byUserName) {
        this.byUserName = byUserName;
    }

    public String getByUserImage() {
        return byUserImage;
    }

    public void setByUserImage(String byUserImage) {
        this.byUserImage = byUserImage;
    }*/

}
