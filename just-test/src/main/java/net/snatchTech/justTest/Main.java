package net.snatchTech.justTest;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Node node1 = new Node(5, null);
        Node node2 = new Node(3, null);
        List<Node> l1 = new ArrayList<>();
        l1.add(node1);
        l1.add(node2);
        Node node3 = new Node(1, l1);
        Node node4 = new Node(14, new ArrayList<>());
        List<Node> l2 = new ArrayList<>();
        l2.add(node4);
        l2.add(node3);
        Node node5 = new Node(1, l2);

        printSum(node5);

    }

    private static long recursion(Node node) {

        long result = node.value;

        if (node.childs == null || node.childs.size() == 0){
            return result;
        }

        for (Node n : node.childs) {
            result += recursion(n);
        }

        return result;
    }

    private static void printSum(Node node) {
        System.out.println(recursion(node));
    }

}

class Node{
    long value;
    List<Node> childs;

    public Node(long value, List<Node> childs) {
        this.value = value;
        this.childs = childs;
    }
}