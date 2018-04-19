package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 04-10-2016.
 */
public class SymptompsOutputData implements Parcelable {
    private String symptomId;
    private String symptomName;

    public static final Creator<SymptompsOutputData> CREATOR = new Creator<SymptompsOutputData>() {
        @Override
        public SymptompsOutputData createFromParcel(Parcel in) {
            return new SymptompsOutputData(in);
        }

        @Override
        public SymptompsOutputData[] newArray(int size) {
            return new SymptompsOutputData[size];
        }
    };


    public SymptompsOutputData(){

    }

    protected SymptompsOutputData(Parcel in) {
        symptomId = in.readString();
        symptomName = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symptomId);
        dest.writeString(symptomName);
    }


    @Override
    public String toString() {
        return "SymptompsOutputData{" +
                "symptomId='" + symptomId + '\'' +
                ", symptomName='" + symptomName + '\'' +
                '}';
    }

    //genrate getter setter
    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
}
