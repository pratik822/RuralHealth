package com.hgs.ruralhealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.activity.BaseActivity;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysioSessionInputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapistInputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.utilities.Utililty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rameshg on 9/2/2016.
 */
public class PhysiotheropistFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;
    private TextView tvSetdate, tvPhysiotherepist, tvVillages, edtAge;
    private EditText edtAdvice, edtSessionTaken, edtChildcount, edtSupport, edtremark;
    private AutoCompleteTextView autoSWPNo, autoPatientName;
    private RadioGroup genderGroup, teacherGroup;
    private RadioButton radioMale, radioFemale;
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSubmit, btnPatientCheckup, btnSessionTaken;
    private ImageView ivDateIcon;
    LinearLayout llsessionPhysio, llPatientPhysio;
    private TextView edtAgeyear;
    DBHelper db;
    String painScore = "0", gender, teacherPresent;
    ArrayAdapter<String> patienttNameAdapter, swpNOAdapter;
    List<RegisterOutputData> regDataList;
    List<String> patientList;
    List<String> swpNoList;
    RegisterOutputData regData;
    private String  swp_No;

    PhysiotherapistInputData physioData;
    List<PhysiotherapistInputData> physiotherapistOutputDataList;

    PhysioSessionInputData physioSessionData;
    List<PhysioSessionInputData> physioSessionInputDataList;
    private int flag = 0;
    private String patient_OR_session = "Patient";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.physio_fragment, container, false);
        setupUI();
        setDefaultButtonColor();
        db = new DBHelper(getActivity());
        physioData = new PhysiotherapistInputData();
        physioSessionData = new PhysioSessionInputData();
        regDataList = db.getAllPatientNameSWPNo();
        physiotherapistOutputDataList = new ArrayList<>();
        physioSessionInputDataList = new ArrayList<>();
        Log.i("doctorId", Utililty.getDoctorId(getActivity()) + "");

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.maleBtn) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });

        //teacher isPreasent
        teacherGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbYes) {
                    teacherPresent = "Yes";
                } else {
                    teacherPresent = "No";
                }
            }
        });

        setPatientSWPAdapter();
        autoPatientName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String patientName = autoPatientName.getText().toString();
                String[] parts = patientName.split(" ");
                regData = db.getRegDataByUserandSwpNo(parts[0],"", "");
                if(regData.getMiddleName().isEmpty()){
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                }else{
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getMiddleName()+" " + regData.getLastName());
                }
                autoSWPNo.setText(regData.getSwpNo());
                edtAge.setText(regData.getAge());
                edtAgeyear.setText(regData.getAgeMonth());
                if (regData.getGender().equalsIgnoreCase("Male")) {
                    radioMale.setChecked(true);
                } else {
                    radioFemale.setChecked(true);
                }
                radioMale.setEnabled(false);
                radioFemale.setEnabled(false);
                tvVillages.setText(regData.getVillage());

            }
        });

        autoSWPNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String swpNO = autoSWPNo.getText().toString();
                String[] parts = swpNO.split(" ");
                regData = db.getRegDataByUserandSwpNo("","", parts[0]);
                if(regData.getMiddleName().isEmpty()){
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                }else{
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getMiddleName()+" " + regData.getLastName());
                }
                edtAgeyear.setText(regData.getAgeMonth());
                autoSWPNo.setText(regData.getSwpNo());
                edtAge.setText(regData.getAge());
                if (regData.getGender().equalsIgnoreCase("Male")) {
                    radioMale.setChecked(true);
                } else {
                    radioFemale.setChecked(true);
                }
                radioMale.setEnabled(false);
                radioFemale.setEnabled(false);
                tvVillages.setText(regData.getVillage());

            }
        });

        return fragmentView;
    }

    private void setDefaultButtonColor() {
        btnPatientCheckup.setBackgroundColor(getResources().getColor(R.color.gray));
        btnPatientCheckup.setTextColor(getResources().getColor(R.color.white));
    }

    private void setPatientSWPAdapter() {
        if (regDataList != null) {
            patientList = new ArrayList<>();
            swpNoList = new ArrayList<>();

            for (int i = 0; i < regDataList.size(); i++) {
                patientList.add(regDataList.get(i).getFirstName() + " " + regDataList.get(i).getLastName() + "(" + regDataList.get(i).getSwpNo() + ")");
                swpNoList.add(regDataList.get(i).getSwpNo() + " " + regDataList.get(i).getFirstName() + " " + regDataList.get(i).getLastName());
            }

            autoPatientName.setThreshold(2);
            autoSWPNo.setThreshold(2);

            patienttNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, patientList);
            swpNOAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, swpNoList);
            autoPatientName.setAdapter(patienttNameAdapter);
            autoSWPNo.setAdapter(swpNOAdapter);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "Physiotherapist Activity");
    }

    private void setupUI() {
        tvSetdate = (TextView) fragmentView.findViewById(R.id.dateTxt);
        tvSetdate.setText(Utililty.getCurDate());
        ivDateIcon = (ImageView) fragmentView.findViewById(R.id.ivDateIcon);
        edtAgeyear=(TextView) fragmentView.findViewById(R.id.edtAgeyear);
        ivDateIcon.setOnClickListener(this);

        autoSWPNo = (AutoCompleteTextView) fragmentView.findViewById(R.id.autoSWPNo);
        autoPatientName = (AutoCompleteTextView) fragmentView.findViewById(R.id.autoPatientName);
        tvVillages = (TextView) fragmentView.findViewById(R.id.tvVillages);

        edtAge = (TextView) fragmentView.findViewById(R.id.edtAge);
        edtAdvice = (EditText) fragmentView.findViewById(R.id.edtAdvice);
        edtSessionTaken = (EditText) fragmentView.findViewById(R.id.edtSessionTaken);
        edtChildcount = (EditText) fragmentView.findViewById(R.id.edtChildcount);
        edtChildcount.setMaxLines(4);
        edtSupport = (EditText) fragmentView.findViewById(R.id.edtSupport);
        edtremark = (EditText) fragmentView.findViewById(R.id.edtremark);

        tvPhysiotherepist = (TextView) fragmentView.findViewById(R.id.tvPhysiotherepist);
        tvPhysiotherepist.setText(Utililty.getDoctorName(getActivity()));

        btnOne = (Button) fragmentView.findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);

        btnTwo = (Button) fragmentView.findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);

        btnThree = (Button) fragmentView.findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);

        btnFour = (Button) fragmentView.findViewById(R.id.btnFour);
        btnFour.setOnClickListener(this);

        btnFive = (Button) fragmentView.findViewById(R.id.btnFive);
        btnFive.setOnClickListener(this);

        btnSubmit = (Button) fragmentView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


        genderGroup = (RadioGroup) fragmentView.findViewById(R.id.radioGroup);
        radioMale = (RadioButton) fragmentView.findViewById(R.id.maleBtn);
        radioFemale = (RadioButton) fragmentView.findViewById(R.id.FemaleBtn);

        teacherGroup = (RadioGroup) fragmentView.findViewById(R.id.radioTeacher);

        llsessionPhysio = (LinearLayout) fragmentView.findViewById(R.id.llSessionPhysio);
        llPatientPhysio = (LinearLayout) fragmentView.findViewById(R.id.llPatientPhysio);

        btnPatientCheckup = (Button) fragmentView.findViewById(R.id.btnPatientCheckup);
        btnPatientCheckup.setOnClickListener(this);

        btnSessionTaken = (Button) fragmentView.findViewById(R.id.btnSessionTaken);
        btnSessionTaken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                setBackgroundPainScore();
                btnOne.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                painScore = "1";
                break;

            case R.id.btnTwo:
                setBackgroundPainScore();
                btnTwo.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                painScore = "2";
                break;

            case R.id.btnThree:
                setBackgroundPainScore();
                btnThree.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                painScore = "3";
                break;

            case R.id.btnFour:
                setBackgroundPainScore();
                btnFour.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                painScore = "4";
                break;

            case R.id.btnFive:
                setBackgroundPainScore();
                btnFive.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                painScore = "5";
                break;

            case R.id.btnSubmit:
                //Validation Filed
                swp_No = autoSWPNo.getText().toString();
                if (patient_OR_session.equalsIgnoreCase("Patient")) {
                    if (validateFieldsPatients()) {

                        if (db.getRegDataByUserandSwpNo("","", swp_No).getSwpNo() != null) {
                            if (db.getAllPhysiotherapistData(swp_No).size() <= 0) {
                                physioData.setCurrentDate(tvSetdate.getText().toString());
                                physioData.setSwpNo(autoSWPNo.getText().toString());
                                physioData.setPatientName(autoPatientName.getText().toString());
                                physioData.setAge(edtAge.getText().toString());
                                physioData.setGender(gender);
                                physioData.setNamePhysio(tvPhysiotherepist.getText().toString());
                                physioData.setPainScore(painScore);
                                physioData.setExercieAdvice(edtAdvice.getText().toString());
                                physioData.setRemark(edtremark.getText().toString());
                                physioData.setDoctorId(Utililty.getDoctorId(getActivity()));
                                physiotherapistOutputDataList.add(physioData);
                                db.insertPhysiothrapistData(physiotherapistOutputDataList);
                               // clearAllDataFeilds();
                                Log.i("Physio Patient GET==>", db.getAllPhysiotherapistDataByIsNew() + "");
                            } else {
                                Utililty.alertDialogShow(getActivity(), "Alert", "Already Physiotheropist data has been taken today.");
                            }
                        } else {
                            Utililty.alertDialogShow(getActivity(), "Alert", "Please register yourself first.");
                        }
                    }
                } else if (patient_OR_session.equalsIgnoreCase("Session")) {
                    if(validateFieldsSession()){
                        physioSessionData.setCurrentDate(tvSetdate.getText().toString());
                        physioSessionData.setSessionTaken(edtSessionTaken.getText().toString());
                        physioSessionData.setNoOfChildrenAtt(edtChildcount.getText().toString());
                        physioSessionData.setTeacherPresent(teacherPresent);
                        physioSessionData.setSupportReq(edtSupport.getText().toString());
                        physioSessionData.setRemark(edtremark.getText().toString());
                        physioSessionData.setDoctorId(Utililty.getDoctorId(getActivity()));
                        physioSessionInputDataList.add(physioSessionData);
                        db.insertPhysioSessionData(physioSessionInputDataList);

                        if (db.getRegDataByUserandSwpNo("","", swp_No).getSwpNo() != null) {
                            if (db.getAllPhysiotherapistData(swp_No).size() <= 0) {
                                physioData.setCurrentDate(tvSetdate.getText().toString());
                                physioData.setSwpNo(autoSWPNo.getText().toString());
                                physioData.setPatientName(autoPatientName.getText().toString());
                                physioData.setAge(edtAge.getText().toString());
                                physioData.setGender(gender);
                                physioData.setNamePhysio(tvPhysiotherepist.getText().toString());
                                physioData.setPainScore(painScore);
                                physioData.setExercieAdvice(edtAdvice.getText().toString());
                                physioData.setRemark(edtremark.getText().toString());
                                physioData.setDoctorId(Utililty.getDoctorId(getActivity()));
                                physiotherapistOutputDataList.add(physioData);
                                db.insertPhysiothrapistData(physiotherapistOutputDataList);
                                clearAllDataFeilds();

                                Intent pass=new Intent(getActivity(), BaseActivity.class);
                                startActivity(pass);
                                Log.i("Physio Patient GET==>", db.getAllPhysiotherapistDataByIsNew() + "");
                            } else {
                                Utililty.alertDialogShow(getActivity(), "Alert", "Already Physiotheropist data has been taken today.");
                            }
                        } else {
                            Utililty.alertDialogShow(getActivity(), "Alert", "Please register yourself first.");
                        }
                    }

                        clearAllDataFeilds();
                        Log.i("Physio Session GET==>", db.getAllPhysioSessionDataByIsNew() + "");
                    }


                break;


            case R.id.ivDateIcon:
                Utililty.datePickDialog(getActivity());
                break;

            case R.id.btnPatientCheckup:
                patient_OR_session = "Patient";
                btnPatientCheckup.setBackgroundColor(getResources().getColor(R.color.gray));
                btnPatientCheckup.setTextColor(getResources().getColor(R.color.white));
                btnSessionTaken.setBackgroundColor(getResources().getColor(R.color.white));
                btnSessionTaken.setTextColor(getResources().getColor(R.color.bw_dark_black));
                llPatientPhysio.setVisibility(View.VISIBLE);
                llsessionPhysio.setVisibility(View.GONE);
                break;

            case R.id.btnSessionTaken:
                patient_OR_session = "Session";
                btnSessionTaken.setBackgroundColor(getResources().getColor(R.color.gray));
                btnSessionTaken.setTextColor(getResources().getColor(R.color.white));
                btnPatientCheckup.setBackgroundColor(getResources().getColor(R.color.white));
                btnPatientCheckup.setTextColor(getResources().getColor(R.color.bw_dark_black));
                llsessionPhysio.setVisibility(View.VISIBLE);
                llPatientPhysio.setVisibility(View.GONE);
                break;
        }
    }

    private void setBackgroundPainScore() {
        btnOne.setBackgroundColor(getContext().getResources().getColor(R.color.bw_light_black));
        btnTwo.setBackgroundColor(getContext().getResources().getColor(R.color.bw_light_black));
        btnThree.setBackgroundColor(getContext().getResources().getColor(R.color.bw_light_black));
        btnFour.setBackgroundColor(getContext().getResources().getColor(R.color.bw_light_black));
        btnFive.setBackgroundColor(getContext().getResources().getColor(R.color.bw_light_black));
    }

    private void clearAllDataFeilds() {
        physioData = new PhysiotherapistInputData();
        autoSWPNo.setText(null);
        autoPatientName.setText(null);
        edtAge.setText(null);
        tvPhysiotherepist.setText(null);
        edtAdvice.setText(null);
        edtSessionTaken.setText(null);
        edtChildcount.setText(null);
        edtSupport.setText(null);
        edtremark.setText(null);
        tvVillages.setText(null);
        Utililty.alertDialogShow(getActivity(), "Alert Dialog", "Data saved successfully.");

    }

    private boolean validateFieldsPatients() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValid = true;
        boolean isFuture = false;
        try {
            isFuture = sdf.parse(tvSetdate.getText().toString().trim()).after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tvSetdate.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter date.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(isFuture) {
            Toast.makeText(getActivity(), "Please don not enter future date", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(autoSWPNo.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter SWPNo.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(autoPatientName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter patient name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(edtAdvice.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter exercise/advice", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(edtSessionTaken.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter session taken", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(edtChildcount.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter no. of school childern attended", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(edtSupport.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter support required.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    private boolean validateFieldsSession() {
        boolean isValid = true;
        if(edtSessionTaken.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter session taken", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(edtChildcount.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter no. of school childern attended", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if(edtSupport.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter support required.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Utililty.logout(getActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
