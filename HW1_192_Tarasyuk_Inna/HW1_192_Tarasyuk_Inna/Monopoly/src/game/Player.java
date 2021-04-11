/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package game;

public class Player {
    int money;
    int housingPayments;
    int debt;
    String name;
    int position;

    public Player(String name_, int money_) {
        name = name_;
        money = money_;
        position = 0;
    }
    /**
     * Метод устанавливает позицию игрока на поле
     *  @param position_ - позиция игрока на поле
     */
    public void setPosition(int position_) {
        position += position_;
        position = position % (2 * (Game.width + Game.height) - 4);
    }
    /**
     * Метод считывания позиции игрока на поле
     * @return возвращает позицию игрока
     */
    public int getPosition() {
        return position;
    }
    /**
     * Метод считывания имени игрока
     * @return возвращает имя игрока
     */
    public String getName() {
        return name;
    }
    /**
     * Метод добавляет деньги на счёт = долг игрока банку
     *  @param debt_ - количество денег = долг игрока
     */
    public void setDebt(int debt_) {
        debt += debt_;
    }
    /**
     * Метод считывания долга игрока банку
     * @return возвращает значение долга банку
     */
    public int getDebt() {
        return debt;
    }
    /**
     * Метод считывания суммы, потраченной игроком на магазины
     * @return возвращает сумму = количество денег, которые игрок потратил на покупку/улучшение магазинов
     */
    public int getHousingPayments() {
        return housingPayments;
    }
    /**
     * Метод добавляет деньги на счёт = сумме денег, которые игрок потратил на покупку/улучшение магазинов
     *  @param payments - количество денег, которые нужно добавить на этот счёт игрока
     */
    public void setHousingPayments(int payments) {
        housingPayments += payments;
    }
    /**
     * Метод считывания количества денег игрока
     * @return возвращает сумму денег игрока
     */
    public int getMoney() {
        return money;
    }
    /**
     * Метод добавляет/снимает деньги со счёта игрока
     *  @param money_ - количество денег, которые нужно добавить на счёт игрока
     */
    public void setMoney(int money_) {
        money += money_;
    }
    /**
     * Метод печатает информацию о том, на какую клетку попал игрок
     * @return возвращает текст с информацией о том, где находится игрок
     */
    public String info() {
        return getName() + String.format(" has balance %d, debt to the bank %d. The total amount of purchases is %d", money, debt, housingPayments);
    }
}
