package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/18/2016.
 */
public class VillageOutputData implements Parcelable {
    private Integer villageId;
    private String villageName;
    private String villagePrefix;

    public VillageOutputData(){

    }

    public static final Creator<VillageOutputData> CREATOR = new Creator<VillageOutputData>() {
        @Override
        public VillageOutputData createFromParcel(Parcel in) {
            return new VillageOutputData(in);
        }

        @Override
        public VillageOutputData[] newArray(int size) {
            return new VillageOutputData[size];
        }
    };

    protected VillageOutputData(Parcel in) {
        this();
        villageId = in.readInt();
        villageName = in.readString();
        villagePrefix = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(villageId);
        dest.writeString(villageName);
        dest.writeString(villagePrefix);
    }

    @Override
    public String toString() {
        return "VillageOutputData{" +
                "villageId='" + villageId + '\'' +
                ", villageName='" + villageName + '\'' +
                ", villagePrefix='" + villagePrefix + '\'' +
                '}';
    }

    //genrate getter setter
    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillagePrefix() {
        return villagePrefix;
    }

    public void setVillagePrefix(String villagePrefix) {
        this.villagePrefix = villagePrefix;
    }
}
