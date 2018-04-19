package com.hgs.ruralhealth.model.register;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 22-09-2016.
 */
public class test implements Parcelable{
    private  String CurrentData;
    private  String SWPNumber;
    private  String PatientName;
    private int AGE;
    private String Gender;
    private String Viilage;
    private String Blurred;
    private String Headache;
    private String Rednes;
    private String Pinguecula;
    private String Watering;
    private String Itching;
    private String Coloboma;
    private String Squint;
    private String ReferactiveError;
    private String Bitots_Spot;
    private String Pterigum;
    private String Pain;
    private String Stye;
    private String Swelling;
    private String Nystogums;
    private String Discharge;
    private String Cotoract;
    private String SpectacleAdvised;
    private String Cornea;
    private String Retina;
    private String LazyEye;

    protected test(Parcel in) {
        CurrentData = in.readString();
        SWPNumber = in.readString();
        PatientName = in.readString();
        AGE = in.readInt();
        Gender = in.readString();
        Viilage = in.readString();
        Blurred = in.readString();
        Headache = in.readString();
        Rednes = in.readString();
        Pinguecula = in.readString();
        Watering = in.readString();
        Itching = in.readString();
        Coloboma = in.readString();
        Squint = in.readString();
        ReferactiveError = in.readString();
        Bitots_Spot = in.readString();
        Pterigum = in.readString();
        Pain = in.readString();
        Stye = in.readString();
        Swelling = in.readString();
        Nystogums = in.readString();
        Discharge = in.readString();
        Cotoract = in.readString();
        SpectacleAdvised = in.readString();
        Cornea = in.readString();
        Retina = in.readString();
        LazyEye = in.readString();
    }

    public static final Creator<test> CREATOR = new Creator<test>() {
        @Override
        public test createFromParcel(Parcel in) {
            return new test(in);
        }

        @Override
        public test[] newArray(int size) {
            return new test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CurrentData);
        dest.writeString(SWPNumber);
        dest.writeString(PatientName);
        dest.writeInt(AGE);
        dest.writeString(Gender);
        dest.writeString(Viilage);
        dest.writeString(Blurred);
        dest.writeString(Headache);
        dest.writeString(Rednes);
        dest.writeString(Pinguecula);
        dest.writeString(Watering);
        dest.writeString(Itching);
        dest.writeString(Coloboma);
        dest.writeString(Squint);
        dest.writeString(ReferactiveError);
        dest.writeString(Bitots_Spot);
        dest.writeString(Pterigum);
        dest.writeString(Pain);
        dest.writeString(Stye);
        dest.writeString(Swelling);
        dest.writeString(Nystogums);
        dest.writeString(Discharge);
        dest.writeString(Cotoract);
        dest.writeString(SpectacleAdvised);
        dest.writeString(Cornea);
        dest.writeString(Retina);
        dest.writeString(LazyEye);
    }
}
