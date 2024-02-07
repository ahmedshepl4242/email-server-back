package com.example.emailserver.Service.Searchvisitor.search;

import com.example.emailserver.Service.Folders.Mail.Attachment;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;

import java.util.ArrayList;
import java.util.Map;

public class attachmentSearch implements searchStrategy {
    private String item;

    public attachmentSearch(String item) {
        this.item = item;
    }

    @Override
    public ArrayList<Mail> search(MailFolders folder) {
        folder.getVEmails().clear();
        ArrayList<Mail> mails = new ArrayList<>();

//        MailFolders folder = factoryMethod.chooseFolder(nameFolder);
        if (folder != null) {
            for (Map.Entry<String, Mail> entry : folder.getEmails().entrySet()) {
                for (Attachment ent : entry.getValue().getBody().getAttachments()) {
                    if (ent.getName().contains(item)) {
                        mails.add(entry.getValue());
                        break;
                    }
                }
            }

        }
        folder.setVemails(mails);
        return null;
    }
}
