package org.example;
import java.util.ArrayList;
import java.util.List;

public class Node {
    public Node leftSon;
    public Node rightSon;
    public Node parent;
    public int value;

    public int deep = 0;

    public List<Node> children = new ArrayList<>();

    public Node(int value) {
        this.leftSon = null;
        this.rightSon = null;
        this.parent = null;
        this.value = value;
    }

}
