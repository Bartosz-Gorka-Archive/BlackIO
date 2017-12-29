package put.io.black.java;

import java.util.LinkedList;

public class Node {

    private int nestingLevel;
    private LinkedList<Node> children = new LinkedList<>();
    private String line;

    //todo visitator

    public Node(String line, int nestingLevel){
        this.line = line;
        this.nestingLevel = nestingLevel;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public int getChildrenCount(){
        return children.size();
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
        return "Node{" +
                "nestingLevel=" + nestingLevel +
                ", line='" + line + '\'' +
                ", childCount= "+ getChildrenCount() +
                '}';
    }
}
