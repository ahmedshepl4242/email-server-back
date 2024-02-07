package com.example.emailserver.Controller;

import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Searchvisitor.search.*;
import com.example.emailserver.Service.Searchvisitor.searchVisitor;
import com.example.emailserver.Service.SortVisitor.IMailvisitor;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/search")
public class search {

    searchStrategy searchStr;

    @PostMapping("")
    public ArrayList<Mail> fromSearch(@RequestParam String id, @RequestParam String item, @RequestParam String folder, @RequestParam String type) {


        Account account = CacheInterface.getAccount(id);


        MailFolders mailFolders = getMailfolder(folder, account);
        System.out.println(item + " " + folder + "  " + type + " ");
        if (type.equalsIgnoreCase("from")) {
            searchStr = new fromSearch(item);
            IMailvisitor mailVisitor = new searchVisitor(searchStr);
            mailFolders.accept(mailVisitor);

        } else if ((type.equalsIgnoreCase("to"))) {
            searchStr = new toSearch(item);
            IMailvisitor mailVisitor = new searchVisitor(searchStr);
            mailFolders.accept(mailVisitor);
        } else if ((type.equalsIgnoreCase("body"))) {
            searchStr = new bodySearch(item);
            IMailvisitor mailVisitor = new searchVisitor(searchStr);
            mailFolders.accept(mailVisitor);

        } else if ((type.equalsIgnoreCase("subject"))) {

            searchStr = new subjectSearch(item);
            IMailvisitor mailVisitor = new searchVisitor(searchStr);
            mailFolders.accept(mailVisitor);

        } else if ((type.equalsIgnoreCase("attach"))) {
            searchStr = new attachmentSearch(item);
            IMailvisitor mailVisitor = new searchVisitor(searchStr);
            mailFolders.accept(mailVisitor);
        }
        return mailFolders.getVEmails();
    }

    private MailFolders getMailfolder(String string, Account account) {
        System.out.println(string);
        switch (string) {
            case "inbox":
                return account.getInbox();
            case "trash":
                return account.getTrash();
            case "draft":
                return account.getDraft();
            case "sentFolders":
                return account.getSentfolders();
            default:
                return getmailfolderfromuserfolder(string, account);
        }
    }

    private MailFolders getmailfolderfromuserfolder(String string, Account account) {
        return account.getUserFolders().get(string);
    }


}
