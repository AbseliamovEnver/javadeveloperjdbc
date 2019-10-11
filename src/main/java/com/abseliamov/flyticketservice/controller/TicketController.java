package com.abseliamov.flyticketservice.controller;

import com.abseliamov.flyticketservice.entity.Ticket;
import com.abseliamov.flyticketservice.service.TicketService;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.util.List;

public class TicketController {
    private TicketService ticketService;
    private CurrentUser currentUser;

    public TicketController(TicketService ticketService, CurrentUser currentUser) {
        this.ticketService = ticketService;
        this.currentUser = currentUser;
    }

    public void addTicket(Ticket ticket) {
        ticketService.add(ticket);
    }

    public Ticket getTicketById(long id) {
        return ticketService.getById(id);
    }

    public List<Ticket> getAllTicket() {
        return ticketService.getAll();
    }

    public void updateTicket(Ticket ticket) {
        ticketService.update(ticket);
    }

    public void deleteTicket(long routeId, int ticketNumber) {
        Ticket ticket = ticketService.getTicketByPlaceNumber(routeId, ticketNumber);
        ticketService.delete(ticket);
    }

    public List<Ticket> getAllTicketsByRouteId(long routeId) {
        return ticketService.getAllTicketsByRouteId(routeId);
    }

    public void printTicket(List<Ticket> tickets) {
        ticketService.printTicket(tickets);
    }

    public Ticket getTicketByPlaceNumber(long routeId, int placeNumber) {
        return ticketService.getTicketByPlaceNumber(routeId, placeNumber);
    }
}
