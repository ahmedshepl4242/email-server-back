package com.example.emailserver.Service.Observer;


import com.example.emailserver.Service.Folders.Mail.Mail;

public interface IObservable
{
       void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers(Mail mail);
        public void clearObservers();
}
