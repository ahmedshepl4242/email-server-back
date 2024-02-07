package com.example.emailserver.Controller;

import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sign")
public class sign {

    @PostMapping("/login")
    String login(@RequestParam String id, @RequestParam String password) {
        System.out.println("id   " + id + "    password" + password + " ");

        Account user = CacheInterface.getAccount(id);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user.getId() ;
            } else {
                throw new RuntimeException("password or email wrong");
            }
        }
        return null;

    }

    @PostMapping("/signup")
    void signUp(@RequestParam String id, @RequestParam String password, @RequestParam String name) {
        System.out.println("id   " + id + "    password" + password + " " + "    name" + name);

//        if (CacheInterface.isFound(id)) {
//            throw new RuntimeException("please change  email ");
//        }
        CacheInterface.create(name, id, password);
    }
}
