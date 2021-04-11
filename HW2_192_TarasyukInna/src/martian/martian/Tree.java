package martian;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Tree<T> {
    martian.Martian<T> root;

    public Tree(martian.Martian<T> martian) {
        this.root = martian;
    }

    public Tree(String info) {
        List<String> lines = Arrays.asList(info.split("\n"));
        if (info.startsWith("ConservatorMartian")) {
            var cons = (martian.ConservatorMartian) parse(lines, 1);
            this.root = (martian.Martian<T>) cons;

        } else {
            var inn = (martian.InnovatorMartian) parse(lines, 0);
            this.root = (martian.Martian<T>) inn;
        }
    }

    /**
     * @param value информация о марсианине
     * @return значение и тип кода
     */
    public static String[] parseMartian(String value) {
        String[] info = new String[2];
        value = value.trim();
        if (value.matches("^.*Martian \\(.+?:.+\\)$")) {
            value = (String) Arrays.stream(value.split(" ")).skip(1L).collect(Collectors.joining(" "));
            value = value.trim().substring(1, value.length() - 1);
            String valueType = value.split(":")[0];
            String val = value.split(":")[1];
            info[0] = valueType;
            info[1] = val;
            return info;
        } else {
            throw new IllegalArgumentException("Incorrect format to deserialize Martian");
        }
    }

    /**
     * @param information
     * @param isCons новатор, если 0, иначе косерватор
     * @return корень марсианской семьи
     */
    public Martian parse(List<String> information, int isCons) {
        Iterator iterator = information.iterator();
        String oneMartian = (String) iterator.next();
        String[] info = parseMartian(oneMartian);

        martian.InnovatorMartian root = new martian.InnovatorMartian(info[1]);
        Stack<InnovatorMartian> currentStack = new Stack<>() {
            {
                this.push(root);
            }
        };

        while (iterator.hasNext()) {
            String cur = (String) iterator.next();
            int depth = depth(cur) / 4;
            String[] inform = parseMartian(cur);

            while (currentStack.size() != depth) {
                currentStack.pop();
            }
            martian.InnovatorMartian current;
            switch (info[0]) {
                case "Integer":
                    current = new martian.InnovatorMartian(Integer.parseInt(inform[1]));
                    break;
                case "Double":
                    current = new martian.InnovatorMartian(Double.parseDouble(inform[1]));
                    break;
                case "String":
                    if (info[1].length() > 256) {
                        throw new IllegalArgumentException("Max size of code should be less than 256 symbols");
                    } else {
                        current = new martian.InnovatorMartian(inform[1]);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect type of SimpleObjectClassName");
            }
            current.setParent((martian.InnovatorMartian) currentStack.peek());
            currentStack.push(current);
        }
        if (isCons == 1) return new martian.ConservatorMartian(root);
        else return (martian.Martian) root;
    }

    static int depth(String line) {
        int depth;
        depth = 0;
        while (line.charAt(depth) == ' ') {
            depth++;
        }
        return depth;
    }

    public String serialize() {
        return this.root.resultString(0);
    }
}
