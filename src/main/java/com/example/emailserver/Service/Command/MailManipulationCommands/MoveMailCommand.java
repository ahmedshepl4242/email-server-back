package com.example.emailserver.Service.Command.MailManipulationCommands;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Folders.UserFolder;

public class MoveMailCommand implements ICommand
{
   private MailFolders from;
    private UserFolder to;
    private String id;

    public MoveMailCommand(MailFolders from, UserFolder to, String id) {
        this.from = from;
        this.to = to;
        this.id = id;
    }

    @Override
    public void execute() {

        to.MoveMail(from,id);

    }
}
