package com.abseliamov.cinemaservice.controller;

import com.abseliamov.cinemaservice.model.Seat;
import com.abseliamov.cinemaservice.service.SeatService;

import java.util.List;

public class SeatController {
    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    public List<Seat> getAllSeatType() {
        return seatService.getAllSeatType();
    }

    public List<Seat> getAll() {
        return seatService.getAll();
    }
}
