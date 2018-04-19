package com.hgs.ruralhealth.model.ophthal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 10/3/2016.
 */
public class Morbidity implements Parcelable{

    public static final Creator<Morbidity> CREATOR = new Creator<Morbidity>() {
        @Override
        public Morbidity createFromParcel(Parcel in) {
            return new Morbidity(in);
        }

        @Override
        public Morbidity[] newArray(int size) {
            return new Morbidity[size];
        }
    };

    private int morbidityID;
    private String morbidityName;

    public Morbidity(){
    }

    protected Morbidity(Parcel in) {
        this();
        morbidityID = in.readInt();
        morbidityName = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(morbidityID);
        dest.writeString(morbidityName);

    }

    @Override
    public String toString() {
        return "Morbidity{" +
                "morbidityID='" + morbidityID + '\'' +
                ",morbidityName='" + morbidityName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Setter and Getter Methods
    public int getMorbidityID() {
        return morbidityID;
    }

    public void setMorbidityID(int morbidityID) {
        this.morbidityID = morbidityID;
    }

    public String getMorbidityName() {
        return morbidityName;
    }

    public void setMorbidityName(String morbidityName) {
        this.morbidityName = morbidityName;
    }
}


