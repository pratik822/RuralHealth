package com.hgs.ruralhealth.model.register;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 20-10-2016.
 */
public class RegistrationData implements Parcelable {
    private String totalReceived;
    private String totalAdded;

    public RegistrationData(){

    }

    public static final Creator<RegistrationData> CREATOR = new Creator<RegistrationData>() {
        @Override
        public RegistrationData createFromParcel(Parcel in) {
            return new RegistrationData(in);
        }

        @Override
        public RegistrationData[] newArray(int size) {
            return new RegistrationData[size];
        }
    };

    protected RegistrationData(Parcel in) {
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
