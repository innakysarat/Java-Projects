/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package cells;

import game.Player;

public class EmptyCell extends Cells {
    int x, y;

    public EmptyCell(int x_, int y_) {
        x = x_;
        y = y_;
    }
    /**
     * Метод печатает информацию о том, на какую клетку попал игрок
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает текст с информацией о том, где находится игрок
     */
    public String information(Player player) {
        return player.getName() + String.format(" is in EmptyCell (<%d, %d>). Just relax there.", x, y);
    }
    /**
     * Метод отображения клетки на поле
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает отображение данной клетки на поле
     */
    @Override
    public String str(Player player) {
        return "E";
    }
}
