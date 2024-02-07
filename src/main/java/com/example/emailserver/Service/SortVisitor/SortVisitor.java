package com.example.emailserver.Service.SortVisitor;

import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.SortVisitor.Strategey.SortStratigies.IStrategey;

public class SortVisitor implements IMailvisitor{
   private IStrategey Strategey;

    public SortVisitor(IStrategey strategey) {
        Strategey = strategey;
    }

    @Override
    public void visit(MailFolders mailFolder) {
         Strategey.sort(mailFolder);
    }
}
