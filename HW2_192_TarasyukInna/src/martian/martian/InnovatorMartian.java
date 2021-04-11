package martian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class InnovatorMartian<T> implements Martian<T> {
    private T value;
    private InnovatorMartian<T> parent;
    private ArrayList<InnovatorMartian<T>> children;

    public InnovatorMartian(T value) {
        setValue(value);

    }

    public T getValue() {
        return this.value;
    }

    public InnovatorMartian<T> getParent() {
        return this.parent;
    }

    public Collection<InnovatorMartian<T>> getChildren() {
        if (this.children != null)
            return new ArrayList<InnovatorMartian<T>>(this.children);
        else return null;
    }

    /**
     * @param child ребёнок, которого нужно добавить
     * @return истину, если ребёнок был добавлен, иначе ложь
     */
    public boolean setChild(InnovatorMartian<T> child) {
        if (conditionChild(child)) {
            if (this.children == null)
                this.children = new ArrayList<>();
            /*if (child != null && child == this.parent)
                return false;*/
            if (child != null) {
                this.children.add(child);
                child.parent = this;
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для удаления ребёнка
     *
     * @param child ребёнок, которого нужно удалить
     * @return истину, если ребёнок был удален, иначе ложь
     */
    public boolean deleteChild(InnovatorMartian<T> child) {
        if (this.children == null) return false;
        if (this.children.contains(child)) {
            this.children.remove(child);
            child.parent = null;
            return true;
        } else return false;
    }


    /**
     * @param someone новый родитель
     * @return истину, если можно установить родителя, иначе ложь
     */
    public boolean condition(Martian<T> someone) {
        if (someone == this) {
            return false;
        } else if (this.getChildren() == null) {
            return true;
        } else if (this.getDescendants() == null) {
            return true;
        } else if (this.getChildren() != someone) {
            Iterator i = this.getChildren().iterator();
            InnovatorMartian m;
            while (i.hasNext()) {
                m = (InnovatorMartian) i.next();
                if (m.equals(someone))
                    return false;
            }
        }
        return !(this.getDescendants().stream().anyMatch((descendant) -> {
            return descendant.equals(someone);
        }));
    }

    /**
     * @param someone ребенок, которого нужно добавить
     * @return истину, если добавление ребёнка возможно по законам марсиан
     */
    public boolean conditionChild(Martian<T> someone) {
        if (someone == this) {
            return false;
        } else if (someone.getChildren() == null) {
            return true;
        } else if (someone.getDescendants() == null) {
            return true;
        } else if (someone.getChildren() != null) {
            Iterator i = someone.getChildren().iterator();
            InnovatorMartian m;
            while (i.hasNext()) {
                m = (InnovatorMartian) i.next();
                if (m.equals(this))
                    return false;
            }
        }
        return !(someone.getDescendants().stream().anyMatch((descendant) -> {
            return descendant.equals(this);
        }));
    }

    /**
     * @param valueNew значение кода генетического
     * @return истину, если значение кода было установлено
     */
    public boolean setValue(T valueNew) {
        this.value = valueNew;
        return true;
    }

    /**
     * @param parentNew новый родитель ребёнка
     * @return истину, если получилось установить нового родителя ребёнку
     */
    public boolean setParent(InnovatorMartian<T> parentNew) {
        if (condition(parentNew)) {
            if (this.parent != null)
                this.parent.deleteChild(this);
            parentNew.setChild(this);
            return true;
        } else return false;
    }

    public boolean changeChildren(Collection<InnovatorMartian<T>> childrenNew) {

        if (this.children != null) {
            for (InnovatorMartian<T> tInnovatorMartian : this.children) {
                tInnovatorMartian.parent = null;
            }
            this.children.clear();
        }
        if (childrenNew == null) {
            childrenNew = new ArrayList<>();
        } else {
            for (InnovatorMartian<T> child : childrenNew) {
                child.setParent(this);
            }
        }
        return true;
    }
}
