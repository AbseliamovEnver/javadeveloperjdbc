package com.abseliamov.cinemaservice.service;

import com.abseliamov.cinemaservice.dao.GenreDaoImpl;
import com.abseliamov.cinemaservice.model.GenericModel;
import com.abseliamov.cinemaservice.model.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenreService {
    private GenreDaoImpl genreDao;

    public GenreService(GenreDaoImpl genreDao) {
        this.genreDao = genreDao;
    }

    public void createGenre(String genreName) {
        Genre newGenre = null;
        List<Genre> genreList = genreDao.getAll();
        if (!genreList.isEmpty()) {
            newGenre = genreList
                    .stream()
                    .filter(genre -> genre.getName().equalsIgnoreCase(genreName))
                    .findFirst()
                    .orElse(null);
        }
        if (newGenre == null) {
            genreDao.add(new Genre(0, genreName));
            System.out.println("Genre with name \'" + genreName + "\' successfully added.");
        } else {
            System.out.println("Such a genre already exists.");
        }
    }

    public List<Genre> getAll() {
        List<Genre> genreList = genreDao.getAll();
        if (!genreList.isEmpty()) {
            List<Genre> sortedGenreList = genreList
                    .stream()
                    .sorted(Comparator.comparingLong(GenericModel::getId))
                    .collect(Collectors.toList());
            System.out.println("\n|------------------------|");
            System.out.printf("%-5s%-1s\n", " ", "LIST OF GENRES");
            System.out.println("|------------------------|");
            System.out.printf("%-3s%-9s%-1s\n", " ", "ID", "GENRE");
            System.out.println("|-------|----------------|");
            sortedGenreList.forEach(System.out::println);
        } else {
            System.out.println("Genre list is empty.");
        }
        return genreList;
    }
}
