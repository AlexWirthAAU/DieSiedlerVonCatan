package com.example.diesiedler;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        /**TODO: Implement server call: send map to the Server
         */

    }

    public void plus(View view) {

        switch (view.getId()) {

            case R.id.plusWoodGive:
                woodGive += 1;
                countWoodGive.setText(woodGive);
                break;

            case R.id.plusWoolGive:
                woolGive += 1;
                countWoolGive.setText(woolGive);
                break;

            case R.id.plusWheatGive:
                wheatGive += 1;
                countWheatGive.setText(wheatGive);
                break;

            case R.id.plusOreGive:
                oreGive += 1;
                countOreGive.setText(oreGive);
                break;

            case R.id.plusClayGive:
                clayGive += 1;
                countClayGive.setText(clayGive);
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
                woodGive -= 1;
                countWoodGive.setText(woodGive);
                break;

            case R.id.minusWoolGive:
                woolGive -= 1;
                countWoolGive.setText(woolGive);
                break;

            case R.id.minusWheatGive:
                wheatGive -= 1;
                countWheatGive.setText(wheatGive);
                break;

            case R.id.minusOreGive:
                oreGive -= 1;
                countOreGive.setText(oreGive);
                break;

            case R.id.minusClayGive:
                clayGive -= 1;
                countClayGive.setText(clayGive);
                break;

            case R.id.minusWoodGet:
                woodGet -= 1;
                countWoodGet.setText(woodGet);
                break;

            case R.id.minusWoolGet:
                woolGet -= 1;
                countWoolGet.setText(woolGet);
                break;

            case R.id.minusWheatGet:
                wheatGet -= 1;
                countWheatGet.setText(wheatGet);
                break;

            case R.id.minusOreGet:
                oreGet -= 1;
                countOreGet.setText(oreGet);
                break;

            case R.id.minusClayGet:
                clayGet -= 1;
                countClayGet.setText(clayGet);
                break;
        }
    }
}
