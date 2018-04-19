package com.hgs.ruralhealth.model.register;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 20-10-2016.
 */
public class RegistrationResponse implements Parcelable{
    private String status,message;
    public RegistrationData data;

    public RegistrationResponse(){
        data=new RegistrationData();
    }

    public static final Creator<RegistrationResponse> CREATOR = new Creator<RegistrationResponse>() {
        @Override
        public RegistrationResponse createFromParcel(Parcel in) {
            return new RegistrationResponse(in);
        }

        @Override
        public RegistrationResponse[] newArray(int size) {
            return new RegistrationResponse[size];
        }
    };



    protected RegistrationResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
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

    public RegistrationData getData() {
        return data;
    }

    public void setData(RegistrationData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "status='" + status + '\'' +
                ",message='" + message + '\'' +
                ",data='" + data + '\'' +
                '}';
    }
}
