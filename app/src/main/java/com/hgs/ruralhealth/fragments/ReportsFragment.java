package com.hgs.ruralhealth.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.activity.LoginActivity;
import com.hgs.ruralhealth.activity.SplashActivity;
import com.hgs.ruralhealth.database.DBHelper;
import com.hgs.ruralhealth.model.MSW_Activity.MswAcitivyInputData;
import com.hgs.ruralhealth.model.MSW_Activity.MswActivityResponse;
import com.hgs.ruralhealth.model.MSW_Activity.MswOuputResponse;
import com.hgs.ruralhealth.model.MSW_Distribution.MswDistributionOuputResponse;
import com.hgs.ruralhealth.model.MSW_Distribution.MswDistributionResponse;
import com.hgs.ruralhealth.model.MSW_Distribution.Msw_Distribution_InputData;
import com.hgs.ruralhealth.model.db.AdviceGetOutputData;
import com.hgs.ruralhealth.model.db.AdviceInputData;
import com.hgs.ruralhealth.model.db.AdviceOutputData;
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
import com.hgs.ruralhealth.model.ophthal.OphthalInputData;
import com.hgs.ruralhealth.model.ophthal.OpthalOuputResponse;
import com.hgs.ruralhealth.model.ophthal.OpthalResponse;
import com.hgs.ruralhealth.model.physiotherapist.PhysioSessionInputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysioSessionResponse;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapisSessiontResponse;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapistInputData;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapistOuputResponse;
import com.hgs.ruralhealth.model.physiotherapist.PhysiotherapistResponse;
import com.hgs.ruralhealth.model.register.RegisterOutputData;
import com.hgs.ruralhealth.model.register.RegistrationInputData;
import com.hgs.ruralhealth.model.register.RegistrationResponse;
import com.hgs.ruralhealth.model.user.SynInput;
import com.hgs.ruralhealth.model.user.SynOutput;
import com.hgs.ruralhealth.networking.RefrofitGetClient;
import com.hgs.ruralhealth.utilities.Utililty;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rameshg on 9/2/2016.
 */
