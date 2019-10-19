package com.abseliamov.cinemaservice.view;

import com.abseliamov.cinemaservice.controller.ViewerController;
import com.abseliamov.cinemaservice.model.Role;
import com.abseliamov.cinemaservice.utils.CurrentViewer;
import com.abseliamov.cinemaservice.utils.IOUtil;

public class AuthorizationMenu {
    private AdminMenu adminMenu;
    private ViewerMenu viewerMenu;
    private ViewerController viewerController;
    private CurrentViewer currentViewer;

    public AuthorizationMenu(AdminMenu adminMenu, ViewerMenu viewerMenu,
                             ViewerController viewerController, CurrentViewer currentViewer) {
        this.adminMenu = adminMenu;
        this.viewerMenu = viewerMenu;
        this.viewerController = viewerController;
        this.currentViewer = currentViewer;
    }

    public void authorizationMenu() {
        Role role;
        long authorizationMenuItem;
        IOUtil.printMenuItem(MenuContent.getAuthorizationMenu());
        while (true) {
            authorizationMenuItem = IOUtil.getValidLongInputData("Select AUTHORIZATION MENU item: ");
            switch ((int) authorizationMenuItem) {
                case 0:
                    IOUtil.printMenuHeader(MenuContent.getFooterMenu());
                    System.exit(0);
                    break;
                case 1:
                    if (authorization()) {
                        role = currentViewer.getViewer().getRole();
                        if (role == Role.ADMIN) {
                            adminMenu.adminMenu();
                        } else if (role == Role.USER) {
                            viewerMenu.viewerMenu();
                        }
                    }
                    break;
                default:
                    if (authorizationMenuItem >= MenuContent.getAuthorizationMenu().size() - 1) {
                        System.out.println("Enter correct menu item.");
                    }
                    break;
            }
        }
    }

    private boolean authorization() {
        while (true) {
            String name = IOUtil.readString("Enter name: ");
            if (!name.equals("0")) {
                String password = IOUtil.readString("Enter password: ");
                if (viewerController.authorization(name, password)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
