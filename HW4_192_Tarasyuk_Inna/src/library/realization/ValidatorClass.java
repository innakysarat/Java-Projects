package library.realization;

import library.Validator;
import library.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.*;

public class ValidatorClass implements Validator {

    public Set<ValidationErrorClass> errors = new LinkedHashSet<>();
    static int count = 0;
    static int firstInit = 0;
    ArrayList<ArrayList<String>> path;
    static String firstField;
    static int num = 0;
    static int num1 = 0;
    static int length_str = 0;

    @Override
    public Set<ValidationErrorClass> validate(Object object) {
        if (object != null) {
            if (object.getClass().isAnnotationPresent(Constrained.class)) {
                Field[] fields = object.getClass().getDeclaredFields();
                if (firstInit == 0) {
                    path = new ArrayList<ArrayList<String>>(fields.length);
                    firstInit++;
                }
                for (int i = 0; i < fields.length - 1; i++) {
                    // получаем поля объекта
                    Field field = fields[i];
                    field.setAccessible(true);
                    // массив для конструирования пути одного поля
                    ArrayList<String> pathOneField = new ArrayList<String>();
                    pathOneField.add(field.getName());
                    // count отвечает за то, ушли ли мы в рекурсию 2 и более уровня
                    if (count == 0) {
                        firstField = field.getName();
                        length_str = firstField.length();
                    } else {
                        if (num1 == 0) {
                            firstField = firstField + "[" + num + "].";
                            num1++;
                        }
                        pathOneField.set(0, firstField + fields[i].getName());
                    }
                    try {
                        // создаём из поля объект
                        Object fl;
                        fl = field.get(object);
                        // получаем аннотации поля
                        Annotation[] annotations = field.getAnnotatedType().getDeclaredAnnotations();
                        getErrors(fl, annotations, pathOneField.size() - 1, pathOneField.get(0));
                        // если объект - лист, то вызываем рекурсивный метод validate
                        if (fl instanceof List) {
                            AnnotatedType annotatedType = field.getAnnotatedType(); //List<@ List < @...>, List<...>>>
                            recursiveValidate(fl, annotatedType, pathOneField);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return errors;
    }

    /**
     * Рекурсивный вызов метода validate для вложенных листов
     * @param object объект
     * @param annotatedType
     * @param path массив пути одного поля
     */
    public void recursiveValidate(Object object, AnnotatedType annotatedType, List<String> path) {
        try {
            AnnotatedType annotatedType1 = ((AnnotatedParameterizedType) annotatedType)
                    .getAnnotatedActualTypeArguments()[0];
            Annotation[] annotations = annotatedType1.getDeclaredAnnotations();
            // в этот метод мы заходим, если объект, полученный из поля, был листом
            // поэтому кастуем его к листу
            if (object !=null) {
                List<?> values = (List<?>) object;
                boolean err = false;
                // в цикле расширяем массив пути одного поля(листа) для нужного размера(т.е. размера самого листа)
                for (int i = 0; i < values.size(); i++) {
                    if (path.size() < values.size()) {
                        if (i < path.size())
                            path.set(i, path.get(0));
                        else path.add(path.get(0));
                    } else
                        path.set(i, path.get(0));
                }
                // проходимся по элементам листа
                for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
                    Object value = values.get(i);
                    String p = path.get(i) + "[" + i + "]";
                    err = getErrors(value, annotations, i, p);
                    // если элемент листа - лист, уходим в рекурсию
                    if (value instanceof List) {
                        path.set(i, p);
                        recursiveValidate(value, annotatedType1, path);
                    } else if (err) {
                        path.set(i, p);
                    }
                }
            }
        } catch (ClassCastException ignored) {
        }
    }

    /**
     * Метод для проверки аннотаций
     * @param fl объект
     * @param annotations список аннотаций
     * @param k
     * @param p путь
     * @return true, если ошибка
     */
    boolean getErrors(Object fl, Annotation[] annotations, int k, String p) {
        for (Annotation annotation : annotations) { // проходимся по аннотациям
            if (annotation instanceof NotNull) {
                if (fl == null) {
                    errors.add(new ValidationErrorClass(p, " must not be null", fl));
                    return true;
                }
            }
            if (annotation instanceof Positive) {
                if (fl != null) {
                    if (fl instanceof Number && ((Number) fl).longValue() < 0) {
                        errors.add(new ValidationErrorClass(p, " must be positive", fl));
                        return true;
                    }
                }
            }
            if (annotation instanceof Negative) {
                if (fl != null) {
                    if (fl instanceof Number && ((Number) fl).longValue() > 0) {
                        errors.add(new ValidationErrorClass(p, " must be negative", fl));
                        return true;
                    }
                }
            }
            if (annotation instanceof AnyOf) {
                AnyOf anyOf = (AnyOf) annotation;
                if (fl != null) {
                    boolean anyB = true;
                    String[] any = anyOf.value();
                    for (String s : any) {
                        assert fl instanceof String;
                        if (fl.equals(s)) {
                            anyB = false;
                            break;
                        }
                    }
                    if (anyB) {
                        errors.add(new ValidationErrorClass(p, " method AnyOf doesn't work", fl));
                        return true;
                    }
                }
            }
            if (annotation instanceof InRange) {
                InRange inRange = (InRange) annotation;
                if (fl != null) {
                    if (fl instanceof Number) {
                        boolean bigger = ((Number) fl).longValue() > inRange.max();
                        boolean smaller = ((Number) fl).longValue() < inRange.min();
                        if (bigger || smaller) {
                            errors.add(new ValidationErrorClass(p, " must be in range " +
                                    "between " + inRange.min() + " and " + inRange.max(), fl));
                            return true;
                        }
                    }
                }
            }
            if (annotation instanceof Size) {
                Size size = (Size) annotation;
                if (fl != null) {
                    if (sizeMethod(fl, size.max(), size.min())) {
                        errors.add(new ValidationErrorClass(p, " size must be in range " + size.min() + " and " + size.max(), fl));
                        return true;
                    }
                }
            }
            if (annotation instanceof NotEmpty) {
                if (fl != null) {
                    if (isEmptyMethod(fl)) {
                        errors.add(new ValidationErrorClass(p, " must not be empty", fl));
                        return true;
                    }
                }
            }
            if (annotation instanceof NotBlank) {
                if (fl != null) {
                    if (!(fl instanceof String)) throw new AssertionError();
                    if (((String) fl).isBlank()) {
                        errors.add(new ValidationErrorClass(p, " string must not be blank", fl));
                        return true;
                    }
                }
            }
        }
        if (fl != null) {
            if (fl.getClass().isAnnotationPresent(Constrained.class)) {
                ++count;
                num = k;
                validate(fl);
                count = 0;
                num1 = 0;
                firstField = firstField.substring(0, length_str);
                length_str = 0;
            }
            return false;
        }
        else {
            return false;
        }
    }

    /**
     * Метод для проверки аннотации Size
     * @param fl объект из поля
     * @param max макс размер
     * @param min мин размер
     * @return логическое значение = true, если ошибка, иначе false
     */
    private boolean sizeMethod(Object fl, long max, long min) {
        try {
            if (fl instanceof String)
                return ((String) fl).length() > max || ((String) fl).length() < min;
            if (fl instanceof Map<?, ?>) {
                return ((Map<?, ?>) fl).size() > max || ((Map<?, ?>) fl).size() < min;
            } else {
                boolean bigger = ((Collection<?>) fl).size() > max;
                boolean smaller = ((Collection<?>) fl).size() < min;
                return bigger || smaller;
            }
        } catch (ClassCastException ex) {
            System.out.printf("%s тип не поддерживает аннотацию Size", fl.getClass().getSimpleName());
        }
        return false;
    }

    /**
     * Метод для проверки аннотации NotEmpty
     * @param fl объект из поля
     * @return true, если ошибка, false, если всё верно
     */
    private boolean isEmptyMethod(Object fl) {
        try {
            if (fl instanceof String)
                return ((String) fl).isEmpty();
            if (fl instanceof Map<?, ?>)
                return ((Map<?, ?>) fl).isEmpty();
            else return ((Collection<?>) fl).isEmpty();
        } catch (ClassCastException ex) {
            System.out.printf("%s тип не поддерживает аннотацию isEmpty", fl.getClass().getSimpleName());
        }
        return false;
    }

}
