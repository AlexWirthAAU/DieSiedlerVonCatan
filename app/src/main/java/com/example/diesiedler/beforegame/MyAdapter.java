package com.example.diesiedler.beforegame;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diesiedler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Adapterclass, to manage the Items in the Recyclerview
 * and react on Changes.
 */
public class MyAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private final List<SelectableItem> myValues;
    private SelectableViewHolder.OnItemSelectedListener listener;
    private boolean isMultiSelectionEnabled;

    /**
     * Constructor - adds all Selectable Items to the local List myValues
     *
     * @param listener OnItemSelectListener for the Class SelecableViewHolder
     * @param data Liste of all Items (active User)
     * @param isMultiSelectionEnabled can one select multiple Elements? here: true
     */
    MyAdapter(SelectableViewHolder.OnItemSelectedListener listener, List<SelectableItem> data, boolean isMultiSelectionEnabled) {

        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        myValues = new ArrayList<>();
        myValues.addAll(data);
    }

    /**
     * Make all Items in the new List SelectableItems. Deletes all values from
     * myValues (curr SelectableItems) and adds all new Items.
     * Notifies to Adapter, that Items have changed.
     *
     * @param newUsernames new List of active Users
     */
    public void update(List<String> newUsernames) {

        myValues.clear();

        for (String str : newUsernames) {
            myValues.add(new SelectableItem(str, false));
        }
        notifyDataSetChanged();
    }

    /**
     * Gets called, when the Recycerview needs a new Viewholder.
     * With inflate, it is put in the Parent-Element and is used,
     * to show a SelectableItem.
     *
     * @param parent Parent-View-Element
     * @param viewType Type of the new View
     * @return new View (SelectableViewHolder) which can hold an Item
     */
    @NonNull
    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.user, parent, false);

        return new SelectableViewHolder(v, this);
    }

    /**
     * The Text of the Item on the <code>position</code> is set as
     * Text of the TextView of the Holder.
     * Depending on if Multiselection is enabled, to Design of the
     * Checkbox is set.
     *
     * @param viewHolder ViewHolder which has to be updated
     * @param position Position of the in the Recyclerview
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

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

    /**
     * @return number of the selected Elements
     */
    @Override
    public int getItemCount() {
        return myValues.size();
    }

    /**
     * @return a List of all selected Elements
     */
    List<SelectableItem> getSelectedItems() {

        List<SelectableItem> selectedItems = new ArrayList<>();

        for (SelectableItem item : myValues) {

            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }

        return selectedItems;
    }

    /**
     *
     * @param position Position of the selected Elements
     * @return MULTI_SELECTION, when Selection of multiple Elements is enabled, else SINGLE_SELECTION
     */
    @Override
    public int getItemViewType(int position) {

        if (isMultiSelectionEnabled) {
            return SelectableViewHolder.MULTI_SELECTION;
        } else {
            return SelectableViewHolder.SINGLE_SELECTION;
        }
    }

    /**
     * Selects or disselcts an Item, depending on if it already was selected.
     * Notifies the Adapter, that the Data has changed.
     *
     * @param item last selected Item
     */
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
    }
}

