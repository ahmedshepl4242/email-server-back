package com.example.emailserver.Service.Services;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.Mail.Priority;
import com.example.emailserver.Service.cache.CacheInterface;

public class ComposeService {
    private String attachmentsPath = "D:\\CSE level 2\\programming 2\\assignments\\assignment 4\\learning stuff\\demo\\" ;

    public void createMail(String userId, String id) {
        Mail mail = new Mail();
        mail.setId(id);
        mail.setDate(new Date());
        mail.setFrom(userId);
        CacheInterface.getAccount(userId).getDraft().addMail(id, mail);
        CacheInterface.update(CacheInterface.getAccount(userId));
    }

    private Mail getMail(String userId, String id) {
               Mail mail=CacheInterface.getAccount(userId).getDraft().getMail(id);
               mail.setDate(new Date());
        return mail;
    }

    public void addTo(String userId, String id, String email) {
        Mail mail = getMail(userId, id);
        mail.addTo(email);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void removeTO(String userId, String id, String email) {
        Mail mail = getMail(userId, id);
        mail.removeTO(email);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void changeText(String userId, String id, String text) {
        if (text==null) text = "";
        Mail mail = getMail(userId, id);
        mail.setText(text);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void changeFrom(String userId, String id, String from) {
        Mail mail = getMail(userId, id);
        mail.setFrom(from);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void changeSubject(String userId, String id, String subject) {
        if (subject==null) subject = "";
        Mail mail = getMail(userId, id);
        mail.setSubject(subject);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void changePriority(String userId, String id, Priority p) {
        Mail mail = getMail(userId, id);
        mail.setPriority(p);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void addAttachment(String userId, String id, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = attachmentsPath + id + fileName;
        File saveFile = new File(filePath);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mail mail = getMail(userId, id);
        mail.addAttachment(fileName);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public void deleteAttachment(String userId, String id, String fileName) {
        String attachmentPath = attachmentsPath + id + fileName;
        File file = new File(attachmentPath);
        file.delete();
        Mail mail = getMail(userId, id);
        mail.deleteAttachment(fileName);
        CacheInterface.update(CacheInterface.getAccount(userId));

    }
    public File getAttachmentFile(String userId, String id, String fileName) {
        return new File(attachmentsPath + id + fileName);
    }
}
