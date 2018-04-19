package com.hgs.ruralhealth.model.physiotherapist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/28/2016.
 */
public class PhysiotherapistInputData implements Parcelable {

    public static final Creator<PhysiotherapistInputData> CREATOR = new Creator<PhysiotherapistInputData>() {
        @Override
        public PhysiotherapistInputData createFromParcel(Parcel in) {
            return new PhysiotherapistInputData(in);
        }

        @Override
        public PhysiotherapistInputData[] newArray(int size) {
            return new PhysiotherapistInputData[size];
        }
    };

    private String createdDate;
    private String swpNo;
    private String patientName;
    private String age;
    private String gender;
    private String namePhysio;
    private String painScore;
    private String exercieAdvice;
    private String remark;
    private String isOld;
    private int doctorId;


    public PhysiotherapistInputData(){
    }
    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    protected PhysiotherapistInputData(Parcel in) {
        this();
        createdDate = in.readString();
        swpNo = in.readString();
        patientName = in.readString();
        age = in.readString();
        gender = in.readString();
        namePhysio = in.readString();
        painScore = in.readString();
        exercieAdvice = in.readString();
        remark = in.readString();
        isOld = in.readString();
        doctorId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdDate);
        dest.writeString(swpNo);
        dest.writeString(patientName);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(namePhysio);
        dest.writeString(painScore);
        dest.writeString(exercieAdvice);
        dest.writeString(remark);
        dest.writeString(isOld);
        dest.writeInt(doctorId);
    }

    @Override
    public String toString() {
        return "PhysiotherapistInputData{" +
                "currentDate='" + createdDate + '\'' +
                ",swpNo='" + swpNo + '\'' +
                ", patientName='" + patientName + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", namePhysio='" + namePhysio + '\'' +
                ", painScore='" + painScore + '\'' +
                ", exercieAdvice='" + exercieAdvice + '\'' +
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

    public String getSwpNo() {
        return swpNo;
    }

    public void setSwpNo(String swpNo) {
        this.swpNo = swpNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNamePhysio() {
        return namePhysio;
    }

    public void setNamePhysio(String namePhysio) {
        this.namePhysio = namePhysio;
    }

    public String getPainScore() {
        return painScore;
    }

    public void setPainScore(String painScore) {
        this.painScore = painScore;
    }

    public String getExercieAdvice() {
        return exercieAdvice;
    }

    public void setExercieAdvice(String exercieAdvice) {
        this.exercieAdvice = exercieAdvice;
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


