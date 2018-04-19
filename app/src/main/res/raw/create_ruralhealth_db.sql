-- ########################### MASTER TABLE - START ################################
CREATE TABLE System (
    systemId INTEGER PRIMARY KEY AUTOINCREMENT,
	systemName VARCHAR NOT NULL
);

CREATE TABLE Medicine (
    medicineId INTEGER PRIMARY KEY AUTOINCREMENT,
	medicineName VARCHAR NOT NULL,
	medicineCode VARCHAR NOT NULL
);

CREATE TABLE Frequency (
    frequencyId INTEGER PRIMARY KEY AUTOINCREMENT,
	frequencyName VARCHAR NOT NULL
);

CREATE TABLE RuralDoctors (
    Doctor_Id INTEGER PRIMARY KEY AUTOINCREMENT,
	Doctor_Name VARCHAR NOT NULL
);

CREATE TABLE Duration (
    Duration_Id INTEGER PRIMARY KEY AUTOINCREMENT,
	Duration_Type VARCHAR NOT NULL
);

CREATE TABLE Product(
        productId VARCHAR,
        productCode VARCHAR,
        productName VARCHAR,
        productDescription VARCHAR,
        unit VARCHAR,
        qty VARCHAR

);

CREATE TABLE Village_Pada (
	villageId VARCHAR NOT NULL,
	villageName VARCHAR NOT NULL,
	villagePrefix VARCHAR NOT NULL
);

CREATE TABLE Symptomps (
    symptomId VARCHAR,
	symptomName VARCHAR NOT NULL
);
-- ########################### MASTER TABLE - END ################################


CREATE TABLE Advice (
    advice_id INTEGER PRIMARY KEY AUTOINCREMENT,
	SWP_no VARCHAR NOT NULL,
	patientName VARCHAR NOT NULL,
	chiefComplaints VARCHAR ,
	investigationreport VARCHAR,
	treatmentAdvised VARCHAR ,
	ruralPrgDrs VARCHAR,
	doctorComment VARCHAR,
	isFollowup VARCHAR,
	followupTypeName VARCHAR,
	followupdate VARCHAR,
	referredTo VARCHAR,
	isOld VARCHAR NOT NULL,
	tabName VARCHAR,
	createdDate VARCHAR NOT NULL,
	doctorId INTEGER

);

CREATE TABLE MedicinePrescribed (
	SWP_no VARCHAR NOT NULL,
	medicineName VARCHAR NULL,
	frequencyName VARCHAR NULL,
	days VARCHAR NULL,
	createdDate VARCHAR NOT NULL
);

CREATE TABLE AdviceSystem (
	SWP_no VARCHAR NOT NULL,
	systemName VARCHAR NOT NULL,
	createdDate VARCHAR NOT NULL
);

CREATE TABLE AdviceInvestigation (
	SWP_no VARCHAR NOT NULL,
	Investigationid VARCHAR NOT NULL,
	investigationName VARCHAR NOT NULL,
	createdDate VARCHAR NOT NULL
);

CREATE TABLE SessionActivity (
	activityId VARCHAR NOT NULL,
	activityName VARCHAR NOT NULL
);



-- ########################### REGISTRATION_TABLE ################################

CREATE TABLE registration (
    swp_no VARCHAR,
    insert_date VARCHAR,
    first_name VARCHAR,
    midel_name VARCHAR,
    last_name VARCHAR,
    month VARCHAR,
    year VARCHAR,
    age VARCHAR,
    gender VARCHAR,
    photo VARCHAR,
    village VARCHAR,
    height VARCHAR,
    heightUnit VARCHAR,
    weight VARCHAR,
    weightUnit VARCHAR,
    blood_pressureone VARCHAR,
    blood_pressuretwo VARCHAR,
    bloodPressureUnit VARCHAR,
    contact_no VARCHAR,
    anc_status VARCHAR,
    gplda_g VARCHAR,
    gplda_p VARCHAR,
    gplda_l VARCHAR,
    gplda_d VARCHAR,
    gplda_a VARCHAR,
    ftnd_f VARCHAR,
    ftnd_t VARCHAR,
    ftnd_n VARCHAR,
    ftnd_d VARCHAR,
    lmp VARCHAR,
    edd VARCHAR,
    bleeding VARCHAR,
    leaking VARCHAR,
    pain VARCHAR,
    others VARCHAR,
    uterine_size VARCHAR,
    sfhInCm VARCHAR,
    pa VARCHAR,
    paUt VARCHAR,
    paWeeks VARCHAR,
    fhs VARCHAR,
    fhsPerMin VARCHAR,
    rs VARCHAR,
    cvs VARCHAR,
    cns VARCHAR,
    tab_no VARCHAR,
    is_new VARCHAR,
    doctorId VARCHAR,
    agemonth VARCHAR,
    complaint VARCHAR
);
-- ########################### REGISTRATION_TABLE_END ################################



