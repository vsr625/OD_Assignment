package com.company.assignment4;

import com.company.assignment1.Item;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {
        // Same Item object from the first assignment
        ProducerConsumer PC = new ProducerConsumer();

        Thread producer = new Thread(() -> {
            try {
                PC.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                PC.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

    public static class ProducerConsumer {
        LinkedList<Item> unprocessed = new LinkedList<>();
        ArrayList<Item> completedProcessing = new ArrayList<>();
        boolean transferComplete = false;

        void producer() throws InterruptedException {
            System.out.println("First thread trying to read the data from the table");
            Connection database;
            Statement statement;
            // Establishing dbms connection
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                database = DriverManager.getConnection("");
                statement = database.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM TABLE_NAME");
                while (result.next()) {
                    Item.Type type = Item.Type.RAW;
                    switch (result.getString(4)) {
                        case "raw":
                            type = Item.Type.RAW;
                            break;
                        case "imported":
                            type = Item.Type.IMPORTED;
                            break;
                        case "manufactured":
                            type = Item.Type.MANUFACTURED;
                            break;
                    }
                    Item item = new Item(result.getString(1), result.getDouble(2), result.getDouble(3), type);
                    // To allow for synchronization between the second thread.
                    synchronized (this) {
                        unprocessed.addLast(item);
                        // Wake up the consumer thread if it was in wait state
                        notify();
                    }
                }
                transferComplete = true;
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't load jdbc driver");
                return;
            } catch (SQLException e) {
                System.err.println("Failed during database operation");
                return;
            }
        }

        void consumer() throws InterruptedException {
            System.out.println("Consumer thread retrieves the items added from the producer and prints the cost");
            while (!transferComplete || !unprocessed.isEmpty()) {
                synchronized (this) {
                    // Got to wait state if the buffer is empty
                    if (unprocessed.isEmpty()) wait();

                    Item item = unprocessed.removeFirst();
                    System.out.println("Item Name = " + item.getName() + " Total cost = " + item.getTotalCost());
                    completedProcessing.add(item);
                }
            }

        }
    }
}
