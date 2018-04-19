package com.hgs.ruralhealth.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.customView.MultiSelectionSpinner;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.fragments.dialog.PrintDialogFragment;
import com.hgs.ruralhealth.model.db.AdviceInputData;
import com.hgs.ruralhealth.model.db.MedicationPrescribed;
import com.hgs.ruralhealth.model.db.SpinnerData;
import com.hgs.ruralhealth.model.masterdata.FrequencieOutputData;
import com.hgs.ruralhealth.model.masterdata.MedicineOutputData;
import com.hgs.ruralhealth.model.masterdata.SystemOutputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.utilities.Utililty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rameshg on 9/2/2016.
 */
public class AdviceFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, AbsListView.MultiChoiceModeListener {

    private static final String TAG = AdviceFragment.class.getName();
    private View fragmentView;
    private EditText spSystem;
    private AutoCompleteTextView autoSWPNo, autoPatientName;
    private Button btnSubmit, btnPrint;
    private LinearLayout llMedicine1, llMedicine2, llMedicine3, llMedicine4, llMedicine5, llMedicine6, llMedicine7, llFrequency1, llFrequency2, llFrequency3,
            llFrequency4, llFrequency5, llFrequency6, llFrequency7, llDays1, llDays2, llDays3, llDays4, llDays5, llDays6, llDays7, llDuration;
    private TextView tvSetdate, tvDoctorName, tvDuration, tvAddMoreMedicine, tvFrequency1, tvFrequency2, tvFrequency3, tvFrequency4, tvFrequency5, tvFrequency6,
            tvFrequency7, tvDays1, tvDays2, tvDays3, tvDays4, tvDays5, tvDays6, tvDays7, tvRemoveMedicine, tvDurationDate;
    private ImageView ivDateIcon, ivMedicine1, ivMedicine2, ivMedicine3, ivMedicine4, ivMedicine5, ivMedicine6, ivMedicine7, ivFrequency1, ivFrequency2, ivFrequency3,
            ivFrequency4, ivFrequency5, ivFrequency6, ivFrequency7, ivDays1, ivDays2, ivDays3, ivDays4, ivDays5, ivDays6, ivDays7, ivDuration;
    private EditText edtChief_Complaint, edtTreamentAdviced, edtDoctorComment, edtReferredTo, edtMedicine1, edtMedicine2, edtMedicine3, edtMedicine4,
            edtMedicine5, edtMedicine6, edtMedicine7;
    private EditText spinvestigation;
    private Switch swFollowUp;
    ListPopupWindow medicinePopupWindow;
    int mypos = 0;

    private String medicine, frequency, days;

    private List<MedicineOutputData> spMedicineData = new ArrayList<>();
    ArrayList<String> medicineListData = new ArrayList<>();
    ArrayList<String> selectedmedicineListData = new ArrayList<>();
    private List<FrequencieOutputData> spFrequencyData = new ArrayList<>();
    ArrayList<String> frequencyListData = new ArrayList<>();

    private String[] medicineFrequency = {"0 - 0 - 0", "1 - 1 - 1", "0 - 0 - 1", "0 - 1 - 1", "1 - 0 - 0", "1 - 1 - 0", "1 - 0 - 1", "0 - 1 - 0"};
    private String[] spDaysData = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

    private List<SpinnerData> spDoctorNameData = new ArrayList<>();
    ArrayList<String> doctorNameListData = new ArrayList<>();


    private List<String> spFollowDuartionData = new ArrayList<>();
    ArrayList<String> followDuartionListData = new ArrayList<>();

    private List<SystemOutputData> spAdviceSystemData = new ArrayList<>();
    ArrayList<String> adviceSystemListData = new ArrayList<>();
    ListView lv;

    AdviceInputData data = new AdviceInputData();
    List<AdviceInputData> adviceDataList = new ArrayList<>();
    List<MedicationPrescribed> medicineList = new ArrayList<>();

    MedicationPrescribed medicine1 = new MedicationPrescribed();
    MedicationPrescribed medicine2 = new MedicationPrescribed();
    MedicationPrescribed medicine3 = new MedicationPrescribed();
    MedicationPrescribed medicine4 = new MedicationPrescribed();
    MedicationPrescribed medicine5 = new MedicationPrescribed();
    MedicationPrescribed medicine6 = new MedicationPrescribed();
    MedicationPrescribed medicine7 = new MedicationPrescribed();
    String FLname, swpNumber;
    DBHelper db;
    ArrayAdapter<String> patienttNameAdapter, swpNOAdapter;
    List<RegisterOutputData> regDataList;
    List<String> patientList;
    List<String> swpNoList;
    RegisterOutputData regData;

    SharedPreferences preferences;
    int medicineCount = 3;
    PrintDialogFragment printDialogFragment;
    ArrayList<String> investigationList = new ArrayList<>();
    ArrayList<String> investigationListPost = new ArrayList<>();
    ArrayList<String> systemListPost = new ArrayList<>();
    EditText edt_investigationreport;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            FLname = bundle.getString("FirstName");
            swpNumber = bundle.getString("SWPNo");
            Log.i("FLname==>", FLname + " ==>  " + swpNumber);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.advice_fragment, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        db = new DBHelper(getActivity());
        regDataList = db.getAllPatientNameSWPNo();
        selectedmedicineListData.add("one");
        selectedmedicineListData.add("two");
        selectedmedicineListData.add("three");
        selectedmedicineListData.add("four");
        selectedmedicineListData.add("five");
        selectedmedicineListData.add("six");
        selectedmedicineListData.add("seven");
        Log.i("adviceDataList==>Get", db.getAdviceAll().toString());
        Log.i("Reg Advuce Size==>", regDataList.size() + "");
        Log.i("doctorID", Utililty.getDoctorId(getActivity()) + "");
        spFollowDuartionData.add("Daily");
        spFollowDuartionData.add("Weekly");
        spFollowDuartionData.add("Fortnight");
        spFollowDuartionData.add("Monthly");
        setupUI();
        getMasterDataFromDB();
        setPatientSWPAdapter();

