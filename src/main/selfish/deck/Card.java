package selfish.deck;
import java.io.Serializable;

/**
 * This class shows up the description of cards to be chosen
 * by the players in the game
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class Card implements Comparable<Card>,Serializable{
    /** 
     * @param serialVersionUID readonly member
     * @param name takes up the name of players
     * @param description holds the decription of the card
      */
    private String name;
    private String description;
    private static final long serialVersionUID=1L;
    /** empty Class constructor*/
     Card(){

     }
     /**
      * public parametrized class constructor having two parameters
      * @param name String type parameter
      * @param description String type parameter
      */
    public Card(String name,String description){
        this.name=name;
        this.description=description;
    }
    /** 
     * getDescription public methode for accquiring the description
     * of the cards to be chosen
     * @return String value 
     */
    public String getDescription(){
        return(description);
    }
    /** 
     * toString public methode for simple conversion to strings
     * @return String value
     */
    public String toString(){
        return(name);
    }
    /**
     * compareto function for comparison
     * @param o card object
     * @return integer value
     */
    
    @Override
    public int compareTo(Card o) {
        int i=this.name.compareTo(o.toString());
        if(i==0)
        return 0;
        else if(i>0)
        return 1;
        else 
        return -1;
        
    }
}
