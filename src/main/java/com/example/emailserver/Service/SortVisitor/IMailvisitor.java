package com.example.emailserver.Service.SortVisitor;


import com.example.emailserver.Service.Folders.MailFolders;

public interface IMailvisitor
{
    public void visit(MailFolders mailFolder);
}
