package martian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConservatorMartianTest {

    InnovatorMartian<String> A = new InnovatorMartian<>("A");
    InnovatorMartian<String> B = new InnovatorMartian<>("B");
    InnovatorMartian<String> M = new InnovatorMartian<>("M");
    InnovatorMartian<String> Z = new InnovatorMartian<>("Z");
    InnovatorMartian<String> N = new InnovatorMartian<>("N");
    InnovatorMartian<Double> K = new InnovatorMartian<>(30.5);
    ConservatorMartian<String> D = new ConservatorMartian<>(A);
    ConservatorMartian<Double> C = new ConservatorMartian<>(K);

    @Test
    void getValue() {
        assertEquals(D.getValue(), "A");
        A.setValue("C");
        assertNotEquals(D.getValue(), "C");
        assertNotEquals(C.getValue(), "A");
    }

    @Test
    void getParent() {
        B.setParent(A);
        ConservatorMartian<String> V = new ConservatorMartian<>(B);
        assertNull(V.getParent());
    }

    @Test
    void getChildren() {
        B.setChild(Z);
        B.setChild(N);
        ConservatorMartian<String> V = new ConservatorMartian<>(B);
        var collection = V.getChildren();
        assertEquals(B, Z.getParent());
    }
}