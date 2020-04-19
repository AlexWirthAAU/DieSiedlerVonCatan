package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.presenters.PresenterCheckGames;
import com.example.diesiedler.presenters.PresenterDelete;
import com.example.diesiedler.presenters.PresenterStartGame;
import com.example.diesiedler.presenters.PresenterUpdate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchPlayersActivity extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private List<String> usernames = new ArrayList<>();
    private PresenterUpdate presenterUpdate = new PresenterUpdate();
    private PresenterDelete presenterDelete = new PresenterDelete();
    private PresenterStartGame presenterStartGame = new PresenterStartGame();
    private String myName;
    private PresenterCheckGames presenterCheckGames = new PresenterCheckGames();
    private ArrayList<String> selectedUsers = new ArrayList<>();

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

        System.out.println(usernames.get(0) + " test");

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenterDelete.removeMeFromUserList(myName, myAdapter);
    }

    public void startPlay(View view) {

        int size = myAdapter.getSelectedItems().size();
        System.out.println(size);
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

                System.out.println(myName + " startnewactivity");
                presenterStartGame.setInGame(selectedUsers, myName, this);
            }
        }
    }

    public void updateList(View view) {

        presenterUpdate.checkForChanges(usernames.size(), myAdapter);
        presenterCheckGames.checkIfIn(myName, this);
    }
}
