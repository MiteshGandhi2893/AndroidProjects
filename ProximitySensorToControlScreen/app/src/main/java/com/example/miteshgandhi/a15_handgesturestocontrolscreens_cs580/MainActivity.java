package com.example.miteshgandhi.a15_handgesturestocontrolscreens_cs580;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.UnicodeSetSpanner;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    CountDownTimer timer;
    SensorManager sensorManager;
    SensorEventListener proximitySensorListener;
    Sensor proximitySensor;
    public int status_flag=1;
    Button open_Animation;

    Switch openwhatsapp,lockscreen;




    EditText texts;
  public static int count=0;
   public static int count2=0;
    public static final int STATUS=11;
     public static DevicePolicyManager mDPM;
    public static  ComponentName mDeviceAdminReceiver;
    public static  ActivityManager avt;
    long totalSeconds = 30;
    long intervalSeconds = 1;
Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
            proximitySensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        avt=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
        mDeviceAdminReceiver = new ComponentName(this, Lock_Unlock.class);
        count=0;

        count2=0;

        lockscreen=(Switch)findViewById(R.id.lockscreen);
        openwhatsapp=(Switch)findViewById(R.id.openwhatsapp);



        sensorManager.unregisterListener(proximitySensorListener);
 intent=new Intent(MainActivity.this,UnlockService.class);
        lockscreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    startService(intent);
                    enableAdmin();
                    count2=0;
                }
                else
                {

                    count=0;
                    count2=0;
                    boolean active = mDPM.isAdminActive(mDeviceAdminReceiver);
                    if(active)
                    {
                        sensorManager.unregisterListener(proximitySensorListener);
                        mDPM.removeActiveAdmin(mDeviceAdminReceiver);
                        stopService(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"no available",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    openwhatsapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b)
            {
                count2=0;
                boolean active = mDPM.isAdminActive(mDeviceAdminReceiver);
                sensorManager.registerListener(proximitySensorListener,
                        proximitySensor, 2 * 1000 * 1000);

                if(active)
                {

                    mDPM.removeActiveAdmin(mDeviceAdminReceiver);
                    stopService(intent);
                }

                Toast.makeText(getApplicationContext(), " 5 sec Wave gesture set to send message on  whatsapp", Toast.LENGTH_SHORT).show();

            }
        }
    });










    }

int c=0;

    public void enableAdmin() {

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "This app needs admin rights to be able to lock the screen.");
        startActivityForResult(intent, STATUS);
    }
    public void disableAdmin() {

        mDPM.removeActiveAdmin(mDeviceAdminReceiver);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode)
        {

            case STATUS:
                if(resultCode== Activity.RESULT_OK)
                {
                    //Toast.makeText(this,String.valueOf(requestCode)+resultCode+""+Activity.RESULT_OK,Toast.LENGTH_LONG).show();
                  //Toast.makeText(this,"enabled featurs",Toast.LENGTH_LONG).show();


                }
                else
                {
                    //Toast.makeText(this,String.valueOf(requestCode)+resultCode+""+Activity.RESULT_OK,Toast.LENGTH_LONG).show();
                    //Toast.makeText(this,"disabled featurs",Toast.LENGTH_LONG).show();

                }
                break;
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        count=0;
        boolean active = mDPM.isAdminActive(mDeviceAdminReceiver);
        proximitySensorListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(openwhatsapp.isChecked())
                {
                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {

                        count2++;
                        if (count2 == 2) {

                            PackageManager pm = MainActivity.this.getPackageManager();
                            try {
                                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                Intent waIntent = new Intent(Intent.ACTION_SEND);
                                waIntent.putExtra("jid", "16073388455" + "@s.whatsapp.net");
                                waIntent.setType("text/plain");
                                waIntent.setPackage("com.whatsapp");
                                waIntent.putExtra(Intent.EXTRA_TEXT, "Hi Poorvi");
                                startActivity(waIntent);

                                count2 = 0;
                            } catch (PackageManager.NameNotFoundException e) {
                                Toast.makeText(MainActivity.this, "Please install whatsapp app ", Toast.LENGTH_SHORT).show();
                                count2 = 0;


                            }


                        }
                    }
                }
                else
                {

                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(proximitySensorListener,
                proximitySensor, 2 * 1000 * 1000);


    }

    @Override
    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);

    }







}
