package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BinaryTree binaryTree = new BinaryTree();
        BinaryTree.generateFile(100000,"tree.txt");
        BinaryTree.parseFile(binaryTree,"tree.txt");
        System.out.println("********************");
        long time = System.currentTimeMillis();
        binaryTree.balance();
        System.out.println(System.currentTimeMillis() - time + " ms");
        long usedBytes = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576;
        System.out.println(binaryTree.nodes.size() + " elements");
        System.out.println(usedBytes + " mb");
    }
}
