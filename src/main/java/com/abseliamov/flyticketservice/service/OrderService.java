package com.abseliamov.flyticketservice.service;

import com.abseliamov.flyticketservice.dao.OrderDao;
import com.abseliamov.flyticketservice.dao.RouteDao;
import com.abseliamov.flyticketservice.dao.TicketDao;
import com.abseliamov.flyticketservice.entity.Order;
import com.abseliamov.flyticketservice.entity.Route;
import com.abseliamov.flyticketservice.entity.Ticket;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderService implements ServiceInterface<Order> {
    private OrderDao orderDao;
    private TicketDao ticketDao;
    private RouteDao routeDao;
    private CurrentUser currentUser;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public OrderService(OrderDao orderDao, TicketDao ticketDao, RouteDao routeDao, CurrentUser currentUser) {
        this.orderDao = orderDao;
        this.ticketDao = ticketDao;
        this.routeDao = routeDao;
        this.currentUser = currentUser;
    }

    @Override
    public void add(Order order) {
        orderDao.create(order);
    }

    @Override
    public Order getById(long id) {
        return orderDao.getById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    public void orderConfirm(long routeId, int placeNumber) {
        Ticket ticket = ticketDao.getAll().stream()
                .filter(route -> route.getRouteId() == routeId)
                .filter(place -> place.getPlaceNumber() == placeNumber)
                .findFirst().get();
        Route route = routeDao.getById(routeId);
        System.out.println("|*******  PASSENGER INFORMATION  ******|");
        System.out.println("|-----------------|--------------------|");
        System.out.printf("  %-20s%-1s\n", "FIRST NAME", "LAST NAME");
        System.out.println("|-----------------|--------------------|");
        System.out.printf("  %-19s%-1s\n",
                currentUser.getUser().getFirstName(),
                currentUser.getUser().getLastName());
        System.out.println("|--------------------------------------|");

        System.out.println("\n|*****************************  FLIGHT INFORMATION  *****************************|");
        System.out.println("|--------|----------------|----------------|------------------|------------------|");
        System.out.printf("  %-9s%-18s%-17s%-19s%-1s", "FLIGHT", "DEPARTURE CITY", "ARRIVAL CITY",
                "DEPARTURE TIME", "ARRIVAL TIME\n");
        System.out.println("|--------|----------------|----------------|------------------|------------------|");
        System.out.printf("  %-9d%-18s%-16s%-18s%-1s\n",
                route.getId(),
                route.getDepartureCity(),
                route.getArrivalCity(),
                route.getDepartureTime().format(formatter),
                route.getArrivalTime().format(formatter));
        System.out.println("|--------------------------------------------------------------------------------|");

        System.out.println("\n|***************  PLACE INFORMATION  ****************|");
        System.out.println("|------|----------|----------|--------------|--------|");
        System.out.printf(" %-9s%-10s%-13s%-13s%-1s", "PLACE", "CLASS", "LOCATION",
                "BAGGAGE", "PRICE\n");
        System.out.println("|------|----------|----------|--------------|--------|");
        System.out.printf("  %-7d%-11s%-11s%-15s%.2f\n",
                ticket.getPlaceNumber(),
                ticket.getTypeSeat(),
                ticket.getLocation(),
                ticket.getBaggage(),
                ticket.getPrice());
        System.out.println("|----------------------------------------------------|\n");
    }

    public boolean getAllOrderingTicket() {
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            System.out.println("\n|*****************************  FLIGHT INFORMATION  *****************************|" +
                    "|***************  PLACE INFORMATION  ****************|");
            System.out.println("|--------|----------------|----------------|------------------|------------------|" +
                    "|------|----------|----------|--------------|--------|");
            System.out.printf("  %-9s%-18s%-17s%-19s%-18s%-9s%-10s%-13s%-13s%-1s", "ORDER ", "DEPARTURE CITY",
                    "ARRIVAL CITY", "DEPARTURE TIME", "ARRIVAL TIME", "PLACE", "CLASS", "LOCATION", "BAGGAGE", "PRICE\n");
            System.out.println("|--------|----------------|----------------|------------------|------------------|" +
                    "|------|----------|----------|--------------|--------|");
            System.out.printf("  %-9d%-18s%-16s%-19s%-19s %-7d%-11s%-11s%-15s%.2f\n",
                    order.getId(),
                    order.getDepartureCity(),
                    order.getArrivalCity(),
                    order.getDepartureTime().format(formatter),
                    order.getArrivalTime().format(formatter),
                    order.getTicket().getPlaceNumber(),
                    order.getTicket().getTypeSeat(),
                    order.getTicket().getLocation(),
                    order.getTicket().getBaggage(),
                    order.getTicket().getPrice());
            System.out.println("|--------------------------------------------------------------------------------|" +
                    "|----------------------------------------------------|\n");
        }
        return orderDao.getAllOrders().size() != 0;
    }

    public long getRouteIDByOrderId(long orderId) {
        Order order = orderDao.getById(orderId);
        return order.getRouteId();
    }

    public Ticket getTicketByOrderId(long orderId) {
        return orderDao.getById(orderId).getTicket();
    }
}
