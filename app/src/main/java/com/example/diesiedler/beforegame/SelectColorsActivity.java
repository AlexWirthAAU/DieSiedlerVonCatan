package com.example.diesiedler.beforegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Colors;
import com.example.catangame.Player;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Aktivität in der die Spieler ihre Spielfarbe auswählen können und das Spiel gestartet wird.
 */
public class SelectColorsActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(SelectColorsActivity.class.getName());
    private Button green;
    private Button orange;
    private Button violett;
    private Button lightblue;
    private List<Button> colors = new ArrayList<>();
    private Handler handler = new SelectColorsHandler(Looper.getMainLooper(), this);
    private Map<Colors, Button> colorsMap = new HashMap<>();

    /**
     * Bei Erstellung werden die Buttons spezifiziert und anschließend in eine Liste gespeichert.
     * Zusätzlich werden die Buttons zusammen mit einem enum Colors Key in einer Map gespeichert.
     * Der Handler wird in der ClientData für die jetzige Aktivität angepasst.
     * @param savedInstanceState gespeicherter Status
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_colors);

        green = findViewById(R.id.green);
        orange = findViewById(R.id.orange);
        violett = findViewById(R.id.violett);
        lightblue = findViewById(R.id.lightblue);

        colors.add(green);
        colors.add(orange);
        colors.add(violett);
        colors.add(lightblue);

        colorsMap.put(Colors.GREEN, green);
        colorsMap.put(Colors.ORANGE, orange);
        colorsMap.put(Colors.VIOLETT, violett);
        colorsMap.put(Colors.LIGHTBLUE, lightblue);

        ClientData.currentHandler = handler;

    }

    /**
     * Wird der grüne Button gedrückt, startet der NetworkThread um eine Farbauswahl an den Server zu schicken.
     */
    public void onGreen(View view) {

        if ((green.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.GREEN.name()));
            networkThread.start();
        }
    }

    /**
     * Wird der orange Button gedrückt, startet der NetworkThread um eine Farbauswahl an den Server zu schicken.
     */
    public void onOrange(View view) {

        if ((orange.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.ORANGE.name()));
            networkThread.start();
        }
    }

    /**
     * Wird der violette Button gedrückt, startet der NetworkThread um eine Farbauswahl an den Server zu schicken.
     */
    public void onViolett(View view) {

        if ((violett.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.VIOLETT.name()));
            networkThread.start();
        }
    }

    /**
     * Wird der hellblaue Button gedrückt, startet der NetworkThread um eine Farbauswahl an den Server zu schicken.
     */
    public void onLightblue(View view) {

        if ((lightblue.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.LIGHTBLUE.name()));
            networkThread.start();
        }
    }

    /**
     * Wird der Start Button gedrückt, wird zuerst geprüft, ob jeder Spieler eine Farbe gewählt hat.
     * Ist dies der Fall, so wird ein NetworkThread gestartet, der das Startrequest an den Server schickt.
     * Ansonsten wird auf dem Bildschirm eine Fehlermeldung ausgegeben.
     *
     * @param view View, um StartButton anzusprechen
     */
    public void startGame(View view) {

        boolean ready = true;

        for (Player player : ClientData.currentGame.getPlayers()) {
            if (player.getColor() == null) {
                ready = false;
                break;
            }
        }

        if (!ready) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage("Alle Mitspieler müssen zuerst eine Farbe auswählen");
            AlertDialog alert1 = builder1.create();
            alert1.show();

        } else {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryStart());
            networkThread.start();
        }
    }

    private class SelectColorsHandler extends HandlerOverride {

        SelectColorsHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }


        /**
         * Handler erhält Message vom ServerCommunicationThread. Bei Farbwahl(oder Wechsel) eines Spielers
         * werden die Buttons neu initialisiert. Bei einem erfolgreichen Start request ist das Spiel gestartet
         * und die MainActivity aufgerufen.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung.
         *            msg.obj beinhaltet gegebenfalls einen String.
         */
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {  // TODO: Change to enums
                for (Button button : ((SelectColorsActivity) activity).colors) {
                    button.setText("");
                    button.setEnabled(true);
                }

                for (Player player : ClientData.currentGame.getPlayers()) {
                    Button button = colorsMap.get(player.getColor());
                    if (button != null) {
                        button.setText(player.getDisplayName());
                        button.setEnabled(false);
                    }
                }
            } else if (msg.arg1 == 5 && msg.obj.equals("STARTGAME")) {  // TODO: Change to enums
                if (ClientData.currentGame.getPlayer(ClientData.currentGame.getCurrPlayer()).getUserId() == ClientData.userId) {
                    Intent intent = new Intent(activity, BuildSettlementActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
