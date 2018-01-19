package put.io.black.java.core.logic;

/**
 * Interface Visitable
 * @see Visitor
 * @see Node
 */
public interface Visitable {

    /**
     * Accept visitor check
     * @param visitor Visitor - guest
     */
    void accept(Visitor visitor);
}
