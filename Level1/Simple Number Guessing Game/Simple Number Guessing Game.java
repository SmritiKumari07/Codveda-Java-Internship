import java.util.Random;
import java.util.Scanner;


public class GuessingGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        final int MAX_ATTEMPTS = 7;
        final int MIN = 1;
        final int MAX = 100;
        System.out.printf("Task 2 of level 1 accomplished by Smriti Kumari");
        int secret = random.nextInt(MAX - MIN + 1) + MIN;
        System.out.printf("Guess the number:", MIN, MAX, MAX_ATTEMPTS);

        int attempts = 0;
        boolean won = false;

        while (attempts < MAX_ATTEMPTS) {
            attempts++;
            System.out.printf("Attempt %d: Enter your guess: ", attempts);
            int guess;
            try {
                guess = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                attempts--;
                continue;
            }

            if (guess < MIN || guess > MAX) {
                System.out.printf("Please enter number between %d and %d.%n", MIN, MAX);
                attempts--;
                continue;
            }

            if (guess == secret) {
                System.out.printf("Congratulations! You guessed it in %d attempts.%n", attempts);
                won = true;
                break;
            } else if (guess < secret) {
                System.out.println("Too low.");
            } else {
                System.out.println("Too high.");
            }
        }

        if (!won) {
            System.out.printf("Sorry! You ran out of attempts.The number was %d.%n", secret);
        }
        sc.close();
    }
}
