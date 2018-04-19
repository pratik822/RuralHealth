package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/18/2016.
 */
public class MedicineOutputData implements Parcelable {
    private Integer medicineId;
    private String medicineName;
    private String medicineCode;

    public MedicineOutputData(){
    }

    public static final Creator<MedicineOutputData> CREATOR = new Creator<MedicineOutputData>() {
        @Override
        public MedicineOutputData createFromParcel(Parcel in) {
            return new MedicineOutputData(in);
        }

        @Override
        public MedicineOutputData[] newArray(int size) {
            return new MedicineOutputData[size];
        }
    };

    protected MedicineOutputData(Parcel in) {
        medicineName = in.readString();
        medicineCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medicineName);
        dest.writeString(medicineCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "MedicineOutputData{" +
                "medicineId='" + medicineId + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", medicineCode='" + medicineCode + '\'' +
                '}';
    }
    //genrate getter setter
    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}




