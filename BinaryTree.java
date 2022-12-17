package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTree {

    boolean isRootHadRoatetd = false; // This field need to second,third,fourth..(etc.) rotation.

    public BinaryTree(int root_value) {
        this.root = new Node(root_value);
        nodes.add(this.root);
    }

    public BinaryTree() {
    }

    List<Node> nodes = new ArrayList<>();

    public Node root;


    void insertLeft(Node root, Node current) { //Insert left son
        if (current.leftSon == null) {
            current.parent = root;
            root.leftSon = current;
            root.children.add(current);
        }
        nodes.add(current);
    }

    void insertRight(Node root, Node current) { //Insert right son
        if (current.rightSon == null) {
            current.parent = root;
            root.rightSon = current;
            root.children.add(current);
        }
        nodes.add(current);

    }
    
  
    //THE FOLLOWING METHODS (goLeft, goRight, goPrev) ARE NEEDED TO NAVIGATE THE TREE

    Node goLeft(Node root) {
        if (root.leftSon != null) {
            return root.leftSon;
        } else {
            return root;
        }
    }

    Node goRight(Node root) {
        if (root.rightSon != null) {
            return root.rightSon;
        } else {
            return root;
        }
    }

    Node goPrev(Node root) {
        if (root.parent != null) {
            return root.parent;
        } else {
            return root;
        }
    }

     public void printTree(Node root, int level) { 
        if (root == null) {
            return;
        } else {
            printTree(root.rightSon, ++level);
            for (int i = 0; i < level; i++) {
                System.out.print("|");
            }
            System.out.println(root.value);
            --level;
        }
        printTree(root.leftSon, ++level);
    }



    public void rotateLeft(Node root) { //Method to make left rotation
        boolean isRootOfTheTree = false;
        if (this.root.equals(root)) {
            isRootOfTheTree = true;
            this.isRootHadRoatetd = true;
        }
        try {
            Node newRoot = root.rightSon;
            root.rightSon = newRoot.leftSon;
            newRoot.leftSon = root;
            newRoot.parent = root.parent;
            try{
                root.parent.rightSon = newRoot;
            }
            catch (NullPointerException e){
            }
            root.parent = newRoot;
            if (isRootOfTheTree) {
                this.root = newRoot;
                this.root.parent = null;
                this.root.leftSon.parent = this.root;
            }
        }
        catch (NullPointerException e){
            rotateRight(root);
        }

    }

    public void rotateRight(Node root) { //Method to make left rotation
        boolean isRootOfTheTree = false;
        if (this.root.equals(root)) {
            isRootOfTheTree = true;
            this.isRootHadRoatetd = true;
        }

        try {
            Node newRoot = root.leftSon;
            root.leftSon = newRoot.rightSon;
            newRoot.rightSon = root;
            newRoot.parent = root.parent;
            root = newRoot;
            try{
                root.parent.leftSon = newRoot;
            }
            catch (NullPointerException e){
            }
            root.parent = newRoot;

            if (isRootOfTheTree) {
                this.root = newRoot;
            }
        }
        catch (NullPointerException e){
            rotateLeft(root);
        }

    }

    public static void generateFile(int size, String fileName) throws IOException { //Method for generate file
        File file = new File(fileName);
        if (file.createNewFile()) {
            System.out.println("File was created");
        }

        FileWriter fileWriter = new FileWriter(fileName);
        int root = (int) (Math.random() * 10);
        System.out.println(root);
        if (root == 0) root = 1;
        fileWriter.write(root + "\n");
        String[] commands = new String[]{"cl", "cr", "gl", "gr", "gp","cl","cr"};

        for (int i = 0; i < size; i++) {
            int command = (int) (Math.random() * 7);
            int value = (int) (Math.random() * 10000);
            switch (command) {
                case 0, 1,5,6 -> {
                    fileWriter.write(commands[command] + " " + value + "\n");
                }
                default -> {
                    fileWriter.write(commands[command] + "\n");
                }
            }
        }
        System.out.println("File was filled");
        fileWriter.close();
    }

    public static void parseFile(BinaryTree binaryTree, String fileName) throws IOException { //Method for parsing file
        File file = new File(fileName);
        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        binaryTree.root = new Node(Integer.parseInt(line));
        binaryTree.nodes.add(binaryTree.root);
        line = reader.readLine();
        Node current = binaryTree.root;
        while (line != null) {
            String[] buffer = line.split(" ");
                switch (buffer[0]) {
                    case "cl" -> {
                        if (current.leftSon == null) {
                            binaryTree.insertLeft(current, new Node(Integer.parseInt(buffer[1])));
                        }
                        line = reader.readLine();
                    }
                    case "cr" -> {
                        if (current.rightSon == null) {
                            binaryTree.insertRight(current, new Node(Integer.parseInt(buffer[1])));
                        }
                        line = reader.readLine();
                    }
                    case "gl" -> {
                        if (current.leftSon != null) {
                            current = binaryTree.goLeft(current);
                        }
                        line = reader.readLine();
                    }
                    case "gr" -> {
                        if (current.rightSon != null) {
                            current = binaryTree.goRight(current);
                        }
                        line = reader.readLine();
                    }
                    case "gp" -> {
                        if (current.parent != null) {
                            current = binaryTree.goPrev(current);
                        }
                        line = reader.readLine();
                    }
                    default -> System.out.println("Error");
                }
            }

        System.out.println("File was parsed");
    }



    public int traverseInOrderWithoutRecursion(Node node) { //Tree traversal method 
        Stack stack = new Stack<>();
        Node current = node;
        List<Node> visitedParents = new ArrayList<>();

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.leftSon;
            }

            Node top = (Node) stack.pop();
            if(!visitedParents.contains(top.parent)){
                ++node.deep;
                visitedParents.add(top.parent);
            }
            current = top.rightSon;
        }

        try{
            return node.deep;
        }catch (NullPointerException e){
            return 0;
        }

    }


    public void setNullDeep(){ //Helper method when re-rotating the tree
        for(Node node : this.nodes){
            node.deep = 0;
        }
    }


    public void balance(){ //Tree balancing method
        setNullDeep();
        Node bad_node = null;
        int delta = Integer.MIN_VALUE;
        for(Node current : nodes){
            if(Math.abs(traverseInOrderWithoutRecursion(current.rightSon) - traverseInOrderWithoutRecursion(current.leftSon)) > Math.abs(delta)){
                if(delta < 0){
                    try{
                        delta = current.rightSon.deep - current.leftSon.deep + 1;
                    }
                    catch (NullPointerException e){
                        if (current.rightSon == null){
                            try{
                                delta = current.leftSon.deep;
                            }
                            catch (NullPointerException b){
                                delta = 0;
                            }
                        }
                        else{
                            delta = current.rightSon.deep;
                        }
                    }
                }
                else if(delta > 0){
                    try{
                        delta = current.rightSon.deep - current.leftSon.deep - 1;
                    }
                    catch (NullPointerException e){
                        if(current.rightSon == null){
                            delta = current.leftSon.deep;
                        }
                        else{
                            delta = current.rightSon.deep;
                        }
                    }
                }
                if(current == this.root && !this.isRootHadRoatetd){
                    bad_node = current;
                }
                if(current!= this.root){
                    bad_node = current;
                }
            }
        }

        if(delta < 0){
            this.rotateRight(bad_node);
        }
        else{
            this.rotateLeft(bad_node);
        }
        System.out.println("dleta is : " + delta + "\n" + "bad node is : " + bad_node.value);
     }
}


