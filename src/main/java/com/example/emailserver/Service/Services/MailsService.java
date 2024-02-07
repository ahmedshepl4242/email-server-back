package com.example.emailserver.Service.Services;

import com.example.emailserver.Service.Command.ICommand;
import com.example.emailserver.Service.Command.MailManipulationCommands.DeleteMailCommand;
import com.example.emailserver.Service.Command.MailManipulationCommands.MoveMailCommand;
import com.example.emailserver.Service.Command.MailManipulationCommands.RestoreMailCommand;
import com.example.emailserver.Service.Command.MailManipulationCommands.SendMailCommand;
import com.example.emailserver.Service.DAO.Account;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.MailFolders;
import com.example.emailserver.Service.Folders.UserFolder;
import com.example.emailserver.Service.SortVisitor.SortVisitor;
import com.example.emailserver.Service.SortVisitor.Strategey.SortStratigies.*;
import com.example.emailserver.Service.cache.CacheInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MailsService {


    ICommand command;
    public String Sendmail(String userid,String mailid,ArrayList<String>Recivers)
    {
        Account account= CacheInterface.getAccount(userid);
        ArrayList<Account>reciv=new ArrayList<>();
        boolean b=true;
        for(String r:Recivers)
        {
            try {
                reciv.add(CacheInterface.getAccount(r));
            }
            catch (Exception e)
            {
                b=false;
                return r;
            }
        }
        if(b) {
            Mail mail=account.getDraft().getEmails().remove(mailid);
            mail.setDate(new Date());
            command = new SendMailCommand(reciv,mail, account.getSentfolders());
            account.performAction(command);
            return "ok";
        }
        return null;
    }
    public void Deletemail(String userid,String Name,String id)
    {
        System.out.println("qqqqqqqqqqqq");
        Account account= CacheInterface.getAccount(userid);
        MailFolders mailFolders=getMailfolder(userid,Name);
        command=new DeleteMailCommand(mailFolders,id,account.getTrash());
        account.performAction(command);
    }
    public void restoremail(String userid,String mailid)
    {
        Account account= CacheInterface.getAccount(userid);
        MailFolders mailFolders=getMailfolder(userid,account.getTrash().getFrom().get(mailid));
        command=new RestoreMailCommand(account.getTrash(),mailid,mailFolders);
        account.performAction(command);
    }
    public void Deletemails(String userid,String name,List<String> ids)
    {

        Account account= CacheInterface.getAccount(userid);
        for(String id:ids) {
            System.out.println(id);
           Deletemail(userid,name,id);
        }
    }
    public void Movemail(String userid,String from, String to, List<String> id)
    {
        Account account= CacheInterface.getAccount(userid);
        MailFolders mailFolders1=getMailfolder(userid,from);
        UserFolder mailFolders2=getmailfolderfromuserfolder(userid,to);
        for (String i:id){
        command=new MoveMailCommand(mailFolders1,mailFolders2,i);
        account.performAction(command);
    }}
    // test
    public void addmail(String userid,String id)
    {
        Account account= CacheInterface.getAccount(userid);
        Mail mail=new Mail();
        mail.setDate(new Date());
        mail.setId(id);
        mail.setText("hello world");
        mail.setFrom("ali");
        mail.setSubject("programming");
        account.getSentfolders().addMail(mail.getId(),mail);
        account.getTrash().addMail(mail.getId(),mail);
       account.getDraft().addMail(mail.getId(),mail);
       account.getInbox().addMail(mail.getId(),mail);


    }

    private  MailFolders getMailfolder(String userid,String string)
    {
        Account account= CacheInterface.getAccount(userid);
        System.out.println(string);
        switch (string)
        {
            case "inbox" :
                return account.getInbox();
            case "trash" :
                return account.getTrash();
            case "draft" :
                return account.getDraft();
            case "sentFolders" :
                return account.getSentfolders();
            default:
                return getmailfolderfromuserfolder(userid,string);
        }
    }
    private UserFolder getmailfolderfromuserfolder(String userid,String string)
    {
        Account account= CacheInterface.getAccount(userid);
        return account.getUserFolders().get(string);
    }
    public ArrayList<Mail> sort(String userid, String way,String folder)
    {
        Account account= CacheInterface.getAccount(userid);
        IStrategey strategey=getStrategey(way);
        SortVisitor sortVisitor=new SortVisitor(strategey);
        MailFolders mailFolders=getMailfolder(userid,folder);
        mailFolders.accept(sortVisitor);
        return mailFolders.getVEmails();
    }

    public ArrayList<Mail>undosort(String userid,String folder)
    {
        Account account= CacheInterface.getAccount(userid);
        MailFolders mailFolders=getMailfolder(userid,folder);
        mailFolders.updateview();
        return mailFolders.getVEmails();
    }
    public IStrategey getStrategey(String way)
    {
        switch (way)
        {
            case "Body":
                return new BodySortStrategey();
            case "Date":
                return new DateSortStrategey();
            case "From":
                return new fromSortStrategey();
            case "To":
                return new ToSortStrategey();
            case "Subject":
                return new SubjectSortStrategey();
            case "Piriority":
                return new PirioritySortStrategey();
            default:
                return null;

        }

    }


}
