package com.example.diesiedler;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private final List<SelectableItem> myValues;
    private SelectableViewHolder.OnItemSelectedListener listener;
    private boolean isMultiSelectionEnabled;

    // Provide a suitable constructor (depends on the kind of dataset)
    MyAdapter(SelectableViewHolder.OnItemSelectedListener listener, List<SelectableItem> data, boolean isMultiSelectionEnabled) {

        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        myValues = new ArrayList<>();
        myValues.addAll(data);
    }

    public void update(List<String> newUsernames) {
        myValues.clear();
        for (String str : newUsernames) {
            myValues.add(new SelectableItem(str, false));
        }
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager) !!
    @NonNull
    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.user, parent, false);

        return new SelectableViewHolder(v, this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        SelectableItem selectableItem = myValues.get(position);
        String name = selectableItem.getText();
        holder.textView.setText(name);

        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.myItem = selectableItem;
        holder.setChecked(holder.myItem.isSelected());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myValues.size();
    }

    List<SelectableItem> getSelectedItems() {

        List<SelectableItem> selectedItems = new ArrayList<>();
        for (SelectableItem item : myValues) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectionEnabled) {
            return SelectableViewHolder.MULTI_SELECTION;
        } else {
            return SelectableViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onItemSelected(SelectableItem item) {
        if (!isMultiSelectionEnabled) {

            for (SelectableItem selectableItem : myValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);
    }
}

