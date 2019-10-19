package com.abseliamov.cinemaservice.service;

import com.abseliamov.cinemaservice.dao.SeatDaoImpl;
import com.abseliamov.cinemaservice.model.Seat;

import java.util.List;

public class SeatService {
    private SeatDaoImpl seatDao;

    public SeatService(SeatDaoImpl seatDao) {
        this.seatDao = seatDao;
    }

    public List<Seat> getAll() {
        List<Seat> seats = seatDao.getAll();
        printSeats(seats);
        return seats;
    }

    public List<Seat> getAllSeatType() {
        List<Seat> seatTypes = seatDao.getAll();
        printSeatTypes(seatTypes);
        return seatTypes;
    }

    private void printSeats(List<Seat> seats) {
        if (seats.size() != 0) {
            System.out.println("|--------------------------|");
            System.out.printf("%-9s%-1s\n", " ", "LIST SEATS");
            System.out.println("|--------------------------|");
            System.out.printf("%-3s%-6s%-11s%-1s\n%-1s\n", " ", "ID", "NUMBER", "TYPE",
                    "|------|--------|----------|");
            seats.forEach(seat -> System.out.printf("%-3s%-8s%-7s%-1s\n%-1s\n",
                    " ", seat.getId(), seat.getNumber(), seat.getSeatTypes(),
                    "|------|--------|----------|"));
        }else {
            System.out.println("List seats is empty.");
        }
    }

    private void printSeatTypes(List<Seat> seats) {

    }
}
