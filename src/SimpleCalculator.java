import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        System.out.println("/-----------------------------------------------------\\");
        System.out.println("| This is super-puper Calculator by Victor Pichuzhkin |");
        System.out.println("| Inspired by Skillfactory course Android task 2.7.1  |");
        System.out.println("\\-----------------------------------------------------/");
        interactWithUser();
        System.out.println("The program will now exit");

    }

    //количество попыток ввода операндов. объявляется как final
    // т.к. эту переменную потом никто не должен мочь переопределить
    // final - это примерно как val в Kotlin
    private static final int GLOBAL_ATTEMPT_COUNT = 5;

    //допустимые арифметические действия
    private static final String ARITHMETIC_ACTIONS = "+-*/";

    // сканер
    private static final Scanner userInputScanner = new Scanner(System.in);

    // запросить числовой операнд, при этом ограничиваем количество попыток, за которые его нужно ввести
    public static int getIntOperand(Scanner scanner, int attemptCount) {
        for (int i = 1; i <= attemptCount; i++) {

            try {
                // приглашаем ввести. обратите внимание print() по сравнению с println() не вставляет возврат каретки и перевод строки
                // в println последние две буквы (ln -> line) имеют значение "и перейди на новую строку".
                // Это удобно для сообщений, но некрасиво для "приглашений ввести данные"
                // Поэтому я избрал print() вместо println()
                System.out.print("Please enter operand (note that only integers are currently supported), e.g. -128: ");
                // считываем, выходим из цикла и возвращаем результат
                // однако может еще случиться и эксепшен!
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println(i == attemptCount ? "No more input attempts" : "Invalid input. Please re-enter");

            } finally {
                //без этой штуки не работает корректно - ввод зацикливается,
                // т.е. после первого "плохого" ввода начинает вылезать запрос повторного - но уже по факту никакого ввода не ждет
                userInputScanner.nextLine();
                // разобрать бы, почему так...
            }
        }
        // без строчки ниже компилятор считает, что return может и не случиться (технически, он наверно прав)
        // см. https://stackoverflow.com/questions/21799367/missing-return-statement-in-for-loop
        // думаю, менторы объяснят как-нибудь, не костыль ли это. А точнее, как избавиться от таких костылей :)
        throw new IllegalArgumentException("This really should not happen.");
    }

    // запросить оператор
    public static char getMathAction(Scanner scanner, int attemptCount) {
        for (int i = 1; i <= attemptCount; i++) {

            try {
                System.out.print("Please enter arithmetic operation (basic 4 well-known), e.g. *: ");
                char scanResult = scanner.next().charAt(0);
                // наверно, самый простой способ проверить на Java, есть ли символ в строке. Хотя выглядит жутковато.
                if (ARITHMETIC_ACTIONS.indexOf(scanResult) == -1) {
                    //если введено что-то не то - не скрываем это
                    throw new IllegalArgumentException();
                }
                return scanResult;
            } catch (Exception e) {
                System.out.println(i == attemptCount ? "No more input attempts" : "Invalid input. Please re-enter");

            } finally {
                userInputScanner.nextLine();
            }
        }
        throw new IllegalArgumentException("This really should not happen.");
    }

    private static int evaluateResult(int operand1, int operand2, char operation) {

            switch (operation) {
                //по факту return делает неявный break
                //так что это довольно редкий случай, когда в switch МОЖНО обойтись без break
                //или нет? Ментор, please help :)
                case '+':
                    return operand1 + operand2;
                case '-':
                    return operand1 - operand2;
                case '*':
                    return operand1 * operand2;
                case '/':
                    return operand1 / operand2;
            }

        throw new IllegalArgumentException("This really should not happen.");
    }

    //взаимодействуем с пользователем
    private static void interactWithUser() {

        // переменная для хранения первого операнда
        int operand1 = getIntOperand(userInputScanner, GLOBAL_ATTEMPT_COUNT);

        // переменная для хранения действия
        char operation = getMathAction(userInputScanner, GLOBAL_ATTEMPT_COUNT);

        // переменная для хранения второго операнда
        int operand2 = getIntOperand(userInputScanner, GLOBAL_ATTEMPT_COUNT);

        System.out.println("Input: " + operand1 + " " + operation + " " + operand2 + ";");
        try {
            System.out.println("Result: " + evaluateResult(operand1, operand2, operation));
        } catch (ArithmeticException e) {
            System.out.println( "Zero should not be the divisor, padavan!");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Well, something went wrong. But I really don't know how");
        }

        System.out.println("End of calculations");


    }

}
