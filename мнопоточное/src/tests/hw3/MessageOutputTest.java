package hw3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageOutputTest {
    Cart cart = new Cart(0, 0);
    Cart cart2 = new Cart(10, 15);
    MessageOutput mes = new MessageOutput(cart);
    MessageOutput mes2 = new MessageOutput(cart2);
    MessageOutput sleep = new MessageOutput(cart);


    @Test
    void testConstructors() {
        assertEquals(mes.cart, cart);
        assertNotEquals(mes.cart, cart2);
        assertNotEquals(mes.cart, null);
        assertEquals(mes2.cart, cart2);
        assertNotEquals(mes2.cart, cart);
        assertNotEquals(mes2.cart, null);
    }

}