package put.io.black.java.core.logic;

/**
 * Node viewer - Visitor pattern
 * @see Visitor
 * @see Node
 */
public class NodeViewer implements Visitor {

    /**
     * Check data in Node (visit)
     * @param node Node to visit
     */
    @Override
    public void visit(Node node) {
        System.out.println("I look into: " + node);
    }
}
