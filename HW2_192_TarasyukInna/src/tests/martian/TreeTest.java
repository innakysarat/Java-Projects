package martian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    InnovatorMartian<String> A = new InnovatorMartian<>("A");
    InnovatorMartian<String> B = new InnovatorMartian<>("A");
    InnovatorMartian<String> C = new InnovatorMartian<>("A");
    Tree<String> tree1 = new Tree<String>("InnovatorMartian (Double:15.15)" + System.lineSeparator() +
            "    InnovatorMartian (Double:2.1)" + System.lineSeparator() +
            "        InnovatorMartian (Double:8.3)" + System.lineSeparator() +
            "            InnovatorMartian (Double:100.5)" + System.lineSeparator() +
            "        InnovatorMartian (Double:10.12)" + System.lineSeparator() +
            "    InnovatorMartian (Double:-550.02)");


    @Test
    void depth() {
        assertEquals(2, Tree.depth("         InnovatorMartian (String:Jared)") / 4);
        assertEquals(0, Tree.depth("InnovatorMartian (String:Jared)") / 4);
    }

    @Test
    void parseMartian() {
        Tree<String> tree = new Tree<String>(A);
        String str = "InnovatorMartian (Double:15.15)";
        assertEquals(tree.parseMartian(str)[0], "Double");
        assertEquals(tree.parseMartian(str)[1], "15.15");
    }

    @Test
    void serialize() {
        Martian martian = tree1.root;

        assertEquals(martian.getValue(), "15.15");
    }
}