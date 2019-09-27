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
 *
 */
public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        LineParser parser = new LineParser(buildScenarioValidators(), buildItemValidators());
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            List<String> lines = stream.collect(Collectors.toList());
            List<PackagingScenario> scenarios = new ArrayList<>();
            for (String line : lines) {
                scenarios.add(parser.parse(line));
            }
            return scenarios.stream().map(PackagingScenario::solve).map(Package::output).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new APIException("could not read file: " + e.getMessage(), e);
        } catch (ValidationException e) {
            throw new APIException("validation error: " + e.getMessage());
        }
    }

    private static List<Validator> buildItemValidators() {
        return Arrays.asList(
                new Validator<Item>(a -> a.getWeight() > 100, "weight is greater than 100."),
                new Validator<Item>(a -> a.getWeight() <= 0, "weight is non-positive."),
                new Validator<Item>(a -> a.getCost() > 100, "cost is greater than 100.")
        );
    }

    private static List<Validator> buildScenarioValidators() {
        return Arrays.asList(
                new Validator<PackagingScenario>(a -> a.getCapacity() > 100, "capacity is greater than 100."),
                new Validator<PackagingScenario>(a -> a.getCapacity() <= 0, "capacity is non-positive."),
                new Validator<PackagingScenario>(a -> a.getItems().size() > 15, "more than 15 item in scenario.")
        );
    }

}
