package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 30-09-2016.
 */
public class VillageData implements Parcelable {
    private int status;
    private String message;
    List<VillageOutputData>data;


    public static final Creator<VillageData> CREATOR = new Creator<VillageData>() {

        @Override
        public VillageData createFromParcel(Parcel in) {
            return new VillageData(in);
        }

        @Override
        public VillageData[] newArray(int size) {
            return new VillageData[size];
        }
    };

    protected VillageData(Parcel in) {
        status = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(VillageOutputData.CREATOR);
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
        return "VillageData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
    //genrate getter setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<VillageOutputData> getData() {
        return data;
    }

    public void setData(List<VillageOutputData> data) {
        this.data = data;
    }

}
