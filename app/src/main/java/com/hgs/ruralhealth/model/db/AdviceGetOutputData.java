package com.hgs.ruralhealth.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.register.RegisterOutputData;

import java.util.List;

/**
 * Created by rameshg on 10/21/2016.
 */
public class AdviceGetOutputData implements Parcelable {
    private String status, message, syncFromId, syncToId;
    List<AdviceInputData> data;


    public static final Creator<AdviceGetOutputData> CREATOR = new Creator<AdviceGetOutputData>() {
        @Override
        public AdviceGetOutputData createFromParcel(Parcel in) {
            return new AdviceGetOutputData(in);
        }

        @Override
        public AdviceGetOutputData[] newArray(int size) {
            return new AdviceGetOutputData[size];
        }
    };

    protected AdviceGetOutputData(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(AdviceInputData.CREATOR);
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

    public List<AdviceInputData> getData() {
        return data;
    }

    public void setData(List<AdviceInputData> data) {
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
        return "AdviceGetOutputData{" +
                "status='" + status + '\'' +
                ",message='" + message + '\'' +
                ",data='" + data + '\'' +
                ",syncFromId='" + syncFromId + '\'' +
                ",syncToId='" + syncToId + '\'' +
                '}';
    }
}
