package com.may.soulrecipes.exception;

public class UpdatingRecipeException extends RecipeOperationException {

    public UpdatingRecipeException() {
        super();
    }

    public UpdatingRecipeException(String message) {
        super(message);
    }

    public UpdatingRecipeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdatingRecipeException(Throwable cause) {
        super(cause);
    }
}
