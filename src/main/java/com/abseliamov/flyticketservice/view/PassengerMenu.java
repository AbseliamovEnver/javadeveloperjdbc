package com.abseliamov.flyticketservice.view;

import com.abseliamov.flyticketservice.controller.CityController;
import com.abseliamov.flyticketservice.controller.OrderController;
import com.abseliamov.flyticketservice.controller.RouteController;
import com.abseliamov.flyticketservice.controller.TicketController;
import com.abseliamov.flyticketservice.entity.Route;
import com.abseliamov.flyticketservice.entity.TypeSeat;
import com.abseliamov.flyticketservice.utils.CurrentUser;
import com.abseliamov.flyticketservice.utils.IOUtil;
import com.abseliamov.flyticketservice.utils.InputData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PassengerMenu {
    private InitializationUser initializationUser;
    private CityController cityController;
    private RouteController routeController;
    private TicketController ticketController;
    private OrderController orderController;
    private CurrentUser currentUser;

    public PassengerMenu(InitializationUser initializationUser, CityController cityController,
                         RouteController routeController, TicketController ticketController,
                         OrderController orderController, CurrentUser currentUser) {
        this.initializationUser = initializationUser;
        this.cityController = cityController;
        this.routeController = routeController;
        this.ticketController = ticketController;
        this.orderController = orderController;
        this.currentUser = currentUser;
    }

    public void mainMenu() {
        IOUtil.printMenuHeader(MenuContentList.getHeaderMenu());
        int mainMenuItem;
        do {
            IOUtil.printMenuItem(MenuContentList.getMainMenu());
            mainMenuItem = Integer.parseInt(IOUtil.getValidInputData("Select MAIN MENU item: ", InputData.INTEGER));

            switch (mainMenuItem) {
                case 0:
                    IOUtil.printMenuHeader(MenuContentList.getFooterMenu());
                    System.exit(0);
                    break;
                case 1:
                    initializationUser.loginUser();
                    if (currentUser != null) {
                        searchMenu();
                    }
                    break;
                case 2:
                    initializationUser.registrationUser();
                    if (currentUser != null) {
                        searchMenu();
                    }
                    break;
                default:
                    System.out.println("Error. Incorrect menu item.\n*********************************");
                    break;
            }
        } while (IOUtil.validateNumberSize(mainMenuItem, MenuContentList.getMainMenu().size()));
    }

    private void searchMenu() {
        int searchMenuItem = 0;
        long routeId = 0;
        long ticketId = 0;

        do {
            if (searchMenuItem == 0) {
                IOUtil.printMenuItem(MenuContentList.getSearchMenu());
                searchMenuItem = Integer.parseInt(IOUtil.getValidInputData("Select TICKET MENU item: ", InputData.INTEGER));
            }
            switch (searchMenuItem) {
                case 0:
                    return;
                case 1:
                    routeId = searchTicket();
                    searchMenuItem = (routeId != 0) ? 2 : 0;
                    break;
                case 2:
                    ticketId = buyTicket(routeId);
                    searchMenuItem = (ticketId != 0) ? 1 : 0;
                    break;
                case 3:
                    searchMenuItem = (returnTicket() != 0) ? 1 : 0;
                    break;
                case 4:
                    if (currentUser != null) {
                        initializationUser.logoutUser();
                    }
                    break;
                default:
                    System.out.println("Error. Incorrect menu item.\n************************************");
                    break;
            }
        } while (IOUtil.validateNumberSize(searchMenuItem, MenuContentList.getSearchMenu().size()));
    }

    private long searchTicket() {
        long cityDepartureId;
        long cityArrivalId;
        String departureCityName = null;
        String arrivalCityName = null;
        int numberPassengers = 0;
        TypeSeat typeSeat = null;
        LocalDate dateDeparture = null;
        List<Route> routes;

        while (true) {
            if (cityController.getAllCity()) {
                do {
                    cityDepartureId = Long.parseLong(IOUtil.getValidInputData(
                            "Select departure city id: ", InputData.INTEGER));
                    cityArrivalId = Long.parseLong(IOUtil.getValidInputData(
                            "Select arrival city id: ", InputData.INTEGER));
                    if (cityDepartureId == cityArrivalId) {
                        System.out.println("Departure and arrival cities should be different.");
                    }
                } while (cityDepartureId == cityArrivalId);
                departureCityName = cityController.getCityById(cityDepartureId);
                arrivalCityName = cityController.getCityById(cityArrivalId);

                String date = IOUtil.getValidInputData(
                        "Enter departure date in format \'dd.MM.yyyy\': ", InputData.DATE);
                dateDeparture = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                TypeSeat.printEnum(TypeSeat.values());
                int classSeat = Integer.parseInt(IOUtil.getValidInputData(
                        "Select ID class cabin: ", InputData.INTEGER));
                typeSeat = TypeSeat.getTypeById(classSeat);

                numberPassengers = Integer.parseInt(IOUtil.getValidInputData(
                        "Enter the number of passengers: ", InputData.INTEGER));
            }
            routes = routeController.getRouteByRequest(departureCityName, arrivalCityName,
                    dateDeparture, typeSeat, numberPassengers);
            if (routes.size() != 0) {
                long ticketId = Integer.parseInt(IOUtil.getValidInputData(
                        "To purchase, select a flight ID or enter \'0\' for a new search: ", InputData.INTEGER));
                if (ticketId != 0) {
                    return ticketId;
                }
            } else {
                System.out.println("For a given request, nothing found.");
                int result = Integer.parseInt(IOUtil.getValidInputData(
                        "Enter \'1\' for new search or \'0\' for return: ", InputData.INTEGER));
                if (result == 0) return result;
            }
        }
    }

    private long buyTicket(long routeId) {
        long reset = 0;
        while (true) {
            if (routeId == 0) {
                System.out.println("Please search route ID.");
                long selectId = Long.parseLong(IOUtil.getValidInputData(
                        "Enter \'1\' to find the flight or \'0\' to return: ", InputData.INTEGER));
                return selectId;
            } else {
                if (ticketController.getAllTicketsByRouteId(routeId) != null) {
                    int placeNumber = Integer.parseInt(IOUtil.getValidInputData(
                            "Select the place number or enter \'0\' to return: ", InputData.INTEGER));
                    if (placeNumber != 0) {
                        orderController.orderConfirm(routeId, placeNumber);
                        int order = Integer.parseInt(IOUtil.getValidInputData(
                                "Enter \'1\' to confirm the order or enter \'0\' to cancel: ", InputData.INTEGER));
                        if (order == 1) {
                            orderController.addOrder(ticketController.getTicketByPlaceNumber(routeId, placeNumber),
                                    routeController.getRouteById(routeId));
                            routeController.reduceSeat(routeId, placeNumber);
                            ticketController.deleteTicket(routeId, placeNumber);
                            System.out.println("You bought a ticket to seat number "
                                    + placeNumber + " for flight number " + routeId + ".\n\tThank you for your purchase.\n");
                            return reset;
                        } else {
                            return reset;
                        }
                    }
                    return placeNumber;
                }
            }
        }
    }

    private long returnTicket() {
        long reset = 0;
        while (true) {
            if (orderController.getAllOrderingTicket()) {
                long orderId = Long.parseLong(IOUtil.getValidInputData(
                        "Select the order number you would like to return or \'0\' to return: ", InputData.INTEGER));
                ticketController.addTicket(orderController.getTicketByOrderId(orderId));
                routeController.incrementTicket(orderController.getRouteIdByOrderId(orderId),
                        orderController.getTicketByOrderId(orderId));
                orderController.deleteOrder(orderId);
            } else {
                System.out.println("No tickets available for return.");
                return reset;
            }
            return reset;
        }
    }
}
