package com.example.emailserver.Service.Command.UserFolderCommands;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Folders.UserFolder;

import java.util.HashMap;

public class DeleteUserFolderCommand implements ICommand {
    private String id;
    private HashMap<String, UserFolder> userFolders=new HashMap<>();


    public DeleteUserFolderCommand(String id, HashMap<String, UserFolder> userFolders) {
        this.id = id;
        this.userFolders = userFolders;

    }

    @Override
    public void execute() {
        userFolders.remove(id);
    }
}
