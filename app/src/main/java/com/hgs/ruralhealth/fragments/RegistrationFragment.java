package com.hgs.ruralhealth.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListPopupWindow;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.activity.BaseActivity;
import com.hgs.ruralhealth.activity.InputFilterMinMax;
import com.hgs.ruralhealth.activity.photo;
import com.hgs.ruralhealth.activity.recvr;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.Device.DeviceData;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.model.register.RegistrationInputData;
import com.hgs.ruralhealth.networking.RefrofitGetClient;
import com.hgs.ruralhealth.utilities.Utililty;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rameshg on 9/2/2016.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener, photo, recvr.Mypic, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private static Context bitmpayEventListener;


    private View fragmentView;
    private EditText dateTxt, PationtFirstnameTxt, PationtLastnameTxt, ageTxt, heightTxt, weightTxt, systolicTxt, diastolicTxt, contactTxt, edt_swpno;
    private TextView tvheightunit, tvweighttunit, tvbloodunit;
    private AutoCompleteTextView tvVillages;
    private AutoCompleteTextView atto_search_swp, actsearchByName;
    private String villageName, villagePrefix, gender;
    Button submitBtn, btnFollowUp, btnNew, btnReset;
    private RadioGroup radioGroup;
    private RadioButton maleBtn, FemaleBtn;
    DBHelper helper;
    private Bitmap myBitmap;
    private LinearLayout searchLayout;
    private ImageView dateIco, iv_Village;
    ArrayAdapter<String> pationtNameAdapter, SWPnoAdapter, vliiagePadaAdapter;
    DBHelper db;
    private List<VillageOutputData> villagePadaList = new ArrayList<>();
    ArrayList<String> villagepadaListData = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    RegisterOutputData registerOutputData;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    ArrayAdapter<String> MSWArrayadapter;
    ListPopupWindow villagePopupWindow;
    private LinearLayout llVillage;
    List<RegisterOutputData> register_pationtname;
    List<String> register_pationtnameData;
    List<String> register_SWPData;
    String followUp = "New";
    private LinearLayout lin_swp;
    SharedPreferences preferences;
    String tabname = "";
    String swpNoDynamic, patientName;
    String newswp = "";
    RegisterOutputData regData;
    List<RegisterOutputData> regDataList;
    int drId;
    ImageView iv_cam;
    private static final int CAMERA_REQUEST = 1888;
    List<RegisterOutputData> registerDataList;
    EditText edt_complaint;

    private RadioGroup anc_radioGroup;
    private RadioButton ancBtn, pncBtn;
    private Spinner spin_G, spin_P, spin_L, spin_D, spin_A;
    private Spinner spin_F, spin_T, spin_N, spin_Ds;
    private EditText edt_lmp, edt_edd, edt_other, edt_sfh, edt_pa_Ut, edt_pa_weeks, edt_fhs;
    private CheckBox ck_bleeding, ck_leaking, ck_pain, ck_Uterine, ck_pa, ck_fhs, ck_rs, ck_cvs, ck_cns;
    private String ancStatus = "";
    private EditText ageYear;
    private EditText PationtMiddelnameTxt;
    LinearLayout ancView;
    Spinner spin_month, spin_year;
    ArrayList<Integer> year;
    int curruntyear, currmonth, newcurryear;
    int myyear;
    int sumyear;
    ArrayList<String> month;
    ArrayAdapter<String> adapterG, adapterL, adapterP, adapterD, adapterA, adapterF, adapterT, adapterN, adapterDa;
    LinearLayout lmps,edds,utrinlayout,Paslayout,fhslayout;

    public static void setActivityResultListener(@NonNull Context mContext) {
        RegistrationFragment.bitmpayEventListener = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.registration_fragment, container, false);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setupUI();



        helper = new DBHelper(getActivity());
        regData = new RegisterOutputData();
        myyear = Calendar.getInstance().get(Calendar.YEAR);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        }

        setDefaultColor();
        if (BaseActivity.mybit != null) {
            iv_cam.setImageBitmap(BaseActivity.mybit);
        }

        Log.d("doctor", Utililty.getDoctorId(getActivity()) + "");
        drId = Utililty.getDoctorId(getActivity());
        //  helper.getRegisterdUser(1234);*/
        villagePopupWindow = new ListPopupWindow(getActivity());
        registerOutputData = new RegisterOutputData();
        db = new DBHelper(getActivity());
        villagePadaList = db.getVillageData();
        setVillageName();
        setVillagesDateList();
        setGPLDA();
        setFTND();
        setmonth();
        setYear();

        for (int j = 0; j < year.size(); j++) {
            if (year.get(j) == myyear) {
                spin_year.setSelection(j);
            }
        }
        register_pationtname = db.getAllPatientNameSWPNo();
        Log.d("pesiont-----------", new Gson().toJson(register_pationtname));

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferences.getString("deviceNo", null) != null) {
            tabname = preferences.getString("deviceNo", null);

        }

        if (db.getallRegisterdUser().size() <= 0) {
            getRegisterBackupAPI();
        }

        iv_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        setPationt_Data();

        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curruntyear = year.get(position);
                sumyear = myyear - curruntyear;
                ageTxt.setText(sumyear + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//feb 2013
        spin_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currmonth = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        actsearchByName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                registerOutputData = new RegisterOutputData();
                String patientName = actsearchByName.getText().toString();
                String[] parts = patientName.split(" ");
                registerOutputData = db.getRegDataByUserandSwpNo(parts[0],"", "");
                atto_search_swp.setText("");
                // dateTxt.setText(registerOutputData.getInsertDate());
                dateTxt.setText(Utililty.getCurDate());
                edt_swpno.setText(registerOutputData.getSwpNo());
                PationtFirstnameTxt.setText(registerOutputData.getFirstName());
                PationtLastnameTxt.setText(registerOutputData.getLastName());
                ageYear.setText(registerOutputData.getAgeMonth()+"");
                //swpTxt.setText(registerOutputData.getSwpNo());

                heightTxt.setText(registerOutputData.getHeight());
                PationtMiddelnameTxt.setText(registerOutputData.getMiddleName());
                spin_year.setSelection(getIndex(spin_year, String.valueOf(registerOutputData.getYear())));
                spin_month.setSelection(getIndex2(spin_month, month.get(registerOutputData.getMonth())));

                sumyear = myyear - registerOutputData.getYear();
                ageTxt.setText(registerOutputData.getAge()+"");

                weightTxt.setText(registerOutputData.getWeight());
                systolicTxt.setText(registerOutputData.getBloodPressureSystolic());
                diastolicTxt.setText(registerOutputData.getBloodPressureDiastolic());
                edt_complaint.setText(registerOutputData.getComplaints());
                try {
                    iv_cam.setImageBitmap(stringToBitmap(registerOutputData.getPhoto()));
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                contactTxt.setText(registerOutputData.getContactNo());
                if (!registerOutputData.getVillage().equals(null)) {
                    tvVillages.setText(registerOutputData.getVillage());
                }

                if (registerOutputData.getGender().equalsIgnoreCase("Male")) {
                    maleBtn.setChecked(true);
                } else {
                    FemaleBtn.setChecked(true);
                }

                try {
                    if (registerOutputData.getAnc_status().equalsIgnoreCase("anc")) {
                        ancBtn.setChecked(true);
                        ancView.setVisibility(View.VISIBLE);
                    } else {
                        pncBtn.setChecked(true);
                        ancView.setVisibility(View.VISIBLE);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                // private Spinner spin_F, spin_T, spin_N, spin_Ds;*/
                try {
                    int spinnerPositionG = adapterG.getPosition(registerOutputData.getGplda_g());
                    spin_G.setSelection(spinnerPositionG);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                try {
                    int spinnerPositionP = adapterP.getPosition(registerOutputData.getGplda_p());
                    spin_P.setSelection(spinnerPositionP);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionL = adapterL.getPosition(registerOutputData.getGplda_l());
                    spin_L.setSelection(spinnerPositionL);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionD = adapterD.getPosition(registerOutputData.getGplda_d());
                    spin_D.setSelection(spinnerPositionD);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionA = adapterA.getPosition(registerOutputData.getGplda_a());
                    spin_A.setSelection(spinnerPositionA);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionF = adapterF.getPosition(registerOutputData.getFtnd_f());
                    spin_F.setSelection(spinnerPositionF);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionT = adapterT.getPosition(registerOutputData.getFtnd_t());
                    spin_T.setSelection(spinnerPositionT);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionN = adapterN.getPosition(registerOutputData.getFtnd_n());
                    spin_N.setSelection(spinnerPositionN);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                try {
                    int spinnerPositionDa = adapterD.getPosition(registerOutputData.getFtnd_d());
                    spin_Ds.setSelection(spinnerPositionDa);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }


                edt_lmp.setText(registerOutputData.getLmp());
                edt_edd.setText(registerOutputData.getEdd());


                if (registerOutputData.getBleeding().equalsIgnoreCase("1")) {
                    ck_bleeding.setChecked(true);
                }
                if (registerOutputData.getLeaking().equalsIgnoreCase("1")) {
                    ck_leaking.setChecked(true);
                }
                if (registerOutputData.getPain().equalsIgnoreCase("1")) {
                    ck_pain.setChecked(true);
                }


                edt_other.setText(registerOutputData.getOthers());

                if (registerOutputData.getUterine_size().equalsIgnoreCase("1")) {
                    ck_Uterine.setChecked(true);
                    edt_sfh.setText(registerOutputData.getSfhInCm());
                }

                if (registerOutputData.getPa().equalsIgnoreCase("1")) {
                    ck_pa.setChecked(true);
                    edt_pa_Ut.setText(registerOutputData.getPaUt());
                    edt_pa_weeks.setText(registerOutputData.getPaWeeks());
                }
                if (registerOutputData.getFhs().equalsIgnoreCase("1")) {
                    edt_fhs.setText(registerOutputData.getFhs());
                }

                if (registerOutputData.getRs().equalsIgnoreCase("1")) {
                    ck_rs.setChecked(true);
                }
                if (registerOutputData.getCvs().equalsIgnoreCase("1")) {
                    ck_cvs.setChecked(true);
                }
                if (registerOutputData.getCns().equalsIgnoreCase("1")) {
                    ck_cns.setChecked(true);
                }


            }
        });
        atto_search_swp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String swpNo = atto_search_swp.getText().toString();
                String[] parts = swpNo.split(" ");
                registerOutputData = db.getRegDataByUserandSwpNo("","", parts[0]);
                actsearchByName.setText("");
                // dateTxt.setText(registerOutputData.getInsertDate());
                dateTxt.setText(Utililty.getCurDate());
                edt_swpno.setText(registerOutputData.getSwpNo());
                PationtFirstnameTxt.setText(registerOutputData.getFirstName());
                PationtLastnameTxt.setText(registerOutputData.getLastName());
                ageYear.setText(registerOutputData.getAgeMonth());
                //swpTxt.setText(registerOutputData.getSwpNo());
             //   ageTxt.setText(registerOutputData.getAge());
                heightTxt.setText(registerOutputData.getHeight());
                weightTxt.setText(registerOutputData.getWeight());
                PationtMiddelnameTxt.setText(registerOutputData.getMiddleName());
                systolicTxt.setText(registerOutputData.getBloodPressureSystolic());
                diastolicTxt.setText(registerOutputData.getBloodPressureDiastolic());
                contactTxt.setText(registerOutputData.getContactNo());
                iv_cam.setImageBitmap(stringToBitmap(registerOutputData.getPhoto()));
                spin_year.setSelection(getIndex(spin_year, String.valueOf(registerOutputData.getYear())));
                spin_month.setSelection(getIndex2(spin_month, month.get(registerOutputData.getMonth())));
                edt_complaint.setText(registerOutputData.getComplaints());

                sumyear = myyear - registerOutputData.getYear();
                ageTxt.setText(registerOutputData.getAge()+"");

                if (!registerOutputData.getVillage().equals(null)) {
                    tvVillages.setText(registerOutputData.getVillage());
                }

                if (registerOutputData.getGender().equalsIgnoreCase("Male")) {
                    maleBtn.setChecked(true);
                } else {
                    FemaleBtn.setChecked(true);
                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.maleBtn) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });

        anc_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ancView.setVisibility(View.VISIBLE);
                if (checkedId == R.id.ancBtn) {
                    ancStatus = "anc";
                    ancView.setVisibility(View.VISIBLE);
                    lmps.setVisibility(View.VISIBLE);
                    edds.setVisibility(View.VISIBLE);

                    utrinlayout.setVisibility(View.VISIBLE);
                    Paslayout.setVisibility(View.VISIBLE);
                    fhslayout.setVisibility(View.VISIBLE);
                    edt_complaint.setVisibility(View.GONE);

                } else {
                    ancStatus = "pnc";
                    ancView.setVisibility(View.VISIBLE);
                    lmps.setVisibility(View.GONE);
                    edds.setVisibility(View.GONE);
                    edt_complaint.setVisibility(View.VISIBLE);
                    utrinlayout.setVisibility(View.GONE);
                    Paslayout.setVisibility(View.GONE);
                    fhslayout.setVisibility(View.GONE);

                    ck_Uterine.setChecked(false);
                    edt_pa_Ut.setText("");
                    edt_sfh.setText("");
                    ck_pa.setChecked(false);
                    edt_pa_Ut.setText("");
                    edt_pa_weeks.setText("");
                    ck_fhs.setChecked(false);
                    edt_fhs.setText("");
                    edt_lmp.setText("");
                    edt_edd.setText("");


                }
            }
        });


        return fragmentView;
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int getIndex2(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }
    private void setVillageName() {
        if (villagePadaList != null) {
            for (int i = 0; i < villagePadaList.size(); i++) {
                villagepadaListData.add(villagePadaList.get(i).getVillageName());
            }
        }
        vliiagePadaAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, villagepadaListData);
    }


    public void setGPLDA() {
        adapterG = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getG());
        adapterP = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getP());
        adapterL = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getL());
        adapterD = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getD());
        adapterA = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getA());

        spin_G.setAdapter(adapterG);
        spin_P.setAdapter(adapterP);
        spin_L.setAdapter(adapterL);
        spin_D.setAdapter(adapterD);
        spin_A.setAdapter(adapterA);

        spin_G.setOnItemSelectedListener(this);
        spin_P.setOnItemSelectedListener(this);
        spin_L.setOnItemSelectedListener(this);
        spin_D.setOnItemSelectedListener(this);
        spin_A.setOnItemSelectedListener(this);


    }

    public void setFTND() {
        adapterF = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getF());
        adapterT = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getT());
        adapterN = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getN());
        adapterDa = new ArrayAdapter<String>(getActivity(), R.layout.custom_drop, R.id.textView7, Utililty.getDa());

        spin_F.setAdapter(adapterF);
        spin_T.setAdapter(adapterT);
        spin_N.setAdapter(adapterN);
        spin_Ds.setAdapter(adapterDa);

        spin_F.setOnItemSelectedListener(this);
        spin_T.setOnItemSelectedListener(this);
        spin_N.setOnItemSelectedListener(this);
        spin_Ds.setOnItemSelectedListener(this);

    }

    private void unEditable() {
        PationtFirstnameTxt.setFocusable(false);
        PationtLastnameTxt.setFocusable(false);
        ageTxt.setFocusable(false);
        llVillage.setClickable(false);
        edt_swpno.setFocusable(false);
        ageYear.setFocusable(false);
        ageYear.setEnabled(false);
        maleBtn.setEnabled(false);
        FemaleBtn.setEnabled(false);
        radioGroup.setEnabled(false);
        spin_month.setEnabled(false);
        spin_year.setEnabled(false);
        lin_swp.setVisibility(View.VISIBLE);
    }

    private void editable() {
        PationtFirstnameTxt.setFocusable(true);
        PationtFirstnameTxt.setFocusableInTouchMode(true);
        PationtFirstnameTxt.setEnabled(true);

        ageYear.setFocusable(true);
        ageYear.setEnabled(true);


        PationtLastnameTxt.setFocusable(true);
        PationtLastnameTxt.setFocusableInTouchMode(true);
        PationtLastnameTxt.setEnabled(true);

        spin_month.setEnabled(true);
        spin_year.setEnabled(true);
        spin_month.setFocusableInTouchMode(true);
        spin_year.setFocusableInTouchMode(true);

        ageTxt.setFocusable(true);
        ageTxt.setFocusableInTouchMode(true);
        ageTxt.setEnabled(true);

        llVillage.setClickable(true);

        edt_swpno.setFocusable(true);
        edt_swpno.setFocusableInTouchMode(true);
        edt_swpno.setEnabled(true);

        maleBtn.setEnabled(true);
        FemaleBtn.setEnabled(true);
        radioGroup.setClickable(true);
        lin_swp.setVisibility(View.GONE);

    }

    //Village Pada
    private void getRegisterBackupAPI() {
        Log.d("register", tabname);

        Call<RegistrationInputData> villageAPICall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getRegisterVerifiedData(tabname, "true");
        villageAPICall.enqueue(new Callback<RegistrationInputData>() {
            @Override
            public void onResponse(Call<RegistrationInputData> call, Response<RegistrationInputData> response) {
                Log.d("register", new Gson().toJson(response));
                registerDataList = new ArrayList<RegisterOutputData>();
                registerDataList = response.body().getData();
                db.insertUser(registerDataList);
            }

            @Override
            public void onFailure(Call<RegistrationInputData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setPationt_Data() {
        if (register_pationtname != null) {
            register_pationtnameData = new ArrayList<>();
            register_SWPData = new ArrayList<>();

            for (int i = 0; i < register_pationtname.size(); i++) {
                register_pationtnameData.add(register_pationtname.get(i).getFirstName() + " " + register_pationtname.get(i).getLastName() + "(" + register_pationtname.get(i).getSwpNo() + ")");
                //+" " +register_pationtname.get(i).getLast_name()+"("+register_pationtname.get(i).getSwp_no()+")");
                register_SWPData.add(register_pationtname.get(i).getSwpNo() + " " + register_pationtname.get(i).getFirstName() + " " + register_pationtname.get(i).getLastName());
                // +"("+register_pationtname.get(i).getFirst_name()+" " +register_pationtname.get(i).getLast_name()+")");
            }

            actsearchByName.setThreshold(2);
            atto_search_swp.setThreshold(2);

            pationtNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, register_pationtnameData);
            SWPnoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, register_SWPData);
            actsearchByName.setAdapter(pationtNameAdapter);
            atto_search_swp.setAdapter(SWPnoAdapter);
        }
    }


    private void setVillagesDateList() {
        tvVillages.setThreshold(2);
        tvVillages.setAdapter(vliiagePadaAdapter);
        tvVillages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                villageName = tvVillages.getText().toString();
                villagePrefix = villagePadaList.get(position).getVillagePrefix();
                for (int i = 0; i < villagePadaList.size(); i++) {
                    if (villageName.equalsIgnoreCase(villagePadaList.get(i).getVillageName())) {
                        villagePrefix = villagePadaList.get(i).getVillagePrefix();
                        Log.d("getvillgeheprifix", villagePrefix);
                        Log.d("getvillgename", villageName);
                        break;
                    }
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "Registration");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        Log.d("myid", telephonyManager.getDeviceId());
    }

    private void setupUI() {
        dateTxt = (EditText) fragmentView.findViewById(R.id.dateTxt);
        dateTxt.setText(Utililty.getCurDate());

        spin_month = (Spinner) fragmentView.findViewById(R.id.spin_month);
        spin_year = (Spinner) fragmentView.findViewById(R.id.spin_year);
        lmps=(LinearLayout)fragmentView.findViewById(R.id.lmps);
        edds=(LinearLayout)fragmentView.findViewById(R.id.edds);
        utrinlayout=(LinearLayout)fragmentView.findViewById(R.id.utrinlayout);
        Paslayout=(LinearLayout)fragmentView.findViewById(R.id.Paslayout);
        fhslayout=(LinearLayout)fragmentView.findViewById(R.id.fhslayout);
        edt_complaint=(EditText)fragmentView.findViewById(R.id.edt_complaint);
        PationtFirstnameTxt = (EditText) fragmentView.findViewById(R.id.PationtFirstnameTxt);
        PationtLastnameTxt = (EditText) fragmentView.findViewById(R.id.PationtLastnameTxt);
        iv_cam = (ImageView) fragmentView.findViewById(R.id.iv_cam);
        //swpTxt = (EditText) fragmentView.findViewById(R.id.swpTxt);
        ageTxt = (EditText) fragmentView.findViewById(R.id.ageTxt);
        heightTxt = (EditText) fragmentView.findViewById(R.id.heightTxt);
        weightTxt = (EditText) fragmentView.findViewById(R.id.weightTxt);
        systolicTxt = (EditText) fragmentView.findViewById(R.id.systolicTxt);
        diastolicTxt = (EditText) fragmentView.findViewById(R.id.diastolicTxt);
        contactTxt = (EditText) fragmentView.findViewById(R.id.contactTxt);
        ageYear=(EditText)fragmentView.findViewById(R.id.ageYear);

        radioGroup = (RadioGroup) fragmentView.findViewById(R.id.radioGroup);
        PationtMiddelnameTxt = (EditText) fragmentView.findViewById(R.id.PationtMiddelnameTxt);

        maleBtn = (RadioButton) fragmentView.findViewById(R.id.maleBtn);
        FemaleBtn = (RadioButton) fragmentView.findViewById(R.id.FemaleBtn);

        submitBtn = (Button) fragmentView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        btnFollowUp = (Button) fragmentView.findViewById(R.id.btnFollowUp);
        btnFollowUp.setOnClickListener(this);

        btnNew = (Button) fragmentView.findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);

        btnReset = (Button) fragmentView.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        searchLayout = (LinearLayout) fragmentView.findViewById(R.id.searchLayout);

        dateIco = (ImageView) fragmentView.findViewById(R.id.dateIco);
        dateIco.setOnClickListener(this);

        atto_search_swp = (AutoCompleteTextView) fragmentView.findViewById(R.id.edt_search_swp);
        actsearchByName = (AutoCompleteTextView) fragmentView.findViewById(R.id.searchByName);

        llVillage = (LinearLayout) fragmentView.findViewById(R.id.llVillage);
        llVillage.setOnClickListener(this);

        iv_Village = (ImageView) fragmentView.findViewById(R.id.iv_Village);
        iv_Village.setOnClickListener(this);

        tvVillages = (AutoCompleteTextView) fragmentView.findViewById(R.id.tvVillages);
        lin_swp = (LinearLayout) fragmentView.findViewById(R.id.lin_swp);
        lin_swp.setVisibility(View.GONE);

        edt_swpno = (EditText) fragmentView.findViewById(R.id.edt_swpno);

        tvheightunit = (TextView) fragmentView.findViewById(R.id.tvheightunit);
        tvweighttunit = (TextView) fragmentView.findViewById(R.id.tvweighttunit);
        tvbloodunit = (TextView) fragmentView.findViewById(R.id.tvbloodunit);


        anc_radioGroup = (RadioGroup) fragmentView.findViewById(R.id.anc_radioGroup);
        ancBtn = (RadioButton) fragmentView.findViewById(R.id.ancBtn);
        spin_G = (Spinner) fragmentView.findViewById(R.id.spin_G);
        spin_P = (Spinner) fragmentView.findViewById(R.id.spin_P);
        spin_L = (Spinner) fragmentView.findViewById(R.id.spin_L);
        spin_D = (Spinner) fragmentView.findViewById(R.id.spin_D);
        spin_A = (Spinner) fragmentView.findViewById(R.id.spin_A);

        spin_F = (Spinner) fragmentView.findViewById(R.id.spin_F);
        spin_T = (Spinner) fragmentView.findViewById(R.id.spin_T);
        spin_N = (Spinner) fragmentView.findViewById(R.id.spin_N);
        spin_Ds = (Spinner) fragmentView.findViewById(R.id.spin_Ds);


        edt_lmp = (EditText) fragmentView.findViewById(R.id.edt_lmp);
        edt_edd = (EditText) fragmentView.findViewById(R.id.edt_edd);
        edt_other = (EditText) fragmentView.findViewById(R.id.edt_other);
        edt_sfh = (EditText) fragmentView.findViewById(R.id.edt_sfh);
        edt_pa_Ut = (EditText) fragmentView.findViewById(R.id.edt_pa_Ut);
        edt_pa_weeks = (EditText) fragmentView.findViewById(R.id.edt_pa_weeks);
        edt_fhs = (EditText) fragmentView.findViewById(R.id.edt_fhs);

        ck_bleeding = (CheckBox) fragmentView.findViewById(R.id.ck_bleeding);
        ck_leaking = (CheckBox) fragmentView.findViewById(R.id.ck_leaking);
        ck_pain = (CheckBox) fragmentView.findViewById(R.id.ck_pain);
        ck_Uterine = (CheckBox) fragmentView.findViewById(R.id.ck_Uterine);
        ck_pa = (CheckBox) fragmentView.findViewById(R.id.ck_pa);
        ck_fhs = (CheckBox) fragmentView.findViewById(R.id.ck_fhs);
        ck_rs = (CheckBox) fragmentView.findViewById(R.id.ck_rs);
        ck_cvs = (CheckBox) fragmentView.findViewById(R.id.ck_cvs);
        ck_cns = (CheckBox) fragmentView.findViewById(R.id.ck_cns);

        ck_bleeding.setOnCheckedChangeListener(this);
        ck_leaking.setOnCheckedChangeListener(this);
        ck_pain.setOnCheckedChangeListener(this);
        ck_Uterine.setOnCheckedChangeListener(this);
        ck_pa.setOnCheckedChangeListener(this);
        ck_fhs.setOnCheckedChangeListener(this);
        ck_rs.setOnCheckedChangeListener(this);
        ck_cvs.setOnCheckedChangeListener(this);
        ck_cns.setOnCheckedChangeListener(this);
        ancView = (LinearLayout) fragmentView.findViewById(R.id.ancView);
        ageYear.setFilters(new InputFilter[]{new InputFilterMinMax("0", "12")});

    }


    public void setmonth() {
         month = new ArrayList<>();
        month.add("Jan");
        month.add("Feb");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("Aug");
        month.add("Sept");
        month.add("Oct");
        month.add("Nov");
        month.add("Dec");
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, month);
        spin_month.setAdapter(monthAdapter);
    }

    public void setYear() {
        year = new ArrayList<Integer>();
        for (int i = 1900; i <= 2018; i++) {
            year.add(i);
        }

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, year);
        spin_year.setAdapter(yearAdapter);
    }


    private void setDefaultColor() {
        btnNew.setBackgroundColor(getResources().getColor(R.color.gray));
        btnNew.setTextColor(getResources().getColor(R.color.white));
    }

    public void Clear() {
        PationtFirstnameTxt.setText("");
        PationtLastnameTxt.setText("");
        edt_swpno.setText("");
        ageTxt.setText("");
        heightTxt.setText("");
        weightTxt.setText("");
        systolicTxt.setText("");
        diastolicTxt.setText("");
        contactTxt.setText("");
    }


    private void setFromAndToDate() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateTxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn: {

                Log.d("getregister", new Gson().toJson(db.getallRegisterdUser()));
                Log.d("getfollowup", new Gson().toJson(db.getallFollowupUser()));
                for (int i = 0; i < db.getallRegisterdUser().size(); i++) {
                    if (db.getallRegisterdUser().get(i).getFirstName().equalsIgnoreCase(PationtFirstnameTxt.getText().toString()) && db.getallRegisterdUser().get(i).getLastName().equalsIgnoreCase(PationtLastnameTxt.getText().toString()) && db.getallRegisterdUser().get(i).getVillage().equalsIgnoreCase(villageName)) {
                        newswp = Utililty.genrate_SWN(getActivity(), villagePrefix);
                        Log.d("user found", db.getallRegisterdUser().get(i).getFirstName() + " " + db.getallRegisterdUser().get(i).getSwpNo());

                        regDataList = new ArrayList<>();
                        swpNoDynamic = db.getallRegisterdUser().get(i).getSwpNo();
                        regData.setSwpNo(swpNoDynamic);
                        regData.setCreatedDate(db.getallRegisterdUser().get(i).getCreatedDate());
                        regData.setFirstName(PationtFirstnameTxt.getText().toString());
                        regData.setMiddleName(PationtMiddelnameTxt.getText().toString());
                        regData.setLastName(PationtLastnameTxt.getText().toString());
                        regData.setMonth(currmonth);
                        regData.setYear(curruntyear);
                        regData.setAge(ageTxt.getText().toString());
                        regData.setGender(gender);
                        regData.setVillage(villageName);
                        regData.setAgeMonth(ageYear.getText().toString());
                        regData.setHeight(heightTxt.getText().toString());
                        regData.setWeight(weightTxt.getText().toString());
                        regData.setBloodPressureSystolic(systolicTxt.getText().toString());
                        regData.setBloodPressureDiastolic(diastolicTxt.getText().toString());
                        regData.setContactNo(contactTxt.getText().toString());
                        regData.setTabNo(tabname);
                        regData.setIsOld("N");
                        regData.setHeightUnit(tvheightunit.getText().toString());
                        regData.setWeightUnit(tvweighttunit.getText().toString());
                        regData.setComplaints(edt_complaint.getText().toString());

                        regData.setBloodPressureUnit(tvbloodunit.getText().toString());
                        regData.setDoctorId(drId);
                        if (myBitmap == null) {
                            regData.setPhoto(db.getallRegisterdUser().get(i).getPhoto());
                        } else {
                            regData.setPhoto(BitMapToString(myBitmap));
                        }

                        regData.setAnc_status(ancStatus);
                        regData.setLmp(edt_lmp.getText().toString());
                        regData.setEdd(edt_edd.getText().toString());
                        regData.setSfhInCm(edt_sfh.getText().toString());
                        regData.setPaUt(edt_pa_Ut.getText().toString());
                        regData.setPaWeeks(edt_pa_weeks.getText().toString());
                        regData.setFhsPerMin(edt_fhs.getText().toString());
                        regData.setOthers(edt_other.getText().toString());


                        if (Utililty.getG().get(spin_G.getSelectedItemPosition()).equalsIgnoreCase("G")) {
                            regData.setGplda_g("0");
                        } else {
                            regData.setGplda_g(Utililty.getG().get(spin_G.getSelectedItemPosition()));
                        }

                        if (Utililty.getP().get(spin_P.getSelectedItemPosition()).equalsIgnoreCase("P")) {
                            regData.setGplda_p("0");
                        } else {
                            regData.setGplda_p(Utililty.getP().get(spin_P.getSelectedItemPosition()));
                        }

                        if (Utililty.getL().get(spin_L.getSelectedItemPosition()).equalsIgnoreCase("L")) {
                            regData.setGplda_l("0");
                        } else {
                            regData.setGplda_l(Utililty.getL().get(spin_L.getSelectedItemPosition()));
                        }

                        if (Utililty.getD().get(spin_D.getSelectedItemPosition()).equalsIgnoreCase("D")) {
                            regData.setGplda_d("0");
                        } else {
                            regData.setGplda_d(Utililty.getD().get(spin_D.getSelectedItemPosition()));
                        }

                        if (Utililty.getA().get(spin_A.getSelectedItemPosition()).equalsIgnoreCase("A")) {
                            regData.setGplda_d("0");
                        } else {
                            regData.setGplda_a(Utililty.getA().get(spin_A.getSelectedItemPosition()));
                        }

                        if (Utililty.getF().get(spin_F.getSelectedItemPosition()).equalsIgnoreCase("F")) {
                            regData.setFtnd_f("0");
                        } else {
                            regData.setFtnd_f(Utililty.getF().get(spin_F.getSelectedItemPosition()));
                        }

                        if (Utililty.getT().get(spin_T.getSelectedItemPosition()).equalsIgnoreCase("T")) {
                            regData.setFtnd_t("0");
                        } else {
                            regData.setFtnd_t(Utililty.getT().get(spin_T.getSelectedItemPosition()));
                        }

                        if (Utililty.getN().get(spin_N.getSelectedItemPosition()).equalsIgnoreCase("N")) {
                            regData.setFtnd_n("0");
                        } else {
                            regData.setFtnd_n(Utililty.getN().get(spin_N.getSelectedItemPosition()));
                        }

                        if (Utililty.getDa().get(spin_D.getSelectedItemPosition()).equalsIgnoreCase("D")) {
                            regData.setFtnd_d("0");
                        } else {
                            regData.setFtnd_d(Utililty.getDa().get(spin_Ds.getSelectedItemPosition()));
                        }


                        regDataList.add(regData);
                        helper.insertfollowupUser(regDataList);
                        sendBundleToAdvice(patientName, edt_swpno.getText().toString().trim());
                        Clear();
                        Log.i("registration", regData.toString());
                        return;
                    }

                }


                if (followUp.equalsIgnoreCase("New")) {
                    if (validateFieldsReg()) {
                        regDataList = new ArrayList<>();
                        swpNoDynamic = Utililty.genrate_SWN(getActivity(), villagePrefix);
                        List<DeviceData> data = new ArrayList<>();
                        data = db.getdeviceList(Utililty.getSystemDevice(getActivity()));


                        Log.d("printserial", swpNoDynamic);
                        regData.setSwpNo(swpNoDynamic);
                        regData.setInsertDate(dateTxt.getText().toString());
                        regData.setFirstName(PationtFirstnameTxt.getText().toString());
                        regData.setMiddleName(PationtMiddelnameTxt.getText().toString());
                        regData.setLastName(PationtLastnameTxt.getText().toString());
                        regData.setAge(ageTxt.getText().toString());
                        regData.setGender(gender);
                        regData.setAgeMonth(ageYear.getText().toString());
                        regData.setVillage(villageName);
                        regData.setMonth(currmonth);
                        regData.setYear(curruntyear);
                        regData.setHeight(heightTxt.getText().toString());
                        regData.setWeight(weightTxt.getText().toString());
                        regData.setBloodPressureSystolic(systolicTxt.getText().toString());
                        regData.setBloodPressureDiastolic(diastolicTxt.getText().toString());
                        regData.setContactNo(contactTxt.getText().toString());
                        regData.setTabNo(tabname);
                        regData.setComplaints(edt_complaint.getText().toString());
                        try {
                            regData.setPhoto(BitMapToString(myBitmap));
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                            regData.setPhoto("");
                        }

                        regData.setAnc_status(ancStatus);
                        regData.setLmp(edt_lmp.getText().toString());
                        regData.setEdd(edt_edd.getText().toString());
                        regData.setSfhInCm(edt_sfh.getText().toString());
                        regData.setPaUt(edt_pa_Ut.getText().toString());
                        regData.setPaWeeks(edt_pa_weeks.getText().toString());
                        regData.setFhsPerMin(edt_fhs.getText().toString());
                        regData.setIsOld("N");
                        regData.setHeightUnit(tvheightunit.getText().toString());
                        regData.setWeightUnit(tvweighttunit.getText().toString());
                        regData.setBloodPressureUnit(tvbloodunit.getText().toString());
                        regData.setDoctorId(drId);
                        regData.setOthers(edt_other.getText().toString());

                        if (Utililty.getG().get(spin_G.getSelectedItemPosition()).equalsIgnoreCase("G")) {
                            regData.setGplda_g("0");
                        } else {
                            regData.setGplda_g(Utililty.getG().get(spin_G.getSelectedItemPosition()));
                        }

                        if (Utililty.getP().get(spin_P.getSelectedItemPosition()).equalsIgnoreCase("P")) {
                            regData.setGplda_p("0");
                        } else {
                            regData.setGplda_p(Utililty.getP().get(spin_P.getSelectedItemPosition()));
                        }

                        if (Utililty.getL().get(spin_L.getSelectedItemPosition()).equalsIgnoreCase("L")) {
                            regData.setGplda_l("0");
                        } else {
                            regData.setGplda_l(Utililty.getL().get(spin_L.getSelectedItemPosition()));
                        }

                        if (Utililty.getD().get(spin_D.getSelectedItemPosition()).equalsIgnoreCase("D")) {
                            regData.setGplda_d("0");
                        } else {
                            regData.setGplda_d(Utililty.getD().get(spin_D.getSelectedItemPosition()));
                        }

                        if (Utililty.getA().get(spin_A.getSelectedItemPosition()).equalsIgnoreCase("A")) {
                            regData.setGplda_a("0");
                        } else {
                            regData.setGplda_a(Utililty.getA().get(spin_A.getSelectedItemPosition()));
                        }

                        if (Utililty.getF().get(spin_F.getSelectedItemPosition()).equalsIgnoreCase("F")) {
                            regData.setFtnd_f("0");
                        } else {
                            regData.setFtnd_f(Utililty.getF().get(spin_F.getSelectedItemPosition()));
                        }

                        if (Utililty.getT().get(spin_T.getSelectedItemPosition()).equalsIgnoreCase("T")) {
                            regData.setFtnd_t("0");
                        } else {
                            regData.setFtnd_t(Utililty.getT().get(spin_T.getSelectedItemPosition()));
                        }

                        if (Utililty.getN().get(spin_N.getSelectedItemPosition()).equalsIgnoreCase("N")) {
                            regData.setFtnd_n("0");
                        } else {
                            regData.setFtnd_n(Utililty.getN().get(spin_N.getSelectedItemPosition()));
                        }

                        if (Utililty.getDa().get(spin_D.getSelectedItemPosition()).equalsIgnoreCase("D")) {
                            regData.setFtnd_d("0");
                        } else {
                            regData.setFtnd_d(Utililty.getDa().get(spin_Ds.getSelectedItemPosition()));
                        }


                        if (!ck_bleeding.isChecked()) {
                            regData.setBleeding("0");
                        }
                        if (!ck_leaking.isChecked()) {
                            regData.setLeaking("0");
                        }
                        if (!ck_pain.isChecked()) {
                            regData.setPain("0");
                        }
                        if (!ck_Uterine.isChecked()) {
                            regData.setUterine_size("0");
                        }
                        if (!ck_pa.isChecked()) {
                            regData.setPa("0");
                        }
                        if (!ck_fhs.isChecked()) {
                            regData.setFhs("0");
                        }
                        if (!ck_rs.isChecked()) {
                            regData.setRs("0");
                        }
                        if (!ck_cvs.isChecked()) {
                            regData.setCvs("0");
                        }
                        if (!ck_cns.isChecked()) {
                            regData.setCns("0");
                        }


                        regDataList.add(regData);

                        helper.insertUser(regDataList);
                        Toast.makeText(getActivity(), "Submit", Toast.LENGTH_LONG).show();
                        patientName = PationtFirstnameTxt.getText().toString() + " " + PationtLastnameTxt.getText().toString();
                        sendBundleToAdvice(patientName, swpNoDynamic);
                        Clear();
                        Utililty.alertMessage(getActivity(), swpNoDynamic);
                    }
                } else if (followUp.equalsIgnoreCase("Follow")) {
                    if (validateFieldsFollow()) {
                        if (db.getRegDataByUserandSwpNo("","", edt_swpno.getText().toString()).getSwpNo() != null) {
                            if (db.getFollowUpSWPData(edt_swpno.getText().toString()).getSwpNo() == null) {
                                regDataList = new ArrayList<>();
                                swpNoDynamic = edt_swpno.getText().toString();
                                regData.setSwpNo(swpNoDynamic);
                                regData.setCreatedDate(dateTxt.getText().toString());
                                regData.setFirstName(PationtFirstnameTxt.getText().toString());
                                regData.setMiddleName(PationtMiddelnameTxt.getText().toString());
                                regData.setLastName(PationtLastnameTxt.getText().toString());
                                regData.setMonth(currmonth);
                                regData.setYear(curruntyear);
                                regData.setAge(ageTxt.getText().toString());
                                regData.setGender(gender);
                                regData.setAgeMonth(ageYear.getText().toString());
                                regData.setVillage(tvVillages.getText().toString());
                                regData.setHeight(heightTxt.getText().toString());
                                regData.setWeight(weightTxt.getText().toString());
                                regData.setBloodPressureSystolic(systolicTxt.getText().toString());
                                regData.setBloodPressureDiastolic(diastolicTxt.getText().toString());
                                regData.setContactNo(contactTxt.getText().toString());
                                regData.setTabNo(tabname);
                                regData.setComplaints(edt_complaint.getText().toString());
                                regData.setIsOld("N");
                                regData.setHeightUnit(tvheightunit.getText().toString());
                                regData.setWeightUnit(tvweighttunit.getText().toString());
                                regData.setBloodPressureUnit(tvbloodunit.getText().toString());
                                regData.setDoctorId(drId);
                                if (myBitmap == null) {
                                    regData.setPhoto(registerOutputData.getPhoto());
                                } else {
                                    regData.setPhoto(BitMapToString(myBitmap));
                                }
                                regData.setAnc_status(ancStatus);
                                regData.setLmp(edt_lmp.getText().toString());
                                regData.setEdd(edt_edd.getText().toString());
                                regData.setSfhInCm(edt_sfh.getText().toString());
                                regData.setPaUt(edt_pa_Ut.getText().toString());
                                regData.setPaWeeks(edt_pa_weeks.getText().toString());
                                regData.setFhsPerMin(edt_fhs.getText().toString());
                                regData.setOthers(edt_other.getText().toString());

                                try {
                                    if (Utililty.getG().get(spin_G.getSelectedItemPosition()).equalsIgnoreCase("G")) {
                                        regData.setGplda_g("0");
                                    } else {
                                        regData.setGplda_g(Utililty.getG().get(spin_G.getSelectedItemPosition()));
                                    }


                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }

                                try {
                                    if (Utililty.getP().get(spin_P.getSelectedItemPosition()).equalsIgnoreCase("P")) {
                                        regData.setGplda_p("0");
                                    } else {
                                        regData.setGplda_p(Utililty.getP().get(spin_P.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }
                                try {
                                    if (Utililty.getL().get(spin_L.getSelectedItemPosition()).equalsIgnoreCase("L")) {
                                        regData.setGplda_l("0");
                                    } else {
                                        regData.setGplda_l(Utililty.getL().get(spin_L.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }
                                try {
                                    if (Utililty.getD().get(spin_D.getSelectedItemPosition()).equalsIgnoreCase("D")) {
                                        regData.setGplda_d("0");
                                    } else {
                                        regData.setGplda_d(Utililty.getD().get(spin_D.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }
                                try {
                                    if (Utililty.getA().get(spin_A.getSelectedItemPosition()).equalsIgnoreCase("A")) {
                                        regData.setGplda_d("0");
                                    } else {
                                        regData.setGplda_a(Utililty.getA().get(spin_A.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }
                                try {

                                    if (Utililty.getF().get(spin_F.getSelectedItemPosition()).equalsIgnoreCase("F")) {
                                        regData.setFtnd_f("0");
                                    } else {
                                        regData.setFtnd_f(Utililty.getF().get(spin_F.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }
                                try {
                                    if (Utililty.getT().get(spin_T.getSelectedItemPosition()).equalsIgnoreCase("T")) {
                                        regData.setFtnd_t("0");
                                    } else {
                                        regData.setFtnd_t(Utililty.getT().get(spin_T.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }


                                try {
                                    if (Utililty.getN().get(spin_N.getSelectedItemPosition()).equalsIgnoreCase("N")) {
                                        regData.setFtnd_n("0");
                                    } else {
                                        regData.setFtnd_n(Utililty.getN().get(spin_N.getSelectedItemPosition()));
                                    }
                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }

                                try {
                                    if (Utililty.getDa().get(spin_D.getSelectedItemPosition()).equalsIgnoreCase("D")) {
                                        regData.setFtnd_d("0");
                                    } else {
                                        regData.setFtnd_d(Utililty.getDa().get(spin_Ds.getSelectedItemPosition()));
                                    }

                                } catch (ArrayIndexOutOfBoundsException ex) {
                                    ex.printStackTrace();
                                }

                                regDataList.add(regData);
                                helper.insertfollowupUser(regDataList);
                                Log.i("registration", regData.toString());
                                Toast.makeText(getActivity(), "Submit", Toast.LENGTH_LONG).show();
                                patientName = PationtFirstnameTxt.getText().toString() + " " + PationtLastnameTxt.getText().toString();
                                sendBundleToAdvice(patientName, edt_swpno.getText().toString().trim());
                                Clear();
                            } else {
                                Utililty.alertDialogShow(getActivity(), "Alert", "FollowUp has taken today.");
                            }
                        } else {
                            Utililty.alertDialogShow(getActivity(), "Alert", "Please register yourself first.");
                        }
                    }

                }
            }
            break;

            case R.id.btnFollowUp:
                followUp = "Follow";
                dateTxt.setText(Utililty.getCurDate());
                regData = new RegisterOutputData();
                Log.d("pointer", "followup");
                unEditable();
                searchLayout.setVisibility(View.VISIBLE);
                btnFollowUp.setBackgroundColor(getResources().getColor(R.color.gray));
                btnFollowUp.setTextColor(getResources().getColor(R.color.white));
                btnNew.setBackgroundColor(getResources().getColor(R.color.white));
                btnNew.setTextColor(getResources().getColor(R.color.bw_dark_black));
                Clear();

                break;

            case R.id.btnNew:
                followUp = "New";
                dateTxt.setText(Utililty.getCurDate());
                regData = new RegisterOutputData();
                Log.d("pointer", "New");
                editable();
                searchLayout.setVisibility(View.GONE);
                atto_search_swp.setText("");
                actsearchByName.setText("");
                btnNew.setBackgroundColor(getResources().getColor(R.color.gray));
                btnNew.setTextColor(getResources().getColor(R.color.white));
                btnFollowUp.setBackgroundColor(getResources().getColor(R.color.white));
                btnFollowUp.setTextColor(getResources().getColor(R.color.bw_dark_black));
                Clear();
                break;

            case R.id.dateIco: {
                setFromAndToDate();
                datePickerDialog.show();
            }
            break;
            case R.id.btnReset:
                Clear();
                break;

            case R.id.llVillage:
                setVillagesDateList();
                break;

            case R.id.iv_Village:
                tvVillages.setText("");
                setVillagesPopupList();
                break;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap stringToBitmap(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void setVillagesPopupList() {
        villagePopupWindow = new ListPopupWindow(getActivity());
        villagePopupWindow.setAdapter(new ArrayAdapter(getActivity(),
                R.layout.spinner_text, villagepadaListData));
        villagePopupWindow.setModal(true);
        villagePopupWindow.setAnchorView(llVillage);
        villagePopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
        villagePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                villagePopupWindow.dismiss();
                tvVillages.setText(villagepadaListData.get(position));
                villageName = villagepadaListData.get(position);
                villagePrefix = villagePadaList.get(position).getVillagePrefix();
            }
        });
        llVillage.post(new Runnable() {
            public void run() {
                villagePopupWindow.show();
            }
        });
    }

    private void sendBundleToAdvice(String patientName, String swpNo) {
        Bundle bundle = new Bundle();
        Fragment fragment = new AdviceFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bundle.putString("FirstName", patientName);
        bundle.putString("SWPNo", swpNo);
        fragment.setArguments(bundle);
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean validateFieldsReg() {
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
        } else if (isFuture) {
            Toast.makeText(getActivity(), "Please don not enter future date", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (PationtFirstnameTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter first name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (PationtLastnameTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter last name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (ageTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter age.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (gender == null) {
            Toast.makeText(getActivity(), "Please select gender.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (tvVillages.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter select village.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }


        return isValid;
    }

    private boolean validateFieldsFollow() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValid = true;
        boolean isFuture = false;
        try {
            isFuture = sdf.parse(dateTxt.getText().toString().trim()).after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (edt_swpno.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please search patient with SWPNo OR patient name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (dateTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter date.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (isFuture) {
            Toast.makeText(getActivity(), "Please don not enter future date", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (PationtFirstnameTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter first name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (PationtLastnameTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter last name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (ageTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter age.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (gender == null) {
            Toast.makeText(getActivity(), "Please select gender.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (tvVillages.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter select village.", Toast.LENGTH_SHORT).show();
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

    @Override
    public Bitmap setBitmap(Bitmap bitmap) {
        Log.d("TAG", "setBitmap: " + bitmap);
        iv_cam.setImageBitmap(bitmap);
        return bitmap;
    }

    @Override
    public Bitmap mypic(@NonNull Bitmap pic) {
        Log.d("mypic", pic.toString());
        myBitmap = pic;
        iv_cam.setImageBitmap(myBitmap);
        return pic;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity)
            ((BaseActivity) context).setOnActivityResultListener(RegistrationFragment.this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            switch (parent.getId()) {


            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.ck_bleeding:
                if (isChecked) {
                    regData.setBleeding("1");
                }
                break;
            case R.id.ck_leaking:
                if (isChecked) {
                    regData.setLeaking("1");
                }

                break;
            case R.id.ck_pain:
                if (isChecked) {
                    regData.setPain("1");
                }
                break;
            case R.id.ck_Uterine:
                if (isChecked) {
                    regData.setUterine_size("1");
                }
                break;
            case R.id.ck_pa:
                if (isChecked) {
                    regData.setPa("1");

                }
                break;
            case R.id.ck_fhs:
                if (isChecked) {
                    regData.setFhs("1");
                }
                break;
            case R.id.ck_rs:
                if (isChecked) {
                    regData.setRs("1");
                }
                break;
            case R.id.ck_cvs:
                if (isChecked) {
                    regData.setCvs("1");
                }
                break;
            case R.id.ck_cns:
                if (isChecked) {
                    regData.setCns("1");
                }
                break;

        }

    }
}
