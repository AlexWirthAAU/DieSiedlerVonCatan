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

    String getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object obj) {

        SelectableItem itemCompare;

        if (obj != null) {

            if (obj instanceof SelectableItem) {

                itemCompare = (SelectableItem) obj;
                String testString = itemCompare.getText();
                return testString.equals(this.getText());
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
