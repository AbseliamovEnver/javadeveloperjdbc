package com.abseliamov.flyticketservice.controller;

import com.abseliamov.flyticketservice.entity.City;
import com.abseliamov.flyticketservice.service.CityService;
import com.abseliamov.flyticketservice.utils.CurrentUser;

public class CityController {
    private CityService cityService;
    private CurrentUser currentUser;

    public CityController(CityService cityService, CurrentUser currentUser) {
        this.cityService = cityService;
        this.currentUser = currentUser;
    }

    public void addCity(City city) {
        cityService.add(city);
    }

    public String getCityById(long id) {
        return cityService.getById(id).getName();
    }

    public boolean getAllCity() {
        return cityService.getAll() != null;
    }

    public void updateCity(City city) {
        cityService.update(city);
    }

    public void deleteCity(City city) {
        cityService.delete(city);
    }
}
