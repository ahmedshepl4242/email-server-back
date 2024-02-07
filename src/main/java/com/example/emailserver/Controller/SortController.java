package com.example.emailserver.Controller;

import com.example.emailserver.Service.Services.MailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.emailserver.Service.Folders.Mail.Mail;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/sort")
public class SortController {
    MailsService mailsService=new MailsService();
    @GetMapping("/getsorted/{userid}/{way}/{folder}")
    public ArrayList<Mail>sort(@PathVariable String userid,@PathVariable String way,@PathVariable  String folder)
    {
            return mailsService.sort(userid,way,folder);
    }
    @GetMapping("/undoSort/{userid}/{folder}")
    public ArrayList<Mail>undosort(@PathVariable String userid,@PathVariable String folder)
    {
         return mailsService.undosort(userid,folder);
    }

}
