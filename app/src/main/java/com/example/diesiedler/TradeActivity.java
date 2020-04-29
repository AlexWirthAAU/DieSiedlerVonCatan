package com.example.diesiedler;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class TradeActivity extends AppCompatActivity {

    private final int STARTVALUE = 0;
    private int woodGive = STARTVALUE;
    private int woodGet = STARTVALUE;
    private int woolGive = STARTVALUE;
    private int woolGet = STARTVALUE;
    private int wheatGive = STARTVALUE;
    private int wheatGet = STARTVALUE;
    private int oreGive = STARTVALUE;
    private int oreGet = STARTVALUE;
    private int clayGive = STARTVALUE;
    private int clayGet = STARTVALUE;

    private Map<String, Integer> tradeMap = new HashMap<>();

    private TextView countWoodGive = findViewById(R.id.countWoodGive);
    private TextView countWoodGet = findViewById(R.id.countWoodGet);
    private TextView countWoolGive = findViewById(R.id.countWoolGive);
    private TextView countWoolGet = findViewById(R.id.countWoolGet);
    private TextView countWheatGive = findViewById(R.id.countWheatGive);
    private TextView countWheatGet = findViewById(R.id.countWheatGet);
    private TextView countOreGive = findViewById(R.id.countOreGive);
    private TextView countOreGet = findViewById(R.id.countOreGet);
    private TextView countClayGive = findViewById(R.id.countClayGive);
    private TextView countClayGet = findViewById(R.id.countClayGet);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
    }

    private boolean checkRessources(int value, String res) {
        /**TODO: Implement check against game session*/
        if (value + 1 >= 0) {
            return true;
        }
        return false;
    }

    private void alert(String res) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(true);
        builder1.setMessage("Du hast nicht genug " + res);
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    public void trade(View view) {

        tradeMap.put("WoodGive", woodGive);
        tradeMap.put("WoolGive", woolGive);
        tradeMap.put("WheatGive", wheatGive);
        tradeMap.put("OreGive", oreGive);
        tradeMap.put("ClayGive", clayGive);
        tradeMap.put("WoodGet", woodGet);
        tradeMap.put("WoolGet", woolGet);
        tradeMap.put("WheatGet", wheatGet);
        tradeMap.put("OreGet", oreGet);
        tradeMap.put("ClayGet", clayGet);

        /**TODO: Implement server call: send map to the Server*/

    }

    public void plus(View view) {

        switch (view.getId()) {

            case R.id.plusWoodGive:
                if (checkRessources(woodGive, "Wood")) {
                    woodGive += 1;
                    countWoodGive.setText(woodGive);
                } else {
                    alert("Holz");
                }
                break;

            case R.id.plusWoolGive:
                if (checkRessources(woolGive, "Wool")) {
                    woolGive += 1;
                    countWoolGive.setText(woolGive);
                } else {
                    alert("Wolle");
                }
                break;

            case R.id.plusWheatGive:
                if (checkRessources(wheatGive, "Wheat")) {
                    wheatGive += 1;
                    countWheatGive.setText(wheatGive);
                } else {
                    alert("Weizen");
                }
                break;

            case R.id.plusOreGive:
                if (checkRessources(oreGive, "Ore")) {
                    oreGive += 1;
                    countOreGive.setText(oreGive);
                } else {
                    alert("Erz");
                }
                break;

            case R.id.plusClayGive:
                if (checkRessources(clayGive, "Clay")) {
                    clayGive += 1;
                    countClayGive.setText(clayGive);
                } else {
                    alert("Lehm");
                }
                break;

            case R.id.plusWoodGet:
                woodGet += 1;
                countWoodGet.setText(woodGet);
                break;

            case R.id.plusWoolGet:
                woolGet += 1;
                countWoolGet.setText(woolGet);
                break;

            case R.id.plusWheatGet:
                wheatGet += 1;
                countWheatGet.setText(wheatGet);
                break;

            case R.id.plusOreGet:
                oreGet += 1;
                countOreGet.setText(oreGet);
                break;

            case R.id.plusClayGet:
                clayGet += 1;
                countClayGet.setText(clayGet);
                break;
        }
    }

    public void minus(View view) {

        switch (view.getId()) {

            case R.id.minusWoodGive:
                if (woodGive >= 1) {
                    woodGive -= 1;
                    countWoodGive.setText(woodGive);
                }
                break;

            case R.id.minusWoolGive:
                if (woolGive >= 1) {
                    woolGive -= 1;
                    countWoolGive.setText(woolGive);
                }
                break;

            case R.id.minusWheatGive:
                if (wheatGive >= 1) {
                    wheatGive -= 1;
                    countWheatGive.setText(wheatGive);
                }
                break;

            case R.id.minusOreGive:
                if (oreGive >= 1) {
                    oreGive -= 1;
                    countOreGive.setText(oreGive);
                }
                break;

            case R.id.minusClayGive:
                if (clayGive >= 1) {
                    clayGive -= 1;
                    countClayGive.setText(clayGive);
                }
                break;

            case R.id.minusWoodGet:
                if (woodGet >= 1) {
                    woodGet -= 1;
                    countWoodGet.setText(woodGet);
                }
                break;

            case R.id.minusWoolGet:
                if (woolGet >= 1) {
                    woolGet -= 1;
                    countWoolGet.setText(woolGet);
                }
                break;

            case R.id.minusWheatGet:
                if (wheatGet >= 1) {
                    wheatGet -= 1;
                    countWheatGet.setText(wheatGet);
                }
                break;

            case R.id.minusOreGet:
                if (oreGet >= 1) {
                    oreGet -= 1;
                    countOreGet.setText(oreGet);
                }
                break;

            case R.id.minusClayGet:
                if (clayGet >= 1) {
                    clayGet -= 1;
                    countClayGet.setText(clayGet);
                }
                break;
        }
    }
}
