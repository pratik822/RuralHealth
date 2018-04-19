package com.hgs.ruralhealth.model.MSW_Distribution;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 20-10-2016.
 */
public class MswDistributionData implements Parcelable {
    private String totalReceived;
    private String totalAdded;

    public MswDistributionData(){

    }

    public static final Creator<MswDistributionData> CREATOR = new Creator<MswDistributionData>() {
        @Override
        public MswDistributionData createFromParcel(Parcel in) {
            return new MswDistributionData(in);
        }

        @Override
        public MswDistributionData[] newArray(int size) {
            return new MswDistributionData[size];
        }
    };

    protected MswDistributionData(Parcel in) {
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
