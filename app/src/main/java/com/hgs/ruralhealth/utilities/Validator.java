package com.hgs.ruralhealth.utilities;

import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pratikb on 17-08-2016.
 */
public class Validator {
    public static boolean isvalidText(EditText txt) {
        if (txt.getText().toString().trim().length() > 0) {
            return true;

        } else {
            return false;
        }

    }

    public static boolean isvalidText(TextView txt) {
        if (txt.getText().toString().trim().length() > 0) {
            return true;

        } else {
            return false;
        }

    }

    public static boolean isValidmobile(EditText mobile) {
        if (mobile.getText().toString().trim().length() >= 10) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isValidEmailAddress(EditText email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email.getText().toString().trim());
        return m.matches();
    }

    public static boolean checkemail(EditText email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[gmail.com]+");
        Matcher matcher = pattern.matcher(email.getText().toString().trim());
        if (matcher.matches() && email.hasFocus()) {
            return true;
        } else {
            return false;
        }
    }

}
