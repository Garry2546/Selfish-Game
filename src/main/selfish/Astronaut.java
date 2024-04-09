package selfish;

import java.io.*;
import java.util.*;
import selfish.deck.*;

/**
 * all players class for controlling them
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class Astronaut implements Serializable {
    /**
     * @param game reference variable of GameEngine
     */
    private GameEngine game;
    /**
     * @param name String variable containing players name
     */
    private String name;
    /**
     * @param actions contains list of action cards
     */
    private List<Card> actions = new ArrayList<Card>();
    /**
     * @param oxygens contains list of oxygen cards
     */
    private List<Oxygen> oxygens = new ArrayList<Oxygen>();
    /**
     * @param track contains list of Cards
     */
    private Collection<Card> track = new ArrayList<Card>();
    /**
     * @param serialVersionUID readonly long data type variable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of the Astronaut class with the specified name and
     * associated game engine context.
     * 
     * @param name for storing name of players
     * @param game reference variable for GameEngine class
     */
    public Astronaut(String name, GameEngine game) {
        this.name = name;
        this.game = game;
    }

    /**
     * Adds a card to the player's hand, determining if it's an oxygen card or an
     * action card based on its type, and stores it in the respective list.
     * 
     * @param card class type var
     */
    public void addToHand(Card card) {
        if (card.toString().equals(GameDeck.OXYGEN_1) || card.toString().equals(GameDeck.OXYGEN_2)) {
            oxygens.add((Oxygen) card);
        } else {
            actions.add(card);
        }

    }

    /**
     * Adds a card to the track collection associated with this player, which may
     * affect gameplay depending on the card's function.
     * 
     * @param card class type variable
     */
    public void addToTrack(Card card) {
        track.add(card);

    }

    /**
     * Processes the action of breathing for the astronaut, which involves using
     * oxygen cards. It may split higher oxygen cards into two if necessary and
     * checks if the player is still alive.
     * 
     * @return oxygen list size
     */
    public int breathe() {
        int c1 = 0;
        for (Oxygen i : oxygens) {
            if (i.toString().equals(GameDeck.OXYGEN_1))
                c1 += 1;
        }
        if (c1 == 0) {
            Oxygen o = null;
            for (Oxygen i : oxygens) {
                if (i.toString().equals(GameDeck.OXYGEN_2)) {
                    o = i;
                    break;
                }
            }
            Oxygen[] oxy = game.getGameDeck().splitOxygen(o);
            oxygens.add(oxy[0]);
            game.getGameDiscard().add(o);
            game.getGameDiscard().add(oxy[1]);
            oxygens.remove(o);
            if (isAlive() == false)
                game.killPlayer(this);
        } else {
            for (Oxygen i : oxygens) {
                if (i.toString().equals(GameDeck.OXYGEN_1)) {
                    game.getGameDiscard().add(i);
                    oxygens.remove(i);
                    break;
                }
            }
            if (isAlive() == false)
                game.killPlayer(this);
        }
        return oxygenRemaining();
    }

    /**
     * Returns the astronaut's current distance from the ship, which is determined
     * by the number of cards in their track.
     * 
     * @return track size
     */
    public int distanceFromShip() {

        return (6 - track.size());
    }

    /**
     * Retrieves a sorted list of action cards currently held by the player.
     * 
     * @return list c
     */
    public List<Card> getActions() {
        List<Card> c = new ArrayList<>();
        c.addAll(actions);
        Collections.sort(c);
        return c;
    }

    /**
     * Generates a string representation of the action cards held by the player,
     * optionally enumerating them and/or excluding shield cards.
     * 
     * @param enumerated     boolean type var
     * @param excludeShields boolean type var
     * @return string
     */
    public String getActionsStr(boolean enumerated, boolean excludeShields) {
        List<Card> card = getActions();
        if (isAlive() == false)
            return "Astronaut is dead!";
        else if (card.size() == 0)
            return "";
        else {
            String str = "";
            List<Card> c = new ArrayList<Card>();
            List<Integer> qty = new ArrayList<Integer>();
            String str1[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
            for (Card crd : card) {
                if (crd.toString().equals(GameDeck.SHIELD) && excludeShields == true)
                    continue;
                boolean jasoos = false;
                for (Card i : c) {
                    if (i.toString().equals(crd.toString())) {
                        jasoos = true;
                        break;
                    }
                }
                if (jasoos == false) {
                    c.add(crd);
                    qty.add(hasCard(crd.toString()));
                }
            }
            for (int i = 0; i < c.size(); i++) {
                if (enumerated == false) {
                    if (i != c.size() - 1) {
                        if (qty.get(i) == 1)
                            str = str + c.get(i).toString() + ", ";
                        else
                            str = str + qty.get(i).toString() + "x " + c.get(i).toString() + ", ";
                    } else {
                        if (qty.get(i) == 1)
                            str = str + c.get(i).toString();
                        else
                            str = str + qty.get(i).toString() + "x " + c.get(i).toString();
                    }
                } else {
                    if (i != c.size() - 1)
                        str = str + "[" + str1[i] + "] " + c.get(i).toString() + ", ";
                    else
                        str = str + "[" + str1[i] + "] " + c.get(i).toString();
                }
            }
            return str;
        }
    }

    /**
     * Returns a list of all cards (action and oxygen) in the player's hand, sorted
     * by predefined order.
     * 
     * @return list of Cards
     */
    public List<Card> getHand() {
        List<Card> actionCard = new ArrayList<Card>();
        actionCard.addAll(getActions());
        actionCard.addAll(oxygens);
        List<Card> a = new ArrayList<Card>();
        List<String> str = new ArrayList<String>();
        str.add("Hack suit");
        str.add("Hole in suit");
        str.add("Laser blast");
        str.add("Oxygen(1)");
        str.add("Oxygen(2)");
        str.add("Oxygen siphon");
        str.add("Rocket booster");
        str.add("Shield");
        str.add("Tether");
        str.add("Tractor beam");

        for (String s : str) {
            for (Card c : actionCard) {
                if (s.equals(c.toString())) {
                    a.add(c);
                }
            }
        }
        return a;
    }

    /**
     * Provides a string representation of the player's entire hand, combining both
     * types of cards into a formatted list.
     * 
     * @return string
     */
    public String getHandStr() {
        List<Card> card = getActions();
        for (Oxygen o : oxygens) {
            if (o.getValue() == 2)
                card.add(o);
        }
        for (Oxygen o : oxygens) {
            if (o.getValue() == 1)
                card.add(o);
        }
        if (isAlive() == false)
            return "Astronaut is dead!";
        else if (card.size() == 0)
            return "";
        else {
            String str = "";
            List<Card> c = new ArrayList<Card>();
            List<Integer> quan = new ArrayList<Integer>();
            for (Card crd : card) {
                boolean flag = false;
                for (Card i : c) {
                    if (i.toString().equals(crd.toString())) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    c.add(crd);
                    quan.add(hasCard(crd.toString()));
                }
            }
            String stro = "";
            for (int i = 0; i < c.size(); i++) {
                if (i != c.size() - 1) {
                    if (quan.get(i) == 1) {
                        if (c.get(i).toString().equals(GameDeck.OXYGEN_1)
                                || c.get(i).toString().equals(GameDeck.OXYGEN_2))
                            stro = stro + c.get(i).toString() + ", ";
                        else
                            str = str + c.get(i).toString() + ", ";
                    } else {
                        if (c.get(i).toString().equals(GameDeck.OXYGEN_1)
                                || c.get(i).toString().equals(GameDeck.OXYGEN_2))
                            stro = stro + quan.get(i).toString() + "x " + c.get(i).toString() + ", ";
                        else
                            str = str + quan.get(i).toString() + "x " + c.get(i).toString() + ", ";
                    }
                } else {
                    if (quan.get(i) == 1) {
                        if (c.get(i).toString().equals(GameDeck.OXYGEN_1)
                                || c.get(i).toString().equals(GameDeck.OXYGEN_2))
                            stro = stro + c.get(i).toString();
                        else
                            str = str + c.get(i).toString();
                    } else {
                        if (c.get(i).toString().equals(GameDeck.OXYGEN_1)
                                || c.get(i).toString().equals(GameDeck.OXYGEN_2))
                            stro = stro + quan.get(i).toString() + "x " + c.get(i).toString();
                        else
                            str = str + quan.get(i).toString() + "x " + c.get(i).toString();
                    }
                }
            }
            str = stro + "; " + str;
            str = str.substring(0, str.length() - 2);
            return str;
        }
    }

    /**
     * Returns the collection of cards that are on the player's track.
     * 
     * @return collection cards
     */
    public Collection<Card> getTrack() {
        return track;
    }

    /**
     * Removes a specified card from the player's hand or track, performing
     * additional actions if the card is of type oxygen, and checks if the player
     * remains alive.
     * 
     * @param card class type variable
     */
    public void hack(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Argument not valid");
        }
        if (card instanceof Oxygen) {
            if (!(this.oxygens.contains(card))) {
                throw new IllegalArgumentException("Card not found");
            }
        } else {
            if (!(this.actions.contains(card))) {
                throw new IllegalArgumentException("Card not found");
            }
        }

        if (card.toString().equals(GameDeck.OXYGEN_1) || card.toString().equals(GameDeck.OXYGEN_2)) {
            oxygens.remove(card);
            if (isAlive() == false) {
                actions.clear();
                game.killPlayer(this);
            }
        } else {
            actions.remove(card);
            if (isAlive() == false) {
                actions.clear();
                game.killPlayer(this);
            }
        }

    }

    /**
     * Overloaded hack method that removes a card identified by its string name from
     * the player's hand, and handles specific game actions based on the card type.
     * 
     * @param card String type var
     * @return Card ref
     */
    public Card hack(String card) {
        if (card == null) {
            throw new IllegalArgumentException("Wrong argument");
        }
        Card crd = null;
        if (hasCard(card) != 0) {
            if (card.equals(GameDeck.OXYGEN_1) || card.equals(GameDeck.OXYGEN_2)) {
                for (int i = 0; i < oxygenRemaining(); i++) {
                    if (card.equals(oxygens.get(i).toString())) {
                        crd = oxygens.get(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < actions.size(); i++) {
                    if (card.equals(actions.get(i).toString())) {
                        crd = actions.get(i);
                        break;
                    }
                }
            }
            hack(crd);
        } else {
            throw new IllegalArgumentException("Card not found");
        }
        return crd;
    }

    /**
     * Checks the player's hand for the presence of a specified card and returns the
     * count of how many times the specified card appears.
     * 
     * @param card String type
     * @return count of cards available
     */
    public int hasCard(String card) {
        List<Card> h = getHand();
        int count = 0;
        for (Card i : h) {
            if (i.toString().equals(card))
                count++;
        }
        return count;
    }

    /**
     * Determines if the player has encountered a specific hazardous condition (like
     * a solar flare) based on the top card of their track.
     * 
     * @return bool
     */
    public boolean hasMeltedEyeballs() {
        Card c = peekAtTrack();
        if (c.toString().equals(SpaceDeck.SOLAR_FLARE)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the player has met the winning conditions (reaching the ship and
     * being alive).
     * 
     * @return true if player won
     */
    public boolean hasWon() {
        if (distanceFromShip() == 0 && isAlive() == true) {
            return true;
        } else
            return false;
    }

    /**
     * Checks if the player is still alive by ensuring they have sufficient oxygen
     * remaining.
     * 
     * @return bool value
     */
    public boolean isAlive() {

        if (oxygenRemaining() > 0) {
            return true;
        }

        else {
            return false;
        }

    }

    /**
     * Performs an action to remove the top card from the player's track, which can
     * represent a defensive or offensive game move.
     * 
     * @return Card ref
     */
    public Card laserBlast() {
        if (track.size() == 0) {
            throw new IllegalArgumentException("");
        }
        Card c = peekAtTrack();
        track.remove(c);
        return c;
    }

    /**
     * Calculates the total amount of oxygen left for the player by counting the
     * values of the oxygen cards in their hand.
     * 
     * @return count of oxygens
     */
    public int oxygenRemaining() {
        int count = 0;
        for (Oxygen i : oxygens) {
            if (i.toString().equals(GameDeck.OXYGEN_1))
                count++;
            if (i.toString().equals(GameDeck.OXYGEN_2))
                count += 2;

        }
        return count;
    }

    /**
     * Returns the top card from the player's track without removing it, useful for
     * previewing potential game effects.
     * 
     * @return Card
     */
    public Card peekAtTrack() {
        if (track.size() > 0) {
            List<Card> tr = (List<Card>) track;
            Collections.reverse(tr);
            return tr.get(0);
        } else
            return null;
    }

    /**
     * Removes an oxygen card from the player's hand, potentially splitting
     * higher-value cards, and handles associated game logic.
     * 
     * @return oxygen card
     */
    public Oxygen siphon() {
        int i = (int) (Math.random() * oxygens.size());
        Oxygen o1 = oxygens.get(i);
        if (o1.toString().equals(GameDeck.OXYGEN_2)) {
            Oxygen[] o2 = game.getGameDeck().splitOxygen(o1);
            oxygens.add(o2[1]);
            game.getGameDiscard().add(o1);
            hack(o1);
            return o2[0];

        } else {
            hack(o1);
            return o1;
        }

    }

    /**
     * Randomly selects a card from the player's hand and removes it, simulating a
     * stealing action in the game.
     * 
     * @return Card
     */
    public Card steal() {
        List<Card> crd = getHand();
        Random random = new Random();
        int v = random.nextInt(crd.size());
        Card card = null;
        card = crd.get(v);
        hack(card);
        return card;
    }

    /**
     * Exchanges the track cards of this astronaut with another, impacting their
     * relative positions or conditions in the game.
     * 
     * @param swapee class type variable
     */
    public void swapTrack(Astronaut swapee) {
        List<Card> strack = (List<Card>) track;
        track = swapee.track;
        swapee.track = strack;
    }

    /**
     * Returns a string representation of the astronaut's name.
     *
     * @return name
     */
    public String toString() {
        if (!isAlive())
            return name + " (is dead)";
        else
            return name;
    }

}