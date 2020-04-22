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

/**
 * @author Christina Senger
 * <p>
 * Adapterklasse, um die Items in der Recyclerview zu verwalten
 * und auf Änderungen zu reagieren.
 */
public class MyAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private final List<SelectableItem> myValues;
    private SelectableViewHolder.OnItemSelectedListener listener;
    private boolean isMultiSelectionEnabled;

    /**
     * Konstruktor - fügt alle übergebenen SelectableItems zu lokalen Liste myValues hinzu
     *
     * @param listener OnItemSelectListener der Klasse SelecableViewHolder
     * @param data Liste aller Items (aktiven User)
     * @param isMultiSelectionEnabled kann man mehrere Elemente auswählen? hier: true
     */
    MyAdapter(SelectableViewHolder.OnItemSelectedListener listener, List<SelectableItem> data, boolean isMultiSelectionEnabled) {

        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        myValues = new ArrayList<>();
        myValues.addAll(data);
    }

    /**
     * Erstellt aus der neuen Liste SelectableItems. Löscht alle Werte aus
     * myValues (bisherige SelectableItems) und fügt alle neuen Items hinzu.
     * Benachrichtigt den Adapter, dass sich die Daten geändert haben.
     *
     * @param newUsernames Neue Liste der aktiven Nutzer
     */
    public void update(List<String> newUsernames) {

        myValues.clear();

        for (String str : newUsernames) {
            myValues.add(new SelectableItem(str, false));
        }
        notifyDataSetChanged();
    }

    /**
     * Methode wird aufgerufen, wenn die Recycerview ein neuen Viewholder braucht.
     * Dieser wird mittels inflate ins Elternelement eingefügt  und benutzt, um SelectableItems anzuzeigen.
     *
     * @param parent Übergeordnetes View-Element
     * @param viewType der Typ der neuen View
     * @return neue View (SelectableViewHolder) der ein Item enthalten kann
     */
    @NonNull
    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.user, parent, false);

        return new SelectableViewHolder(v, this);
    }

    /**
     * Der Text des Items an der angegebenen Position wird als Text
     * der TextView des Holder festgelegt.
     * Je nachdem on Mulit-Selektion erlaubt ist, wird das Aussehen der
     * Checkbox festgelegt.
     *
     * @param viewHolder der zu aktualisierende ViewHolder
     * @param position die Position des Items in der Recyclerview
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
     * @return die Anzahl der auszuwählenden Elemente
     */
    @Override
    public int getItemCount() {
        return myValues.size();
    }

    /**
     * @return eine Liste aller ausgewählten Items
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
     * @param position Position des gewählten Element
     * @return MULTI_SELECTION, wenn die Auswahl mehrerer Elemente erlaubt ist, sonst SINGLE_SELECTION
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
     * Wählt Item aus oder ab, je nachdem, ob es bereits zuvor gewählt war.
     * Benachrichtigt den Adapter, dass sich die Daten geändert haben.
     *
     * @param item zuletzt ausgewähltes Item
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
        listener.onItemSelected(item);
    }
}

