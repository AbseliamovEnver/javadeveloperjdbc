package com.abseliamov.flyticketservice.service;

import com.abseliamov.flyticketservice.dao.RouteDao;
import com.abseliamov.flyticketservice.entity.Route;
import com.abseliamov.flyticketservice.entity.Ticket;
import com.abseliamov.flyticketservice.entity.TypeSeat;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class RouteService implements ServiceInterface<Route> {
    private RouteDao routeDao;
    private TicketService ticketService;
    private CurrentUser currentUser;

    public RouteService(RouteDao routeDao, TicketService ticketService, CurrentUser currentUser) {
        this.routeDao = routeDao;
        this.ticketService = ticketService;
        this.currentUser = currentUser;
    }

    @Override
    public void add(Route route) {
        routeDao.create(route);
    }

    @Override
    public Route getById(long id) {
        return routeDao.getById(id);
    }

    @Override
    public List<Route> getAll() {
        return routeDao.getAll();
    }

    @Override
    public void update(Route route) {
        routeDao.update(route);
    }

    @Override
    public void delete(Route route) {
        routeDao.delete(route);
    }

    public List<Route> getRouteByRequest(String departureCityName, String arrivalCityName, LocalDate dateDeparture,
                                         TypeSeat typeSeat, int numberPassengers) {
        LocalDateTime startTimeDeparture;
        LocalDateTime endTimeDeparture;
        if (dateDeparture.equals(LocalDate.now())) {
            startTimeDeparture = LocalDateTime.now().plusHours(1);
        } else {
            startTimeDeparture = dateDeparture.atStartOfDay();
        }
        endTimeDeparture = startTimeDeparture.plusDays(1);
        List<Route> routes = routeDao.getAll().stream()
                .filter(route -> route.getDepartureCity().equals(departureCityName))
                .filter(route -> route.getArrivalCity().equals(arrivalCityName))
                .filter(route -> route.getDepartureTime().isAfter(startTimeDeparture))
                .filter(route -> route.getDepartureTime().isBefore(endTimeDeparture))
                .collect(Collectors.toList());
        if (typeSeat == TypeSeat.ECONOMY) {
            routes = routes.stream()
                    .filter(route -> route.getEconomyClassSeatCount() >= numberPassengers)
                    .collect(Collectors.toList());
        } else if (typeSeat == TypeSeat.BUSINESS) {
            routes = routes.stream()
                    .filter(route -> route.getBusinessClassSeatCount() >= numberPassengers)
                    .collect(Collectors.toList());
        }
        if (!routes.isEmpty()) {
            printRoutes(routes);
        }
        return routes;
    }

    private void printRoutes(List<Route> routes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-15s%-32s%-30s%-1s\n", " ", "CITY", "DATE", "CLASS PLACE");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-5s%-15s%-15s%-19s%-20s%-10s%-1s", "ID", "DEPARTURE", "ARRIVAL",
                "DEPARTURE", "ARRIVAL", "BUSINESS", "ECONOMY\n");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (Route route : routes) {
            System.out.printf("%-5d%-15s%-15s%-19s%-23s%-10d%-1d\n",
                    route.getId(),
                    route.getDepartureCity(),
                    route.getArrivalCity(),
                    route.getDepartureTime().format(formatter),
                    route.getArrivalTime().format(formatter),
                    route.getBusinessClassSeatCount(),
                    route.getEconomyClassSeatCount());
        }
        System.out.println("--------------------------------------------------------------------------------------------\n");
    }

    public void reduceSeat(long routeId, int ticketNumber) {
        Route route = routeDao.getById(routeId);
        Ticket ticket = ticketService.getTicketByPlaceNumber(routeId, ticketNumber);
        if (route != null) {
            if (ticket.getTypeSeat() == TypeSeat.BUSINESS) {
                route = Route.newBuilder()
                        .setId(routeId)
                        .setDepartureCity(route.getDepartureCity())
                        .setArrivalCity(route.getArrivalCity())
                        .setDepartureTime(route.getDepartureTime())
                        .setArrivalTime(route.getArrivalTime())
                        .setNumberBusinessClassSeat(route.getBusinessClassSeatCount() - 1)
                        .setNumberEconomyClassSeat(route.getEconomyClassSeatCount())
                        .build();
            } else if (ticket.getTypeSeat() == TypeSeat.ECONOMY) {
                route = Route.newBuilder()
                        .setId(routeId)
                        .setDepartureCity(route.getDepartureCity())
                        .setArrivalCity(route.getArrivalCity())
                        .setDepartureTime(route.getDepartureTime())
                        .setArrivalTime(route.getArrivalTime())
                        .setNumberBusinessClassSeat(route.getBusinessClassSeatCount())
                        .setNumberEconomyClassSeat(route.getEconomyClassSeatCount() - 1)
                        .build();
            }
        }
        routeDao.update(route);
    }

    public void incrementTicket(long routeId, Ticket ticket) {
        Route route = routeDao.getById(routeId);
        int businessSeat = 0;
        int economySeat = 0;
        if (ticket.getTypeSeat() == TypeSeat.BUSINESS) {
            businessSeat = 1;
        } else if (ticket.getTypeSeat() == TypeSeat.ECONOMY) {
            economySeat = 1;
        }
        Route updateRoute = Route.newBuilder()
                .setId(routeId)
                .setDepartureCity(route.getDepartureCity())
                .setArrivalCity(route.getArrivalCity())
                .setDepartureTime(route.getDepartureTime())
                .setArrivalTime(route.getArrivalTime())
                .setNumberBusinessClassSeat(route.getBusinessClassSeatCount() + businessSeat)
                .setNumberEconomyClassSeat(route.getEconomyClassSeatCount() + economySeat)
                .build();
        routeDao.update(updateRoute);
    }
}
