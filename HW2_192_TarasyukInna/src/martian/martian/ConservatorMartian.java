package martian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ConservatorMartian<T> implements Martian<T> {
    final T value;
    final Martian<T> parent;
    final ArrayList<ConservatorMartian<T>> children;

    public ConservatorMartian(InnovatorMartian<T> innovator) {
        this(innovator, null);
    }

    public ConservatorMartian(InnovatorMartian<T> c, ConservatorMartian<T> parent) {
        this.parent = parent;
        this.value = c.getValue();
        this.children = new ArrayList<>();
        if (c.getChildren() != null) {
            for (InnovatorMartian<T> child : c.getChildren()) {
                ConservatorMartian<T> conservatorChild = new ConservatorMartian<>(child, this);
                this.children.add(conservatorChild);
            }
        }
    }

    public T getValue() {
        return this.value;
    }

    public Martian<T> getParent() {
        return this.parent;
    }

    public Collection<? extends Martian<T>> getChildren() {
        return Collections.unmodifiableCollection(children);
    }
}
