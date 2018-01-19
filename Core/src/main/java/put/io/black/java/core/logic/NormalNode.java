package put.io.black.java.core.logic;

/**
 * Normal node in scenario
 * @see Node
 */
public class NormalNode extends Node {

    /**
     * Node constructor
     * @param nestingLevel Node nesting level
     * @param line Line with text
     */
    public NormalNode(int nestingLevel, String line) {
        super(nestingLevel, line);
    }

    /**
     * Custom toString result
     * @return Normal node description
     */
    @Override
    public String toString() {
        return "NormalNode{" + "nestingLevel=" + getNestingLevel() +
                ", line='" + getLine() + '\'' + '}';
    }

    /**
     * Accept visitor
     * @param visitor Visitor - guest
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
