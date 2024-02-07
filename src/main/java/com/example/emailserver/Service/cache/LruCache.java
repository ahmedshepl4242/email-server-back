package com.example.emailserver.Service.cache;


import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.DAO.managerDB;

public class LruCache {
    private static int capacity = 100;

    public static int getCapacity() {
        return capacity;
    }


    public static Account get(String userID) {
        if (managerDB.cache.containsKey(userID)) {
            System.out.println("enter cache");
            return managerDB.cache.get(userID);
        } else {
            if (managerDB.cache.size() > 0) {
                return null;
            }
            boolean test = managerDB.excute();
            System.out.println("enteeeeeeeeeeeeeeeeeeer" + test);
            if (!test) {
                return null;
            }
            System.out.println(managerDB.cache);
            if (managerDB.cache.containsKey(userID)) {
                return managerDB.cache.get(userID);
            }
        }
        System.out.println("no account");

        return null;
    }

    public static void set(String userID, Account account) {
        if (!cahceContain(userID)) {
            return;
        }
        managerDB.cache.put(userID, account);
        managerDB.updateDb(account);
    }

    public static void write(Account user) {
        if (managerDB.cache.containsKey(user.getId())) {
            return;
        } else {
            managerDB.cache.put(user.getId(), user);
            managerDB.writeToDb(user);
        }
    }


    public static boolean contains(String userId) {
        if (managerDB.cache.containsKey(userId)) {
            return true;
        }
        return false;
    }

    public static boolean cahceContain(String userId) {
        return managerDB.cache.containsKey(userId);
    }

    public static boolean isFull() {
        return managerDB.cache.size() == capacity;
    }

    public static void remove(String userId) {
        if (!managerDB.cache.containsKey(userId)) {
            return;
        }
        managerDB.cache.remove(userId);
        managerDB.deleteDB(userId);
    }


    public static void setCapcity(int newCapacity) {
        capacity = newCapacity;
    }

}
