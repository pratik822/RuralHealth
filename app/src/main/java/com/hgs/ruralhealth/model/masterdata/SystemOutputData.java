package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/18/2016.
 */
public class SystemOutputData implements Parcelable {
    private int systemId;
    private String systemName;

    public SystemOutputData() {
    }

    public static final Creator<SystemOutputData> CREATOR = new Creator<SystemOutputData>() {
        @Override
        public SystemOutputData createFromParcel(Parcel in) {
            return new SystemOutputData(in);
        }

        @Override
        public SystemOutputData[] newArray(int size) {
            return new SystemOutputData[size];
        }
    };

    protected SystemOutputData(Parcel in) {
        systemId = in.readInt();
        systemName = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(systemId);
        dest.writeString(systemName);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "SystemOutputData{" +
                "systemId='" + systemId + '\'' +
                ", systemName='" + systemName + '\'' +
                '}';
    }
    //genrate getter setter

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}








