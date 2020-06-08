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
import com.example.diesiedler.presenter.handler.PreGameHandler;
import com.example.diesiedler.threads.NetworkThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Activity where the Users can select their Colors and Start the Game.
 */
public class SelectColorsActivity extends AppCompatActivity {

    private Button green; // Color-Buttons
    private Button orange;
    private Button violett;
    private Button lightblue;

    private List<Button> colors = new ArrayList<>(); // List of all Color-Buttons

    private Handler handler = new SelectColorsHandler(Looper.getMainLooper(), this); // Handler

    private Map<Colors, Button> colorsMap = new HashMap<>();

    /**
     * On Create the Color-Buttons are specified and saved in a List.
     * Furthermore they are saved in with a Color-Enum as Key in a Map.
     * The Handler in ClientData is set for the current Activity
     *
     * @param savedInstanceState saved State
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

    @Override
    public void onBackPressed() {
        // Back button should not return to the previous Activity here.
    }

    /**
     * When the green Button is clicked, the NetworkThread is started to send a Color-Selection to the Server.
     *
     * @param view View to access the Button
     */
    public void onGreen(View view) {

        if ((green.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.GREEN.name()));
            networkThread.start();
        }
    }

    /**
     * When the orange Button is clicked, the NetworkThread is started to send a Color-Selection to the Server.
     *
     * @param view View to access the Button
     */
    public void onOrange(View view) {

        if ((orange.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.ORANGE.name()));
            networkThread.start();
        }
    }

    /**
     * When the violett Button is clicked, the NetworkThread is started to send a Color-Selection to the Server.
     *
     * @param view View to access the Button
     */
    public void onViolett(View view) {

        if ((violett.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.VIOLETT.name()));
            networkThread.start();
        }
    }

    /**
     * When the lightblue Button is clicked, the NetworkThread is started to send a Color-Selection to the Server.
     *
     * @param view View to access the Button
     */
    public void onLightblue(View view) {

        if ((lightblue.getText().toString()).isEmpty()) {
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryColor(Colors.LIGHTBLUE.name()));
            networkThread.start();
        }
    }

    /**
     * When the StartButton is clicked, it is checked, if all Players have selected their Colors.
     * If this is the case, the NetworkThread is started, which sends a Startrequest to the Server.
     * Else an Error-Message is shown.
     *
     * @param view View, to access the StartButton
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

    /**
     * @author Fabian Schaffenrath (edit)
     * <p>
     * Handler for the SelectColorsActivity
     */
    private class SelectColorsHandler extends PreGameHandler {

        SelectColorsHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Handler gets a Message from ServerCommunicationThread. On Color-Select(or Change),
         * the Buttons get initialised once more. On successful Start-Request, a Game is
         * started and the MainActivity (If its the Players Turn, the BuildRoadActivity) is started.
         *
         * @param msg msg.arg1 has the Param for further actions
         *            msg.obj when msg.arg1 is 5, it holds a String
         */
        @Override
        public void handleMessage(Message msg) {

            if (msg.arg1 == 4) {
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

            } else if (msg.arg1 == 5 && msg.obj.equals("STARTGAME")) {
                if (ClientData.currentGame.getCurr().getUserId() == ClientData.userId) {
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
