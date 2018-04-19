package com.hgs.ruralhealth.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.MSW_Activity.MswAcitivyInputData;
import com.hgs.ruralhealth.model.db.SpinnerData;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.utilities.Utililty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by rameshg on 9/2/2016.
 */
public class MSWActiviyFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View fragmentView;
    private TextView tv_MSW;
    private AutoCompleteTextView tvVillages;
    private EditText dateTxt;
    private ImageView dateIco, iv_Village;
    private EditText edtother, edtpeople, edtsupport, edtremark, edtvisit;
    CheckBox cb_activity_one, cb_activity_two, cb_activity_three, cb_activity_four, cb_activity_five, cb_activity_six;
    Switch sw_teacher_present, sw_filter, sw_dispenser;
    Button btnsubmit, btnMSWdistribution;
    String villageName, villagePrefix;
    ArrayAdapter<String> vliiagePadaAdapter;

    DBHelper db;
    List<SpinnerData> mswNameList = new ArrayList<>();
    ArrayList<String> mswNameListData;
    MswAcitivyInputData mswAcitivyOutput;
    List<MswAcitivyInputData> mswAcitivyOutputList;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy", Locale.US);

    private List<VillageOutputData> villagePadaList = new ArrayList<>();
    ArrayList<String> villagepadaListData = new ArrayList<>();

    ListPopupWindow MSWListPopupWindow, villagePopupWindow;
    LinearLayout llVillage;
    DatePickerDialog datePickerDialog;
    ArrayList<String> activities;
    List<ActivityData> sessionDatalist;
    SharedPreferences preferences;
    String tabname;
    int drId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.msw_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setupUI();
        drId = Utililty.getDoctorId(getActivity());
        mswAcitivyOutput = new MswAcitivyInputData();
        activities = new ArrayList<>();
        db = new DBHelper(getActivity());
        sessionDatalist = db.getsessionActivityList();
        Log.d("mswactivitylist", new Gson().toJson(db.getsessionActivityList()));
        mswNameList = db.getMswNameSystemData();
        for (int i = 0; i < sessionDatalist.size(); i++) {
            if (i == 0) {
                cb_activity_one.setVisibility(View.VISIBLE);
                cb_activity_one.setText(sessionDatalist.get(i).getActivityName());
            } else if (i == 0) {
                cb_activity_two.setVisibility(View.VISIBLE);
                cb_activity_two.setText(sessionDatalist.get(i).getActivityName());
            } else if (i == 1) {
                cb_activity_three.setVisibility(View.VISIBLE);
                cb_activity_three.setText(sessionDatalist.get(i).getActivityName());
            } else if (i == 2) {
                cb_activity_four.setVisibility(View.VISIBLE);
                cb_activity_four.setText(sessionDatalist.get(i).getActivityName());
            } else if (i == 3) {
                cb_activity_five.setVisibility(View.VISIBLE);
                cb_activity_five.setText(sessionDatalist.get(i).getActivityName());
            } else if (i == 4) {
                cb_activity_six.setVisibility(View.VISIBLE);
                cb_activity_six.setText(sessionDatalist.get(i).getActivityName());
            }

        }
        MSWListPopupWindow = new ListPopupWindow(getActivity());
        villagePopupWindow = new ListPopupWindow(getActivity());
        villagePadaList = db.getVillageData();
        setVillageName();
        setVillagesDateList();
        tv_MSW.setText(Utililty.getDoctorName(getActivity()));

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferences.getString("deviceNo", null) != null) {
            tabname = preferences.getString("deviceNo", null);
        }
        return fragmentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "MSW Activity");
    }

    public void setupUI() {
        tvVillages = (AutoCompleteTextView) fragmentView.findViewById(R.id.tv_Villages);

        llVillage = (LinearLayout) fragmentView.findViewById(R.id.llVillage);
        llVillage.setOnClickListener(this);

        iv_Village = (ImageView) fragmentView.findViewById(R.id.iv_Village);
        iv_Village.setOnClickListener(this);

        btnsubmit = (Button) fragmentView.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(this);

        btnMSWdistribution = (Button) fragmentView.findViewById(R.id.btnMSWdistribution);
        btnMSWdistribution.setOnClickListener(this);

        tv_MSW = (TextView) fragmentView.findViewById(R.id.tv_MSW);

        edtother = (EditText) fragmentView.findViewById(R.id.edtother);
        edtpeople = (EditText) fragmentView.findViewById(R.id.edtpeople);
        edtsupport = (EditText) fragmentView.findViewById(R.id.edtsupport);
        edtremark = (EditText) fragmentView.findViewById(R.id.edtremark);
        edtvisit = (EditText) fragmentView.findViewById(R.id.edtvisit);
        dateTxt = (EditText) fragmentView.findViewById(R.id.dateTxt);
        dateTxt.setText(Utililty.getCurDate());


        cb_activity_one = (CheckBox) fragmentView.findViewById(R.id.cb_activity_one);
        cb_activity_two = (CheckBox) fragmentView.findViewById(R.id.cb_activity_two);
        cb_activity_three = (CheckBox) fragmentView.findViewById(R.id.cb_activity_three);
        cb_activity_four = (CheckBox) fragmentView.findViewById(R.id.cb_activity_four);
        cb_activity_five = (CheckBox) fragmentView.findViewById(R.id.cb_activity_five);
        cb_activity_six = (CheckBox) fragmentView.findViewById(R.id.cb_activity_six);

        cb_activity_one.setOnCheckedChangeListener(this);
        cb_activity_two.setOnCheckedChangeListener(this);
        cb_activity_three.setOnCheckedChangeListener(this);
        cb_activity_four.setOnCheckedChangeListener(this);
        cb_activity_five.setOnCheckedChangeListener(this);
        cb_activity_six.setOnCheckedChangeListener(this);

        sw_teacher_present = (Switch) fragmentView.findViewById(R.id.sw_teacher_present);
        sw_filter = (Switch) fragmentView.findViewById(R.id.sw_filter);
        sw_dispenser = (Switch) fragmentView.findViewById(R.id.sw_dispenser);

        sw_teacher_present.setOnCheckedChangeListener(this);
        sw_filter.setOnCheckedChangeListener(this);
        sw_dispenser.setOnCheckedChangeListener(this);


        dateIco = (ImageView) fragmentView.findViewById(R.id.dateIco);
        dateIco.setOnClickListener(this);


    }


    private void setMSW_Name_PreSpinner() {
        if (mswNameList != null) {
            mswNameListData = new ArrayList<>();
            for (int i = 0; i < mswNameList.size(); i++) {
                mswNameListData.add(mswNameList.get(i).getName());
            }
        }
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
            case R.id.btnsubmit:
                mswAcitivyOutputList = new ArrayList<>();
                if (sw_teacher_present.isChecked()) {
                    mswAcitivyOutput.setIsTeacherPresent("YES");
                } else {
                    mswAcitivyOutput.setIsTeacherPresent("-");
                }
                if (sw_filter.isChecked()) {
                    mswAcitivyOutput.setIsWaterFilterPresent("YES");
                } else {
                    mswAcitivyOutput.setIsWaterFilterPresent("-");
                }

                if (sw_dispenser.isChecked()) {
                    System.out.println("YES");
                    mswAcitivyOutput.setIsHandWashPresent("YES");
                } else {
                    mswAcitivyOutput.setIsHandWashPresent("-");
                }
                mswAcitivyOutput.setCreatedDate(dateTxt.getText().toString());
                mswAcitivyOutput.setMswName(tv_MSW.getText().toString());
                mswAcitivyOutput.setVillage(villageName);
                mswAcitivyOutput.setOtherOption(edtother.getText().toString());

                System.out.println("List after short"+activities);
                mswAcitivyOutput.setActivities(activities);
                mswAcitivyOutput.setSupportReq(edtsupport.getText().toString());
                mswAcitivyOutput.setRemark(edtremark.getText().toString());
                mswAcitivyOutput.setChildrenCount(edtpeople.getText().toString());
                mswAcitivyOutput.setHouseVisit(edtvisit.getText().toString());
                mswAcitivyOutput.setIsOld("N");
                mswAcitivyOutput.setTabNo(tabname);
                mswAcitivyOutput.setDoctorId(drId);
                Log.d("mswdata", mswAcitivyOutput.toString());
                mswAcitivyOutputList.add(mswAcitivyOutput);
                db.insertMSWACtivity(mswAcitivyOutputList);
                Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
                Clear();
                break;

            case R.id.dateIco:
                setFromAndToDate();
                datePickerDialog.show();
                break;

            case R.id.btnMSWdistribution:
                MSWDistributionActiviyFragment fragment = new MSWDistributionActiviyFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;


            case R.id.iv_Village:
                tvVillages.setText("");
                setVillagesPopupList();
                break;
        }

    }

    private void setVillageName() {
        if (villagePadaList != null) {
            for (int i = 0; i < villagePadaList.size(); i++) {
                villagepadaListData.add(villagePadaList.get(i).getVillageName());
            }
        }
        vliiagePadaAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, villagepadaListData);
    }

    private void setVillagesDateList() {
        tvVillages.setThreshold(2);
        tvVillages.setAdapter(vliiagePadaAdapter);
        tvVillages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                villageName = tvVillages.getText().toString();
                mswAcitivyOutput.setVillage(villageName);
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
                villageName = villagepadaListData.get(position);
                mswAcitivyOutput.setVillage(villageName);
                tvVillages.setText(villagepadaListData.get(position));
            }
        });
        llVillage.post(new Runnable() {
            public void run() {
                villagePopupWindow.show();
            }
        });
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

    private void Clear() {
        edtother.setText("");
        edtpeople.setText("");
        edtsupport.setText("");
        edtremark.setText("");
        edtvisit.setText("");

        cb_activity_one.setChecked(false);
        cb_activity_two.setChecked(false);
        cb_activity_three.setChecked(false);
        cb_activity_four.setChecked(false);
        cb_activity_five.setChecked(false);
        cb_activity_six.setChecked(false);

        sw_teacher_present.setChecked(false);
        sw_filter.setChecked(false);
        sw_dispenser.setChecked(false);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.cb_activity_one:
                if (cb_activity_one.isChecked()) {

                    activities.add(cb_activity_one.getText().toString());
                }else{
                    activities.remove(cb_activity_one.getText().toString());
                }



                break;

            case R.id.cb_activity_two:
                if (cb_activity_two.isChecked()) {
                    activities.add(cb_activity_two.getText().toString());
                }else{
                    activities.remove(cb_activity_two.getText().toString());
                }
                break;

            case R.id.cb_activity_three:
                if (cb_activity_three.isChecked()) {
                    activities.add(cb_activity_three.getText().toString());
                }else{
                    activities.remove(cb_activity_three.getText().toString());
                }
                break;
            case R.id.cb_activity_four:
                if (cb_activity_four.isChecked()) {
                    activities.add(cb_activity_four.getText().toString());
                }else{
                    activities.remove(cb_activity_four.getText().toString());
                }
                break;

            case R.id.cb_activity_five:
                if (cb_activity_five.isChecked()) {
                    activities.add(cb_activity_five.getText().toString());
                }
                break;

            case R.id.cb_activity_six:
                if (cb_activity_six.isChecked()) {
                    activities.add(cb_activity_six.getText().toString());
                }
                break;

            case R.id.sw_teacher_present:
                if (sw_teacher_present.isChecked()) {
                    mswAcitivyOutput.setIsTeacherPresent("YES");
                } else {
                    mswAcitivyOutput.setIsTeacherPresent("-");

                }
                break;

            case R.id.sw_filter:
                if (sw_filter.isChecked()) {
                    mswAcitivyOutput.setIsWaterFilterPresent("YES");
                } else {
                    mswAcitivyOutput.setIsWaterFilterPresent("-");

                }
                break;

            case R.id.sw_dispenser:
                if (sw_dispenser.isChecked()) {
                    System.out.println("YES");
                    mswAcitivyOutput.setIsHandWashPresent("YES");
                } else {
                    mswAcitivyOutput.setIsHandWashPresent("-");

                }
                break;
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (dateTxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter date.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (tv_MSW.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter Doctor name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (tvVillages.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please select village.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}
