import java.util.*;
import java.io.*;

public class RacingGame {

    static void showMenu(BufferedReader br) throws Exception {
        clear();
        System.out.println("===== ТЕРМІНАЛЬНІ ГОНКИ =====");
        System.out.println();
        System.out.println("1. Почати гру");
        System.out.println("2. Вихід");
        System.out.print("Ваш вибір: ");

        String choice = br.readLine();

        if (choice.equals("1")) {
            startGame();
        } else if (choice.equals("2")) {
            System.exit(0);
        }
    }

    static final int WIDTH = 40;
    static final int HEIGHT = 20;

    static final int ROAD_CENTER = WIDTH / 2;
    static final int ROAD_HALF_WIDTH = 8;

    static int playerX;
    static int speed;
    static int health;
    static int score;

    static boolean running = true;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            showMenu(br);
        }
    }
    static void startGame() throws Exception {
        resetGame();
        running = true;

        while (running) {
            handleInput();
            updateGame();
            draw();
            Thread.sleep(120);
        }
    }

    static void resetGame() {
        playerX = ROAD_CENTER;
        speed = 1;          
        health = 100;
        score = 0;
    }

    static void updateGame() {
        score += speed;
    }
     static void handleInput() throws Exception {
        if (System.in.available() > 0) {
            char c = Character.toUpperCase((char) System.in.read());

            switch (c) {
    case 'A':
        playerX--;
        break;
    case 'D':
        playerX++;
        break;
    case 'W':
        speed = Math.min(5, speed + 1);
        break;
    case 'S':
        speed = Math.max(1, speed - 1);
        break;
    case 'Q':
        running = false;
        break;
}

            int left = ROAD_CENTER - ROAD_HALF_WIDTH + 1;
            int right = ROAD_CENTER + ROAD_HALF_WIDTH - 1;
            playerX = Math.max(left, Math.min(right, playerX));
        }
    }

    static void draw() {
        clear();

        System.out.println("Швидкість: " + speed +
                "  Здоровʼя: " + health +
                "  Рахунок: " + score);
        System.out.println();

        int leftBorder = ROAD_CENTER - ROAD_HALF_WIDTH;
        int rightBorder = ROAD_CENTER + ROAD_HALF_WIDTH;

        for (int y = 0; y < HEIGHT; y++) {
            StringBuilder line = new StringBuilder();

            for (int x = 0; x < WIDTH; x++) {
                if (x == leftBorder || x == rightBorder) {
                    line.append("|");
                } else if (x == playerX && y == HEIGHT - 2) {
                    line.append("A");
                } else if (x > leftBorder && x < rightBorder) {
                    line.append(" ");
                } else {
                    line.append(".");
                }
            }
            System.out.println(line);
        }
    }

    static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
