package com.mobiquityinc.packer;

/**
 * Data class representing an Item.
 *
 * @author Taher Khorshidi
 */
public class Item {

    private int index;
    private double weight;
    private int cost;

    public Item(int index, double weight, int cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public double getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
