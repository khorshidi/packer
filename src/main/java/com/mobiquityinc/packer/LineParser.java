package com.mobiquityinc.packer;

import com.mobiquityinc.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@link LineParser} is responsible for parsing a line from input file. A line pattern is "capacity :
 * (item1.index,item1.weight,€item2.cost) (item1.index,item1.weight,€item2.cost) ..." For example this is a valid line:
 * 81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
 *
 * @author Taher Khorshidi
 */
public class LineParser {

    private List<Validator<PackagingScenario>> scenarioValidators;
    private List<Validator<Item>> itemValidators;
    private Pattern linePattern = Pattern.compile("^(-?\\d+) : (.+)");
    private Pattern fieldsPattern = Pattern.compile("\\((-?\\d+),(-?\\d+\\.?\\d*?),€(-?\\d+)\\)");

    public LineParser(List<Validator<PackagingScenario>> scenarioValidators, List<Validator<Item>> itemValidators) {
        this.scenarioValidators = scenarioValidators;
        this.itemValidators = itemValidators;
    }

    /**
     * @param line input line.
     * @return the corresponding {@link PackagingScenario}
     * @throws ValidationException if the line doesn't match correct line pattern or contains an invalid scenario or any
     * invalid item.
     */
    public PackagingScenario parse(String line) throws ValidationException {
        Matcher lineMatcher = linePattern.matcher(line);
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
        for (Validator<PackagingScenario> validator : scenarioValidators) {
            validator.validate(scenario);
        }
    }

    private List<Item> extractItems(String itemsString) throws ValidationException {
        Matcher matcher = fieldsPattern.matcher(itemsString);
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
        for (Validator<Item> validator : itemValidators) {
            validator.validate(item);
        }
    }

}
