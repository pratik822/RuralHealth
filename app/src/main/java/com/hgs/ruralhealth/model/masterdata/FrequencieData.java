package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.user.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pratikb on 30-09-2016.
 */
public class FrequencieData implements Parcelable {
    private String status;
    private String message;
    List<FrequencieOutputData>data;


    public static final Creator<FrequencieData> CREATOR = new Creator<FrequencieData>() {

        @Override
        public FrequencieData createFromParcel(Parcel in) {
            return new FrequencieData(in);
        }

        @Override
        public FrequencieData[] newArray(int size) {
            return new FrequencieData[size];
        }
    };

    public FrequencieData() {
    }


    protected FrequencieData(Parcel in) {
        this();
        status = in.readString();
        message = in.readString();
        in.readList(data, ArrayList.class.getClassLoader());
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "FrequencieData{" +
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FrequencieOutputData> getData() {
        return data;
    }

    public void setData(List<FrequencieOutputData> data) {
        this.data = data;
    }

}
