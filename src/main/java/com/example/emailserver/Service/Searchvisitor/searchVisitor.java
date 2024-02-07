package com.example.emailserver.Service.Searchvisitor;

import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Searchvisitor.search.searchStrategy;
import com.example.emailserver.Service.SortVisitor.IMailvisitor;

public class searchVisitor implements IMailvisitor {
    private searchStrategy searchstrategy;

    public searchVisitor(searchStrategy searchStrategy) {
        this.searchstrategy = searchStrategy;
    }

    @Override
    public void visit(MailFolders folder) {
        searchstrategy.search(folder);
    }
}
