package selfish.deck;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import selfish.GameException;

/**
 * public class for deck of game
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class GameDeck extends Deck {
    /**
     * HACK_SUIT readonly string variables
     */
    public final static String HACK_SUIT = "Hack suit";
    /**
     * HOLE_IN_SUIT readonly string variables
     */
    public final static String HOLE_IN_SUIT = "Hole in suit";
    /**
     * LASER_BLAST readonly string variables
     */
    public final static String LASER_BLAST = "Laser blast";
    /**
     * OXYGEN readonly string variables
     */
    public final static String OXYGEN = "Oxygen";
    /**
     * OXYGEN_1 readonly string variables
     */
    public final static String OXYGEN_1 = "Oxygen(1)";
    /**
     * OXYGEN_2 readonly string variables
     */
    public final static String OXYGEN_2 = "Oxygen(2)";
    /**
     * OXYGEN_SIPHONE readonly string variables
     */
    public final static String OXYGEN_SIPHON = "Oxygen siphon";
    /**
     * ROCKET_BOOSTER readonly string variables
     */
    public final static String ROCKET_BOOSTER = "Rocket booster";
    /**
     * SHIELD readonly string variables
     */
    public final static String SHIELD = "Shield";
    /**
     * TETHER readonly string variables
     */
    public final static String TETHER = "Tether";
    /**
     * TRACTOR_BEAM readonly string variables
     */
    public final static String TRACTOR_BEAM = "Tractor beam";
    /**
     * serialVersionUID readonly long variables
     */
    private final static long serialVersionUID = 1L;

    /**
     * empty class constructor
     */
    public GameDeck() {

    }

    /**
     * parametrized class constuctor caliing load cards methode
     * 
     * @param path String variable
     * @throws Exception it throws exeption
     */
    public GameDeck(String path) throws Exception {
        try {
            add(super.loadCards(path));
            for (int i = 1; i <= 10; i++) {
                Oxygen o = new Oxygen(2);
                add(o);
            }
            for (int i = 1; i <= 38; i++) {
                Oxygen o1 = new Oxygen(1);
                add(o1);
            }
        } catch (Exception e) {
            throw new GameException(("File erorr!"), new FileNotFoundException());
        }

    }

    /**
     * methode for drawing oxygen card
     * 
     * @param value int variable
     * @return Oxygen
     */
    public Oxygen drawOxygen(int value) {
        if (this.size() == 0) {
            throw new IllegalStateException("Empty deck!!");
        }
        Boolean flag = false;
        ArrayList<Card> card = new ArrayList<>();
        Card crd = null;
        while (size() > 0) {
            crd = draw();
            if ((crd.toString().equals(OXYGEN_1) && value == 1) || (crd.toString().equals(OXYGEN_2) && value == 2)) {
                flag = true;
                break;
            } else {
                card.add(crd);
            }
        }
        if (flag == false)
            throw new IllegalStateException("Cannot find card");
        for (int i = card.size() - 1; i >= 0; i--) {
            add(card.get(i));
        }
        Oxygen o = (Oxygen) crd;
        return o;
    }

    /**
     * methode for exchanging value 2 oxygen card with two value 1 oxygen card
     * 
     * @param dbl class type variable
     * @return o
     */
    public Oxygen[] splitOxygen(Oxygen dbl) {
        if (dbl.toString().equals(GameDeck.OXYGEN_1))
            throw new IllegalArgumentException("cannot split 1 level oxygen");
        if (this.size() <= 1)
            throw new IllegalStateException("wrong!!");
        Oxygen o[] = { drawOxygen(1), drawOxygen(1) };
        add(dbl);
        Oxygen temp = o[0];
        o[0] = o[1];
        o[1] = temp;
        return o;
    }
}
