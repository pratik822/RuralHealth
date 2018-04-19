package com.hgs.ruralhealth.model.ophthal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rameshg on 9/22/2016.
 */
public class OphthalInputData implements Parcelable {
    private String swpNo;
    private String patientName;
    private String age;
    private String gender;
    private String village;
    private String isOld;
    private String tabName;
    private String createdDate;
    private List<String> morbidity = new ArrayList<>();
    private int doctorId;
    private String remark;

    public static final Creator<OphthalInputData> CREATOR = new Creator<OphthalInputData>() {
        @Override
        public OphthalInputData createFromParcel(Parcel in) {
            return new OphthalInputData(in);
        }

        @Override
        public OphthalInputData[] newArray(int size) {
            return new OphthalInputData[size];
        }
    };

    public OphthalInputData() {

    }

    OphthalInputData(Parcel in) {
        swpNo = in.readString();
        patientName = in.readString();
        age = in.readString();
        gender = in.readString();
        village = in.readString();
        isOld = in.readString();
        tabName = in.readString();
        createdDate = in.readString();
        morbidity = in.createStringArrayList();
        doctorId = in.readInt();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(swpNo);
        dest.writeString(patientName);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(village);
        dest.writeString(isOld);
        dest.writeString(tabName);
        dest.writeString(createdDate);
        dest.writeStringList(morbidity);
        dest.writeInt(doctorId);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getMorbidity() {
        return morbidity;
    }

    public void setMorbidity(List<String> morbidity) {
        this.morbidity = morbidity;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


