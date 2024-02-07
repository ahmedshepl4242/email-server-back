package com.example.emailserver.Service.Folders.Mail;
import java.util.ArrayList;
import java.util.Date;

public class Header {
    private ArrayList<String> to;
    private String from;
    private String subject;
    private Priority priority;
    private Date date;

    public Header() {
        to = new ArrayList<String>();
        from = new String();
        subject = new String();
        priority = Priority.NORMAL;
        date = null;
    }

    public Date getDate() {
        return date;
    }
    public String getFrom() {
        return from;
    }
    public Priority getPriority() {
        return priority;
    }
    public String getSubject() {
        return subject;
    }
    public ArrayList<String> getTo() {
        return to;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void addTo(String email) {
        this.to.add(email);
    }
    public void removeTO(String email) {
        this.to.remove(email);
    }
}
