package com.example.diesiedler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;

import java.util.ArrayList;
import java.util.List;

public class PortChangeActivity extends AppCompatActivity {

    Button woodGive = findViewById(R.id.woodGive);
    Button woolGive = findViewById(R.id.woolGive);
    Button wheatGive = findViewById(R.id.wheatGive);
    Button oreGive = findViewById(R.id.oreGive);
    Button clayGive = findViewById(R.id.clayGive);

    List<Button> giveBtns = new ArrayList<>();

    Button woodGet = findViewById(R.id.woodGet);
    Button woolGet = findViewById(R.id.woolGet);
    Button wheatGet = findViewById(R.id.wheatGet);
    Button oreGet = findViewById(R.id.oreGet);
    Button clayGet = findViewById(R.id.clayGet);

    List<Button> getBtns = new ArrayList<>();

    StringBuilder res = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_change);

        giveBtns.add(woodGive);
        giveBtns.add(woolGive);
        giveBtns.add(wheatGive);
        giveBtns.add(oreGive);
        giveBtns.add(clayGive);

        getBtns.add(woodGet);
        getBtns.add(woolGet);
        getBtns.add(wheatGet);
        getBtns.add(oreGet);
        getBtns.add(clayGet);

        GameSession game = (GameSession) getIntent().getSerializableExtra("game");
        Player player = game.getCurr();

        if (player.getInventory().isWoodport()) {
            woodGive.setEnabled(false);
        }

        if (player.getInventory().isWoolport()) {
            woolGive.setEnabled(false);
        }

        if (player.getInventory().isWheatport()) {
            wheatGive.setEnabled(false);
        }

        if (player.getInventory().isOreport()) {
            oreGive.setEnabled(false);
        }

        if (player.getInventory().isClayport()) {
            clayGet.setEnabled(false);
        }
    }

    public void setGive(View view) {

        for (Button btn : giveBtns) {
            btn.setEnabled(false);

            if (btn.getId() == view.getId()) {
                res.append(btn.getText().toString()).append("/");
            }
        }
    }

    public void setGet(View view) {

        for (Button btn : getBtns) {
            btn.setEnabled(false);

            if (btn.getId() == view.getId()) {
                res.append(btn.getText().toString());
            }
        }
    }

    public void change(View view) {
        /** TODO: send Map to Server */
        String toSendTrade = res.toString();
    }
}
