package put.io.black.java.core.logic;

/**
 * Interface Visitor
 * @see Visitable
 * @see Node
 */
public interface Visitor {

    /**
     * Visit (check) node
     * @param node Node to check
     */
    void visit(Node node);
}
