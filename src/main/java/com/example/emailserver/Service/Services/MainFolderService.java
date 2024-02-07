package com.example.emailserver.Service.Services;



import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Command.MailManipulationCommands.SendMailCommand;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class MainFolderService {


    public ArrayList<Mail> getInbox(String userid) {
        Account account = CacheInterface.getAccount(userid);
        return account.getInbox().getVEmails();
    }

    public Mail getInboxMail(String userid, String id) {
        Account account = CacheInterface.getAccount(userid);
        return account.getInbox().getMail(id);
    }

    public ArrayList<Mail> getTrash(String userid) {
        Account account = CacheInterface.getAccount(userid);
        return account.getTrash().getVEmails();
    }

    public Mail getTrashMail(String userid, String id) {
        Account account = CacheInterface.getAccount(userid);
        return account.getTrash().getMail(id);
    }

    public ArrayList<Mail> getDraft(String userid) {
        Account account = CacheInterface.getAccount(userid);
        return account.getDraft().getVEmails();
    }

    public Mail getDraftMail(String userid, String id) {
        Account account = CacheInterface.getAccount(userid);
        return account.getDraft().getMail(id);
    }

    public ArrayList<Mail> getSentFolder(String userid) {
        Account account = CacheInterface.getAccount(userid);
        return account.getSentfolders().getVEmails();
    }

    public Mail getSentMail(String userid, String id) {
        Account account = CacheInterface.getAccount(userid);
        return account.getSentfolders().getMail(id);
    }
    public Mail getUserFolder(String userid, String id,String userfolderid)
    {
        Account account = CacheInterface.getAccount(userid);
        return account.getUserFolders().get(userfolderid).getEmails().get(id);
    }

}
