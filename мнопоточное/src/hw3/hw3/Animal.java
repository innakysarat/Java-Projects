package hw3;

/**
 * Класс для представления одного животного (лебедь/рак/щука)
 */
class Animal implements Runnable {

    int s, a;
    String name;
    Cart cart;
    // логическая переменная, нужная для вывода сообщения об остановке потока
    // равенство истине означает, что имя животного имеет женский род (у щуки - ж.р.)
    boolean feminine = false;

    /**
     * Конструктор класса животного
     *
     * @param s     индивидуальный коэффициент животного (участвует в формуле для передвижения телеги)
     * @param a     индивидульаный угол животного (участвует в формуле для передвижения телеги)
     * @param name  имя животного: лебедь, рак, щука
     * @param point телега
     */
    Animal(int s, int a, String name, Cart point) {
        this.name = name;
        this.s = s;
        this.a = a;
        this.cart = point;
        if (name.startsWith("Щ")) {
            this.feminine = true;
        }
    }

    /**
     * Метод реализует логику изменения положения телеги и засыпание потока после сдвига
     */
    public void run() {
        while (!Thread.interrupted()) {
            try {
                cart.change(this.name, this.s, this.a);
                Thread.sleep(1000 + (int) (Math.random() * 4000));
            } catch (InterruptedException e) {
                if (this.feminine)
                    System.out.println(name + " была остановлена");
                else System.out.println(name + " был остановлен");
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Метод печатает начальную информацию о животных: индивидуальный коэффициент, угол и имя
     *
     * @return строка с информацией о животном
     */
    @Override
    public String toString() {
        return this.name + ": s = " + this.s + ", угол = " + this.a;
    }
}

