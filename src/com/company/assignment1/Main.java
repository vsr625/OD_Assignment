package com.company.assignment1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Assignment 1 -> Inventory Management System
 */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Item> mInventory = new ArrayList<>();

        char choice;
        String name;
        double price, quantity;
        Item.Type type = Item.Type.RAW;
        int temp;

        Item argItem = scanArgs(args);

        if (argItem == null) {
            System.err.println("Error in the arguments passed!");
            System.err.println("Exiting");
            return;
        }

        System.out.println("Do you want to enter details of any other item (y/n):");
        choice = in.next().charAt(0);
        while (choice == 'y' || choice == 'Y') {
            System.out.println("Enter the Name of the new Item");
            name = in.next();
            System.out.println("Enter the price of the Item");
            price = in.nextDouble();
            System.out.println("Enter the quantity of the Item");
            quantity = in.nextDouble();

            boolean done; // Temporary variable used for Item type validation
            do {
                System.out.println("Enter the type of the Item: \n 1: RAW \n 2: MANUFACTURED \n 3: IMPORTED \n");
                temp = in.nextInt();
                done = true;
                switch (temp) {
                    case 1:
                        type = Item.Type.RAW;
                        break;
                    case 2:
                        type = Item.Type.MANUFACTURED;
                        break;
                    case 3:
                        type = Item.Type.IMPORTED;
                        break;
                    default:
                        done = false;
                        System.out.println("Invalid Item type entered");
                        System.out.println("Enter the type again.");
                }
            } while (!done);

            // Add the new item to the inventory
            mInventory.add(new Item(name, price, quantity, type));
            System.out.println("Calculated Total price for the item = " +
                    mInventory.get(mInventory.size() - 1).getTotalCost());

            System.out.println("Do you want to enter details of any other item (y/n):");
            choice = in.next().charAt(0);
        }
    }

    private static Item scanArgs(String[] args) {
        if ((args.length >= 2 && !args[0].equalsIgnoreCase("-name")) || args.length < 8) return null;
        String name = args[1];
        Item.Type type = Item.Type.RAW;
        double quantity = 0.0, price = 0.0;

        for (int i = 2; i < args.length; i += 2) {
            switch (args[i]) {
                case "-price":
                    price = Double.valueOf(args[i + 1]);
                    break;
                case "-quantity":
                    quantity = Double.valueOf(args[i + 1]);
                    break;
                case "-type":
                    switch (args[i + 1]) {
                        case "raw":
                            type = Item.Type.RAW;
                            break;
                        case "manufactured":
                            type = Item.Type.MANUFACTURED;
                            break;
                        case "imported":
                            type = Item.Type.IMPORTED;
                            break;

                    }
                    break;
            }
        }

        return new Item(name, price, quantity, type);
    }
}


