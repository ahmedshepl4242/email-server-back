package com.example.emailserver.Service.Folders.Mail;
import java.util.Date;

public class Mail {
    private String id;
    private Body body;
    private Header header;

    public Mail() {
        id = null;
        body = new Body();
        header = new Header();
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getId() {
        return id;
    }
    public Body getBody() {
        return body;
    }
    public Header getHeader() {
        return header;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setText(String text) {
        body.setText(text);
    }
    public void addAttachment(String fileName) {
        body.addAttachment(fileName);
    }
    public void deleteAttachment(String fileName) {
        body.deleteAttachment(fileName);
    }
    public void addTo(String email) {
        header.addTo(email);
    }
    public void removeTO(String email) {
        header.removeTO(email);
    }
    public void setFrom(String from) {
        header.setFrom(from);
    }
    public void setSubject(String subject) {
        header.setSubject(subject);
    }
    public void setPriority(Priority p) {
        header.setPriority(p);
    }
    public void setDate(Date date) {
        header.setDate(date);
    }
}
