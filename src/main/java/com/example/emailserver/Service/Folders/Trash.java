package com.example.emailserver.Service.Folders;



import com.example.emailserver.Service.Folders.Mail.Mail;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Trash extends MailFolders
{
    protected HashMap<String, Date>deleteDate=new HashMap<>();
    private HashMap<String,String>from=new HashMap<>();
    public Trash(String name) {
        super(name);
    }

    public HashMap<String, Date> getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(HashMap<String, Date> deleteDate) {
        this.deleteDate = deleteDate;
    }

    public HashMap<String, String> getFrom() {
        return from;
    }

    public void setFrom(HashMap<String, String> from) {
        this.from = from;
    }

    @Override
    public void removeMail(String id,Trash trash)
    {
        Emails.remove(id);
        updateview();
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public ArrayList<Mail> getVEmails()
    {


        for (HashMap.Entry< String, Mail> entry : Emails.entrySet())
        {
            //System.out.println(entry.getValue());
            if(new Date().after(deleteDate.get(entry.getKey())))
            { Emails.remove(entry.getKey());}


        }
        updateview();
        return VEmails;
    }
//   public void restore()
//   {
//
//   }
}