-- ########################### FOLLOWUP_TABLE ################################
CREATE TABLE followup (
    swp_no VARCHAR,
    insert_date VARCHAR,
    first_name VARCHAR,
    midel_name VARCHAR,
    last_name VARCHAR,
    month VARCHAR,
    year VARCHAR,
    age VARCHAR,
    gender VARCHAR,
    photo VARCHAR,
    village VARCHAR,
    height VARCHAR,
    heightUnit VARCHAR,
    weight VARCHAR,
    weightUnit VARCHAR,
    blood_pressureone VARCHAR,
    blood_pressuretwo VARCHAR,
    bloodPressureUnit VARCHAR,
    contact_no VARCHAR,
    anc_status VARCHAR,
        gplda_g VARCHAR,
        gplda_p VARCHAR,
        gplda_l VARCHAR,
        gplda_d VARCHAR,
        gplda_a VARCHAR,
        ftnd_f VARCHAR,
        ftnd_t VARCHAR,
        ftnd_n VARCHAR,
        ftnd_d VARCHAR,
        lmp VARCHAR,
        edd VARCHAR,
        bleeding VARCHAR,
        leaking VARCHAR,
        pain VARCHAR,
        others VARCHAR,
        uterine_size VARCHAR,
        sfhInCm VARCHAR,
        pa VARCHAR,
        paUt VARCHAR,
        paWeeks VARCHAR,
        fhs VARCHAR,
        fhsPerMin VARCHAR,
        rs VARCHAR,
        cvs VARCHAR,
        cns VARCHAR,
    tab_no VARCHAR,
    is_new VARCHAR,
    doctorId VARCHAR,
    agemonth VARCHAR,
    complaint VARCHAR
);
-- ########################### FOLLOWUP_TABLE ################################



-- ########################### MSW_ACTIVITY ################################

CREATE TABLE Msw_activity(
        insert_date VARCHAR,
        Msw_Name VARCHAR,
        Village_Attented VARCHAR,
        Other_Option VARCHAR,
        Children_Count VARCHAR,
        Teacher_Present VARCHAR,
        Water_Filter_Present VARCHAR,
        Hand_Wash_Presnet VARCHAR,
        Support_Required VARCHAR,
        Remark VARCHAR,
        HouseVisit VARCHAR,
        isOld VARCHAR NOT NULL,
        tabName VARCHAR,
        doctorId VARCHAR
);

-- ########################### MSW_Distribution ################################

CREATE TABLE MSW_Distribution(
        insert_date VARCHAR,
        Msw_Name VARCHAR,
        Village_Attented VARCHAR,
        isOld VARCHAR NOT NULL,
        tabName VARCHAR,
        doctorId VARCHAR
);


-- ########################### MSW_food ################################

CREATE TABLE MSW_food(
        insert_date VARCHAR,
        Msw_Name VARCHAR,
        itemName VARCHAR,
        qty VARCHAR,
        Unit VARCHAR

);


CREATE TABLE activity(
  insert_date VARCHAR,
  Msw_Name VARCHAR,
  Village_Attented VARCHAR,
  activities VARCHAR
);



CREATE TABLE Msw_Names (
	Msw_Name_Id VARCHAR NOT NULL,
	Msw_Name VARCHAR NOT NULL
);

CREATE TABLE Village_Attendent (
	Village_Attendnent_Id VARCHAR NOT NULL,
	Village_Attendnent_Name VARCHAR NOT NULL
);



-- ########################### MSW_ACTIVITY_END################################


-- ########################### Ophthalmology Table Started ################################
CREATE TABLE Ophthalmolgy(
        SWP_no VARCHAR NOT NULL,
        PatientName VARCHAR,
        Age VARCHAR,
        Gender VARCHAR,
        Village VARCHAR,
        isOld VARCHAR NOT NULL,
        tabName  VARCHAR,
        createdDate VARCHAR NOT NULL,
        doctorId INTEGER,
        remark VARCHAR
);

CREATE TABLE Morbidity(
        SWP_no VARCHAR NOT NULL,
        Morbidity VARCHAR NOT NULL,
        createdDate VARCHAR NOT NULL
);
-- ########################### Ophthalmology Table End ################################
-- ########################### Phsiotherapist Table Started ###########################
CREATE TABLE Physiotherapist(
        currentDate VARCHAR,
        swpNo VARCHAR,
        patientName VARCHAR,
        age VARCHAR,
        gender VARCHAR,
        namePhysio VARCHAR,
        painScore VARCHAR,
        exercieAdvice VARCHAR,
        remark VARCHAR,
        isOld VARCHAR NOT NULL,
        doctorId INTEGER
);

CREATE TABLE PhysiotherapistSession(
        currentDate VARCHAR,
        sessionTaken VARCHAR,
        noOfChildrenAtt VARCHAR,
        teacherPresent VARCHAR,
        supportReq VARCHAR,
        remark VARCHAR,
        isOld VARCHAR NOT NULL,
        doctorId INTEGER
);


CREATE TABLE investigation(
        investigationId VARCHAR,
        investigationName VARCHAR

);


-- ########################### Phsiotherapist Table End ################################
-- ########################### User Table Started ######################################
CREATE TABLE User(
        userId INTEGER,
        loginId VARCHAR,
        password VARCHAR,
        device VARCHAR

);
-- ########################### User Table End ######################################



CREATE TABLE device(
        deviceId INTEGER,
        deviceName VARCHAR,
        serialNumber VARCHAR

);
-- ########################### User Table End ######################################




