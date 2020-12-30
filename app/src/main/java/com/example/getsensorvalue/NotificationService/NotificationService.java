package com.example.getsensorvalue.NotificationService;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.getsensorvalue.MainActivity;
import com.example.getsensorvalue.R;

import static com.example.getsensorvalue.NotificationService.App.CHANNEL_ID;

public class NotificationService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor mSensorLight, mSensorProximity, mSensorAccelerometer, mSensorGyroscope;

    private String lightValue, proximityValue, accelerometerValue, gyrospoeValue, bindValue;

    @Override
    public void onCreate() {
        super.onCreate(); sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

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
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        float currentValue = event.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:

                lightValue = "Light Sensor Value: " + currentValue;
                break;
            case Sensor.TYPE_PROXIMITY:
                proximityValue = "Proximity Sensor Value: " + currentValue;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerValue = "Accelerometer Sensor Value: " + currentValue;
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyrospoeValue = "Gyroscope Sensor Value: " + currentValue;
                break;
            default:
        }

        bindValue = lightValue + "\n" + proximityValue + "\n" + accelerometerValue + "\n" + gyrospoeValue;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(bindValue)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);
        startForeground(1, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                while (true) {
                    notification.setContentText(bindValue);
                    notification.setStyle(new NotificationCompat.BigTextStyle().bigText(bindValue));
                    startForeground(1, notification.build());
                }

            }
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
