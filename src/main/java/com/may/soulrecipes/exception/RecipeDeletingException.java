package com.may.soulrecipes.exception;

public class RecipeDeletingException extends RecipeOperationException {

    public RecipeDeletingException() {
    }

    public RecipeDeletingException(String message) {
        super(message);
    }

    public RecipeDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeDeletingException(Throwable cause) {
        super(cause);
    }
}
