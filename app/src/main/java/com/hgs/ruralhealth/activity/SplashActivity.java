package com.hgs.ruralhealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.fragments.ActivityModel;
import com.hgs.ruralhealth.model.Device.Device;
import com.hgs.ruralhealth.model.Device.DeviceData;
import com.hgs.ruralhealth.model.masterdata.FrequencieData;
import com.hgs.ruralhealth.model.masterdata.FrequencieOutputData;
import com.hgs.ruralhealth.model.masterdata.Investigation;
import com.hgs.ruralhealth.model.masterdata.InvestigationData;
import com.hgs.ruralhealth.model.masterdata.MedicineData;
import com.hgs.ruralhealth.model.masterdata.MedicineOutputData;
import com.hgs.ruralhealth.model.masterdata.ProductsData;
import com.hgs.ruralhealth.model.masterdata.ProductsOutputData;
import com.hgs.ruralhealth.model.masterdata.SymptompsData;
import com.hgs.ruralhealth.model.masterdata.SymptompsOutputData;
import com.hgs.ruralhealth.model.masterdata.SystemData;
import com.hgs.ruralhealth.model.masterdata.SystemOutputData;
import com.hgs.ruralhealth.model.masterdata.VillageData;
import com.hgs.ruralhealth.model.masterdata.VillageOutputData;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.model.register.RegistrationInputData;
import com.hgs.ruralhealth.networking.RefrofitGetClient;
import com.hgs.ruralhealth.utilities.Utililty;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rameshg on 9/1/2016.
 */
public class SplashActivity extends Activity {

    protected int _splashTime = 3000;
    private Activity _activity;
    public Handler splash_time_handler;
    private Runnable r_callingMenu;
    public static boolean onlyOnce = true;
    DBHelper db;

    VillageData villageData;
    List<VillageOutputData> villageList;

    MedicineData medicineData;
    List<MedicineOutputData> medicineList;

    FrequencieData frequencieData;
    ActivityModel activityModel;
    List<FrequencieOutputData> frequencyList;

    SystemData systemData;
    List<SystemOutputData> systemList;

    ProductsData productsData;
    List<ProductsOutputData> productList;

    SymptompsData symptompsData;
    List<SymptompsOutputData> symptompsList;
    List<InvestigationData> investigationData;


    Device device;
    List<DeviceData> deviceDataList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        _activity = this;
        //Log.i("get Entity==>", _activity.getPackageName().get)
        onlyOnce = true;// to reset the date to the todays Date
        db = new DBHelper(this);
       db.deletedata();
        Log.d("sysid", Utililty.getSystemDevice(getApplicationContext()));

