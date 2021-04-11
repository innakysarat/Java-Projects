package martian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MartianTest {
    InnovatorMartian<String> A = new InnovatorMartian<>("A");
    InnovatorMartian<String> B = new InnovatorMartian<>("B");
    InnovatorMartian<String> O = new InnovatorMartian<>("B");
    InnovatorMartian<String> M = new InnovatorMartian<>("M");
    InnovatorMartian<Double> K = new InnovatorMartian<>(30.5);
    InnovatorMartian<Integer> L = new InnovatorMartian<>(30);
    InnovatorMartian<Integer> J = new InnovatorMartian<>(40);
    ConservatorMartian<String> D = new ConservatorMartian<>(A);
    ConservatorMartian<Double> C = new ConservatorMartian<>(K);

    @Test
    void getValue() {
        assertEquals("A", A.getValue());
        assertNotEquals("A", B.getValue());
        assertEquals(L.getValue(), 30);
        assertEquals(D.getValue(), "A");
        assertEquals(C.getValue(), 30.5);
    }

    @Test
    void getParent() {
        A.setChild(B);
        J.setParent(L);
        assertEquals(A, B.getParent());
        assertNotEquals(J, L.getParent());
        assertNull(K.getParent());
    }

    @Test
    void getChildren() {
        A.setChild(B);
        A.setChild(O);
        var collection = A.getChildren();
        assertTrue(collection.contains(B));
        assertTrue(collection.contains(O));
        assertFalse(collection.contains(M));
        assertNull(J.getChildren());
    }

    @Test
    void getDescendants() {
        A.setChild(B);
        B.setChild(O);
        B.setChild(M);
        var collection = A.getDescendants();
        assertTrue(collection.contains(B));
        assertTrue(collection.contains(O));
        assertTrue(collection.contains(M));
        assertFalse(collection.contains(J));
        assertNull(J.getDescendants());
    }

    @Test
    void getRoot() {
        A.setChild(B);
        B.setChild(O);
        O.setChild(M);
        assertEquals(A, O.getRoot());
        assertEquals(A, B.getRoot());
        assertEquals(A, M.getRoot());
        assertNotEquals(B, M.getRoot());
        assertNotEquals(B, O.getRoot());
    }

    @Test
    void hasChildWithValue() {
        A.setChild(B);
        B.setChild(O);
        O.setChild(M);
        assertTrue(A.hasChildWithValue("B"));
        assertFalse(A.hasChildWithValue("M"));

    }

    @Test
    void hasDescendantWithValue() {
        A.setChild(B);
        B.setChild(O);
        O.setChild(M);
        assertTrue(A.hasDescendantWithValue("B"));
        assertTrue(A.hasDescendantWithValue("M"));
    }

    @Test
    void toStringFormat() {
        assertEquals(A.toStringFormat(), "InnovatorMartian (String:A)");
        assertNotEquals(A.toStringFormat(), "InnovatorMartian (String:B)");
        assertNotEquals(A.toStringFormat(), "InnovatorMartian (Double:A)");
        assertNotEquals(A.toStringFormat(), "ConservatorMartian (String:A)");
        assertNotEquals(A.toStringFormat(), "   InnovatorMartian (String:A)");
        assertNotEquals(A.toStringFormat(), "InnovatorMartian (String: A)");
        assertEquals(K.toStringFormat(), "InnovatorMartian (Double:30.5)");
    }

    @Test
    void resultString() {
        A.setChild(B);
        assertEquals(A.resultString(0), "InnovatorMartian (String:A)" + System.lineSeparator() + "    InnovatorMartian (String:B)");
    }
}