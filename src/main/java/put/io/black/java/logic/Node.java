package put.io.black.java.logic;

import java.util.LinkedList;

public class Node implements Visitable{

    private int nestingLevel;
    private LinkedList<Node> children = new LinkedList<>();
    private String line;

    //TODO visitator

    public Node(String line, int nestingLevel) {
        this.line = line;
        this.nestingLevel = nestingLevel;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public int getChildrenCount() {
        return children.size();
    }

    public boolean hasChildren() {
        return children.size() != 0;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public String getLine() {
        return line;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Node{" + "nestingLevel=" + nestingLevel +
                ", line='" + line + '\'' +
                ", childCount= " + getChildrenCount() + '}';
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
