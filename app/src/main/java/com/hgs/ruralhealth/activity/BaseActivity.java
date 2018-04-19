package com.hgs.ruralhealth.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.fragments.HomeFragment;
import com.hgs.ruralhealth.fragments.RegistrationFragment;

/**
 * Created by rameshg on 9/2/2016.
 */
public class BaseActivity extends AppCompatActivity  {
    photo photos;
    public static Bitmap mybit;

    private Object setBitmap;
    private recvr.Mypic registrationFragmentListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        //photos=(photo)this;
        Fragment fragment = new HomeFragment();
        if(null!= fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap myphoto = (Bitmap) data.getExtras().get("data");
            registrationFragmentListener.mypic(myphoto);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Log.d("myid",telephonyManager.getDeviceId());
    }
    public void setOnActivityResultListener(@NonNull recvr.Mypic registrationFragment) {
        this.registrationFragmentListener = registrationFragment;
    }
}
