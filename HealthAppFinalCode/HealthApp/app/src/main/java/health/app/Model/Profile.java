package health.app.Model;

import java.io.File;

/**
 * Created by Developer Six on 8/26/2017.
 */

public class Profile {
    File ttrainerImage;

    public String getTfirstName() {
        return tfirstName;
    }

    public void setTfirstName(String tfirstName) {
        this.tfirstName = tfirstName;
    }

    public String getTlastName() {
        return tlastName;
    }

    public void setTlastName(String tlastName) {
        this.tlastName = tlastName;
    }

    String tfirstName;
    String tlastName;
    String temail;

    public File getTtrainerImage() {
        return ttrainerImage;
    }

    public void setTtrainerImage(File ttrainerImage) {
        this.ttrainerImage = ttrainerImage;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public String getTphoneNum() {
        return tphoneNum;
    }

    public void setTphoneNum(String tphoneNum) {
        this.tphoneNum = tphoneNum;
    }


    public String getTlocation() {
        return tlocation;
    }

    public void setTlocation(String tlocation) {
        this.tlocation = tlocation;
    }

    public File getCtrainerImage() {
        return ctrainerImage;
    }

    public void setCtrainerImage(File ctrainerImage) {
        this.ctrainerImage = ctrainerImage;
    }


    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCphoneNum() {
        return cphoneNum;
    }

    public void setCphoneNum(String cphoneNum) {
        this.cphoneNum = cphoneNum;
    }

    public String getCheight() {
        return cheight;
    }

    public void setCheight(String cheight) {
        this.cheight = cheight;
    }

    public String getCweight() {
        return cweight;
    }

    public void setCweight(String cweight) {
        this.cweight = cweight;
    }

    String tphoneNum;


    public String getTtrainingType() {
        return ttrainingType;
    }

    public void setTtrainingType(String ttrainingType) {
        this.ttrainingType = ttrainingType;
    }

    String ttrainingType;
    String tlocation;
    File ctrainerImage;

    public String getCfName() {
        return cfName;
    }

    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    public String getClName() {
        return clName;
    }

    public void setClName(String clName) {
        this.clName = clName;
    }

    String cfName;
    String clName;
    String cemail;
    String cphoneNum;
    String cheight;
    String cweight;


    public String getCgender() {
        return cgender;
    }

    public void setCgender(String cgender) {
        this.cgender = cgender;
    }

    String cgender;
}
