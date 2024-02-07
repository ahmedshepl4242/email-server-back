package com.example.emailserver.Controller.Requests;

import java.util.List;

public class DeleteRequest {

    private List<String> ids;
    private String name;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
