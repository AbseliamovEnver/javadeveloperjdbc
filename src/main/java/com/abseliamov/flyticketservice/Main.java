package com.abseliamov.flyticketservice;

import com.abseliamov.flyticketservice.utils.Injector;
import com.abseliamov.flyticketservice.view.MenuContentList;

public class Main {
    public static void main(String[] args) {

        MenuContentList.createMenuContent();

        Injector.getPassengerMenu().mainMenu();
    }
}
