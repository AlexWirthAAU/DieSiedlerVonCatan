package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenters.Presenter;
import com.example.diesiedler.presenters.PresenterSetColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectColorsActivity extends AppCompatActivity {

    private ArrayList<String> gameList = new ArrayList<>();
    private Button green;
    private Button orange;
    private Button violett;
    private Button lightblue;
    private String myName;
    private HashMap<String, String> map = new HashMap<>();
    private List<Button> colors = new ArrayList<>();
    private static final Logger log = Logger.getLogger(SelectColorsActivity.class.getName());


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

        Presenter.checkColors(Integer.parseInt(gameList.get(0)), this);
    }

    /**
     * @param view current View to access color Button
     */
    public void onGreen(View view) {

        if ((green.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            green.setText(myName);
            map.put("green", myName);
            PresenterSetColor.setColor(map);
        }
    }

    /**
     *
     * @param view current View to access color Button
     */
    public void onOrange(View view) {

        if ((orange.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            orange.setText(myName);
            map.put("orange", myName);
            PresenterSetColor.setColor(map);
        }
    }

    /**
     *
     * @param view current View to access color Button
     */
    public void onViolett(View view) {

        if ((violett.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            violett.setText(myName);
            map.put("violett", myName);
            PresenterSetColor.setColor(map);
        }
    }

    /**
     *
     * @param view current View to access color Button
     */
    public void onLightblue(View view) {

        if ((lightblue.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            lightblue.setText(myName);
            map.put("lightblue", myName);
            PresenterSetColor.setColor(map);
        }
    }

    /**
     *
     * @param view current View to access Start Button
     */
    public void startGame(View view) {

        int selectedColors = 0;
        boolean ready = false;

        for (Button btn : colors) {

            log.log(Level.INFO, "btntext", btn.getText().toString());
            if (!btn.getText().toString().isEmpty()) {
                selectedColors++;
            }

            log.log(Level.INFO, "player", gameList.size() - 1);
            log.log(Level.INFO, "selectedColors", selectedColors);

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

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("myName", myName);
            intent.putStringArrayListExtra("gameList", gameList);
            startActivity(intent);
        }
    }

    /**
     *
     * @param view - current View to accesc Reload Button
     */
    public void update(View view) {
        Presenter.checkColors(Integer.parseInt(gameList.get(0)), this);
    }
}
