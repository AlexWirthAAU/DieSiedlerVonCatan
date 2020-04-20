package com.example.diesiedler;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckedTextView;

import androidx.recyclerview.widget.RecyclerView;


class SelectableViewHolder extends RecyclerView.ViewHolder {

    static final int MULTI_SELECTION = 2;
    static final int SINGLE_SELECTION = 1;
    CheckedTextView textView;
    SelectableItem myItem;
    private OnItemSelectedListener itemSelectedListener;


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

    void setChecked(boolean value) {
        if (value) {
            textView.setBackgroundColor(Color.YELLOW);
        } else {
            textView.setBackground(null);
        }
        myItem.setSelected(value);
        textView.setChecked(value);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(SelectableItem item);
    }
}
