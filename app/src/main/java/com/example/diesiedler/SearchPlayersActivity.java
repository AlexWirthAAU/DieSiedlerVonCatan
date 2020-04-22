package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.presenter.Presenter;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Aktivität enthält eine Recyclerview mit allen aktiven Usern als Items
 */
public class SearchPlayersActivity extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<String> usernames = new ArrayList<>();
    private String myName;
    private ArrayList<String> selectedUsers = new ArrayList<>();

    private static final Logger log = Logger.getLogger(SearchPlayersActivity.class.getName());

    /**
     * Eine Liste mit den Namen aller aktiven Usern und Name
     * des aktuellen Users wird aus dem Intent geholt und
     * in den Variablen usernames und myName gespeichert.
     *
     * Aus allen Usernamen werden SelectableItems erstellt.
     *
     * Die Recyclerview wird als dem xml geholt und erhält einen
     * Layoutmanager.
     *
     * Es wird eine Instanz der Klasse Myadapter mit der aktuelle
     * Aktivität, den SelectableItems und true für Multisektion als
     * Parametern erstellt und der Recyclerview zugeordnet.
     *
     * Die Anzahl der aktiven User wird dem Presenter übergeben,
     * der auf Aktualisierungen vom Server wartet und ggf. die View updated.
     *
     * @param savedInstanceState gespeicherter Status
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_players);

        Intent intent = getIntent();
        usernames = intent.getStringArrayListExtra("name");
        myName = intent.getStringExtra("myName");

        List<SelectableItem> selectableItems = new ArrayList<>();

        for (String str : usernames) {
            SelectableItem user = new SelectableItem(str, false);
            selectableItems.add(user);
        }

        log.log(Level.INFO, usernames.get(0));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = this.findViewById(R.id.userlist);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(this, selectableItems, true);
        recyclerView.setAdapter(myAdapter);

        Presenter.checkForChanges(usernames.size(), myAdapter, myName, this);

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
     * Wird die Aktivität zerstört, wird der Name des aktuellen
     * Users dem Presenter übergeben, der beim Server das löschen
     * dieses User aus der Liste der aktuellen User und ein
     * Update der Userliste für alle anderen Nutzer beantragt.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Presenter.removeMeFromUserList(myName, myAdapter);
    }

    /**
     * Überprüft, ob der aktuelle User sich selbst ausgewählt hat.
     * Sind weniger als 3 oder mehr als 4 User ausgewählt, erscheint
     * eine Warnung. Wenn nicht, werden alle ausgewählten User in eine
     * Liste gespeichert. Diese wird dem Presenter übergeben, der am
     * Server ein neues Spiel beantragt.
     *
     * @param view View, um StartButton anzusprechen
     */
    public void startPlay(View view) throws IOException {

        int size = myAdapter.getSelectedItems().size();
        boolean meIn = false;

        for (SelectableItem item : myAdapter.getSelectedItems()) {
            if (item.getText().equals(myName)) {
                meIn = true;
                break;
            }
        }

        if (meIn) {

            if (size < 2) {

                List<SelectableItem> selectedItems = myAdapter.getSelectedItems();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setCancelable(true);
                builder2.setMessage("Du musst 3 oder 4 Mitspieler auswählen. Momentan ausgewählt: " + selectedItems.size());
                AlertDialog alert2 = builder2.create();
                alert2.show();

            } else if (size > 4) {

                List<SelectableItem> selectedItems = myAdapter.getSelectedItems();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setCancelable(true);
                builder1.setMessage("Du musst 3 oder 4 Mitspieler auswählen. Momentan ausgewählt: " + selectedItems.size());
                AlertDialog alert1 = builder1.create();
                alert1.show();

            } else {

                for (SelectableItem item : myAdapter.getSelectedItems()) {
                    selectedUsers.add(item.getText());
                }

                log.log(Level.INFO, "startnewactivity");
                Presenter.setInGame(selectedUsers, myName, this);
            }
        }
    }

    /**
     *
     * @param view View um Reload Button zu erhalten
     */
    public void updateList(View view) {

        Presenter.checkForChanges(usernames.size(), myAdapter, myName, this);
    }
}
