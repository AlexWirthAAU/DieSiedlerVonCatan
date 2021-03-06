package com.example.diesiedler;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.RollDice;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;

/**
 * @author Alex Wirth
 * @author Fabian Schaffenrath (edit)
 * <p>
 * This activity is the beginning of a player's turn. It has to be executed every begiinig of a new turn. Only if the player diced, he can continue his turn.
 * Player is able to shake dive to generate some random value between 2-12.
 * The value will then be sent to the Server where the ressources are allocated depending on the value.
 * If value 7 is diced, the player can re-place the thief, which will be handled by an extra activity.
 */
public class RollDiceActivity extends AppCompatActivity implements SensorEventListener {

    //Senors
    private SensorManager sensorManager;
    private Sensor accelerometer;

    //SHAKE_Threshold: How heavy does the user have to shake the device
    private final int SHAKE_THRESHOLD = 8;
    private int sum;
    private int statusStarts = 0;
    private int finalSum = 0;

    private Handler handler = new RollDiceHandler(Looper.getMainLooper(), this); // Handler

    /**
     * Sets Sensors, the Handler and lets the Device Vibrate.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_shaker);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(200);
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ClientData.currentHandler = handler;
    }

    @Override
    public void onBackPressed() {
        // Back button should not return to the previous Activity here.
    }


    @Override
    protected void onResume() {
        super.onResume();
        ClientData.currentHandler = handler;
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void onRestart() {
        super.onRestart();
        ClientData.currentHandler = handler;
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * This method listens to when the device is moved. While moved, random values are generated by the RollDice class.
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];


        float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

        /**
         * As soon as the player moves his device stronger than the SHAKE_THRESHOLD, randoms are deliverd.
         */
        if (acceleration > SHAKE_THRESHOLD) {
            this.statusStarts = 1;
            RollDice rollDice = new RollDice(this);
            sum = rollDice.rollDice();
            Log.d("DEBUG", "Sum is: " + sum);
        }
        /**
         * If the acceleration of the phone reaches a minimum, its presumed that the user stoped shaking.
         * The sensor is stopped to avoid, that the user sakes again if he is unhappy with the shaken value.
         * The final value is transmitted to the server by starting a new Network-thread, as soon as the player clicks away the alert.
         * The alert is shown, so tha user has enough time to check what he diced.
         */
        if (Math.abs(acceleration) < 0.1 && statusStarts == 1) {
            //This is where the shaking stops
            finalSum = sum;
            sensorManager.unregisterListener(this);
            Log.d("DEBUG", "FINAL VALUE IS: " + finalSum);

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Würfelwert");
            builder1.setMessage("Du hast " + finalSum + " gewürfelt!");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (finalSum == 7) {
                                Intent intent = new Intent(RollDiceActivity.this, ThiefActivity.class);
                                startActivity(intent);
                            } else {
                                Thread networkThread = new NetworkThread(ServerQueries.createStringRolledDice(Integer.toString(finalSum)));
                                networkThread.start();
                            }
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Interface requirements, but not needed.
    }


    private class RollDiceHandler extends GameHandler {

        public RollDiceHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * After having send the dice-value and receives the rolled command, the ChooseActionActivity
         * will be called from the super handleMessage method.
         */
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
        }
    }
}
