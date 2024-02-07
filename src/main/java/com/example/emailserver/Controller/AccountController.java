package com.example.emailserver.Controller;

//import com.example.emailserver.Service.Folders.Inbox;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Services.AccountService;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {
    AccountService userService=new AccountService();

    @GetMapping("/getaccount/{userid}")
    public Account getAccount(@PathVariable String userid)
    {
        return userService.getUser(userid);
    }


    @PostMapping("/delete")
    public  void delete(@RequestParam  String id){
        System.out.println(id);
        CacheInterface.delete(id);
    }


}
