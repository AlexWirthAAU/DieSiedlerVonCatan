package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.presenters.Presenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchPlayersActivity extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private List<String> usernames = new ArrayList<>();
    private String myName;
    private static final Logger log = Logger.getLogger(SearchPlayersActivity.class.getName());
    private ArrayList<String> selectedUsers = new ArrayList<>();
    private Presenter presenter = new Presenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_players);

        Intent intent = getIntent();
        usernames = intent.getStringArrayListExtra("name");
        List<SelectableItem> selectableItems = new ArrayList<>();

        for (String str : usernames) {
            SelectableItem user = new SelectableItem(str, false);
            selectableItems.add(user);
        }
        myName = intent.getStringExtra("myName");

        log.log(Level.INFO, "firstname", usernames.get(0));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = this.findViewById(R.id.userlist);

        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(this, selectableItems, true);
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onItemSelected(SelectableItem selectableItem) {

        List<SelectableItem> selectedItems = myAdapter.getSelectedItems();
        Snackbar.make(recyclerView, "Selected item is " + selectableItem.getText() +
                ", Totally  selectem item count is " + selectedItems.size(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Presenter.removeMeFromUserList(myName, myAdapter);
    }

    /**
     * @param view - current View to access selected items
     */
    public void startPlay(View view) {

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
     * @param view - current view to access Reload Button
     */
    public void updateList(View view) {

        Presenter.checkForChanges(usernames.size(), myAdapter);
        presenter.checkIfIn(myName, this);
    }
}
