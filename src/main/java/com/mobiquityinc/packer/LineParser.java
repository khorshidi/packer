package com.mobiquityinc.packer;

import com.mobiquityinc.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taher Khorshidi
 */
public class LineParser {

    private List<Validator> scenarioValidators;
    private List<Validator> itemValidators;

    public LineParser(List<Validator> scenarioValidators, List<Validator> itemValidators) {
        this.scenarioValidators = scenarioValidators;
        this.itemValidators = itemValidators;
    }

    public PackagingScenario parse(String line) throws ValidationException {
        Matcher lineMatcher = Pattern.compile("^(-?\\d+) : (.+)").matcher(line);
        if (!lineMatcher.find()) {
            throw new ValidationException("invalid line: [" + line + "]");
        }
        String capacityString = lineMatcher.group(1);
        int capacity = 0;
        try {
            capacity = Integer.parseInt(capacityString);
        } catch (NumberFormatException e) {
            throw new ValidationException("invalid capacity '" + capacityString + "' in line : [" + line + "]");
        }
        String itemsString = lineMatcher.group(2);
        List<Item> items = extractItems(itemsString);
        PackagingScenario scenario = new PackagingScenario(items, capacity);
        validateScenario(scenario);
        return scenario;
    }

    private void validateScenario(PackagingScenario scenario) throws ValidationException {
        for (Validator validator : scenarioValidators) {
            validator.validate(scenario);
        }
    }

    private List<Item> extractItems(String itemsString) throws ValidationException {
        Matcher matcher = Pattern.compile("\\((-?\\d+),(-?\\d+\\.?\\d*?),€(-?\\d+)\\)").matcher(itemsString);
        List<Item> items = new ArrayList<>();
        try {
            while (matcher.find()) {
                int index = Integer.parseInt(matcher.group(1));
                double weight = Double.parseDouble(matcher.group(2));
                int cost = Integer.parseInt(matcher.group(3));
                Item item = new Item(index, weight, cost);
                validateItem(item);
                items.add(item);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("invalid number in item: [" + matcher.group() + "], " + e.getMessage());
        }
        return items;
    }

    private void validateItem(Item item) throws ValidationException {
        for (Validator validator : itemValidators) {
            validator.validate(item);
        }
    }

}
