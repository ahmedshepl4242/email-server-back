package com.example.emailserver.Service.DAO;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Contacts.ContactsRegistery;
import com.example.emailserver.Service.Folders.*;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Observer.Observer;
import com.google.gson.Gson;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.GsonBuilder;


import java.util.Date;
import java.util.HashMap;

public class Account implements Observer
{
    public Account() {
    }
    private String id;

    private String name;

    private Inbox inbox=new Inbox("inbox");
    private Trash trash=new Trash("trash");
    private Draft draft=new Draft("draft");
    private SentFolders sentfolders=new SentFolders("sentFolders");
    private HashMap<String, UserFolder>userFolders=new HashMap<>();

    private ContactsRegistery contactsRegistery = new ContactsRegistery();

    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
    }

    public void setTrash(Trash trash) {
        this.trash = trash;
    }

    public void setDraft(Draft draft) {
        this.draft = draft;
    }

    public void setSentfolders(SentFolders sentfolders) {
        this.sentfolders = sentfolders;
    }

    public void setUserFolders(HashMap<String, UserFolder> userFolders) {
        this.userFolders = userFolders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    public Account(String name,String id,String password) {
        this.name = name;
        this.id=id;
        this.password=password;
    }
    @JsonProperty("id")
    public String getId() {
        return id;
    }
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public ContactsRegistery getContactsRegistery() {
        return contactsRegistery;
    }

    public void setContactsRegistery(ContactsRegistery contactsRegistery) {
        this.contactsRegistery = contactsRegistery;
    }

    @JsonProperty("inbox")
    public Inbox getInbox() {

        return inbox;
    }
    @JsonProperty("trash")
    public Trash getTrash() {
        return trash;
    }
    @JsonProperty("draft")
    public Draft getDraft() {
        return draft;
    }
    @JsonProperty("sentfolders")
    public SentFolders getSentfolders() {
        return sentfolders;
    }
    @JsonProperty("userfolder")
    public HashMap<String, UserFolder> getUserFolders() {
        return userFolders;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    @Override
    public void update(Mail mail) {
        inbox.addMail(mail.getId(),mail);
    }

    public void performAction(ICommand command) {
        command.execute();
    }

}
