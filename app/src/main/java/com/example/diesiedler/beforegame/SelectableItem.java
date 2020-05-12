package com.example.diesiedler.beforegame;

/**
 * @author Christina Senger
 * <p>
 * Model-Klasse für die auszuwählenden Elemente einer Recycerview
 */
public class SelectableItem {

    private boolean isSelected;
    private String text;

    /**
     * Konstruktor
     *
     * @param text Textinhalt des Items
     * @param isSelected Item ausgewählt?
     */
    SelectableItem(String text, boolean isSelected) {
        this.isSelected = isSelected;
        this.text = text;
    }

    // Getter isSelected
    boolean isSelected() {
        return isSelected;
    }

    // Setter isSelected
    void setSelected(boolean selected) {
        isSelected = selected;
    }

    // Getter text
    String getText() {
        return this.text;
    }

    /**
     * Überprüft ob die Variable Text eines initialisierten,
     * obj der Klasse SelectableItem gleich dem Text der aktuellen
     * Instanz ist.
     *
     * @param obj Referenz des zu vergleichenden Objekt
     * @return true, wenn obj nicht null, Instanz der Klasse
     * Selectableitem und dessen Text gleiche jenem von der
     * aktuellen Instanz. Sonst false.
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
