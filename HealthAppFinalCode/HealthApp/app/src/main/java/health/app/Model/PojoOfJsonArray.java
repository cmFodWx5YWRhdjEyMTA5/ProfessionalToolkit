package health.app.Model;

/**
 * Created by Developer Six on 9/13/2017.
 */

public class PojoOfJsonArray {

   String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSessionSlotId() {
        return sessionSlotId;
    }

    public void setSessionSlotId(String sessionSlotId) {
        this.sessionSlotId = sessionSlotId;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String sessionSlotId;
    String nId;
    String status;
    String trainerId;
    String name;
    String date;
    String nType;
    String msg;
    String image;

    public PojoOfJsonArray(String requestId, String sessionSlotId, String nId, String status, String trainerId, String name, String date, String nType, String msg, String image) {
        this.requestId = requestId;
        this.sessionSlotId = sessionSlotId;
        this.nId = nId;
        this.status = status;
        this.trainerId = trainerId;
        this.name = name;
        this.date = date;
        this.nType = nType;
        this.msg = msg;
        this.image = image;
    }




}
