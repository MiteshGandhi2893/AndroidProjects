package com.example.miteshgandhi.a15_handgesturestocontrolscreens_cs580;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by miteshgandhi on 11/13/17.
 */

public class Lock_Unlock extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        //super.onEnabled(context, intent);
       // Toast.makeText(context,"admin enabled",Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onDisabled(Context context, Intent intent) {

        // super.onDisabled(context, intent);
       // Toast.makeText(context,"no",Toast.LENGTH_SHORT).show();


    }
}
