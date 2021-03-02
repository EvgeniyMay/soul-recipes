package com.may.soulrecipes.exception;

public class RecipeNotFountException extends RecipeOperationException {

    public RecipeNotFountException() {
    }

    public RecipeNotFountException(String message) {
        super(message);
    }

    public RecipeNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeNotFountException(Throwable cause) {
        super(cause);
    }
}
