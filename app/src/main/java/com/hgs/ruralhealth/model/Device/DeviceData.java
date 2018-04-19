package com.hgs.ruralhealth.model.Device;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 24-04-2017.
 */
public class DeviceData implements Parcelable {
    String deviceId,deviceName,serialNumber;

    public DeviceData(){

    }

    public static final Creator<DeviceData> CREATOR = new Creator<DeviceData>() {
        @Override
        public DeviceData createFromParcel(Parcel in) {
            return new DeviceData(in);
        }

        @Override
        public DeviceData[] newArray(int size) {
            return new DeviceData[size];
        }
    };

    protected DeviceData(Parcel in) {
        deviceId = in.readString();
        deviceName = in.readString();
        serialNumber = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceId);
        dest.writeString(deviceName);
        dest.writeString(serialNumber);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
