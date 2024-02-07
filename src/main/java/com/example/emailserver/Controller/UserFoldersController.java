package com.example.emailserver.Controller;

import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.UserFolder;
import com.example.emailserver.Service.Services.UserFolderservices;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/userfolders")
public class UserFoldersController
{

    UserFolderservices userFolderservices=new UserFolderservices();
    @GetMapping("/getuserfolders/{userid}")
    public ArrayList<UserFolder> getUserFolders(@PathVariable String userid) {
       return userFolderservices.getUserFolders(userid);
    }

    @GetMapping("/getuserfoldersbyid/{userid}/{id}")
    public ArrayList<Mail> getUserFolder(@PathVariable String userid, @PathVariable String id) {
        return userFolderservices.getUserFoldersmails(userid,id);
    }


    @PostMapping("/createuserfolder/{userid}")
    public void createUserFolder(@PathVariable String userid,@RequestParam String id, @RequestParam String name) {
        System.out.println(id+"yyyyyyyyyy");
        userFolderservices.CreateUserFolder(userid,id, name);
    }

    @PutMapping("/renameuserfolder/{userid}")
    public void renameUserFolder(@PathVariable String userid,@RequestParam String id, @RequestParam String name)
    {
        userFolderservices.renameUserFolder(userid,id,name);
    }
    @DeleteMapping("/deleteuserfolder/{userid}")
    public void deleteUserFolder(@PathVariable String userid,@RequestBody String id)
    {
        userFolderservices.deleteUserFolder(userid,id);
    }
}
