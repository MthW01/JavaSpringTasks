package SkillBoxTasks.Task1Edited.contact;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private List<SkillBoxTasks.Task1Edited.contact.Contact> contacts = new ArrayList<>();

    public void addContact(SkillBoxTasks.Task1Edited.contact.Contact contact) {
        contacts.add(contact);
    }

    public void removeContactByEmail(String email) {
        contacts.removeIf(contact -> contact.getEmail().equalsIgnoreCase(email));
    }

    public List<SkillBoxTasks.Task1Edited.contact.Contact> getContacts() {
        return contacts;
    }

    public void printContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}