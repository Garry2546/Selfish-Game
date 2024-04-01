package selfish;
/**
 * class for all exceptions thrown in inherited classes
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class GameException extends Exception {
    /**
     * class constructor with parameters
     * @param msg msg var for reciving message
     * @param e for name of exception
     */
    public GameException(String msg,Throwable e){
        super(msg,e);   
     }
}
