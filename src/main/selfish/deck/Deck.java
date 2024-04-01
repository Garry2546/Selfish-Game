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
     * loadcards methode for loading cards
     * 
     * @param path String type
     * @return list of cards
     * @throws Exception it throws exception
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
     * conversion of string to cards
     * 
     * @param str String type
     * @return array of cards
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
     * method for adding cards
     * 
     * @param card class type var
     * @return card size
     */
    public int add(Card card) {
        cards.add(card);
        return cards.size();
    }

    /**
     * adding more than one cards at a time method
     * 
     * @param cards list of cards
     * @return card size
     */
    protected int add(List<Card> cards) {
        this.cards.addAll(cards);
        return this.cards.size();
    }

    /**
     * public methode for drawing cards
     * 
     * @return c
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
     * methode for removing cards
     * 
     * @param card class type var
     */
    public void remove(Card card) {
        cards.remove(card);

    }

    /**
     * methode for shuffling deck
     * 
     * @param random Random type
     */
    public void shuffle(Random random) {
        Collections.shuffle((List<Card>) cards, random);
    }

    /**
     * methode for getting size of deck
     * 
     * @return cards size
     */
    public int size() {
        return cards.size();
    }
}
