package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.ValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class {@link Packer} can solve multiple 0/1 knapsack problems that represented in an input file. Every line in
 * input file is a particular 0/1 knapsack problem. The Line pattern is "capacity :
 * (item1.index,item1.weight,€item2.cost) (item1.index,item1.weight,€item2.cost) ..." For example this is a valid line:
 * 81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
 */
public class Packer {

    private Packer() {
    }

    public static void main(String[] args) throws APIException {
        String pack = Packer.pack("/home/taher/workspace/mobiquity/packer/src/test/resources/multi_line");
        System.out.println(pack);
    }

    /**
     * @param filePath input file absolute path.
     * @return an String representing the solution of all Knapsack problems in the file separated by new-line character
     * @throws APIException if file not found or file contain invalid lines.
     */
    public static String pack(String filePath) throws APIException {
        LineParser parser = new LineParser(buildScenarioValidators(), buildItemValidators());
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            List<String> lines = stream.collect(Collectors.toList());
            List<PackagingScenario> scenarios = new ArrayList<>();
            for (String line : lines) {
                scenarios.add(parser.parse(line));
            }
            return scenarios.stream().map(PackagingScenario::solve).map(Package::output)
                  .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new APIException("could not read file: " + e.getMessage(), e);
        } catch (ValidationException e) {
            throw new APIException("validation error: " + e.getMessage());
        }
    }

    /**
     * @return a list of validators on {@link PackagingScenario} object.
     */
    private static List<Validator<Item>> buildItemValidators() {
        return Arrays.asList(
              new Validator<>(a -> a.getWeight() > 100, "weight is greater than 100."),
              new Validator<>(a -> a.getWeight() <= 0, "weight is non-positive."),
              new Validator<>(a -> a.getCost() > 100, "cost is greater than 100.")
        );
    }

    /**
     * @return a list of validators on {@link Item} object.
     */
    private static List<Validator<PackagingScenario>> buildScenarioValidators() {
        return Arrays.asList(
              new Validator<>(a -> a.getCapacity() > 100, "capacity is greater than 100."),
              new Validator<>(a -> a.getCapacity() <= 0, "capacity is non-positive."),
              new Validator<>(a -> a.getItems().size() > 15, "more than 15 item in scenario.")
        );
    }

}