        if (Utililty.isInternetConnectionAlive(getApplicationContext())) {
            _activity = this;

            new LoadMasterData().execute();
        } else
        if (db.getVillageData().size() <= 0 || db.getMedicineData().size() <= 0 || db.getFrequencyData().size() <= 0 ||
                db.getAdviceSystemData().size() <= 0 || db.geSymptomsData().size() <= 0 || db.getInvestigationData().size() <= 0) {
            Utililty.alertDialogShowAndDismiss(this, "Alert", "No Internet Connection");
            //finish();
        } else {
            Intent intent = new Intent(_activity, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }



    //Village Pada
    private void getVillagePI() {
        Call<VillageData> villageAPICall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getvillageDataList();
        villageAPICall.enqueue(new Callback<VillageData>() {
            @Override
            public void onResponse(Call<VillageData> call, Response<VillageData> response) {
                villageData = response.body();
                if (villageData != null && villageData.getMessage().equalsIgnoreCase("Found Data.")) {
                    //getVillageData = villageData.getData();
                    db.insertVillageData(villageData.getData());
                    villageList = new ArrayList<>();
                    villageList = db.getVillageData();
                    Log.i("getVillageData==>", villageList.toString());
                } else {
                    Log.i("getVillageData", "No data");
                }
            }

            @Override
            public void onFailure(Call<VillageData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getDeviceAPI() {
        Call<Device> villageAPICall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getDevices();
        villageAPICall.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                device = response.body();
                if (device != null && device.getMessage().equalsIgnoreCase("Found Data.")) {
                    //getVillageData = villageData.getData();
                    db.insertDeviceData(device.getData());
                    deviceDataList = new ArrayList<>();
                    deviceDataList = db.getdeviceList(Utililty.getSystemDevice(SplashActivity.this));
                    if (deviceDataList.size() > 0) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                        SharedPreferences.Editor editor = null;
                        editor = preferences.edit();
                        editor.putString("deviceNo", deviceDataList.get(0).getDeviceName()).commit();

                    }
                    Log.i("getDevices==>", new Gson().toJson(deviceDataList));
                } else {
                    Log.i("getVillageData", "No data");
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    //Medicine
    private void getMedicineAPI() {
        Call<MedicineData> medicineOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getmedicineDataList();
        medicineOutputDataCall.enqueue(new Callback<MedicineData>() {
            @Override
            public void onResponse(Call<MedicineData> call, Response<MedicineData> response) {
                medicineData = response.body();
                Log.i("getMedicineData==>",new Gson().toJson(medicineData));
                if (medicineData != null && medicineData.getMessage().equalsIgnoreCase("Found Data.")) {
                    //getMedicineData = medicineData.getData();
                    db.insertMedicineData(medicineData.getData());
                    medicineList = new ArrayList<>();
                    medicineList = db.getMedicineData();
                    Log.i("getMedicineData==>", medicineList.toString());
                } else {
                    Log.i("getMedicineData", "No data");
                }

            }

            @Override
            public void onFailure(Call<MedicineData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //Frequency
    private void getactivityList() {
        Call<ActivityModel> medicineOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getactivitys();
        medicineOutputDataCall.enqueue(new Callback<ActivityModel>() {
            @Override
            public void onResponse(Call<ActivityModel> call, Response<ActivityModel> response) {
                activityModel = response.body();
                Log.d("myactivity",new Gson().toJson(activityModel));
                if (activityModel != null && activityModel.getMessage().equalsIgnoreCase("Found Data.")) {

                   db.insertActivityData(activityModel.getData());
                   // frequencyList = new ArrayList<>();
                     Log.d("getactivitylist",new Gson().toJson(db.getsessionActivityList())) ;
                 //   Log.i("getFrequencyData==>", frequencyList.toString());
                } else {
                    Log.i("getFrequencyData", "No data");
                }
            }

            @Override
            public void onFailure(Call<ActivityModel> call, Throwable t) {

            }
        });
    }

    //Frequency
    private void getFrequencieAPI() {
        Call<FrequencieData> medicineOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getfrequenciesDataList();
        medicineOutputDataCall.enqueue(new Callback<FrequencieData>() {
            @Override
            public void onResponse(Call<FrequencieData> call, Response<FrequencieData> response) {
                frequencieData = response.body();
                if (frequencieData != null && frequencieData.getMessage().equalsIgnoreCase("Found Data.")) {
                    // getfrequencyData = frequencieData.getData();
                    db.insertFrequencyData(frequencieData.getData());
                    frequencyList = new ArrayList<>();
                    frequencyList = db.getFrequencyData();
                    Log.i("getFrequencyData==>", frequencyList.toString());
                } else {
                    Log.i("getFrequencyData", "No data");
                }
            }

            @Override
            public void onFailure(Call<FrequencieData> call, Throwable t) {

            }
        });
    }

    //System
    private void getSystemAPI() {
        Call<SystemData> systemOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getsystemDataList();
        systemOutputDataCall.enqueue(new Callback<SystemData>() {
            @Override
            public void onResponse(Call<SystemData> call, Response<SystemData> response) {
                systemData = response.body();
                if (systemData != null && systemData.getMessage().equalsIgnoreCase("Found Data.")) {
                    db.insertAdviceSystem(systemData.getData());
                    systemList = new ArrayList<>();
                    systemList = db.getAdviceSystemData();
                    Log.i("systemList==>", systemList.toString());
                } else {
                    Log.i("systemList", "No data");
                }
            }

            @Override
            public void onFailure(Call<SystemData> call, Throwable t) {

            }
        });
    }

    //Products
    private void getProductDataAPI() {
        Call<ProductsData> productsDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getproductsDataList();
        productsDataCall.enqueue(new Callback<ProductsData>() {
            @Override
            public void onResponse(Call<ProductsData> call, Response<ProductsData> response) {
                Log.i("productList==>", new Gson().toJson(response));
                if (response.body() != null && response.body().getMessage().equalsIgnoreCase("Found Data.")) {
                    productsData = response.body();
                    db.insertMSWProductSystem(productsData.getData());
                    productList = new ArrayList<>();
                    productList = db.getMSWProductData();
                    Log.i("productList==>", productList.toString());
                } else {
                    Log.i("productList", "No data");
                }

            }

            @Override
            public void onFailure(Call<ProductsData> call, Throwable t) {

            }
        });
    }


    //Symptoms
    private void getSymptompsAPI() {
        Call<SymptompsData> symptomsDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getsymptoms();
        symptomsDataCall.enqueue(new Callback<SymptompsData>() {
            @Override
            public void onResponse(Call<SymptompsData> call, Response<SymptompsData> response) {
                symptompsData = response.body();
                if (symptompsData != null && symptompsData.getMessage().equalsIgnoreCase("Found Data.")) {
                    db.insertSymptomsSystem(symptompsData.getData());
                    symptompsList = new ArrayList<>();
                    symptompsList = db.geSymptomsData();
                    Log.i("symptompsList==>", symptompsList.toString());
                } else {
                    Log.i("symptompsList", "No Data");
                }
            }

            @Override
            public void onFailure(Call<SymptompsData> call, Throwable t) {

            }
        });
    }

    //investigationApi

    private void getInvestigationData() {
        Call<Investigation> symptomsDataCall = RefrofitGetClient.getsRetrofitClientInterface(SplashActivity.this).getInvestigationData();
        symptomsDataCall.enqueue(new Callback<Investigation>() {
            @Override
            public void onResponse(Call<Investigation> call, Response<Investigation> response) {

                if (response.body() != null && response.body().getMessage().equalsIgnoreCase("Found Data.")) {
                    investigationData = response.body().getData();
                    db.insertInvestigationData(investigationData);
                    investigationData = db.getInvestigationData();
                    Log.i("InvestigationData==>", new Gson().toJson(investigationData));
                } else {
                    Log.i("InvestigationData", "No Data");
                }
            }

            @Override
            public void onFailure(Call<Investigation> call, Throwable t) {

            }
        });
    }


    class LoadMasterData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Utililty.showProgressDailog(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //db.getallRegisterdUser();
            //db.insertDuartionData(getDurationfromAPI());
            //db.insertProgramDoctorsData(getDoctorsfromAPI());
            // db.insertMSWNameSystem(getMSWNameDataFromAPI());
            getactivityList();

            if (db.getVillageData().size() <= 0)
                getVillagePI();

            if (db.getMedicineData().size() <= 0)
                getMedicineAPI();

            if (db.getFrequencyData().size() <= 0)
                getFrequencieAPI();

            if (db.getAdviceSystemData().size() <= 0)
                getSystemAPI();

            if (db.getMSWProductData().size() <= 0)
                getProductDataAPI();

            if (db.geSymptomsData().size() <= 0)
                getSymptompsAPI();
            if (db.getInvestigationData().size() <= 0)
                getInvestigationData();
            getDeviceAPI();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getHandler().postDelayed(r_callingMenu = new Runnable() {
                public void run() {
                    Intent intent = new Intent(_activity, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, _splashTime);
           /* Utililty.dismissProgressDialog();
            Intent intent = new Intent(_activity, LoginActivity.class);
            startActivity(intent);
            finish();*/
        }

    }

    public Handler getHandler() {
        splash_time_handler = new Handler();
        return splash_time_handler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //splash_time_handler.removeCallbacks(r_callingMenu);
    }
}
