package hw3;

class Program {
    public static void main(String[] args) {
        double x, y;
        if (args.length < 2) {
            x = 0;
            y = 0;
        }
        else if (checkString(args[0]) && checkString(args[1])) {
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
        } else {
            x = 0;
            y = 0;
        }
        var cart = new Cart(x, y);
        var swan = new Animal(1 + (int) (Math.random() * 9), 60, "Лебедь", cart);
        var crayfish = new Animal(1 + (int) (Math.random() * 9), 180, "Рак  ", cart);
        var pike = new Animal(1 + (int) (Math.random() * 9), 300, "Щука", cart);
        System.out.println(swan.toString());
        System.out.println(crayfish.toString());
        System.out.println(pike.toString());
        System.out.println("Животное\t|\tКоординаты\t|\tДлина\t|");
        Thread l = new Thread(swan);
        Thread r = new Thread(crayfish);
        Thread z = new Thread(pike);
        var sleep = new MessageOutput(cart);
        Thread thread_sleep = new Thread(sleep);
        l.start(); // поток лебедя
        r.start(); // поток рака
        z.start(); // поток щуки
        thread_sleep.start();
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.interrupt();
        r.interrupt();
        z.interrupt();
        thread_sleep.interrupt();
    }

    /**
     * Метод проверяет входные параметры на корректность
     *
     * @param str входная строка
     * @return логическое значение, равное истине, если входная строка соответствует условию, иначе ложь
     */
    public static boolean checkString(String str) {
        return str != null && str.matches("[0-9]+");
    }
}