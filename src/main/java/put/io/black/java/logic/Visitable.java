package put.io.black.java.logic;

/**
 * Interface Visitable
 * @see Visitor
 */
public interface Visitable {

    /**
     * Accept visitor check
     * @param visitor Visitor - guest
     */
    public void accept(Visitor visitor);
}
