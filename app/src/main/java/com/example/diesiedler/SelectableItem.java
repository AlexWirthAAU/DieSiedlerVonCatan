package com.example.diesiedler;

public class SelectableItem {

    private boolean isSelected;
    private String text;

    SelectableItem(String text, boolean isSelected) {
        this.isSelected = isSelected;
        this.text = text;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() {
        return this.text;
    }

    /*
    public void setText(String text) {
        this.text = text;
    }
    */

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        SelectableItem itemCompare = (SelectableItem) obj;
        return itemCompare.getText().equals(this.getText());
    }

}
