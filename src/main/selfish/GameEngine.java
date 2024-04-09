package selfish;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import selfish.deck.*;

/**
 * Game engine class for controlling game
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class GameEngine implements Serializable {
    /**
     * @param activePlayers    collection of object of Astronaut
     * @param corpses          List of object of Astronaut
     * @param currentPlayer    class refrence variable
     * @param hasStarted       boolean variable
     * @param random           Random type variable
     * @param gameDeck         class refrence variable
     * @param gameDiscard      class refrence variable
     * @param spaceDeck        class refrence variable
     * @param spaceDiscard     class refrence variable
     * @param serialVersionUID readonly int variable
     */
    private Collection<Astronaut> activePlayers = new ArrayList<Astronaut>();
    private List<Astronaut> corpses = new ArrayList<Astronaut>();
    private Astronaut currentPlayer;
    private boolean hasStarted;
    private Random random;
    private GameDeck gameDeck;
    private GameDeck gameDiscard;
    private SpaceDeck spaceDeck;
    private SpaceDeck spaceDiscard;
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor made private to prevent instantiation without
     * initializing required components.
     */
    private GameEngine() {

    }

    /**
     * Initializes a new game engine with specific seed values for randomness and
     * deck configurations. This constructor sets up the game and space decks,
     * shuffles them, and prepares for gameplay, ensuring files exist before
     * proceeding.
     * 
     * @param seed      Long var
     * @param gameDeck  String var
     * @param spaceDeck String var
     * @throws Exception throws exception
     */
    public GameEngine(long seed, String gameDeck, String spaceDeck) throws Exception {
        File f1 = new File(spaceDeck);
        File f2 = new File(gameDeck);
        if (f1.exists() && f2.exists()) {
            this.gameDeck = new GameDeck(gameDeck);
            this.spaceDeck = new SpaceDeck(spaceDeck);
            this.gameDiscard = new GameDeck();
            this.spaceDiscard = new SpaceDeck();
        } else {
            throw new GameException("File eroor", null);
        }

        this.random = new Random(seed);
        this.gameDeck.shuffle(random);
        this.spaceDeck.shuffle(random);
    }

    /**
     * Adds a new player to the game before it starts. Throws an exception if
     * attempted after the game has started or if the maximum number of players is
     * reached.
     * 
     * @param players String var
     * @return activeplayer size
     */
    public int addPlayer(String players) {
        if (hasStarted == true)
            throw new IllegalStateException("No more players can be added");
        if (activePlayers.size() == 5)
            throw new IllegalStateException("No more players can be added");
        activePlayers.add(new Astronaut(players, this));
        return activePlayers.size();
    }

    /**
     * Ends the current player's turn and resets the current player to null,
     * preparing for the next turn.
     * 
     * @return count of active players remaining.
     */
    public int endTurn() {
        if (corpses.contains(currentPlayer) != true && currentPlayer != null) {
            activePlayers.add(currentPlayer);
            currentPlayer = null;
        }
        currentPlayer = null;
        return activePlayers.size();
    }

    /**
     * Checks if the game is over, either by having no active players left or by
     * having a winner
     * 
     * @return boolean if game is over
     */

    public boolean gameOver() {
        if ((activePlayers.size() == 0) || (getWinner() != null))
            return true;
        else
            return false;
    }

    /**
     * Returns a list of all players in the game, including both active players and
     * corpses, ensuring the current player is included if alive.
     * 
     * @return list of players
     */
    public List<Astronaut> getAllPlayers() {
        List<Astronaut> ast = new ArrayList<>();
        ast.addAll(activePlayers);
        ast.addAll(corpses);
        if (this.currentPlayer != null && !corpses.contains(this.currentPlayer)) {
            ast.add(currentPlayer);
        }
        return ast;
    }

    /**
     * Retrieves the current player of the game. Returns null if the game has not
     * started.
     * 
     * @return currentplayer
     */
    public Astronaut getCurrentPlayer() {
        if (hasStarted == false)
            return null;
        return currentPlayer;
    }

    /**
     * Returns the total number of players participating in the game, including both
     * active players and corpses.
     * 
     * @return total player size
     */
    public int getFullPlayerCount() {
        return (getAllPlayers().size());
    }

    /**
     * Provides access to the main game deck used in the game.
     * 
     * @return gamedeck reference
     */
    public GameDeck getGameDeck() {

        return gameDeck;
    }

    /**
     * Provides access to the game discard pile.
     * 
     * @return gamediscard reference
     */
    public GameDeck getGameDiscard() {

        return gameDiscard;
    }

    /**
     * Provides access to the space deck used for specific game actions related to
     * space elements.
     * 
     * @return spacedeck reference
     */
    public SpaceDeck getSpaceDeck() {

        return spaceDeck;
    }

    /**
     * Provides access to the space discard pile.
     * 
     * @return spacediscard reference
     */
    public SpaceDeck getSpaceDiscard() {

        return spaceDiscard;
    }

    /**
     * Determines and returns the winner of the game based on the winning conditions
     * defined within each player's status.
     * 
     * @return winner
     */
    public Astronaut getWinner() {
        for (Astronaut i : activePlayers) {
            if (i.hasWon() == true) {
                return i;
            }
        }
        return null;

    }

    /**
     * Marks a player as deceased, moves them to the corpse list, and discards all
     * their cards into the game discard pile.
     * 
     * @param corpse refrence type
     */
    public void killPlayer(Astronaut corpse) {
        corpses.add(corpse);
        if (activePlayers.contains(corpse) == true)
            activePlayers.remove(corpse);
        for (Card i : corpse.getHand()) {
            gameDiscard.add(i);
        }
        corpse.getHand().clear();
    }

    /**
     * Loads a saved game state from a file, returning a fully instantiated
     * GameEngine object.
     * 
     * @param path String variable
     * @return gameEngine refrence
     * @throws GameException Throws an exception if file operations fail.
     */
    public static GameEngine loadState(String path) throws GameException {
        GameEngine g = new GameEngine();
        try (FileInputStream f = new FileInputStream(path);
                ObjectInputStream obj = new ObjectInputStream(f);) {
            g = (GameEngine) obj.readObject();
            obj.close();
        } catch (Exception exp) {
            throw new GameException("File error!", exp);
        }
        return g;
    }

    /**
     * Merges two decks together, typically used for combining main and discard
     * decks. Shuffles the second deck before merging.
     * 
     * @param deck1 class ref var
     * @param deck2 class ref var
     */
    public void mergeDecks(Deck deck1, Deck deck2) {
        deck2.shuffle(random);
        while (deck2.size() != 0) {
            deck1.add(deck2.draw());
        }
    }

    /**
     * Saves the current game state to a file to allow resuming later.
     * 
     * @param path string var
     * @throws Exception throws exceptions on failures of file handling.
     */
    public void saveState(String path) throws Exception {
        try (FileOutputStream f = new FileOutputStream(path);
                ObjectOutputStream obj = new ObjectOutputStream(f)) {
            obj.writeObject(this);
            obj.close();
        } catch (Exception e) {
            throw new GameException("File error!", e);
        }
    }

    /**
     * Splits a double oxygen card into two single oxygen cards. Used when handling
     * specific game mechanics related to oxygen management.
     * 
     * @param dbl class ref var
     * @return oxygen ref array
     */
    public Oxygen[] splitOxygen(Oxygen dbl) {
        Oxygen[] o = null;
        if ((gameDeck.size() == 0 && gameDiscard.size() == 1) || (gameDeck.size() == 1 && gameDiscard.size() == 0)) {
            throw new IllegalStateException();
        }
        if (gameDiscard.size() > 1) {
            o = gameDiscard.splitOxygen(dbl);
            gameDiscard.shuffle(random);
        } else if (gameDeck.size() > 1) {
            o = gameDeck.splitOxygen(dbl);
        } else if (gameDeck.size() == 1 && gameDiscard.size() == 1) {
            mergeDecks(gameDeck, gameDiscard);
            o = gameDeck.splitOxygen(dbl);
        }
        return o;
    }

    /**
     * Initializes game settings, distributes initial cards to players, and
     * officially starts the game. Throws exceptions if the game conditions for
     * starting are not met.
     */
    public void startGame() {
        if (activePlayers.size() > 5 || activePlayers.size() == 1 || hasStarted == true) {
            throw new IllegalStateException("Wrong approach");
        }
        for (Astronaut i : activePlayers) {
            i.addToHand(this.getGameDeck().drawOxygen(2));
            i.addToHand(this.getGameDeck().drawOxygen(1));
            i.addToHand(this.getGameDeck().drawOxygen(1));
            i.addToHand(this.getGameDeck().drawOxygen(1));
            i.addToHand(this.getGameDeck().drawOxygen(1));
        }
        for (int j = 0; j < 4; j++) {
            for (Astronaut i : activePlayers) {
                i.addToHand(gameDeck.draw());
            }
        }
        hasStarted = true;
    }

    /**
     * Begins a new turn for the next player in the sequence. Ensures game
     * conditions are correct to start a turn and sets the current player.
     */
    public void startTurn() {
        if (hasStarted == false || activePlayers.size() == 0 || getWinner() != null || currentPlayer != null) {
            throw new IllegalStateException("Wrong approach to start turn!!");
        }
        currentPlayer = activePlayers.iterator().next();
        activePlayers.remove(currentPlayer);

    }

    /**
     * Handles the travel action where a player advances by using oxygen cards and
     * drawing space deck cards, potentially facing hazards. Adjusts game state
     * based on the cards drawn and player's status.
     * 
     * @param traveller Astronaut refrence variable
     * @return Card
     * @exception Exception it throws exception
     */
    public Card travel(Astronaut traveller) {
        if (currentPlayer.oxygenRemaining() <= 1)
            throw new IllegalStateException("Travelling with a single oxygen card!");
        int count = 0;
        boolean flag = false;
        for (Card i : traveller.getHand()) {
            if (i.toString().equals(GameDeck.OXYGEN_2)) {
                gameDiscard.add(i);
                traveller.hack(i);
                flag = false;
                break;
            } else
                flag = true;
        }
        if (flag == true) {
            for (Card i : traveller.getHand()) {
                if (i.toString().equals(GameDeck.OXYGEN_1)) {
                    gameDiscard.add(i);
                    traveller.hack(i);
                    count += 1;
                }
                if (count == 2)
                    break;
            }
        }
        if (traveller.isAlive() == false)
            killPlayer(traveller);
        Card c = spaceDeck.draw();
        if (c.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY)) {
            spaceDiscard.add(c);
            return c;
        }
        if (c.toString().equals(SpaceDeck.HYPERSPACE)) {
            traveller.addToTrack(c);
            c = spaceDeck.draw();
            if (c.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY))
                spaceDiscard.add(c);
            return c;
        }
        traveller.addToTrack(c);
        return c;
    }

    /*
     * Returns a list of currently active players who have not been marked as
     * deceased during the game.
     */
    public List<Astronaut> getActivePlayers() {
        return (List<Astronaut>) activePlayers;
    }
}