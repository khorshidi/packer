package com.mobiquityinc.packer;

import com.mobiquityinc.exception.ValidationException;

import java.util.function.Function;

/**
 * @author Taher Khorshidi
 */
public class Validator<T> {

    private Function<T, Boolean> condition;
    private String message;

    public Validator(Function<T, Boolean> condition, String message) {
        this.condition = condition;
        this.message = message;
    }

    public void validate(T t) throws ValidationException {
        if (condition.apply(t)) {
            throw new ValidationException(message);
        }
    }

}

