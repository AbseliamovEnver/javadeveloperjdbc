package com.abseliamov.flyticketservice.dao;

import com.abseliamov.flyticketservice.entity.Ticket;
import com.abseliamov.flyticketservice.entity.TypeSeat;
import com.abseliamov.flyticketservice.utils.CurrentUser;
import com.abseliamov.flyticketservice.utils.IOUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketDao extends AbstractDao<Ticket> {
    private CurrentUser currentUser;

    private File file = IOUtil.getFile("file.tickets");
    private final String TICKETS_FILE_HEADER = "ID, ROUTE ID, PLACE NUMBER, LOCATION, TYPE SEAT, BAGGAGE, PRICE";
    private final String COMMA_SEPARATOR = ",";

    public TicketDao(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void create(Ticket ticket) {
        boolean ticketExist = false;
        long ticketId = 1;
        List<Ticket> tickets = getAll();
        if (tickets.size() == 0) {
            tickets.add(Ticket.newBuilder()
                    .setId(ticketId)
                    .setRouteId(ticket.getRouteId())
                    .setPlaceNumber(ticket.getPlaceNumber())
                    .setTypeSeat(ticket.getTypeSeat())
                    .setLocation(ticket.getLocation())
                    .setBaggage(ticket.getBaggage())
                    .setPrice(ticket.getPrice())
                    .build());
            writeToFile(tickets);
            ticketExist = true;
        } else {
            for (Ticket ticketItem : tickets) {
                if (ticketItem.equals(ticket)) {
                    System.out.println("Such ticket already exists.");
                    ticketExist = true;
                    break;
                }
            }
        }
        if (!ticketExist) {
            long newId = (ticket.getId() == 0) ? getId(tickets) : ticket.getId();
            tickets.add(Ticket.newBuilder()
                    .setId(newId)
                    .setRouteId(ticket.getRouteId())
                    .setPlaceNumber(ticket.getPlaceNumber())
                    .setTypeSeat(ticket.getTypeSeat())
                    .setLocation(ticket.getLocation())
                    .setBaggage(ticket.getBaggage())
                    .setPrice(ticket.getPrice())
                    .build());
            writeToFile(tickets);
        }
    }

    @Override
    public Ticket getById(long id) {
        return getAll().stream()
                .filter(ticket -> ticket.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Ticket ticket) {
        List<Ticket> tickets = getAll();
        List<Ticket> updateList = new ArrayList<>();
        if (!tickets.isEmpty()) {
            for (Ticket ticketItem : tickets) {
                if (ticketItem.getId() == ticket.getId()) {
                    updateList.add(ticket);
                    continue;
                }
                updateList.add(ticketItem);
            }
            writeToFile(updateList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public void delete(Ticket ticket) {
        List<Ticket> tickets = getAll();
        List<Ticket> deleteList = new ArrayList<>();
        if (!tickets.isEmpty()) {
            for (Ticket ticketItem : tickets) {
                if (ticketItem.getId() == ticket.getId()) {
                    continue;
                }
                deleteList.add(ticketItem);
            }
            writeToFile(deleteList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public Ticket parseDataFromFile(String[] ticketData) {
        Ticket ticket = Ticket.newBuilder()
                .setId(Long.parseLong(ticketData[0]))
                .setRouteId(Long.parseLong(ticketData[1]))
                .setPlaceNumber(Integer.parseInt(ticketData[2]))
                .setTypeSeat(TypeSeat.getTypeByName(ticketData[3]))
                .setLocation(ticketData[4])
                .setBaggage(ticketData[5])
                .setPrice(Double.parseDouble(ticketData[6]))
                .build();
        return ticket;
    }

    @Override
    public StringBuilder buildDataToFile(List<Ticket> tickets) {
        StringBuilder builder = new StringBuilder();
        builder.append(TICKETS_FILE_HEADER);
        for (Ticket ticketItem : tickets) {
            builder.append("\n");
            builder.append(ticketItem.getId());
            builder.append(COMMA_SEPARATOR);
            builder.append(ticketItem.getRouteId());
            builder.append(COMMA_SEPARATOR);
            builder.append(ticketItem.getPlaceNumber());
            builder.append(COMMA_SEPARATOR);
            builder.append(ticketItem.getTypeSeat());
            builder.append(COMMA_SEPARATOR);
            builder.append(ticketItem.getLocation());
            builder.append(COMMA_SEPARATOR);
            builder.append(ticketItem.getBaggage());
            builder.append(COMMA_SEPARATOR);
            builder.append(ticketItem.getPrice());
        }
        return builder;
    }

    public List<Ticket> getAll() {
        return getAll(file, TICKETS_FILE_HEADER);
    }

    public Ticket getTicketByItem(String ticket) {
        String[] ticketId = ticket.split(",");
        return getById(Long.parseLong(ticketId[0].trim()));
    }

    public void writeToFile(List<Ticket> tickets) {
        writeToFile(file, tickets);
    }

    long getId(List<Ticket> tickets) {
        final long[] id = {1};
        tickets.forEach(ticket -> {
            if (ticket.getId() >= id[0]) {
                id[0] = ticket.getId() + 1;
            }
        });
        return id[0];
    }
}
