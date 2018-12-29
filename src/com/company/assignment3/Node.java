package com.company.assignment3;

import java.util.ArrayList;
import java.util.HashSet;

public class Node {
    private int nodeId;
    private String nodeName;
    private ArrayList<Node> parents;
    private ArrayList<Node> children;

    Node(int id, String name) {
        nodeId = id;
        nodeName = name;
        parents = new ArrayList<>();
        children = new ArrayList<>();
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public ArrayList<Node> getParents() {
        return parents;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addParent(Node node) {
        this.parents.add(node);
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public void removeParent(Node node) {
        this.parents.remove(node);
    }

    public void removeChild(Node node) {
        this.children.remove(node);
    }

    public void displayChildren() {
        System.out.println("Immediate Children of the node are as follows:");
        for (Node child : this.children) {
            child.displayInfo();
        }
    }

    public void displayParents() {
        System.out.println("Immediate Parent of the node are as follows:");
        for (Node parent : this.parents) {
            parent.displayInfo();
        }
    }

    public void displayDescendants() {
        HashSet<Node> descendants = this.getDescendants();
        System.out.println("Descendants of the Node are as follows");
        for (Node node : descendants) {
            node.displayInfo();
        }
    }

    private HashSet<Node> getDescendants() {
        HashSet<Node> result = new HashSet<>(this.children);
        for (Node child : this.children) {
            result.addAll(child.getDescendants());
        }
        return result;
    }

    public void displayAncestors() {
        HashSet<Node> ancestors = this.getAncestors();
        System.out.println("Ancestors of the Node are as follows");
        for (Node node : ancestors) {
            node.displayInfo();
        }
    }

    private HashSet<Node> getAncestors() {
        HashSet<Node> result = new HashSet<>(this.parents);
        for (Node parent : this.parents) {
            result.addAll(parent.getAncestors());
        }
        return result;
    }

    public boolean cyclicDependencyCheck() {
        HashSet<Node> visited = new HashSet<>();
        return cyclicDependencyRecurse(visited);
    }

    public void displayInfo() {
        System.out.println("Node Id = " + this.nodeId + " Name = " + this.nodeName);
    }

    // In the simplest way, check to see if a node is visited again during the dfs traversal
    private boolean cyclicDependencyRecurse(HashSet<Node> visited) {
        // If the current node is already visited, return true
        if (visited.contains(this)) return true;

        boolean flag = false;
        visited.add(this);
        for (Node child : this.getChildren()) {
            if (child.cyclicDependencyRecurse(visited)) {
                flag = true;
                break;
            }
        }
        visited.remove(this);
        return flag;
    }
}
