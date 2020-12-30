package com.example.getsensorvalue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getsensorvalue.NotificationService.NotificationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor mSensorLight, mSensorProximity, mSensorAccelerometer, mSensorGyroscope;

    private TextView mtxtSensorLight, mtxtSensorProximity, mtxtSensorAccelerometer, mtxtSensorGyroscope;

    private String lightValue, proximityValue, accelerometerValue, gyrospoeValue;
    private String time;

    DatabaseHelper databaseHelper;
    static ArrayList arrayListLight, arrayListProximity, arrayListAccelerometer, arrayListGyrosope, arrayListTime;


    private static final long START_TIME_IN_MILLIS = 300000;
    private TextView mTextViewCountDown, textView;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        mtxtSensorLight = findViewById(R.id.light_txt_id);
        mtxtSensorProximity = findViewById(R.id.proximity_txt_id);
        mtxtSensorAccelerometer = findViewById(R.id.accelerometer_txt_id);
        mtxtSensorGyroscope = findViewById(R.id.gyroscope_txt_id);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        String sensor_error = getResources().getString(R.string.error);


        databaseHelper = new DatabaseHelper(this);

        arrayListLight = databaseHelper.getLight();
        arrayListProximity = databaseHelper.getProximity();
        arrayListAccelerometer = databaseHelper.getAccelerometer();
        arrayListGyrosope = databaseHelper.getGyrospoe();
        arrayListTime = databaseHelper.getTime();


        mTextViewCountDown = findViewById(R.id.txt_countdown);
        textView = findViewById(R.id.txt_time);
        mtxtSensorLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String light="Light Sensor Chart";
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                intent.putStringArrayListExtra("time", arrayListTime);
                intent.putStringArrayListExtra("value", arrayListLight);
                intent.putExtra("title", light);
                startActivity(intent);
            }
        });
        mtxtSensorProximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proximity="Proximity Sensor Chart";
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                intent.putStringArrayListExtra("time", arrayListTime);
                intent.putStringArrayListExtra("value", arrayListProximity);
                intent.putExtra("title", proximity);
                startActivity(intent);
            }
        });
        mtxtSensorAccelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accelerometer="Accelerometer Sensor Chart";
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                intent.putStringArrayListExtra("time", arrayListTime);
                intent.putStringArrayListExtra("value", arrayListAccelerometer);
                intent.putExtra("title", accelerometer);
                startActivity(intent);
            }
        });
        mtxtSensorGyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gyroscope="Gyroscope Sensor Chart";
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                intent.putStringArrayListExtra("time", arrayListTime);
                intent.putStringArrayListExtra("value", arrayListGyrosope);
                intent.putExtra("title", gyroscope);
                startActivity(intent);
            }
        });

        startTimer();
        updateCountdownText();

        if (mSensorLight == null) {
            mtxtSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mtxtSensorProximity.setText(sensor_error);
        }

        if (mSensorAccelerometer == null) {
            mtxtSensorAccelerometer.setText(sensor_error);
        }

        if (mSensorGyroscope == null) {
            mtxtSensorGyroscope.setText(sensor_error);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
        if (mSensorLight != null) {
            sensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorProximity != null) {
            sensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorAccelerometer != null) {
            sensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorGyroscope != null) {
            sensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        float currentValue = event.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mtxtSensorLight.setText(getResources().getString(R.string.sensor_light, currentValue));
                lightValue = String.format(Locale.getDefault(), "%1$.2f", currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mtxtSensorProximity.setText(getResources().getString(R.string.sensor_proximity, currentValue));
                proximityValue = String.format(Locale.getDefault(), "%1$.2f", currentValue);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mtxtSensorAccelerometer.setText(getResources().getString(R.string.sensor_accelerometer, currentValue));
                accelerometerValue = String.format(Locale.getDefault(), "%1$.2f", currentValue);
                break;
            case Sensor.TYPE_GYROSCOPE:
                mtxtSensorGyroscope.setText(getResources().getString(R.string.sensor_gyroscope, currentValue));
                gyrospoeValue = String.format(Locale.getDefault(), "%1$.2f", currentValue);
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {


                mTimerRunning = false;
                resetTimer();
                mCountDownTimer.cancel();
                addValue();
                this.start();

            }
        }.start();
        mTimerRunning = true;

    }

    private void resetTimer() {

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountdownText();

    }

    private void updateCountdownText() {

        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeleft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeleft);

    }

    private void addValue() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        time = dateFormat.format(new Date());
        textView.setText(time);
        String timeValue = textView.getText().toString();

            if (databaseHelper.insert(lightValue, proximityValue, accelerometerValue, gyrospoeValue, timeValue)) {
                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();


                arrayListLight.clear();
                arrayListProximity.clear();
                arrayListAccelerometer.clear();
                arrayListGyrosope.clear();
                arrayListTime.clear();

                arrayListLight.addAll(databaseHelper.getLight());
                arrayListProximity.addAll(databaseHelper.getProximity());
                arrayListAccelerometer.addAll(databaseHelper.getAccelerometer());
                arrayListGyrosope.addAll(databaseHelper.getGyrospoe());
                arrayListTime.addAll(databaseHelper.getTime());


            }

    }
}