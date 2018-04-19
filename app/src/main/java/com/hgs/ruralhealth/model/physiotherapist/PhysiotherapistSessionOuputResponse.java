package com.hgs.ruralhealth.model.physiotherapist;


import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.MSW_Activity.MswData;

/**
 * Created by pratikb on 20-10-2016.
 */
public class PhysiotherapistSessionOuputResponse implements Parcelable{
    private String status,message;
    public PhysiotherapistOutputData data;

    public PhysiotherapistSessionOuputResponse(){

    }
    public static final Creator<PhysiotherapistSessionOuputResponse> CREATOR = new Creator<PhysiotherapistSessionOuputResponse>() {
        @Override
        public PhysiotherapistSessionOuputResponse createFromParcel(Parcel in) {
            return new PhysiotherapistSessionOuputResponse(in);
        }

        @Override
        public PhysiotherapistSessionOuputResponse[] newArray(int size) {
            return new PhysiotherapistSessionOuputResponse[size];
        }
    };

    protected PhysiotherapistSessionOuputResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.readParcelable(MswData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeParcelable(data, flags);
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

    public PhysiotherapistOutputData getData() {
        return data;
    }

    public void setData(PhysiotherapistOutputData data) {
        this.data = data;
    }
}
