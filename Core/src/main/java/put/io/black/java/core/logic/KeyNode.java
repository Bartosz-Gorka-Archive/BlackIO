package put.io.black.java.core.logic;

import java.util.LinkedList;

/**
 * Key node in scenario
 * @see Node
 */
public class KeyNode extends Node {
    /**
     * List with node children
     */
    private LinkedList<Node> children = new LinkedList<>();

    /**
     * Constructor
     * @param nestingLevel Node nesting level
     * @param line Node text
     */
    public KeyNode(int nestingLevel, String line) {
        super(nestingLevel, line);
    }

    /**
     * Add new child to children list
     * @param child Child to add
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Count children
     * @return Number of children
     */
    public int getChildrenCount() {
        return children.size();
    }

    /**
     * Check children list not empty
     * @return Status about has children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Getter - children list
     * @return Children list
     */
    public LinkedList<Node> getChildren() {
        return children;
    }

    /**
     * Custom toString
     * @return KeyNode details
     */
    @Override
    public String toString() {
        return "KeyNode{" + "nestingLevel=" + getNestingLevel() +
                ", line='" + getLine() + '\'' +
                ", childCount= " + getChildrenCount() + '}';
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
