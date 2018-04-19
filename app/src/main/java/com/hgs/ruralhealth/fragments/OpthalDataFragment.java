package com.hgs.ruralhealth.fragments;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.adapters.MorbidityAdapter;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.masterdata.SymptompsOutputData;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.model.ophthal.OphthalInputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.utilities.Utililty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rameshg on 9/2/2016.
 */
public class OpthalDataFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;
    private TextView dateTxt, tvVillages, edtAge, edtRemark;
    private AutoCompleteTextView autoSWPNo, autoPatientName;
    private Button btnSubmitOpthaData;
    private ImageView ivVillageSpinner;
    private LinearLayout llVillage;
    private RadioGroup genderGroup;
    private RadioButton radioMale, radioFemale;
    private ImageView ivDateIcon;
    private GridView gridMorbidity;

    DBHelper db;
    private List<VillageOutputData> villagePadaList = new ArrayList<>();
    ArrayList<String> villagepadaListData = new ArrayList<>();
    ListPopupWindow showRoomListPopupWindow;
    List<OphthalInputData> ophthaDataList;
    OphthalInputData ophthaDataSet;
    List<OphthalInputData>ophthalOutputDataList;

    ArrayAdapter<String> patienttNameAdapter, swpNOAdapter, villageNameAdapter;
    List<RegisterOutputData> regDataList;
    List<String> patientList;
    private TextView edtAgeYear;
    List<String> swpNoList;
    RegisterOutputData regData;
    ArrayList morbidityList = new ArrayList();
    MorbidityAdapter morbidityAdapter;
    private List<SymptompsOutputData> listMorbidity = new ArrayList<>();

    String gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.opthal_data_fragment, container, false);
        setupUI();
        db = new DBHelper(getActivity());
        ophthaDataSet = new OphthalInputData();
        ophthaDataList = new ArrayList<>();
        regDataList = db.getAllPatientNameSWPNo();
        listMorbidity = db.geSymptomsData();
        ophthalOutputDataList=new ArrayList<>();
        //setVillageNameAdapter();
        setPatientSWPAdapter();
        autoPatientName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String patientName = autoPatientName.getText().toString();
                String[] parts = patientName.split(" ");
                regData = db.getRegDataByUserandSwpNo(parts[0],"", "");
                if (!regData.getMiddleName().isEmpty()) {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getMiddleName() + " " + regData.getLastName());
                } else {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                }

                autoSWPNo.setText(regData.getSwpNo());
                edtAgeYear.setText(regData.getAgeMonth());
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

        autoSWPNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String swpNO = autoSWPNo.getText().toString();
                String[] parts = swpNO.split(" ");
                regData = db.getRegDataByUserandSwpNo("","", parts[0]);
                if (!regData.getMiddleName().isEmpty()) {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getMiddleName() + " " + regData.getLastName());
                } else {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                }

                edtAgeYear.setText(regData.getAgeMonth());
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

        //Select Gender
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

        //Morbidity
        morbidityAdapter = new MorbidityAdapter(getActivity(), listMorbidity);
        gridMorbidity.setAdapter(morbidityAdapter);
        morbidityAdapter.setOnItemClickListener(new MorbidityAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    morbidityList.add(listMorbidity.get(position).getSymptomName());
                    Log.i("switch YES==>", listMorbidity.get(position).getSymptomName());
                } else {
                    morbidityList.remove(listMorbidity.get(position).getSymptomName());
                    Log.i("switch NO==>", listMorbidity.get(position).getSymptomName());
                }
            }
        });
        return fragmentView;
    }

    /*private void setVillageNameAdapter() {
        villagePadaList = db.getVillageData();
        if (villagePadaList != null) {
            for (int i = 0; i < villagePadaList.size(); i++) {
                villagepadaListData.add(villagePadaList.get(i).getVillageName());
            }
        }
        autoVillages.setThreshold(2);
        villageNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, villagepadaListData);
        autoVillages.setAdapter(villageNameAdapter);
    }*/


    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "Opthal Data");
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

    public void setupUI() {
        dateTxt = (TextView) fragmentView.findViewById(R.id.dateTxt);
        dateTxt.setText(Utililty.getCurDate());
        ivDateIcon = (ImageView) fragmentView.findViewById(R.id.ivDateIcon);
        edtAgeYear=(TextView)fragmentView.findViewById(R.id.edtAgeYear);
        ivDateIcon.setOnClickListener(this);

        autoSWPNo = (AutoCompleteTextView) fragmentView.findViewById(R.id.autoSWPNo);
        autoPatientName = (AutoCompleteTextView) fragmentView.findViewById(R.id.autoPatientName);
        edtAge = (TextView) fragmentView.findViewById(R.id.edtAge);

        /*llVillage = (LinearLayout) fragmentView.findViewById(R.id.llVillage);
        llVillage.setOnClickListener(this);

        ivVillageSpinner = (ImageView) fragmentView.findViewById(R.id.ivVillageSpinner);
        ivVillageSpinner.setOnClickListener(this);*/

        tvVillages = (TextView) fragmentView.findViewById(R.id.tvVillages);


        btnSubmitOpthaData = (Button) fragmentView.findViewById(R.id.btnSubmit);
        btnSubmitOpthaData.setOnClickListener(this);

        genderGroup = (RadioGroup) fragmentView.findViewById(R.id.radioGroup);
        radioMale = (RadioButton) fragmentView.findViewById(R.id.maleBtn);
        radioFemale = (RadioButton) fragmentView.findViewById(R.id.FemaleBtn);


        gridMorbidity = (GridView) fragmentView.findViewById(R.id.gridMorbidity);

        edtRemark = (EditText) fragmentView.findViewById(R.id.edtRemark);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.llVillage:
                setVillagesDateList();
                break;

            case R.id.ivVillageSpinner:
                setVillagesDateList();
                break;*/

            case R.id.ivDateIcon:
                Utililty.datePickDialog(getActivity());
                break;

            case R.id.btnSubmit:
                String swp_No = autoSWPNo.getText().toString();
                if(validateFields()){
                    if (db.getRegDataByUserandSwpNo("","", swp_No).getSwpNo() != null) {
                        if (db.getAllOphthalmolgyData(swp_No).size() <= 0) {
                            insertOphthaDataIntoDB();
                        } else {
                            Utililty.alertDialogShow(getActivity(), "Alert", "Already ophthalogy data has been taken today" );
                        }
                    } else {
                        Utililty.alertDialogShow(getActivity(), "Alert", "Please register yourself first." );
                        //Toast.makeText(getActivity(), "Please Register Yourself", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
        }
    }

    private void insertOphthaDataIntoDB() {
        ophthaDataSet.setCreatedDate(dateTxt.getText().toString());
        ophthaDataSet.setSwpNo(autoSWPNo.getText().toString());
        ophthaDataSet.setPatientName(autoPatientName.getText().toString());
        ophthaDataSet.setAge(edtAge.getText().toString());
        ophthaDataSet.setGender(gender);
        ophthaDataSet.setVillage(tvVillages.getText().toString());
        ophthaDataSet.setMorbidity(morbidityList);
        ophthaDataSet.setTabName(Utililty.getCurruntDevice(getActivity()));
        ophthaDataSet.setDoctorId(Utililty.getDoctorId(getActivity()));
        ophthaDataSet.setRemark(edtRemark.getText().toString());
        Log.i("OphthDat SAVE==>", ophthaDataSet.toString());
        ophthalOutputDataList.add(ophthaDataSet);
        db.insertOphthalmolgy(ophthalOutputDataList);
        for (OphthalInputData data : db.getAllOphthalmolgyData(null)) {
            Log.i("Ophthal GET==>", new Gson().toJson(data));
        }
        Utililty.alertDialogMSWShowAndDismiss(getActivity(),"Sucsess","Record Inserted Suscsessfully");
    }


    private boolean validateFields(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValid = true;
        boolean isFuture = false;
        try {
            isFuture = sdf.parse(dateTxt.getText().toString().trim()).after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dateTxt.getText().toString().trim().isEmpty()) {
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
