package com.example.diesiedler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.catangame.Player;

import java.util.List;

class PlayerListAdapter extends ArrayAdapter<Player> {

    private Context myContext;
    private int mResource;


    public PlayerListAdapter(@NonNull Context context, int resource, @NonNull List<Player> objects) {
        super(context, resource, objects);
        myContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getDisplayName();
        int victoryPointsInt = getItem(position).getInventory().getVictoryPoints();
        String victoryPoints = Integer.toString(victoryPointsInt);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView playerName = convertView.findViewById(R.id.scPlayer);
        TextView playerPoints = convertView.findViewById(R.id.scPoints);

        playerName.setText(name);
        playerPoints.setText(victoryPoints);

        return convertView;
    }
}
