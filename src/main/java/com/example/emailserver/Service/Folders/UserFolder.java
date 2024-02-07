package com.example.emailserver.Service.Folders;

public class UserFolder extends MailFolders {


    String id;

    public void setId(String id) {
        this.id = id;
    }

    public UserFolder(String name, String id) {
        super(name);
        this.id = id;
    }
    public UserFolder(String name) {
        super(name);

    }



    public void rename(String name) {
        this.Name = name;
    }

    public String getId() {
        return id;
    }



}
