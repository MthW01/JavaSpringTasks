package SkillBoxTasks.Task1Edited.contact;

import SkillBoxTasks.Task1Edited.contact.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<SkillBoxTasks.Task1Edited.contact.Contact> loadContacts(String filePath) throws IOException {
        List<SkillBoxTasks.Task1Edited.contact.Contact> contacts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    contacts.add(new SkillBoxTasks.Task1Edited.contact.Contact(parts[0], parts[1], parts[2]));
                }
            }
        }
        return contacts;
    }

    public void saveContacts(String filePath, List<SkillBoxTasks.Task1Edited.contact.Contact> contacts) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                bw.write(String.format("%s;%s;%s%n", contact.getFullName(), contact.getPhoneNumber(), contact.getEmail()));
            }
        }
    }
}