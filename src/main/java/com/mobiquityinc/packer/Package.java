package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Taher Khorshidi
 */
public class Package {

    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public String output() {
        if (items.isEmpty()) {
            return "-";
        }
        return items.stream().map(i -> String.valueOf(i.getIndex())).collect(Collectors.joining(","));
    }

}

