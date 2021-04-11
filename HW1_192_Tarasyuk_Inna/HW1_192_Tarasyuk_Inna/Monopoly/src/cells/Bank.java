/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package cells;

import game.Player;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Bank extends Cells {
    double creditCoeff;
    double debtCoeff;
    int x, y;

    public Bank(double debt, double cred, int x_, int y_) {
        x = x_;
        y = y_;
        debtCoeff = debt;
        creditCoeff = cred;
    }
    /**
     * Метод обрабатывает выдачу кредита для игрока, попавшего на клетку банка
     *  @param player - игрок, который хочет взять/выплатить кредит
     * @return возвращает значение, указывающее, есть ли смысл продолжать игру (если = true, игрок проиграл)
     */
    public boolean getCredit(Player player) {
        // проверяем, имеет ли игрок долг банку
        if (player.getDebt() > 0) {
            // money - целочисленное число, округляем по арифметическим правилам
            player.setMoney(-(int) Math.round(player.getDebt()));
            // если игрок не может выплатить долг, то есть сумма его денег<долга,
            // игра заканчивается его поражением
            if (player.getMoney() < 0) {
                System.out.println("The end of the game. " + player.getName() + " lost because can't pay the money to the bank.");
                return true;
            } else {
                player.setDebt(-(player.getDebt()));
                return false;
            }
            // если игрок не имеет долга, предлагаем взять кредит
        } else {
            // переменная summa показывает сумму, которую может
            // взять игрок в банке в качестве кредита
            int summa = (int) Math.round(player.getHousingPayments() * creditCoeff);
            if (summa == 0) {
                System.out.print("You can't take out a loan now because have bought/improved nothing yet.\n");
                return false;

            } else System.out.printf("\nYou can take out a loan of %d\n" +"Input the amount of money you want to take.", summa);
            // проверяем введенное число на корректность
            String num = isNumber();
            int number = parseInt(num);
            // обрабатываем ввод неверно введённого значения
            while (number < 0 || number > summa) {
                System.out.print("\nYou can't take credit for such an amount of money. Try again.");
                num = isNumber();
                number = parseInt(num);
            }
            // увеличиваем количество денег игрока на сумму кредита
            player.setMoney(number);
            int debt = (int) Math.round(debtCoeff * number);
            // устанавливаем долг игроку
            player.setDebt(debt);
            System.out.printf("\nYou took out a loan of %d", number);
            System.out.printf("\nNow you owe the bank %d\n", debt);
            return false;
        }
    }
    /**
     * Метод отображения клетки на поле
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает отображение данной клетки на поле
     */
    @Override
    public String str(Player player) {
        return "$";
    }
    /**
     * Метод обеспечивает корректный ввод игрока
     * @return возвращает ответ игрока
     */
    public String isNumber() {
        Scanner in = new Scanner(System.in);
        String num = in.nextLine();
        while (!checkString(num)) {
            System.out.println("You entered incorrect data. Try again.");
            num = in.nextLine();
        }
        return num;
    }
    /**
     * Метод печатает информацию о том, на какую клетку попал игрок
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает текст с информацией о том, где находится игрок
     */
    public String information(Player player) {
        if (player.getName().equals("Bot"))
            return String.format("Bot is in the bank office (<%d, %d>).", x, y);
        else {
            if (player.getDebt() > 0)
                return String.format(player.getName() + " is in the bank office (<%d, %d>). You have to pay the debt.", x, y);
            else
                return String.format(player.getName() + " is in the bank office (<%d, %d>). Would you like to get a credit? Input 'Yes' to get or ’No’", x, y);
        }
    }
    /**
     * Метод проверяет корректность ввода ответа игрока
     *  @param string - ответ игрока
     * @return возвращает значение, был ли ввод игрока корректным
     */
    public boolean checkString(String string) {
        if (string == null) return false;
        return string.matches("^-?\\d+$");
    }
}
