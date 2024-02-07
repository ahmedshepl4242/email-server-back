package com.example.emailserver.Service.Observer;

import com.example.emailserver.Service.Folders.Mail.Mail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Channel implements IObservable {
    private Queue<Observer> observers = new LinkedList<>();
    @Override
    public void addObserver(Observer observer) {
        observers.offer(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }



    @Override
    public void notifyObservers(Mail message) {
       while (!observers.isEmpty()) {

            observers.poll().update(message);
        }
    }
    public void clearObservers()
    {
        observers.clear();
    }
}
