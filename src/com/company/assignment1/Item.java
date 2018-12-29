package com.company.assignment1;

/**
 * Individual Item in the Inventory
 */
public class Item {

    // Type of the Item
    public enum Type {
        RAW, MANUFACTURED, IMPORTED
    }

    private String name;
    private double price, quantity, totalCost;
    private Type mItemType;

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Item(String name, double price, double quantity, Type mItemType) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.mItemType = mItemType;
        computeTotalCost();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Type getmItemType() {
        return mItemType;
    }

    public void setmItemType(Type mItemType) {
        this.mItemType = mItemType;
    }

    private void computeTotalCost() {
        double tax, surcharge;
        switch (mItemType) {
            case RAW:
                // Raw: 12.5% of the item cost
                tax = price / 100 * 12.5;
                totalCost = price + tax;
                break;

            case MANUFACTURED:
                // Manufactured: 12.5% of the item cost + 2% of (item cost + 12.5% of item cost)
                tax = price / 100 * 12.5;
                tax += (price + tax) / 100 * 2.0;
                totalCost = price + tax;
                break;

            case IMPORTED:
                // Import duty
                tax = price / 100 * 10.0;
                totalCost = price + tax;

                // Calculate the surcharge for the item
                if (totalCost < 100) surcharge = 5;
                else if (totalCost > 100 && totalCost < 200) surcharge = 10;
                else surcharge = totalCost / 100 * 5.0;

                // Add the surcharge to the final cost
                totalCost += surcharge;
                break;
        }
        totalCost *= quantity;
    }
}
