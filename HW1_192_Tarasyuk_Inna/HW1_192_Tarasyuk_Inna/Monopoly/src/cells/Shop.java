/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */
package cells;

import game.Player;

public class Shop extends Cells {
    int n, k;
    double compensationCoeff, improvementCoeff;
    //переменная показывает, был ли куплен магазин(в случае, если нет, значение = 0),
    // а если да, то кем именно был куплен(если игроком, то значение = 1, иначе = 2)
    public int isBoughtByPlayer;
    int x, y;

    public Shop(int n_, int k_, double comp, double impr, int x_, int y_) {
        x = x_;
        y = y_;
        n = n_;
        k = k_;
        improvementCoeff = impr;
        compensationCoeff = comp;
    }
    /**
     * Метод покупки магазина
     *  @param player - игрок, попавший на клетку своего магазина
     *  @param response - ответ игрока, будет ли он покупать данный магазин
     * @return возвращает информацию о решении игрока
     */
    public String byeShop(Player player, String response) {
        // проверяем, согласился ли игрок покупать магазин
        if (response.equalsIgnoreCase("Yes")) {
            // в случае согласия проверяем, достаточно ли средств для покупки магазина
            if (player.getMoney() < getN())
                return player.getName() + String.format(" doesn't have financial resources to buy (<%d>, <%d>) shop.", x, y);
            else {
                // если средств достаточно, то снимаем стоимость магазина из суммы денег игрока
                player.setMoney(-n);
                // увеличиваем сумму расходов игрока
                player.setHousingPayments(n);
                // по имени игрока определяем, какое значение записывать в переменную
                // для дальнейшей возможности определить, кем куплен магазин - ботом или игроком
                if (!player.getName().equals("Bot"))
                    isBoughtByPlayer = 1;
                else isBoughtByPlayer = 2;
            }
            return String.format(player.getName() + " bought a shop (<%d>, <%d>) for %d", x, y, n);
        } else return String.format(player.getName() + " refused to buy shop (<%d>, <%d>).", x, y);

    }
    /**
     * Метод улучшения магазина
     *  @param player - игрок, попавший на клетку своего магазина
     *  @param response - ответ игрока, будет ли он улучшать магазин
     * @return возвращает информацию о решении игрока
     */
    public String improve(Player player, String response) {
        String result;
        if (response.equalsIgnoreCase("Yes")) {
            int moneyForImprovement = (int) Math.round(n * improvementCoeff);
            // снимаем стоимость улучшения магазина со счета игрока
            player.setMoney(-moneyForImprovement);
            n += Math.round(improvementCoeff * n);
            k += Math.round(compensationCoeff * k);
            // увеличиваем сумму расходов игрока
            player.setHousingPayments(moneyForImprovement);
            result = String.format(player.getName() + " upgraded the shop (<%d>, <%d>). Now store value is %d and compensation is %d. Improvement cost %d", x, y, n, k, moneyForImprovement);
        } else result = player.getName() + String.format(" refused upgrading the shop (<%d>, <%d>)", x, y);
        return result;
    }

    /**
     * Метод выплаты игроком компенсации за чужой магазин, в который попал данный игрок
     *  @param player - игрок, попавший на клетку чужого магазина
     * @return возвращает значение, указываюшее, имеет ли игра смысл(если true, данный игрок проиграл)
     */
    public boolean paymentForHousing(Player player) {
        // проверяем, достаточно ли средств у игрока, чтобы заплатить компенсацию
        // за попадание на клетку магазина другого игрока
        if (player.getMoney() < k)
            // если средств недостаточно, то игра заканчивается поражением данного игрока
            return true;
        else {
            // иначе снимаем со счета игрока компенсацию
            player.setMoney(-k);
            System.out.println(player.getName() + String.format(" payed %d for staying in the opponent shop (<%d, %d>)", k, x, y));
        }
        return false;
    }

    public int getN() {
        return n;
    }
    /**
     * Метод печатает информацию о том, на какую клетку попал игрок
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает текст с информацией о том, где находится игрок
     */
    public String information(Player player) {
        String str;
        if (isBoughtByPlayer == 0) {
            if(player.getName().equals("Bot")) str = String.format("Bot is in the shop cell (<%d>, <%d>). This shop has no owner.", x,y);
            else str = String.format(player.getName() + " is in the shop cell (<%d>, <%d>). This shop has no owner. Would you like to buy it for %d? Input ‘Yes’ if you agree or ‘No’ otherwise", x, y, n);
        } else if (isBoughtByPlayer == 1) {
            if (!player.getName().equals("Bot"))
                str = String.format(player.getName() + " is in your shop (<%d>, <%d>). Would you like to upgrade it for %d? Input ‘Yes’ if you agree or ‘No’ otherwise", x, y, (int) (n * improvementCoeff));
            else
                str = String.format("Bot is in the opponent shop (<%d>, <%d>). Payment for staying is %d", x, y, k);
        } else {
            if (player.getName().equals("Bot"))
                str = String.format("Bot is in his shop (<%d>, <%d>)", x, y);
            else
                str = String.format(player.getName() + " is in the opponent shop (<%d>, <%d>). You have to pay %d", x, y, k);
        }
        return str;
    }
    /**
     * Метод отображения клетки на поле
     *  @param player - игрок, попавший на данную клетку
     * @return возвращает отображение данной клетки на поле
     */
    @Override
    public String str(Player player) {
        if (player == null || isBoughtByPlayer == 0) return "S";
        else if (player.getName().equals("Bot")) {
            if (isBoughtByPlayer == 2) return "M";
            else return "O";
        } else if (!player.getName().equals("Bot")) {
            if (isBoughtByPlayer == 1) return "M";
            else return "O";
        }
        return "";
    }
}


