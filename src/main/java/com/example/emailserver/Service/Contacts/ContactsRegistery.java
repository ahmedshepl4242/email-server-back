package com.example.emailserver.Service.Contacts;

import com.example.emailserver.Service.cache.CacheInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactsRegistery {
    private HashMap<String, Contact> contacts = new HashMap<>();

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> c = new ArrayList<>();
        for (Contact cc : contacts.values()) {
            c.add(cc);
        }
        return c;
    }



    public HashMap<String, Contact> getHash() {
        return this.contacts;
    }

    public void addContact(String name, String emails) {
        System.out.println(name+"  ");
        contacts.put(emails, new Contact(name, emails));
    }

    public void deleteContact(String email) {
        contacts.remove(email);

    }

    public Contact getContact(String id) {
        return contacts.get(id);
    }

    public void editContact( String name, String emails) {

        System.out.println(name+" ");
        try {
            Contact editcont = contacts.get(emails);
            editcont.setName(name);
            contacts.put(emails, editcont);
        }catch (Exception e){
            e.getMessage();
            System.out.println(e.getMessage());
        }
    }
}