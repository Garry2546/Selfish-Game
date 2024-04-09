package selfish.deck;

import java.io.Serializable;

/**
 * This class shows up the description of cards to be chosen
 * by the players in the game
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class Card implements Comparable<Card>, Serializable {
    /**
     * @param serialVersionUID readonly member
     * @param name             takes up the name of players
     * @param description      holds the decription of the card
     */
    private String name;
    private String description;
    private static final long serialVersionUID = 1L;

    /** empty Class constructor */
    Card() {

    }

    /**
     * Constructs a Card with a specified name and description.
     * This constructor initializes the card's properties directly upon creation.
     * 
     * @param name        String type parameter
     * @param description String type parameter
     */
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Retrieves the description of the card.
     * This method is typically used to provide players with information about the
     * card's effects or usage.
     * 
     * @return String value
     */
    public String getDescription() {
        return (description);
    }

    /**
     * Provides a string representation of the card, primarily showing the card's
     * name.
     * 
     * @return String value
     */
    public String toString() {
        return (name);
    }

    /**
     * Compares this card to another card based on the alphabetical order of their
     * names.
     * 
     * @param o card object
     * @return integer value
     */

    @Override
    public int compareTo(Card o) {
        int i = this.name.compareTo(o.toString());
        if (i == 0)
            return 0;
        else if (i > 0)
            return 1;
        else
            return -1;

    }
}
