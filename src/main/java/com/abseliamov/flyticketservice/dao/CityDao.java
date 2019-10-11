package com.abseliamov.flyticketservice.dao;

import com.abseliamov.flyticketservice.entity.City;
import com.abseliamov.flyticketservice.utils.IOUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CityDao extends AbstractDao<City> {
    private File file = IOUtil.getFile("file.cities");
    private final String CITIES_FILE_HEADER = "ID, CITY NAME";
    private final String COMMA_SEPARATOR = ",";

    @Override
    public void create(City city) {
        boolean cityExist = false;
        long cityId = 1;
        List<City> cities = getAll();
        if (cities.size() == 0) {
            cities.add(new City(cityId, city.getName()));
            writeToFile(cities);
            cityExist = true;
        } else {
            for (City cityItem : cities) {
                if (cityItem.equals(city)) {
                    System.out.println("Such city already exists.");
                    cityExist = true;
                    break;
                }
            }
        }
        if (!cityExist) {
            long newId = getId(cities);
            cities.add(new City(newId, city.getName()));
            writeToFile(cities);
        }
    }

    @Override
    public City getById(long id) {
        return getAll().stream()
                .filter(city -> city.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(City city) {
        List<City> cities = getAll();
        List<City> updateList = new ArrayList<>();
        if (!cities.isEmpty()) {
            for (City cityItem : cities) {
                if (cityItem.getId() == city.getId()) {
                    updateList.add(city);
                    continue;
                }
                updateList.add(cityItem);
            }
            writeToFile(updateList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public void delete(City city) {
        List<City> cities = getAll();
        List<City> deleteList = new ArrayList<>();
        if (!cities.isEmpty()) {
            for (City cityItem : cities) {
                if (cityItem.getId() == city.getId()) {
                    continue;
                }
                deleteList.add(cityItem);
            }
            writeToFile(deleteList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public City parseDataFromFile(String[] cityData) {
        City city = new City(
                Long.parseLong(cityData[0]),
                cityData[1]);
        return city;
    }

    @Override
    public StringBuilder buildDataToFile(List<City> cities) {
        StringBuilder builder = new StringBuilder();
        builder.append(CITIES_FILE_HEADER);
        for (City cityItem : cities) {
            builder.append("\n");
            builder.append(cityItem.getId());
            builder.append(COMMA_SEPARATOR);
            builder.append(cityItem.getName());
        }
        return builder;
    }

    public City getCityByName(String cityName) {
        return getAll().stream()
                .filter(city -> city.getName().equals(cityName.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<City> getAll() {
        return getAll(file, CITIES_FILE_HEADER);
    }

    private void writeToFile(List<City> cities) {
        writeToFile(file, cities);
    }

    long getId(List<City> cities) {
        final long[] id = {1};
        cities.forEach(city -> {
            if (city.getId() >= id[0]) {
                id[0] = city.getId() + 1;
            }
        });
        return id[0];
    }
}