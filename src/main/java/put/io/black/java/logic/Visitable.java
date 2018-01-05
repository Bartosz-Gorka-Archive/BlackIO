package put.io.black.java.logic;

/**
 * Interface Visitable
 * @see Visitor
 */
public interface Visitable {

    /**
     * Accept visitor check
     * @param visitor Visitor - guess
     */
    public void accept(Visitor visitor);
}
