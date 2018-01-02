package put.io.black.java.logic;

public class NodeViewer implements Visitor {
    @Override
    public void visit(Node node) {
        System.out.println("I look into : " + node);
    }
}
