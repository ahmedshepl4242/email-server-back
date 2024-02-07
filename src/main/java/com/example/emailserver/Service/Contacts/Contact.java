package com.example.emailserver.Service.Contacts;


public class Contact {
    public Contact() {

    }

    private String name;
    private String emails;

    public Contact(String name, String emails) {

        this.name = name;
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public void addEmail(String email) {
        this.emails = (email);
    }
}