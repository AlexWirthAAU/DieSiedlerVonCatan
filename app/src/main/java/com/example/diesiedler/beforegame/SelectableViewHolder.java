package com.example.diesiedler.beforegame;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckedTextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.R;

/**
 * @author Christina Senger
 * <p>
 * Repräsentation eines auswählbaren Behälters für SelectableItems
 */
class SelectableViewHolder extends RecyclerView.ViewHolder {

    static final int MULTI_SELECTION = 2;
    static final int SINGLE_SELECTION = 1;
    CheckedTextView textView;
    SelectableItem myItem;
    private OnItemSelectedListener itemSelectedListener;


    /**
     * Konstruktor - Setzt für die View einen OnClickListener und ruft dessen
     * onItemSelected-Methode auf.
     *
     * @param view Repräsentation für HolderView eines Items
     * @param listener Instanz von Myadapter
     */
    SelectableViewHolder(View view, OnItemSelectedListener listener) {

        super(view);
        itemSelectedListener = listener;
        textView = view.findViewById(R.id.username);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean check = false;

                if (!myItem.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    check = true;
                }

                setChecked(check);
                itemSelectedListener.onItemSelected(myItem);
            }
        });
    }

    /**
     * Wird ein Item ausgewählt, wird der Hintegrund dessen View gelb
     * eingefärbt. In der Checkbox wird ein Haken gesetzt.
     *
     * @param value true wenn Item ausgewählt wurde, sonst false
     */
    void setChecked(boolean value) {

        if (value) {
            textView.setBackgroundColor(Color.YELLOW);
        } else {
            textView.setBackground(null);
        }

        myItem.setSelected(value);
        textView.setChecked(value);
    }

    /**
     * Interface für den OnItemSelectedListener enthält eine Methode, um auf die
     * Auswahl eines SelectedItems zu reagieren.
     */
    public interface OnItemSelectedListener {
        void onItemSelected(SelectableItem item); }
}
