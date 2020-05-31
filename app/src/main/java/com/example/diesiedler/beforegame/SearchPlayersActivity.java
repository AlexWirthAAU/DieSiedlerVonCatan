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
import com.example.diesiedler.presenter.handler.PreGameHandler;
import com.example.diesiedler.threads.NetworkThread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Activity which holds a Recyclerview with all active Users as Items.
 * Sie kümmert sich um die Such-An- und Abmeldung, sowie die Spielerstellung.
 */
public class SearchPlayersActivity extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {

    private static final Logger logger = Logger.getLogger(SearchPlayersActivity.class.getName()); // Logger
    Handler handler = new SearchPlayersHandler(Looper.getMainLooper(), this); // Handler
    private RecyclerView recyclerView; // Recyclerview with all active Users as Items
    private MyAdapter myAdapter; // Adapter-Class for the Recyclerview
    private Button searchButton; // Button to start seraching for Players
    private Button stopButton; // Button to stop seraching for Players

    /**
     * The Recyclerview is loaded from the xml and gets a Layoutmanager.
     * The Buttons to start and stop searching are specified.
     *
     * The Handler in ClientData is set for the current Activity
     *
     * @param savedInstanceState saved State
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
     * Debug-Method for the Recyclerview
     *
     * @param selectableItem last selected Item
     */
    @Override
    public void onItemSelected(SelectableItem selectableItem) {

    }

    /**
     * When the Actity is destroyed, the NetworkThread sends the
     * Logout from the Search to the Server.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryStop());
        networkThread.start();
        // TODO: Liste leeren oder nicht?
    }

    /**
     * On Click it is checked, if there a between 1 and 3 Players selected.
     * If this is the case, the NetworkThread is started, which sends all UserIds
     * in a Create-Request to the Server and stores them locally. Else a Error-Message is shown.
     *
     * @param view View, to access StartButton
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
     * Sends a Login-Request for the Search to the Server.
     *
     * @param view View, to access the SearchButton
     */
    public void startSearching(View view) {
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryApply());
        networkThread.start();
    }

    /**
     * Sends a Logout-Request for the Search to the Server und toggles Search & Stop Button.
     *
     * @param view View, to access StopButton
     */
    public void stopSearching(View view){
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryStop());
        networkThread.start();
        searchButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
    }

    /**
     * @author Fabian Schaffenrath (edit)
     * <p>
     * Handler for the SearchPlayersActivity
     */
    private class SearchPlayersHandler extends PreGameHandler {

        SearchPlayersHandler(Looper mainLooper, Activity ac) {
            super(mainLooper,ac);
        }

        /**
         * Called from ServerCommunicationThread. When a List was send,
         * a new List for Player-Selection is created. When a GameSession
         * was send, a Game was created and the SelectColorActivity is started.
         *
         * @param msg msg.arg1 gets the Param for further actions
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
