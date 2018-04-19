package com.hgs.ruralhealth.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by pratikb on 19-06-2017.
 */
public class recvr extends BroadcastReceiver {
    Mypic mypic;

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();


        Log.i("Receiver", "Broadcast received: " + action);

        if (action.equals("my.action.string")) {
            try {
                mypic = (Mypic) context;
                Bitmap state = intent.getExtras().getParcelable("extra");
                mypic.mypic(state);
                Log.i("Receiver", "Broadcast received: " + state);
            } catch (ClassCastException ex) {
                ex.printStackTrace();
            }

        }
    }

    public interface Mypic {
        public Bitmap mypic(Bitmap pic);
    }
}
