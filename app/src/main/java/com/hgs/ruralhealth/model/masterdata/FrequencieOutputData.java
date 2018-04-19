package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/18/2016.
 */
public class FrequencieOutputData implements Parcelable {
    private Integer frequencyId;
    private String frequencyName;

    public FrequencieOutputData(){
    }

    public static final Creator<FrequencieOutputData> CREATOR = new Creator<FrequencieOutputData>() {
        @Override
        public FrequencieOutputData createFromParcel(Parcel in) {
            return new FrequencieOutputData(in);
        }

        @Override
        public FrequencieOutputData[] newArray(int size) {
            return new FrequencieOutputData[size];
        }
    };

    protected FrequencieOutputData(Parcel in) {
        frequencyName = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(frequencyName);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "FrequencieOutputData{" +
                "frequencyId='" + frequencyId + '\'' +
                ", frequencyName='" + frequencyName + '\'' +
                '}';
    }

    //genrate getter setter
    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }
}




