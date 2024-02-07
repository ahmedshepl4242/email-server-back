package com.example.emailserver.Service.SortVisitor.Strategey.SortStratigies;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ToSortStrategey implements IStrategey
{
    @Override
    public void sort(MailFolders mailFolders) {
        Queue<Mail> mailQueue = new PriorityQueue<>((mail1, mail2) -> mail2.getHeader().getTo().get(0).toLowerCase().compareTo(mail1.getHeader().getTo().get(0).toLowerCase()));
        for(Mail mail:mailFolders.getEmails().values())
        {
            mailQueue.add(mail);
        }
        ArrayList<Mail> mails=new ArrayList<>();
        while (!mailQueue.isEmpty())
        {
            mails.add(mailQueue.poll());
        }
        ArrayList<Mail>mails2=new ArrayList<>();
        for(int i=mails.size()-1;i>=0;i--)
        {
            mails2.add(mails.get(i));
        }
        mailFolders.setVemails(mails2);
    }
}
