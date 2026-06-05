package ru.ave.contactsBook;

import ru.ave.contactsBook.exceptions.UserAlreadyExistException;
import ru.ave.contactsBook.model.Contact;
import ru.ave.contactsBook.services.ContactService;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    static void main() {

        final String menu = """
                «1»: Добавить контакт
                «2»: Удалить контакт
                «3»: Посмотреть все контакты
                «4»: Найти контакт по имени
                «5»: Посмотреть контакты по группе
                «0»: Выход
                """;

        final String errorMessage = "Некорректный ввод";

        boolean isNotExit = true;

        ContactService service = new ContactService();

        try (Scanner scan = new Scanner(System.in)) {

            while (isNotExit) {
                try {
                    System.out.println(menu);
                    switch (readInt(scan)) {
                        case 1:
                            System.out.print("Введите имя: ");
                            String name = readLine(scan);
                            System.out.print("Введите телефон: ");
                            String phone = readLine(scan);
                            System.out.print("Введите email: ");
                            String email = scan.nextLine();
                            System.out.print("Введите группу: ");
                            String group = scan.nextLine();
                            service.addContact(new Contact(name, phone, email, group));
                            break;
                        case 2:
                            System.out.print("Введите имя: ");
                            String nameDelete = readLine(scan);
                            System.out.print("Введите телефон: ");
                            String phoneDelete = readLine(scan);
                            service.deleteContactByName(nameDelete, phoneDelete);
                            break;
                        case 3:
                            service.printAllContact();
                            break;
                        case 4:
                            System.out.print("Введите имя: ");
                            service.printContactByName(readLine(scan));
                            break;
                        case 5:
                            System.out.print("Введите группу: ");
                            service.printContactByGroup(scan.nextLine());
                            break;
                        case 0:
                            isNotExit = false;
                            break;
                        default:
                            System.out.println(errorMessage);
                    }
                } catch (UserAlreadyExistException e) {
                    System.out.println("Контакт \"" + e.getMessage() + "\" уже добавлен");
                } catch (InputMismatchException e) {
                    System.out.println(errorMessage + ": \"" + e.getMessage() + "\"");
                } catch (NoSuchElementException e) {
                    System.out.println("Не найден контакт по: \"" + e.getMessage() + "\"");
                } catch (RuntimeException e) {
                    System.out.println(errorMessage + ": \"" + e.getMessage() + "\"");
                }
            }
        }
    }

    private static String readLine(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) throw new InputMismatchException("Поле не может быть пустым");
        else return input;
    }

    private static int readInt(Scanner scanner) {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(input);
        }
    }

}
