package com.hgs.ruralhealth.model.register;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 22-09-2016.
 */
public class RegisterOutputData implements Parcelable {
    private String swpNo;
    private String createdDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer month;
    private Integer year;
    private String ageYear;
    private String gender;
    private String photo;
    private String village;
    private String height;
    private String heightUnit;
    private String weight;
    private String weightUnit;
    private String bloodPressureSystolic;
    private String bloodPressureDiastolic;
    private String bloodPressureone;
    private String bloodPressuretwo;
    private String insertDate;
    private String bloodPressureUnit;
    private String contactNo;
    private String tabNo;
    private String isOld;
    private int doctorId;
    private String complaints;
    private String anc_status;
    private String gplda_g;
    private String gplda_p;
    private String gplda_l;
    private String gplda_d;
    private String gplda_a;
    private String ftnd_f;
    private String ftnd_t;
    private String ftnd_n;
    private String ftnd_d;
    private String lmp;
    private String edd;
    private String bleeding;
    private String leaking;
    private String pain;
    private String others;
    private String uterine_size;
    private String sfhInCm;
    private String pa;
    private String paUt;
    private String paWeeks;
    private String fhs;
    private String fhsPerMin;
    private String rs;
    private String cvs;
    private String cns;
    private String ageMonth;

    public RegisterOutputData() {

    }

    public static final Creator<RegisterOutputData> CREATOR = new Creator<RegisterOutputData>() {
        @Override
        public RegisterOutputData createFromParcel(Parcel in) {
            return new RegisterOutputData(in);
        }

        @Override
        public RegisterOutputData[] newArray(int size) {
            return new RegisterOutputData[size];
        }
    };

    public String getAgeMonth() {
        return ageMonth;
    }

