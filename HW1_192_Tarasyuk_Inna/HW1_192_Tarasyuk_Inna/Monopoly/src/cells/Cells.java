/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package cells;

import game.Player;

public abstract class Cells {
    /**
     * Метод отображения клетки на поле
     *  @param player - игрок, попавший на клетку
     * @return возвращает отображение клетки на поле
     */
    public abstract String str(Player player);

}