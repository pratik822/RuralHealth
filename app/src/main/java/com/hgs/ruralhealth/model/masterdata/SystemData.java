package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 30-09-2016.
 */
public class SystemData implements Parcelable {
    private int status;
    private String message;
    List<SystemOutputData>data;


    public static final Creator<SystemData> CREATOR = new Creator<SystemData>() {

        @Override
        public SystemData createFromParcel(Parcel in) {
            return new SystemData(in);
        }

        @Override
        public SystemData[] newArray(int size) {
            return new SystemData[size];
        }
    };

    protected SystemData(Parcel in) {
        status = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(SystemOutputData.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "SystemData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
    //genrate getter setter


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SystemOutputData> getData() {
        return data;
    }

    public void setData(List<SystemOutputData> data) {
        this.data = data;
    }
}
