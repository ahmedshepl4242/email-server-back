package com.example.emailserver.Service.Services;


import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.stereotype.Service;


@Service
public class AccountService
{


    private ICommand command;
    public Account getUser(String userid)
    {
        Account account= CacheInterface.getAccount(userid);
        return account;
    }

}