    public void setAgeMonth(String ageMonth) {
        this.ageMonth = ageMonth;
    }


    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    protected RegisterOutputData(Parcel in) {
        swpNo = in.readString();
        createdDate = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        ageYear = in.readString();
        gender = in.readString();
        photo = in.readString();
        village = in.readString();
        height = in.readString();
        heightUnit = in.readString();
        weight = in.readString();
        weightUnit = in.readString();
        bloodPressureSystolic = in.readString();
        bloodPressureDiastolic = in.readString();
        bloodPressureone = in.readString();
        bloodPressuretwo = in.readString();
        insertDate = in.readString();
        bloodPressureUnit = in.readString();
        contactNo = in.readString();
        tabNo = in.readString();
        isOld = in.readString();
        doctorId = in.readInt();
        anc_status = in.readString();
        gplda_g = in.readString();
        gplda_p = in.readString();
        gplda_l = in.readString();
        gplda_d = in.readString();
        gplda_a = in.readString();
        ftnd_f = in.readString();
        ftnd_t = in.readString();
        ftnd_n = in.readString();
        ftnd_d = in.readString();
        lmp = in.readString();
        edd = in.readString();
        bleeding = in.readString();
        leaking = in.readString();
        pain = in.readString();
        others = in.readString();
        uterine_size = in.readString();
        sfhInCm = in.readString();
        pa = in.readString();
        paUt = in.readString();
        paWeeks = in.readString();
        fhs = in.readString();
        fhsPerMin = in.readString();
        rs = in.readString();
        cvs = in.readString();
        cns = in.readString();
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(swpNo);
        dest.writeString(createdDate);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(ageYear);
        dest.writeString(gender);
        dest.writeString(photo);
        dest.writeString(village);
        dest.writeString(height);
        dest.writeString(heightUnit);
        dest.writeString(weight);
        dest.writeString(weightUnit);
        dest.writeString(bloodPressureSystolic);
        dest.writeString(bloodPressureDiastolic);
        dest.writeString(bloodPressureone);
        dest.writeString(bloodPressuretwo);
        dest.writeString(insertDate);
        dest.writeString(bloodPressureUnit);
        dest.writeString(contactNo);
        dest.writeString(tabNo);
        dest.writeString(isOld);
        dest.writeInt(doctorId);
        dest.writeString(anc_status);
        dest.writeString(gplda_g);
        dest.writeString(gplda_p);
        dest.writeString(gplda_l);
        dest.writeString(gplda_d);
        dest.writeString(gplda_a);
        dest.writeString(ftnd_f);
        dest.writeString(ftnd_t);
        dest.writeString(ftnd_n);
        dest.writeString(ftnd_d);
        dest.writeString(lmp);
        dest.writeString(edd);
        dest.writeString(bleeding);
        dest.writeString(leaking);
        dest.writeString(pain);
        dest.writeString(others);
        dest.writeString(uterine_size);
        dest.writeString(sfhInCm);
        dest.writeString(pa);
        dest.writeString(paUt);
        dest.writeString(paWeeks);
        dest.writeString(fhs);
        dest.writeString(fhsPerMin);
        dest.writeString(rs);
        dest.writeString(cvs);
        dest.writeString(cns);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getSwpNo() {
        return swpNo;
    }

    public void setSwpNo(String swpNo) {
        this.swpNo = swpNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return ageYear;
    }

    public void setAge(String age) {
        this.ageYear = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(String bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public String getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(String bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public String getBloodPressureone() {
        return bloodPressureone;
    }

    public void setBloodPressureone(String bloodPressureone) {
        this.bloodPressureone = bloodPressureone;
    }

    public String getBloodPressuretwo() {
        return bloodPressuretwo;
    }

    public void setBloodPressuretwo(String bloodPressuretwo) {
        this.bloodPressuretwo = bloodPressuretwo;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getBloodPressureUnit() {
        return bloodPressureUnit;
    }

    public void setBloodPressureUnit(String bloodPressureUnit) {
        this.bloodPressureUnit = bloodPressureUnit;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getTabNo() {
        return tabNo;
    }

    public void setTabNo(String tabNo) {
        this.tabNo = tabNo;
    }

    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAnc_status() {
        return anc_status;
    }

    public void setAnc_status(String anc_status) {
        this.anc_status = anc_status;
    }

    public String getGplda_g() {
        return gplda_g;
    }

    public void setGplda_g(String gplda_g) {
        this.gplda_g = gplda_g;
    }

    public String getGplda_p() {
        return gplda_p;
    }

    public void setGplda_p(String gplda_p) {
        this.gplda_p = gplda_p;
    }

    public String getGplda_l() {
        return gplda_l;
    }

    public void setGplda_l(String gplda_l) {
        this.gplda_l = gplda_l;
    }

    public String getGplda_d() {
        return gplda_d;
    }

    public void setGplda_d(String gplda_d) {
        this.gplda_d = gplda_d;
    }

    public String getGplda_a() {
        return gplda_a;
    }

    public void setGplda_a(String gplda_a) {
        this.gplda_a = gplda_a;
    }

    public String getFtnd_f() {
        return ftnd_f;
    }

    public void setFtnd_f(String ftnd_f) {
        this.ftnd_f = ftnd_f;
    }

    public String getFtnd_t() {
        return ftnd_t;
    }

    public void setFtnd_t(String ftnd_t) {
        this.ftnd_t = ftnd_t;
    }

    public String getFtnd_n() {
        return ftnd_n;
    }

    public void setFtnd_n(String ftnd_n) {
        this.ftnd_n = ftnd_n;
    }

    public String getFtnd_d() {
        return ftnd_d;
    }

    public void setFtnd_d(String ftnd_d) {
        this.ftnd_d = ftnd_d;
    }

    public String getLmp() {
        return lmp;
    }

    public void setLmp(String lmp) {
        this.lmp = lmp;
    }

    public String getEdd() {
        return edd;
    }

    public void setEdd(String edd) {
        this.edd = edd;
    }

    public String getBleeding() {
        return bleeding;
    }

    public void setBleeding(String bleeding) {
        this.bleeding = bleeding;
    }

    public String getLeaking() {
        return leaking;
    }

    public void setLeaking(String leaking) {
        this.leaking = leaking;
    }

    public String getPain() {
        return pain;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getUterine_size() {
        return uterine_size;
    }

    public void setUterine_size(String uterine_size) {
        this.uterine_size = uterine_size;
    }

    public String getSfhInCm() {
        return sfhInCm;
    }

    public void setSfhInCm(String sfhInCm) {
        this.sfhInCm = sfhInCm;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getPaUt() {
        return paUt;
    }

    public void setPaUt(String paUt) {
        this.paUt = paUt;
    }

    public String getPaWeeks() {
        return paWeeks;
    }

    public void setPaWeeks(String paWeeks) {
        this.paWeeks = paWeeks;
    }

    public String getFhs() {
        return fhs;
    }

    public void setFhs(String fhs) {
        this.fhs = fhs;
    }

    public String getFhsPerMin() {
        return fhsPerMin;
    }

    public void setFhsPerMin(String fhsPerMin) {
        this.fhsPerMin = fhsPerMin;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getCvs() {
        return cvs;
    }

    public void setCvs(String cvs) {
        this.cvs = cvs;
    }

    public String getCns() {
        return cns;
    }

    public void setCns(String cns) {
        this.cns = cns;
    }

    public static Creator<RegisterOutputData> getCREATOR() {
        return CREATOR;
    }
}
