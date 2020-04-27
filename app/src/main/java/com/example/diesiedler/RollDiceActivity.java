package com.example.diesiedler;


import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.RollDice;

public class RollDiceActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private final int SHAKE_THRESHOLD = 8;
    private int sum;
    private int statusStarts = 0;
    private int finalSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_shaker);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];


        float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

        if (acceleration > SHAKE_THRESHOLD) {
            this.statusStarts = 1;
            RollDice rollDice = new RollDice(this);
            sum = rollDice.rollDice();
            Log.d("DEBUG", "Sum is: " + sum);
        }
        if (Math.abs(acceleration) < 0.1 && statusStarts == 1) {
            finalSum = sum;
            sensorManager.unregisterListener(this);
            Log.d("DEBUG", "FINAL VALUE IS: " + finalSum);
            new AlertDialog.Builder(this).setTitle("YOUR VALUE").setMessage("Du hast " + finalSum + " gewürfelt.").show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
