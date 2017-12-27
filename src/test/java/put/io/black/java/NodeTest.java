package put.io.black.java;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    private String nodeTestText = "node text";
    private Node node;


    @Before
    public void setUp() throws Exception {
        node = new Node(nodeTestText, 1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void nodeDoesNotHaveChild(){
        assertEquals(0, node.getChildrenCount());
    }

    @Test
    public void nodeHasChild(){
        Node nodechild1 = new Node(nodeTestText, 1);
        Node nodechild2 = new Node(nodeTestText, 1);
        node.addChild(nodechild1);
        node.addChild(nodechild2);
        assertEquals(2, node.getChildrenCount());
    }

    @Test
    public void nodeNestLevelTest(){
        assertEquals(1,node.getNestingLevel());
    }

}