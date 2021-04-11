package hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    Cart cart = new Cart(0, 0);
    Animal swan = new Animal(1, 60, "Лебедь", cart);
    Animal crayfish = new Animal(2, 180, "Рак  ", cart);
    Animal pike = new Animal(3, 300, "Щука", cart);


    @Test
    void testConstructors() {
        assertEquals(swan.s, 1);
        assertEquals(swan.a, 60);
        assertNotEquals(swan.name, "Рак");
        assertEquals(swan.name, "Лебедь");
        assertNotEquals(crayfish.a, 300);
        assertEquals(pike.cart, cart);
        assertNotEquals(swan.cart, null);
    }

    @Test
    void testToString() {
        assertEquals("Лебедь: s = 1, угол = 60", swan.toString());
        assertNotEquals("Рак: s = 1, угол = 60", crayfish.toString());
        assertNotEquals("Щука :s = 3, угол = 300", pike.toString());
        assertNotEquals("", crayfish.toString());
        assertNotEquals("Щука: s =3, угол =300",crayfish.toString());
        assertNotEquals("Щука: s= 3, угол= 300",crayfish.toString());
    }
}