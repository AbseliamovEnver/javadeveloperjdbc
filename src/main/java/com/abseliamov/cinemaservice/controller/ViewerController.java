package com.abseliamov.cinemaservice.controller;

import com.abseliamov.cinemaservice.model.Viewer;
import com.abseliamov.cinemaservice.service.ViewerService;
import com.google.common.collect.Multimap;

import java.time.LocalDate;
import java.util.List;

public class ViewerController {
    private ViewerService viewerService;

    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    public boolean authorization(String name, String password) {
        return viewerService.authorization(name, password);
    }

    public List<Viewer> getAll() {
        return viewerService.getAll();
    }

    public List<Viewer> searchViewerMovieCountByGenre(long genreId) {
        return viewerService.searchViewerMovieCountByGenre(genreId);
    }

    public List<Viewer> searchViewersVisitingMovieInIntervalDaysFromBirthday() {
        return viewerService.searchViewersVisitingMovieInIntervalDaysFromBirthday();
    }

    public List<Viewer> searchViewerByComplexQuery(long genreId, double amount, List<LocalDate> dates) {
        return viewerService.searchViewerByComplexQuery(genreId, amount, dates);
    }

    public Multimap<String, Viewer> searchDateWithSeveralViewersBirthday() {
        return viewerService.searchDateWithSeveralViewersBirthday();
    }
}