public class ReportsFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;
    private Button btnSave, btnGet;
    private List<RegisterOutputData> registerDataList;
    private List<RegisterOutputData> followupDataList;
    private List<OphthalInputData> opthalDataList;
    private List<AdviceInputData> adviceDataList;
    private List<SynInput> synInputList;
    private DBHelper db;
    private List<MswAcitivyInputData> mswAcitivyOutputList;
    private List<Msw_Distribution_InputData> mswDistributionInputDataList;
    List<PhysiotherapistInputData> physiotherapistOutputDataList;
    List<PhysioSessionInputData> physiotherapistSessionOutputDataList;

    VillageData villageData;
    List<VillageOutputData> villageList;


    MedicineData medicineData;
    List<MedicineOutputData> medicineList;

    FrequencieData frequencieData;
    List<FrequencieOutputData> frequencyList;

    SystemData systemData;
    List<SystemOutputData> systemList;

    ProductsData productsData;
    List<ProductsOutputData> productList;

    SymptompsData symptompsData;
    List<SymptompsOutputData> symptompsList;
    List<InvestigationData> investigationData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.reports_fragment, container, false);
        setupUI();
        db = new DBHelper(getActivity());
        registerDataList = new ArrayList<>();
        adviceDataList = new ArrayList<>();
        mswAcitivyOutputList = new ArrayList<>();

        postphysiotherapistSession();
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utililty.setTitle(getActivity(), "Reports");
    }


    public void setupUI() {
        btnSave = (Button) fragmentView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnGet = (Button) fragmentView.findViewById(R.id.btnGet);
        btnGet.setOnClickListener(this);
    }


    /**
     * Post the registration data to base server
     */
    private void postRegisterData() {
        registerDataList = db.getRegByIsoldN();
        for (int i = 0; i < registerDataList.size(); i++) {
            Log.d("register", new Gson().toJson(registerDataList.get(i)));
        }


        Log.d("data--->", new Gson().toJson(registerDataList));
        if (registerDataList.size() <= 0) {
            new LoadAPIData().execute();
        } else {
            Utililty.showProgressDailog(getActivity());
            Call<RegistrationResponse> registrationApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postRegisterData(Utililty.getCurruntDevice(getActivity()), registerDataList);
            registrationApiCall.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    System.out.println("registrationResponce" + response.body().toString());
                    new LoadAPIData().execute();
                    if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                        for (int i = 0; i < registerDataList.size(); i++) {
                            db.updateRegIsold(registerDataList.get(i).getSwpNo());
                        }
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    registerDataList = new ArrayList<RegisterOutputData>();
                    registerDataList = db.getallRegisterdUser();
                    Log.i("registerDataList==>", registerDataList.toString());
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    Utililty.dismissProgressDialog();
                    t.printStackTrace();
                }
            });
        }
    }


    /**
     * Post the Advice data base server
     */
    private void postAdviceData() {
        /**Get Data*/
        adviceDataList = db.getAdviceDataByIsoldN();
        Log.d("adviceDatass----",new Gson().toJson(adviceDataList));
        Call<AdviceOutputData> callAdvice = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postAdvice(Utililty.getCurruntDevice(getActivity()), adviceDataList);
        callAdvice.enqueue(new Callback<AdviceOutputData>() {
            @Override
            public void onResponse(Call<AdviceOutputData> call, Response<AdviceOutputData> response) {
                Log.i("adviceReponce---", response.body().toString());
                if (response.body().getStatus() == 1 && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < adviceDataList.size(); i++) {
                        db.updateAdviceIsoldByY(adviceDataList.get(i).getSwpNo());
                    }
                    adviceDataList = new ArrayList<AdviceInputData>();
                    adviceDataList = db.getAdviceAll();
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId()+"");
                    synInput.setSyncToId(response.body().getSyncToId()+"");
                    postAdviceSync(synInput);
                    Log.i("adviceData All==>", adviceDataList.toString());
                }
            }

            @Override
            public void onFailure(Call<AdviceOutputData> call, Throwable t) {
                Utililty.dismissProgressDialog();
            }
        });

    }

    /**
     * Post the FollowUp data base server
     */


    class LoadMasterData extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
            // Utililty.showProgressDailog(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //db.getallRegisterdUser();
            //db.insertDuartionData(getDurationfromAPI());
            //db.insertProgramDoctorsData(getDoctorsfromAPI());
            // db.insertMSWNameSystem(getMSWNameDataFromAPI());


            getVillagePI();
            getMedicineAPI();
            getFrequencieAPI();
            getSystemAPI();
            getSymptompsAPI();
            getInvestigationData();
            getProductDataAPI();
            //getDeviceAPI();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
           ;

           /* Utililty.dismissProgressDialog();
            Intent intent = new Intent(_activity, LoginActivity.class);
            startActivity(intent);
            finish();*/
        }

    }

    private void postFollowUpData() {
        followupDataList = db.getFollowupByIsoldN();
        Log.i("followupResponce", new Gson().toJson(followupDataList));
        Call<RegistrationResponse> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postFollowUpData(Utililty.getCurruntDevice(getActivity()), followupDataList);
        followupApiCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                Log.i("followup", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < followupDataList.size(); i++) {
                        db.updateFollowupsold(followupDataList.get(i).getSwpNo());
                    }
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
                followupDataList = new ArrayList<RegisterOutputData>();
                followupDataList = db.getallFollowupUser();

                Log.i("followupDataList==>", followupDataList.toString());
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Utililty.dismissProgressDialog();
                t.printStackTrace();
            }
        });
    }


    /**
     * Post the Opthal data base server
     */
    private void postOpthalData() {
        opthalDataList = db.getAllOphthalmolgyDataByIsoldN();
        Call<OpthalOuputResponse> opthalApicall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postophthalmology(Utililty.getCurruntDevice(getActivity()), opthalDataList);
        opthalApicall.enqueue(new Callback<OpthalOuputResponse>() {
            @Override
            public void onResponse(Call<OpthalOuputResponse> call, Response<OpthalOuputResponse> response) {
                Log.i("OpthalOuputResponse", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < opthalDataList.size(); i++) {
                        db.updateOpthalsold(opthalDataList.get(i).getSwpNo());
                    }
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<OpthalOuputResponse> call, Throwable t) {
                Utililty.dismissProgressDialog();
                t.printStackTrace();
            }
        });
    }

    /**
     * Get the Registration data base server
     */
    private void getRegistrationData() {
        final Call<RegistrationInputData> registerApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getRegisterData(Utililty.getCurruntDevice(getActivity()));
        registerApiCall.enqueue(new Callback<RegistrationInputData>() {
            @Override
            public void onResponse(Call<RegistrationInputData> call, Response<RegistrationInputData> response) {
                Log.i("getregistration==>", new Gson().toJson(response));

                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    registerDataList = new ArrayList<RegisterOutputData>();
                    registerDataList = response.body().getData();
                    db.insertUser(registerDataList);

                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());

                    PostSyncDataReg(synInput);
                }

            }

            @Override
            public void onFailure(Call<RegistrationInputData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Post the Sync data base server
     */
    private void PostSyncDataReg(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postSyncDataReg(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("getsyncstatus==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Get the Foloowup data base server
     */
    private void getFollowupData() {
        Call<RegistrationInputData> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getFollowupData(Utililty.getCurruntDevice(getActivity()));
        followupApiCall.enqueue(new Callback<RegistrationInputData>() {
            @Override
            public void onResponse(Call<RegistrationInputData> call, Response<RegistrationInputData> response) {
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertfollowupUser(response.body().getData());
                    Log.i("getfollowup==>", new Gson().toJson(response));
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                      PostSyncDataFollowup(synInput);
                }


            }

            @Override
            public void onFailure(Call<RegistrationInputData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Get the getOpthalData data base server
     */
    private void getOpthalData() {
        Call<OpthalResponse> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getophthalmologyAPI(Utililty.getCurruntDevice(getActivity()));
        followupApiCall.enqueue(new Callback<OpthalResponse>() {
            @Override
            public void onResponse(Call<OpthalResponse> call, Response<OpthalResponse> response) {
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertOphthalmolgy(response.body().getData());
                    Log.i("getOpthalResponce==>", new Gson().toJson(response));
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    PostSyncDataOpthomology(synInput);
                }


            }

            @Override
            public void onFailure(Call<OpthalResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Get the physiologyactivity data base server
     */
    private void getphysiologyData() {
        Call<PhysiotherapistResponse> physiologyApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getphysiologyAPI(Utililty.getCurruntDevice(getActivity()));
        physiologyApiCall.enqueue(new Callback<PhysiotherapistResponse>() {
            @Override
            public void onResponse(Call<PhysiotherapistResponse> call, Response<PhysiotherapistResponse> response) {
                Log.i("getphysioResponce==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertPhysiothrapistData(response.body().getData());

                   SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    PostSyncDataphysiogy(synInput);
                }


            }

            @Override
            public void onFailure(Call<PhysiotherapistResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Get the MSW Distribution  data base server
     */
    private void getMswDistributionData() {
        Call<MswDistributionResponse> followupApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getMswDistributionAPI(Utililty.getCurruntDevice(getActivity()));
        followupApiCall.enqueue(new Callback<MswDistributionResponse>() {
            @Override
            public void onResponse(Call<MswDistributionResponse> call, Response<MswDistributionResponse> response) {
                Log.i("getDistributionData==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertMswDistribution(response.body().getData());
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    PostSyncDataReg(synInput);
                }


            }

            @Override
            public void onFailure(Call<MswDistributionResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Post the Sync data Followup base server
     */
    private void PostSyncDataFollowup(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postSyncDataFollowup(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("getsyncstatus==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Post the Sync data Followup base server
     */
    private void PostSyncDataOpthomology(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postSyncOpthomology(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("syncOpthomology==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Post the Sync data Followup base server
     */
    private void PostSyncDataphysiogy(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postSyncphysiology(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("syncphysiogylogy==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Post the MSW Distribution data  base server
     */
    private void PostMswDistribution() {

        mswDistributionInputDataList= db.getMSWDistributionByIsoldN();
        Log.i("PostMswDistribution==>", new Gson().toJson(mswDistributionInputDataList));
        Call<MswDistributionOuputResponse> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postMswDistribution(Utililty.getCurruntDevice(getActivity()), mswDistributionInputDataList);
        syncApiCall.enqueue(new Callback<MswDistributionOuputResponse>() {
            @Override
            public void onResponse(Call<MswDistributionOuputResponse> call, Response<MswDistributionOuputResponse> response) {
                Log.i("PostMswDistribution==>", new Gson().toJson(response));
                mswDistributionInputDataList = db.getMSWDistributionByIsoldN();
                if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < mswAcitivyOutputList.size(); i++) {
                        db.updateMswsold(mswAcitivyOutputList.get(i).getMswName());
                    }
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MswDistributionOuputResponse> call, Throwable t) {
                t.printStackTrace();
                Utililty.dismissProgressDialog();
            }
        });
    }


    private void postphysiotherapistSession() {
        physiotherapistSessionOutputDataList = db.getAllPhysioSessionDataByIsNew();
        Log.d("physio",new Gson().toJson(physiotherapistSessionOutputDataList));

        Call<PhysiotherapistOuputResponse> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postphysiotherapistSession(Utililty.getCurruntDevice(getActivity()), physiotherapistSessionOutputDataList);
        syncApiCall.enqueue(new Callback<PhysiotherapistOuputResponse>() {
            @Override
            public void onResponse(Call<PhysiotherapistOuputResponse> call, Response<PhysiotherapistOuputResponse> response) {

                Log.i("postphysioSession==>", new Gson().toJson(response));

                if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < physiotherapistSessionOutputDataList.size(); i++) {
                        db.updatePhysiosSessionold(physiotherapistSessionOutputDataList.get(i).getDoctorId());
                    }

                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PhysiotherapistOuputResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Get others physiotherapistSession.
     */
    private void getphysiotherapistSession() {
        Call<PhysiotherapisSessiontResponse> getAdviceOtherTabData = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getphysiologySessionAPI(Utililty.getCurruntDevice(getActivity()));
        getAdviceOtherTabData.enqueue(new Callback<PhysiotherapisSessiontResponse>() {
            @Override
            public void onResponse(Call<PhysiotherapisSessiontResponse> call, Response<PhysiotherapisSessiontResponse> response) {
                Log.i("get Physio Session==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertPhysioSessionData(response.body().getData());
                    Log.i("Other Tab Advice==>==>", new Gson().toJson(response));
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    postSyncphysiologySession(synInput);
                }

            }

            @Override
            public void onFailure(Call<PhysiotherapisSessiontResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void postSyncphysiologySession(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postSyncphysiologySession(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                ;
                Log.i("physessionSync Resp==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

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
    private void getAdviceOtherTabAllData() {
        Call<AdviceGetOutputData> getAdviceOtherTabData = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getAdviceDataAPI(Utililty.getCurruntDevice(getActivity()));
        getAdviceOtherTabData.enqueue(new Callback<AdviceGetOutputData>() {
            @Override
            public void onResponse(Call<AdviceGetOutputData> call, Response<AdviceGetOutputData> response) {
                Log.i("Other Tab Advice==>==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.saveAdviceInput(response.body().getData());
                    Log.i("Other Tab Advice==>==>", new Gson().toJson(response));
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    postAdviceSync(synInput);
                }

            }

            @Override
            public void onFailure(Call<AdviceGetOutputData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Get others TAB Msw data and insert into local DB.
     */
    private void getMswTabAllData() {
        Call<MswActivityResponse> getAdviceOtherTabData = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getActivityMswAPI(Utililty.getCurruntDevice(getActivity()));
        getAdviceOtherTabData.enqueue(new Callback<MswActivityResponse>() {
            @Override
            public void onResponse(Call<MswActivityResponse> call, Response<MswActivityResponse> response) {
                Log.i("MSW ACtivity==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    db.insertMSWACtivity(response.body().getData());
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    postMswSync(synInput);
                }

            }

            @Override
            public void onFailure(Call<MswActivityResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Sync advice data with server to local DB.
     *
     * @param synInput
     */
    private void postAdviceSync(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postSyncAdvice(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                ;
                Log.i("Advice Sync Response==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    SynInput synInput = new SynInput();
                    synInput.setSyncFromId(response.body().getSyncFromId());
                    synInput.setSyncToId(response.body().getSyncToId());
                    postMswDistributionSync(synInput);

                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Sync advice data with server to local DB.
     *
     * @param synInput
     */
    private void postMswSync(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postMswAdvice(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("MSW Sync Response==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void postMswDistributionSync(SynInput synInput) {
        Call<SynOutput> syncApiCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postMswDistribution(Utililty.getCurruntDevice(getActivity()), synInput);
        syncApiCall.enqueue(new Callback<SynOutput>() {
            @Override
            public void onResponse(Call<SynOutput> call, Response<SynOutput> response) {
                Log.i("Distribution Sync ==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SynOutput> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void postMswActivityData() {
        mswAcitivyOutputList = db.getMSWByIsoldN();
        Log.d("postMswActivityData", new Gson().toJson(mswAcitivyOutputList));
        Call<MswOuputResponse> mswApicall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postMswActivity(Utililty.getCurruntDevice(getActivity()), mswAcitivyOutputList);
        mswApicall.enqueue(new Callback<MswOuputResponse>() {
            @Override
            public void onResponse(Call<MswOuputResponse> call, Response<MswOuputResponse> response) {
                Log.i("MSWActivity Response==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < mswAcitivyOutputList.size(); i++) {
                        db.updateMswsold(mswAcitivyOutputList.get(i).getMswName());
                    }
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MswOuputResponse> call, Throwable t) {
                t.printStackTrace();
                Utililty.dismissProgressDialog();
            }
        });
    }

    private void postphysiotherapist() {
        physiotherapistOutputDataList = db.getAllPhysiotherapistDataByIsNew();
        Log.d("postPhysioData", new Gson().toJson(physiotherapistOutputDataList));
        Call<PhysiotherapistOuputResponse> physiotherapistOuputResponseCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).postphysiotherapist(Utililty.getCurruntDevice(getActivity()), physiotherapistOutputDataList);
        physiotherapistOuputResponseCall.enqueue(new Callback<PhysiotherapistOuputResponse>() {
            @Override
            public void onResponse(Call<PhysiotherapistOuputResponse> call, Response<PhysiotherapistOuputResponse> response) {
                Log.i("physiot Response==>", new Gson().toJson(response));
                if (response.body().getStatus().equalsIgnoreCase("1") && response.body().getMessage().equalsIgnoreCase("Successfully added.")) {
                    for (int i = 0; i < physiotherapistOutputDataList.size(); i++) {
                        db.updatePhysiosold(physiotherapistOutputDataList.get(i).getSwpNo());
                    }
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<PhysiotherapistOuputResponse> call, Throwable t) {
                Utililty.dismissProgressDialog();
            }
        });
    }


    //Village Pada
    private void getVillagePI() {
        Call<VillageData> villageAPICall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getvillageDataList();
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


    //Medicine
    private void getMedicineAPI() {
        Call<MedicineData> medicineOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getmedicineDataList();
        medicineOutputDataCall.enqueue(new Callback<MedicineData>() {
            @Override
            public void onResponse(Call<MedicineData> call, Response<MedicineData> response) {
                medicineData = response.body();
                Log.i("getMedicineData==>", new Gson().toJson(medicineData));
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
    private void getFrequencieAPI() {
        Call<FrequencieData> medicineOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getfrequenciesDataList();
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
        Call<SystemData> systemOutputDataCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getsystemDataList();
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
        Call<ProductsData> productsDataCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getproductsDataList();
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
        Call<SymptompsData> symptomsDataCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getsymptoms();
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
        Call<Investigation> symptomsDataCall = RefrofitGetClient.getsRetrofitClientInterface(getActivity()).getInvestigationData();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                postRegisterData();
                break;
            case R.id.btnGet:
                new LoadMasterData().execute();
                new GetAPIData().execute();
                break;
        }
    }


    class LoadAPIData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utililty.showProgressDailog(getActivity());
        }

        @Override
        protected Void doInBackground(Void... params) {
            postAdviceData();
            postFollowUpData();
            postMswActivityData();
            PostMswDistribution();
            postOpthalData();
            postphysiotherapist();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Utililty.dismissProgressDialog();

        }
    }


    class GetAPIData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utililty.showProgressDailog(getActivity());
        }

        @Override
        protected Void doInBackground(Void... params) {
            getRegistrationData();
            getFollowupData();
            getAdviceOtherTabAllData();
            getMswTabAllData();
            getMswDistributionData();
            getOpthalData();
            getphysiologyData();
            getphysiotherapistSession();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Utililty.dismissProgressDialog();

        }
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
