package com.hgs.ruralhealth.model.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 10/19/2016.
 */
public class AdviceOutputData implements Parcelable {

    public static final Creator<AdviceOutputData> CREATOR = new Creator<AdviceOutputData>() {
        @Override
        public AdviceOutputData createFromParcel(Parcel in) {
            return new AdviceOutputData(in);
        }

        @Override
        public AdviceOutputData[] newArray(int size) {
            return new AdviceOutputData[size];
        }
    };

    private int status;
    private String message;
    private DataOutput data;
    private int syncFromId;
    private int syncToId;

    public AdviceOutputData() {
        data = new DataOutput();
    }

    protected AdviceOutputData(Parcel in) {
        this();
        status = in.readInt();
        message = in.readString();
        data = in.readParcelable(DataOutput.class.getClassLoader());
        syncFromId = in.readInt();
        syncToId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
        dest.writeParcelable(data, flags);
        dest.writeInt(syncFromId);
        dest.writeInt(syncToId);
    }

    @Override
    public String toString() {
        return "AdviceOutputData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", syncFromId='" + syncFromId + '\'' +
                ", syncToId='" + syncToId + '\'' +
                '}';
    }

    ;

    @Override
    public int describeContents() {
        return 0;
    }

    //Setter and Getter
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataOutput getData() {
        return data;
    }

    public void setData(DataOutput data) {
        this.data = data;
    }

    public int getSyncFromId() {
        return syncFromId;
    }

    public void setSyncFromId(int syncFromId) {
        this.syncFromId = syncFromId;
    }

    public int getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(int syncToId) {
        this.syncToId = syncToId;
    }
}
