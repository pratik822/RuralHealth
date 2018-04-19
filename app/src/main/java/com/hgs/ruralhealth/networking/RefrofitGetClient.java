package com.hgs.ruralhealth.networking;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hgs.ruralhealth.fragments.ActivityModel;
import com.hgs.ruralhealth.model.Device.Device;
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
import com.hgs.ruralhealth.model.masterdata.Investigation;
import com.hgs.ruralhealth.model.masterdata.InvestigationData;
import com.hgs.ruralhealth.model.masterdata.MedicineData;
import com.hgs.ruralhealth.model.masterdata.ProductsData;
import com.hgs.ruralhealth.model.masterdata.SymptompsData;
import com.hgs.ruralhealth.model.masterdata.SystemData;
import com.hgs.ruralhealth.model.masterdata.VillageData;
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
import com.hgs.ruralhealth.model.user.UserInputData;
import com.hgs.ruralhealth.model.user.UserOutputData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by rameshg on 8/12/2016.
 */
public class RefrofitGetClient {
    private static Retrocallback sRetrofitClientInterface;

    //private static String url1 = "http://124.30.44.228:9008"; // Public
     private static String url1 = "http://124.30.44.228:9006"; // Public
    // private static String url1 = "http://172.16.60.36:9006"; // Private

    public static Retrocallback getsRetrofitClientInterface(Context context) {
        if (sRetrofitClientInterface == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url1)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(client)
                    .build();
            sRetrofitClientInterface = retrofit.create(Retrocallback.class);
        }
        return sRetrofitClientInterface;
    }

    public interface Retrocallback {

        //+++++++++++++++  Master data API  +++++++++++++++//
        @GET("/api/villages/")
        Call<VillageData> getvillageDataList();

        @GET("/api/medicines/")
        Call<MedicineData> getmedicineDataList();


        @GET("/api/frequencies/")
        Call<FrequencieData> getfrequenciesDataList();


        @GET("/api/systems/")
        Call<SystemData> getsystemDataList();

        @GET("/api/products/")
        Call<ProductsData> getproductsDataList();


        @GET("/api/symptoms/")
        Call<SymptompsData> getsymptoms();

        //+++++++++++++++  Login data API  +++++++++++++++//
        @POST("/api/login")
        Call<UserOutputData> postUserLogin(@Body UserInputData data);

        //+++++++++++++++  Registration data API  +++++++++++++++//
        @POST("/api/patients/")
        Call<RegistrationResponse> postRegisterData(@Query("deviceId") String deviceId, @Body List<RegisterOutputData> data);

        @GET("/api/patients/")
        Call<RegistrationInputData> getRegisterData(@Query("deviceId") String deviceId);

        @POST("/api/syncstatuspatients/")
        Call<SynOutput> postSyncDataReg(@Query("deviceId") String deviceId, @Body SynInput data);

        //+++++++++++++++  Follow up data API  +++++++++++++++//
        @POST("/api/followup/")
        Call<RegistrationResponse> postFollowUpData(@Query("deviceId") String deviceId, @Body List<RegisterOutputData> data);

        @GET("/api/followup/")
        Call<RegistrationInputData> getFollowupData(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusfollowup/")
        Call<SynOutput> postSyncDataFollowup(@Query("deviceId") String deviceId, @Body SynInput data);

        //+++++++++++++++  Advice data API  +++++++++++++++//
        @POST("/api/advice/")
        Call<AdviceOutputData> postAdvice(@Query("deviceId") String deviceId, @Body List<AdviceInputData> adviceInputDatas);

        @GET("/api/advice/")
        Call<AdviceGetOutputData> getAdviceDataAPI(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusadvice/")
        Call<SynOutput> postSyncAdvice(@Query("deviceId") String deviceId, @Body SynInput data);


        //+++++++++++++++  MSW ACTIVITY data API  +++++++++++++++//
        @POST("/api/mswactivity/")
        Call<MswOuputResponse> postMswActivity(@Query("deviceId") String deviceId, @Body List<MswAcitivyInputData> mswAcitivyInputDatas);

        @GET("/api/mswactivity/")
        Call<MswActivityResponse> getActivityMswAPI(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusmswactivity/")
        Call<SynOutput> postMswAdvice(@Query("deviceId") String deviceId, @Body SynInput data);


        //+++++++++++++++  MSW DISTRIBUTION data API  +++++++++++++++//
        @POST("/api/mswdistribution/")
        Call<MswDistributionOuputResponse> postMswDistribution(@Query("deviceId") String deviceId, @Body List<Msw_Distribution_InputData> mswAcitivyInputDatas);

        @GET("/api/mswdistribution/")
        Call<MswDistributionResponse> getMswDistributionAPI(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusmswdistribution/")
        Call<SynOutput> postMswDistribution(@Query("deviceId") String deviceId, @Body SynInput data);

        //+++++++++++++++   ophthalmology data API  +++++++++++++++//
        @POST("/api/ophthalmology/")
        Call<OpthalOuputResponse> postophthalmology(@Query("deviceId") String deviceId, @Body List<OphthalInputData> mswAcitivyInputDatas);

        @GET("/api/ophthalmology/")
        Call<OpthalResponse> getophthalmologyAPI(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusophthalmology/")
        Call<SynOutput> postSyncOpthomology(@Query("deviceId") String deviceId, @Body SynInput data);


        /*//+++++++++++++++   Physiotheripist data API  +++++++++++++++/*/
        @POST("/api/physiologyactivity/")
        Call<PhysiotherapistOuputResponse> postphysiotherapist(@Query("deviceId") String deviceId, @Body List<PhysiotherapistInputData> physiotherapistInputData);

        @GET("/api/physiologyactivity/")
        Call<PhysiotherapistResponse> getphysiologyAPI(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusphysiologyactivity/")
        Call<SynOutput> postSyncphysiology(@Query("deviceId") String deviceId, @Body SynInput data);


        //          Physiotheripist Session Data


        /*//+++++++++++++++   Physiotheripist data API  +++++++++++++++/*/
        @POST("/api/physiologysession/")
        Call<PhysiotherapistOuputResponse> postphysiotherapistSession(@Query("deviceId") String deviceId, @Body List<PhysioSessionInputData> physiotherapistSessionmInputData);

       @GET("/api/physiologysession/")
        Call<PhysiotherapisSessiontResponse> getphysiologySessionAPI(@Query("deviceId") String deviceId);

        @POST("/api/syncstatusphysiologysession/")
        Call<SynOutput> postSyncphysiologySession(@Query("deviceId") String deviceId, @Body SynInput data);

        //+++++++++++++++  Get the Verified complete data  +++++++++++++++//

        //Reg backup
        @GET("/api/patients/")
        Call<RegistrationInputData> getRegisterVerifiedData(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatuspatients/")
        Call<SynOutput> postSyncDataRegBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        //FollowUp backup
        @GET("/api/followup/")
        Call<RegistrationInputData> getFollowupBackupData(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusfollowup/")
        Call<SynOutput> postSyncDataFollowupBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        //Advice backup
        @GET("/api/advice/")
        Call<AdviceGetOutputData> getAdviceBackupApi(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusadvice/")
        Call<SynOutput> postSyncAdviceBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        //Ophthalmology Backup
        @GET("/api/ophthalmology/")
        Call<OpthalResponse> getophthalmologyBackupAPI(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusophthalmology/")
        Call<SynOutput> postSyncOpthomologyBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        //Physiotherapist BackUp
        @GET("/api/physiologyactivity/")
        Call<PhysiotherapistResponse> getphysiologyAPIBackup(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusphysiologyactivity/")
        Call<SynOutput> postSyncphysiologyBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        //MSW Activity BackUp
        @GET("/api/mswactivity/")
        Call<MswActivityResponse> getActivityMswBackupAPI(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusmswactivity/")
        Call<SynOutput> postMswAdviceBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        //MSW Distribution BackUp
        @GET("/api/mswdistribution/")
        Call<MswDistributionResponse> getMswDistributionBackupAPI(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusmswdistribution/")
        Call<SynOutput> postMswDistributionBackup(@Query("deviceId") String deviceId, @Body SynInput data);


        //Physio Session  BackUp
        @GET("/api/physiologysession/")
        Call<PhysiotherapisSessiontResponse> getPhysioSessionBackupAPI(@Query("deviceId") String deviceId,@Query("all") String all);

        @POST("/api/syncstatusphysiologysession/")
        Call<SynOutput> postPhysioSessionBackup(@Query("deviceId") String deviceId, @Body SynInput data);

        @POST("/api/validatedevice/")
        @FormUrlEncoded
        Call<SynOutput> postValidateapi(@Field("serialNumber") String serialNumber);


        @GET("/api/investigation/")
        Call<Investigation> getInvestigationData();

        @GET("/api/devices/")
        Call<Device> getDevices();


        @GET("/api/activities/")
        Call<ActivityModel> getactivitys();


    }

}
