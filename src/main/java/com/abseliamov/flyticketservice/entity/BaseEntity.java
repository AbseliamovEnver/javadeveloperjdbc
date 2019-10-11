package com.abseliamov.flyticketservice.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseEntity implements Serializable {
    private long id;

    protected BaseEntity() {
        id = generateId();
    }

    private static long generateId() {
        long id = -1;
        do {
            id = UUID.randomUUID().getMostSignificantBits();
        } while (id < 0);
        return id;
    }
}
