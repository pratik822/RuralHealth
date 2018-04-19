package com.hgs.ruralhealth.model.ophthal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 20-10-2016.
 */
public class OpthalOutputData implements Parcelable {
    private String totalReceived;
    private String totalAdded;

    public OpthalOutputData(){

    }

    public static final Creator<OpthalOutputData> CREATOR = new Creator<OpthalOutputData>() {
        @Override
        public OpthalOutputData createFromParcel(Parcel in) {
            return new OpthalOutputData(in);
        }

        @Override
        public OpthalOutputData[] newArray(int size) {
            return new OpthalOutputData[size];
        }
    };

    protected OpthalOutputData(Parcel in) {
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
