package health.app.Demo;

import java.io.Serializable;

/**
 * Created by Developer Six on 11/1/2017.
 */

public class Student implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String startTime;

    private String endTime;

    private String name;

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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    private String profileImage;

    private boolean isSelected;

    public String getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
    }

    private String slotDate;

    public Student() {

    }

    public Student(String startTime, String endTime, boolean isSelected) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}