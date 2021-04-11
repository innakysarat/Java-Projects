package library;

import library.realization.ValidationErrorClass;

import java.util.Set;

public interface Validator {
    Set<ValidationErrorClass> validate(Object object);
}
