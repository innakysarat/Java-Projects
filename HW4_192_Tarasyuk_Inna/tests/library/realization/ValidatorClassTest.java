package library.realization;

import library.annotations.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;

import static java.util.Arrays.compare;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorClassTest {

    ValidatorClass validator = new ValidatorClass();

    @Test
    void lalala() {
        @Constrained
        class Goods {
            List<@Positive Integer> lol = new ArrayList<@Positive Integer>();
        }
        Goods Goods = new Goods();
        Goods.lol.add(null);
        var errorSet = validator.validate(Goods);
    }

    @Test
    void validate() {
        @Constrained
        class Inna {
            @Size(min = 4, max = 20)
            final List<@NotEmpty List<@Positive Integer>> list = List.of(List.of(-3, 2, 1));
            final List<@Negative Integer> list2 = List.of(5, 4, -3);
        }
        var inna = new Inna();
        @Constrained
        class GuestForm {
            @NotNull
            @NotBlank
            private String firstName;
            @NotBlank
            @NotNull
            private String lastName;
            @InRange(min = 0, max = 200)
            private int age;

            public GuestForm(String firstName, String lastName, int age) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.age = age;
            }
        }
        class Unrelated {
            @Positive
            private int x;

            public Unrelated(int x) {
                this.x = x;
            }
        }
        @Constrained
        class BookingForm {
            @NotNull
            @Size(min = 1, max = 5)
            private List<@NotNull GuestForm> guests;
            @NotNull
            private List<@AnyOf({"TV", "Kitchen"}) String> amenities;
            @NotNull
            @AnyOf({"House", "Hostel"})
            private String propertyType;
            @NotNull
            private Unrelated unrelated;

            public BookingForm(List<GuestForm> guests, List<String> amenities, String
                    propertyType, Unrelated unrelated) {
                this.guests = guests;
                this.amenities = amenities;
                this.unrelated = unrelated;
            }
        }
        List<GuestForm> guests = List.of(
                new GuestForm(/*firstName*/ null, /*lastName*/ "Def", /*age*/ 21),
                new GuestForm(/*firstName*/ "", /*lastName*/ "Ijk", /*age*/ -3)
        );
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                /*amenities*/ List.of("TV", "Piano"),
                /*propertyType*/ "Apartment",
                unrelated
        );
        var validator1 = validator.validate(bookingForm);
        assertEquals(validator1.size(), 5);
    }

    @Test
    void positiveTest() {
        @Constrained
        class Student {
            final List<@Positive Integer> positive = List.of(5, 4, 3);
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void positiveTestError() {
        @Constrained
        class Student {
            final List<@Positive Integer> positive = List.of(5, -4, 3);
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 1);
        Set<ValidationErrorClass> expected = new LinkedHashSet<ValidationErrorClass>();
        expected.add(new ValidationErrorClass("positive[1]", " must be positive", -4));
        boolean comp = compare(error, expected);
        assertTrue(comp);
    }

    @Test
    void negativeTest() {
        @Constrained
        class Student {
            final List<@Negative Integer> negative = List.of(-5, -4, -3);
        }

        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void negativeTestError() {
        @Constrained
        class Student {
            final List<@Negative Integer> negative = List.of(5, -4, -3);
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 1);
        Set<ValidationErrorClass> expected = new LinkedHashSet<ValidationErrorClass>();
        expected.add(new ValidationErrorClass("negative[0]", " must be negative", -4));
        boolean comp = compare(error, expected);
        assertTrue(comp);
    }

    @Test
    void sizeTest() {
        @Constrained
        class Student {
            @Size(min = 2, max = 7)
            final String model = "Student";
            @Size(min = 2, max = 7)
            final String model1 = "St";
            @Size(min = 2, max = 7)
            final String model2 = "Stud";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void sizeTestError() {
        @Constrained
        class Student {
            @Size(min = 3, max = 7)
            final String model = "StudentInna";
            @Size(min = 2, max = 7)
            final String model1 = "S";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 2);
    }

    @Test
    void inRangeTest() {
        @Constrained
        class Student {
            @InRange(min = 0, max = 200)
            final int age = 5;
            @InRange(min = 0, max = 200)
            final int age1 = 0;
            @InRange(min = 0, max = 200)
            final int age2 = 200;
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void inRangeTestError() {
        @Constrained
        class Student {
            @InRange(min = 0, max = 200)
            final int age = -5;
            @InRange(min = 0, max = 200)
            final int age1 = 201;
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 2);
        Set<ValidationErrorClass> expected = new LinkedHashSet<ValidationErrorClass>();
        expected.add(new ValidationErrorClass("age", " must be in range between 0 and 200", -5));
        expected.add(new ValidationErrorClass("age1", " must be in range between 0 and 200", 201));
        boolean comp = compare(error, expected);
        assertTrue(comp);
    }

    @Test
    void anyOfTest() {
        @Constrained
        class Student {
            @AnyOf({"Student1", "Student2"})
            final String student = "Student1";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void anyOfTestError() {
        @Constrained
        class Student {
            @AnyOf({"Student1", "Student2"})
            final String student = "Student3";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 1);
        Set<ValidationErrorClass> expected = new LinkedHashSet<ValidationErrorClass>();
        expected.add(new ValidationErrorClass("student", " method AnyOf doesn't work", "Student3"));
        boolean comp = compare(error, expected);
        assertTrue(comp);
    }

    @Test
    void notEmptyTest() {
        @Constrained
        class Student {
            @NotEmpty
            final List<List<Integer>> notEmpty = List.of(List.of(3, 2, 1));
            final List<@NotEmpty List<Integer>> notEmpty1 = List.of(List.of(3, 2, 1));
            @NotEmpty
            final String student = "Student";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void notEmptyTestError() {
        @Constrained
        class Student {
            final List<@NotEmpty List<Integer>> notEmpty = List.of(List.of());
            @NotEmpty
            final String student = "";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 2);
        Set<ValidationErrorClass> expected = new LinkedHashSet<ValidationErrorClass>();
        expected.add(new ValidationErrorClass("notEmpty[0]", " must not be empty", 0));
        expected.add(new ValidationErrorClass("student", " must not be empty", 0));
        boolean comp = compare(error, expected);
        assertTrue(comp);
    }

    @Test
    void notBlankTest() {
        @Constrained
        class Student {
            @NotBlank
            final String notBlank = "Student";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 0);
    }

    @Test
    void notBlankTestError() {
        @Constrained
        class Student {
            @NotBlank
            final String notBlank = "";
        }
        Student student = new Student();
        var error = validator.validate(student);
        assertEquals(error.size(), 1);
        Set<ValidationErrorClass> expected = new LinkedHashSet<ValidationErrorClass>();
        expected.add(new ValidationErrorClass("notBlank", " string must not be blank", ""));
        boolean comp = compare(error, expected);
        assertTrue(comp);
    }

    // тестовый пример формы с вложенными классами:
    @Constrained
    class Students {
        @Positive
        final Integer count = -5;
    }

    @Constrained
    class School {
        final Students students = new Students();
    }

    @Test
    void innerClass() {
        var school = new School();
        var error = validator.validate(school);
        Set<ValidationErrorClass> mySet = new LinkedHashSet<ValidationErrorClass>();
        mySet.add(new ValidationErrorClass(" students.count", " must be positive", -5));
        assertEquals(mySet.size(), error.size());
        compare(mySet, error);
    }

    private boolean compare(Set<ValidationErrorClass> error, Set<ValidationErrorClass> expected) {
        int i = 0;
        List<ValidationErrorClass> errorList = new ArrayList<>(error);
        List<ValidationErrorClass> expectedList = new ArrayList<>(expected);
        for (int j = 0; j < errorList.size(); j++) {
            if (!(errorList.get(i).getMessage().equals(expectedList.get(i).getMessage())) ||
                    !(errorList.get(i).getPath().equals(expectedList.get(i).getPath()))) {
                return false;
            }
        }
        return true;
    }

    @Test
    void getErrors() {

    }
}