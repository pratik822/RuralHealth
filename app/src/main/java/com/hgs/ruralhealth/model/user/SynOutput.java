package com.hgs.ruralhealth.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 21-10-2016.
 */
public class SynOutput implements Parcelable {
    private String status,message,data,syncFromId,syncToId;

    public SynOutput(){

    }


    public static final Creator<SynOutput> CREATOR = new Creator<SynOutput>() {
        @Override
        public SynOutput createFromParcel(Parcel in) {
            return new SynOutput(in);
        }

        @Override
        public SynOutput[] newArray(int size) {
            return new SynOutput[size];
        }
    };


    protected SynOutput(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.readString();
        syncFromId = in.readString();
        syncToId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeString(data);
        dest.writeString(syncFromId);
        dest.writeString(syncToId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        return "SynOutput{" +
                "status='" + status + '\'' +
                ",message='" + message + '\'' +
                ",data='" + data + '\'' +
                ",syncFromId='" + syncFromId + '\'' +
                ",syncToId='" + syncToId + '\'' +
                '}';
    }
}
