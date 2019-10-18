package com.abseliamov.cinemaservice.service;

import com.abseliamov.cinemaservice.dao.TicketDaoImpl;
import com.abseliamov.cinemaservice.dao.ViewerDaoImpl;
import com.abseliamov.cinemaservice.model.Ticket;
import com.abseliamov.cinemaservice.model.Viewer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {
    private TicketDaoImpl ticketDao;
    private ViewerDaoImpl viewerDao;

    public TicketService(TicketDaoImpl ticketDao, ViewerDaoImpl viewerDao) {
        this.ticketDao = ticketDao;
        this.viewerDao = viewerDao;
    }

    public List<Ticket> getTicketByMovieTitle(String movieTitle) {
        List<Ticket> ticketList = ticketDao.getTicketByMovieTitle(movieTitle);
        printTicket(ticketList);
        return ticketList;
    }

    public List<Ticket> getTicketByGenre(long genreId) {
        List<Ticket> ticketList = ticketDao.getTicketByGenre(genreId);
        printTicket(ticketList);
        return ticketList;
    }

    public List<Ticket> getTicketBySeatType(long seatTypeId) {
        List<Ticket> ticketList = ticketDao.getTicketBySeatType(seatTypeId);
        printTicket(ticketList);
        return ticketList;
    }

    public Ticket getById(long ticketId) {
        Ticket ticket = ticketDao.getById(ticketId);
        if (ticket.getStatus() == 0) {
            List<Ticket> list = Arrays.asList(ticket);
            printTicket(list);
        } else ticket = null;
        return ticket;
    }

    public boolean buyTicket(long ticketId) {
        Ticket ticket = ticketDao.getById(ticketId);
        return ticketDao.buyTicket(ticket);
    }

    public List<Ticket> getAllTicketViewer() {
        List<Ticket> ticketList = ticketDao.getAllTicketViewer();
        printTicket(ticketList);
        return ticketList;
    }

    public Ticket getOrderedTicketById(long ticketId) {
        Ticket ticket = ticketDao.getOrderedTicketById(ticketId);
        List<Ticket> list = Arrays.asList(ticket);
        printTicket(list);
        return ticket;
    }

    public boolean returnTicket(long ticketId) {
        Ticket ticket = ticketDao.getOrderedTicketById(ticketId);
        return ticketDao.returnTicket(ticket);
    }

    public List<Ticket> getAllTicketByViewerId(long viewerId) {
        List<Ticket> ticketList = ticketDao.getAllTicketByViewerId(viewerId);
        Viewer viewer = viewerDao.getById(viewerId);
        if (ticketList.size() != 0) {
            System.out.println("List of tickets of a viewer with a name \'" + viewer.getName() + "\'.");
            printTicket(ticketList);
        } else {
            System.out.println("List of tickets of a viewer with a name \'" + viewer.getName() + "\' is empty.");
        }
        return ticketList;
    }

    public Map<LocalDate, Long> getAllDate() {
        Map<LocalDate, Long> dateMap = new TreeMap<>();
        List<Ticket> ticketList = ticketDao.getAllDate();
        if (ticketList != null) {
            for (Ticket ticket : ticketList) {
                dateMap.put(ticket.getDateTime().toLocalDate(), ticket.getId());
            }
            printAllDate(dateMap);
        } else {
            System.out.println("Date list is empty.");
        }
        return dateMap;
    }

    public List<Ticket> getAllTicketByDate(long ticketId) {
        List<Ticket> result = null;
        LocalDate date;
        List<Ticket> ticketList = ticketDao.getAll();
        Ticket ticket = ticketDao.getById(ticketId);
        if (ticketList != null && ticket != null) {
            date = ticket.getDateTime().toLocalDate();
            result = ticketList.stream()
                    .filter(ticketItem -> ticketItem.getDateTime().toLocalDate().equals(date))
                    .filter(ticketItem -> ticketItem.getStatus() == 0)
                    .collect(Collectors.toList());
            printTicket(result);
        } else {
            System.out.println("");
        }
        return result;
    }

    private void printAllDate(Map<LocalDate, Long> dateList) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter weekdayFormatter = DateTimeFormatter.ofPattern("EEEE").withLocale(Locale.ENGLISH);
        System.out.println("\n|------------------------------------|");
        System.out.printf("%-13s%-1s\n", " ", "LIST OF DATE");
        System.out.println("|------------------------------------|");
        System.out.printf("%-3s%-11s%-12s%-1s\n%-1s\n", " ", "ID", "DATE", "WEEKDAY",
                "|-------|-------------|--------------|");
        dateList.forEach((date, ticketId) -> System.out.printf("%-2s%-8s%-15s%-1s\n%-1s\n",
                " ", ticketId, date.format(dateFormatter), date.format(weekdayFormatter).toUpperCase(),
                "|-------|-------------|--------------|"));
    }

    public boolean checkTicketAvailable(long ticketId) {
        boolean ticketAvailable = false;
        Ticket ticket = ticketDao.getById(ticketId);
        if (ticket.getStatus() == 0) {
            ticketAvailable = true;
        } else {
            System.out.println("Ticket with id \'" + ticketId + "\' not available.\n" +
                    "Please try again.");
        }
        return ticketAvailable;
    }

    private void printTicket(List<Ticket> ticketList) {
        if (!ticketList.isEmpty()) {
            System.out.println("\n|--------------------------------------------------------------------" +
                    "--------------------------------------------------|");
            System.out.printf("%-55s%-1s\n", " ", "LIST OF TICKETS");
            System.out.println("|----------------------------------------------------------------------" +
                    "------------------------------------------------|");
            System.out.printf("%-3s%-15s%-29s%-17s%-12s%-9s%-12s%-15s%-1s\n",
                    " ", "ID", "MOVIE TITLE", "GENRE", "DATE", "TIME", "SEAT TYPE", "SEAT NUMBER", "PRICE");
            System.out.println("|-------|------------------------------|-------------------|------------|----------" +
                    "|-----------|-------------|---------|");
            ticketList.stream()
                    .sorted(Comparator.comparing(Ticket::getId))
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        } else {
            System.out.println("At your request tickets available is not found");
        }
    }
}
