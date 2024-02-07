package com.example.emailserver.Service.Command.MailManipulationCommands;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Folders.Trash;

public class RestoreMailCommand implements ICommand {


    private Trash trash;
    private String id;
    private MailFolders mailFolders;

    public RestoreMailCommand(Trash trash, String id, MailFolders mailFolders) {
        this.trash = trash;
        this.id = id;
        this.mailFolders = mailFolders;
    }

    @Override
    public void execute() {
       mailFolders.addMail(id,trash.getEmails().get(id));
       trash.removeMail(id,trash);
    }
}
