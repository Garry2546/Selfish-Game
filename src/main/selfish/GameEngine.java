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
     * public empty class constructor
     */
    private GameEngine() {

    }

    /**
     * parametized constructor
     * 
     * @param seed      Long var
     * @param gameDeck  String var
     * @param spaceDeck String var
     * @throws Exception throws exception
     */
    public GameEngine(long seed, String gameDeck, String spaceDeck) throws Exception {
        // this.random = new Random(seed);
        // gameDiscard=new GameDeck();
        // spaceDiscard=new SpaceDeck();
        // try {
        //     this.gameDeck = new GameDeck(gameDeck);
        //     this.spaceDeck = new SpaceDeck(spaceDeck);
        // } catch (Exception e) {
        //     throw new GameException("file error",e);
        // }
        // this.gameDeck.shuffle(this.random);
        // this.spaceDeck.shuffle(this.random);
        File f1=new File(spaceDeck);
        File f2=new File(gameDeck);
        if(f1.exists() && f2.exists())
        {
            this.gameDeck=new GameDeck(gameDeck);
            this.spaceDeck=new SpaceDeck(spaceDeck);
            this.gameDiscard=new GameDeck();
            this.spaceDiscard=new SpaceDeck();
        }
        else{
            throw new GameException("File eroor", null);
        }
        
        this.random=new Random(seed);
        this.gameDeck.shuffle(random);
        this.spaceDeck.shuffle(random);
    }
    

    /**
     * adding player methode
     * 
     * @param players String var
     * @return activeplayer size
     */
    public int addPlayer(String players) {
        if(hasStarted==true)
        throw new IllegalStateException("No more players can be added");
        if (activePlayers.size()==5)
            throw new IllegalStateException("No more players can be added");
        activePlayers.add(new Astronaut(players, this));
        return activePlayers.size();
    }

    /**
     * ending the turn of player methode
     * 
     * @return activeplayer size
     */
    public int endTurn() {
        if (corpses.contains(currentPlayer)!=true && currentPlayer!=null) {
            activePlayers.add(currentPlayer);
            currentPlayer = null;
        }
        currentPlayer = null;
        return activePlayers.size();
    }

    /**
     * methode for ending game
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
     * methode for returning list type of all players
     * 
     * @return list of players
     */
    public List<Astronaut> getAllPlayers() {
        List<Astronaut> ast = new ArrayList<>();
        ast.addAll(activePlayers);
        ast.addAll(corpses);
        if (this.currentPlayer != null && !corpses.contains(this.currentPlayer)){
            ast.add(currentPlayer);
        }
        return ast;
    }

    /**
     * public getter for current player
     * 
     * @return currentplayer
     */
    public Astronaut getCurrentPlayer() {
        if (hasStarted == false)
            return null;
        return currentPlayer;
    }

    /**
     * methode for count of all players
     * 
     * @return total player size
     */
    public int getFullPlayerCount() {
        return (getAllPlayers().size());
    }

    /**
     * this methode returns gamedeck
     * 
     * @return gamedeck ref
     */
    public GameDeck getGameDeck() {
        
        return gameDeck;
    }

    /**
     * methode for discarding game
     * 
     * @return gamediscard ref
     */
    public GameDeck getGameDiscard(){
       
        return gameDiscard;
    }

    /**
     * public methode returning null
     * 
     * @return spacedeck ref
     */
    public SpaceDeck getSpaceDeck() {
        
        return spaceDeck;
    }

    /**
     * methode for getting space discard
     * 
     * @return spacediscard ref
     */
    public SpaceDeck getSpaceDiscard() {
       
        return spaceDiscard;
    }

    /**
     * returning winner player methode
     * 
     * @return winner
     */
    public Astronaut getWinner() {
        for (Astronaut i : activePlayers) {
            if (i.hasWon() == true)
            {
                return i;
            }
        }
        return null;

    }

    /**
     * methode to kill the player
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
     * methode loading the game 
     * 
     * @param path String var
     * @return  gameEngine ref
     * @throws GameException it throws exception
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
     * merging deck methode
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
     * saving the game method
     * 
     * @param path string var
     * @throws Exception throws exception
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
     * method for splitting oxygen cards
     * 
     * @param dbl class ref var
     * @return oxygen ref array
     */
    public Oxygen[] splitOxygen(Oxygen dbl) {
        Oxygen[] o=null;
        if((gameDeck.size()==0 && gameDiscard.size()==1) || (gameDeck.size()==1 && gameDiscard.size()==0)){
            throw new IllegalStateException();
        }
        if(gameDiscard.size()>1)
        {
            o=gameDiscard.splitOxygen(dbl);
            gameDiscard.shuffle(random);
        }
        else if(gameDeck.size()>1)
        {
            o=gameDeck.splitOxygen(dbl);
        }
        else if(gameDeck.size()==1 && gameDiscard.size()==1){
            mergeDecks(gameDeck, gameDiscard);
            o=gameDeck.splitOxygen(dbl);
        }
        return o;
    }

    /**
     * this methode is used to start the game
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
     * this methode defines the turn of a player
     */
    public void startTurn(){
        if (hasStarted == false || activePlayers.size() == 0 || getWinner() != null || currentPlayer != null) {
            throw new IllegalStateException("Wrong approach to start turn!!");
        }
        currentPlayer = activePlayers.iterator().next();
        activePlayers.remove(currentPlayer);

    }

    /**
     * this function is used to remove oxygen from players hand and add one card in
     * his track
     * 
     * @param traveller Astronaut refrence variable
     * @return Card
     * @exception Exception it throws exception
     */
    public Card travel(Astronaut traveller){
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
/**
  * this return dead players
  * @return list
 */
 public List<Astronaut> getCorpse(){
    return corpses;
 }
 /**
  * this return active players
  * @return list
 */
 public List<Astronaut> getActivePlayers(){
    return (List<Astronaut>) activePlayers;
 }
}
