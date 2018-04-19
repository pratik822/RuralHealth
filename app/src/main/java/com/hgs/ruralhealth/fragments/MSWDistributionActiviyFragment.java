package com.hgs.ruralhealth.fragments;

import android.content.Context;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.MSW_Distribution.Items;
import com.hgs.ruralhealth.model.MSW_Distribution.Msw_Distribution_InputData;
import com.hgs.ruralhealth.model.db.SpinnerData;
import com.hgs.ruralhealth.model.masterdata.ProductsOutputData;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.utilities.Utililty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by rameshg on 9/2/2016.
 */
public class MSWDistributionActiviyFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;
    TextView txtamt;
    Button btnsubmit;
    //  ListView list;
    ArrayAdapter<String> vliiagePadaAdapter;
    private TextView tv_MSW;
    private AutoCompleteTextView tvVillages;
    MSWdistributionAdapter adapter;
    private EditText dateTxt;
    DBHelper db;
    List<SpinnerData> mswNameList = new ArrayList<>();
    LinearLayout llVillage;
    ImageView ivVillage;
    ListPopupWindow MSWListPopupWindow, villagePopupWindow;
    private List<VillageOutputData> villagePadaList = new ArrayList<>();
    ArrayList<String> villagepadaListData = new ArrayList<>();
    List<ProductsOutputData> msw_Product_List = new ArrayList<>();
    List<Msw_Distribution_InputData> msw_distribution_outputDataList;
    List<EditText> allEds;
    String villageName, villagePrefix;
    int drId;
    List<Items> itemsList;
    List<Items> newList;
    Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.msw_distribution_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setupUI();
        drId = Utililty.getDoctorId(getActivity());
        db = new DBHelper(getActivity());
        mswNameList = db.getMswNameSystemData();
        msw_Product_List = db.getMSWProductData();
        villagePadaList = db.getVillageData();

        MSWListPopupWindow = new ListPopupWindow(getActivity());
        villagePopupWindow = new ListPopupWindow(getActivity());
        setVillageName();
        setVillagesDateList();
        setList();
        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout l = (LinearLayout) fragmentView.findViewById(R.id.lin);
        allEds = new ArrayList<EditText>();
        for (int x = 0; x < msw_Product_List.size(); x++) {
            View v = vi.inflate(R.layout.msw_activity_layer, null);
            EditText editText3 = (EditText) v.findViewById(R.id.editText3);
            allEds.add(editText3);
            final TextView textView2 = (TextView) v.findViewById(R.id.textView2);
            final TextView tvUnits = (TextView) v.findViewById(R.id.tvUnit);
            textView2.setText(msw_Product_List.get(x).getProductName());
            tvUnits.setText(msw_Product_List.get(x).getUnit());
            l.addView(v);
        }

        tv_MSW.setText(Utililty.getDoctorName(getActivity()));

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "MSW Distribution Activity");
    }

    public void setupUI() {
        tv_MSW = (TextView) fragmentView.findViewById(R.id.tvMSWDisti);
        tvVillages = (AutoCompleteTextView) fragmentView.findViewById(R.id.tvVillages);
        ivVillage = (ImageView) fragmentView.findViewById(R.id.ivVillage);
        ivVillage.setOnClickListener(this);
        llVillage = (LinearLayout) fragmentView.findViewById(R.id.llVillage);
        llVillage.setOnClickListener(this);

        txtamt = (TextView) fragmentView.findViewById(R.id.textView3);
        //   list = (ListView) fragmentView.findViewById(R.id.list);
        dateTxt = (EditText) fragmentView.findViewById(R.id.dateTxt);
        dateTxt.setText(Utililty.getCurDate());
        btnsubmit = (Button) fragmentView.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(this);

    }


    public void setList() {
        adapter = new MSWdistributionAdapter(getActivity(), msw_Product_List);
        //      list.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsubmit:
                itemsList = new ArrayList<>();
                newList = new ArrayList<>();
                for (int i = 0; i < allEds.size(); i++) {
                    if (!allEds.get(i).getText().toString().isEmpty()) {
                        Items itm = new Items();
                        itm.setItemName(msw_Product_List.get(i).getProductName());
                        itm.setQty(allEds.get(i).getText().toString());
                        itm.setUnit(msw_Product_List.get(i).getUnit());
                        itemsList.add(itm);

                    }


                }

                Log.d("----itemlist", new Gson().toJson(itemsList));
                Log.d("----databselist", new Gson().toJson(db.getallMSWProduct()));

                for (int i = 0; i < itemsList.size(); i++) {
                    Log.d("saerchdublicate", new Gson().toJson(db.getMSWProduct(itemsList.get(i).getItemName())));
                }
                if (validateFields()) {
                    msw_distribution_outputDataList = new ArrayList<>();
                    Msw_Distribution_InputData msw_distribution_outputData = new Msw_Distribution_InputData();
                    msw_distribution_outputData.setCreatedDate(dateTxt.getText().toString());
                    msw_distribution_outputData.setMswName(tv_MSW.getText().toString());
                    msw_distribution_outputData.setVillage(villageName);
                    msw_distribution_outputData.setIsOld("N");
                    HashSet<Items> y_hashSet = new HashSet<Items>();
                  /*  y_hashSet.addAll(MSWdistributionAdapter.itemsList);

                    List<Items> list = new ArrayList<Items>();
                    list.addAll(y_hashSet);

                    LinkedHashSet<Items> listToSet = new LinkedHashSet<Items>(list);
                    list.clear();

                    Set<Items> set = new HashSet<Items>(list);
                    list.clear();
                    list.addAll(set);*/


                    Log.d("finalitems", new Gson().toJson(itemsList));
                    msw_distribution_outputData.setTabName(Utililty.getCurruntDevice(getActivity()));
                    msw_distribution_outputData.setDoctorId(drId);
                    msw_distribution_outputDataList.add(msw_distribution_outputData);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date d1 = new Date(dateTxt.getText().toString());

                    Date date1 = null;
                    try {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateTxt.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date d2 = new Date();


                    //   Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
                   /* if(db.getMSWProduct(itemsList.get(c).getItemName()).size()==0){
                        db.insertMswDistribution(msw_distribution_outputDataList);
                        Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");

                    }*/
                    Log.d("----predatabselist", new Gson().toJson(db.getallMSWProduct()));

                    for (int c = 0; c < itemsList.size(); c++) {
                        Items it = new Items();

                        if (db.CheckIsDataAlreadyInDBorNot(itemsList.get(c).getItemName())) {
                            Log.d("mystatus", itemsList.get(c).getItemName());
                            Log.d("updates", new Gson().toJson(itemsList.get(c).getItemName()));
                            newList = new ArrayList<>();
                            // db.updateitems(itemsList.get(c).getItemName(), itemsList.get(c).getQty());

                            it.setItemName(itemsList.get(c).getItemName());
                            it.setQty(itemsList.get(c).getQty());
                            it.setUnit(itemsList.get(c).getUnit());
                            newList.add(it);
                            msw_distribution_outputData.setItems(newList);

                            db.UpdateMswDistribution(msw_distribution_outputDataList);
                            Log.d("----output-up", new Gson().toJson(msw_distribution_outputDataList));
                            Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Updated Suscsessfully");
                        } else {

                            newList = new ArrayList<>();
                            it.setItemName(itemsList.get(c).getItemName());
                            it.setQty(itemsList.get(c).getQty());
                            it.setUnit(itemsList.get(c).getUnit());
                            newList.add(it);
                            msw_distribution_outputData.setItems(newList);
                            Log.d("----output-in", new Gson().toJson(msw_distribution_outputDataList));
                            db.insertMswDistribution(msw_distribution_outputDataList);


                        }
                        Log.d("----insertitemfinal", new Gson().toJson(itemsList));
                        //   db.insertMswDistribution(msw_distribution_outputDataList);
                        Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
                    }

                    if (db.getallMSWProduct().size() == 0) {
                        db.insertMswDistribution(msw_distribution_outputDataList);
                        Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");

                    }

                    Log.d("----itemlist", new Gson().toJson(itemsList));
                    Log.d("----databselist", new Gson().toJson(db.getallMSWProduct()));




             /*      for (int c=0;c<itemsList.size();c++){
                        if (db.getMSWProduct(itemsList.get(c).getItemName()).size()>0){
                            Log.d("mystatus",itemsList.get(c).getItemName());
                            db.updateitems(itemsList.get(c).getItemName(), itemsList.get(c).getQty());
                        }else{
                            db.insertMswDistribution(msw_distribution_outputDataList);
                            Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
                        }
                    }*/

                    Log.d("mswDistribution11", new Gson().toJson(db.getMSWDistributionByIsoldN().get(0).getItems()));

               /*  for (int c=0;c<itemsList.size();c++){
                        if (db.getMSWProduct(itemsList.get(c).getItemName()).size()>0){
                            Log.d("mystatus",itemsList.get(c).getItemName());
                            db.updateitems(itemsList.get(c).getItemName(), itemsList.get(c).getQty());
                        }
                 }


                   for (int l = 0; l < db.getMSWDistributionByIsoldN().size(); l++) {
                        for (int m = 0; m < db.getMSWDistributionByIsoldN().get(l).getItems().size(); m++) {
                            Log.d("arrays", new Gson().toJson(db.getMSWDistributionByIsoldN().get(l).getItems().get(m).getItemName()));
                            for (int x = 0; x < itemsList.size(); x++) {
                                if (itemsList.get(x).getItemName().equalsIgnoreCase(db.getMSWDistributionByIsoldN().get(l).getItems().get(m).getItemName())){
                                    Log.d("mswDistribution11", new Gson().toJson(msw_distribution_outputData));
                                 //   db.updateitems(itemsList.get(x).getItemName(), itemsList.get(x).getQty());

                                }else{
                                  *//*     db.insertMswDistribution(msw_distribution_outputDataList);
                                    Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
                                    Log.d("mswDistribution", new Gson().toJson(msw_distribution_outputData));*//*
                                }


                            }
                        }
                        //  Log.d("arrays",new Gson().toJson(db.getMSWDistributionByIsoldN().get(l).getItems()));

                    }*/

                 /*   if (db.getMSWDistributionByIsoldN().size()==0){
                        db.insertMswDistribution(msw_distribution_outputDataList);
                        Utililty.alertDialogMSWShowAndDismiss(getActivity(), "Sucsess", "Record Inserted Suscsessfully");
                        Log.d("mswDistribution", new Gson().toJson(msw_distribution_outputData));
                    }*/


                }
                break;

            case R.id.ivVillage:
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
                tvVillages.setText(villagepadaListData.get(position));
            }
        });
        llVillage.post(new Runnable() {
            public void run() {
                villagePopupWindow.show();
            }
        });
    }

    private boolean validateFields() {
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
        } else if (tv_MSW.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter Doctor name.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (tvVillages.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please select village.", Toast.LENGTH_SHORT).show();
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
