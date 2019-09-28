package com.mobiquityinc.packer;

import java.util.Comparator;
import java.util.List;

/**
 * A Package Scenario is particular 0/1 knapsack problem that contains a list of items to be chosen and maximum capacity of knapsack.
 *
 * @author Taher Khorshidi
 */
public class PackagingScenario {

    private List<Item> items;
    private int capacity;

    /**
     * @param items    list of available items.
     * @param capacity maximum capacity of knapsack.
     */
    public PackagingScenario(List<Item> items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getCapacity() {
        return capacity;
    }

    /**
     * this method solves the knapsack problem using Dynamic Programming technique.
     *
     * @return a {@link Package} that have maximum cost.
     */
    public Package solve() {
        this.capacity = capacity * 100;
        int count = items.size();
        items.sort(Comparator.comparing(Item::getWeight));
        int[][] optimal = new int[count + 1][capacity + 1];
        for (int i = 0; i <= capacity; i++)
            optimal[0][i] = 0;
        for (int i = 1; i <= count; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (items.get(i - 1).getWeight() * 100 > j) {
                    optimal[i][j] = optimal[i - 1][j];
                } else {
                    optimal[i][j] = Math.max(
                            optimal[i - 1][j],
                            optimal[i - 1][j - (int) (items.get(i - 1).getWeight() * 100)]
                                    + items.get(i - 1).getCost());
                }
            }
        }
        int cost = optimal[count][capacity];
        int weight = capacity;
        Package output = new Package();
        for (int i = count; i > 0 && cost > 0; i--) {
            if (cost != optimal[i - 1][weight]) {
                output.addItem(items.get(i - 1));
                weight -= items.get(i - 1).getWeight() * 100;
                cost -= items.get(i - 1).getCost();
            }
        }
        return output;
    }

}
