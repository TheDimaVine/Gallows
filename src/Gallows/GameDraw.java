package Gallows;

import java.util.Arrays;

/**
 * Класс GameDraw отвечает за визуальное отображение состояния игры "Виселица".
 * На основе количества оставшихся попыток отображает текущее состояние виселицы.
 */
public class GameDraw {
    /**
     * Двумерный массив символов для хранения изображения виселицы
     */
    private char[][] visual = new char[5][10];

    public GameDraw() {}

    /**
     * Очищает массив визуализации, заполняя его пробелами.
     */
    private void clearVisual() {
        for (int i = 0; i < visual.length; i++) {
            Arrays.fill(visual[i], ' ');
        }
    }

    /**
     * Формирует изображение виселицы в зависимости от количества
     * оставшихся попыток в игре.
     */
    private void drawGallows() {
        clearVisual();

        if (GameLogic.getAttempt() <= 4) { // основание
            for (int j = 0; j <= 8; j++) {
                visual[4][j] = '*';
            }
        }

        if (GameLogic.getAttempt() <= 3) { // столб
            for (int i = 0; i <= 4; i++) {
                visual[i][4] = '*';
            }
        }

        if (GameLogic.getAttempt() <= 2) { // перекладина
            for (int j = 4; j <= 8; j++) {
                visual[0][j] = '*';
            }
        }

        if (GameLogic.getAttempt() <= 1) { // верёвка
            visual[1][8] = '*';
        }

        if (GameLogic.getAttempt() == 0) {
            visual[2][7] = '*';
            visual[2][9] = '*';
            visual[3][8] = '*';
        }
    }

    /**
     * Отображает текущее состояние виселицы в консоли.
     */
    public void draw() {
        drawGallows();

        for (char[] row : visual) {
            System.out.println(row);
        }
    }
}
