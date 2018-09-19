package health.app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer Six on 11/10/2017.
 */

public class AllMeasurement {

     String Weight;

     String Height;

     String Waist;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

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

    public String getChest() {
        return Chest;
    }

    public void setChest(String chest) {
        Chest = chest;
    }

    public String getCalf() {
        return Calf;
    }

    public void setCalf(String calf) {
        Calf = calf;
    }

    public String getThigh() {
        return Thigh;
    }

    public void setThigh(String thigh) {
        Thigh = thigh;
    }

    public String getArm() {
        return Arm;
    }

    public void setArm(String arm) {
        Arm = arm;
    }

    String Neck;

     String BMI;

     String Hips;

    String Chest;
    String Calf;
    String Thigh;
    String Arm;

}
