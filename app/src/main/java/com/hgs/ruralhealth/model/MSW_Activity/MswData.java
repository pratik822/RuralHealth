package com.hgs.ruralhealth.model.MSW_Activity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 20-10-2016.
 */
public class MswData implements Parcelable {
    private String totalReceived;
    private String totalAdded;

    public MswData(){

    }

    public static final Creator<MswData> CREATOR = new Creator<MswData>() {
        @Override
        public MswData createFromParcel(Parcel in) {
            return new MswData(in);
        }

        @Override
        public MswData[] newArray(int size) {
            return new MswData[size];
        }
    };

    protected MswData(Parcel in) {
        totalReceived = in.readString();
        totalAdded = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalReceived);
        dest.writeString(totalAdded);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "RegistrationData{" +
                "totalReceived='" + totalReceived + '\'' +
                ",totalAdded='" + totalAdded + '\'' +
                '}';
    }
}
