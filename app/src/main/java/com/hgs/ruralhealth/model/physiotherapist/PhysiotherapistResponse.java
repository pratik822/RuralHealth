package com.hgs.ruralhealth.model.physiotherapist;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 20-10-2016.
 */
public class PhysiotherapistResponse implements Parcelable {
    private String status, message, syncFromId, syncToId;
    public List<PhysiotherapistInputData> data;

    public PhysiotherapistResponse(){

    }

    public static final Creator<PhysiotherapistResponse> CREATOR = new Creator<PhysiotherapistResponse>() {
        @Override
        public PhysiotherapistResponse createFromParcel(Parcel in) {
            return new PhysiotherapistResponse(in);
        }

        @Override
        public PhysiotherapistResponse[] newArray(int size) {
            return new PhysiotherapistResponse[size];
        }
    };

    protected PhysiotherapistResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        syncFromId = in.readString();
        syncToId = in.readString();
        data = in.createTypedArrayList(PhysiotherapistInputData.CREATOR);
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

    public List<PhysiotherapistInputData> getData() {
        return data;
    }

    public void setData(List<PhysiotherapistInputData> data) {
        this.data = data;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }
}
