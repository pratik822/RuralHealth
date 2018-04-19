package com.hgs.ruralhealth.model.ophthal;


import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.MSW_Activity.MswData;
import com.hgs.ruralhealth.model.MSW_Distribution.MswDistributionData;

/**
 * Created by pratikb on 20-10-2016.
 */
public class OpthalOuputResponse implements Parcelable{
    private String status,message;
    public OpthalOutputData data;

    public OpthalOuputResponse(){

    }
    public static final Creator<OpthalOuputResponse> CREATOR = new Creator<OpthalOuputResponse>() {
        @Override
        public OpthalOuputResponse createFromParcel(Parcel in) {
            return new OpthalOuputResponse(in);
        }

        @Override
        public OpthalOuputResponse[] newArray(int size) {
            return new OpthalOuputResponse[size];
        }
    };

    protected OpthalOuputResponse(Parcel in) {
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

    public OpthalOutputData getData() {
        return data;
    }

    public void setData(OpthalOutputData data) {
        this.data = data;
    }
}
