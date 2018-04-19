package com.hgs.ruralhealth.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ramesh on 9/17/2016.
 */

public class AdviceInputData implements Parcelable {

    public static final Creator<AdviceInputData> CREATOR = new Creator<AdviceInputData>() {
        @Override
        public AdviceInputData createFromParcel(Parcel in) {
            return new AdviceInputData(in);
        }

        @Override
        public AdviceInputData[] newArray(int size) {
            return new AdviceInputData[size];
        }
    };

    private String swpNo;
    private String patientName;
    private String chiefComplaints;
    private List<String> system;
    private String treatmentAdviced;
    private List<MedicationPrescribed> medicationPrescribed;
    private String ruralProgDrs;
    private String investigationreport;
    private String drsComment;
    private String isFollowup;
    private String followupType;
    private String followupDate;
    private String referred;
    private String isOld;
    private String createdDate;
    private int doctorId;
    private List<String> investigations;

    public AdviceInputData() {
    }

    public List<String> getInvestigations() {
        return investigations;
    }

    public void setInvestigations(List<String> investigations) {
        this.investigations = investigations;
    }

    public String getInvestigationreport() {
        return investigationreport;
    }

    public void setInvestigationreport(String investigationreport) {
        this.investigationreport = investigationreport;
    }

    protected AdviceInputData(Parcel in) {
        this();
        swpNo = in.readString();
        patientName = in.readString();
        chiefComplaints = in.readString();
        in.readList(system, ArrayList.class.getClassLoader());
        treatmentAdviced = in.readString();
        in.readList(medicationPrescribed, MedicationPrescribed.class.getClassLoader());
        ruralProgDrs = in.readString();
        drsComment = in.readString();
        isFollowup = in.readString();
        followupType = in.readString();
        followupDate = in.readString();
        referred = in.readString();
        isOld = in.readString();
        createdDate = in.readString();
        doctorId = in.readInt();
        in.readList(investigations, ArrayList.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(swpNo);
        dest.writeString(patientName);
        dest.writeString(chiefComplaints);
        dest.writeList(system);
        dest.writeString(treatmentAdviced);
        dest.writeList(medicationPrescribed);
        dest.writeString(ruralProgDrs);
        dest.writeString(drsComment);
        dest.writeString(isFollowup);
        dest.writeString(followupType);
        dest.writeString(followupDate);
        dest.writeString(referred);
        dest.writeString(isOld);
        dest.writeString(createdDate);
        dest.writeInt(doctorId);
        dest.writeList(investigations);
    }




    @Override
    public int describeContents() {
        return 0;
    }


    //Setter and Getter
    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    private String tabName;

    public String getSwpNo() {
        return swpNo;
    }

    public void setSwpNo(String swpNo) {
        this.swpNo = swpNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(String chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    public List<String> getSystem() {
        return system;
    }

    public void setSystem(List<String> system) {
        this.system = system;
    }

    public String getTreatmentAdviced() {
        return treatmentAdviced;
    }

    public void setTreatmentAdviced(String treatmentAdviced) {
        this.treatmentAdviced = treatmentAdviced;
    }

    public List<MedicationPrescribed> getMedicationPrescribed() {
        return medicationPrescribed;
    }

    public void setMedicationPrescribed(List<MedicationPrescribed> medicationPrescribed) {
        this.medicationPrescribed = medicationPrescribed;
    }

    public String getRuralProgDrs() {
        return ruralProgDrs;
    }

    public void setRuralProgDrs(String ruralProgDrs) {
        this.ruralProgDrs = ruralProgDrs;
    }

    public String getDrsComment() {
        return drsComment;
    }

    public void setDrsComment(String drsComment) {
        this.drsComment = drsComment;
    }

    public String getIsFollowup() {
        return isFollowup;
    }

    public void setIsFollowup(String isFollowup) {
        this.isFollowup = isFollowup;
    }

    public String getFollowupType() {
        return followupType;
    }

    public void setFollowupType(String followupType) {
        this.followupType = followupType;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getReferred() {
        return referred;
    }

    public void setReferred(String referred) {
        this.referred = referred;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }



    public List<String> getInvestigationId() {
        return investigations;
    }

    public void setInvestigationIds(List<String> investigationids) {
        this.investigations = investigationids;
    }
}
