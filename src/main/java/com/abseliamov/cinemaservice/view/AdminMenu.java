package com.abseliamov.cinemaservice.view;

import com.abseliamov.cinemaservice.utils.IOUtil;

public class AdminMenu extends ViewerMenu {
    private ViewerMenu viewerMenu;

    public AdminMenu(ViewerMenu viewerMenu) {
        super();
        this.viewerMenu = viewerMenu;
    }

    public void adminMenu() {
        IOUtil.printMenuHeader(MenuContent.getHeaderMenu());
        long adminMenuItem = -1;
        while (true) {
            if (adminMenuItem == -1) {
                IOUtil.printMenuItem(MenuContent.getAdminMenu());
                adminMenuItem = IOUtil.getValidLongInputData("Select ADMIN MENU item: ");
            }
            switch ((int) adminMenuItem) {
                case 0:
                    IOUtil.printMenuHeader(MenuContent.getFooterMenu());
                    System.exit(0);
                    break;
                case 1:
                    adminMenuItem = create();
                    break;
                case 2:
                    adminMenuItem = select();
                    break;
                case 3:
                    adminMenuItem = update();
                    break;
                case 4:
                    adminMenuItem = delete();
                    break;
                case 5:
                    adminMenuItem = viewerMenu.viewerMenu();
                    break;
                default:
                    if (adminMenuItem >= MenuContent.getAdminMenu().size() - 1) {
                        adminMenuItem = -1;
                        System.out.println("Enter correct admin menu item.\n*********************************");
                    }
                    break;
            }
        }
    }

    private long create() {
        long createMenuItem = -1;
        long ticketId;
        while (true) {
            if (createMenuItem == -1) {
                IOUtil.printMenuItem(MenuContent.getAdminMenuCreate());
                createMenuItem = IOUtil.getValidLongInputData("Select CREATE MENU item: ");
            }
            switch ((int) createMenuItem) {
                case 0:
                    return -1;
            }
        }
    }

    private long select() {
        return 0;
    }

    private long update() {
        return 0;
    }

    private long delete() {
        return 0;
    }
}
