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
            startGame(br);
        } else if (choice.equals("2")) {
            System.exit(0);
        }
    }
       