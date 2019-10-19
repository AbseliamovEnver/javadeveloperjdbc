package com.abseliamov.cinemaservice.view;

import com.abseliamov.cinemaservice.controller.*;
import com.abseliamov.cinemaservice.utils.IOUtil;

public class AdminMenu extends ViewerMenu {
    private GenreController genreController;
    private MovieController movieController;
    private SeatController seatController;
    private ViewerController viewerController;
    private TicketController ticketController;
    private ViewerMenu viewerMenu;

    public AdminMenu(GenreController genreController, MovieController movieController,
                     SeatController seatController, ViewerController viewerController,
                     TicketController ticketController, ViewerMenu viewerMenu) {
        super();
        this.genreController = genreController;
        this.movieController = movieController;
        this.seatController = seatController;
        this.viewerController = viewerController;
        this.ticketController = ticketController;
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
                    adminMenuItem = createMenu();
                    break;
                case 2:
                    adminMenuItem = selectMenu();
                    break;
                case 3:
                    adminMenuItem = updateMenu();
                    break;
                case 4:
                    adminMenuItem = deleteMenu();
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

    private long createMenu() {
        long createMenuItem = -1;
        while (true) {
            if (createMenuItem == -1) {
                IOUtil.printMenuItem(MenuContent.getAdminMenuCreate());
                createMenuItem = IOUtil.getValidLongInputData("Select CREATE MENU item: ");
            }
            switch ((int) createMenuItem) {
                case 0:
                    return -1;
                case 1:
                    createGenre();
                    createMenuItem = -1;
                    break;
            }
        }
    }

    private long selectMenu() {
        long selectMenuItem = -1;
        while (true) {
            if (selectMenuItem == -1) {
                IOUtil.printMenuItem(MenuContent.getAdminMenuSelect());
                selectMenuItem = IOUtil.getValidLongInputData("Choose SELECT MENU item: ");
            }
            switch ((int) selectMenuItem) {
                case 0:
                    return -1;
                case 1:
                    genreController.getAll();
                    selectMenuItem = -1;
                    break;
                case 2:
                    movieController.getAll();
                    selectMenuItem = -1;
                    break;
                case 3:
                    seatController.getAll();
                    selectMenuItem = -1;
                    break;
                case 4:
                    viewerController.getAll();
                    selectMenuItem = -1;
                    break;
                case 5:
                    ticketController.getAllTicket();
                    selectMenuItem = -1;
                    break;
                default:
                    if (selectMenuItem >= MenuContent.getAdminMenuSelect().size() - 1) {
                        selectMenuItem = -1;
                        System.out.println("Enter correct select menu item.\n*********************************");
                    }
                    break;
            }
        }
    }

    private long updateMenu() {
        return 0;
    }

    private long deleteMenu() {
        return 0;
    }

    private void createGenre() {
        String genreName = IOUtil.readString("Enter new genre name:");
        genreController.createGenre(genreName);
    }
}
