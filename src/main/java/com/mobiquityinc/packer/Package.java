package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data class holding a valid package data.
 *
 * @author Taher Khorshidi
 */
public class Package {

    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * @return a String representing the package item. The string contains all item indexes separated by ','.
     * If package is empty returns a '-' character.
     */
    public String output() {
        if (items.isEmpty()) {
            return "-";
        }
        return items.stream().map(i -> String.valueOf(i.getIndex())).collect(Collectors.joining(","));
    }

}
