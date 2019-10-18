package com.abseliamov.cinemaservice.model;

public enum Role {
    ADMIN(1),
    USER(2);

    private long id;

    Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
