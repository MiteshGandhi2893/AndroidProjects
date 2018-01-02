package com.example.miteshgandhi.a15_handgesturestocontrolscreens_cs580;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import java.util.Timer;
import java.util.TimerTask;

public class AnimateRocket extends AppCompatActivity {

    private SensorManager sensorManager;
    int status=1;
    int count=0;
    private SensorEventListener proximitySensorListener;
   private Sensor proximitySensor;
    ValueAnimator valueAnimator;
    ImageView rocket,welcome;
    Animation animationFadein;
    long totalSeconds = 30;
    long intervalSeconds = 1;
    CountDownTimer timer;

    float value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_rocket);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        rocket=(ImageView)findViewById(R.id.rocket);
        valueAnimator=new ValueAnimator();
        welcome=(ImageView)findViewById(R.id.welcome);



        Intent intent =new Intent(this,UnlockService.class);

       // startService(intent);


         timer = new CountDownTimer(totalSeconds * 90, intervalSeconds * 90) {

            public void onTick(long millisUntilFinished) {
               count++;
            }

            public void onFinish() {
                sensorManager.unregisterListener(proximitySensorListener);
                startActivity(new Intent(AnimateRocket.this,MainActivity.class));

            }

        };


    }


    @Override
    public void onResume() {
        super.onResume();
          count=0;
        proximitySensorListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                // Nothing is nearby

                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {

                    count++;
                    try {
                        if (count == 1) {
                            animateRocket();
                            FadeWelcome();
                             count = 0;               ;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

            } else

            {

                //count=0;

                //sensorManager.unregisterListener(proximitySensorListener);


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



    public void animateRocket()
    {
        timer.start();
        valueAnimator=ValueAnimator.ofFloat(0f,-2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                value= (float) valueAnimator.getAnimatedValue();
                rocket.setTranslationY(value);

            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.start();

    }
    public void FadeWelcome()
    {
       // welcome.animate().alpha(0f).setDuration(1000);
        valueAnimator=ValueAnimator.ofFloat(0f,-500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                value= (float) valueAnimator.getAnimatedValue();
                welcome.setTranslationY(value);

            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.start();

    }







}
