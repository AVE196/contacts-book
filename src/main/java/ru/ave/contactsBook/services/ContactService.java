package ru.ave.contactsBook.services;

import ru.ave.contactsBook.exceptions.UserAlreadyExistException;
import ru.ave.contactsBook.model.Contact;

import java.util.*;

public class ContactService {

    private Map<String, ArrayList<Contact>> contactsByGroup;
    private List<Contact> allContacts;
    private Set<Contact> uniqContacts;

    {
        contactsByGroup = new HashMap<>();
        allContacts = new LinkedList<>();
        uniqContacts = new HashSet<>();
    }

    public void addContact(Contact contact) {
        if (!uniqContacts.add(contact)) throw new UserAlreadyExistException(contact.getName() + " | " + contact.getPhone());
        else {
            allContacts.add(contact);
            if (contactsByGroup.containsKey(contact.getGroup())) {
                contactsByGroup.get(contact.getGroup()).add(contact);
            } else {
                contactsByGroup.put(contact.getGroup(), new ArrayList<>());
                contactsByGroup.get(contact.getGroup()).add(contact);
            }
        }
    }

    public void deleteContactByName(String name) {
        if (uniqContacts.isEmpty()) System.out.println("Книга контактов пуста");
        else {
            Contact contactForDelete = null;
            Iterator<Contact> iterator = uniqContacts.iterator();
            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getName().equalsIgnoreCase(name)) {
                    contactForDelete = contact;
                    break;
                }
            }
            if (contactForDelete == null) {
                throw new NoSuchElementException(name);
            } else {
                uniqContacts.remove(contactForDelete);
                allContacts.remove(contactForDelete);
                contactsByGroup.get(contactForDelete.getGroup()).remove(contactForDelete);
            }
        }
    }

    public void printAllContact() {
        if (allContacts.isEmpty()) System.out.println("Книга контактов пуста");
        else printAllFromIterator(allContacts.iterator());
    }

    public void printContactByGroup(String group) {
        if (uniqContacts.isEmpty()) System.out.println("Книга контактов пуста");
        else if (contactsByGroup.containsKey(group)) printAllFromIterator(contactsByGroup.get(group).iterator());
        else throw new NoSuchElementException("Группа: \"" + group + "\"");
    }

    public void printContactByName(String name) {
        if (uniqContacts.isEmpty()) {
            System.out.println("Книга контактов пуста");
        } else {
            Iterator<Contact> iterator = uniqContacts.iterator();
            Contact contactForPrint = null;
            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getName().equalsIgnoreCase(name)) {
                    contactForPrint = contact;
                    break;
                }
            }
            if (contactForPrint == null) {
                throw new NoSuchElementException("Имя: \"" + name + "\"");
            } else System.out.println(contactForPrint);
        }
    }

    private void printAllFromIterator (Iterator<Contact> iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
