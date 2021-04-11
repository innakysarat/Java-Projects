package martian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public interface Martian<T> {
    T getValue();

    Martian<T> getParent();

    Collection<? extends Martian<T>> getChildren();

    default Collection<? extends Martian<T>> getDescendants() {
        ArrayList<Martian<T>> des = new ArrayList<>();
        if (this.getChildren() == null) return null;
        else {
            Iterator i = this.getChildren().iterator();
            while (i.hasNext()) {
                Martian child = (Martian) i.next();
                des.add(child);
                if (child.getDescendants() != null) {
                    des.addAll(child.getDescendants());
                }
            }
            return des;
        }
    }

    default Martian<T> getRoot() {
        Martian<T> root;
        root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    default boolean hasChildWithValue(T value) {
        var i = this.getChildren().iterator();
        Martian child;
        boolean hasChild = false;
        while (i.hasNext()) {
            Martian<T> elem = (Martian<T>) i.next();
            child = (Martian) elem;
            if (value.equals(child.getValue())) {
                hasChild = true;
                break;
            }
        }
        return hasChild;
    }

    default boolean hasDescendantWithValue(T value) {
        Iterator<? extends Martian<T>> i = this.getDescendants().iterator();
        Martian desc;
        boolean hasDesc = false;
        while (i.hasNext()) {
            Martian<T> elem = (Martian<T>) i.next();
            desc = (Martian) elem;
            if (value.equals(desc.getValue())) {
                hasDesc = true;
                break;
            }
        }
        return hasDesc;
    }

    /**
     * @return строка со всей информацией об одном марсианине в нужном формате
     */
    default String toStringFormat() {
        if (this.getValue().toString().length() > 256) {
            throw new IllegalArgumentException("Can not serialize");
        } else
            return String.format("%s (%s:%s)", this.getClass().getSimpleName(), this.getValue().getClass().getSimpleName(), this.getValue());
    }

    /**
     * @param depth глубина (количество отступов)
     * @return строка с всей информацией  семье марсианина
     */
    default String resultString(int depth) {
        String skip = (new String(new char[4 * depth])).replace('\u0000', ' ');
        StringBuilder res = new StringBuilder(skip + this.toStringFormat());
        if (this.getChildren() != null) {
            for (Martian<T> martian : this.getChildren()) {
                Martian child = (Martian) martian;
                res.append(System.lineSeparator()).append(child.resultString(depth + 1));
            }
        }
        return res.toString();
    }
}

