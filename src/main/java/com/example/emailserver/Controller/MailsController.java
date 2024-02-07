package com.example.emailserver.Controller;


import com.example.emailserver.Controller.Requests.DeleteRequest;
import com.example.emailserver.Controller.Requests.MoveRequest;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Services.MailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/mails")
public class MailsController
{
    MailsService mailsService=new MailsService();


    @PutMapping("/Sendmail/{userid}/{mailid}")
    public String Sendmail(@PathVariable String userid,@PathVariable String mailid,@RequestBody ArrayList<String>recivers)
    {
    return mailsService.Sendmail(userid,mailid,recivers);
    }

    @DeleteMapping("/deletemails/{userid}")
    public void deleteMails(@PathVariable String userid,@RequestBody DeleteRequest deleteRequest) {
        System.out.println(deleteRequest.getIds().toString()+"wywqqqqqqqqqqqqqqqqqqqqqqq");
        mailsService.Deletemails(userid,deleteRequest.getName(), deleteRequest.getIds());
    }

    @PutMapping("/movemails/{userid}")
    public void Movemail(@PathVariable String userid,@RequestBody MoveRequest moveRequest)
    {

        mailsService.Movemail(userid,moveRequest.getFrom(),moveRequest.getTo(),moveRequest.getId());
    }

    @PutMapping("/restoretrash/{userid}/{mailid}")
    public void RestoreMail(@PathVariable String userid,@PathVariable String mailid)
    {
         mailsService.restoremail(userid,mailid);
    }



}
