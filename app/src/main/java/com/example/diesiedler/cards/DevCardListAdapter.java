package com.example.diesiedler.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.catangame.PlayerInventory;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;

import java.util.List;

public class DevCardListAdapter extends ArrayAdapter<String> {
    private Context myContext;
    private int mResource;

    public DevCardListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        myContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();
        String name = getItem(position);
        int amount = 0;
        switch (name) {
            case "Straßenbau":
                amount = playerInventory.getBuildStreetCard();
                break;
            case "Monopol":
                amount = playerInventory.getMonopolCard();
                break;
            case "Ritterkarte":
                amount = playerInventory.getKnightCard();
                break;
            case "Erfindung":
                amount = playerInventory.getInventionCard();
                break;
            default:
                break;
        }

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView devCard = convertView.findViewById(R.id.devCardName);
        TextView devCardAmount = convertView.findViewById(R.id.devCardAmount);

        devCard.setText(name);
        devCardAmount.setText(Integer.toString(amount));

        return convertView;
    }
}
