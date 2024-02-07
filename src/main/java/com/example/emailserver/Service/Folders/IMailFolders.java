package com.example.emailserver.Service.Folders;

import com.example.emailserver.Service.Folders.Mail.Mail;

import java.util.ArrayList;

public interface IMailFolders
{
    public void addMail(String id, Mail Email);
    public void removeMail(String id, Trash trash);
    public Mail getMail(String id);

    public void setId(String id);
    public String getId();
    public String getName();
    public ArrayList<Mail> getVEmails();


}
