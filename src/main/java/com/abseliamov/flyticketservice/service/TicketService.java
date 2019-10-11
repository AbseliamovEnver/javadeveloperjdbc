package com.abseliamov.flyticketservice.service;

import com.abseliamov.flyticketservice.dao.RouteDao;
import com.abseliamov.flyticketservice.dao.TicketDao;
import com.abseliamov.flyticketservice.entity.Route;
import com.abseliamov.flyticketservice.entity.Ticket;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TicketService implements ServiceInterface<Ticket> {
    TicketDao ticketDao;
    RouteDao routeDao;
    CurrentUser currentUser;

    public TicketService(TicketDao ticketDao, RouteDao routeDao, CurrentUser currentUser) {
        this.ticketDao = ticketDao;
        this.routeDao = routeDao;
        this.currentUser = currentUser;
    }

    @Override
    public void add(Ticket ticket) {
        ticketDao.create(ticket);
    }

    @Override
    public Ticket getById(long id) {
        return ticketDao.getById(id);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    @Override
    public void update(Ticket ticket) {
        ticketDao.update(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public List<Ticket> getAllTicketsByRouteId(long routeId) {
        Route route = routeDao.getById(routeId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        System.out.println("\n|----------------------------------------------------------------------------|");
        System.out.printf("  %-5s%-17s%-18s%-20s%-1s", "ID", "DEPARTURE CITY", "ARRIVAL CITY",
                "DEPARTURE TIME", "ARRIVAL TIME\n");
        System.out.println("|----|----------------|----------------|------------------|------------------|");
        System.out.printf("  %-5d%-17s%-17s%-19s%-1s\n",
                route.getId(),
                route.getDepartureCity(),
                route.getArrivalCity(),
                route.getDepartureTime().format(formatter),
                route.getArrivalTime().format(formatter));
        System.out.println("|----------------------------------------------------------------------------|");

        List<Ticket> tickets = ticketDao.getAll().stream()
                .filter(ticket -> ticket.getRouteId() == routeId)
                .collect(Collectors.toList());

        printTicket(tickets);
        return tickets;
    }

    public void printTicket(List<Ticket> tickets) {
        System.out.println("\n|----------------------------------------------------|");
        System.out.printf(" %-9s%-10s%-13s%-13s%-1s", "PLACE", "CLASS", "LOCATION",
                "BAGGAGE", "PRICE\n");
        System.out.println("|------|----------|----------|--------------|--------|");
        for (Ticket ticketItem : tickets) {
            System.out.printf("  %-7d%-11s%-11s%-15s%.2f\n",
                    ticketItem.getPlaceNumber(),
                    ticketItem.getTypeSeat(),
                    ticketItem.getLocation(),
                    ticketItem.getBaggage(),
                    ticketItem.getPrice());
            System.out.println("|------|----------|----------|--------------|--------|");
        }
        System.out.println("|----------------------------------------------------|\n");
    }

    public Ticket getTicketByPlaceNumber(long routeId, int placeNumber) {
        return ticketDao.getAll().stream()
                .filter(ticketRouteId -> ticketRouteId.getRouteId() == routeId)
                .filter(ticketPlaceName -> ticketPlaceName.getPlaceNumber() == placeNumber)
                .findFirst().get();
    }
}
