package com.hgs.ruralhealth.model.physiotherapist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by rameshg on 12/14/2016.
 */
public class PhysioSessionResponse implements Parcelable {
    private String status;
    private String message;
    public List<PhysioSessionInputData> data;
    private String syncFromId;
    private String syncToId;


    public PhysioSessionResponse() {

    }

    public static final Creator<PhysioSessionResponse> CREATOR = new Creator<PhysioSessionResponse>() {
        @Override
        public PhysioSessionResponse createFromParcel(Parcel in) {
            return new PhysioSessionResponse(in);
        }

        @Override
        public PhysioSessionResponse[] newArray(int size) {
            return new PhysioSessionResponse[size];
        }
    };

    protected PhysioSessionResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(PhysioSessionInputData.CREATOR);
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

    //Setter and Getter

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

    public List<PhysioSessionInputData> getData() {
        return data;
    }

    public void setData(List<PhysioSessionInputData> data) {
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
}