package SkillBoxTasks.Task1Edited;

import SkillBoxTasks.Task1Edited.contact.Contact;
import SkillBoxTasks.Task1Edited.contact.FileManager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Contact contactManager = new Contact();
        FileManager fileManager = new FileManager();

        String filePath = "src/resources/default-contacts.txt";

        try {
            // Загружаем контакты из файла, если он существует
            List<Contact> loadedContacts = fileManager.loadContacts(filePath);
            for (Contact contact : loadedContacts) {
                contactManager.addContact(contact);
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки контактов: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("Введите команду (add, remove, list, exit): ");
            command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Введите контакт (Ф. И. О.; номер телефона; адрес электронной почты): ");
                    String input = scanner.nextLine();
                    String[] parts = input.split(";");
                    if (parts.length == 3) {
                        Contact contact = new Contact(parts[0], parts[1], parts[2]);
                        contactManager.addContact(contact);
                    } else {
                        System.out.println("Неправильный формат ввода.");
                    }
                    break;
                case "remove":
                    System.out.println("Введите адрес электронной почты для удаления: ");
                    String email = scanner.nextLine();
                    contactManager.removeContactByEmail(email);
                    break;
                case "list":
                    contactManager.printContacts();
                    break;
                case "exit":
                    try {
                        fileManager.saveContacts(filePath, contactManager.getContacts());
                    } catch (IOException e) {
                        System.out.println("Ошибка сохранения контактов: " + e.getMessage());
                    }
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }
}