package com.hgs.ruralhealth.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rameshg on 9/18/2016.
 */
public class MedicationPrescribed implements Parcelable {

    public static final Creator<MedicationPrescribed> CREATOR = new Creator<MedicationPrescribed>() {
        @Override
        public MedicationPrescribed createFromParcel(Parcel in) {
            return new MedicationPrescribed(in);
        }

        @Override
        public MedicationPrescribed[] newArray(int size) {
            return new MedicationPrescribed[size];
        }
    };
    private String swpNo;
    private String medicine;
    private String frequency;
    private String duration;

    public String getSwpNo() {
        return swpNo;
    }

    public void setSwpNo(String swpNo) {
        this.swpNo = swpNo;
    }

    private String createdDate;

    public MedicationPrescribed() {
    }

    protected MedicationPrescribed(Parcel in) {
        this();
        medicine = in.readString();
        frequency = in.readString();
        duration = in.readString();
        createdDate = in.readString();
        swpNo=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medicine);
        dest.writeString(frequency);
        dest.writeString(duration);
        dest.writeString(createdDate);
        dest.writeString(swpNo);

    }

    @Override
    public String toString() {
        return "MedicationPrescribed{" +
                "medicine='" + medicine + '\'' +
                ", frequency='" + frequency + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    ;

    @Override
    public int describeContents() {
        return 0;
    }


    //Setter and Getter
    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
