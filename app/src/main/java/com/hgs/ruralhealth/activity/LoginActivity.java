package com.hgs.ruralhealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.customView.LabelEditText;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.MSW_Activity.MswActivityResponse;
import com.hgs.ruralhealth.model.MSW_Distribution.MswDistributionResponse;
import com.hgs.ruralhealth.model.db.AdviceGetOutputData;
import com.hgs.ruralhealth.model.db.AdviceInputData;
import com.hgs.ruralhealth.model.masterdata.ProductsData;
import com.hgs.ruralhealth.model.masterdata.ProductsOutputData;
import com.hgs.ruralhealth.model.masterdata.VillageData;
import com.hgs.ruralhealth.model.ophthal.OpthalResponse;
import com.hgs.ruralhealth.model.physiotherapist.PhysioSessionInputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapisSessiontResponse;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapistResponse;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.model.register.RegistrationInputData;
import com.hgs.ruralhealth.model.user.SynInput;
import com.hgs.ruralhealth.model.user.SynOutput;
import com.hgs.ruralhealth.model.user.UserData;
import com.hgs.ruralhealth.model.user.UserInputData;
import com.hgs.ruralhealth.model.user.UserOutputData;
import com.hgs.ruralhealth.networking.RefrofitGetClient;
import com.hgs.ruralhealth.utilities.Constants;
import com.hgs.ruralhealth.utilities.Utililty;
import com.hgs.ruralhealth.utilities.Validator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rameshg on 9/1/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText mUsername, mPassword;
    public Button btnLogin;
    AlertDialog dialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DBHelper db;
    UserOutputData userOutPutData;
    TextView tv_id;
    UserData userData;
    List<UserData> userDataList = new ArrayList<>();
    private List<RegisterOutputData> registerDataList;
    List<AdviceInputData> adviceDataList = new ArrayList<>();

    protected int _splashTime = 1000;
    private Activity _activity;
    public Handler splash_time_handler;
    private Runnable r_callingMenu;
    public static boolean onlyOnce = true;
    ProductsData productsData;
    List<ProductsOutputData> productList;
        String tabname="";
    private int flag = 0;
    private ProgressBar progressBar2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Utililty.hideKeyboard(this);
        _activity = this;
        onlyOnce = true;// to reset the date to the todays Date
        db = new DBHelper(this);
        setupPrefrance();
        initUI();
        if (preferences.getInt("flag", 0) == 1) {
            Intent homeIntent = new Intent(LoginActivity.this, BaseActivity.class);
            startActivity(homeIntent);
            finish();
        }

    }

    public void initUI() {
        mUsername = (EditText) findViewById(R.id.username_edittext);
        tv_id=(TextView)findViewById(R.id.tv_id);
        tv_id.setText(Utililty.getSystemDeviceId(LoginActivity.this)+"");
        mUsername.setText("manjit.pawar@hindujahospital.com");
        mPassword = (EditText) findViewById(R.id.password_edittext);
        mPassword.setText("admin@123$");
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBar2=(ProgressBar)findViewById(R.id.progressBar2);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                progressBar2.setVisibility(View.VISIBLE);
                Call<SynOutput> postSearial = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postValidateapi(Utililty.getSystemDeviceId(LoginActivity.this)+"");
                postSearial.enqueue(new Callback<SynOutput>() {
                    @Override
                    public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                      if(!response.body().getData().equalsIgnoreCase("false")){
                          if (validateFields()) {
                              doLoginOfflineOrAPI();
                          }
                      }
                        Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<SynOutput> call, Throwable t) {
                        t.printStackTrace();

                    }
                });


                break;
        }
    }

    /**
     * doLoginOfflineOrAPI() is to login offline or through API call
     */
    private void doLoginOfflineOrAPI(){
        String username = Constants.EMPTY, password = Constants.EMPTY;
        username = mUsername.getText().toString();
        password = mPassword.getText().toString().trim();
        userDataList = db.getAllUserLoginData();
        if (!username.contains(" ")) {
            if (userDataList.size() > 0) {
                for (int i = 0; i < userDataList.size(); i++) {
                    /*if ((userDataList.get(i).getLoginId().equalsIgnoreCase(username))){

                    }*/
                    if (userDataList.get(i).getLoginId().equalsIgnoreCase(username) && userDataList.get(i).getPassword().equalsIgnoreCase(password)) {
                        editor.putInt("flag", 1);
                        editor.putString("DoctorName", username);
                        editor.putInt("doctorId", userDataList.get(i).getUserId());
                        editor.putString("roleName", userDataList.get(i).getName()).commit();
                        progressBar2.setVisibility(View.GONE);
                     //   setTabDialog(userDataList.get(i).getDeviceName());
                       // getProductDataAPI(userDataList.get(i).getDeviceName());
                        Intent homeIntent = new Intent(LoginActivity.this, SplashActivity.class);
                        startActivity(homeIntent);
                        finish();
                        break;
                    } else {
                        loginAPICall(username, password);
                        //Toast.makeText(LoginActivity.this, "Invalid Username or password ", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                loginAPICall(username, password);
            }
        } else {
            Toast.makeText(LoginActivity.this, "Username Not Contains Space", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * user login method for making API call
     * @param userName
     * @param password
     */
    private void loginAPICall(final String userName, String password) {
        final UserInputData userInputData = new UserInputData();
        userInputData.setLoginId(userName);
        userInputData.setPassword(password);
        Utililty.showProgressDailog(this);
        Log.d("login",new Gson().toJson(userInputData));
        Call<UserOutputData> callLogin = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postUserLogin(userInputData);
        callLogin.enqueue(new Callback<UserOutputData>() {
            @Override
            public void onResponse(Call<UserOutputData> call, Response<UserOutputData> response) {
                Log.i("userOutPutData ==>", response.body().toString());
                userOutPutData = new UserOutputData();
                userOutPutData = response.body();
                if (userOutPutData.getStatus() == 1) {
                    Log.i("Success ==>",new Gson().toJson(userOutPutData));
                    Log.i("Success ==>", "Success");
                    if (db.getallRegisterdUser().size() <= 0) {
                        Log.i("inside ==>", db.getallRegisterdUser().size() + "");
                        new LoadBackUpData().execute();
                        saveUserDataInDB(userOutPutData);
                    } else {
                        Utililty.dismissProgressDialog();
                        saveUserDataInDB(userOutPutData);
                      //  setTabDialog(userOutPutData.getData().getDeviceName());
                    }
                    //getProductDataAPI(userOutPutData.getData().getDeviceName());
                    progressBar2.setVisibility(View.GONE);
                } else {
                    Utililty.dismissProgressDialog();
                    progressBar2.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Please enter valid UserName and password", Toast.LENGTH_SHORT).show();
                    Utililty.alertDialogShow(LoginActivity.this, "Alert", "Please enter valid UserName and password");
                }

            }

            @Override
            public void onFailure(Call<UserOutputData> call, Throwable t) {
                Log.d("inside----error","");
                t.printStackTrace();
                Utililty.dismissProgressDialog();
            }
        });

    }

    /**
     * saveUserDataInDB() save the user data
     * @param userOutPutData
     */
    private void saveUserDataInDB(UserOutputData userOutPutData) {
        // Check Reg DB .size
        userData = new UserData();
        userData.setUserId(userOutPutData.getData().getUserId());
        userData.setLoginId(userOutPutData.getData().getLoginId());
        userData.setPassword(mPassword.getText().toString());
        userData.setDeviceName(userOutPutData.getData().getDeviceName());
        editor.putInt("flag", 1);
        editor.putString("roleName", userOutPutData.getData().getName());//Need to change
        editor.putInt("doctorId", userOutPutData.getData().getUserId());
        editor.putString("DoctorName", userOutPutData.getData().getName()).commit();
        db.insertUserData(userData);

    }

    /**
     * save the device Name
     * @param deviceName
     */
    public void setTabDialog(String deviceName) {
        editor.putString("deviceNo", deviceName).commit();
        editor.putInt("flag", 1).commit();
        Intent homeIntent = new Intent(LoginActivity.this, BaseActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public void setupPrefrance() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
    }

    //Get All BackUp Verified Data
    private void getAllRegBackupData(final String deviceName) {
        Call<RegistrationInputData> registrationApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getRegisterVerifiedData(deviceName, "true");
        registrationApiCall.enqueue(new Callback<RegistrationInputData>() {
            @Override
            public void onResponse(Call<RegistrationInputData> call, Response<RegistrationInputData> response) {
                Log.i("Reg Backup==>", new Gson().toJson(response));
                if (!response.body().getMessage().equalsIgnoreCase("Not Found")) {
                    Log.i("getregistrationsync==>", new Gson().toJson(response));
                        registerDataList = new ArrayList<RegisterOutputData>();
                        registerDataList = response.body().getData();
                        Log.d("getbackup",registerDataList.size()+"");
                        db.insertUser(registerDataList);
                        SynInput synInput = new SynInput();
                        synInput.setSyncFromId(response.body().getSyncFromId());
                        synInput.setSyncToId(response.body().getSyncToId());
                       // postSyncRegBackup(synInput, deviceName);

                }
            }

            @Override
            public void onFailure(Call<RegistrationInputData> call, Throwable t) {
                Utililty.dismissProgressDialog();
                t.printStackTrace();
            }
        });
    }

    /**
     * Post the Sync data base server
     */
    private void postSyncRegBackup(SynInput synInput, String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postSyncDataRegBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("Reg BackUp Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Get others TAB advice data and insert into local DB.
     */
    private void getAdviceBackupData(final String deviceName) {
        Call<AdviceGetOutputData> getAdviceOtherTabData = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getAdviceBackupApi(deviceName, "true");
        getAdviceOtherTabData.enqueue(new Callback<AdviceGetOutputData>() {
            @Override
            public void onResponse(Call<AdviceGetOutputData> call, Response<AdviceGetOutputData> response) {
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Log.i("AdviceBackup==>", new Gson().toJson(response));
                    adviceDataList = response.body().getData();
                    db.saveAdviceInput(adviceDataList);
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                   // postAdviceBackupSync(synInput, deviceName);
                }

            }

            @Override
            public void onFailure(Call<AdviceGetOutputData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void postAdviceBackupSync(SynInput synInput, String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postSyncAdviceBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("Advice BackUp Sync==>", new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void getFollowupBackupData(final String deviceName) {
        Call<RegistrationInputData> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getFollowupBackupData(deviceName, "true");
        followupApiCall.enqueue(new Callback<RegistrationInputData>() {
            @Override
            public void onResponse(Call<RegistrationInputData> call, Response<RegistrationInputData> response) {
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertfollowupUser(response.body().getData());
                    Log.i("Follow BackUp==>", new Gson().toJson(response));
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                   // PostSyncFollowup(synInput, deviceName);
                }


            }

            @Override
            public void onFailure(Call<RegistrationInputData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void PostSyncFollowup(SynInput synInput, final String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postSyncDataFollowupBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("Follow Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void getOpthalDataBackup(final String deviceName) {
        Call<OpthalResponse> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getophthalmologyBackupAPI(deviceName, "true");
        followupApiCall.enqueue(new Callback<OpthalResponse>() {
            @Override
            public void onResponse(Call<OpthalResponse> call, Response<OpthalResponse> response) {
                Log.i("Opthal Backup==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertOphthalmolgy(response.body().getData());

                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                   // PostSyncDataOpthomology(synInput, deviceName);
                }


            }

            @Override
            public void onFailure(Call<OpthalResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void PostSyncDataOpthomology(SynInput synInput, final String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postSyncOpthomologyBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("Opthomology Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getPhysiologyData(final String deviceName) {
        Call<PhysiotherapistResponse> physiologyApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getphysiologyAPIBackup(deviceName, "ture");
        physiologyApiCall.enqueue(new Callback<PhysiotherapistResponse>() {
            @Override
            public void onResponse(Call<PhysiotherapistResponse> call, Response<PhysiotherapistResponse> response) {
                Log.i("Physio Backup==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertPhysiothrapistData(response.body().getData());
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                  //  PostSyncDataphysiogy(synInput, deviceName);
                }
            }

            @Override
            public void onFailure(Call<PhysiotherapistResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void PostSyncDataphysiogy(SynInput synInput, final String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postSyncphysiologyBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("Physiogy Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void getMswTabAllData(final String deviceName) {
        Call<MswActivityResponse> getAdviceOtherTabData = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getActivityMswBackupAPI(deviceName, "true");
        getAdviceOtherTabData.enqueue(new Callback<MswActivityResponse>() {
            @Override
            public void onResponse(Call<MswActivityResponse> call, Response<MswActivityResponse> response) {
                Log.i("MSWACtivity Backup==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertMSWACtivity(response.body().getData());
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                  //  postMswSync(synInput, deviceName);
                }
            }

            @Override
            public void onFailure(Call<MswActivityResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void postMswSync(SynInput synInput, final String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postMswAdviceBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("MSWActivity Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getMswDistributionData(final String deviceName) {
        Call<MswDistributionResponse> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getMswDistributionBackupAPI(deviceName, "true");
        followupApiCall.enqueue(new Callback<MswDistributionResponse>() {
            @Override
            public void onResponse(Call<MswDistributionResponse> call, Response<MswDistributionResponse> response) {
                Log.i("MSWDist BackUp==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertMswDistribution(response.body().getData());
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                  //  PostSyncDataReg(synInput, deviceName);
                }
            }

            @Override
            public void onFailure(Call<MswDistributionResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void PostSyncDataReg(SynInput synInput, final String deviceName) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postMswDistributionBackup(deviceName, synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("MSWDist Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //Products
   /* private void getProductDataAPI(String device) {

        Call<ProductsData> productsDataCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getproductsDataList(device);
        productsDataCall.enqueue(new Callback<ProductsData>() {
            @Override
            public void onResponse(Call<ProductsData> call, Response<ProductsData> response) {
                Log.i("productList==>", new Gson().toJson(response));
                Log.i("productList==>", Utililty.getCurruntDevice(LoginActivity.this));
                productsData = response.body();
                if (productsData != null && productsData.getStatus().equalsIgnoreCase("1")) {
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
*/
    //PhysioSession Backup Data
    private void postPhysioSessionSync(SynInput synInput, final String deviceName) {
        Call<SynOutput> postPhysioSessionApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).postPhysioSessionBackup(deviceName, synInput);
        postPhysioSessionApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("PhysioSession Sync==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getPhysioSessionData(final String deviceName) {
        Call<PhysiotherapisSessiontResponse> PhysiotherapisSessiontApiCall = RefrofitGetClient.getsRetrofitClientInterface(LoginActivity.this).getPhysioSessionBackupAPI(deviceName, "true");
        PhysiotherapisSessiontApiCall.enqueue(new Callback<PhysiotherapisSessiontResponse>() {
            @Override
            public void onResponse(Call<PhysiotherapisSessiontResponse> call, Response<PhysiotherapisSessiontResponse> response) {
                Log.i("PhysioSession BackUp==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertPhysioSessionData(response.body().getData());
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                   // postPhysioSessionSync(synInput, deviceName);
                }
            }

            @Override
            public void onFailure(Call<PhysiotherapisSessiontResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    class LoadBackUpData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

            // Utililty.showProgressDailog(LoginActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (preferences.getString("deviceNo", null) != null) {
                tabname = preferences.getString("deviceNo", null);
                getAllRegBackupData(tabname);
                getAdviceBackupData(tabname);
                getFollowupBackupData(tabname);
                getOpthalDataBackup(tabname);
                getPhysiologyData(tabname);
                getMswTabAllData(tabname);
                getMswDistributionData(tabname);
                getPhysioSessionData(tabname);

            }





            /*if (db.getVillageData().size() <= 0)
                getVillagePI();*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getHandler().postDelayed(r_callingMenu = new Runnable() {
                public void run() {
                    Utililty.dismissProgressDialog();
                    //Please wait it will take long to sync all data ...
                   // setTabDialog(userOutPutData.getData().getDeviceName());
                }
            }, _splashTime);
        }
    }

    public Handler getHandler() {
        splash_time_handler = new Handler();
        return splash_time_handler;
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (mUsername.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter username.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (mPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}
