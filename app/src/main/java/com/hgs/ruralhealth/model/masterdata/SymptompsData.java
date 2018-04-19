package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 04-10-2016.
 */
public class SymptompsData implements Parcelable {
    private String status;
    private String message;
    private  List<SymptompsOutputData>data;

    public SymptompsData(){


    }
    public static final Creator<SymptompsData> CREATOR = new Creator<SymptompsData>() {
        @Override
        public SymptompsData createFromParcel(Parcel in) {
            return new SymptompsData(in);
        }

        @Override
        public SymptompsData[] newArray(int size) {
            return new SymptompsData[size];
        }
    };

    protected SymptompsData(Parcel in) {
        status = in.readString();
        message = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
    }
    //genrate getter setter

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SymptompsOutputData> getData() {
        return data;
    }

    public void setData(List<SymptompsOutputData> data) {
        this.data = data;
    }
}
