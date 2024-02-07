package com.example.emailserver.Service.Folders;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.SortVisitor.IMailvisitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;
public abstract class MailFolders implements IMailFolders {

    protected  String Name;
    protected HashMap<String, Mail> Emails=new HashMap<>();
    protected ArrayList<Mail>VEmails=new ArrayList<>();


    public MailFolders(String name) {
        Name = name;
    }

    public void addMail(String id, Mail mail)
    {
        Emails.put(id,mail);
        updateview();

    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmails(HashMap<String, Mail> emails) {
        Emails = emails;
    }

    public void setVEmails(ArrayList<Mail> VEmails) {
        this.VEmails = VEmails;
    }

    public void removeMail(String id, Trash trash)
    {Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Add 30 days
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        // Get the result as a Date
        Date futureDate = calendar.getTime();

        Mail deleted=this.Emails.remove(id);
        trash.deleteDate.put(id,futureDate);
        trash.addMail(id,deleted );
        updateview();
    }
    public void MoveMail(MailFolders from,String id)
    {
        this.addMail(id,from.Emails.remove(id));
        updateview();
    }

    public void accept(IMailvisitor visitor)
    {
        visitor.visit(this);
    }

    public Mail getMail(String id)
    {
        return Emails.get(id);
    }
    public String getName()
    {
        return this.Name;
    }
    public HashMap<String, Mail> getEmails() {
        return Emails;
    }
    public void updateview()
    {
        VEmails.clear();
        for(Mail mail:Emails.values())
        {
            VEmails.add(mail);
        }
    }

    public void setVemails(ArrayList<Mail> VEmails) {
        this.VEmails = VEmails;
    }

    public ArrayList<Mail> getVEmails() {
        return VEmails;
    }
}
