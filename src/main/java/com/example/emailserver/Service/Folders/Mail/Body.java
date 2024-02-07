package com.example.emailserver.Service.Folders.Mail;
import java.util.ArrayList;


public class Body {
    private String text;
    private ArrayList<Attachment> attachments;
    
    public Body() {
        text = new String();
        attachments = new ArrayList<Attachment>();
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public void addAttachment(String fileName) {
        attachments.add(new Attachment(fileName));
    }

    public void deleteAttachment(String fileName) {
        Attachment attachment = null;
        for (Attachment a : attachments) {
            if (a.getName().equals(fileName)) {
                attachment = a;
                break;
            }
        }
        attachments.remove(attachment);
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }
}
