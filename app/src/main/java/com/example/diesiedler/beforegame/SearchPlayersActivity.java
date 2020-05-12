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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Aktivität enthält eine Recyclerview mit allen aktiven Usern als Items. Sie kümmert sich um die Such-
 * An- und Abmeldung, sowie die Spielerstellung.
 */
public class SearchPlayersActivity extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {


    Handler handler = new SearchPlayersHandler(Looper.getMainLooper(),this);

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private Button searchButton;
    private Button stopButton;

    private static final Logger logger = Logger.getLogger(SearchPlayersActivity.class.getName());

    /**
     * Die Recyclerview wird als dem xml geholt und erhält einen
     * Layoutmanager. Button zur An- und Abmeldung werden spezifiziert.
     *
     * Der Handler wird in der ClientData für die jetzige Aktivität angepasst.
     * @param savedInstanceState gespeicherter Status
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_players);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = this.findViewById(R.id.userlist);
        recyclerView.setLayoutManager(layoutManager);

        searchButton = this.findViewById(R.id.searchButton);
        stopButton = this.findViewById(R.id.stopButton);

        ClientData.currentHandler = handler;
    }

    /**
     * Methode dient zum Test der Recyclerview
     *
     * @param selectableItem das zuletzt augewählte Item
     */
    @Override
    public void onItemSelected(SelectableItem selectableItem) {

        List<SelectableItem> selectedItems = myAdapter.getSelectedItems();
        Snackbar.make(recyclerView, "Selected item is " + selectableItem.getText() +
                ", Totally  selectem item count is " + selectedItems.size(), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Wird die Aktivität zerstört, so schickt ein NetworkThread eine
     * Abmeldung von der Suche zum Server.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryStop());
        networkThread.start();
        // TODO: Liste leeren oder nicht?
    }

    /**
     * Bei Betätigung des Start Buttons wird erst überprüft, ob zwischen 1 und 3 Mitspieler ausgewählt ist/sind.
     * Falls ja wird der NetworkThread gestartet, der alle UserIds der Spieler zusammen mit einem Create
     * request an den Server schickt. Falls nein wird eine Fehlermeldung am Bildschirm ausgegeben.
     * @param view View, um StartButton anzusprechen
     */
    public void startPlay(View view) {

        int size = myAdapter.getSelectedItems().size();

        if (size < 1) {

            List<SelectableItem> selectedItems = myAdapter.getSelectedItems();
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setCancelable(true);
            builder2.setMessage("Du musst mindestends einen Mitspieler auswählen. Momentan ausgewählt: " + selectedItems.size());
            AlertDialog alert2 = builder2.create();
            alert2.show();

        } else if (size > 3) {

            List<SelectableItem> selectedItems = myAdapter.getSelectedItems();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage("Du kannst maximal 3 Mitspieler auswählen. Momentan ausgewählt: " + selectedItems.size());
            AlertDialog alert1 = builder1.create();
            alert1.show();

        } else {

            ArrayList<Integer> selectedUserIds = new ArrayList<>();
            for (SelectableItem item : myAdapter.getSelectedItems()) {
                selectedUserIds.add(ClientData.searchingUsers.get(item.getText()));
            }

            logger.log(Level.INFO, "CREATE GAME");
            Thread networkThread = new NetworkThread(ServerQueries.createStringQueryCreate(selectedUserIds));
            networkThread.start();
        }
    }

    /**
     * Sendet ein Anmelde request für die Spielersuche an den Server.
     *
     * @param view View, um SearchButton anzusprechen
     */

    public void startSearching(View view) {
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryApply());
        networkThread.start();
    }

    /**
     * Sendet ein Abmelde request für die Spielersuche an den Server und toggled Search & Stop Button.
     * @param view View, um StopButton anzusprechen
     */

    public void stopSearching(View view){
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryStop());
        networkThread.start();
        searchButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
    }

    private class SearchPlayersHandler extends HandlerOverride {

        SearchPlayersHandler(Looper mainLooper, Activity ac) {
            super(mainLooper,ac);
        }

        /**
         * Wird vom ServerCommunicationThread aufgerufen. Im Falle einer Listübertragung wird
         * eine neue Liste für die Mitspieler auswahl erstellt, sollte eine GameSession übertragen
         * werden, so wurde das Spiel erstellt und die SelectColorActivity wird aufgerufen.
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */

        @Override
        public void handleMessage(Message msg){
            if(msg.arg1 == 3){  // TODO: Change to enums

                // TODO: Keep chosen players

                searchButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);

                List<SelectableItem> selectableItems = new ArrayList<>();

                for (String str:ClientData.searchingUserNames) {
                    SelectableItem user = new SelectableItem(str, false);
                    selectableItems.add(user);
                }

                myAdapter = new MyAdapter((SearchPlayersActivity)activity, selectableItems, true);
                recyclerView.setAdapter(myAdapter);
            }
            if(msg.arg1 == 4){  // TODO: Change to enums
                Intent intent = new Intent(activity, SelectColorsActivity.class);
                startActivity(intent);
            }
        }
    }
}
