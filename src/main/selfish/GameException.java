package selfish;

/**
 * class for all exceptions thrown in inherited classes
 * 
 * @author Geetinder Singh
 * @ selfish
 * @version JDK 11.0.16
 */
public class GameException extends Exception {
    /**
     * Constructs a new GameException with a specified message and a cause.
     * 
     * @param msg msg The detailed message that explains the reason for the
     *            exception.
     * @param e   The throwable cause associated with this exception. This can be
     *            used
     *            to trace back to another exception that caused this one. If the
     *            cause
     *            is non-existent or unknown, this parameter can be null.
     */
    public GameException(String msg, Throwable e) {
        super(msg, e);
    }
}
