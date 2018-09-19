package health.app.Model;

import java.io.File;

/**
 * Created by Developer Six on 8/8/2017.
 */

public class SignUp {
    public File getcImagePath() {
        return cImagePath;
    }

    public void setcImagePath(File cImagePath) {
        this.cImagePath = cImagePath;
    }

    File cImagePath;

    public String getcFirstName() {
        return cFirstName;
    }

    public void setcFirstName(String cFirstName) {
        this.cFirstName = cFirstName;
    }

    public String getcLastName() {
        return cLastName;
    }

    public void setcLastName(String cLastName) {
        this.cLastName = cLastName;
    }

    String cFirstName;
    String cLastName;


    public String getcGender() {
        return cGender;
    }

    public void setcGender(String cGender) {
        this.cGender = cGender;
    }

    public String getcPhoneNo() {
        return cPhoneNo;
    }

    public void setcPhoneNo(String cPhoneNo) {
        this.cPhoneNo = cPhoneNo;
    }

    public String getcAge() {
        return cAge;
    }

    public void setcAge(String cAge) {
        this.cAge = cAge;
    }



    public String getcHeight() {
        return cHeight;
    }

    public void setcHeight(String cHeight) {
        this.cHeight = cHeight;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcBirthday() {
        return cBirthday;
    }

    public void setcBirthday(String cBirthday) {
        this.cBirthday = cBirthday;
    }

    public String getcDailyActivity() {
        return cDailyActivity;
    }

    public void setcDailyActivity(String cDailyActivity) {
        this.cDailyActivity = cDailyActivity;
    }

    public String getcShortBio() {
        return cShortBio;
    }

    public void setcShortBio(String cShortBio) {
        this.cShortBio = cShortBio;
    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    String cGender;
    String cPhoneNo;
    String cAge;


    public String getcWeight() {
        return cWeight;
    }

    public void setcWeight(String cWeight) {
        this.cWeight = cWeight;
    }

    String cWeight;
    String cHeight;
    String cEmail;
    String cBirthday;
    String cDailyActivity;
    String cShortBio;
    String cPassword;
}
