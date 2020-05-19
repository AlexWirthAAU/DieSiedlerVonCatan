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

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.RollDice;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

/**
 * @author Alex Wirth
 * <p>
 * Player is able to shake dive to generate some random value between 2-12.
 * The value will then be sent to the Server where the ressources are allocated depending on the value.
 * If value 7 is diced, the player can re-place the thief, which will be handled by an extra activity.
 */
public class RollDiceActivity extends AppCompatActivity implements SensorEventListener {

    // TODO: Methoden kommmentieren
    private SensorManager sensorManager; // Senors
    private Sensor accelerometer;

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

    /**
     * Going back is not possible here.
     */
    @Override
    public void onBackPressed() {
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
            //This is where the shaking stops
            finalSum = sum;
            sensorManager.unregisterListener(this);
            Log.d("DEBUG", "FINAL VALUE IS: " + finalSum);
            //new AlertDialog.Builder(this).setTitle("AUGENSUMME:").setMessage("Du hast " + finalSum + " gewürfelt.").show();


            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Würfelwert");
            builder1.setMessage("Du hast " + finalSum + " gewürfelt!");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (finalSum == 7) {
                                //TODO: call activity to replace Thief
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

    }

    private class RollDiceHandler extends HandlerOverride {

        public RollDiceHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                GameSession gameSession = ClientData.currentGame;
                Player currentP = gameSession.getPlayer(gameSession.getCurrPlayer());

                if (currentP.getUserId() == ClientData.userId) {
                    Intent intent = new Intent(activity, ChooseActionActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
