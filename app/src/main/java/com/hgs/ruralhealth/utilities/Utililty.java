package com.hgs.ruralhealth.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.hgs.ruralhealth.R;
import com.hgs.ruralhealth.activity.BaseActivity;
import com.hgs.ruralhealth.activity.LoginActivity;
import com.hgs.ruralhealth.database.DBHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by rameshg on 9/21/2016.
 */
public class Utililty {

    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static String currentDate;
    public static ProgressDialog progressDialog;
    static String month;
    public static void setTitle(Activity activity, String title) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setTitle(title);
        /*TextView textview = new TextView(activity);
        LayoutParams layoutparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setText("ActionBar Title");
        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(30);
        textview.setText(title);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textview);*/

    }

    public static void logout(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();
        // editor = preferences.edit();
        editor.remove("flag").commit();
        Toast.makeText(activity, "Logout Sucsessfully", Toast.LENGTH_LONG).show();
        activity.finish();
        Intent loginInent = new Intent(activity, LoginActivity.class);
        activity.startActivity(loginInent);
    }

    /**
     * Generate the SWP Number dynamically
     *
     * @param act
     * @param villageName
     * @return swpNo
     */
    public static String genrate_SWN(Activity act, String villageName) {
        String swpNo = "";
        Random ran = new Random();
        int x = ran.nextInt(1000);
        DBHelper db = new DBHelper(act);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(act);



        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        String date=df.format(c.getTime());
        String dates[]=date.split("-");
        for(int i=0;i<dates.length;i++){
            Log.d("dates",dates[i]);
        }

       for(int i=0;i<=12;i++){
           int name = c.get(Calendar.MONTH)+1;
            if(i==name){
                 month="0"+name;
            }

       }

        Log.d("myname",month+"");
        if (preferences.getString("deviceNo", null) != null) {
           swpNo = villageName + preferences.getString("deviceNo", null) + dates[0] + month + dates[2] + (db.getallRegisterdUsersize(preferences.getString("deviceNo", null)).size()+1);
            Log.i("SWPNO==>", swpNo);
        }
        return swpNo;
    }

    public static String getDoctorName(Activity act) {
        String doctorName = "";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(act);
        if (preferences.getString("DoctorName", null) != null) {
            doctorName = preferences.getString("DoctorName", null);
        }
        return doctorName;
    }

    public static List<String> getG() {
        List<String> getGPLDA = new ArrayList<>();
        getGPLDA.add("G");
        for (int i = 1; i <= 5; i++) {
            getGPLDA.add(String.valueOf(i));
        }

        return getGPLDA;
    }

    public static List<String> getP() {
        List<String> getGPLDA = new ArrayList<>();
        getGPLDA.add("P");
        for (int i = 1; i <= 5; i++) {
            getGPLDA.add(String.valueOf(i));
        }

        return getGPLDA;
    }

    public static List<String> getL() {
        List<String> getGPLDA = new ArrayList<>();
        getGPLDA.add("L");
        for (int i = 1; i <= 5; i++) {
            getGPLDA.add(String.valueOf(i));
        }

        return getGPLDA;
    }

    public static List<String> getD() {
        List<String> getGPLDA = new ArrayList<>();
        getGPLDA.add("D");
        for (int i = 1; i <= 5; i++) {
            getGPLDA.add(String.valueOf(i));
        }

        return getGPLDA;
    }

    public static List<String> getA() {
        List<String> getGPLDA = new ArrayList<>();
        getGPLDA.add("A");
        for (int i = 1; i <= 5; i++) {
            getGPLDA.add(String.valueOf(i));
        }

        return getGPLDA;
    }

    public static List<String> getF() {
        List<String> getFTND = new ArrayList<>();
        getFTND.add("F");
        for (int i =1; i <= 4; i++) {
            getFTND.add(String.valueOf(i));
        }
        return getFTND;
    }

    public static List<String> getT() {
        List<String> getFTND = new ArrayList<>();
        getFTND.add("T");
        for (int i = 1; i <= 4; i++) {
            getFTND.add(String.valueOf(i));
        }
        return getFTND;
    }

    public static List<String> getN() {
        List<String> getFTND = new ArrayList<>();
        getFTND.add("N");
        for (int i = 1; i <= 4; i++) {
            getFTND.add(String.valueOf(i));
        }
        return getFTND;
    }

    public static List<String> getDa() {
        List<String> getFTND = new ArrayList<>();
        getFTND.add("D");
        for (int i = 1; i <= 4; i++) {
            getFTND.add(String.valueOf(i));
        }
        return getFTND;
    }

    public static String datePickDialog(final Activity activity) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                currentDate = dateFormatter.format(newDate.getTime());
                TextView dateTxt = (TextView) activity.findViewById(R.id.dateTxt);
                dateTxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        return currentDate;
    }

    public static String getCurDate() {
        String date = "";
        Calendar newDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(newDate.getTime());
        return date;
    }


    public static void alertMessage(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * method to close the device on screen keyboard
     */
    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Hide soft keyboard
     *
     * @param view : The respective view for which soft keyboard opened.
     */
    public static void hideSoftKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showProgressDailog(Activity activity) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        progressDialog.dismiss();
    }


    /**
     * method to show alert dialog
     */
    public static void alertDialogShow(Activity activity, String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /**
     * method to show alert dialog and dismiss on OK
     */
    public static void alertDialogShowAndDismiss(final Activity activity, String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        activity.finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void alertDialogMSWShowAndDismiss(final Activity activity, String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent home = new Intent(activity, BaseActivity.class);
                        activity.startActivity(home);
                        activity.finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public static boolean isInternetConnectionAlive(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable();
    }

    public static int getDoctorId(Context ctx) {
        int getDoctorId = 0;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        if (preferences.getInt("doctorId", 0) > 0) {
            getDoctorId = preferences.getInt("doctorId", 0);
            Log.d("getdoctor", getDoctorId + "");
        }


        return getDoctorId;
    }

    public static String getCurruntDevice(Context ctx) {
        String deviceName = "";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        if (preferences.getString("deviceNo", null) != null) {
            deviceName = preferences.getString("deviceNo", null);
        }

        return deviceName;
    }

    public static String getSystemDeviceId(Context ctx) {

        String android_id = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return android_id;
    }


    public static String getSystemDevice(Context ctx) {
        SharedPreferences.Editor editor = null;
        String android_id = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putString("sysId", android_id);
        editor.commit();
        return android_id;
    }

}
