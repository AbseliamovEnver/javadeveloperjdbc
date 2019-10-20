package com.abseliamov.cinemaservice.view;

import com.abseliamov.cinemaservice.controller.*;
import com.abseliamov.cinemaservice.model.Genre;
import com.abseliamov.cinemaservice.model.Movie;
import com.abseliamov.cinemaservice.model.Seat;
import com.abseliamov.cinemaservice.model.SeatTypes;
import com.abseliamov.cinemaservice.utils.IOUtil;

import java.math.BigDecimal;

public class AdminMenu extends ViewerMenu {
    private GenreController genreController;
    private MovieController movieController;
    private SeatController seatController;
    private ViewerController viewerController;
    private TicketController ticketController;
    private SeatTypesController seatTypesController;
    private ViewerMenu viewerMenu;

    public AdminMenu(GenreController genreController, MovieController movieController,
                     SeatController seatController, ViewerController viewerController,
                     TicketController ticketController, SeatTypesController seatTypesController,
                     ViewerMenu viewerMenu) {
        super();
        this.genreController = genreController;
        this.movieController = movieController;
        this.seatController = seatController;
        this.viewerController = viewerController;
        this.ticketController = ticketController;
        this.seatTypesController = seatTypesController;
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
                case 2:
                    createMovie();
                    createMenuItem = -1;
                    break;
                case 3:
                    createSeat();
                    createMenuItem = -1;
                    break;
                default:
                    if (createMenuItem >= MenuContent.getAdminMenuCreate().size() - 1) {
                        createMenuItem = -1;
                        System.out.println("Enter correct create menu item.\n*********************************");
                    }
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
        long updateMenuItem = -1;
        while (true) {
            if (updateMenuItem == -1) {
                IOUtil.printMenuItem(MenuContent.getAdminMenuUpdate());
                updateMenuItem = IOUtil.getValidLongInputData("Select UPDATE MENU item: ");
            }
            switch ((int) updateMenuItem) {
                case 0:
                    return -1;
                case 1:
                    updateGenre();
                    updateMenuItem = -1;
                    break;
                case 2:
                    updateMovie();
                    updateMenuItem = -1;
                    break;
                case 3:
                    updateSeat();
                    updateMenuItem = -1;
                    break;
                default:
                    if (updateMenuItem >= MenuContent.getAdminMenuUpdate().size() - 1) {
                        updateMenuItem = -1;
                        System.out.println("Enter correct update menu item.\n*********************************");
                    }
                    break;
            }
        }
    }

    private long deleteMenu() {
        long deleteMenuItem = -1;
        while (true) {
            if (deleteMenuItem == -1) {
                IOUtil.printMenuItem(MenuContent.getAdminMenuDelete());
                deleteMenuItem = IOUtil.getValidLongInputData("Select DELETE MENU item: ");
            }
            switch ((int) deleteMenuItem) {
                case 0:
                    return -1;
                case 1:
                    deleteGenre();
                    deleteMenuItem = -1;
                    break;
                case 2:
                    deleteMovie();
                    deleteMenuItem = -1;
                    break;
                case 3:
                    deleteSeat();
                    deleteMenuItem = -1;
                    break;
                default:
                    if (deleteMenuItem >= MenuContent.getAdminMenuDelete().size() - 1) {
                        deleteMenuItem = -1;
                        System.out.println("Enter correct delete menu item.\n*********************************");
                    }
                    break;
            }
        }
    }

    private void createGenre() {
        String genreName = IOUtil.readString("Enter new genre name or \'0\' to return: ");
        if (!genreName.equals("0")) {
            genreController.createGenre(genreName);
        }
    }

    private void createMovie() {
        Genre genre;
        String movieTitle = IOUtil.readString("Enter new movie title or \'0\' to return: ");
        if (!movieTitle.equals("0") && genreController.getAll() != null) {
            long genreId = IOUtil.readNumber("Select movie genre ID or \'0\' to return:");
            if (genreId != 0 && (genre = genreController.getById(genreId)) != null) {
                movieController.createMovie(movieTitle, genre);
            }
        }
    }

