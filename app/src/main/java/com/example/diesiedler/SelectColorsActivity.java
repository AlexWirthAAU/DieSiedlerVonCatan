package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenters.PresenterCheckColors;
import com.example.diesiedler.presenters.PresenterSetColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectColorsActivity extends AppCompatActivity {

    private ArrayList<String> gameList = new ArrayList<>();
    private Button green;
    private Button orange;
    private Button violett;
    private Button lightblue;
    private String myName;
    private PresenterSetColor presenterSetColor = new PresenterSetColor();
    private PresenterCheckColors presenterCheckColors = new PresenterCheckColors();
    private HashMap<String, String> map = new HashMap<>();
    private List<Button> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_colors);

        gameList = getIntent().getStringArrayListExtra("gameList");
        myName = getIntent().getStringExtra("myName");

        green = findViewById(R.id.green);
        orange = findViewById(R.id.orange);
        violett = findViewById(R.id.violett);
        lightblue = findViewById(R.id.lightblue);

        colors.add(green);
        colors.add(orange);
        colors.add(violett);
        colors.add(lightblue);

        presenterCheckColors.checkColors(Integer.parseInt(gameList.get(0)), this);

        /*
                new Thread() {
            public void run() {
                while (running) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                presenterCheckColors(Integer.parseInt(gameList.get(0)), this);
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
         */
    }

    public void onGreen(View view) {

        if ((green.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                System.out.println(btn.getText().toString() + " btntext");
                btn.setEnabled(false);
            }
            green.setText(myName);
            map.put("green", myName);
            presenterSetColor.setColor(map);
        }
    }

    public void onOrange(View view) {

        if ((orange.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                System.out.println(btn.getText().toString() + " btntext");
                btn.setEnabled(false);
            }
            orange.setText(myName);
            map.put("orange", myName);
            presenterSetColor.setColor(map);
        }
    }

    public void onViolett(View view) {

        if ((violett.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                System.out.println(btn.getText().toString() + " btntext");
                btn.setEnabled(false);
            }
            violett.setText(myName);
            map.put("violett", myName);
            presenterSetColor.setColor(map);
        }
    }

    public void onLightblue(View view) {

        if ((lightblue.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                System.out.println(btn.getText().toString() + " btntext");
                btn.setEnabled(false);
            }
            lightblue.setText(myName);
            map.put("lightblue", myName);
            presenterSetColor.setColor(map);
        }
    }

    public void startGame(View view) {

        int selectedColors = 0;
        boolean ready = false;

        for (Button btn : colors) {

            System.out.println(btn.getText().toString() + " btntext");
            if (!btn.getText().toString().isEmpty()) {
                selectedColors++;
            }

            System.out.println(gameList.size() - 1 + " player " + selectedColors + "selectedcolors");
            if (gameList.size() == selectedColors) {
                ready = true;
            }
        }

        if (!ready) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage("Alle Mitspieler müssen zuerst eine Farbe auswählen");
            AlertDialog alert1 = builder1.create();
            alert1.show();

        } else {

            Intent intent = new Intent(this, StartGameActivity.class);
            intent.putExtra("myName", myName);
            intent.putStringArrayListExtra("gameList", gameList);
            startActivity(intent);
        }
    }

    public void update(View view) {
        presenterCheckColors.checkColors(Integer.parseInt(gameList.get(0)), this);
    }
}
