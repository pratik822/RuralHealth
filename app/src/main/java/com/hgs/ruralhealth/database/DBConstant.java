package com.hgs.ruralhealth.database;

public interface DBConstant {
    public static final String TABLE_ADVICE = "Advice";
    public static final String ADVICE_SWP_NO = "SWP_no";
    public static final String PATIENT_NAME = "patientName";
    public static final String CHIEF_COMPLAINTS = "chiefComplaints";
    public static final String INVESTIGATION_ID = "investigationid";
    public static final String TREATMENT_ADVISED = "treatmentAdvised";
    public static final String RURAL_PRG_DRS = "ruralPrgDrs";
    public static final String DOCTORS_COMMENT = "doctorComment";
    public static final String IS_FOLLOWUP = "isFollowup";
    public static final String FOLLOWUP_DURATION_ID = "followuptypeId";//Duration_ID
    public static final String FOLLOWUP_TYPE_NAME = "followupTypeName";
    public static final String REFERRED_TO = "referredTo";
    public static final String ISOLD = "isOld";
    public static final String TAB_NAME = "tabName";
    public static final String CREATED_DATE = "createdDate";
    public static final String PHOTO="photo";

    /**
     * Child Table Fields FOR SYSTEM
     */
    public static final String TABLE_SYSTEM = "System";
    public static final String TABLE_ADVICE_SYSTEM = "AdviceSystem";
    public static final String TABLE_ADVICE_INVESTIGATION = "AdviceInvestigation";
    public static final String SYSTEM_ID = "systemId";
    public static final String SYSTEM_Name = "systemName";


    /**
     * Child Table Fields FOR MEDICATION_PRESCRIBED
     */
    public static final String TABLE_MEDICINE_ADVICE = "MedicinePrescribed";
    public static final String MEDICINE_ID = "medicineId";
    public static final String MEDICINE_NAME = "medicineName";
    public static final String MEDICINE_CODE = "medicineCode";
    public static final String FREQUENCY_ID = "frequencyId";
    public static final String FREQUENCY_NAME = "frequencyName";
    public static final String DAYS = "days";


    /**
     * Child Table Medicine
     * Child Table Frequency
     * Child Table Days
     */
    public static final String TABLE_MEDICINE = "Medicine";
    public static final String TABLE_FREQUENCY = "Frequency";
    public static final String TABLE_Activity = "SessionActivity";

    /**
     * Child Table Fields FOR Doctor
     */
    public static final String TABLE_RURAL_DOCTORS = "RuralDoctors";
    public static final String DOCTOR_ID = "Doctor_Id";
    public static final String DOCTOR_NAME = "Doctor_Name";


    /**
     * Child Table Fields FOR DURATION
     */
    public static final String TABLE_DURATION = "Duration";
    public static final String DURATION_ID = "Duration_Id";
    public static final String DURATION_TYPE = "Duration_Type";

    public static final String TABLE_NAME = "registration";
    public static final String TABLE_FOLLOWUP = "followup";
    public static final String SWP_NO = "swp_no";
    public static final String INSERT_DATE = "insert_date";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String VILLAGE = "village";
    public static final String HEIGHT = "height";
    public static final String HEIGHT_UNIT = "heightUnit";
    public static final String WEIGHT = "weight";
    public static final String WEIGHT_UNIT = "weightUnit";
    public static final String BLOOD_PRESSUREONE = "blood_pressureone";
    public static final String BLOOD_PRESSURETWO = "blood_pressuretwo";
    public static final String BLOOD_PRESSURE_UNIT = "bloodPressureUnit";
    public static final String CONTACT_NO = "contact_no";

    public static final String ANC_STATUS = "anc_status";
    public static final String G = "gplda_g";
    public static final String P = "gplda_p";
    public static final String L = "gplda_l";
    public static final String DS = "gplda_d";
    public static final String A = "gplda_a";
    public static final String F = "ftnd_f";
    public static final String T = "ftnd_t";
    public static final String N = "ftnd_n";
    public static final String D = "ftnd_d";
    public static final String LMP = "lmp";
    public static final String EDD = "edd";
    public static final String BLEEDING = "bleeding";
    public static final String LEAKING = "leaking";
    public static final String PAIN = "pain";
    public static final String OTHER = "others";
    public static final String UTERINE_SIZE = "uterine_size";
    public static final String SFH = "sfhInCm";
    public static final String PA = "pa";
    public static final String PA_UT = "paUt";
    public static final String PA_WEEKS = "paWeeks";
    public static final String FHS = "fhs";
    public static final String RS = "rs";
    public static final String CVS = "cvs";
    public static final String CNS = "cns";


    public static final String TAB_NO = "tab_no";
    public static final String IS_NEW = "is_new";
    public static final String DOCTORS_ID = "doctorId";

    /*table MSW Activity*/
    public static final String TABLE_MSW_ACTIVITY = "Msw_activity";
    public static final String CURRENTS_DATE = "insert_date";
    public static final String MSW_NAME = "Msw_Name";
    public static final String VILLAGE_ATTENTED = "Village_Attented";
    public static final String ACTIVITY_ONE = "Activity_One";
    public static final String ACTIVITY_TWO = "Activity_Two";
    public static final String ACTIVITY_THREE = "Activity_Three";
    public static final String ACTIVITY_FOUR = "Activity_Four";
    public static final String ACTIVITY_FIVE = "Activity_Five";
    public static final String ACTIVITY_SIX = "Activity_Six";
    public static final String OTHER_OPTION = "Other_Option";
    public static final String CHILDREN_COUNT = "Children_Count";
    public static final String IS_TEACHER_PRESENT = "Teacher_Present";
    public static final String IS_WATER_FILTER_PRESENT = "Water_Filter_Present";
    public static final String IS_HAND_WASH_PRESENT = "Hand_Wash_Presnet";
    public static final String SUPPORT_REQUIRED = "Support_Required";
    public static final String REMARK = "Remark";
    public static final String HOUSE_VISIT = "HouseVisit";


