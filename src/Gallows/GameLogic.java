package Gallows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Класс GameLogic реализует основную логику игры "Виселица":
 * загрузку слов, выбор случайного слова, обработку ввода пользователя
 * и проверку условий выигрыша.
 */
public class GameLogic {

    private List<String> words = new ArrayList<>();
    private static int attempt = 5;

    private String keyword;
    private List<Character> letters = new ArrayList<>();
    private List<Character> showLetters = new ArrayList<>();
    private Set<Character> usedLetters = new HashSet<>();

    public GameLogic() {
        System.out.println("Отгадайте слово по буквам.");
        start();
    }

    /**
     * Инициализация и подготовка данных перед началом игры:
     * загрузка слов, выбор случайного слова,
     * формирование списка букв и маски слова.
     */
    private void start() {
        loadWords();
        chooseRandomWord();
        initLetters();
        initMask();
        printWord();
    }

    /**
     * Загружает список слов из текстового файла.
     * Каждая строка файла представляет отдельное слово.
     */
    private void loadWords() {
        final String PATH = "./src/Gallows/Words.txt";
        Path path = Paths.get(PATH);
        try {
            words = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }
    }

    /**
     * Выбирает случайное слово из загруженного списка
     * и сохраняет его в переменную keyword.
     */
    private void chooseRandomWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Список слов пуст");
        }
        Random random = new Random();
        keyword = words.get(random.nextInt(words.size()));
    }

    /**
     * Преобразует загаданное слово в список отдельных символов
     * для дальнейшей обработки в игре.
     */
    private void initLetters() {
        letters.clear();
        for (char c : keyword.toCharArray()) {
            letters.add(c);
        }
    }

    /**
     * Инициализирует маску слова:
     * первая и последняя буквы открыты,
     * остальные заменены символом '_'.
     */
    private void initMask() {
        showLetters.clear();
        for (int i = 0; i < letters.size(); i++) {
            if (i == 0 || i == letters.size() - 1) {
                showLetters.add(letters.get(i));
            } else {
                showLetters.add('_');
            }
        }
    }

    /**
     * Выводит текущее состояние отгадываемого слова на экран.
     */
    private void printWord() {
        for (char c : showLetters) {
            System.out.print(c + " / ");
        }
        System.out.println();
    }

    /**
     * Обрабатывает ввод пользователя:
     * проверяет корректность символа,
     * проверяет, вводилась ли буква ранее,
     * открывает букву или уменьшает количество попыток.
     *
     * @param scanner объект Scanner для чтения ввода пользователя
     */
    public void input(Scanner scanner) {
        System.out.print("Введите букву: ");
        String input = scanner.nextLine().trim();

        if (!isValidInput(input)) {
            System.out.println("Ошибка ввода. Введите одну маленькую букву кириллицы.");
            return;
        }

        char inputChar = input.charAt(0);

        if (usedLetters.contains(inputChar)) {
            System.out.println("Эта буква уже вводилась.");
            return;
        }

        usedLetters.add(inputChar);

        if (!openLetter(inputChar)) {
            attempt--;
            System.out.println("Неверно! Осталось попыток: " + attempt);
        }

        printWord();
    }

    /**
     * Открывает введённую букву в маске слова, если она присутствует.
     *
     * @param c буква, введённая пользователем
     * @return true — если буква найдена в слове
     *         false — если буква отсутствует
     */
    private boolean openLetter(char c) {
        boolean found = false;

        for (int i = 1; i < letters.size() - 1; i++) {
            if (letters.get(i) == c && showLetters.get(i) == '_') {
                showLetters.set(i, c);
                found = true;
            }
        }

        return found;
    }

    /**
     * Проверяет корректность ввода пользователя.
     * Разрешена только одна строчная буква кириллицы.
     *
     * @param input строка, введённая пользователем
     * @return true — если ввод корректный
     *         false — если ввод некорректный
     */
    private boolean isValidInput(String input) {
        return input.matches("[а-яё]");
    }

    /**
     * Возвращает текущее количество оставшихся попыток.
     *
     * @return количество оставшихся попыток
     */
    public static int getAttempt() {
        return attempt;
    }

    /**
     * Проверяет условие победы игрока.
     * Победа считается достигнутой, если все буквы слова открыты.
     *
     * @return true — если игрок выиграл
     *         false — если игрок проиграл
     */
    public boolean checkWin() {
        return letters.equals(showLetters);
    }
}
