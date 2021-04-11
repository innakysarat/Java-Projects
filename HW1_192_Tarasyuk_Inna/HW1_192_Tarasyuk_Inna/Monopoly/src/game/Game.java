/**
 * @author <a href="mailto:ivtarasyuk@edu.hse.ru"> Inna Tarasyuk</a>
 */

package game;

import cells.*;

import java.util.Scanner;

public class Game {
    static int width = 8;
    public static int height = 6;
    static Cells[] cells;
    static double debt;
    static double cred;
    static boolean gameOver;
    static Scanner in = new Scanner(System.in);
    static Scanner inn = new Scanner(System.in);
    public static Player player;
    static Player bot;
    /**
     * Метод, реализующий работу программы
     *  @param args -  аргументы командной строки
     */
    public static void main(String[] args) {
        String startOver;

       boolean correctInput = inputValidation(args[0], args[1], args[2]);

        if (correctInput) {
           height = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
            cells = new Cells[2 * (width + height) - 4];
            do {
                gameOver = false;
                emptyArray();
                System.out.println("Enter the name of player:");
                player = new Player(inn.nextLine(), Integer.parseInt(args[2]));
                bot = new Player("Bot", Integer.parseInt(args[2]));
                fillThePlayingField();
                int N = 0;
                int K;
                double comp, impr;
                int[] coord;
                for (int i = 0; i < 2 * (width + height) - 4; ++i) {
                    if (cells[i] == null) {
                        N = (int) ((Math.random() * 451) + 50);
                        K = (int) (Math.random() * (0.4 * N) + 0.5 * N);
                        comp = Math.random() + 0.1;
                        impr = Math.random() * 2 + 0.1;
                        coord = coordinates(i);
                        cells[i] = new Shop(N, K, comp, impr, coord[0], coord[1]);
                    }
                }
                displayField(bot);
                System.out.println();
                int priority = (int) (Math.random() * 2);
                if (priority == 0) {
                    startGame(player, bot);
                } else {
                    startGame(bot, player);
                }
                System.out.println("Would you like to start over? Input 'Yes' if you want or 'No' otherwise.");
                startOver = inn.nextLine();
            } while (startOver.equalsIgnoreCase("Yes"));
        } else System.out.println("Input is incorrect");
    }

