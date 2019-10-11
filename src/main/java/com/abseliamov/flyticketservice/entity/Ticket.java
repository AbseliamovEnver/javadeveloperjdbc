package com.abseliamov.flyticketservice.entity;

import java.io.Serializable;

public class Ticket extends BaseEntity implements Serializable {
    private long id;
    private long routeId;
    private Route route;
    private int placeNumber;
    private String location;
    private TypeSeat typeSeat;
    private String baggage;
    private double price;

    private Ticket() {
    }

    public long getId() {
        return id;
    }

    public long getRouteId() {
        return routeId;
    }

    public Route getRoute() {
        return route;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public String getLocation() {
        return location;
    }

    public TypeSeat getTypeSeat() {
        return typeSeat;
    }

    public String getBaggage() {
        return baggage;
    }

    public double getPrice() {
        return price;
    }

    public static TicketBuilder newBuilder() {
        return new Ticket().new TicketBuilder();
    }

    public class TicketBuilder {
        private TicketBuilder() {
        }

        public TicketBuilder setId(long id) {
            Ticket.this.id = id;
            return this;
        }

        public TicketBuilder setRouteId(long routeId) {
            Ticket.this.routeId = routeId;
            return this;
        }

        public TicketBuilder setRoute(Route route) {
            Ticket.this.route = route;
            return this;
        }

        public TicketBuilder setPlaceNumber(int placeNumber) {
            Ticket.this.placeNumber = placeNumber;
            return this;
        }

        public TicketBuilder setLocation(String location) {
            Ticket.this.location = location;
            return this;
        }

        public TicketBuilder setTypeSeat(TypeSeat typeSeat) {
            Ticket.this.typeSeat = typeSeat;
            return this;
        }

        public TicketBuilder setBaggage(String baggage) {
            Ticket.this.baggage = baggage;
            return this;
        }

        public TicketBuilder setPrice(double price) {
            Ticket.this.price = price;
            return this;
        }

        public Ticket build() {
            return Ticket.this;
        }
    }
}
