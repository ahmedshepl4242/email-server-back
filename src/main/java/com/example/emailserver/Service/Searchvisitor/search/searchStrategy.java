package com.example.emailserver.Service.Searchvisitor.search;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;

import java.util.ArrayList;

public interface searchStrategy {
  public ArrayList<Mail>  search(MailFolders folder);
}
