package com.may.soulrecipes.exception;

public class CreationRecipeException extends RecipeOperationException {

    public CreationRecipeException() {
    }

    public CreationRecipeException(String message) {
        super(message);
    }

    public CreationRecipeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationRecipeException(Throwable cause) {
        super(cause);
    }
}
