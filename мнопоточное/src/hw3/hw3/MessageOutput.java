package hw3;

/**
 * Класс, необходимый для вывода сообщений раз в две секунды
 */
public class MessageOutput implements Runnable {

    Cart cart;

    MessageOutput(Cart point) {
        this.cart = point;
    }

    /**
     * Метод реализует логику вывода информации о передвижении телеги раз в две секунды
     */
    public void run() {
        while (!Thread.interrupted()) {
            try {
                cart.write();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
