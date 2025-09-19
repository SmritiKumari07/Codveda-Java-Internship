import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        System.out.println("Task 1 By Smriti Kumari");
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter first number: ");
        double a = sc.nextDouble();
        System.out.println("Enter operator (Choose any of them +, -, *, /): ");
        char op = sc.next().charAt(0);
        System.out.println("Enter second number: ");
        double b = sc.nextDouble();

        double result = 0;
        switch(op) {
            case '+': result = a + b;
            break;
            case '-': result = a - b;
            break;
            case '*': result = a * b;
            break;
            case '/': 
                if (b != 0) result = a / b;
                else System.out.println("Error: Division by zero!");
                break;
            default: 
                System.out.println("Invalid operator!");
        }

        System.out.println("Result of the given numbers: " + result);
        sc.close();
    }
}
