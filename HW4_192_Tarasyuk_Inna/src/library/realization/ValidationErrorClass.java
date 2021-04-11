package library.realization;

import library.ValidationError;

public class ValidationErrorClass implements ValidationError {

    private final String path;
    private final String message;
    private final Object value;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return value;
    }

    public ValidationErrorClass(String path, String message, Object value) {
        this.value = value;
        this.path = path;
        this.message = message;
    }
}
