package com.example.emailserver.Service.SortVisitor.Strategey.SortStratigies;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class DateSortStrategey implements IStrategey {
    @Override
    public void sort(MailFolders mailFolders) {
        Queue<Mail> mailQueue = new PriorityQueue<>((mail1, mail2) -> mail2.getHeader().getDate().compareTo(mail1.getHeader().getDate()));
        for(Mail mail:mailFolders.getEmails().values())
        {
            mailQueue.add(mail);
        }
        ArrayList<Mail> mails=new ArrayList<>();
        while (!mailQueue.isEmpty())
        {
            mails.add(mailQueue.poll());
        }
        mailFolders.setVemails(mails);
    }
}
