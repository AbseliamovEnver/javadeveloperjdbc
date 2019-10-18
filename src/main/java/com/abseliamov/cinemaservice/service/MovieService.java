package com.abseliamov.cinemaservice.service;

import com.abseliamov.cinemaservice.dao.MovieDaoImpl;
import com.abseliamov.cinemaservice.model.Movie;

import java.math.BigDecimal;
import java.util.List;

public class MovieService {
    private MovieDaoImpl movieDao;

    public MovieService(MovieDaoImpl movieDao) {
        this.movieDao = movieDao;
    }

    public boolean increaseCostMovie(BigDecimal ticketCost, Movie movie) {
        long movieId = movie.getId();
        Movie newMovie = new Movie(movieId, movie.getName(), movie.getGenre(), (movie.getCost()).add(ticketCost));
        return movieDao.update(movieId, newMovie);
    }

    public boolean reduceCostMovie(BigDecimal ticketCost, Movie movie) {
        long movieId = movie.getId();
        Movie newMovie = new Movie(movieId, movie.getName(), movie.getGenre(), movie.getCost().subtract(ticketCost));
        return movieDao.update(movieId, newMovie);
    }

    public List<Movie> searchMostProfitableMovie() {
        List<Movie> movies = movieDao.searchMostProfitableMovie();
        printMovieByRequest(movies);
        return movies;
    }

    public List<Movie> searchLeastProfitableMovie() {
        List<Movie> movies = movieDao.searchLeastProfitableMovie();
        printMovieByRequest(movies);
        return movies;
    }

    private void printMovieByRequest(List<Movie> movies) {
        if (movies.size() != 0) {
            System.out.println("|-------------------------------------------------|");
            System.out.printf("%-19s%-1s\n", " ", "REQUEST RESULT");
            System.out.println("|-------------------------------------------------|");
            System.out.printf("%-3s%-13s%-23s%-1s\n%-1s\n", " ", "ID", "MOVIE TITLE", "TOTAL COST",
                    "|------|-----------------------------|------------|");
            movies.forEach(movie -> System.out.printf("%-3s%-6s%-32s%-1s\n%-1s\n",
                    " ", movie.getId(), movie.getName(), movie.getCost(),
                    "|------|-----------------------------|------------|"));
        }else {
            System.out.println("At your request a movie is not found");
        }
    }
}
