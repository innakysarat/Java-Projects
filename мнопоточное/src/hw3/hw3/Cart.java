package hw3;

import java.util.ArrayList;

/**
 * Класс, представляющий телегу
 */
public class Cart {

    double x;
    double y;
    ArrayList<Double> lengths;
    ArrayList<Double> coordinates;
    String name;

    /**
     * Конструктор класс телеги, устанавливающий начальные значения координат телеги
     *
     * @param x_ координата x
     * @param y_ координата y
     */
    Cart(double x_, double y_) {
        x = x_;
        y = y_;
        // хранит длины, на которые была сдвинута телега
        lengths = new ArrayList<Double>();
        // хранит меняющиеся координаты телеги
        coordinates = new ArrayList<Double>();
    }

    /**
     * Метод меняет координаты телеги
     *
     * @param name имя животного, который толкнул телегу (лебедь/рак/щука)
     * @param s    индивидуальный коэффициент животного
     * @param a    индивидуальный угол, на который животное двигает телегу
     */
    public synchronized void change(String name, int s, int a) {
        this.name = name;
        x += s * Math.cos(Math.toRadians(a));
        y += s * Math.sin(Math.toRadians(a));
        coordinates.add(x);
        coordinates.add(y);
        if (coordinates.size() >= 4) {
            int size = coordinates.size();
            double y_old = coordinates.get(size - 3);
            double x_old = coordinates.get(size - 4);
            double y_new = coordinates.get(size - 1);
            double x_new = coordinates.get(size - 2);
            // вычисление длины сдвига телеги
            double length = (y_new - y_old) * (y_new - y_old) + (x_new - x_old) * (x_new - x_old);
            lengths.add(length);
        } else lengths.add(y * y + x * x);
        // печать актуальных изменений координат телеги
        // так как 2 секундный репорт, выполняющийся в методе write() может какие-либо передвижения телеги терять
        System.out.printf(name + "\t|\t{%.2f; %.2f}\t|\t%.2f\t|\n", x, y, lengths.get(lengths.size() - 1));
    }

    /**
     * Метод печатает информацию о движении телеги каждые две секунды
     */
    public synchronized String write() {
        String str = "";
        if (lengths.size() > 1) {
            System.out.printf("\nreport (2 seconds): " + name + " push the cart to {%.2f; %.2f}\n", x, y);
        }
        System.out.println(str);
        // для проверки в классе теста return, в целом он не нужен
        return str;
    }
}
