package by.epam.lab.controller;

public interface IViewController extends Runnable{
       
       public void start();
       public void abort();
       public void view();
       public void updateView();
}
