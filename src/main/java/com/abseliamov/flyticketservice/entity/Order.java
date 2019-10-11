package com.abseliamov.flyticketservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order extends BaseEntity implements Serializable {
    private long id;
    private long routeId;
    private Ticket ticket;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Order() {
    }

    public long getId() {
        return id;
    }

    public long getRouteId() {
        return routeId;
    }

    public Ticket getTicket() {
        return ticket;
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

    public static OrderBuilder newOrderBuilder() {
        return new Order.OrderBuilder();
    }

    public static class OrderBuilder {
        private Order orderInstance = new Order();

        private OrderBuilder() {

        }

        public OrderBuilder setId(long id) {
            orderInstance.id = id;
            return this;
        }

        public OrderBuilder setRouteId(long routeId){
            orderInstance.routeId = routeId;
            return this;
        }

        public OrderBuilder setTicket(Ticket ticket) {
            orderInstance.ticket = ticket;
            return this;
        }

        public OrderBuilder setDepartureCity(String departureCity) {
            orderInstance.departureCity = departureCity;
            return this;
        }

        public OrderBuilder setArrivalCity(String arrivalCity) {
            orderInstance.arrivalCity = arrivalCity;
            return this;
        }

        public OrderBuilder setDepartureTime(LocalDateTime departureTime) {
            orderInstance.departureTime = departureTime;
            return this;
        }

        public OrderBuilder setArrivalTime(LocalDateTime arrivalTime) {
            orderInstance.arrivalTime = arrivalTime;
            return this;
        }

        public Order build() {
            return orderInstance;
        }
    }
}
