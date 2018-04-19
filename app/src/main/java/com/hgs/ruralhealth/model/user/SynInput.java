package com.hgs.ruralhealth.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 21-10-2016.
 */
public class SynInput implements Parcelable {
    private String syncFromId,syncToId;

    public SynInput(){

    }


    public static final Creator<SynInput> CREATOR = new Creator<SynInput>() {
        @Override
        public SynInput createFromParcel(Parcel in) {
            return new SynInput(in);
        }

        @Override
        public SynInput[] newArray(int size) {
            return new SynInput[size];
        }
    };


    protected SynInput(Parcel in) {
        syncFromId = in.readString();
        syncToId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(syncFromId);
        dest.writeString(syncToId);
    }

    @Override
    public int describeContents() {
        return 0;

    }

    public String getSyncFromId() {
        return syncFromId;
    }

    public void setSyncFromId(String syncFromId) {
        this.syncFromId = syncFromId;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }


    @Override
    public String toString() {
        return "SynInput{" +
                "syncFromId='" + syncFromId + '\'' +
                ",syncToId='" + syncToId + '\'' +
                '}';
    }
}
