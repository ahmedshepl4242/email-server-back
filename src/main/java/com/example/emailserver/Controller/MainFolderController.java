package com.example.emailserver.Controller;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Services.MainFolderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/mainfolders")
public class MainFolderController {
    MainFolderService mainFolderService=new MainFolderService();
    @GetMapping("/getinbox/{userid}")
    public ArrayList<Mail> getInbox(@PathVariable String userid)
    {
        return mainFolderService.getInbox(userid);
    }
    @GetMapping("/getinbox/{userid}/{id}")
    public Mail getInboxMail(@PathVariable String userid, @PathVariable String id)
    {
        return mainFolderService.getInboxMail(userid, id);
    }
    @GetMapping("/gettrash/{userid}")
    public ArrayList<Mail> getTrash(@PathVariable String userid)
    {
        return mainFolderService.getTrash(userid);
    }
    @GetMapping("/gettrash/{userid}/{id}")
    public Mail getTrashMail(@PathVariable String userid, @PathVariable String id)
    {
        return mainFolderService.getTrashMail(userid, id);
    }
    @GetMapping("/getdraft/{userid}")
    public ArrayList<Mail> getDraft(@PathVariable String userid)
    {
        return mainFolderService.getDraft(userid);
    }
    @GetMapping("/getdraft/{userid}/{id}")
    public Mail getDraftMail(@PathVariable String userid, @PathVariable String id)
    {
        return mainFolderService.getDraftMail(userid, id);
    }
    @GetMapping("/getsentfolder/{userid}")
    public ArrayList<Mail> getSentFolder(@PathVariable String userid)
    {
        return mainFolderService.getSentFolder(userid);
    }
    @GetMapping("/getsent/{userid}/{id}")
    public Mail getSentMail(@PathVariable String userid, @PathVariable String id)
    {
        return mainFolderService.getSentMail(userid, id);
    }
    @GetMapping("/getmail/{userfolderid}/{userid}/{id}")
    public Mail getUserfoldermail(@PathVariable String userid,@PathVariable String userfolderid , @PathVariable String id)
    {
        System.out.println(userfolderid+" "+userid+" "+id+" ");
        Mail mail=mainFolderService.getUserFolder(userid,id,userfolderid);

        System.out.println(mail.getHeader().getFrom());
        return mail ;
    }

}