package put.io.black.java.logic;

import java.util.LinkedList;

/**
 * Node - scenario line
 * @see put.io.black.java.logic.Visitable
 */
public class Node implements Visitable {

    /**
     * Node's nesting level
     */
    private int nestingLevel;
    /**
     * List with Children -> Node with reference to next nodes
     */
    private LinkedList<Node> children = new LinkedList<>();
    /**
     * Node's text (single line)
     */
    private String line;

    /**
     * Node - one, single scenario line
     * @param line String with scenario line
     * @param nestingLevel Line nesting level
     */
    public Node(String line, int nestingLevel) {
        this.line = line;
        this.nestingLevel = nestingLevel;
    }

    /**
     * Add child to Nodes list
     * @param child Child node
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Count children (only direct)
     * @return Number of children
     */
    public int getChildrenCount() {
        return children.size();
    }

    /**
     * Verify - Node with children
     * @return Boolean with status
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get Node's nesting level
     * @return Nesting level
     */
    public int getNestingLevel() {
        return nestingLevel;
    }

    /**
     * Get Node's line text
     * @return String with text
     */
    public String getLine() {
        return line;
    }

    /**
     * Get Node's children as LinkedList
     * @return LinkedList with children
     */
    public LinkedList<Node> getChildren() {
        return children;
    }

    /**
     * toString() override to ensure data in debug
     * @return Node description to debug
     */
    @Override
    public String toString() {
        return "Node{" + "nestingLevel=" + nestingLevel +
                ", line='" + line + '\'' +
                ", childCount= " + getChildrenCount() + '}';
    }

    /**
     * Visit node - Visitor pattern
     * @param visitor Visitor to visit node
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
