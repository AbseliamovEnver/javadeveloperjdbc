package com.abseliamov.cinemaservice.controller;

import com.abseliamov.cinemaservice.model.Movie;
import com.abseliamov.cinemaservice.service.MovieService;

import java.math.BigDecimal;
import java.util.List;

public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public boolean increaseCostMovie(BigDecimal ticketCost, Movie movie) {
        return movieService.increaseCostMovie(ticketCost, movie);
    }

    public boolean reduceCostMovie(BigDecimal ticketCost, Movie movie) {
        return movieService.reduceCostMovie(ticketCost, movie);
    }

    public void searchMostProfitableMovie(){
        movieService.searchMostProfitableMovie();
    }

    public void searchLeastProfitableMovie() {
        movieService.searchLeastProfitableMovie();
    }

    public List<Movie> getAll() {
        return movieService.getAll();
    }
}
