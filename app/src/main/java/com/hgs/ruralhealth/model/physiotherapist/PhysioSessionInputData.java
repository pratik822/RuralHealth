package com.hgs.ruralhealth.model.physiotherapist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 12/14/2016.
 */
public class PhysioSessionInputData implements Parcelable {

    public static final Creator<PhysioSessionInputData> CREATOR = new Creator<PhysioSessionInputData>() {
        @Override
        public PhysioSessionInputData createFromParcel(Parcel in) {
            return new PhysioSessionInputData(in);
        }

        @Override
        public PhysioSessionInputData[] newArray(int size) {
            return new PhysioSessionInputData[size];
        }
    };

    private String createdDate;
    private String sessionTaken;
    private String noOfChildrenAtt;
    private String teacherPresent;
    private String supportReq;
    private String remark;
    private String isOld;
    private int doctorId;


    public PhysioSessionInputData(){
    }
    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    protected PhysioSessionInputData(Parcel in) {
        this();
        createdDate = in.readString();
        sessionTaken = in.readString();
        noOfChildrenAtt = in.readString();
        teacherPresent = in.readString();
        supportReq = in.readString();
        remark = in.readString();
        isOld = in.readString();
        doctorId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdDate);
        dest.writeString(sessionTaken);
        dest.writeString(noOfChildrenAtt);
        dest.writeString(teacherPresent);
        dest.writeString(supportReq);
        dest.writeString(remark);
        dest.writeString(isOld);
        dest.writeInt(doctorId);
    }

    @Override
    public String toString() {
        return "PhysioSessionInputData{" +
                "currentDate='" + createdDate + '\'' +
                ", sessionTaken='" + sessionTaken + '\'' +
                ", noOfChildrenAtt='" + noOfChildrenAtt + '\'' +
                ", teacherPresent='" + teacherPresent + '\'' +
                ", supportReq='" + supportReq + '\'' +
                ", remark='" + remark + '\'' +
                ", isOld='" + isOld + '\'' +
                ", doctorId='" + doctorId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Setter and Getter Methods


    public String getCurrentDate() {
        return createdDate;
    }

    public void setCurrentDate(String currentDate) {
        this.createdDate = currentDate;
    }

    public String getSessionTaken() {
        return sessionTaken;
    }

    public void setSessionTaken(String sessionTaken) {
        this.sessionTaken = sessionTaken;
    }

    public String getNoOfChildrenAtt() {
        return noOfChildrenAtt;
    }

    public void setNoOfChildrenAtt(String noOfChildrenAtt) {
        this.noOfChildrenAtt = noOfChildrenAtt;
    }

    public String getTeacherPresent() {
        return teacherPresent;
    }

    public void setTeacherPresent(String teacherPresent) {
        this.teacherPresent = teacherPresent;
    }

    public String getSupportReq() {
        return supportReq;
    }

    public void setSupportReq(String supportReq) {
        this.supportReq = supportReq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}


