package com.example.emailserver.Service.Command.UserFolderCommands;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Folders.UserFolder;

import java.util.HashMap;

public class RenameUserFolderCommand implements ICommand {
    private String id;
    private HashMap<String, UserFolder>userFolders;
    private String Name;

    public RenameUserFolderCommand(String id, HashMap<String, UserFolder> userFolders, String name) {
        this.id = id;
        this.userFolders = userFolders;
        Name = name;
    }

    @Override
    public void execute() {
        userFolders.get(id).rename(Name);
    }
}
