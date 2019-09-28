package com.mobiquityinc.packer;

import com.mobiquityinc.exception.ValidationException;

import java.util.function.Function;

/**
 * This class is responsible to check that a condition is met or not.
 *
 * @author Taher Khorshidi
 */
public class Validator<T> {

    private Function<T, Boolean> condition;
    private String message;

    /**
     * @param condition the condition to check
     * @param message   the message of {@link ValidationException} when the condition is not met
     */
    public Validator(Function<T, Boolean> condition, String message) {
        this.condition = condition;
        this.message = message;
    }

    /**
     * @param t the validation subject
     * @throws ValidationException when the condition is not met
     */
    public void validate(T t) throws ValidationException {
        if (condition.apply(t)) {
            throw new ValidationException(message);
        }
    }

}
