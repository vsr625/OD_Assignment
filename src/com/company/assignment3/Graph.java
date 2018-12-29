package com.company.assignment3;

import java.util.HashMap;
import java.util.Scanner;

public class Graph {
    private static HashMap<Integer, Node> graph = new HashMap<>();
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int choice, id, id1, id2;
        do {
            System.out.println("1. Get Immediate parents of a node");
            System.out.println("2. Get Immediate children of a node");
            System.out.println("3. Get all ancestors of a node");
            System.out.println("4. Get all descendants of a node");
            System.out.println("5. Delete a dependency");
            System.out.println("6. Delete a node");
            System.out.println("7. Add a new dependency");
            System.out.println("8. Add a new node");
            System.out.println("9. Exit");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    id = readId("Node");
                    graph.get(id).displayParents();
                    break;
                case 2:
                    id = readId("Node");
                    graph.get(id).displayChildren();
                    break;
                case 3:
                    id = readId("Node");
                    graph.get(id).displayAncestors();
                    break;
                case 4:
                    id = readId("Node");
                    graph.get(id).displayDescendants();
                    break;
                case 5:
                    deleteDependency();
                    break;
                case 6:
                    deleteNode();
                    break;
                case 7:
                    addDependency();
                    break;
                case 8:
                    addNewNode();
                    break;
                case 9:
                    break;
            }
        } while (choice != 9);

    }

    private static void deleteDependency() {
        int parentId = readId("Parent"), childId = readId("Child");
        Node parent = graph.get(parentId), child = graph.get(childId);
        parent.removeChild(child);
        child.removeParent(parent);
        System.out.println("Dependency successfully deleted");
    }

    // Helper function to get the id of an existing node in the dependency graph
    private static int readId(String nodeName) {
        boolean idExists;
        int id;
        do {
            idExists = true;
            System.out.println("Enter the Id for " + nodeName);
            id = in.nextInt();
            if (!graph.containsKey(id)) {
                System.out.println(nodeName + " Id doesn't exist");
                idExists = false;
            }
        } while (!idExists);
        return id;
    }

    private static void deleteNode() {
        int id = readId("Deleting");
        Node node = graph.get(id);

        // Removing all the dependencies
        for (Node child : node.getChildren()) {
            child.removeParent(node);
        }

        graph.remove(node.getNodeId());
        System.out.println("Node successfully deleted");
    }

    private static void addDependency() {
        int parentId = readId("Parent"), childId = readId("Child");
        Node parent = graph.get(parentId), child = graph.get(childId);
        parent.addChild(child);
        child.addParent(parent);
        if (parent.cyclicDependencyCheck()) {
            System.out.println("Failed: There exists a cycle after adding the dependency");
            parent.removeChild(child);
            child.removeParent(parent);
        } else {
            System.out.println("Successfully added the dependency");
        }
    }

    private static void addNewNode() {
        boolean idUnique;
        int id;
        do {
            idUnique = true;
            System.out.println("Enter the id of the new node");
            id = in.nextInt();
            if (graph.containsKey(id)) {
                idUnique = false;
                System.out.println("You have entered an Id that is already used");
            }
        } while (!idUnique);

        System.out.println("Enter the name of the new node");
        String name = in.next();

        Node node = new Node(id, name);
        graph.put(id, node);
        System.out.println("New Node added successfully");
    }
}
