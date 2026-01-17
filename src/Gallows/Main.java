package Gallows;

import java.util.Scanner;

/**
 * Класс Main демонстрирует работу консольной игры "Виселица".
 * Содержит точку входа в программу.
 */
public class Main {
    /**
     * Точка входа в программу.
     * Организует меню запуска игры и основной игровой цикл.
     *
     * @param args аргументы командной строки (не исппользуются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Для начала игры нажмите s." +
                    "\nДля выхода из игры нажмите q");
            String s = scanner.nextLine();
            if (s.equals("s")) {
                GameLogic logic = new GameLogic();
                GameDraw gameDraw = new GameDraw();
                
                // Основной игровой цикл
                while(true) {
                    if (logic.getAttempt() == 0) {
                        System.out.println("Вы проиграли!. " +
                                "Закончились попытки");
                        break;
                    } else if (logic.checkWin()) {
                        System.out.println("Вы выиграли!");
                        break;
                    }
                    logic.input(scanner);
                    gameDraw.draw();
                }
            }
            else if (s.equals("q")) {
                break;
            }
        }

        System.out.println("Вы вышли из игры");
        scanner.close();
    }
}
