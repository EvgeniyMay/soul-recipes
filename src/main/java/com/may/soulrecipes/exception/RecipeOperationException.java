package com.may.soulrecipes.exception;

public class RecipeOperationException extends RuntimeException {

    public RecipeOperationException() {
    }

    public RecipeOperationException(String message) {
        super(message);
    }

    public RecipeOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeOperationException(Throwable cause) {
        super(cause);
    }
}