    private void createSeat() {
        SeatTypes seatType = null;
        long seatNumber = IOUtil.readNumber("Enter seat number or enter \'0\' to return: ");
        if (seatNumber != 0 && seatTypesController.getAllSeatType() != null) {
            long seatTypeId = IOUtil.readNumber("Select seat type id or enter \'0\' to return: ");
            if (seatTypeId != 0) {
                for (SeatTypes seatTypesItem : SeatTypes.values()) {
                    if (seatTypesItem.getId() == seatTypeId) {
                        seatType = seatTypesItem;
                    }
                }
                seatController.createSeat(seatNumber, seatType);
            }
        }
    }

    private void updateGenre() {
        String updateGenreName;
        if (genreController.getAll() != null) {
            long genreId = IOUtil.readNumber("Select genre id to update or enter \'0\' to return: ");
            if (genreId != 0 && genreController.getById(genreId) != null) {
                updateGenreName = IOUtil.readString("Enter a new name for the genre to update: ");
                genreController.updateGenre(genreId, updateGenreName);
            }
        }
    }

    private void updateMovie() {
        Movie movie;
        Genre genre = null;
        BigDecimal cost = null;
        String movieTitle = null;
        if (movieController.getAll() != null) {
            long movieId = IOUtil.readNumber("Select movie id to update or enter \'0\' to return: ");
            if (movieId != 0 && (movie = movieController.getById(movieId)) != null) {
                movieTitle = IOUtil.readString("Enter a new title for the movie to update " +
                        "or press \'ENTER\' key to continue: ");
                movieTitle = movieTitle.equals("") ? movie.getName() : movieTitle;
                genreController.getAll();
                String genreStr = IOUtil.readString("Select genre id to update or press \'ENTER\' key to continue: ");
                if (genreStr.equals("")) {
                    genre = movie.getGenre();
                } else {
                    genre = genreController.getById(Long.parseLong(genreStr));
                }
                String costStr = IOUtil.readString("Enter a new cost for the movie to update " +
                        "or press \'ENTER\' key to continue: ");
                if (costStr.equals("")) {
                    cost = movie.getCost();
                } else {
                    cost = new BigDecimal(costStr);
                }
            }
            movieController.updateMovie(movieId, movieTitle, genre, cost);
        }
    }

    private void updateSeat() {
        Seat seat;
        SeatTypes seatType;
        long seatTypeId = 0;
        if (seatController.getAll() != null) {
            long seatId = IOUtil.readNumber("Select seat id to update or enter \'0\' to return: ");
            if (seatId != 0 && (seat = seatController.getById(seatId)) != null) {
                seatTypesController.getAllSeatType();
                String seatTypeStr = IOUtil.readString("Enter a new seat type to update " +
                        "or press \'ENTER\' key to continue: ");
                seatTypeId = seatTypeStr.equals("") ? seat.getSeatTypes().getId() : Long.parseLong(seatTypeStr);
                if ((seatType = seatTypesController.getById(seatTypeId)) != null) {
                    long seatNumber = IOUtil.readNumber("Enter a new seat number to update " +
                            "or press \'ENTER\' key to continue: ");
                    seatController.updateSeat(seatId, seatType, seatNumber);
                }
            }
        }
    }

    private void deleteGenre() {
        if (genreController.getAll() != null) {
            long genreId = IOUtil.readNumber("Select genre id to delete or enter \'0\' to return: ");
            if (genreId != 0 && genreController.getById(genreId) != null) {
                genreController.deleteGenre(genreId);
            }
        }
    }

    private void deleteMovie() {
        if (movieController.getAll() != null) {
            long movieId = IOUtil.readNumber("Select movie id to delete or enter \'0\' to return: ");
            if (movieId != 0 && movieController.getById(movieId) != null) {
                movieController.deleteMovie(movieId);
            }
        }
    }

    private void deleteSeat() {
        if (seatController.getAll() != null) {
            long seatId = IOUtil.readNumber("Select seat id to delete or enter \'0\' to return: ");
            if (seatId != 0 && seatController.getById(seatId) != null){
                seatController.deleteSeat(seatId);
            }
        }
    }
}
