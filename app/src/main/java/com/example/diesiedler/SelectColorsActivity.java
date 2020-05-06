package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.Presenter;
import com.example.diesiedler.presenter.PresenterSetColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Aktivität in der die Spieler ihre Spielfarbe auswählen können
 */
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
    private PresenterSetColor presenterSetColor = new PresenterSetColor();

    /**
     * Eine Liste mit der GameId an erster an den Spielern mit ihren
     * Farben wird aus dem Intent geholt und in der Varible gameList
     * gespeichert. Der Name des aktuellen Users wird als myName geseichert.
     *
     * Die Buttons werden aus der ContentView geholt, lokal gespeichert und zu
     * einer Liste colors hinzugefügt.
     *
     * Die GameId und die aktuelle Aktivität werden dem Presenter übergeben.
     * Dieser warten auf Nachrichten vom Server, dass die Mitspieler Farben
     * gewählt haben und aktualisiert die View dementsprechend.
     *
     * @param savedInstanceState gespeicherter Status
     */
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
     * Ist die Farbe des gelickten Button noch frei,
     * werden alle anderen Farbbuttons für den aktuellen Spieler
     * unklickbar. Der Username erscheint als Buttontext.
     * Gleichzeitig wird die Wahl als Map von Farbe und Name an den
     * Presenter übergeben, der dies an den Server weiterleitet.
     *
     * @param view View, um Farbbutton anzusprechen
     */
    public void onGreen(View view) {

        if ((green.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            green.setText(myName);
            map.put("green", myName);
            presenterSetColor.setColor(map);
        }
    }

    /**
     * Ist die Farbe des gelickten Button noch frei,
     * werden alle anderen Farbbuttons für den aktuellen Spieler
     * unklickbar. Der Username erscheint als Buttontext.
     * Gleichzeitig wird die Wahl als Map von Farbe und Name an den
     * Presenter übergeben, der dies an den Server weiterleitet.
     *
     * @param view View, um Farbbutton anzusprechen
     */
    public void onOrange(View view) {

        if ((orange.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            orange.setText(myName);
            map.put("orange", myName);
            presenterSetColor.setColor(map);
        }
    }

    /**
     * Ist die Farbe des gelickten Button noch frei,
     * werden alle anderen Farbbuttons für den aktuellen Spieler
     * unklickbar. Der Username erscheint als Buttontext.
     * Gleichzeitig wird die Wahl als Map von Farbe und Name an den
     * Presenter übergeben, der dies an den Server weiterleitet.
     *
     * @param view View, um Farbbutton anzusprechen
     */
    public void onViolett(View view) {

        if ((violett.getText().toString()).isEmpty()) {
            for (Button btn : colors) {

                btn.setEnabled(false);
            }
            violett.setText(myName);
            map.put("violett", myName);
            presenterSetColor.setColor(map);
        }
    }

    /**
     * Ist die Farbe des gelickten Button noch frei,
     * werden alle anderen Farbbuttons für den aktuellen Spieler
     * unklickbar. Der Username erscheint als Buttontext.
     * Gleichzeitig wird die Wahl als Map von Farbe und Name an den
     * Presenter übergeben, der dies an den Server weiterleitet.
     *
     * @param view View, um Farbbutton anzusprechen
     */
    public void onLightblue(View view) {

        if ((lightblue.getText().toString()).isEmpty()) {
            for (Button btn : colors) {
                btn.setEnabled(false);
            }

            lightblue.setText(myName);
            map.put("lightblue", myName);
            presenterSetColor.setColor(map);
        }
    }

    /**
     * Haben alle Spieler eine Frbe gewählt, wird das Spiel geladen.
     * Sonst wird eine Warnung angezeigt. Dies wird überprüft, indem
     * die Anzahl der aktiven Spieler (aus dem Intent) mit der Anzahl
     * der nichtleeren Buttons verglichen wird.
     *
     * @param view View, um StartButton anzusprechen
     */
    public void startGame(View view) {

        int selectedColors = 0;
        boolean ready = false;

        for (Button btn : colors) {

            log.log(Level.INFO, "btntext" + btn.getText().toString());
            if (!btn.getText().toString().isEmpty()) {
                selectedColors++;
            }
        }

        if (selectedColors == Integer.parseInt(gameList.get(1))) {
            ready = true;
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
