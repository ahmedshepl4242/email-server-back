package com.example.emailserver.Service.Command.MailManipulationCommands;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Folders.Trash;
import com.example.emailserver.Service.Folders.UserFolder;

public class DeleteMailCommand implements ICommand
{

    private MailFolders mailFolders;
    private String id;
    private Trash trash;

    public DeleteMailCommand(MailFolders mailFolders, String id, Trash trash) {
        this.mailFolders = mailFolders;
        this.id = id;
        this.trash = trash;
    }




    @Override
    public void execute() {
        String string;


        trash.getFrom().put(id, getname(mailFolders));
        mailFolders.removeMail(id,trash);
    }
    public String getname(MailFolders mailFolders)
    {
        switch (mailFolders.getName())
        {
            case "inbox" :
            case "trash" :
            case "draft" :
            case "sentFolders" :
                return mailFolders.getName();
            default:
                return mailFolders.getId();
        }
    }

}



