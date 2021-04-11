/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package cells;

import game.Player;

public class Taxi extends Cells {
    int x, y;
    /**
     * Метод печатает информацию о том, на какую клетку попал игрок
     *  @param player - игрок, попавший на данную клетку
     *  @param taxiDistance - количество клеток, на которое должен переместиться игрок
     * @return возвращает текст с информацией о том, где находится игрок
     */
    public String information(Player player, int taxiDistance) {
        return String.format(player.getName() + " is shifted forward by %d cells", taxiDistance);
    }

    public Taxi(int x_, int y_) {
        x = x_;
        y = y_;
    }
    /**
     * Метод отображения клетки на поле
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает отображение данной клетки на поле
     */
    @Override
    public String str(Player player) {
        return "T";
    }
}
