/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package cells;

import game.Player;

public class PenaltyCell extends Cells {
    double penaltyCoeff;
    int x, y;

    public PenaltyCell(double penalty, int x_, int y_) {
        penaltyCoeff = penalty;
        x = x_;
        y = y_;

    }
    /**
     * Метод снимает сумму денег со счета игрока, если он попал на штрафную клетку
     *  @param player - игрок, попавший на штрафную клетку
     * @return возвращает значение, указывающее, есть ли смысл продолжать игру (если = true, игрок проиграл)
     */
    public boolean penaltyPayment(Player player) {
        long penalty = Math.round(penaltyCoeff * player.getMoney());
        player.setMoney(-(int) penalty);
        if (player.getMoney() < 0) return true;
        else {
            System.out.printf(player.getName() + " is in a PenaltyCell (<%d, %d>). Payment for penalty is %d%n", x, y, penalty);
        }
        return false;
    }

    /**
     * Метод отображения клетки на поле
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает отображение данной клетки на поле
     */
    @Override
    public String str(Player player) {
        return "%";
    }
}
