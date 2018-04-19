package com.hgs.ruralhealth.model.physiotherapist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 20-10-2016.
 */
public class PhysiotherapistOutputData implements Parcelable {
    private String totalReceived;
    private String totalAdded;

    public PhysiotherapistOutputData(){

    }

    public static final Creator<PhysiotherapistOutputData> CREATOR = new Creator<PhysiotherapistOutputData>() {
        @Override
        public PhysiotherapistOutputData createFromParcel(Parcel in) {
            return new PhysiotherapistOutputData(in);
        }

        @Override
        public PhysiotherapistOutputData[] newArray(int size) {
            return new PhysiotherapistOutputData[size];
        }
    };

    protected PhysiotherapistOutputData(Parcel in) {
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


}
