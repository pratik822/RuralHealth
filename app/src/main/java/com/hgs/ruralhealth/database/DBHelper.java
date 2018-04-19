package com.hgs.ruralhealth.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.fragments.ActivityData;
import com.hgs.ruralhealth.model.Device.DeviceData;
import com.hgs.ruralhealth.model.MSW_Activity.MswAcitivyInputData;
import com.hgs.ruralhealth.model.MSW_Distribution.Items;
import com.hgs.ruralhealth.model.MSW_Distribution.Msw_Distribution_InputData;
import com.hgs.ruralhealth.model.db.AdviceInputData;
import com.hgs.ruralhealth.model.db.MedicationPrescribed;
import com.hgs.ruralhealth.model.db.SpinnerData;
import com.hgs.ruralhealth.model.masterdata.FrequencieOutputData;
import com.hgs.ruralhealth.model.masterdata.InvestigationData;
import com.hgs.ruralhealth.model.masterdata.MedicineOutputData;
import com.hgs.ruralhealth.model.masterdata.ProductsOutputData;
import com.hgs.ruralhealth.model.masterdata.SymptompsOutputData;
import com.hgs.ruralhealth.model.masterdata.SystemOutputData;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.model.ophthal.OphthalInputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysioSessionInputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapistInputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.model.user.UserData;
import com.hgs.ruralhealth.utilities.Utililty;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pratikb on 12-09-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ruraldb";
    private Context context;
    Cursor cursor;
    List<String> investigationList;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createDataBase(db);
        } catch (Exception e) {
            Log.e("SQLiteHelper", e.getMessage());
        }
    }

    public void createDataBase(SQLiteDatabase db) {
        String wholeSql = "";
        try {
            Resources res = this.context.getResources();
            InputStream in_s = res.openRawResource(R.raw.create_ruralhealth_db);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            wholeSql = new String(b);
            String[] splitSql = wholeSql.split(";");

            for (String createSql : splitSql) {
                db.execSQL(createSql + ";");
                System.out.println("mysql" + createSql);
            }

        } catch (Exception e) {
            Log.e("SQLiteHelper", "createDataBase: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.deleteDatabase(DATABASE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADVICE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYSTEM);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DURATION);

    }

    /**
     * Insert patients registration data
     *
     * @param registerData
     */
    public void insertUser(List<RegisterOutputData> registerData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (RegisterOutputData registerOutputData : registerData) {
            values.put(DBConstant.SWP_NO, registerOutputData.getSwpNo());
            values.put(DBConstant.INSERT_DATE, registerOutputData.getInsertDate());
            values.put(DBConstant.FIRST_NAME, registerOutputData.getFirstName());
            values.put(DBConstant.LAST_NAME, registerOutputData.getLastName());
            values.put("midel_name", registerOutputData.getMiddleName());
            values.put("month", registerOutputData.getMonth());
            values.put("year", registerOutputData.getYear());
            values.put(DBConstant.AGE, registerOutputData.getAge());
            values.put(DBConstant.GENDER, registerOutputData.getGender());
            values.put(DBConstant.PHOTO, registerOutputData.getPhoto());
            values.put(DBConstant.VILLAGE, registerOutputData.getVillage());
            values.put(DBConstant.HEIGHT, registerOutputData.getHeight());
            values.put(DBConstant.HEIGHT_UNIT, registerOutputData.getHeightUnit());
            values.put(DBConstant.WEIGHT, registerOutputData.getWeight());
            values.put(DBConstant.WEIGHT_UNIT, registerOutputData.getWeightUnit());
            values.put(DBConstant.BLOOD_PRESSUREONE, registerOutputData.getBloodPressureSystolic());
            values.put(DBConstant.BLOOD_PRESSURETWO, registerOutputData.getBloodPressureDiastolic());
            values.put(DBConstant.BLOOD_PRESSURE_UNIT, registerOutputData.getBloodPressureUnit());
            values.put(DBConstant.CONTACT_NO, registerOutputData.getContactNo());

            values.put(DBConstant.ANC_STATUS, registerOutputData.getAnc_status());
            values.put(DBConstant.G, registerOutputData.getGplda_g());
            values.put(DBConstant.P, registerOutputData.getGplda_p());
            values.put(DBConstant.L, registerOutputData.getGplda_l());
            values.put(DBConstant.DS, registerOutputData.getGplda_d());
            values.put(DBConstant.A, registerOutputData.getGplda_a());
            values.put(DBConstant.F, registerOutputData.getFtnd_f());
            values.put(DBConstant.T, registerOutputData.getFtnd_t());
            values.put(DBConstant.N, registerOutputData.getFtnd_n());
            values.put(DBConstant.D, registerOutputData.getFtnd_d());
            values.put(DBConstant.LMP, registerOutputData.getLmp());
            values.put(DBConstant.EDD, registerOutputData.getEdd());
            values.put(DBConstant.BLEEDING, registerOutputData.getBleeding());
            values.put(DBConstant.LEAKING, registerOutputData.getLeaking());
            values.put(DBConstant.PAIN, registerOutputData.getPain());
            values.put("paUt",registerOutputData.getPaUt());
            values.put("fhsPerMin",registerOutputData.getFhsPerMin());
            values.put(DBConstant.OTHER, registerOutputData.getOthers());
            values.put(DBConstant.UTERINE_SIZE, registerOutputData.getUterine_size());
            values.put(DBConstant.SFH, registerOutputData.getSfhInCm());
            values.put(DBConstant.PA, registerOutputData.getPa());
            values.put(DBConstant.PA_WEEKS, registerOutputData.getPaWeeks());
            values.put(DBConstant.FHS, registerOutputData.getFhs());
            values.put(DBConstant.RS, registerOutputData.getRs());
            values.put(DBConstant.CVS, registerOutputData.getCvs());
            values.put(DBConstant.CNS, registerOutputData.getCns());

            values.put(DBConstant.TAB_NO, registerOutputData.getTabNo());
            values.put(DBConstant.IS_NEW, registerOutputData.getIsOld());
            values.put(DBConstant.DOCTORS_ID, registerOutputData.getDoctorId());
            values.put("agemonth", registerOutputData.getAgeMonth());
            values.put("complaint",registerOutputData.getComplaints());
            db.insert(DBConstant.TABLE_NAME, null, values);
        }

        db.close(); // Closing database connection
    }

    /**
     * Insert patient followup data
     *
     * @param followupOutputData
     */
    public void insertfollowupUser(List<RegisterOutputData> followupOutputData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (RegisterOutputData registerOutputData : followupOutputData) {
            values.put(DBConstant.SWP_NO, registerOutputData.getSwpNo());
            values.put(DBConstant.INSERT_DATE, registerOutputData.getCreatedDate());
            values.put(DBConstant.FIRST_NAME, registerOutputData.getFirstName());
            values.put(DBConstant.LAST_NAME, registerOutputData.getLastName());
            values.put("midel_name", registerOutputData.getMiddleName());
            values.put("month", registerOutputData.getMonth());
            values.put("year", registerOutputData.getYear());
            values.put(DBConstant.AGE, registerOutputData.getAge());
            values.put(DBConstant.GENDER, registerOutputData.getGender());
            values.put(DBConstant.PHOTO, registerOutputData.getPhoto());
            values.put(DBConstant.VILLAGE, registerOutputData.getVillage());
            values.put(DBConstant.HEIGHT, registerOutputData.getHeight());
            values.put(DBConstant.HEIGHT_UNIT, registerOutputData.getHeightUnit());
            values.put(DBConstant.WEIGHT, registerOutputData.getWeight());
            values.put(DBConstant.WEIGHT_UNIT, registerOutputData.getWeightUnit());
            values.put(DBConstant.BLOOD_PRESSUREONE, registerOutputData.getBloodPressureSystolic());
            values.put(DBConstant.BLOOD_PRESSURETWO, registerOutputData.getBloodPressureDiastolic());
            values.put(DBConstant.BLOOD_PRESSURE_UNIT, registerOutputData.getBloodPressureUnit());
            values.put(DBConstant.CONTACT_NO, registerOutputData.getContactNo());
            values.put("paUt",registerOutputData.getPaUt());
            values.put("fhsPerMin",registerOutputData.getFhsPerMin());
            values.put(DBConstant.ANC_STATUS, registerOutputData.getAnc_status());
            values.put(DBConstant.G, registerOutputData.getGplda_g());
            values.put(DBConstant.P, registerOutputData.getGplda_p());
            values.put(DBConstant.L, registerOutputData.getGplda_l());
            values.put(DBConstant.DS, registerOutputData.getGplda_d());
            values.put(DBConstant.A, registerOutputData.getGplda_a());
            values.put(DBConstant.F, registerOutputData.getFtnd_f());
            values.put(DBConstant.T, registerOutputData.getFtnd_t());
            values.put(DBConstant.N, registerOutputData.getFtnd_n());
            values.put(DBConstant.D, registerOutputData.getFtnd_d());
            values.put(DBConstant.LMP, registerOutputData.getLmp());
            values.put(DBConstant.EDD, registerOutputData.getEdd());
            values.put(DBConstant.BLEEDING, registerOutputData.getBleeding());
            values.put(DBConstant.LEAKING, registerOutputData.getLeaking());
            values.put(DBConstant.PAIN, registerOutputData.getPain());
            values.put(DBConstant.OTHER, registerOutputData.getOthers());
            values.put(DBConstant.UTERINE_SIZE, registerOutputData.getUterine_size());
            values.put(DBConstant.SFH, registerOutputData.getSfhInCm());
            values.put(DBConstant.PA, registerOutputData.getPa());
            values.put(DBConstant.PA_WEEKS, registerOutputData.getPaWeeks());
            values.put(DBConstant.FHS, registerOutputData.getFhs());
            values.put(DBConstant.RS, registerOutputData.getRs());
            values.put(DBConstant.CVS, registerOutputData.getCvs());
            values.put(DBConstant.CNS, registerOutputData.getCns());

            values.put(DBConstant.TAB_NO, registerOutputData.getTabNo());
            values.put(DBConstant.IS_NEW, registerOutputData.getIsOld());
            values.put(DBConstant.DOCTORS_ID, registerOutputData.getDoctorId());
            values.put("agemonth", registerOutputData.getAgeMonth());
            values.put("complaint",registerOutputData.getComplaints());
            Log.i("print", values.toString());
            db.insert(DBConstant.TABLE_FOLLOWUP, null, values);
        }

        db.close(); // Closing database connection
    }


    public RegisterOutputData getFollowUpSWPData(String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        RegisterOutputData regData = new RegisterOutputData();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_FOLLOWUP + " WHERE "
                + DBConstant.SWP_NO + "='" + swp + "' " + "AND " + DBConstant.INSERT_DATE + "='" + Utililty.getCurDate() + "'", null);

        if (cursor.moveToFirst()) {
            do {
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setLastName(cursor.getString(3));
                regData.setAge(cursor.getString(4));
                regData.setGender(cursor.getString(5));
                regData.setPhoto(cursor.getString(6));
                regData.setVillage(cursor.getString(7));
                regData.setHeight(cursor.getString(8));
                regData.setWeight(cursor.getString(10));
                regData.setBloodPressureSystolic(cursor.getString(12));
                regData.setBloodPressureDiastolic(cursor.getString(13));
                regData.setContactNo(cursor.getString(15));
            } while (cursor.moveToNext());
        }

        return regData;
    }


    public RegisterOutputData getRegDataByUserandSwpNo(String name, String mid,String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        RegisterOutputData regData = new RegisterOutputData();
        if (!swp.isEmpty()) {
          /*  cursor = db.query(DBConstant.TABLE_NAME, new String[]{
                            DBConstant.SWP_NO, DBConstant.INSERT_DATE, DBConstant.FIRST_NAME, DBConstant.LAST_NAME, DBConstant.AGE, DBConstant.GENDER, DBConstant.PHOTO, DBConstant.VILLAGE, DBConstant.HEIGHT, DBConstant.WEIGHT, DBConstant.BLOOD_PRESSUREONE, DBConstant.BLOOD_PRESSURETWO, DBConstant.CONTACT_NO}
                    , DBConstant.SWP_NO + "=?",
                    new String[]{String.valueOf(swp)}, null, null, null, null);*/
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_NAME + " WHERE " + DBConstant.SWP_NO + "='" + swp + "'", null);
        } else if (!name.isEmpty()) {
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_NAME + " WHERE " + DBConstant.FIRST_NAME + "='" + name + "'", null);
          /*  cursor = db.query(DBConstant.TABLE_NAME, new String[]{
                            DBConstant.SWP_NO, DBConstant.INSERT_DATE, DBConstant.FIRST_NAME, DBConstant.LAST_NAME, DBConstant.AGE, DBConstant.GENDER, DBConstant.PHOTO, DBConstant.VILLAGE, DBConstant.HEIGHT, DBConstant.WEIGHT, DBConstant.BLOOD_PRESSUREONE, DBConstant.BLOOD_PRESSURETWO, DBConstant.CONTACT_NO}
                    , DBConstant.FIRST_NAME + "=?",
                    new String[]{String.valueOf(name)}, null, null, null, null);*/
        }

        if (cursor.moveToFirst()) {
            do {
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setMiddleName(cursor.getString(3));
                regData.setLastName(cursor.getString(4));
                regData.setMonth(cursor.getInt(5));
                regData.setYear(cursor.getInt(6));
                regData.setAge(cursor.getString(7));
                regData.setGender(cursor.getString(8));
                regData.setPhoto(cursor.getString(9));
                regData.setVillage(cursor.getString(10));
                regData.setHeight(cursor.getString(11));
                regData.setHeightUnit(cursor.getString(12));
                regData.setWeight(cursor.getString(13));
                regData.setWeightUnit(cursor.getString(14));
                regData.setBloodPressureSystolic(cursor.getString(15));
                regData.setBloodPressureDiastolic(cursor.getString(16));
                regData.setBloodPressureUnit(cursor.getString(17));
                regData.setContactNo(cursor.getString(18));
                regData.setAnc_status(cursor.getString(19));
                regData.setGplda_g(cursor.getString(20));
                regData.setGplda_p(cursor.getString(21));
                regData.setGplda_l(cursor.getString(22));
                regData.setGplda_d(cursor.getString(23));
                regData.setGplda_a(cursor.getString(24));
                regData.setFtnd_f(cursor.getString(25));
                regData.setFtnd_t(cursor.getString(26));
                regData.setFtnd_n(cursor.getString(27));
                regData.setFtnd_d(cursor.getString(28));
                regData.setLmp(cursor.getString(29));
                regData.setEdd(cursor.getString(30));
                regData.setBleeding(cursor.getString(31));
                regData.setLeaking(cursor.getString(32));
                regData.setPain(cursor.getString(33));
                regData.setOthers(cursor.getString(34));
                regData.setUterine_size(cursor.getString(35));
                regData.setSfhInCm(cursor.getString(36));
                regData.setPa(cursor.getString(37));
                regData.setPaUt(cursor.getString(38));
                regData.setPaWeeks(cursor.getString(39));
                regData.setFhs(cursor.getString(40));
                regData.setFhsPerMin(cursor.getString(41));
                regData.setRs(cursor.getString(42));
                regData.setCvs(cursor.getString(43));
                regData.setCns(cursor.getString(44));
                regData.setTabNo(cursor.getString(45));
                regData.setIsOld(cursor.getString(46));
                regData.setDoctorId(cursor.getInt(47));
                regData.setAgeMonth(cursor.getString(48));
                regData.setComplaints(cursor.getString(49));
                Log.d("rrrrrrrrrr",cursor.getString(48));
            } while (cursor.moveToNext());
        }

        return regData;
    }

    public List<RegisterOutputData> getAllPatientNameSWPNo() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RegisterOutputData> registerOutputDataList = new ArrayList<>();
        cursor = db.query(DBConstant.TABLE_NAME, new String[]{
                        DBConstant.SWP_NO, DBConstant.FIRST_NAME, DBConstant.LAST_NAME,"agemonth"}
                , null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                RegisterOutputData data = new RegisterOutputData();
                data.setSwpNo(cursor.getString(0));
                data.setFirstName(cursor.getString(1));
                data.setLastName(cursor.getString(2));
                data.setAgeMonth(cursor.getString(3));
                registerOutputDataList.add(data);

            } while (cursor.moveToNext());
        }
        return registerOutputDataList;
    }

    public List<RegisterOutputData> getallRegisterdUsersize(String tab) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RegisterOutputData> registerOutputDataList = new ArrayList<>();
        Log.d("myq", "SELECT * FROM " + DBConstant.TABLE_NAME + " WHERE tab_no =" + tab);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_NAME, null);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        if (cursor.moveToFirst()) {
            do {
                RegisterOutputData regData = new RegisterOutputData();
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setMiddleName(cursor.getString(3));
                regData.setLastName(cursor.getString(4));
                regData.setMonth(cursor.getInt(5));
                regData.setYear(cursor.getInt(6));
                regData.setAge(cursor.getString(7));
                regData.setGender(cursor.getString(8));
                regData.setPhoto(cursor.getString(9));
                regData.setVillage(cursor.getString(10));
                regData.setHeight(cursor.getString(11));
                regData.setHeightUnit(cursor.getString(12));
                regData.setWeight(cursor.getString(13));
                regData.setWeightUnit(cursor.getString(14));
                regData.setBloodPressureSystolic(cursor.getString(15));
                regData.setBloodPressureDiastolic(cursor.getString(16));
                regData.setBloodPressureUnit(cursor.getString(17));
                regData.setContactNo(cursor.getString(18));
                regData.setAnc_status(cursor.getString(19));
                regData.setGplda_g(cursor.getString(20));
                regData.setGplda_p(cursor.getString(21));
                regData.setGplda_l(cursor.getString(22));
                regData.setGplda_d(cursor.getString(23));
                regData.setGplda_a(cursor.getString(24));
                regData.setFtnd_f(cursor.getString(25));
                regData.setFtnd_t(cursor.getString(26));
                regData.setFtnd_n(cursor.getString(27));
                regData.setFtnd_d(cursor.getString(28));
                regData.setLmp(cursor.getString(29));
                regData.setEdd(cursor.getString(30));
                regData.setBleeding(cursor.getString(31));
                regData.setLeaking(cursor.getString(32));
                regData.setPain(cursor.getString(33));
                regData.setOthers(cursor.getString(34));
                regData.setUterine_size(cursor.getString(35));
                regData.setSfhInCm(cursor.getString(36));
                regData.setPa(cursor.getString(37));
                regData.setPaUt(cursor.getString(38));
                regData.setPaWeeks(cursor.getString(39));
                regData.setFhs(cursor.getString(40));
                regData.setFhsPerMin(cursor.getString(41));
                regData.setRs(cursor.getString(42));
                regData.setCvs(cursor.getString(43));
                regData.setCns(cursor.getString(44));
                regData.setTabNo(cursor.getString(45));
                regData.setIsOld(cursor.getString(46));
                regData.setDoctorId(cursor.getInt(47));
                regData.setAgeMonth(cursor.getString(48));
                regData.setComplaints(cursor.getString(49));
                registerOutputDataList.add(regData);
            } while (cursor.moveToNext());
        }
        return registerOutputDataList;
    }

    public List<RegisterOutputData> getallRegisterdUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RegisterOutputData> registerOutputDataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                RegisterOutputData regData = new RegisterOutputData();
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setMiddleName(cursor.getString(3));
                regData.setLastName(cursor.getString(4));
                regData.setMonth(cursor.getInt(5));
                regData.setYear(cursor.getInt(6));
                regData.setAge(cursor.getString(7));
                regData.setGender(cursor.getString(8));
                regData.setPhoto(cursor.getString(9));
                regData.setVillage(cursor.getString(10));
                regData.setHeight(cursor.getString(11));
                regData.setHeightUnit(cursor.getString(12));
                regData.setWeight(cursor.getString(13));
                regData.setWeightUnit(cursor.getString(14));
                regData.setBloodPressureSystolic(cursor.getString(15));
                regData.setBloodPressureDiastolic(cursor.getString(16));
                regData.setBloodPressureUnit(cursor.getString(17));
                regData.setContactNo(cursor.getString(18));
                regData.setAnc_status(cursor.getString(19));
                regData.setGplda_g(cursor.getString(20));
                regData.setGplda_p(cursor.getString(21));
                regData.setGplda_l(cursor.getString(22));
                regData.setGplda_d(cursor.getString(23));
                regData.setGplda_a(cursor.getString(24));
                regData.setFtnd_f(cursor.getString(25));
                regData.setFtnd_t(cursor.getString(26));
                regData.setFtnd_n(cursor.getString(27));
                regData.setFtnd_d(cursor.getString(28));
                regData.setLmp(cursor.getString(29));
                regData.setEdd(cursor.getString(30));
                regData.setBleeding(cursor.getString(31));
                regData.setLeaking(cursor.getString(32));
                regData.setPain(cursor.getString(33));
                regData.setOthers(cursor.getString(34));
                regData.setUterine_size(cursor.getString(35));
                regData.setSfhInCm(cursor.getString(36));
                regData.setPa(cursor.getString(37));
                regData.setPaUt(cursor.getString(38));
                regData.setPaWeeks(cursor.getString(39));
                regData.setFhs(cursor.getString(40));
                regData.setFhsPerMin(cursor.getString(41));
                regData.setRs(cursor.getString(42));
                regData.setCvs(cursor.getString(43));
                regData.setCns(cursor.getString(44));
                regData.setTabNo(cursor.getString(45));
                regData.setIsOld(cursor.getString(46));
                regData.setDoctorId(cursor.getInt(47));
                regData.setAgeMonth(cursor.getString(48));
                regData.setComplaints(cursor.getString(49));
                registerOutputDataList.add(regData);
            } while (cursor.moveToNext());
        }
        return registerOutputDataList;
    }


    public void updateRegIsold(String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_NAME + " SET " + DBConstant.IS_NEW + "=" + "'Y'" + " WHERE " + DBConstant.SWP_NO + "=" + "'" + swp + "'");
    }

    public List<RegisterOutputData> getRegByIsoldN() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RegisterOutputData> registerOutputDataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_NAME + " WHERE " + DBConstant.IS_NEW + " ='N'", null);
        if (cursor.moveToFirst()) {
            do {
                RegisterOutputData regData = new RegisterOutputData();
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setMiddleName(cursor.getString(3));
                regData.setLastName(cursor.getString(4));
                regData.setMonth(cursor.getInt(5));
                regData.setYear(cursor.getInt(6));
                regData.setAge(cursor.getString(7));
                regData.setGender(cursor.getString(8));
                regData.setPhoto(cursor.getString(9));
                regData.setVillage(cursor.getString(10));
                regData.setHeight(cursor.getString(11));
                regData.setHeightUnit(cursor.getString(12));
                regData.setWeight(cursor.getString(13));
                regData.setWeightUnit(cursor.getString(14));
                regData.setBloodPressureSystolic(cursor.getString(15));
                regData.setBloodPressureDiastolic(cursor.getString(16));
                regData.setBloodPressureUnit(cursor.getString(17));
                regData.setContactNo(cursor.getString(18));
                regData.setAnc_status(cursor.getString(19));
                regData.setGplda_g(cursor.getString(20));
                regData.setGplda_p(cursor.getString(21));
                regData.setGplda_l(cursor.getString(22));
                regData.setGplda_d(cursor.getString(23));
                regData.setGplda_a(cursor.getString(24));
                regData.setFtnd_f(cursor.getString(25));
                regData.setFtnd_t(cursor.getString(26));
                regData.setFtnd_n(cursor.getString(27));
                regData.setFtnd_d(cursor.getString(28));
                regData.setLmp(cursor.getString(29));
                regData.setEdd(cursor.getString(30));
                regData.setBleeding(cursor.getString(31));
                regData.setLeaking(cursor.getString(32));
                regData.setPain(cursor.getString(33));
                regData.setOthers(cursor.getString(34));
                regData.setUterine_size(cursor.getString(35));
                regData.setSfhInCm(cursor.getString(36));
                regData.setPa(cursor.getString(37));
                regData.setPaUt(cursor.getString(38));
                regData.setPaWeeks(cursor.getString(39));
                regData.setFhs(cursor.getString(40));
                regData.setFhsPerMin(cursor.getString(41));
                regData.setRs(cursor.getString(42));
                regData.setCvs(cursor.getString(43));
                regData.setCns(cursor.getString(44));
                regData.setTabNo(cursor.getString(45));
                regData.setIsOld(cursor.getString(46));
                regData.setDoctorId(cursor.getInt(47));
                regData.setAgeMonth(cursor.getString(48));
                regData.setComplaints(cursor.getString(49));
                registerOutputDataList.add(regData);


            } while (cursor.moveToNext());
        }
        return registerOutputDataList;
    }

    public List<RegisterOutputData> getallFollowupUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RegisterOutputData> registerOutputDataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_FOLLOWUP, null);

        if (cursor.moveToFirst()) {
            do {
                RegisterOutputData regData = new RegisterOutputData();
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setMiddleName(cursor.getString(3));
                regData.setLastName(cursor.getString(4));
                regData.setMonth(cursor.getInt(5));
                regData.setYear(cursor.getInt(6));
                regData.setAge(cursor.getString(7));
                regData.setGender(cursor.getString(8));
                regData.setPhoto(cursor.getString(9));
                regData.setVillage(cursor.getString(10));
                regData.setHeight(cursor.getString(11));
                regData.setHeightUnit(cursor.getString(12));
                regData.setWeight(cursor.getString(13));
                regData.setWeightUnit(cursor.getString(14));
                regData.setBloodPressureSystolic(cursor.getString(15));
                regData.setBloodPressureDiastolic(cursor.getString(16));
                regData.setBloodPressureUnit(cursor.getString(17));
                regData.setContactNo(cursor.getString(18));
                regData.setAnc_status(cursor.getString(19));
                regData.setGplda_g(cursor.getString(20));
                regData.setGplda_p(cursor.getString(21));
                regData.setGplda_l(cursor.getString(22));
                regData.setGplda_d(cursor.getString(23));
                regData.setGplda_a(cursor.getString(24));
                regData.setFtnd_f(cursor.getString(25));
                regData.setFtnd_t(cursor.getString(26));
                regData.setFtnd_n(cursor.getString(27));
                regData.setFtnd_d(cursor.getString(28));
                regData.setLmp(cursor.getString(29));
                regData.setEdd(cursor.getString(30));
                regData.setBleeding(cursor.getString(31));
                regData.setLeaking(cursor.getString(32));
                regData.setPain(cursor.getString(33));
                regData.setOthers(cursor.getString(34));
                regData.setUterine_size(cursor.getString(35));
                regData.setSfhInCm(cursor.getString(36));
                regData.setPa(cursor.getString(37));
                regData.setPaUt(cursor.getString(38));
                regData.setPaWeeks(cursor.getString(39));
                regData.setFhs(cursor.getString(40));
                regData.setFhsPerMin(cursor.getString(41));
                regData.setRs(cursor.getString(42));
                regData.setCvs(cursor.getString(43));
                regData.setCns(cursor.getString(44));
                regData.setTabNo(cursor.getString(45));
                regData.setIsOld(cursor.getString(46));
                regData.setDoctorId(cursor.getInt(47));
                regData.setAgeMonth(cursor.getString(48));
                regData.setComplaints(cursor.getString(49));
                registerOutputDataList.add(regData);
            } while (cursor.moveToNext());
        }
        return registerOutputDataList;
    }

    public List<RegisterOutputData> getFollowupByIsoldN() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RegisterOutputData> registerOutputDataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_FOLLOWUP + " WHERE " + DBConstant.IS_NEW + " ='N'", null);
        if (cursor.moveToFirst()) {
            do {
                RegisterOutputData regData = new RegisterOutputData();
                regData.setSwpNo(cursor.getString(0));
                regData.setCreatedDate(cursor.getString(1));
                regData.setFirstName(cursor.getString(2));
                regData.setMiddleName(cursor.getString(3));
                regData.setLastName(cursor.getString(4));
                regData.setMonth(cursor.getInt(5));
                regData.setYear(cursor.getInt(6));
                regData.setAge(cursor.getString(7));
                regData.setGender(cursor.getString(8));
                regData.setPhoto(cursor.getString(9));
                regData.setVillage(cursor.getString(10));
                regData.setHeight(cursor.getString(11));
                regData.setHeightUnit(cursor.getString(12));
                regData.setWeight(cursor.getString(13));
                regData.setWeightUnit(cursor.getString(14));
                regData.setBloodPressureSystolic(cursor.getString(15));
                regData.setBloodPressureDiastolic(cursor.getString(16));
                regData.setBloodPressureUnit(cursor.getString(17));
                regData.setContactNo(cursor.getString(18));
                regData.setAnc_status(cursor.getString(19));
                regData.setGplda_g(cursor.getString(20));
                regData.setGplda_p(cursor.getString(21));
                regData.setGplda_l(cursor.getString(22));
                regData.setGplda_d(cursor.getString(23));
                regData.setGplda_a(cursor.getString(24));
                regData.setFtnd_f(cursor.getString(25));
                regData.setFtnd_t(cursor.getString(26));
                regData.setFtnd_n(cursor.getString(27));
                regData.setFtnd_d(cursor.getString(28));
                regData.setLmp(cursor.getString(29));
                regData.setEdd(cursor.getString(30));
                regData.setBleeding(cursor.getString(31));
                regData.setLeaking(cursor.getString(32));
                regData.setPain(cursor.getString(33));
                regData.setOthers(cursor.getString(34));
                regData.setUterine_size(cursor.getString(35));
                regData.setSfhInCm(cursor.getString(36));
                regData.setPa(cursor.getString(37));
                regData.setPaUt(cursor.getString(38));
                regData.setPaWeeks(cursor.getString(39));
                regData.setFhs(cursor.getString(40));
                regData.setFhsPerMin(cursor.getString(41));
                regData.setRs(cursor.getString(42));
                regData.setCvs(cursor.getString(43));
                regData.setCns(cursor.getString(44));
                regData.setTabNo(cursor.getString(45));
                regData.setIsOld(cursor.getString(46));
                regData.setDoctorId(cursor.getInt(47));
                regData.setAgeMonth(cursor.getString(48));
                regData.setComplaints(cursor.getString(49));
                registerOutputDataList.add(regData);
            } while (cursor.moveToNext());
        }
        Log.d("foloooow", new Gson().toJson(registerOutputDataList));
        return registerOutputDataList;
    }

    public List<Items> getallMSWProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> itemsList = new ArrayList<>();
        Cursor itemsCursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_MSW_FOOD, null);
        ///Log.d("aaaaa","SELECT * FROM " + DBConstant.TABLE_MSW_FOOD + " WHERE " + DBConstant.MSW_ITEM_NAME+" ="+ name);
        if (itemsCursor.moveToFirst()) {
            do {
                Items items = new Items();
                items.setCreatedDate(itemsCursor.getString(0));
                items.setmSWName(itemsCursor.getString(1));
                items.setItemName(itemsCursor.getString(2));
                items.setQty(itemsCursor.getString(3));
                items.setUnit(itemsCursor.getString(4));
                itemsList.add(items);

            } while (itemsCursor.moveToNext());
        }
        return itemsList;

    }

    //MSW DistribituinData
    public String UpdateMswDistribution(List<Msw_Distribution_InputData> mswList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_MSW_DISTRIBUTION);
        //Insert Data into MSW DISTRIBUTION Table

        for (int i = 0; i < mswList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MSW_INSERT_DATE, mswList.get(i).getCreatedDate());
            values.put(DBConstant.MSW_NAME, mswList.get(i).getMswName());
            values.put(DBConstant.MSW_VILLAGES, mswList.get(i).getVillage());
            values.put(DBConstant.MSW_ISOLD, mswList.get(i).getIsOld());
            values.put(DBConstant.MSW_TAB_NAME, mswList.get(i).getTabName());
            values.put(DBConstant.DOCTORS_ID, mswList.get(i).getDoctorId());
            db.insert(DBConstant.TABLE_MSW_DISTRIBUTION, null, values);

            for (Items data : mswList.get(i).getItems()) {
                db.execSQL("UPDATE " + DBConstant.TABLE_MSW_FOOD + " SET " + DBConstant.MSW_QTY + "=" + data.getQty() + " WHERE " + DBConstant.MSW_ITEM_NAME + "=" + "'" + data.getItemName() + "'");
            }

        }


        db.close();
        return "SUCCESS";
    }

    public boolean CheckIsDataAlreadyInDBorNot(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + DBConstant.TABLE_MSW_FOOD + " where " + DBConstant.MSW_ITEM_NAME + " = " + "'" + name + "'";
        Log.d("myqueary", Query);
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<MswAcitivyInputData> getMSWByIsoldN() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MswAcitivyInputData> mswAcitivyOutputsList = new ArrayList<>();
        ArrayList<String> activityList;
        List<MswAcitivyInputData> registerOutputDataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_MSW_ACTIVITY + " WHERE " + DBConstant.ISOLD + " ='N'", null);
        if (cursor.moveToFirst()) {
            activityList = new ArrayList<>();

            do {
                MswAcitivyInputData mswAcitivyOutput = new MswAcitivyInputData();
                mswAcitivyOutput.setCreatedDate(cursor.getString(0));
                mswAcitivyOutput.setMswName(cursor.getString(1));
                mswAcitivyOutput.setVillage(cursor.getString(2));
                mswAcitivyOutput.setOtherOption(cursor.getString(3));
                mswAcitivyOutput.setChildrenCount(cursor.getString(4));
                mswAcitivyOutput.setIsTeacherPresent(cursor.getString(5));
                mswAcitivyOutput.setIsWaterFilterPresent(cursor.getString(6));
                mswAcitivyOutput.setIsHandWashPresent(cursor.getString(7));
                mswAcitivyOutput.setSupportReq(cursor.getString(8));
                mswAcitivyOutput.setRemark(cursor.getString(9));
                mswAcitivyOutput.setHouseVisit(cursor.getString(10));
                mswAcitivyOutput.setIsOld(cursor.getString(11));
                mswAcitivyOutput.setTabNo(cursor.getString(12));
                mswAcitivyOutput.setDoctorId(cursor.getInt(13));


                Cursor activityCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_activity + " " + " WHERE " + DBConstant.MSW_NAME + "='" + mswAcitivyOutput.getMswName() + "' " + "AND insert_date='" + mswAcitivyOutput.getCreatedDate() + "'", null);

                System.out.println("query" + "SELECT * FROM  " + DBConstant.TABLE_activity + " " + " WHERE " + DBConstant.MSW_NAME + "='" + mswAcitivyOutput.getMswName() + "' " + "AND insert_date='" + mswAcitivyOutput.getCreatedDate() + "'");


                if (activityCursor.moveToFirst()) {
                    activityList = new ArrayList<>();
                    do {

                        activityList.add(activityCursor.getString(3));
                    } while (activityCursor.moveToNext());
                    mswAcitivyOutput.setActivities(activityList);


                }
                mswAcitivyOutputsList.add(mswAcitivyOutput);
            } while (cursor.moveToNext());

        }
        return mswAcitivyOutputsList;
    }


    public List<Msw_Distribution_InputData> getMSWDistributionByIsoldN() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Msw_Distribution_InputData> mswAcitivyOutputsList = new ArrayList<>();
        List<Items> itemsList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_MSW_DISTRIBUTION + " WHERE " + DBConstant.ISOLD + " ='N'", null);
        if (cursor.moveToFirst()) {

            do {
                Msw_Distribution_InputData msw_distribution_inputData = new Msw_Distribution_InputData();
                msw_distribution_inputData.setCreatedDate(cursor.getString(0));
                msw_distribution_inputData.setMswName(cursor.getString(1));
                msw_distribution_inputData.setVillage(cursor.getString(2));
                msw_distribution_inputData.setIsOld(cursor.getString(3));
                msw_distribution_inputData.setTabName(cursor.getString(4));
                msw_distribution_inputData.setDoctorId(cursor.getInt(5));
                Cursor itemsCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MSW_FOOD + " " +
                        " WHERE " + DBConstant.MSW_NAME + "='" + cursor.getString(1) + "' " +
                        "AND insert_date='" + cursor.getString(0) + "'", null);
                if (itemsCursor.moveToFirst()) {
                    do {
                        Items items = new Items();
                        items.setCreatedDate(itemsCursor.getString(0));
                        items.setmSWName(itemsCursor.getString(1));
                        items.setItemName(itemsCursor.getString(2));
                        items.setQty(itemsCursor.getString(3));
                        items.setUnit(itemsCursor.getString(4));
                        itemsList.add(items);

                    } while (itemsCursor.moveToNext());
                }
                msw_distribution_inputData.setItems(itemsList);
                mswAcitivyOutputsList.add(msw_distribution_inputData);

            } while (cursor.moveToNext());
        }
        return mswAcitivyOutputsList;

    }

    public List<Items> getMSWProduct(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> itemsList = new ArrayList<>();
        Cursor itemsCursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_MSW_FOOD + " WHERE " + DBConstant.MSW_ITEM_NAME + "=" + "'" + name + "'", null);
        ///Log.d("aaaaa","SELECT * FROM " + DBConstant.TABLE_MSW_FOOD + " WHERE " + DBConstant.MSW_ITEM_NAME+" ="+ name);
        if (itemsCursor.moveToFirst()) {
            do {
                Items items = new Items();
                items.setCreatedDate(itemsCursor.getString(0));
                items.setmSWName(itemsCursor.getString(1));
                items.setItemName(itemsCursor.getString(2));
                items.setQty(itemsCursor.getString(3));
                items.setUnit(itemsCursor.getString(4));
                itemsList.add(items);

            } while (itemsCursor.moveToNext());
        }
        return itemsList;

    }

    public void updateitems(String mswName, String qty) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_MSW_FOOD + " SET " + DBConstant.MSW_QTY + "=" + qty + " WHERE " + DBConstant.MSW_ITEM_NAME + "=" + "'" + mswName + "'");
        Log.d("quary", "UPDATE " + DBConstant.TABLE_MSW_FOOD + " SET " + DBConstant.MSW_QTY + "=" + qty + " WHERE " + DBConstant.MSW_ITEM_NAME + "=" + "'" + mswName + "'");
    }

    public void updateMswsold(String mswName) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_MSW_ACTIVITY + " SET " + DBConstant.ISOLD + "=" + "'Y'" + " WHERE " + DBConstant.MSW_NAME + "=" + "'" + mswName + "'");
    }

    public void updateMswDistributionsold(String mswName) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_MSW_DISTRIBUTION + " SET " + DBConstant.ISOLD + "=" + "'Y'" + " WHERE " + DBConstant.MSW_NAME + "=" + "'" + mswName + "'");
    }

    public void updateFollowupsold(String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_FOLLOWUP + " SET " + DBConstant.IS_NEW + "=" + "'Y'" + " WHERE " + DBConstant.SWP_NO + "=" + "'" + swp + "'");
    }

    public void updateOpthalsold(String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_OPHTHALMOLOGY + " SET " + DBConstant.ISOLD + "=" + "'Y'" + " WHERE " + DBConstant.SWP_NO + "=" + "'" + swp + "'");
    }

    public void updatePhysiosold(String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_PHYSIOTHERAPIST + " SET " + DBConstant.ISOLD + "=" + "'Y'" + " WHERE " + DBConstant.SWP_NO_PHYSIO + "=" + "'" + swp + "'");
    }

    public void updatePhysiosSessionold(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_PHYSIO_SESSION + " SET " + DBConstant.ISOLD + "=" + "'Y'" + " WHERE " + DBConstant.DOCTORS_ID + "=" + "'" + doctorId + "'");
    }

    /**
     * Insert Data into Advice Table
     */

    public String saveAdviceInput(List<AdviceInputData> adviceDataList) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Insert Data into Advice Table
        ContentValues values = new ContentValues();
        for (int i = 0; i < adviceDataList.size(); i++) {
            values.put(DBConstant.ADVICE_SWP_NO, adviceDataList.get(i).getSwpNo());
            values.put(DBConstant.PATIENT_NAME, adviceDataList.get(i).getPatientName());
            values.put(DBConstant.CHIEF_COMPLAINTS, adviceDataList.get(i).getChiefComplaints());
            values.put("investigationreport", adviceDataList.get(i).getInvestigationreport());
            values.put(DBConstant.TREATMENT_ADVISED, adviceDataList.get(i).getTreatmentAdviced());
            values.put(DBConstant.RURAL_PRG_DRS, adviceDataList.get(i).getRuralProgDrs());
            values.put(DBConstant.DOCTORS_COMMENT, adviceDataList.get(i).getDrsComment());
            values.put(DBConstant.IS_FOLLOWUP, adviceDataList.get(i).getIsFollowup());
            values.put(DBConstant.FOLLOWUP_TYPE_NAME, adviceDataList.get(i).getFollowupType());
            values.put(DBConstant.REFERRED_TO, adviceDataList.get(i).getReferred());
            values.put(DBConstant.ISOLD, "N");
            values.put(DBConstant.TAB_NAME, adviceDataList.get(i).getTabName());
            values.put("followupdate", adviceDataList.get(i).getFollowupDate());
            values.put(DBConstant.CREATED_DATE, String.valueOf(adviceDataList.get(i).getCreatedDate()));
            values.put(DBConstant.DOCTORS_ID, adviceDataList.get(i).getDoctorId());
            db.insert(DBConstant.TABLE_ADVICE, null, values);

            //Insert Data into ADVICE_SYSTEM
            if (adviceDataList.get(i).getSystem() != null)
                for (String systemData : adviceDataList.get(i).getSystem()) {
                    values = new ContentValues();
                    values.put(DBConstant.ADVICE_SWP_NO, adviceDataList.get(i).getSwpNo());
                    values.put(DBConstant.SYSTEM_Name, systemData);
                    values.put(DBConstant.CREATED_DATE, String.valueOf(adviceDataList.get(i).getCreatedDate()));
                    db.insert(DBConstant.TABLE_ADVICE_SYSTEM, null, values);
                }

            if (adviceDataList.get(i).getInvestigationId() != null)
                for (String investigation : adviceDataList.get(i).getInvestigationId()) {
                    values = new ContentValues();
                    values.put(DBConstant.SWP_NO, adviceDataList.get(i).getSwpNo());
                    values.put(DBConstant.INVESTIGATIN_ID, investigation);
                    values.put(DBConstant.INVESTIGATION_NAME, "");
                    values.put("createdDate", adviceDataList.get(i).getCreatedDate());
                    db.insert(DBConstant.TABLE_ADVICE_INVESTIGATION, null, values);
                }

            //Insert Data into Medicine_Prescribed
            if (adviceDataList.get(i).getMedicationPrescribed() != null)
                for (MedicationPrescribed medicineData : adviceDataList.get(i).getMedicationPrescribed()) {
                    values = new ContentValues();
                    values.put(DBConstant.ADVICE_SWP_NO, adviceDataList.get(i).getSwpNo());
                    values.put(DBConstant.MEDICINE_NAME, medicineData.getMedicine());
                    values.put(DBConstant.FREQUENCY_NAME, medicineData.getFrequency());
                    values.put(DBConstant.DAYS, medicineData.getDuration());
                    values.put(DBConstant.CREATED_DATE, String.valueOf(adviceDataList.get(i).getCreatedDate()));
                    db.insert(DBConstant.TABLE_MEDICINE_ADVICE, null, values);
                }
            values = new ContentValues();
        }
        db.close(); // Closing database connection
        return "SUCCESS";
    }

    /**
     * Update avdice table with isold = Y
     *
     * @param swp
     */
    public void updateAdviceIsoldByY(String swp) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + DBConstant.TABLE_ADVICE + " SET " + DBConstant.ISOLD + "=" + "'Y'" + " WHERE " + DBConstant.ADVICE_SWP_NO + "=" + "'" + swp + "'");
    }

    /**
     * Get the advice list for isold N
     *
     * @return adviceIsoldNList
     */
    public List<AdviceInputData> getAdviceDataByIsoldN() {
        List<AdviceInputData> adviceIsoldNList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE + " WHERE "
                + DBConstant.ISOLD + "='N'", null);
        if (cursor.moveToFirst()) {
            do {
                AdviceInputData adviceData = new AdviceInputData();
                List<MedicationPrescribed> medicineList = new ArrayList<>();
                adviceData.setSwpNo(cursor.getString(1));
                adviceData.setPatientName(cursor.getString(2));
                adviceData.setChiefComplaints(cursor.getString(3));
                adviceData.setInvestigationreport(cursor.getString(4));
                adviceData.setTreatmentAdviced(cursor.getString(5));
                adviceData.setRuralProgDrs(cursor.getString(6));
                adviceData.setDrsComment(cursor.getString(7));
                adviceData.setIsFollowup(cursor.getString(8));
                adviceData.setFollowupType(cursor.getString(9));
                adviceData.setFollowupDate(cursor.getString(10));
                adviceData.setReferred(cursor.getString(11));
                adviceData.setIsOld(cursor.getString(12));
                adviceData.setTabName(cursor.getString(13));
                adviceData.setCreatedDate(cursor.getString(14));
                adviceData.setDoctorId(cursor.getInt(15));

                Cursor mediCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MEDICINE_ADVICE + " " +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND CreatedDate='" + adviceData.getCreatedDate() + "'", null);
                if (mediCursor.moveToFirst()) {
                    do {
                        MedicationPrescribed medicine = new MedicationPrescribed();
                        medicine.setMedicine(mediCursor.getString(1));
                        medicine.setFrequency(mediCursor.getString(2));
                        medicine.setDuration(mediCursor.getString(3));
                        medicineList.add(medicine);
                    } while (mediCursor.moveToNext());
                    adviceData.setMedicationPrescribed(medicineList);
                }

                Cursor systemCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE_SYSTEM +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND CreatedDate='" + adviceData.getCreatedDate() + "'", null);
                if (systemCursor.moveToFirst()) {
                    List<String> systemList = new ArrayList<>();
                    do {
                        systemList.add(systemCursor.getString(1));
                    } while (systemCursor.moveToNext());
                    adviceData.setSystem(systemList);
                    Log.d("systemlist", new Gson().toJson(systemList));
                }


                Cursor investmentCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE_INVESTIGATION +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND CreatedDate='" + adviceData.getCreatedDate() + "'", null);
                if (investmentCursor.moveToFirst()) {
                    investigationList = new ArrayList<>();
                    do {

                        investigationList.add(investmentCursor.getString(1));
                    } while (investmentCursor.moveToNext());
                    adviceData.setInvestigationIds(investigationList);
                    Log.d("listttt", new Gson().toJson(investigationList));
                }


                adviceIsoldNList.add(adviceData);
            } while (cursor.moveToNext());
        }

        return adviceIsoldNList;
    }

    /**
     * Get all advice table data
     *
     * @return adviceDataList
     */
    public List<AdviceInputData> getAdviceAll() {
        List<AdviceInputData> adviceDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE, null);
        if (cursor.moveToFirst()) {
            do {
                AdviceInputData adviceData = new AdviceInputData();
                List<MedicationPrescribed> medicineList = new ArrayList<>();
                adviceData.setSwpNo(cursor.getString(1));
                adviceData.setPatientName(cursor.getString(2));
                adviceData.setChiefComplaints(cursor.getString(3));
                adviceData.setTreatmentAdviced(cursor.getString(4));
                adviceData.setRuralProgDrs(cursor.getString(5));
                adviceData.setDrsComment(cursor.getString(6));
                adviceData.setIsFollowup(cursor.getString(7));
                adviceData.setFollowupType(cursor.getString(8));
                adviceData.setReferred(cursor.getString(9));
                adviceData.setIsOld(cursor.getString(10));
                adviceData.setTabName(cursor.getString(11));
                adviceData.setCreatedDate(cursor.getString(12));
                adviceData.setDoctorId(cursor.getInt(13));

                Cursor mediCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MEDICINE_ADVICE + " " +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND createdDate='" + adviceData.getCreatedDate() + "'", null);
                if (mediCursor.moveToFirst()) {
                    do {
                        MedicationPrescribed medicine = new MedicationPrescribed();
                        medicine.setMedicine(mediCursor.getString(1));
                        medicine.setFrequency(mediCursor.getString(2));
                        medicine.setDuration(mediCursor.getString(3));
                        medicineList.add(medicine);
                    } while (mediCursor.moveToNext());
                    adviceData.setMedicationPrescribed(medicineList);
                }

                Cursor systemCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE_SYSTEM +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND createdDate='" + adviceData.getCreatedDate() + "'", null);
                if (systemCursor.moveToFirst()) {
                    List<String> systemList = new ArrayList<>();
                    do {
                        systemList.add(systemCursor.getString(1));
                    } while (systemCursor.moveToNext());
                    adviceData.setSystem(systemList);
                }
                adviceDataList.add(adviceData);
            } while (cursor.moveToNext());
        }

        return adviceDataList;
    }

    /**
     * Get print Advice Data
     */
    public AdviceInputData printAdviceData(String swpNo) {
        AdviceInputData adviceData = new AdviceInputData();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE +
                " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + swpNo + "' " +
                " Order BY " + DBConstant.CREATED_DATE + " DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            do {

                List<MedicationPrescribed> medicineList = new ArrayList<>();
                adviceData.setSwpNo(cursor.getString(1));
                adviceData.setPatientName(cursor.getString(2));
                adviceData.setChiefComplaints(cursor.getString(3));
                adviceData.setTreatmentAdviced(cursor.getString(4));
                adviceData.setRuralProgDrs(cursor.getString(5));
                adviceData.setDrsComment(cursor.getString(6));
                adviceData.setIsFollowup(cursor.getString(7));
                adviceData.setFollowupType(cursor.getString(8));
                adviceData.setReferred(cursor.getString(9));
                adviceData.setIsOld(cursor.getString(10));
                adviceData.setTabName(cursor.getString(11));
                adviceData.setCreatedDate(cursor.getString(12));
                adviceData.setDoctorId(cursor.getInt(13));

                Cursor mediCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MEDICINE_ADVICE + " " +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND CreatedDate='" + adviceData.getCreatedDate() + "'", null);
                if (mediCursor.moveToFirst()) {
                    do {
                        MedicationPrescribed medicine = new MedicationPrescribed();
                        medicine.setMedicine(mediCursor.getString(1));
                        medicine.setFrequency(mediCursor.getString(2));
                        medicine.setDuration(mediCursor.getString(3));
                        medicineList.add(medicine);
                    } while (mediCursor.moveToNext());
                    adviceData.setMedicationPrescribed(medicineList);
                }

                Cursor systemCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE_SYSTEM +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + adviceData.getSwpNo() + "' " +
                        "AND CreatedDate='" + adviceData.getCreatedDate() + "'", null);
                if (systemCursor.moveToFirst()) {
                    List<String> systemList = new ArrayList<>();
                    do {
                        systemList.add(systemCursor.getString(1));
                    } while (systemCursor.moveToNext());
                    adviceData.setSystem(systemList);
                }

            } while (cursor.moveToNext());
        }

        return adviceData;
    }

    public void insertMSWACtivity(List<MswAcitivyInputData> mswAcitivyOutputsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (MswAcitivyInputData data : mswAcitivyOutputsList) {
            values.put(DBConstant.CURRENTS_DATE, data.getCreatedDate());
            values.put(DBConstant.MSW_NAME, data.getMswName());
            values.put(DBConstant.VILLAGE_ATTENTED, data.getVillage());
            values.put(DBConstant.OTHER_OPTION, data.getOtherOption());
            values.put(DBConstant.CHILDREN_COUNT, data.getChildrenCount());
            values.put(DBConstant.IS_TEACHER_PRESENT, data.getIsTeacherPresent());
            values.put(DBConstant.IS_WATER_FILTER_PRESENT, data.getIsWaterFilterPresent());
            values.put(DBConstant.IS_HAND_WASH_PRESENT, data.getIsHandWashPresent());
            values.put(DBConstant.SUPPORT_REQUIRED, data.getSupportReq());
            values.put(DBConstant.REMARK, data.getRemark());
            values.put(DBConstant.HOUSE_VISIT, data.getHouseVisit());
            values.put(DBConstant.ISOLD, data.getIsOld());
            values.put(DBConstant.TAB_NAME, data.getTabNo());
            values.put(DBConstant.DOCTORS_ID, data.getDoctorId());
            db.insert(DBConstant.TABLE_MSW_ACTIVITY, null, values);

            for (String activity : data.getActivities()) {
                values = new ContentValues();
                values.put(DBConstant.ACT_INSERT_DATE, data.getCreatedDate());
                values.put(DBConstant.ACT_MSW_NAME, data.getMswName());
                values.put(DBConstant.ACT_MSW_VILLAGE, data.getVillage());
                values.put(DBConstant.ACT_ACTIVITY, activity);
                db.insert(DBConstant.TABLE_activity, null, values);
            }
            values = new ContentValues();
        }


        db.close(); // Closing database connection
    }

    public List<MswAcitivyInputData> getMswData() {
        ArrayList<MswAcitivyInputData> mswAcitivyOutputsList = new ArrayList<>();
        ArrayList<String> activityList;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_MSW_ACTIVITY, new String[]{
                        DBConstant.INSERT_DATE, DBConstant.MSW_NAME, DBConstant.VILLAGE_ATTENTED, DBConstant.OTHER_OPTION, DBConstant.CHILDREN_COUNT,
                        DBConstant.IS_TEACHER_PRESENT, DBConstant.IS_WATER_FILTER_PRESENT, DBConstant.IS_HAND_WASH_PRESENT, DBConstant.SUPPORT_REQUIRED, DBConstant.REMARK, DBConstant.HOUSE_VISIT, DBConstant.TAB_NAME, DBConstant.ISOLD}
                , null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            activityList = new ArrayList<>();

            do {
                MswAcitivyInputData mswAcitivyOutput = new MswAcitivyInputData();
                mswAcitivyOutput.setCreatedDate(cursor.getString(0));
                mswAcitivyOutput.setMswName(cursor.getString(1));
                mswAcitivyOutput.setVillage(cursor.getString(2));
                mswAcitivyOutput.setOtherOption(cursor.getString(3));
                mswAcitivyOutput.setChildrenCount(cursor.getString(4));
                mswAcitivyOutput.setIsTeacherPresent(cursor.getString(5));
                mswAcitivyOutput.setIsWaterFilterPresent(cursor.getString(6));
                mswAcitivyOutput.setIsHandWashPresent(cursor.getString(7));
                mswAcitivyOutput.setSupportReq(cursor.getString(8));
                mswAcitivyOutput.setRemark(cursor.getString(9));
                mswAcitivyOutput.setHouseVisit(cursor.getString(10));
                mswAcitivyOutput.setIsOld(cursor.getString(11));
                mswAcitivyOutput.setTabNo(cursor.getString(12));


                Cursor activityCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_activity + " " + " WHERE " + DBConstant.MSW_NAME + "='" + mswAcitivyOutput.getMswName() + "' " + "AND insert_date='" + mswAcitivyOutput.getCreatedDate() + "'", null);

                System.out.println("query" + "SELECT * FROM  " + DBConstant.TABLE_activity + " " + " WHERE " + DBConstant.MSW_NAME + "='" + mswAcitivyOutput.getMswName() + "' " + "AND insert_date='" + mswAcitivyOutput.getCreatedDate() + "'");


                if (activityCursor.moveToFirst()) {
                    do {
                        activityList.add(activityCursor.getString(3));
                    } while (activityCursor.moveToNext());
                    mswAcitivyOutput.setActivities(activityList);
                    activityList = new ArrayList<>();

                }
                mswAcitivyOutputsList.add(mswAcitivyOutput);
            } while (cursor.moveToNext());
        }

        return mswAcitivyOutputsList;
    }

    public MswAcitivyInputData getMSWData(String MSWName) {

        MswAcitivyInputData data = new MswAcitivyInputData();
        ArrayList<String> activities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBConstant.TABLE_MSW_ACTIVITY, new String[]{
                        DBConstant.MSW_NAME, DBConstant.VILLAGE_ATTENTED, DBConstant.ACTIVITY_ONE, DBConstant.ACTIVITY_TWO, DBConstant.ACTIVITY_THREE,
                        DBConstant.ACTIVITY_FOUR, DBConstant.ACTIVITY_FIVE, DBConstant.ACTIVITY_SIX, DBConstant.OTHER_OPTION, DBConstant.CHILDREN_COUNT,
                        DBConstant.IS_TEACHER_PRESENT, DBConstant.IS_WATER_FILTER_PRESENT, DBConstant.IS_HAND_WASH_PRESENT, DBConstant.SUPPORT_REQUIRED, DBConstant.REMARK, DBConstant.HOUSE_VISIT}
                , DBConstant.MSW_NAME + "=?",
                new String[]{String.valueOf(MSWName)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                data.setMswName(cursor.getString(0));
                data.setVillage(cursor.getString(1));
                activities.add(cursor.getString(2));
                activities.add(cursor.getString(3));
                activities.add(cursor.getString(4));
                activities.add(cursor.getString(5));
                activities.add(cursor.getString(6));
                activities.add(cursor.getString(7));
                data.setActivities(activities);
                data.setOtherOption(cursor.getString(8));
                data.setChildrenCount(cursor.getString(9));
                data.setIsTeacherPresent(cursor.getString(10));
                data.setIsWaterFilterPresent(cursor.getString(11));
                data.setIsHandWashPresent(cursor.getString(12));
                data.setSupportReq(cursor.getString(13));
                data.setRemark(cursor.getString(14));
                data.setHouseVisit(cursor.getString(15));
                data.setIsOld(cursor.getString(16));
                data.setTabNo(cursor.getString(17));

            } while (cursor.moveToNext());

        }
        return data;
    }


    //MSW_ACTIVITY TABLES

    public void deletedata() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_MSW_FOOD);
    }

    public String insertMSWNameSystem(List<SpinnerData> mswSystemList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_MSW_NAME);
        //Insert Data into Medicine Table
        for (SpinnerData data : mswSystemList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MSW_NAME_ID, data.getId());
            values.put(DBConstant.MSW_NAMES, data.getName());
            db.insert(DBConstant.TABLE_MSW_NAME, null, values);
        }
        db.close();
        return "SUCCESS";
    }


    public List<SpinnerData> getMswNameSystemData() {
        List<SpinnerData> mswSystemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_MSW_NAME, new String[]{
                DBConstant.MSW_NAME_ID, DBConstant.MSW_NAMES}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SpinnerData data = new SpinnerData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                mswSystemList.add(data);
            } while (cursor.moveToNext());
        }
        return mswSystemList;
    }


    public String insert_Village_Attendent_System(List<SpinnerData> mswVillageAttendant) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_VILLAGE_ATTENDENT);
        //Insert Data into Medicine Table
        for (SpinnerData data : mswVillageAttendant) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MSW_VILLAGE_ATTENDENT_ID, data.getId());
            values.put(DBConstant.MSW_VILLAGE_ATTENDENT_NAME, data.getName());
            db.insert(DBConstant.TABLE_VILLAGE_ATTENDENT, null, values);
        }
        db.close();
        return "SUCCESS";
    }


    public List<SpinnerData> getVillage_Attendant_NameSystemData() {
        List<SpinnerData> mswSystemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_VILLAGE_ATTENDENT, new String[]{
                DBConstant.MSW_VILLAGE_ATTENDENT_ID, DBConstant.MSW_VILLAGE_ATTENDENT_NAME}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SpinnerData data = new SpinnerData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                mswSystemList.add(data);
            } while (cursor.moveToNext());
        }
        return mswSystemList;
    }

    //Advice
    public String insertAdviceSystem(List<SystemOutputData> adviceSystemList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_SYSTEM);
        //Insert Data into Medicine Table
        for (SystemOutputData data : adviceSystemList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.SYSTEM_ID, data.getSystemId());
            values.put(DBConstant.SYSTEM_Name, data.getSystemName());
            db.insert(DBConstant.TABLE_SYSTEM, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    public List<SystemOutputData> getAdviceSystemData() {
        List<SystemOutputData> adviceSystemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_SYSTEM, new String[]{
                DBConstant.SYSTEM_ID, DBConstant.SYSTEM_Name}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SystemOutputData data = new SystemOutputData();
                data.setSystemId(cursor.getInt(0));
                data.setSystemName(cursor.getString(1));
                adviceSystemList.add(data);
            } while (cursor.moveToNext());
        }
        return adviceSystemList;
    }

    public String insertMedicineData(List<MedicineOutputData> medicineList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_MEDICINE);
        //Insert Data into Medicine Table
        for (MedicineOutputData data : medicineList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MEDICINE_ID, data.getMedicineId());
            values.put(DBConstant.MEDICINE_NAME, data.getMedicineName());
            values.put(DBConstant.MEDICINE_CODE, data.getMedicineCode());
            db.insert(DBConstant.TABLE_MEDICINE, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    public List<MedicineOutputData> getMedicineData() {
        List<MedicineOutputData> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_MEDICINE, new String[]{
                DBConstant.MEDICINE_ID, DBConstant.MEDICINE_NAME, DBConstant.MEDICINE_CODE}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                MedicineOutputData data = new MedicineOutputData();
                data.setMedicineId(cursor.getInt(0));
                data.setMedicineName(cursor.getString(1));
                data.setMedicineCode(cursor.getString(2));
                medicineList.add(data);
            } while (cursor.moveToNext());
        }
        return medicineList;
    }

    //Frequency
    public String insertFrequencyData(List<FrequencieOutputData> frequencylist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_FREQUENCY);
        //Insert Data into Medicine Table
        for (FrequencieOutputData data : frequencylist) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.FREQUENCY_ID, data.getFrequencyId());
            values.put(DBConstant.FREQUENCY_NAME, data.getFrequencyName());
            db.insert(DBConstant.TABLE_FREQUENCY, null, values);
        }
