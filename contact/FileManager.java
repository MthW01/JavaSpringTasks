package SkillBoxTasks.Task1.contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<Contact> loadContacts(String filePath) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        }
        return contacts;
    }

    public void saveContacts(String filePath, List<Contact> contacts) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                bw.write(String.format("%s;%s;%s%n", contact.getFullName(), contact.getPhoneNumber(), contact.getEmail()));
            }
        }
    }
}