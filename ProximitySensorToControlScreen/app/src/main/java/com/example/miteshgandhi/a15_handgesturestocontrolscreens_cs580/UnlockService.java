package com.example.miteshgandhi.a15_handgesturestocontrolscreens_cs580;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class UnlockService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private SensorEventListener proximitySensorListener;
    private Sensor proximitySensor;
    int count = 0;
    int count2 = 0;

    public UnlockService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        count = 0;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        sensorManager.registerListener(this,
                proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);


        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {


            count++;

            boolean active = MainActivity.mDPM.isAdminActive(MainActivity.mDeviceAdminReceiver);

            if (count == 2) {
                if (active) {
                    MainActivity.mDPM.lockNow();
                    count = 0;
                    MainActivity.count2 = 0;
                }
            } else {

            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}