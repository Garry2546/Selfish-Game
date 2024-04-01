package selfish.deck;


/**
 * public oxygen class for oxygen cards
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class Oxygen extends Card{
/** 
 * @param value  variable for storing int value
 */
    private int value;
    /**
     *@param serialVersionUID static final
     */
    private static final long serialVersionUID=1L;
    /**
     * empty class constructor 
     */
   public Oxygen(){

    }
    /**
     * parametrized constructor
     * @param value value for variable
     */
    public Oxygen(int value){
        super();
        this.value=value;
    }
/** 
 * Method returning integer
 * @return int value
*/
    public int getValue(){
        return value;
    }
/** 
 * methode returning string 
 * @return oxygen value
*/
    public String toString(){
        return "Oxygen("+value+")";
    }
    /**
     * overidden function
     * @param c card type
     * @return int
     */
    @Override
    public int compareTo(Card crd) {
        String c=crd.toString();
        int val =0;
        if(c.equals(GameDeck.OXYGEN_1))
        val = 1;
        if(c.equals(GameDeck.OXYGEN_2))
        val = 2;
        if(this.value==val)
        return 0;
        else if(this.value>val)
        return 1;
        else
        return -1; 
    }
}