    /**
     * Метод проверки корректности командной строки
     *  @param str1 - 1 аргумент командной строки(height)
     * @param str2 - 2 аргумент командной строки(width)
     * @param str3 - 3 аргумент командной строки(money)
     * @return возвращает ответ, корректны ли данные(true, если да, иначе false)
     */
    public static boolean inputValidation(String str1, String str2, String str3) {
        boolean isCorrect = checkString(str1);
        boolean isCorrect2 = checkString(str2);
        boolean isCorrect3 = checkString(str3);
        if (!isCorrect || !isCorrect2 || !isCorrect3)
            return false;
        else {
            int h = Integer.parseInt(str1);
            int w = Integer.parseInt(str2);
            int m = Integer.parseInt(str3);
            if (h >= 6 && h <= 30 && w >= 6 && w <= 30 && m >= 500 && m <= 15000)
                return true;
        }
        return false;
    }
    /**
     * Метод проверки корректности командной строки(являются ли аргументы числами)
     *  @param string - аргумент командной строки, который нужно проверить на принадлежность числу
     * @return возвращает ответ, корректен ли аргумент командной строки(true, если да, иначе false)
     */
    public static boolean checkString(String string) {
        if (string == null) return false;
        return string.matches("^-?\\d+$");
    }
    /**
     * Метод очищает поле после завершения партии
     */
    public static void emptyArray() {
        for (int i = 0; i < 2 * (width + height) - 4; i++) {
            cells[i] = null;
        }
    }
    /**
     * Метод запускает игру и выполняет поочередный ход игроков
     *  @param player1 - игрок, делающий первый ход
     *  @param player2 - игрок, совершающий второй ход
     */
    public static void startGame(Player player1, Player player2) {
        int i = 1;
        boolean nextMoveB;
        System.out.println("First move makes " + player1.getName());
        do {
            System.out.printf("Now " + player1.getName() + " moves. It's %d move.%n", i);
            game(player1);
            String nextMove;
            if (!player1.getName().equals("Bot")) {
                System.out.println("Do you want to go the next move? Input 'Yes' if you want or 'No' otherwise.");
                nextMove = inn.nextLine();
                nextMoveB = (nextMove.equalsIgnoreCase("yes"));
            } else nextMoveB = true;
            if (nextMoveB) {
                ++i;
                if (!gameOver) {
                    System.out.printf("Now " + player2.getName() + " moves. It's %d move.%n", i);
                    game(player2);
                    String nextMove1;
                    if (!player2.getName().equals("Bot")) {
                        System.out.println("Do you want to go the next move? Input 'Yes' if you want or 'No' otherwise.");
                        nextMove1 = inn.nextLine();
                        nextMoveB = (nextMove1.equalsIgnoreCase("yes"));
                    } else nextMoveB = true;
                    if (nextMoveB) {
                        ++i;
                    } else {
                        System.out.println("The game is over.");
                        gameOver = true;
                    }
                }
            } else {
                System.out.println("The game is over.");
                gameOver = true;
            }
        } while (!gameOver);
    }
    /**
     * Метод оттображает актуальное поле для игрока
     *  @param player - игрок, для которого должно быть выполнено отображение игрового поля
     */
    public static void displayField(Player player) {
        StringBuilder str = new StringBuilder();
        str.append("   ".repeat(Math.max(0, width - 2)));
        str.append("  ");
        for (int i = 0; i < width; ++i) {
            System.out.print("  " + i);
        }
        System.out.println();
        System.out.print(0);
        System.out.print(" " + cells[0].str(player));
        for (int i = 1; i < width; ++i) {
            System.out.print("  " + cells[i].str(player));
        }
        int k = 2 * (width + height) - 5;
        StringBuilder s = new StringBuilder("\r");
        int j = 1;
        for (int i = width; i < width + height - 1; ++i) {
            System.out.print("\n" + "\r" + j + " " + cells[k].str(player) + str + cells[i].str(player));
            ++j;
            --k;
        }
        s.append(height - 1);
        s.append(" ").append(cells[2 * width + height - 3].str(player)).append("  ");
        for (int i = 2 * width + height - 4; i > width + height - 2; --i) {
            s.append(cells[i].str(player)).append("  ");
        }
        s.append(cells[width + height - 2].str(player));
        System.out.print(s);
        System.out.println();
    }
    /**
     * Метод вычисляет координаты всех клеток для поля
     *  @param i - номер конкретной клетки в одномерном массиве клеток
     * @return возвращает координаты клетки (x,y)
     */
    public static int[] coordinates(int i) {
        int[] coordinates = new int[2];
        if (i < width) {
            coordinates[0] = i;
        } else if (i < width + height - 1 && i > width - 1) {
            coordinates[0] = width - 1;
            coordinates[1] = i % (width - 1);
        } else if (i < 2 * width + height - 2 && i > width + height - 2) {
            int k = i - width - height + 2;
            coordinates[0] = i - 2 * k - height + 1;
            coordinates[1] = height - 1;
        } else if (i < 2 * (width + height) - 4 && i > 2 * width + height - 3) {
            coordinates[1] = (2 * (width + height) - 4 - i);
        }
        return coordinates;
    }
    /**
     * Метод вычисляет пределы для номера конкретной клетки в одномерном массиве клеток
     *  @param j - линия поля
     * @return возвращает число = номер клетки в одномерном массиве всех клеток
     */
    public static int position(int j) {
        if (j == 0) return (int) (Math.random() * (width - 2) + 1);
        if (j == 1) return (int) (Math.random() * (height - 2) + width);
        if (j == 2) return (int) (Math.random() * (width - 2) + width + height - 1);
        if (j == 3) return (int) (Math.random() * (height - 2) + 2 * width + height - 2);
        return 0;
    }
    /**
     * Метод заполняет одномерный массив клеток клетками типа penalty и taxi
     *  @param penalty_ показывает, является ли клетка пенальти (если false, то клетка типа taxi)
     */
    public static void positionPenaltyAndTaxi(boolean penalty_) {
        int[] coord;
        coord = new int[2];
        double p = 0;
        if (penalty_) {
            p = ((int) (Math.random() * 10)) / 100.0 + 0.01;
            System.out.printf("penalty coefficient = %.2f%n", p);
        }
        int position, quantity;
        boolean check = true;
        for (int j = 0; j < 4; ++j) {
            // обработка случая, когда длина или ширина поля = 6,
            // и на одной линии нужно поставить 2 штрафные клетки или 2 такси
            // в таком случае предпочтение отдаётся штрафной клетке
            if ((width == 6 || height == 6) && (!penalty_)) {
                switch (j) {
                    case 0:
                        check = hasSpace(0, width);
                        break;
                    case 1:
                        check = hasSpace(width, width + height - 1);
                        break;
                    case 2:
                        check = hasSpace(width + height - 2, 2 * width + height - 3);
                        break;
                    case 3:
                        check = hasSpace(2 * width + height - 3, 2 * (width + height) - 4);
                        break;
                }
            }
            quantity = (int) (Math.random() * 2);
            if (!check) quantity = 0;
            for (int i = 0; i <= quantity; ++i) {
                do position = position(j); while (cells[position] != null);
                coord = coordinates(position);
                if (penalty_) {
                    cells[position] = new PenaltyCell(p, coord[0], coord[1]);

                } else {
                    cells[position] = new Taxi(coord[0], coord[1]);
                }
            }
        }
    }
    /**
     * Метод заполняет одномерный массив клетками разных типов
     */
    public static void fillThePlayingField() {
        cells[0] = new EmptyCell(0, 0);
        cells[width - 1] = new EmptyCell(width - 1, 0);
        cells[width + height - 2] = new EmptyCell(width - 1, height - 1);
        cells[2 * width + height - 3] = new EmptyCell(0, height - 1);
        debt = 1 + (int) (Math.random() * 3);
        cred = ((int) (Math.random() * 20)) / 100.0 + 0.002;
        System.out.printf("Credit coefficient = %.2f, debt coefficient = %.2f, ", cred, debt);
        createBanks((int) (Math.random() * (width - 2) + 1));
        createBanks((int) (Math.random() * (height - 2) + width));
        createBanks((int) (Math.random() * (width - 2) + width + height - 1));
        createBanks((int) (Math.random() * (height - 2) + 2 * width + height - 2));
        positionPenaltyAndTaxi(true);
        positionPenaltyAndTaxi(false);
    }
    /**
     * Метод заполняет массив всех клеток клетками банка
     *  @param position указывает на номер клетки банка в одномерном массиве клеток
     */
    public static void createBanks(int position) {
        int[] coord;
        coord = new int[2];
        coord = coordinates(position);
        cells[position] = new Bank(debt, cred, coord[0], coord[1]);
    }

