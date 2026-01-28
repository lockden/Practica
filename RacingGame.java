import java.io.*;
import java.util.*;

public class TerminalRacing {

    static final int WIDTH = 40;
    static final int HEIGHT = 20;

    static final int ROAD_CENTER = WIDTH / 2;
    static final int ROAD_HALF_WIDTH = 8;

    static int playerX;
    static int speed;
    static int health;
    static int score;

    static boolean running = true;

    static List<int[]> traffic = new ArrayList<>();
    static Random random = new Random();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            showMenu(br);
        }
    }

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
        traffic.clear();
    }

    static void updateGame() {
        score += speed;

        spawnTraffic();
        moveTraffic();
        checkCollision();

        if (health <= 0) {
            gameOver();
            running = false;
        }
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

   static void spawnTraffic() {
    if (random.nextInt(10) < 2) {

        int left = ROAD_CENTER - ROAD_HALF_WIDTH + 1;
        int right = ROAD_CENTER + ROAD_HALF_WIDTH - 1;

        int x = random.nextInt(right - left + 1) + left;

        traffic.add(new int[]{x, 0});
    }
}


    static void moveTraffic() {
        Iterator<int[]> it = traffic.iterator();
        while (it.hasNext()) {
            int[] car = it.next();
            car[1] += speed;
            if (car[1] > HEIGHT) it.remove();
        }
    }

    static void checkCollision() {
        for (int[] car : traffic) {
            if (car[1] == HEIGHT - 2 && car[0] == playerX) {
                health -= 25;
            }
        }
    }

    static void draw() {
        clear();

        System.out.println(
                "Швидкість: " + speed +
                " | Здоровʼя: " + health +
                " | Рахунок: " + score
        );
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
                } else if (hasTraffic(x, y)) {
                    line.append("X");
                } else if (x > leftBorder && x < rightBorder) {
                    line.append(" ");
                } else {
                    line.append(".");
                }
            }
            System.out.println(line);
        }
    }

    static boolean hasTraffic(int x, int y) {
        for (int[] car : traffic) {
            if (car[0] == x && car[1] == y) return true;
        }
        return false;
    }

    static void gameOver() {
        clear();
        System.out.println(" GAME OVER ");
        System.out.println("Авто знищено!");
        System.out.println("Ваш рахунок: " + score);
        System.out.println();
        System.out.println("Повернення в меню...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
    }

    static void clear() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
}
