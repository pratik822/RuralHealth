package com.hgs.ruralhealth.model.register;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 21-10-2016.
 */
public class RegistrationInputData implements Parcelable {
    private String status,message,syncFromId,syncToId;
    List<RegisterOutputData>data;


    public static final Creator<RegistrationInputData> CREATOR = new Creator<RegistrationInputData>() {
        @Override
        public RegistrationInputData createFromParcel(Parcel in) {
            return new RegistrationInputData(in);
        }

        @Override
        public RegistrationInputData[] newArray(int size) {
            return new RegistrationInputData[size];
        }
    };

    protected RegistrationInputData(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(RegisterOutputData.CREATOR);
        syncFromId = in.readString();
        syncToId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeTypedList(data);
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

    public List<RegisterOutputData> getData() {
        return data;
    }

    public void setData(List<RegisterOutputData> data) {
        this.data = data;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }

    public String getSyncFromId() {
        return syncFromId;
    }

    public void setSyncFromId(String syncFromId) {
        this.syncFromId = syncFromId;
    }

    @Override
    public String toString() {
        return "RegistrationInputData{" +
                "status='" + status + '\'' +
                ",message='" + message + '\'' +
                ",data='" + data + '\'' +
                ",syncFromId='" + syncFromId + '\'' +
                ",syncToId='" + syncToId + '\'' +
                '}';
    }
}