//        db.close();
        return "SUCCESS";
    }


    //Frequency
    public String insertActivityData(List<ActivityData> frequencylist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_Activity);
        //Insert Data into Medicine Table
        for (ActivityData data : frequencylist) {
            ContentValues values = new ContentValues();
            values.put("activityId", data.getActivityId());
            values.put("activityName", data.getActivityName());
            db.insert(DBConstant.TABLE_Activity, null, values);
        }
//        db.close();
        return "SUCCESS";
    }

    public List<FrequencieOutputData> getFrequencyData() {
        List<FrequencieOutputData> frequencyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_FREQUENCY, new String[]{
                DBConstant.FREQUENCY_ID, DBConstant.FREQUENCY_NAME}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                FrequencieOutputData data = new FrequencieOutputData();
                data.setFrequencyId(cursor.getInt(0));
                data.setFrequencyName(cursor.getString(1));
                frequencyList.add(data);
            } while (cursor.moveToNext());
        }
        return frequencyList;
    }

    public List<ActivityData> getsessionActivityList() {
        List<ActivityData> getactivityList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_Activity, new String[]{
                "activityId", "activityName"}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ActivityData data = new ActivityData();
                data.setActivityId(cursor.getString(0));
                data.setActivityName(cursor.getString(1));
                getactivityList.add(data);
            } while (cursor.moveToNext());
        }
        return getactivityList;
    }


    //Programm Doctors
    public String insertProgramDoctorsData(List<SpinnerData> doctorslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_RURAL_DOCTORS);
        //Insert Data into Medicine Table
        for (SpinnerData data : doctorslist) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.DOCTOR_ID, data.getId());
            values.put(DBConstant.DOCTOR_NAME, data.getName());
            db.insert(DBConstant.TABLE_RURAL_DOCTORS, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    public List<SpinnerData> getProgrammDoctorsData() {
        List<SpinnerData> doctorsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_RURAL_DOCTORS, new String[]{
                DBConstant.DOCTOR_ID, DBConstant.DOCTOR_NAME}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SpinnerData data = new SpinnerData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                doctorsList.add(data);
            } while (cursor.moveToNext());
        }
        return doctorsList;
    }

    //Durations
    public String insertDuartionData(List<SpinnerData> durationlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_DURATION);
        //Insert Data into Medicine Table
        for (SpinnerData data : durationlist) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.DURATION_ID, data.getId());
            values.put(DBConstant.DURATION_TYPE, data.getName());
            db.insert(DBConstant.TABLE_DURATION, null, values);
        }
        db.close();
        return "SUCCESS";
    }


    public String insertMSWData(List<SpinnerData> mswList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_DURATION);
        //Insert Data into Medicine Table
        for (SpinnerData data : mswList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.DURATION_ID, data.getId());
            values.put(DBConstant.DURATION_TYPE, data.getName());
            db.insert(DBConstant.TABLE_DURATION, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    //Durations
    public List<SpinnerData> getDurationData() {
        List<SpinnerData> durationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_DURATION, new String[]{
                DBConstant.DURATION_ID, DBConstant.DURATION_TYPE}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SpinnerData data = new SpinnerData();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                durationList.add(data);
            } while (cursor.moveToNext());
        }
        return durationList;
    }


    //MSW_ACTIVITY TABLES
    public String insertVillageData(List<VillageOutputData> mswSystemList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_VILLAGE_PADA);
        //Insert Data into Medicine Table
        for (VillageOutputData data : mswSystemList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MSW_VILLAGE_ID, data.getVillageId());
            values.put(DBConstant.MSW_VILLAGE_NAME, data.getVillageName());
            values.put(DBConstant.MSW_VILLAGE_PREFIX, data.getVillagePrefix());
            db.insert(DBConstant.TABLE_VILLAGE_PADA, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    //Village
    public List<VillageOutputData> getVillageData() {
        List<VillageOutputData> villageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_VILLAGE_PADA, new String[]{
                DBConstant.MSW_VILLAGE_ID, DBConstant.MSW_VILLAGE_NAME, DBConstant.MSW_VILLAGE_PREFIX}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                VillageOutputData data = new VillageOutputData();
                data.setVillageId(cursor.getInt(0));
                data.setVillageName(cursor.getString(1));
                data.setVillagePrefix(cursor.getString(2));
                villageList.add(data);
            } while (cursor.moveToNext());
        }
        return villageList;
    }


    //insert MSW Product data

    //Advice
    public String insertMSWProductSystem(List<ProductsOutputData> adviceSystemList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_PRODUCTS);
        //Insert Data into Medicine Table
        for (ProductsOutputData data : adviceSystemList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.PRODUCT_ID, data.getProductId());
            values.put(DBConstant.PRODUCT_CODE, data.getProductCode());
            values.put(DBConstant.PRODUCT_NAME, data.getProductName());
            values.put(DBConstant.PRODUCT_DESCRIPTION, data.getProductDescription());
            values.put(DBConstant.UNIT, data.getUnit());
            values.put(DBConstant.QUANTITY, data.getQty());
            db.insert(DBConstant.TABLE_PRODUCTS, null, values);
        }
        db.close();
        return "SUCCESS";
    }


    //getMSWProducts
    public List<ProductsOutputData> getMSWProductData() {
        List<ProductsOutputData> mswProductList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_PRODUCTS, new String[]{
                DBConstant.PRODUCT_NAME, DBConstant.UNIT, DBConstant.QUANTITY}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ProductsOutputData data = new ProductsOutputData();
                data.setProductName(cursor.getString(0));
                data.setUnit(cursor.getString(1));
                data.setQty(cursor.getString(2));
                mswProductList.add(data);
            } while (cursor.moveToNext());
        }
        return mswProductList;
    }


    /**
     * Insert data in Ophthalmolgy table
     *
     * @param opthaldataList
     */
    public void insertOphthalmolgy(List<OphthalInputData> opthaldataList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (OphthalInputData data : opthaldataList) {
            values = new ContentValues();
            values.put(DBConstant.SWP_NO_OPHTHA, data.getSwpNo());
            values.put(DBConstant.PATIENT_NAME_OPHTHA, data.getPatientName());
            values.put(DBConstant.AGE_OPHTHA, data.getAge());
            values.put(DBConstant.GENDER_OPHTHA, data.getGender());
            values.put(DBConstant.VILLAGE_OPHTHA, data.getVillage());
            values.put(DBConstant.CURRENT_DATE, data.getCreatedDate());
            values.put(DBConstant.Opthal_ISOLD, "N");
            values.put(DBConstant.OPTHAL_TAB_NAME, data.getTabName());
            values.put(DBConstant.DOCTORS_ID, data.getDoctorId());
            values.put(DBConstant.REMARK_OPHTHA, data.getRemark());
            db.insert(DBConstant.TABLE_OPHTHALMOLOGY, null, values);
            for (String morbidity : data.getMorbidity()) {
                values = new ContentValues();
                values.put(DBConstant.SWP_NO_OPHTHA, data.getSwpNo());
                values.put(DBConstant.MORBIDITY, morbidity);
                values.put(DBConstant.CURRENT_DATE, data.getCreatedDate());
                db.insert(DBConstant.TABLE_MORBIDITY, null, values);
            }
        }

        db.close(); // Closing database connection
    }

    /**
     * Get/Fetch data from Ophthalmolgy table
     */
    public List<OphthalInputData> getAllOphthalmolgyDataByIsoldN() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<OphthalInputData> ophthaList = new ArrayList<>();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_OPHTHALMOLOGY + " WHERE " + DBConstant.Opthal_ISOLD + " ='N'", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    OphthalInputData ophthaData = new OphthalInputData();
                    ophthaData.setSwpNo(cursor.getString(0));
                    ophthaData.setPatientName(cursor.getString(1));
                    ophthaData.setAge(cursor.getString(2));
                    ophthaData.setGender(cursor.getString(3));
                    ophthaData.setVillage(cursor.getString(4));
                    ophthaData.setIsOld(cursor.getString(5));
                    ophthaData.setTabName(cursor.getString(6));
                    ophthaData.setCreatedDate(cursor.getString(7));
                    ophthaData.setDoctorId(cursor.getInt(8));
                    ophthaData.setRemark(cursor.getString(9));
                    Cursor morbidityCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MORBIDITY + " " +
                            " WHERE " + DBConstant.SWP_NO_OPHTHA + "='" + ophthaData.getSwpNo() + "' " +
                            "AND CreatedDate='" + ophthaData.getCreatedDate() + "'", null);
                    List<String> morbidityList = new ArrayList<>();
                    if (morbidityCursor.moveToFirst()) {
                        do {
                            morbidityList.add(morbidityCursor.getString(1));
                        } while (morbidityCursor.moveToNext());
                        ophthaData.setMorbidity(morbidityList);
                    }
                    ophthaList.add(ophthaData);
                } while (cursor.moveToNext());
            }
        }
        return ophthaList;
    }

    /**
     * Get/Fetch data from Ophthalmolgy table
     */
    public List<OphthalInputData> getAllOphthalmolgyData(String swpNo) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<OphthalInputData> ophthaList = new ArrayList<>();
        Cursor cursor = null;
        if (swpNo != null) {
            cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_OPHTHALMOLOGY + " " +
                    " WHERE " + DBConstant.SWP_NO_OPHTHA + "='" + swpNo + "' " +
                    "AND " + DBConstant.CURRENT_DATE + "='" + Utililty.getCurDate() + "'", null);
        } else {
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_OPHTHALMOLOGY, null);
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    OphthalInputData ophthaData = new OphthalInputData();
                    ophthaData.setSwpNo(cursor.getString(0));
                    ophthaData.setPatientName(cursor.getString(1));
                    ophthaData.setAge(cursor.getString(2));
                    ophthaData.setGender(cursor.getString(3));
                    ophthaData.setVillage(cursor.getString(4));
                    ophthaData.setIsOld(cursor.getString(5));
                    ophthaData.setTabName(cursor.getString(6));
                    ophthaData.setCreatedDate(cursor.getString(7));
                    ophthaData.setDoctorId(cursor.getInt(8));
                    ophthaData.setRemark(cursor.getString(9));
                    Cursor morbidityCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MORBIDITY + " " +
                            " WHERE " + DBConstant.SWP_NO_OPHTHA + "='" + ophthaData.getSwpNo() + "' " +
                            "AND CreatedDate='" + ophthaData.getCreatedDate() + "'", null);
                    List<String> morbidityList = new ArrayList<>();
                    if (morbidityCursor.moveToFirst()) {
                        do {
                            morbidityList.add(morbidityCursor.getString(1));
                        } while (morbidityCursor.moveToNext());
                        ophthaData.setMorbidity(morbidityList);
                    }
                    ophthaList.add(ophthaData);
                } while (cursor.moveToNext());
            }
        }
        return ophthaList;
    }

    /**
     * Insert data in Physiothrapist table
     *
     * @param dataList
     */
    public void insertPhysiothrapistData(List<PhysiotherapistInputData> dataList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (PhysiotherapistInputData data : dataList) {
            values = new ContentValues();
            values.put(DBConstant.CURRENT_DATE_PHYSIO, data.getCurrentDate());
            values.put(DBConstant.SWP_NO_PHYSIO, data.getSwpNo());
            values.put(DBConstant.PATIENT_NAME_PHYSIO, data.getPatientName());
            values.put(DBConstant.AGE_PHYSIO, data.getAge());
            values.put(DBConstant.GENDER_PHYSIO, data.getGender());
            values.put(DBConstant.NAME_PHYSIO, data.getNamePhysio());
            values.put(DBConstant.PAIN_SCORE, data.getPainScore());
            values.put(DBConstant.EXERCIE_ADVICE, data.getExercieAdvice());
            values.put(DBConstant.REMARK_PHYSIO, data.getRemark());
            values.put(DBConstant.ISOLD, "N");
            values.put(DBConstant.DOCTORS_ID, data.getDoctorId());
            db.insert(DBConstant.TABLE_PHYSIOTHERAPIST, null, values);
            // Closing database connection
        }
        db.close();
    }

    /**
     * Get/Fetch data from Physiothrapist table
     */
    public List<PhysiotherapistInputData> getAllPhysiotherapistData(String swpNo) {
        List<PhysiotherapistInputData> physioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (swpNo != null) {
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_PHYSIOTHERAPIST +
                    " WHERE " + DBConstant.SWP_NO_PHYSIO + "='" + swpNo + "' AND " +
                    DBConstant.CURRENT_DATE_PHYSIO + "='" + Utililty.getCurDate() + "'", null);
        } else {
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_PHYSIOTHERAPIST, null);
        }
        if (cursor.moveToFirst()) {
            do {
                PhysiotherapistInputData physioData = new PhysiotherapistInputData();
                physioData.setCurrentDate(cursor.getString(0));
                physioData.setSwpNo(cursor.getString(1));
                physioData.setPatientName(cursor.getString(2));
                physioData.setAge(cursor.getString(3));
                physioData.setGender(cursor.getString(4));
                physioData.setNamePhysio(cursor.getString(5));
                physioData.setPainScore(cursor.getString(6));
                physioData.setExercieAdvice(cursor.getString(7));
                physioData.setRemark(cursor.getString(8));
                physioData.setIsOld(cursor.getString(9));
                physioData.setDoctorId(cursor.getInt(10));
                physioList.add(physioData);
            } while (cursor.moveToNext());
        }
        return physioList;
    }

    /**
     * Get/Fetch all data from Physiothrapist table
     */
    public List<PhysiotherapistInputData> getAllPhysiotherapistDataByIsNew() {
        List<PhysiotherapistInputData> physioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_PHYSIOTHERAPIST + " WHERE " + DBConstant.ISOLD + " ='N'", null);
        if (cursor.moveToFirst()) {
            do {
                PhysiotherapistInputData physioData = new PhysiotherapistInputData();
                physioData.setCurrentDate(cursor.getString(0));
                physioData.setSwpNo(cursor.getString(1));
                physioData.setPatientName(cursor.getString(2));
                physioData.setAge(cursor.getString(3));
                physioData.setGender(cursor.getString(4));
                physioData.setNamePhysio(cursor.getString(5));
                physioData.setPainScore(cursor.getString(6));
                physioData.setExercieAdvice(cursor.getString(7));
                physioData.setRemark(cursor.getString(8));
                physioData.setIsOld(cursor.getString(9));
                physioData.setDoctorId(cursor.getInt(10));
                physioList.add(physioData);
            } while (cursor.moveToNext());
        }
        return physioList;
    }

    /**
     * Insert data in Physiothrapist Session table
     *
     * @param dataList
     */
    public void insertPhysioSessionData(List<PhysioSessionInputData> dataList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (PhysioSessionInputData data : dataList) {
            values = new ContentValues();
            values.put(DBConstant.CURRENT_DATE_PHYSIO, data.getCurrentDate());
            values.put(DBConstant.SESSION_TAKEN, data.getSessionTaken());
            values.put(DBConstant.NO_OF_CHILDREN_ATT, data.getNoOfChildrenAtt());
            values.put(DBConstant.TEACHER_PRESENT, data.getTeacherPresent());
            values.put(DBConstant.SUPPORT_REQ, data.getSupportReq());
            values.put(DBConstant.REMARK_PHYSIO, data.getRemark());
            values.put(DBConstant.ISOLD, "N");
            values.put(DBConstant.DOCTORS_ID, data.getDoctorId());
            db.insert(DBConstant.TABLE_PHYSIO_SESSION, null, values);
            // Closing database connection
        }
        db.close();
    }


    /**
     * Get/Fetch data from Physiothrapist Session table
     */
    /*public List<PhysioSessionInputData> getAllPhysioSessionData() {
        List<PhysioSessionInputData> physioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (swpNo != null) {
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_PHYSIO_SESSION +
                    " WHERE " + DBConstant.CURRENT_DATE_PHYSIO + "='" + Utililty.getCurDate() + "'", null);
        } else {
            cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_PHYSIO_SESSION, null);
        }
        if (cursor.moveToFirst()) {
            do {
                PhysioSessionInputData physioData = new PhysioSessionInputData();
                physioData.setCurrentDate(cursor.getString(0));
                physioData.setSessionTaken(cursor.getString(1));
                physioData.setNoOfChildrenAtt(cursor.getString(2));
                physioData.setTeacherPresent(cursor.getString(3));
                physioData.setSupportReq(cursor.getString(4));
                physioData.setRemark(cursor.getString(5));
                physioData.setIsOld(cursor.getString(6));
                physioData.setDoctorId(cursor.getInt(7));
                physioList.add(physioData);
            } while (cursor.moveToNext());
        }
        return physioList;
    }*/

    /**
     * Get/Fetch all data from Physiothrapist table
     */
    public List<PhysioSessionInputData> getAllPhysioSessionDataByIsNew() {
        List<PhysioSessionInputData> physioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_PHYSIO_SESSION + " WHERE " + DBConstant.ISOLD + " ='N'", null);
        if (cursor.moveToFirst()) {
            do {
                PhysioSessionInputData physioData = new PhysioSessionInputData();
                physioData.setCurrentDate(cursor.getString(0));
                physioData.setSessionTaken(cursor.getString(1));
                physioData.setNoOfChildrenAtt(cursor.getString(2));
                physioData.setTeacherPresent(cursor.getString(3));
                physioData.setSupportReq(cursor.getString(4));
                physioData.setRemark(cursor.getString(5));
                physioData.setIsOld(cursor.getString(6));
                physioData.setDoctorId(cursor.getInt(7));
                physioList.add(physioData);
            } while (cursor.moveToNext());
        }
        return physioList;
    }


    /**
     * Insert User Data in User Table
     */
    public void insertUserData(UserData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstant.USER_ID, data.getUserId());
        values.put(DBConstant.USER_NAME, data.getLoginId());
        values.put(DBConstant.USER_PASSWORD, data.getPassword());
        values.put(DBConstant.USER_DEVICE, data.getDeviceName());
        db.insert(DBConstant.TABLE_USER, null, values);
        db.close();
    }

    //Symptoms
    public String insertSymptomsSystem(List<SymptompsOutputData> symptomsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_SYMPTOMPS);
        //Insert Data into Medicine Table
        for (SymptompsOutputData data : symptomsList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.SYMPTOM_ID, data.getSymptomId());
            values.put(DBConstant.SYMPTOM_NAME, data.getSymptomName());
            db.insert(DBConstant.TABLE_SYMPTOMPS, null, values);
        }
        db.close();
        return "SUCCESS";
    }


    //MSW DistribituinData
    public String insertMswDistribution(List<Msw_Distribution_InputData> mswList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_MSW_DISTRIBUTION);
        //Insert Data into MSW DISTRIBUTION Table

        for (int i = 0; i < mswList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MSW_INSERT_DATE, mswList.get(i).getCreatedDate());
            values.put(DBConstant.MSW_NAME, mswList.get(i).getMswName());
            values.put(DBConstant.MSW_VILLAGES, mswList.get(i).getVillage());
            values.put(DBConstant.MSW_ISOLD, mswList.get(i).getIsOld());
            values.put(DBConstant.MSW_TAB_NAME, mswList.get(i).getTabName());
            values.put(DBConstant.DOCTORS_ID, mswList.get(i).getDoctorId());
            db.insert(DBConstant.TABLE_MSW_DISTRIBUTION, null, values);

            for (Items data : mswList.get(i).getItems()) {
                values = new ContentValues();
                values.put(DBConstant.MSW_INSERT_DATE, mswList.get(i).getCreatedDate());
                values.put(DBConstant.MSW_NAME, mswList.get(i).getMswName());
                values.put(DBConstant.MSW_ITEM_NAME, data.getItemName());
                values.put(DBConstant.MSW_QTY, data.getQty());
                values.put(DBConstant.MSW_UNIT, data.getUnit());
                db.insert(DBConstant.TABLE_MSW_FOOD, null, values);
            }

        }


        db.close();
        return "SUCCESS";
    }



  /*  //MSW DistribituinData FOOD
    public String insertMswFood(List<Items> mswFoodList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_MSW_FOOD);
        //Insert Data into MSW FOOD Table
        for (Items data : mswFoodList) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.MSW_INSERT_DATE, data.getCreatedDate());
            values.put(DBConstant.MSW_NAME, data.getMswName());
            values.put(DBConstant.MSW_ITEM_NAME, data.getItemName());
            values.put(DBConstant.MSW_QTY, data.getQty());
            values.put(DBConstant.MSW_UNIT, data.getUnit());
            db.insert(DBConstant.TABLE_MSW_FOOD, null, values);
        }
        db.close();
        return "SUCCESS";
    }*/

    public List<SymptompsOutputData> geSymptomsData() {
        List<SymptompsOutputData> symptomsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_SYMPTOMPS, new String[]{
                DBConstant.SYMPTOM_ID, DBConstant.SYMPTOM_NAME}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SymptompsOutputData data = new SymptompsOutputData();
                data.setSymptomId(cursor.getString(0));
                data.setSymptomName(cursor.getString(1));
                symptomsList.add(data);
            } while (cursor.moveToNext());
        }
        return symptomsList;
    }


    /**
     * Get the user data
     */
    public List<UserData> getAllUserLoginData() {

        List<UserData> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_USER, null);
        if (cursor.moveToFirst()) {
            do {
                UserData userData = new UserData();
                userData.setUserId(cursor.getInt(0));
                userData.setLoginId(cursor.getString(1));
                userData.setPassword(cursor.getString(2));
                userData.setDeviceName(cursor.getString(3));
                //    userData.setDoctorId(cursor.getInt(7));
                userList.add(userData);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    /**
     * Get Advive
     *
     * @param autoSWPNo
     * @return AdviceInputData
     */
    public AdviceInputData getSWPAdviceData(String autoSWPNo) {
        AdviceInputData advice = new AdviceInputData();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE + " WHERE "
                + DBConstant.ADVICE_SWP_NO + "='" + autoSWPNo + "' AND " + DBConstant.CREATED_DATE + "='" + Utililty.getCurDate() + "'", null);
        if (cursor.moveToFirst()) {
            do {
                List<MedicationPrescribed> medicineList = new ArrayList<>();
                advice.setSwpNo(cursor.getString(1));
                advice.setPatientName(cursor.getString(2));
                advice.setChiefComplaints(cursor.getString(3));
                advice.setTreatmentAdviced(cursor.getString(4));
                advice.setRuralProgDrs(cursor.getString(5));
                advice.setDrsComment(cursor.getString(6));
                advice.setIsFollowup(cursor.getString(7));
                advice.setFollowupType(cursor.getString(8));
                advice.setReferred(cursor.getString(9));
                advice.setIsOld(cursor.getString(10));
                advice.setTabName(cursor.getString(11));
                advice.setCreatedDate(cursor.getString(12));

                Cursor mediCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_MEDICINE_ADVICE + " " +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + advice.getSwpNo() + "' " +
                        "AND CreatedDate='" + advice.getCreatedDate() + "'", null);
                if (mediCursor.moveToFirst()) {
                    do {
                        MedicationPrescribed medicine = new MedicationPrescribed();
                        medicine.setMedicine(mediCursor.getString(1));
                        medicine.setFrequency(mediCursor.getString(2));
                        medicine.setDuration(mediCursor.getString(3));
                        medicineList.add(medicine);
                    } while (mediCursor.moveToNext());
                    advice.setMedicationPrescribed(medicineList);
                }

                Cursor systemCursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_ADVICE_SYSTEM +
                        " WHERE " + DBConstant.ADVICE_SWP_NO + "='" + advice.getSwpNo() + "' " +
                        "AND CreatedDate='" + advice.getCreatedDate() + "'", null);
                if (systemCursor.moveToFirst()) {
                    List<String> systemList = new ArrayList<>();
                    do {
                        systemList.add(systemCursor.getString(1));
                    } while (systemCursor.moveToNext());
                    advice.setSystem(systemList);
                }

            } while (cursor.moveToNext());
        }

        return advice;
    }


    //MSW_ACTIVITY TABLES
    public String insertInvestigationData(List<InvestigationData> investigationData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_VILLAGE_PADA);
        //Insert Data into Medicine Table
        for (InvestigationData data : investigationData) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.INVESTIGATIN_ID, data.getInvestigationId());
            values.put(DBConstant.INVESTIGATION_NAME, data.getInvestigationName());
            db.insert(DBConstant.TABLE_INVESTIGATION, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    //Village
    public List<InvestigationData> getInvestigationData() {
        List<InvestigationData> investigationData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DBConstant.TABLE_INVESTIGATION, new String[]{
                DBConstant.INVESTIGATIN_ID, DBConstant.INVESTIGATION_NAME}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                InvestigationData data = new InvestigationData();
                data.setInvestigationId(cursor.getString(0));
                data.setInvestigationName(cursor.getString(1));
                investigationData.add(data);
            } while (cursor.moveToNext());
        }
        return investigationData;
    }


    //MSW_ACTIVITY TABLES
    public String insertDeviceData(List<DeviceData> deviceData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_DEVICE);
        //Insert Data into Medicine Table
        for (DeviceData data : deviceData) {
            ContentValues values = new ContentValues();
            values.put(DBConstant.DEVICE_ID, data.getDeviceId());
            values.put(DBConstant.DEVICE_NAME, data.getDeviceName());
            values.put(DBConstant.SERIAL_NO, data.getSerialNumber());
            db.insert(DBConstant.TABLE_DEVICE, null, values);
        }
        db.close();
        return "SUCCESS";
    }

    //Device
    public List<DeviceData> getdeviceList(String serial) {
        List<DeviceData> devInvestigationData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + DBConstant.TABLE_DEVICE + " WHERE " + DBConstant.SERIAL_NO + "='" + serial + "'", null);
        if (cursor.moveToFirst()) {
            do {
                DeviceData data = new DeviceData();
                data.setDeviceId(cursor.getString(0));
                data.setDeviceName(cursor.getString(1));
                data.setSerialNumber(cursor.getString(2));
                devInvestigationData.add(data);
            } while (cursor.moveToNext());
        }
        return devInvestigationData;
    }


}