        for (int i = 0; i < db.getInvestigationData().size(); i++) {
            investigationList.add(db.getInvestigationData().get(i).getInvestigationName());

        }

        autoPatientName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("mypppppppp", regDataList.get(position).getFirstName());
                regData = db.getRegDataByUserandSwpNo(regDataList.get(position).getFirstName(), regDataList.get(position).getMiddleName(), "");
                if (regData.getMiddleName().isEmpty()) {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getMiddleName() + " " + regData.getLastName());
                } else {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                }

                autoSWPNo.setText(regData.getSwpNo());
            }
        });


        autoSWPNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String swpNO = autoSWPNo.getText().toString();
                String[] parts = swpNO.split(" ");
                regData = db.getRegDataByUserandSwpNo("", "", parts[0]);

                if (regData.getMiddleName() != null && regData.getMiddleName().isEmpty()) {
                    autoPatientName.setText(regData.getFirstName() + " " + regData.getLastName());
                } else {

                    autoPatientName.setText(regData.getFirstName() + " " + regData.getMiddleName() + " " + regData.getLastName());
                }

                autoSWPNo.setText(regData.getSwpNo());
            }
        });
        spinvestigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupInvestigationSpiner();
            }
        });

        spSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spSystemData();
            }
        });



  /*      spinvestigation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data.setInvestigationId(Integer.parseInt(db.getInvestigationData().get(position).getInvestigationId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "Advice");
    }


    private void spSystemData() {
        if (spAdviceSystemData != null) {
            for (int i = 0; i < spAdviceSystemData.size(); i++) {
                adviceSystemListData.add(spAdviceSystemData.get(i).getSystemName());
            }

        }

        ArrayAdapter<String> investigationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, adviceSystemListData);
        //  spinvestigation.setAdapter(investigationAdapter);
        LayoutInflater infator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = infator.inflate(R.layout.layout, null);
        final ListView list = (ListView) view.findViewById(R.id.listView);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(investigationAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checked = list.getCheckedItemPositions();
                Log.d("yyyyyyyy", checked.get(position) + "");

                    if(!checked.get(position)==true){
                        systemListPost.remove(adviceSystemListData.get(position));
                    }else{
                        systemListPost.add(adviceSystemListData.get(position));
                    }



            }
        });
        AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        bldr.setTitle("Select");
        bldr.setView(view);

        bldr.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txtSystem = "";
                        for (int i = 0; i < systemListPost.size(); i++) {
                            txtSystem += systemListPost.get(i) + ", ";
                        }
                        spSystem.setText(txtSystem);
                    }
                });
        bldr.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog dlg = bldr.create();
        dlg.show();
        //  spSystem.setItems(adviceSystemListData);
        //spSystem.setSelection(new int[]{2, 6});
        //  spSystem.setListener(this);
    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(getActivity(), strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (validateFields()) {
                    swpNumber = autoSWPNo.getText().toString().trim();
                    Log.i("SWP_No", swpNumber);

                    if (db.getRegDataByUserandSwpNo("", "", swpNumber).getSwpNo() != null) {
                        if (db.getSWPAdviceData(swpNumber).getSwpNo() == null) {
                            data.setSwpNo(autoSWPNo.getText().toString());
                            data.setPatientName(autoPatientName.getText().toString());
                            data.setChiefComplaints(edtChief_Complaint.getText().toString());
                            data.setSystem(systemListPost);
                            data.setInvestigationreport(edt_investigationreport.getText().toString());
                            data.setInvestigationIds(investigationListPost);
                            data.setTreatmentAdviced(edtTreamentAdviced.getText().toString());
                            data.setRuralProgDrs(tvDoctorName.getText().toString());
                            data.setDrsComment(edtDoctorComment.getText().toString());
                            data.setReferred(edtReferredTo.getText().toString());
                            data.setFollowupDate(tvDurationDate.getText().toString());
                            data.setIsOld("N");
                            data.setTabName(preferences.getString("deviceNo", null));
                            data.setCreatedDate(tvSetdate.getText().toString());
                            data.setDoctorId(Utililty.getDoctorId(getActivity()));

                            if (!edtMedicine1.getText().toString().isEmpty() || !tvFrequency1.getText().toString().isEmpty() || !tvDays1.getText().toString().isEmpty()) {
                                medicine1.setMedicine(edtMedicine1.getText().toString());
                                medicine1.setFrequency(tvFrequency1.getText().toString());
                                medicine1.setDuration(tvDays1.getText().toString());
                                medicineList.add(medicine1);
                            }
                            if (!edtMedicine2.getText().toString().isEmpty() || !tvFrequency2.getText().toString().isEmpty() || !tvDays2.getText().toString().isEmpty()) {
                                medicine2.setMedicine(edtMedicine2.getText().toString());
                                medicine2.setFrequency(tvFrequency2.getText().toString());
                                medicine2.setDuration(tvDays2.getText().toString());
                                medicineList.add(medicine2);
                            }
                            if (!edtMedicine3.getText().toString().isEmpty() || !tvFrequency3.getText().toString().isEmpty() || !tvDays3.getText().toString().isEmpty()) {
                                medicine3.setMedicine(edtMedicine3.getText().toString());
                                medicine3.setFrequency(tvFrequency3.getText().toString());
                                medicine3.setDuration(tvDays3.getText().toString());
                                medicineList.add(medicine3);
                            }

                            if (fragmentView.findViewById(R.id.llAddMore4).getVisibility() == View.VISIBLE) {
                                if (!edtMedicine4.getText().toString().isEmpty() || !tvFrequency4.getText().toString().isEmpty() || !tvDays4.getText().toString().isEmpty()) {
                                    medicine4.setMedicine(edtMedicine4.getText().toString());
                                    medicine4.setFrequency(tvFrequency4.getText().toString());
                                    medicine4.setDuration(tvDays4.getText().toString());
                                    medicineList.add(medicine4);
                                }
                            }
                            if (fragmentView.findViewById(R.id.llAddMore5).getVisibility() == View.VISIBLE) {
                                if (!edtMedicine5.getText().toString().isEmpty() || !tvFrequency5.getText().toString().isEmpty() || !tvDays5.getText().toString().isEmpty()) {
                                    medicine5.setMedicine(edtMedicine5.getText().toString());
                                    medicine5.setFrequency(tvFrequency5.getText().toString());
                                    medicine5.setDuration(tvDays5.getText().toString());
                                    medicineList.add(medicine5);
                                }
                            }
                            if (fragmentView.findViewById(R.id.llAddMore6).getVisibility() == View.VISIBLE) {
                                if (!edtMedicine6.getText().toString().isEmpty() || !tvFrequency6.getText().toString().isEmpty() || !tvDays6.getText().toString().isEmpty()) {
                                    medicine6.setMedicine(edtMedicine6.getText().toString());
                                    medicine6.setFrequency(tvFrequency6.getText().toString());
                                    medicine6.setDuration(tvDays6.getText().toString());
                                    medicineList.add(medicine6);
                                }
                            }
                            if (fragmentView.findViewById(R.id.llAddMore7).getVisibility() == View.VISIBLE) {
                                if (!edtMedicine7.getText().toString().isEmpty() || !tvFrequency7.getText().toString().isEmpty() || !tvDays7.getText().toString().isEmpty()) {
                                    medicine7.setMedicine(edtMedicine7.getText().toString());
                                    medicine7.setFrequency(tvFrequency7.getText().toString());
                                    medicine7.setDuration(tvDays7.getText().toString());
                                    medicineList.add(medicine7);
                                }
                            }
                            data.setMedicationPrescribed(medicineList);
                            adviceDataList.add(data);
                            Log.i("adviceDataList==>Save", new Gson().toJson(adviceDataList));
                            db.saveAdviceInput(adviceDataList);

                            clearAllDataFeilds();
                        } else {
                            Utililty.alertDialogShow(getActivity(), "Alert", "Already advice has been taken today");
                        }
                    } else {
                        Utililty.alertDialogShow(getActivity(), "Alert", "Please register yourself first.");
                    }
                }
                break;

            case R.id.btnPrint:
                printDialogFragment = new PrintDialogFragment();
                printDialogFragment.show(getActivity().getFragmentManager(), AdviceFragment.TAG);
                //printDocument();
                break;

            case R.id.ivDateIcon:
                Utililty.datePickDialog(getActivity());
                break;

            case R.id.tvDurationDate:
                Utililty.datePickDialog(getActivity());
                tvDurationDate.setText(Utililty.datePickDialog(getActivity()));
                break;

            //Medicine 1
            case R.id.ivMedicine1:
                medicine = "llMedicine1";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency1:
                frequency = "Frequency1";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays1:
                days = "llDays1";
                setDaysList(days);
                break;

            //Medicine 2
            case R.id.ivMedicine2:
                medicine = "llMedicine2";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency2:
                frequency = "Frequency2";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays2:
                days = "llDays2";
                setDaysList(days);
                break;

            //Medicine 3
            case R.id.ivMedicine3:
                medicine = "llMedicine3";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency3:
                frequency = "Frequency3";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays3:
                days = "llDays3";
                setDaysList(days);
                break;

            //Medicine 4
            case R.id.ivMedicine4:
                medicine = "llMedicine4";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency4:
                frequency = "Frequency4";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays4:
                days = "llDays4";
                setDaysList(days);
                break;

            //Medicine 5
            case R.id.ivMedicine5:
                medicine = "llMedicine5";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency5:
                frequency = "Frequency5";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays5:
                days = "llDays5";
                setDaysList(days);
                break;

            //Medicine 6
            case R.id.ivMedicine6:
                medicine = "llMedicine6";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency6:
                frequency = "Frequency6";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays6:
                days = "llDays6";
                setDaysList(days);
                break;

            //Medicine 7
            case R.id.ivMedicine7:
                medicine = "llMedicine7";
                setMedicineList(medicine);
                break;

            case R.id.ivFrequency7:
                frequency = "Frequency7";
                setFrequnecyList(frequency);
                break;

            case R.id.ivDays7:
                days = "llDays7";
                setDaysList(days);
                break;

            //Duration
            case R.id.llDuration:
                setDurationList();
                break;

            case R.id.ivDuration:
                setDurationList();
                break;

            //Add Medicine
            case R.id.tvAddMoreMedicine:
                addMoreMedicine();
                break;

            case R.id.tvRemoveMedicine:
                removeMedicine();
                break;
        }
    }

    private void removeMedicine() {
        if (fragmentView.findViewById(R.id.llAddMore7).getVisibility() == View.VISIBLE) {
            fragmentView.findViewById(R.id.llAddMore7).setVisibility(View.GONE);
        } else if (fragmentView.findViewById(R.id.llAddMore6).getVisibility() == View.VISIBLE) {
            fragmentView.findViewById(R.id.llAddMore6).setVisibility(View.GONE);
        } else if (fragmentView.findViewById(R.id.llAddMore5).getVisibility() == View.VISIBLE) {
            fragmentView.findViewById(R.id.llAddMore5).setVisibility(View.GONE);
        } else if (fragmentView.findViewById(R.id.llAddMore4).getVisibility() == View.VISIBLE) {
            fragmentView.findViewById(R.id.llAddMore4).setVisibility(View.GONE);
            tvRemoveMedicine.setVisibility(View.GONE);
        }
        medicineCount--;
    }

    private void addMoreMedicine() {
        if (fragmentView.findViewById(R.id.llAddMore4).getVisibility() == View.GONE) {
            fragmentView.findViewById(R.id.llAddMore4).setVisibility(View.VISIBLE);
            tvRemoveMedicine.setVisibility(View.VISIBLE);
        } else if (fragmentView.findViewById(R.id.llAddMore5).getVisibility() == View.GONE) {
            fragmentView.findViewById(R.id.llAddMore5).setVisibility(View.VISIBLE);
        } else if (fragmentView.findViewById(R.id.llAddMore6).getVisibility() == View.GONE) {
            fragmentView.findViewById(R.id.llAddMore6).setVisibility(View.VISIBLE);
        } else if (fragmentView.findViewById(R.id.llAddMore7).getVisibility() == View.GONE) {
            fragmentView.findViewById(R.id.llAddMore7).setVisibility(View.VISIBLE);
        }
        if (medicineCount >= 7) {
            Toast.makeText(getActivity(), "Cannot add more than 8 medicines", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Set the medicine list drop dwon
     *
     * @param medicine
     */

    private void setMedicineList(String medicine) {
        medicinePopupWindow = new ListPopupWindow(getActivity());
        medicinePopupWindow.setAdapter(new ArrayAdapter(getActivity(),
                R.layout.spinner_text, medicineListData));
        medicinePopupWindow.setModal(true);
        medicinePopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);

        if (medicine.equalsIgnoreCase("llMedicine1")) {
            medicinePopupWindow.setAnchorView(llMedicine1);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    edtMedicine1.setText(medicineListData.get(position));
                    selectedmedicineListData.add(medicineListData.get(position));

                }
            });

            llMedicine1.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (medicine.equalsIgnoreCase("llMedicine2")) {
            medicinePopupWindow.setAnchorView(llMedicine2);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    if (!edtMedicine1.getText().toString().contains("Medicine one")) {

                        edtMedicine2.setText(medicineListData.get(position));
                    } else {
                        Toast.makeText(getActivity(), "Please select medicine one", Toast.LENGTH_LONG).show();
                    }


                  /*  if(((edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine3.getText().toString().equalsIgnoreCase(edtMedicine4.getText().toString())||edtMedicine5.getText().toString().equalsIgnoreCase(edtMedicine6.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine2.setText("Medicine two");
                    }*/

              /*      if(((!edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine3.getText().toString().equalsIgnoreCase(edtMedicine4.getText().toString())||edtMedicine5.getText().toString().equalsIgnoreCase(edtMedicine6.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine2.setText("");
                    }*/


                }
            });

            llMedicine2.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (medicine.equalsIgnoreCase("llMedicine3")) {
            medicinePopupWindow.setAnchorView(llMedicine3);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    if (!edtMedicine2.getText().toString().contains("Medicine two")) {
                        edtMedicine3.setText(medicineListData.get(position));
                    } else {
                        Toast.makeText(getActivity(), "Please select medicine two", Toast.LENGTH_LONG).show();
                    }

                   /* if(((edtMedicine3.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine4.getText().toString())||edtMedicine5.getText().toString().equalsIgnoreCase(edtMedicine6.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine3.setText("Medicine three");
                    }*/

                }
            });

            llMedicine3.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (medicine.equalsIgnoreCase("llMedicine4")) {
            medicinePopupWindow.setAnchorView(llMedicine4);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    if (!edtMedicine3.getText().toString().contains("Medicine three")) {
                        edtMedicine4.setText(medicineListData.get(position));
                    } else {
                        Toast.makeText(getActivity(), "Please select medicine three", Toast.LENGTH_LONG).show();
                    }

                  /*  if(( (edtMedicine4.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine3.getText().toString())||edtMedicine4.getText().toString().equalsIgnoreCase(edtMedicine5.getText().toString())||edtMedicine5.getText().toString().equalsIgnoreCase(edtMedicine6.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine4.setText("Medicine four");
                    }*/

                }
            });

            llMedicine4.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (medicine.equalsIgnoreCase("llMedicine5")) {
            medicinePopupWindow.setAnchorView(llMedicine5);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();

                    if (!edtMedicine4.getText().toString().contains("Medicine five")) {
                        edtMedicine5.setText(medicineListData.get(position));
                        selectedmedicineListData.add(medicineListData.get(position));
                    } else {
                        Toast.makeText(getActivity(), "Please select medicine five", Toast.LENGTH_LONG).show();
                    }

                   /* if(((edtMedicine5.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine3.getText().toString())||edtMedicine4.getText().toString().equalsIgnoreCase(edtMedicine6.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                      */
                    // }


                 /*   if(edtMedicine4.getText().toString().equalsIgnoreCase(edtMedicine5.getText().toString())){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine5.setText("");
                    }else{
                        edtMedicine5.setText(medicineListData.get(position));
                    }*/

                }
            });

            llMedicine5.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (medicine.equalsIgnoreCase("llMedicine6")) {
            medicinePopupWindow.setAnchorView(llMedicine6);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();

                    if (!edtMedicine5.getText().toString().contains("Medicine five")) {
                        edtMedicine6.setText(medicineListData.get(position));
                        selectedmedicineListData.add(medicineListData.get(position));
                    } else {
                        Toast.makeText(getActivity(), "Please select medicine five", Toast.LENGTH_LONG).show();
                    }


                 /*   edtMedicine6.setText(medicineListData.get(position));
                    if(((edtMedicine6.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine3.getText().toString())||edtMedicine4.getText().toString().equalsIgnoreCase(edtMedicine5.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine6.setText("Medicine six");
                    }*/

                }
            });

            llMedicine6.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (medicine.equalsIgnoreCase("llMedicine7")) {
            medicinePopupWindow.setAnchorView(llMedicine7);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();

                    if (!edtMedicine6.getText().toString().contains("Medicine six")) {
                        edtMedicine7.setText(medicineListData.get(position));
                        selectedmedicineListData.add(medicineListData.get(position));
                    } else {
                        Toast.makeText(getActivity(), "Please select medicine six", Toast.LENGTH_LONG).show();
                    }


                   /* edtMedicine7.setText(medicineListData.get(position));
                    if(( (!edtMedicine7.getText().toString().equalsIgnoreCase(edtMedicine1.getText().toString())||edtMedicine2.getText().toString().equalsIgnoreCase(edtMedicine3.getText().toString())||edtMedicine4.getText().toString().equalsIgnoreCase(edtMedicine7.getText().toString())))){
                        Toast.makeText(getActivity(),"Already selected",Toast.LENGTH_LONG).show();
                        edtMedicine7.setText("Medicine seven");
                    }*/
                }
            });

            llMedicine7.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        }
    }

    /**
     * Set the frequency list drop down
     *
     * @param frequency
     */
    private void setFrequnecyList(String frequency) {
        medicinePopupWindow = new ListPopupWindow(getActivity());
        medicinePopupWindow.setAdapter(new ArrayAdapter(getActivity(),
                R.layout.spinner_text, frequencyListData));
        medicinePopupWindow.setModal(true);
        medicinePopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);

        if (frequency.equalsIgnoreCase("Frequency1")) {
            medicinePopupWindow.setAnchorView(llFrequency1);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency1.setText(frequencyListData.get(position));
                }
            });

            llFrequency1.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (frequency.equalsIgnoreCase("Frequency2")) {
            medicinePopupWindow.setAnchorView(llFrequency2);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency2.setText(frequencyListData.get(position));
                }
            });

            llFrequency2.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (frequency.equalsIgnoreCase("Frequency3")) {
            medicinePopupWindow.setAnchorView(llFrequency3);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency3.setText(frequencyListData.get(position));
                }
            });

            llFrequency3.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (frequency.equalsIgnoreCase("Frequency4")) {
            medicinePopupWindow.setAnchorView(llFrequency4);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency4.setText(frequencyListData.get(position));
                }
            });

            llFrequency4.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (frequency.equalsIgnoreCase("Frequency5")) {
            medicinePopupWindow.setAnchorView(llFrequency5);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency5.setText(frequencyListData.get(position));
                }
            });

            llFrequency5.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (frequency.equalsIgnoreCase("Frequency6")) {
            medicinePopupWindow.setAnchorView(llFrequency6);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency6.setText(frequencyListData.get(position));
                }
            });

            llFrequency6.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (frequency.equalsIgnoreCase("Frequency7")) {
            medicinePopupWindow.setAnchorView(llFrequency7);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvFrequency7.setText(frequencyListData.get(position));
                }
            });

            llFrequency7.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        }
    }

    /**
     * Set the days list drop dwon
     *
     * @param days
     */
    private void setDaysList(String days) {
        medicinePopupWindow = new ListPopupWindow(getActivity());
        medicinePopupWindow.setAdapter(new ArrayAdapter(getActivity(),
                R.layout.spinner_text, spDaysData));
        medicinePopupWindow.setModal(true);
        medicinePopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);

        if (days.equalsIgnoreCase("llDays1")) {
            medicinePopupWindow.setAnchorView(llDays1);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays1.setText(spDaysData[position]);

                }
            });

            llDays1.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });

        } else if (days.equalsIgnoreCase("llDays2")) {
            medicinePopupWindow.setAnchorView(llDays2);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays2.setText(spDaysData[position]);
                }
            });
            llDays2.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });

        } else if (days.equalsIgnoreCase("llDays3")) {
            medicinePopupWindow.setAnchorView(llDays3);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays3.setText(spDaysData[position]);
                }
            });
            llDays3.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });
        } else if (days.equalsIgnoreCase("llDays4")) {
            medicinePopupWindow.setAnchorView(llDays4);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays4.setText(spDaysData[position]);

                }
            });

            llDays4.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });

        } else if (days.equalsIgnoreCase("llDays5")) {
            medicinePopupWindow.setAnchorView(llDays5);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays5.setText(spDaysData[position]);

                }
            });

            llDays5.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });

        } else if (days.equalsIgnoreCase("llDays6")) {
            medicinePopupWindow.setAnchorView(llDays6);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays6.setText(spDaysData[position]);

                }
            });

            llDays6.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });

        } else if (days.equalsIgnoreCase("llDays7")) {
            medicinePopupWindow.setAnchorView(llDays7);
            medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    medicinePopupWindow.dismiss();
                    tvDays7.setText(spDaysData[position]);

                }
            });

            llDays7.post(new Runnable() {
                public void run() {
                    medicinePopupWindow.show();
                }
            });

        }
    }

    private void setDurationList() {
        medicinePopupWindow = new ListPopupWindow(getActivity());
        medicinePopupWindow.setAdapter(new ArrayAdapter(getActivity(),
                R.layout.spinner_text, followDuartionListData));
        medicinePopupWindow.setModal(true);
        medicinePopupWindow.setAnchorView(llDuration);
        medicinePopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);

        medicinePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicinePopupWindow.dismiss();
                tvDuration.setText(followDuartionListData.get(position));
                data.setFollowupType(followDuartionListData.get(position));
            }
        });
        llDuration.post(new Runnable() {
            public void run() {
                medicinePopupWindow.show();
            }
        });
    }

    private void getMasterDataFromDB() {
        spMedicineData = db.getMedicineData();
        spFrequencyData = db.getFrequencyData();
        Log.d("iiiiiiii", new Gson().toJson(spFrequencyData));
        spDoctorNameData = db.getProgrammDoctorsData();
        //  spFollowDuartionData = db.getFrequencyData();


        spAdviceSystemData = db.getAdviceSystemData();
        if (spMedicineData != null) {
            for (int i = 0; i < spMedicineData.size(); i++) {
                medicineListData.add(spMedicineData.get(i).getMedicineName());
            }
        }
        if (spFrequencyData != null) {
            for (int i = 0; i < spFrequencyData.size(); i++) {
                frequencyListData.add(spFrequencyData.get(i).getFrequencyName());
            }
        }

        if (spDoctorNameData != null) {
            for (int i = 0; i < spDoctorNameData.size(); i++) {
                doctorNameListData.add(spDoctorNameData.get(i).getName());
            }
        }
        if (spFollowDuartionData != null) {
            for (int i = 0; i < spFollowDuartionData.size(); i++) {
                followDuartionListData.add(spFollowDuartionData.get(i));
            }
        }
    }

    private void setPatientSWPAdapter() {
        if (regDataList != null) {
            patientList = new ArrayList<>();
            swpNoList = new ArrayList<>();

            for (int i = 0; i < regDataList.size(); i++) {
                patientList.add(regDataList.get(i).getFirstName() + " " + regDataList.get(i).getLastName() + "(" + regDataList.get(i).getSwpNo() + ")");
                //+" " +register_pationtname.get(i).getLast_name()+"("+register_pationtname.get(i).getSwp_no()+")");
                swpNoList.add(regDataList.get(i).getSwpNo() + " " + regDataList.get(i).getFirstName() + " " + regDataList.get(i).getLastName());
                // +"("+register_pationtname.get(i).getFirst_name()+" " +register_pationtname.get(i).getLast_name()+")");
            }

            autoPatientName.setThreshold(2);
            autoSWPNo.setThreshold(2);

            patienttNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, patientList);
            swpNOAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, swpNoList);
            autoPatientName.setAdapter(patienttNameAdapter);
            autoSWPNo.setAdapter(swpNOAdapter);
        }
    }

    private void clearAllDataFeilds() {
        data = new AdviceInputData();
        autoSWPNo.setText(null);
        autoPatientName.setText(null);
        edtChief_Complaint.setText(null);
        adviceSystemListData = new ArrayList<>();
        spSystem.setSelection(0);
        //spSystem.setSelection(new int[]{});
        edtTreamentAdviced.setText(null);
        medicineList = new ArrayList<>();
        edtMedicine1.setText(null);
        edtMedicine2.setText(null);
        edtMedicine3.setText(null);
        edtMedicine4.setText(null);
        edtMedicine5.setText(null);
        edtMedicine6.setText(null);
        edtMedicine7.setText(null);

        tvFrequency1.setText(null);
        tvFrequency2.setText(null);
        tvFrequency3.setText(null);
        tvFrequency4.setText(null);
        tvFrequency5.setText(null);
        tvFrequency6.setText(null);
        tvFrequency7.setText(null);

        tvDays1.setText(null);
        tvDays2.setText(null);
        tvDays3.setText(null);
        tvDays4.setText(null);
        tvDays5.setText(null);
        tvDays6.setText(null);
        tvDays7.setText(null);

        tvDoctorName.setText(Utililty.getDoctorName(getActivity()));
        edtDoctorComment.setText(null);
        tvDuration.setText(null);
        edtReferredTo.setText(null);
        swFollowUp.setChecked(false);
        Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
    }


    private void setupUI() {
        tvSetdate = (TextView) fragmentView.findViewById(R.id.dateTxt);
        tvSetdate.setText(Utililty.getCurDate());
        ivDateIcon = (ImageView) fragmentView.findViewById(R.id.ivDateIcon);
        edt_investigationreport = (EditText) fragmentView.findViewById(R.id.edt_investigationreport);
        ivDateIcon.setOnClickListener(this);

        tvDurationDate = (TextView) fragmentView.findViewById(R.id.tvDurationDate);
        tvDurationDate.setOnClickListener(this);

        spSystem = (EditText) fragmentView.findViewById(R.id.edt_system);

        autoSWPNo = (AutoCompleteTextView) fragmentView.findViewById(R.id.autoSWPNo);
        autoSWPNo.setText(swpNumber);

        autoPatientName = (AutoCompleteTextView) fragmentView.findViewById(R.id.autoPatientName);
        autoPatientName.setText(FLname);

        edtChief_Complaint = (EditText) fragmentView.findViewById(R.id.edtChief_Complaint);
        edtTreamentAdviced = (EditText) fragmentView.findViewById(R.id.edtTreamentAdviced);
        edtDoctorComment = (EditText) fragmentView.findViewById(R.id.edtDoctorComment);
        edtReferredTo = (EditText) fragmentView.findViewById(R.id.edtReferredTo);

        // ++++++++++++++++ Medicine == 1
        llMedicine1 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine1);
        edtMedicine1 = (EditText) fragmentView.findViewById(R.id.edtMedicine1);
        ivMedicine1 = (ImageView) fragmentView.findViewById(R.id.ivMedicine1);
        ivMedicine1.setOnClickListener(this);

        llFrequency1 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency1);
        tvFrequency1 = (TextView) fragmentView.findViewById(R.id.tvFrequency1);
        ivFrequency1 = (ImageView) fragmentView.findViewById(R.id.ivFrequency1);
        ivFrequency1.setOnClickListener(this);

        llDays1 = (LinearLayout) fragmentView.findViewById(R.id.llDays1);
        tvDays1 = (TextView) fragmentView.findViewById(R.id.tvDays1);
        ivDays1 = (ImageView) fragmentView.findViewById(R.id.ivDays1);
        ivDays1.setOnClickListener(this);

        // ++++++++++++++++ Medicine == 2
        llMedicine2 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine2);
        edtMedicine2 = (EditText) fragmentView.findViewById(R.id.edtMedicine2);
        ivMedicine2 = (ImageView) fragmentView.findViewById(R.id.ivMedicine2);
        ivMedicine2.setOnClickListener(this);

        llFrequency2 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency2);
        tvFrequency2 = (TextView) fragmentView.findViewById(R.id.tvFrequency2);
        ivFrequency2 = (ImageView) fragmentView.findViewById(R.id.ivFrequency2);
        ivFrequency2.setOnClickListener(this);

        llDays2 = (LinearLayout) fragmentView.findViewById(R.id.llDays2);
        tvDays2 = (TextView) fragmentView.findViewById(R.id.tvDays2);
        ivDays2 = (ImageView) fragmentView.findViewById(R.id.ivDays2);
        ivDays2.setOnClickListener(this);

        // ++++++++++++++++ Medicine == 3
        llMedicine3 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine3);
        edtMedicine3 = (EditText) fragmentView.findViewById(R.id.edtMedicine3);
        ivMedicine3 = (ImageView) fragmentView.findViewById(R.id.ivMedicine3);
        ivMedicine3.setOnClickListener(this);

        llFrequency3 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency3);
        tvFrequency3 = (TextView) fragmentView.findViewById(R.id.tvFrequency3);
        ivFrequency3 = (ImageView) fragmentView.findViewById(R.id.ivFrequency3);
        ivFrequency3.setOnClickListener(this);

        llDays3 = (LinearLayout) fragmentView.findViewById(R.id.llDays3);
        tvDays3 = (TextView) fragmentView.findViewById(R.id.tvDays3);
        ivDays3 = (ImageView) fragmentView.findViewById(R.id.ivDays3);
        ivDays3.setOnClickListener(this);

        // ++++++++++++++++ Medicine == 4
        llMedicine4 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine4);
        edtMedicine4 = (EditText) fragmentView.findViewById(R.id.edtMedicine4);
        ivMedicine4 = (ImageView) fragmentView.findViewById(R.id.ivMedicine4);
        ivMedicine4.setOnClickListener(this);

        llFrequency4 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency4);
        tvFrequency4 = (TextView) fragmentView.findViewById(R.id.tvFrequency4);
        ivFrequency4 = (ImageView) fragmentView.findViewById(R.id.ivFrequency4);
        ivFrequency4.setOnClickListener(this);

        llDays4 = (LinearLayout) fragmentView.findViewById(R.id.llDays4);
        tvDays4 = (TextView) fragmentView.findViewById(R.id.tvDays4);
        ivDays4 = (ImageView) fragmentView.findViewById(R.id.ivDays4);
        ivDays4.setOnClickListener(this);

        // ++++++++++++++++ Medicine == 5
        llMedicine5 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine5);
        edtMedicine5 = (EditText) fragmentView.findViewById(R.id.edtMedicine5);
        ivMedicine5 = (ImageView) fragmentView.findViewById(R.id.ivMedicine5);
        ivMedicine5.setOnClickListener(this);

        llFrequency5 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency5);
        tvFrequency5 = (TextView) fragmentView.findViewById(R.id.tvFrequency5);
        ivFrequency5 = (ImageView) fragmentView.findViewById(R.id.ivFrequency5);
        ivFrequency5.setOnClickListener(this);

        llDays5 = (LinearLayout) fragmentView.findViewById(R.id.llDays5);
        tvDays5 = (TextView) fragmentView.findViewById(R.id.tvDays5);
        ivDays5 = (ImageView) fragmentView.findViewById(R.id.ivDays5);
        ivDays5.setOnClickListener(this);

        // ++++++++++++++++ Medicine == 6
        llMedicine6 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine6);
        edtMedicine6 = (EditText) fragmentView.findViewById(R.id.edtMedicine6);
        ivMedicine6 = (ImageView) fragmentView.findViewById(R.id.ivMedicine6);
        ivMedicine6.setOnClickListener(this);

        llFrequency6 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency6);
        tvFrequency6 = (TextView) fragmentView.findViewById(R.id.tvFrequency6);
        ivFrequency6 = (ImageView) fragmentView.findViewById(R.id.ivFrequency6);
        ivFrequency6.setOnClickListener(this);

        llDays6 = (LinearLayout) fragmentView.findViewById(R.id.llDays6);
        tvDays6 = (TextView) fragmentView.findViewById(R.id.tvDays6);
        ivDays6 = (ImageView) fragmentView.findViewById(R.id.ivDays6);
        ivDays6.setOnClickListener(this);

        // ++++++++++++++++ Medicine == 7
        llMedicine7 = (LinearLayout) fragmentView.findViewById(R.id.llMedicine7);
        edtMedicine7 = (EditText) fragmentView.findViewById(R.id.edtMedicine7);
        ivMedicine7 = (ImageView) fragmentView.findViewById(R.id.ivMedicine7);
        ivMedicine7.setOnClickListener(this);

        llFrequency7 = (LinearLayout) fragmentView.findViewById(R.id.llFrequency7);
        tvFrequency7 = (TextView) fragmentView.findViewById(R.id.tvFrequency7);
        ivFrequency7 = (ImageView) fragmentView.findViewById(R.id.ivFrequency7);
        ivFrequency7.setOnClickListener(this);

        llDays7 = (LinearLayout) fragmentView.findViewById(R.id.llDays7);
        tvDays7 = (TextView) fragmentView.findViewById(R.id.tvDays7);
        ivDays7 = (ImageView) fragmentView.findViewById(R.id.ivDays7);
        ivDays7.setOnClickListener(this);

        tvRemoveMedicine = (TextView) fragmentView.findViewById(R.id.tvRemoveMedicine);
        tvRemoveMedicine.setOnClickListener(this);

        tvDoctorName = (TextView) fragmentView.findViewById(R.id.tvDoctorName);
        tvDoctorName.setText(Utililty.getDoctorName(getActivity()));

        llDuration = (LinearLayout) fragmentView.findViewById(R.id.llDuration);
        llDuration.setOnClickListener(this);
        tvDuration = (TextView) fragmentView.findViewById(R.id.tvDuration);
        ivDuration = (ImageView) fragmentView.findViewById(R.id.ivDuration);
        ivDuration.setOnClickListener(this);

        swFollowUp = (Switch) fragmentView.findViewById(R.id.swFollowUp);
        swFollowUp.setOnCheckedChangeListener(this);

        btnSubmit = (Button) fragmentView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnPrint = (Button) fragmentView.findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(this);

        tvAddMoreMedicine = (TextView) fragmentView.findViewById(R.id.tvAddMoreMedicine);
        tvAddMoreMedicine.setOnClickListener(this);

        spinvestigation = (EditText) fragmentView.findViewById(R.id.edtvestigation);

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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swFollowUp:
                if (isChecked) {
                    data.setIsFollowup("Yes");
                } else {
                    data.setIsFollowup("No");
                }
                break;

        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }


    private boolean validateFields() {
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
        } else if (isFuture) {
            Toast.makeText(getActivity(), "Please don't not enter future date", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (autoSWPNo.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter SWPNo.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (autoPatientName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter patient name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (edtChief_Complaint.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter chief complaint.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (edtTreamentAdviced.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter treament adviced.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;

    }

    public void setupInvestigationSpiner() {

        final ArrayAdapter<String> investigationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, investigationList);
        Log.d("investigationlist", new Gson().toJson(investigationList));
        //  spinvestigation.setAdapter(investigationAdapter);
        LayoutInflater infator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = infator.inflate(R.layout.layout, null);
        final ListView list = (ListView) view.findViewById(R.id.listView);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(investigationAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checked = list.getCheckedItemPositions();
                Log.d("yyyyyyyy", checked.get(position) + "");
                    if(!checked.get(position)==true){
                        investigationListPost.remove(investigationList.get(position));
                    }else{
                        investigationListPost.add(investigationList.get(position));
                    }




            }
        });
        AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        bldr.setTitle("Select");
        bldr.setView(view);

        bldr.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txtInvestigation = "";
                        for (int i = 0; i < investigationListPost.size(); i++) {
                            txtInvestigation += investigationListPost.get(i) + ", ";
                        }
                        spinvestigation.setText(txtInvestigation);
                    }
                });
        bldr.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog dlg = bldr.create();
        dlg.show();

        // spinvestigation.setItems(investigationList);
        //spSystem.setSelection(new int[]{2, 6});
        // spinvestigation.setListener(this);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        Log.d("hii", "");
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

}
