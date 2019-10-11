package com.abseliamov.flyticketservice.dao;

import com.abseliamov.flyticketservice.entity.Route;
import com.abseliamov.flyticketservice.utils.IOUtil;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RouteDao extends AbstractDao<Route> {
    private CityDao cityDao;

    private File file = IOUtil.getFile("file.routes");
    private final String ROUTES_FILE_HEADER = "ID, DEPARTURE CITY, ARRIVAL CITY, " +
            "DEPARTURE DATE, ARRIVAL DATE, BUSINESS CLASS, ECONOMY CLASS";
    private final String COMMA_SEPARATOR = ",";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public RouteDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public void create(Route route) {

        boolean routeExist = false;
        long routeId = 1;
        List<Route> routes = getAll();
        if (routes.size() == 0) {
            routes.add(Route.newBuilder()
                    .setId(routeId)
                    .setDepartureCity(route.getDepartureCity())
                    .setArrivalCity(route.getArrivalCity())
                    .setDepartureTime(route.getDepartureTime())
                    .setArrivalTime(route.getArrivalTime())
                    .setNumberBusinessClassSeat(route.getBusinessClassSeatCount())
                    .setNumberEconomyClassSeat(route.getEconomyClassSeatCount())
                    .build());
            writeToFile(routes);
            routeExist = true;
        } else {
            for (Route routeItem : routes) {
                if (routeItem.equals(route)) {
                    System.out.println("Such route already exists.");
                    routeExist = true;
                    break;
                }
            }
        }
        if (!routeExist) {
            long newId = getId(routes);
            routes.add(Route.newBuilder()
                    .setId(newId)
                    .setDepartureCity(route.getDepartureCity())
                    .setArrivalCity(route.getArrivalCity())
                    .setDepartureTime(route.getDepartureTime())
                    .setArrivalTime(route.getArrivalTime())
                    .setNumberBusinessClassSeat(route.getBusinessClassSeatCount())
                    .setNumberEconomyClassSeat(route.getEconomyClassSeatCount())
                    .build());
            writeToFile(routes);
        }
    }

    @Override
    public Route getById(long id) {
        return getAll().stream()
                .filter(route -> route.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Route route) {
        List<Route> routes = getAll();
        List<Route> updateList = new ArrayList<>();
        if (!routes.isEmpty()) {
            for (Route routeItem : routes) {
                if (routeItem.getId() == route.getId()) {
                    updateList.add(route);
                    continue;
                }
                updateList.add(routeItem);
            }
            writeToFile(updateList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public void delete(Route route) {
        List<Route> routes = getAll();
        List<Route> deleteList = new ArrayList<>();
        if (!routes.isEmpty()) {
            for (Route routeItem : routes) {
                if (routeItem.getId() == route.getId()) {
                    continue;
                }
                deleteList.add(routeItem);
            }
            writeToFile(deleteList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    Route parseDataFromFile(String[] routeData) {
        Route route = Route.newBuilder()
                .setId(Long.parseLong(routeData[0]))
                .setDepartureCity(routeData[1].trim())
                .setArrivalCity(routeData[2].trim())
                .setDepartureTime(parseDate(routeData[3]))
                .setArrivalTime(parseDate(routeData[4]))
                .setNumberBusinessClassSeat(Integer.parseInt(routeData[5]))
                .setNumberEconomyClassSeat(Integer.parseInt(routeData[6]))
                .build();
        return route;
    }

    @Override
    StringBuilder buildDataToFile(List<Route> routes) {
        StringBuilder builder = new StringBuilder();
        builder.append(ROUTES_FILE_HEADER);
        for (Route routeItem : routes) {
            builder.append("\n");
            builder.append(routeItem.getId());
            builder.append(COMMA_SEPARATOR);
            builder.append(routeItem.getDepartureCity());
            builder.append(COMMA_SEPARATOR);
            builder.append(routeItem.getArrivalCity());
            builder.append(COMMA_SEPARATOR);
            builder.append(routeItem.getDepartureTime().format(formatter));
            builder.append(COMMA_SEPARATOR);
            builder.append(routeItem.getArrivalTime().format(formatter));
            builder.append(COMMA_SEPARATOR);
            builder.append(routeItem.getBusinessClassSeatCount());
            builder.append(COMMA_SEPARATOR);
            builder.append(routeItem.getEconomyClassSeatCount());
        }
        return builder;
    }

    public List<Route> getAll() {
        return getAll(file, ROUTES_FILE_HEADER);
    }

    public Route getRouteByItem(String route) {
        String[] routeId = route.split(",");
        return getById(Long.parseLong(routeId[0].trim()));
    }

    public void writeToFile(List<Route> routes) {
        writeToFile(file, routes);
    }

    long getId(List<Route> routes) {
        final long[] id = {1};
        routes.forEach(route -> {
            if (route.getId() >= id[0]) {
                id[0] = route.getId() + 1;
            }
        });
        return id[0];
    }
}
