package put.io.black.java.core.logic;

import java.util.LinkedList;

public class KeyNode extends Node{

    private LinkedList<Node> children = new LinkedList<>();

    public KeyNode(int nestingLevel, String line) {
        super(nestingLevel, line);
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public int getChildrenCount() {
        return children.size();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "KeyNode{" + "nestingLevel=" + getNestingLevel() +
                ", line='" + getLine() + '\'' +
                ", childCount= " + getChildrenCount() + '}';
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
