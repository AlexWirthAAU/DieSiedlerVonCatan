package com.example.diesiedler.beforegame;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckedTextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.R;

/**
 * @author Christina Senger
 * <p>
 * Representation of a selectable Holder for SelectableItems
 */
class SelectableViewHolder extends RecyclerView.ViewHolder {

    static final int MULTI_SELECTION = 2;
    static final int SINGLE_SELECTION = 1;
    CheckedTextView textView; // TextView of the HolderView of an SelectableItem
    SelectableItem myItem; // Representation of an SelectableItem
    private OnItemSelectedListener itemSelectedListener;


    /**
     * Constructor - Sets an OnClickListener for the View
     * and calls its onItemSelected-Methode.
     *
     * @param view Representation of the HolderView of one Item
     * @param listener Instance of MyAdapter-Class
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
     * When an Item is selected, to Background of its View
     * is painted yellow. The Checkbox is checked.
     *
     * @param value true when Item is selected, else false
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
     * Interface for the OnItemSelectedListener has a Methode, to
     * react on the Selection of a SelectedItem.
     */
    public interface OnItemSelectedListener {
        void onItemSelected(SelectableItem item); }
}