    public static boolean hasSpace(int borderLeft, int borderRight) {
        int isTrue = 0;
        for (int i = borderLeft; i < borderRight; ++i) {
            if (cells[i] == null)
                ++isTrue;
        }
        return isTrue != 1;
    }
    /**
     * Метод реализует ход одного конкретного игрока
     *  @param player - игрок, совершающий ход
     * @return указывает, есть ли смысл продолжать игру(проиграл ли игрок при очередном ходе)
     */
    public static boolean game(Player player) {
        if (!player.getName().equals("Bot")) {
            displayField(player);
        }
        String typeOfCell;
        // бросаем игральные кости
        int dieResult = tossADie();
        // перемещаем игрока на dieResult клеток вперед
        player.setPosition(dieResult);
        int pos = player.getPosition();
        // определяем тип клетки
        typeOfCell = cells[pos].str(player);
        if (typeOfCell.equals("T"))
            pos = taxiCell(typeOfCell, pos, player);
        typeOfCell = cells[pos].str(player);
        switch (typeOfCell) {
            case "S":
                gameOver = shopCell(pos, player);
                break;
            case "$":
                gameOver = bank(pos, player);
                break;
            case "E":
                System.out.println(((EmptyCell) cells[pos]).information(player));
                break;
            case "%":
                gameOver = ((PenaltyCell) cells[pos]).penaltyPayment(player);
                break;
        }
        System.out.println(player.info() + "\n\n");
        return gameOver;
    }
    /**
     * Метод реализует ход одного конкретного игрока, если он попал на клетку банка
     * @param pos - позиция игрока
     *  @param player - игрок, совершающий ход
     * @return указывает, есть ли смысл продолжать игру(проиграл ли игрок при очередном ходе)
     */
    public static boolean bank(int pos, Player player) {
        String answer;
        // выводим информацию о банке
        System.out.println(((Bank) cells[pos]).information(player));
        if (!player.getName().equals("Bot")) {
            // определяем ответ игрока
            answer = answer(player);
            if (answer.equalsIgnoreCase("Yes")) {
                gameOver = ((Bank) cells[pos]).getCredit(player);
            } else {
                if (answer.equalsIgnoreCase("No")) {
                    if (player.getDebt() > 0) player.setMoney(-(int) Math.round(player.getDebt()));
                    if (player.getMoney() < 0) gameOver = true;
                    else
                        System.out.println(player.getName() + " refused to get a credit.");
                }
            }
        } else System.out.println("Bot can't use bank services.");
        // логическая переменная указывает, не проиграл ли кто-либо из игроков
        return gameOver;
    }
    /**
     * Метод реализует ход одного конкретного игрока, если он попал на клетку магазина
     * @param pos - позиция игрока
     *  @param player - игрок, совершающий ход
     * @return указывает, есть ли смысл продолжать игру(проиграл ли игрок при очередном ходе)
     */
    public static boolean shopCell(int pos, Player player) {
        String answer;
        // отображаем информацию о магазине
        System.out.println(((Shop) cells[pos]).information(player));
        // проверяем, куплен ли магазин
        if (((Shop) cells[pos]).isBoughtByPlayer == 0) {
            answer = answer(player);
            System.out.println(((Shop) cells[pos]).byeShop(player, answer));
            // если магазин куплен игроком
        } else if (((Shop) cells[pos]).isBoughtByPlayer == 1) {
            // если на клетку магазина попал сам игрок, предлагаем улучшить магазин
            if (!player.getName().equals("Bot")) {
                answer = answer(player);
                System.out.println(((Shop) cells[pos]).improve(player, answer));
                // инчае на клетку магазина попал бот, за что он платит деньги
            } else gameOver = ((Shop) cells[pos]).paymentForHousing(player);
            // если магазин куплен ботом
        } else if (((Shop) cells[pos]).isBoughtByPlayer == 2) {
            // и на клетку магазина попал сам бот, то предлагаем улучшить магазин
            if (player.getName().equals("Bot")) {
                answer = answer(player);
                System.out.println(((Shop) cells[pos]).improve(player, answer));
            } else
                // иначе игрок выплачивает компенсацию боту
                gameOver = ((Shop) cells[pos]).paymentForHousing(player);
        }
        // логическая переменная указывает, не проиграл ли кто-либо из игроков
        return gameOver;
    }
    /**
     * Метод реализует ход одного конкретного игрока, если он попал на клетку такси
     * @param typeOfCell - тип клетки, на которую попал игрок после такси
     * @param pos - позиция игрока
     *  @param player - игрок, совершающий ход
     * @return позицию, на которую попал игрок после такси
     */
    public static int taxiCell(String typeOfCell, int pos, Player player) {
        while (typeOfCell.equals("T")) {
            // генерируется taxiDistance
            int position = ((int) (Math.random() * 3 + 3) % (2 * (width + height) - 4));
            System.out.println(((Taxi) cells[pos]).information(player, position));
            // игрок перемещается на position клеток вперёд
            player.setPosition(position);
            pos = player.getPosition();
            // определяем тип клетки после перемещения
            typeOfCell = cells[pos].str(player);
        }
        return pos;
    }
    /**
     * Метод реализует подбрасывание 2 игральных костей
     * @return возвращает сумму, выпавшую на двух игральных костях
     */
    public static int tossADie() {
        int die1 = (int) (Math.random() * 6) + 1;
        int die2 = (int) (Math.random() * 6) + 1;
        return (die1 + die2);
    }
    /**
     * Метод обрабатывает корректность ввода ответа игрока на тех типах клеток, что требуют ответа
     * @param player - игрок, попавший на клетку (банка или магазина)
     * @return ответ игрока
     */
    public static String answer(Player player) {
        String response;
        int responseBot;
        int countMistakes = 0;
        if (!"Bot".equals(player.getName())) {
            do {
                if (countMistakes > 0) {
                    System.out.println(player.getName() + ", you entered incorrect data. Try again.");
                }
                response = in.next();
                countMistakes++;
            } while (!(response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("No")));
        } else {
            responseBot = (int) (Math.random() * 2);
            if (responseBot != 0) {
                response = "No";
            } else {
                response = "Yes";
            }
        }
        return response;
    }

}








