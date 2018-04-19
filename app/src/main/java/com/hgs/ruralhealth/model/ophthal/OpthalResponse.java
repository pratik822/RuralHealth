package com.hgs.ruralhealth.model.ophthal;


import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.MSW_Distribution.Msw_Distribution_InputData;

import java.util.List;

/**
 * Created by pratikb on 20-10-2016.
 */
public class OpthalResponse implements Parcelable {
    private String status, message, syncFromId, syncToId;
    public List<OphthalInputData> data;

    public OpthalResponse(){

    }

    public static final Creator<OpthalResponse> CREATOR = new Creator<OpthalResponse>() {
        @Override
        public OpthalResponse createFromParcel(Parcel in) {
            return new OpthalResponse(in);
        }

        @Override
        public OpthalResponse[] newArray(int size) {
            return new OpthalResponse[size];
        }
    };

    protected OpthalResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        syncFromId = in.readString();
        syncToId = in.readString();
        data = in.createTypedArrayList(OphthalInputData.CREATOR);
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

    public List<OphthalInputData> getData() {
        return data;
    }

    public void setData(List<OphthalInputData> data) {
        this.data = data;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }
}