    //MSW DIstribution

    public static final String TABLE_MSW_DISTRIBUTION = "MSW_Distribution";
    public static final String MSW_INSERT_DATE = "insert_date";
    public static final String MSW_VILLAGES = "Village_Attented";
    public static final String MSW_ISOLD = "isOld";
    public static final String MSW_TAB_NAME = "tabName";

    //MSW Food

    public static final String TABLE_MSW_FOOD = "MSW_food";
    public static final String MSW_ITEM_NAME = "itemName";
    public static final String MSW_QTY = "qty";
    public static final String MSW_UNIT = "Unit";




    /**
     * child table MSW Activity
     */

    public static final String TABLE_MSW_NAME = "Msw_Names";
    public static final String MSW_NAME_ID = "Msw_Name_Id";
    public static final String MSW_NAMES = "Msw_Name";

    /**
     * child table MSW Activity
     */
    public static final String TABLE_VILLAGE_ATTENDENT = "Village_Attendent";
    public static final String MSW_VILLAGE_ATTENDENT_ID = "Village_Attendnent_Id";
    public static final String MSW_VILLAGE_ATTENDENT_NAME = "Village_Attendnent_Name";


    /*table MSW diffrent activity*/
    public static final String TABLE_activity = "activity";
    public static final String ACT_INSERT_DATE = "insert_date";
    public static final String ACT_MSW_NAME = "Msw_Name";
    public static final String ACT_MSW_VILLAGE = "Village_Attented";
    public static final String ACT_ACTIVITY = "activities";

    /**
     * VILLAGE PADA
     */
    public static final String TABLE_VILLAGE_PADA = "Village_Pada";
    public static final String MSW_VILLAGE_ID = "villageId";
    public static final String MSW_VILLAGE_NAME = "villageName";
    public static final String MSW_VILLAGE_PREFIX = "villagePrefix";

    /**
     * Ophthalmology Table
     */
    public static final String TABLE_OPHTHALMOLOGY = "Ophthalmolgy";
    public static final String CURRENT_DATE = "createdDate";
    public static final String SWP_NO_OPHTHA = "SWP_no";
    public static final String PATIENT_NAME_OPHTHA = "PatientName";
    public static final String AGE_OPHTHA = "Age";
    public static final String GENDER_OPHTHA = "Gender";
    public static final String VILLAGE_OPHTHA = "Village";
    public static final String REMARK_OPHTHA = "remark";


    public static final String TABLE_MORBIDITY = "Morbidity";
    public static final String MORBIDITY = "Morbidity";
    public static final String Opthal_ISOLD = "isOld";
    public static final String  OPTHAL_TAB_NAME = "tabName";

    /**
     * Physiotherapist Activity Table
     */
    public static final String TABLE_PHYSIOTHERAPIST = "Physiotherapist";
    public static final String CURRENT_DATE_PHYSIO = "currentDate";
    public static final String SWP_NO_PHYSIO = "swpNo";
    public static final String PATIENT_NAME_PHYSIO = "patientName";
    public static final String AGE_PHYSIO = "age";
    public static final String GENDER_PHYSIO = "gender";
    public static final String NAME_PHYSIO = "namePhysio";
    public static final String PAIN_SCORE = "painScore";
    public static final String EXERCIE_ADVICE = "exercieAdvice";
    public static final String REMARK_PHYSIO = "remark";

    /**
     * Physiotherapist Session Table
     */
    public static final String TABLE_PHYSIO_SESSION = "PhysiotherapistSession";
    public static final String SESSION_TAKEN = "sessionTaken";
    public static final String NO_OF_CHILDREN_ATT = "noOfChildrenAtt";
    public static final String TEACHER_PRESENT = "teacherPresent";
    public static final String SUPPORT_REQ = "supportReq";

    /**
     * User Table
     */
    public static final String TABLE_USER = "User";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "loginId";
    public static final String USER_PASSWORD = "password";
    public static final String USER_DEVICE = "device";


    //MSW Product Data

    public static final String TABLE_PRODUCTS = "Product";
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_CODE = "productCode";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_DESCRIPTION = "productDescription";
    public static final String UNIT = "unit";
    public static final String QUANTITY = "qty";


    //Syptoms
    public static final String TABLE_SYMPTOMPS = "Symptomps";
    public static final String SYMPTOM_ID = "symptomId";
    public static final String SYMPTOM_NAME = "symptomName";


    //INVESTIGATION
    public static final String TABLE_INVESTIGATION = "investigation";
    public static final String INVESTIGATIN_ID = "investigationId";
    public static final String INVESTIGATION_NAME = "investigationName";

    //DEVICE
    public static final String TABLE_DEVICE = "device";
    public static final String DEVICE_ID = "deviceId";
    public static final String DEVICE_NAME = "deviceName";
    public static final String SERIAL_NO = "serialNumber";




}
