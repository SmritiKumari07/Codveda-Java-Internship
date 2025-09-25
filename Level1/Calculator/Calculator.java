import java.util.InputMismatchException;
import java.util.Scanner;

public class BasicCalculator {
    public static double add(double a, double b) { return a + b; }
    public static double subtract(double a, double b) { return a - b; }
    public static double multiply(double a, double b) { return a * b; }
    public static double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("Division by zero is not allowed.");
        return a / b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Task 1 of Level 1 accomplished by Smriti Kumari");
        System.out.println("=Basic Calculator =");
        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1) Add  2) Subtract  3) Multiply  4) Divide  5) Exit");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.nextLine();
                continue;
            }

            if (choice == 5) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            System.out.print("Enter first number: ");
            double a = readDouble(sc);
            System.out.print("Enter second number: ");
            double b = readDouble(sc);

            try {
                double result;
                switch (choice) {
                    case 1 -> result = add(a, b);
                    case 2 -> result = subtract(a, b);
                    case 3 -> result = multiply(a, b);
                    default -> result = divide(a, b);
                }
                System.out.println("Result: " + result);
            } catch (ArithmeticException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        sc.close();
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Invalid number. Try again: ");
                sc.nextLine();
            }
        }
    }
}
