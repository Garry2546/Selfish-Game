package selfish.deck;

import java.util.*;

import selfish.GameException;

import java.io.*;

/**
 * public class for deck of cards
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 * @since 26-04-23
 */
public abstract class Deck implements Serializable {
    /**
     * @param cards list of cards
     */
    private Collection<Card> cards = new ArrayList<Card>();
    /**
     * @param serialVersionUID static final
     */
    private static final long serialVersionUID = 1L;

    /**
     * protected empty constructor
     */
    protected Deck() {

    }

    /**
     * Loads cards from a specified file path into a list. This method reads card
     * information from a file and converts it into Card objects.
     * 
     * @param path Path to the file containing card data.
     * @return A list of loaded Card objects.
     * @throws Exception if there is an issue reading from the file or parsing card
     *                   data.
     */
    protected static List<Card> loadCards(String path) throws Exception {
        ArrayList<Card> c = new ArrayList<Card>();
        int count = 0;
        String str;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((str = br.readLine()) != null) {
                if (count > 0) {
                    Card cd[] = stringToCards(str);
                    for (int i = 0; i < cd.length; i++) {
                        c.add(cd[i]);
                    }
                }
                count++;
            }
            br.close();

        } catch (Exception exp) {
            throw new GameException("File error", new FileNotFoundException());
        }

        return c;
    }

    /**
     * Converts a semicolon-separated string into an array of Card objects.
     * 
     * @param str The string to parse, expected to be in the format
     *            "name;description;count".
     * @return An array of Card objects.
     */
    protected static Card[] stringToCards(String str) {

        String temp[] = str.split(";");
        temp[2] = temp[2].trim();
        ArrayList<Card> crd = new ArrayList<Card>();
        for (int i = 0; i < Integer.valueOf(temp[2]); i++) {
            Card c = new Card(temp[0], temp[1].trim());
            crd.add(c);
        }
        Card cd[] = new Card[Integer.parseInt(temp[2])];
        return crd.toArray(cd);
    }

    /**
     * Adds a single card to the deck.
     * 
     * @param card The card to add.
     * @return The new size of the deck after adding the card.
     */
    public int add(Card card) {
        cards.add(card);
        return cards.size();
    }

    /**
     * Adds multiple cards to the deck at once.
     * 
     * @param cards The list of cards to add.
     * @return The new size of the deck after adding the cards.
     */
    protected int add(List<Card> cards) {
        this.cards.addAll(cards);
        return this.cards.size();
    }

    /**
     * Draws the top card from the deck.
     * 
     * @return The drawn Card.
     * @throws IllegalStateException if the deck is empty.
     */
    public Card draw() {
        if (this.size() == 0) {
            throw new IllegalStateException("empty!!");
        }
        List<Card> list = new ArrayList<>(this.cards);
        Card c = list.get(size() - 1);
        cards.remove(c);
        return c;
    }

    /**
     * Removes a specific card from the deck.
     * 
     * @param card The card to remove.
     */
    public void remove(Card card) {
        cards.remove(card);

    }

    /**
     * Shuffles the deck using the specified source of randomness.
     * 
     * @param random The random number generator to use for shuffling.
     */
    public void shuffle(Random random) {
        Collections.shuffle((List<Card>) cards, random);
    }

    /**
     * Gets the current number of cards in the deck.
     * 
     * @return The number of cards in the deck.
     */
    public int size() {
        return cards.size();
    }
}
