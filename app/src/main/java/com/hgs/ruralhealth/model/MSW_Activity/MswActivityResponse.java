package com.hgs.ruralhealth.model.MSW_Activity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 20-10-2016.
 */
public class MswActivityResponse implements Parcelable {
    private String status, message, syncFromId, syncToId;
    public List<MswAcitivyInputData> data;

    public MswActivityResponse(){

    }

    public static final Creator<MswActivityResponse> CREATOR = new Creator<MswActivityResponse>() {
        @Override
        public MswActivityResponse createFromParcel(Parcel in) {
            return new MswActivityResponse(in);
        }

        @Override
        public MswActivityResponse[] newArray(int size) {
            return new MswActivityResponse[size];
        }
    };

    protected MswActivityResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        syncFromId = in.readString();
        syncToId = in.readString();
        data = in.createTypedArrayList(MswAcitivyInputData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeString(syncFromId);
        dest.writeString(syncToId);
        dest.writeTypedList(data);
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

    public String getSyncFromId() {
        return syncFromId;
    }

    public void setSyncFromId(String syncFromId) {
        this.syncFromId = syncFromId;
    }

    public List<MswAcitivyInputData> getData() {
        return data;
    }

    public void setData(List<MswAcitivyInputData> data) {
        this.data = data;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }
}
