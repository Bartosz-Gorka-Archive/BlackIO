package put.io.black.java;

import java.util.LinkedList;

public class Node {

    private int nestingLevel;
    LinkedList<Node> children = new LinkedList<>();
    String line;

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

}
