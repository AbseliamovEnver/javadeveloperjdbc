package com.abseliamov.flyticketservice.controller;

import com.abseliamov.flyticketservice.entity.Order;
import com.abseliamov.flyticketservice.entity.Route;
import com.abseliamov.flyticketservice.entity.Ticket;
import com.abseliamov.flyticketservice.service.OrderService;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.util.List;

public class OrderController {
    private OrderService orderService;
    private CurrentUser currentUser;

    public OrderController(OrderService orderService, CurrentUser currentUser) {
        this.orderService = orderService;
        this.currentUser = currentUser;
    }

    public void addOrder(Ticket ticket, Route route) {
        Order order = Order.newOrderBuilder()
                .setId(0)
                .setRouteId(route.getId())
                .setTicket(ticket)
                .setDepartureCity(route.getDepartureCity())
                .setArrivalCity(route.getArrivalCity())
                .setDepartureTime(route.getDepartureTime())
                .setArrivalTime(route.getArrivalTime())
                .build();
        orderService.add(order);
    }

    public Order getOrderById(long id) {
        return orderService.getById(id);
    }

    public List<Order> getAllOrder() {
        return orderService.getAll();
    }

    public void updateOrder(Order order) {
        orderService.update(order);
    }

    public void deleteOrder(long orderId) {
        orderService.delete(orderService.getById(orderId));
    }

    public void orderConfirm(long routeId, int placeNumber) {
        orderService.orderConfirm(routeId, placeNumber);
    }

    public boolean getAllOrderingTicket() {
        return orderService.getAllOrderingTicket();
    }

    public long getRouteIdByOrderId(long orderId) {
        return orderService.getRouteIDByOrderId(orderId);
    }

    public Ticket getTicketByOrderId(long orderId) {
        return orderService.getTicketByOrderId(orderId);
    }
}
