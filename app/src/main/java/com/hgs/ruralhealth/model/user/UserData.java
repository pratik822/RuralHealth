package com.hgs.ruralhealth.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/29/2016.
 */
public class UserData implements Parcelable {

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    private int userId;
    private String loginId;
    private String password;
    private String name;
    private String emailId;
    private String mobileNo;
    private String deviceName;
    private int doctorId;

    public UserData() {
    }

    protected UserData(Parcel in) {
        this();
        userId = in.readInt();
        loginId = in.readString();
        password = in.readString();
        name = in.readString();
        emailId = in.readString();
        mobileNo = in.readString();
        deviceName = in.readString();
        doctorId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(loginId);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(emailId);
        dest.writeString(mobileNo);
        dest.writeString(deviceName);
        dest.writeInt(doctorId);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userId='" + userId + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", doctorId='" + doctorId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
