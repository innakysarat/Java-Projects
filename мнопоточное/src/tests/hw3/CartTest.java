package hw3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart = new Cart(0, 0);
    Cart cart2 = new Cart(10, 15);
    Animal swan = new Animal(1, 60, "Лебедь", cart);
    Animal crayfish = new Animal(2, 180, "Рак  ", cart);
    Animal pike = new Animal(3, 300, "Щука", cart);


    @Test
    void change() {
        cart.change("Лебедь", 0, 60);
        assertEquals(cart.coordinates.get(0), 0);
        assertNotEquals(cart.coordinates.get(1), 10);
        cart.change("Лебедь", 10, 60);
        assertEquals(cart.coordinates.get(0), 0);
        assertNotEquals(cart.coordinates.get(0), 10);
        assertEquals(cart.x, 5.000000000000001);
        assertNotEquals(cart.y, 100);
        assertEquals(cart.y, 8.660254037844386);
    }

    @Test
    void testConstructors() {
        assertEquals(cart.x, 0);
        assertNotEquals(cart.x, 10);
        assertEquals(cart.y, 0);
        assertNotEquals(cart.y, 100);
        assertEquals(cart2.x, 10);
        assertNotEquals(cart2.y, 100);

    }

    @Test
    void write() {
        assertNotEquals("report (2 seconds): Лебедь push the cart to {0;0}", cart.write());
    }
}