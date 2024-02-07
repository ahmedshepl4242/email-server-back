package com.example.emailserver.Service.Observer;

import com.example.emailserver.Service.Folders.Mail.Mail;

public interface Observer {
    void update(Mail mail);
}
