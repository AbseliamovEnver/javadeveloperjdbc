package com.abseliamov.cinemaservice.controller;

import com.abseliamov.cinemaservice.model.Genre;
import com.abseliamov.cinemaservice.service.GenreService;

import java.util.List;

public class GenreController {
    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    public List<Genre> getAll(){
        return genreService.getAll();
    }

    public void createGenre(String genreName) {
        genreService.createGenre(genreName);
    }
}
