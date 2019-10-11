package com.abseliamov.flyticketservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Route extends BaseEntity implements Serializable {
    private long id;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int businessClassSeatCount;
    private int economyClassSeatCount;

    private Route() {
    }

    public long getId() {
        return id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public int getBusinessClassSeatCount() {
        return businessClassSeatCount;
    }

    public int getEconomyClassSeatCount() {
        return economyClassSeatCount;
    }

    public static RouteBuilder newBuilder() {
        return new Route().new RouteBuilder();
    }

    public class RouteBuilder {
        private RouteBuilder() {
        }

        public RouteBuilder setId(long routeId) {
            Route.this.id = routeId;
            return this;
        }

        public RouteBuilder setDepartureCity(String departureCity) {
            Route.this.departureCity = departureCity;
            return this;
        }

        public RouteBuilder setArrivalCity(String arrivalCity) {
            Route.this.arrivalCity = arrivalCity;
            return this;
        }

        public RouteBuilder setDepartureTime(LocalDateTime departureTime) {
            Route.this.departureTime = departureTime;
            return this;
        }

        public RouteBuilder setArrivalTime(LocalDateTime arrivalTime) {
            Route.this.arrivalTime = arrivalTime;
            return this;
        }

        public RouteBuilder setNumberBusinessClassSeat(int numberBusinessClassSeat) {
            Route.this.businessClassSeatCount = numberBusinessClassSeat;
            return this;
        }

        public RouteBuilder setNumberEconomyClassSeat(int numberEconomyClassSeat) {
            Route.this.economyClassSeatCount = numberEconomyClassSeat;
            return this;
        }

        public Route build() {
            return Route.this;
        }
    }
}
