package com.abseliamov.flyticketservice.utils;

import com.abseliamov.flyticketservice.controller.*;
import com.abseliamov.flyticketservice.dao.*;
import com.abseliamov.flyticketservice.service.*;
import com.abseliamov.flyticketservice.view.InitializationUser;
import com.abseliamov.flyticketservice.view.PassengerMenu;

public class Injector {
    private static CurrentUser currentUser = CurrentUser.getInstance();

    private static UserDao userDao = new UserDao();
    private static UserService userService = new UserService(userDao, currentUser);
    private static UserController userController = new UserController(userService, currentUser);
    private static InitializationUser initializationUser = new InitializationUser(userController);

    private static CityDao cityDao = new CityDao();
    private static CityService cityService = new CityService(cityDao);
    private static CityController cityController = new CityController(cityService, currentUser);

    private static RouteDao routeDao = new RouteDao(cityDao);
    private static TicketDao ticketDao = new TicketDao(currentUser);
    private static TicketService ticketService = new TicketService(ticketDao, routeDao, currentUser);
    private static TicketController ticketController = new TicketController(ticketService, currentUser);

    private static RouteService routeService = new RouteService(routeDao, ticketService, currentUser);
    private static RouteController routeController = new RouteController(routeService, currentUser);

    private static OrderDao orderDao = new OrderDao(userDao, ticketDao, currentUser);
    private static OrderService orderService = new OrderService(orderDao, ticketDao, routeDao, currentUser);
    private static OrderController orderController = new OrderController(orderService, currentUser);

    private static PassengerMenu passengerMenu = new PassengerMenu(initializationUser, cityController,
            routeController, ticketController, orderController, currentUser);

    private Injector() {
    }

    public static CurrentUser getCurrentUser() {
        return currentUser;
    }

    public static UserDao getUserDAO() {
        return userDao;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static UserController getUserController() {
        return userController;
    }

    public static InitializationUser getInitializationUser() {
        return initializationUser;
    }

    public static CityDao getCityDao() {
        return cityDao;
    }

    public static CityService getCityService() {
        return cityService;
    }

    public static CityController getCityController() {
        return cityController;
    }

    public static RouteDao getRouteDao() {
        return routeDao;
    }

    public static TicketDao getTicketDao() {
        return ticketDao;
    }

    public static RouteService getRouteService() {
        return routeService;
    }

    public static RouteController getRouteController() {
        return routeController;
    }

    public static TicketService getTicketService() {
        return ticketService;
    }

    public static TicketController getTicketController() {
        return ticketController;
    }

    public static OrderDao getOrderDao() {
        return orderDao;
    }

    public static OrderService getOrderService() {
        return orderService;
    }

    public static OrderController getOrderController() {
        return orderController;
    }

    public static PassengerMenu getPassengerMenu() {
        return passengerMenu;
    }
}
