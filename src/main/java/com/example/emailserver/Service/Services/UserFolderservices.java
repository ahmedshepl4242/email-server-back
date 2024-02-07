package com.example.emailserver.Service.Services;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Command.UserFolderCommands.CreateUserFolderCommand;
import com.example.emailserver.Service.Command.UserFolderCommands.DeleteUserFolderCommand;
import com.example.emailserver.Service.Command.UserFolderCommands.RenameUserFolderCommand;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.UserFolder;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserFolderservices
{


    ICommand command;
    public ArrayList<UserFolder> getUserFolders(String userid)
    {
        Account account= CacheInterface.getAccount(userid);
        ArrayList<UserFolder>userFolders=new ArrayList<>();
        for(UserFolder us: account.getUserFolders().values())
        {
            userFolders.add(us);
        }
        CacheInterface.update(CacheInterface.getAccount(userid));
        return userFolders;
    }
    public ArrayList<Mail>getUserFoldersmails(String userid,String l)
    {
        Account account= CacheInterface.getAccount(userid);
        try {
            CacheInterface.update(CacheInterface.getAccount(userid));

            return  account.getUserFolders().get(l).getVEmails();

        }catch (Exception e)
        {
            System.out.println("its empty");
            return null;
        }

    }
    public void CreateUserFolder(String userid,String id ,String Name)
    {        System.out.println(id+"yyyyyyyyyy");
        Account account= CacheInterface.getAccount(userid);
        command=new CreateUserFolderCommand(id,account.getUserFolders(),Name);
        account.performAction(command);
        CacheInterface.update(CacheInterface.getAccount(userid));

    }
    public void renameUserFolder(String userid,String id,String Name)
    {
        Account account= CacheInterface.getAccount(userid);
        command=new RenameUserFolderCommand(id,account.getUserFolders(),Name);
        account.performAction(command);
        CacheInterface.update(CacheInterface.getAccount(userid));

    }
    public void deleteUserFolder(String userid,String id)
    {
        Account account= CacheInterface.getAccount(userid);
        command=new DeleteUserFolderCommand(id,account.getUserFolders());
        account.performAction(command);
        CacheInterface.update(CacheInterface.getAccount(userid));

    }
}
