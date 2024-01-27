import java.util.Scanner;
import java.util.InputMismatchException;

public class SimpleCalculator {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        double number1, number2;
        char operation;
        boolean keepCalculating = true;

        System.out.println("Welcome to the Calculator");

        while (keepCalculating) {
            try {
                System.out.print("Enter first number: ");
                number1 = scanner.nextDouble();

                System.out.print("Enter second number: ");
                number2 = scanner.nextDouble();

                System.out.print("Choose an operation (+, -, *, /): ");
                operation = scanner.next().charAt(0);

                double result = performOperation(number1, number2, operation);
                System.out.printf("Result: %.2f\n", result);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values.");
                scanner.next(); // to clear the scanner buffer
                continue;
            }

            System.out.print("Do you want to perform another calculation? (Y/N): ");
            char anotherCalc = scanner.next().charAt(0);
            if (anotherCalc != 'Y' && anotherCalc != 'y') {
                keepCalculating = false;
            }
        }

        System.out.println("Thank you for using Calculator!");
    }

    private static double performOperation(double number1, double number2, char operation) {
        switch (operation) {
            case '+':
                return number1 + number2;
            case '-':
                return number1 - number2;
            case '*':
                return number1 * number2;
            case '/':
                if (number2 == 0) {
                    System.out.println("Division by zero is not allowed.");
                    return 0;
                } else {
                    return number1 / number2;
                }
            default:
                System.out.println("Invalid operation.");
                return 0;
        }
    }
}
