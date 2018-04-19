package com.hgs.ruralhealth.model.MSW_Activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by pratikb on 03-11-2016.
 */
public class MswAcitivyInputData implements Parcelable {
    private String createdDate, mswName, village, otherActivity, childrenCount, isTeacherPresent, isWaterFilterPresent, isHandWashPresent, supportReq, remark, houseVisit, tabNo, isOld;
    private int doctorId;
    private ArrayList<String> activities;


    public MswAcitivyInputData() {

    }

    public static final Creator<MswAcitivyInputData> CREATOR = new Creator<MswAcitivyInputData>() {
        @Override
        public MswAcitivyInputData createFromParcel(Parcel in) {
            return new MswAcitivyInputData(in);
        }

        @Override
        public MswAcitivyInputData[] newArray(int size) {
            return new MswAcitivyInputData[size];
        }
    };

    protected MswAcitivyInputData(Parcel in) {
        createdDate = in.readString();
        mswName = in.readString();
        village = in.readString();
        otherActivity = in.readString();
        childrenCount = in.readString();
        isTeacherPresent = in.readString();
        isWaterFilterPresent = in.readString();
        isHandWashPresent = in.readString();
        supportReq = in.readString();
        remark = in.readString();
        houseVisit = in.readString();
        tabNo = in.readString();
        isOld = in.readString();
        activities = in.createStringArrayList();
        doctorId=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdDate);
        dest.writeString(mswName);
        dest.writeString(village);
        dest.writeString(otherActivity);
        dest.writeString(childrenCount);
        dest.writeString(isTeacherPresent);
        dest.writeString(isWaterFilterPresent);
        dest.writeString(isHandWashPresent);
        dest.writeString(supportReq);
        dest.writeString(remark);
        dest.writeString(houseVisit);
        dest.writeString(tabNo);
        dest.writeString(isOld);
        dest.writeStringList(activities);
        dest.writeInt(doctorId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getMswName() {
        return mswName;
    }

    public void setMswName(String mswName) {
        this.mswName = mswName;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(String childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getOtherOption() {
        return otherActivity;
    }

    public void setOtherOption(String otherOption) {
        this.otherActivity = otherOption;
    }

    public String getIsWaterFilterPresent() {
        return isWaterFilterPresent;
    }

    public void setIsWaterFilterPresent(String isWaterFilterPresent) {
        this.isWaterFilterPresent = isWaterFilterPresent;
    }

    public String getIsTeacherPresent() {
        return isTeacherPresent;
    }

    public void setIsTeacherPresent(String isTeacherPresent) {
        this.isTeacherPresent = isTeacherPresent;
    }

    public String getIsHandWashPresent() {
        return isHandWashPresent;
    }

    public void setIsHandWashPresent(String isHandWashPresent) {
        this.isHandWashPresent = isHandWashPresent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSupportReq() {
        return supportReq;
    }

    public void setSupportReq(String supportReq) {
        this.supportReq = supportReq;
    }

    public String getHouseVisit() {
        return houseVisit;
    }

    public void setHouseVisit(String houseVisit) {
        this.houseVisit = houseVisit;
    }

    public String getTabNo() {
        return tabNo;
    }

    public void setTabNo(String tabNo) {
        this.tabNo = tabNo;
    }

    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
