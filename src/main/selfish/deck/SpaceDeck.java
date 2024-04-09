package selfish.deck;

import java.io.FileNotFoundException;

import selfish.GameException;

/**
 * public class Space deck for space cards
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class SpaceDeck extends Deck {
    /**
     * ASTEROID_FIELDreadonly String var
     */
    public static final String ASTEROID_FIELD = "Asteroid field";
    /**
     * BLANK_SPACE readonly String var
     */
    public static final String BLANK_SPACE = "Blank space";
    /**
     * COSMIC_RADIATION readonly String var
     */
    public static final String COSMIC_RADIATION = "Cosmic radiation";
    /**
     * GRAVITATIONAL_ANOMALY readonly String var
     */
    public static final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    /**
     * HYPERSPACE readonly String var
     */
    public static final String HYPERSPACE = "Hyperspace";
    /**
     * METEROID readonly String var
     */
    public static final String METEOROID = "Meteroid";
    /**
     * MYSTERIOUS_NEBULA readonly String var
     */
    public static final String MYSTERIOUS_NEBULA = "Mysterious nebula";
    /**
     * SOLAR_FLARE readonly String var
     */
    public static final String SOLAR_FLARE = "Solar flare";
    /**
     * USEFUL_JUNk readonly String var
     */
    public static final String USEFUL_JUNK = "Useful junk";
    /**
     * WORMHOLE readonly String var
     */
    public static final String WORMHOLE = "Wormhole";
    /**
     * serialVersionUID readonly long var
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for creating an empty SpaceDeck. This can be used when no
     * initial card loading is needed, or cards will be added in a different manner.
     */

    public SpaceDeck() {

    }

    /**
     * Constructs a SpaceDeck by loading cards from a specified file path. This
     * constructor initializes the space deck
     * with space cards defined in an external data file.
     * 
     * @param path Path to the file from which to load space card data.
     * @throws GameException if there is an issue loading cards from the file,
     *                       encapsulating any lower-level exceptions.
     */
    public SpaceDeck(String path) throws GameException {
        try {
            add(super.loadCards(path));
        } catch (Exception e) {
            throw new GameException("File error!", new FileNotFoundException());
        }
    }
}