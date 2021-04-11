package martian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovatorMartianTest {
    InnovatorMartian<String> A = new InnovatorMartian<>("A");
    InnovatorMartian<String> B = new InnovatorMartian<>("B");
    InnovatorMartian<String> C = new InnovatorMartian<>("C");
    InnovatorMartian<String> D = new InnovatorMartian<>("D");
    InnovatorMartian<String> F = new InnovatorMartian<>("F");
    InnovatorMartian<Double> K = new InnovatorMartian<>(30.5);
    InnovatorMartian<Integer> L = new InnovatorMartian<>(30);
    @Test
    void getValue() {
        A.setValue("C");
        assertEquals(A.getValue(), "C");
        assertNotEquals(A.getValue(), "A");
    }

    @Test
    void getParent() {

        A.setChild(B);
        B.setChild(C);
        assertEquals(A, B.getParent());
        assertNotEquals(A, C.getParent());
        assertNull(A.getParent());
    }

    @Test
    void getChildren() {

        A.setChild(B);
        A.setChild(C);
        var collection = A.getChildren();
        assertTrue(collection.contains(B));
        assertTrue(collection.contains(C));
        assertFalse(collection.contains(D));
    }

    @Test
    void setChild() {

        assertTrue(A.setChild(B));
        assertFalse(A.setChild(A));
        assertTrue(B.setChild(C));
        assertFalse(B.setChild(A));
        assertFalse(C.setChild(A));

    }

    @Test
    void deleteChild() {
        assertFalse(A.deleteChild(B));
        A.setChild(B);
        A.deleteChild(B);
        assertFalse(A.getChildren().contains(B));
        assertNull(B.getParent());
    }

    @Test
    void condition() {
        A.setParent(B);
        B.setParent(C);
        assertFalse(A.setParent(A));
        assertFalse(B.setParent(A));
        assertFalse(C.setParent(A));
    }

    @Test
    void conditionChild() {
        assertTrue(D.setChild(B));
        assertFalse(C.setChild(C));
        assertTrue(B.setChild(C));
        assertFalse(C.setChild(B));
        assertFalse(C.setChild(D));
    }

    @Test
    void setValue() {
        A.setValue("B");
        assertEquals("B", A.getValue());
        assertEquals(30, L.getValue());
    }

    @Test
    void setParent() {
        A.setParent(B);
        B.setParent(C);
        assertEquals(A.getParent(), B);
        assertEquals(B.getParent(), C);
        assertNotEquals(A.getParent(), C);
        assertNull(D.getParent());
    }

    @Test
    void changeChildren() {
        A.setChild(B);
        D.setChild(C);
        D.setChild(F);
        var collection = D.getChildren();
        A.changeChildren(collection);
        assertEquals(A, C.getParent());
        assertNotEquals(A, B.getParent());
        assertNull(B.getParent());
    }
}