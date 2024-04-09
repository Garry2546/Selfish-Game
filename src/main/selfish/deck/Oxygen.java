package selfish.deck;

/**
 * public oxygen class for oxygen cards
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class Oxygen extends Card {
    /**
     * @param value variable for storing int value
     */
    private int value;
    /**
     * @param serialVersionUID static final
     */
    private static final long serialVersionUID = 1L;

    /**
     * empty class constructor
     */
    public Oxygen() {

    }

    /**
     * Default constructor for creating an Oxygen card with uninitialized
     * properties.
     * 
     * @param value value for variable
     */
    public Oxygen(int value) {
        super();
        this.value = value;
    }

    /**
     * Retrieves the oxygen value of the card.
     * This method is typically used in game logic to determine how much oxygen a
     * player can use or gain.
     * 
     * @return The integer value of the oxygen level on the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Provides a string representation of the Oxygen card, including its value.
     * This override is useful for displaying the card's specific type and value in
     * a user interface or log.
     * 
     * @return A string that describes the oxygen card with its value.
     */
    public String toString() {
        return "Oxygen(" + value + ")";
    }

    /**
     * Compares this Oxygen card with another card for ordering. The comparison is
     * based on the oxygen value.
     * This method is essential for sorting or comparing cards based on their oxygen
     * levels.
     * 
     * @param crd The other Card object to be compared to this one.
     * @return A negative integer, zero, or a positive integer as this card has
     *         less, equal, or more oxygen than the specified card.
     */

    @Override
    public int compareTo(Card crd) {
        String c = crd.toString();
        int val = 0;
        if (c.equals(GameDeck.OXYGEN_1))
            val = 1;
        if (c.equals(GameDeck.OXYGEN_2))
            val = 2;
        if (this.value == val)
            return 0;
        else if (this.value > val)
            return 1;
        else
            return -1;
    }
}
