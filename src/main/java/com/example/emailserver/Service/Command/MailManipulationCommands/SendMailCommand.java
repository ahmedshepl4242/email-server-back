package com.example.emailserver.Service.Command.MailManipulationCommands;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.SentFolders;
import com.example.emailserver.Service.Observer.Channel;

import java.util.ArrayList;

public class SendMailCommand implements ICommand {

    private ArrayList<Account> recipients;
    private Mail message;
    private Channel channel=new Channel();
    private SentFolders sentFolders;
    public SendMailCommand(ArrayList<Account> recipients, Mail message, SentFolders sentFolders) {
        this.recipients = recipients;
        this.message = message;
        this.sentFolders=sentFolders;
    }

    @Override
    public void execute() {

        sentFolders.addMail(message.getId(),message);
     for(Account recipent:recipients)
        {

            channel.addObserver(recipent);
        }
        channel.notifyObservers(message);
        channel.clearObservers();
    }
}
