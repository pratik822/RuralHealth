package com.hgs.ruralhealth.model.physiotherapist;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 20-10-2016.
 */
public class PhysiotherapisSessiontResponse implements Parcelable {
    private String status, message, syncFromId, syncToId;
    public List<PhysioSessionInputData> data;

    public PhysiotherapisSessiontResponse(){

    }

    public static final Creator<PhysiotherapisSessiontResponse> CREATOR = new Creator<PhysiotherapisSessiontResponse>() {
        @Override
        public PhysiotherapisSessiontResponse createFromParcel(Parcel in) {
            return new PhysiotherapisSessiontResponse(in);
        }

        @Override
        public PhysiotherapisSessiontResponse[] newArray(int size) {
            return new PhysiotherapisSessiontResponse[size];
        }
    };

    protected PhysiotherapisSessiontResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        syncFromId = in.readString();
        syncToId = in.readString();
        data = in.createTypedArrayList(PhysioSessionInputData.CREATOR);
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

    public List<PhysioSessionInputData> getData() {
        return data;
    }

    public void setData(List<PhysioSessionInputData> data) {
        this.data = data;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }
}
