package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 21-04-2017.
 */
public class InvestigationData implements Parcelable  {
    String investigationId,investigationName;

    public static final Creator<InvestigationData> CREATOR = new Creator<InvestigationData>() {
        @Override
        public InvestigationData createFromParcel(Parcel in) {
            return new InvestigationData(in);
        }

        @Override
        public InvestigationData[] newArray(int size) {
            return new InvestigationData[size];
        }
    };

    public InvestigationData(){

    }


    protected InvestigationData(Parcel in) {
        investigationId = in.readString();
        investigationName = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(investigationId);
        dest.writeString(investigationName);
    }

    public String getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(String investigationId) {
        this.investigationId = investigationId;
    }

    public String getInvestigationName() {
        return investigationName;
    }

    public void setInvestigationName(String investigationName) {
        this.investigationName = investigationName;
    }
}
