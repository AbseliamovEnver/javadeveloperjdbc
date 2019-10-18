package com.abseliamov.cinemaservice.service;

import com.abseliamov.cinemaservice.dao.SeatDaoImpl;
import com.abseliamov.cinemaservice.model.Seat;

import java.util.List;

public class SeatService {
    private SeatDaoImpl seatDao;

    public SeatService(SeatDaoImpl seatDao) {
        this.seatDao = seatDao;
    }

    public List<Seat> getAllSeatType() {
        List<Seat> seatTypes = seatDao.getAll();
        printSeatTypes(seatTypes);
        return seatTypes;
    }

    private void printSeatTypes(List<Seat> seats){

    }
}
