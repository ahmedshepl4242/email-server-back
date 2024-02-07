package com.example.emailserver.Service.Searchvisitor.search;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;

import java.util.ArrayList;
import java.util.Map;

public class bodySearch implements searchStrategy {
    private String item;


    public bodySearch(String item) {
        this.item = item;
    }
    @Override
    public ArrayList<Mail> search(MailFolders folder) {
        ArrayList<Mail> mails = new ArrayList<>();
        folder.getVEmails().clear();

//        MailFolders folder = factoryMethod.chooseFolder(nameFolder);
        if (folder != null) {
            for (Map.Entry<String, Mail> entry : folder.getEmails().entrySet()) {
                if (entry.getValue().getBody().getText().contains(item)) {
                    mails.add(entry.getValue());
                }
            }

        }
        folder.setVemails(mails);
        return null;
    }
}
