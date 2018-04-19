package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 30-09-2016.
 */
public class MedicineData implements Parcelable {
    private int status;
    private String message;
    List<MedicineOutputData>data;


    public static final Creator<MedicineData> CREATOR = new Creator<MedicineData>() {

        @Override
        public MedicineData createFromParcel(Parcel in) {
            return new MedicineData(in);
        }

        @Override
        public MedicineData[] newArray(int size) {
            return new MedicineData[size];
        }
    };

    protected MedicineData(Parcel in) {
        status = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(MedicineOutputData.CREATOR);
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
        return "MedicineData{" +
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

    public List<MedicineOutputData> getData() {
        return data;
    }

    public void setData(List<MedicineOutputData> data) {
        this.data = data;
    }

}
