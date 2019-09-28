package com.mobiquityinc.exception;

/**
 * Checked exception thrown when a validation condition is not met. see {@link com.mobiquityinc.packer.Validator}
 *
 * @author Taher Khorshidi
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}

