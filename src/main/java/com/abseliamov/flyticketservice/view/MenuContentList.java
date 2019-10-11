package com.abseliamov.flyticketservice.view;

import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class MenuContentList {
    private static List<String> headerMenu = new ArrayList<>();
    private static List<String> mainMenu = new ArrayList<>();
    private static List<String> searchMenu = new ArrayList<>();
    private static List<String> mainAdminMenu = new ArrayList<>();
    private static List<String> footerMenu = new ArrayList<>();
    private static CurrentUser currentUser;

    private MenuContentList() {
    }

    public static void createMenuContent() {
        headerMenu.add("*********************************");
        headerMenu.add(" ** WELCOME TO BOOKING FLIGHT **");
        headerMenu.add("*********************************");

        mainMenu.add("MAIN MENU");
        mainMenu.add("Sing in");
        mainMenu.add("Create account");
        mainMenu.add("Exit");

        searchMenu.add("TICKET MENU");
        searchMenu.add("Search ticket");
        searchMenu.add("Buy ticket");
        searchMenu.add("Return ticket");
        searchMenu.add("Logout");
        searchMenu.add("Exit");

        mainAdminMenu.add("Create route");
        mainAdminMenu.add("Search route");
        mainAdminMenu.add("Update route");
        mainAdminMenu.add("Delete route");
        mainAdminMenu.add("Update user");
        mainAdminMenu.add("Exit");

        footerMenu.add("**************************************");
        footerMenu.add("-= THANK FOR USING OUR APPLICATION! =-");
        footerMenu.add(" *****-= HAVE A NICE TRIP!!! =-*****");
    }

    public static List<String> getHeaderMenu() {
        return headerMenu;
    }

    public static List<String> getMainMenu() {
        return mainMenu;
    }

    public static List<String> getSearchMenu() {
        return searchMenu;
    }

    public static List<String> getMainAdminMenu() {
        return mainAdminMenu;
    }

    public static List<String> getFooterMenu() {
        return footerMenu;
    }
}
