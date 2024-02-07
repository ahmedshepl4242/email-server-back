package com.example.emailserver.Service.cache;

import com.example.emailserver.Service.DAO.Account;
import org.springframework.stereotype.Service;

@Service
public class CacheInterface {


    public static Account getAccount(String id) {
        System.out.println(id);


        return LruCache.get(id);
    }

    public static void update(Account user) {
        LruCache.set(user.getId(), user);
    }

    public static boolean isFound(String id) {
        return LruCache.contains(id);
    }

    public static void delete(String id) {
        LruCache.remove(id);
    }

    public static void create(String name, String id, String password) {
        LruCache.write(new Account(name, id, password));
    }


}
