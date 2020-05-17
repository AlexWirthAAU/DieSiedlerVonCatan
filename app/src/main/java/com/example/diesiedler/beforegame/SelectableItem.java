package com.example.diesiedler.beforegame;

/**
 * @author Christina Senger
 * <p>
 * Model-Class for the selectable Elements of the Recycerview
 */
public class SelectableItem {

    private boolean isSelected;
    private String text;

    /**
     * Constructor
     *
     * @param text Text of the Item
     * @param isSelected Item selected?
     */
    SelectableItem(String text, boolean isSelected) {
        this.isSelected = isSelected;
        this.text = text;
    }

    // Getter Setter isSelected
    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    // Getter Text
    String getText() {
        return this.text;
    }

    /**
     * Überprüft ob die Variable Text eines initialisierten,
     * obj der Klasse SelectableItem gleich dem Text der aktuellen
     * Instanz ist.
     *
     * @param obj Reference of the Object to compare
     * @return true, when obj is not null, Instance of the class
     * Selectableitem and its Text is equal to that from the
     * actual Instance. Else false.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
