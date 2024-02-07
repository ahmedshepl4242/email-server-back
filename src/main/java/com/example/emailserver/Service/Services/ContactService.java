package com.example.emailserver.Service.Services;

import com.example.emailserver.Service.Contacts.Contact;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class ContactService {
//        private Account account= CacheInterface.getAccount("q");

    public void addContact(String id, String name, String emails) {
        Account account = CacheInterface.getAccount(id);
        account.getContactsRegistery().addContact(name, emails);
        CacheInterface.update(account);
    }

    public void deleteContact(String id, String email) {
        Account account = CacheInterface.getAccount(id);
        account.getContactsRegistery().deleteContact(email);
        CacheInterface.update(account);

    }


    //    public Contact getContact(String id) {
//        Account account = CacheInterface.getAccount(id);
//        return account.getContactsRegistery().getContact(id);
//
//    }
//    public ArrayList<Contact> getContacts() {
    public Contact searchName(String id, String name) {
        Account account = CacheInterface.getAccount(id);
        for (Map.Entry<String, Contact> entry : account.getContactsRegistery().getHash().entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public ArrayList<Contact> sort(String id) {
        Queue<Contact> contactQueue = new PriorityQueue<>((mail1, mail2) -> mail2.getName().compareTo(mail1.getName()));

        ArrayList<Contact> arr;
        arr=CacheInterface.getAccount(id).getContactsRegistery().getContacts();
        for(Contact contact:arr){
            contactQueue.add(contact);
        }
        ArrayList<Contact> contacts=new ArrayList<>();
        while (!contactQueue.isEmpty())
        {
            contacts.add(contactQueue.poll());
        }
        ArrayList<Contact>mails2=new ArrayList<>();
        for(int i=contacts.size()-1;i>=0;i--)
        {
            mails2.add(contacts.get(i));
        }
        return  mails2;
    }
//        Account account = CacheInterface.getAccount("q");
//        return account.getContactsRegistery().getContacts();
//
//    }

    public void editContact(String id, String name, String emails) {
        Account account = CacheInterface.getAccount(id);
        account.getContactsRegistery().editContact(name, emails);
        CacheInterface.update(account);
    }
}