package com.abseliamov.flyticketservice.service;

import com.abseliamov.flyticketservice.dao.CityDao;
import com.abseliamov.flyticketservice.entity.City;

import java.util.List;

public class CityService implements ServiceInterface<City> {
    private CityDao cityDao;

    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public void add(City city) {
        cityDao.create(city);
    }

    @Override
    public City getById(long id) {
        return cityDao.getById(id);
    }

    @Override
    public List<City> getAll() {
        List<City> cities = cityDao.getAll();
        if (!cities.isEmpty()) {
            System.out.println("*********************************");
            System.out.println("ID\tCITY");
            System.out.println("*********************************");
            for (City city : cities) {
                System.out.println(city.getId() + ".\t" + city.getName());
            }
            System.out.println("*********************************");
        } else {
            System.out.println("List cities is empty.");
        }
        return cities;
    }

    @Override
    public void update(City city) {
        cityDao.update(city);
    }

    @Override
    public void delete(City city) {
        cityDao.delete(city);
    }
}
