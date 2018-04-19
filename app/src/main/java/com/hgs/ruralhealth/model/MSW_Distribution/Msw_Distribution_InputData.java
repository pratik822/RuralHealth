package com.hgs.ruralhealth.model.MSW_Distribution;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 03-11-2016.
 */
public class Msw_Distribution_InputData implements Parcelable {
    private String createdDate, mSWName, village, isOld, tabName;
    private int doctorId;
    private List<Items> items;

    public Msw_Distribution_InputData() {

    }

    public static final Creator<Msw_Distribution_InputData> CREATOR = new Creator<Msw_Distribution_InputData>() {
        @Override
        public Msw_Distribution_InputData createFromParcel(Parcel in) {
            return new Msw_Distribution_InputData(in);
        }

        @Override
        public Msw_Distribution_InputData[] newArray(int size) {
            return new Msw_Distribution_InputData[size];
        }
    };

    Msw_Distribution_InputData(Parcel in) {
        createdDate = in.readString();
        mSWName = in.readString();
        village = in.readString();
        isOld = in.readString();
        tabName = in.readString();
        items = in.createTypedArrayList(com.hgs.ruralhealth.model.MSW_Distribution.Items.CREATOR);
        doctorId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdDate);
        dest.writeString(mSWName);
        dest.writeString(village);
        dest.writeString(isOld);
        dest.writeString(tabName);
        dest.writeTypedList(items);
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
        return mSWName;
    }

    public void setMswName(String mswName) {
        this.mSWName = mswName;
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

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public List<com.hgs.ruralhealth.model.MSW_Distribution.Items> getItems() {
        return items;
    }

    public void setItems(List<com.hgs.ruralhealth.model.MSW_Distribution.Items> item) {
        items = item;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
