package com.abseliamov.flyticketservice.entity;

import java.io.Serializable;

public enum TypeSeat implements Serializable {
    ECONOMY(1),
    BUSINESS(2),
    ECONOMY_AND_BUSINESS(3);

    private final int value;

    TypeSeat(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TypeSeat getTypeById(int value) {
        if (value == 1) return TypeSeat.ECONOMY;
        if (value == 2) return TypeSeat.BUSINESS;
        if (value == 3) return TypeSeat.ECONOMY_AND_BUSINESS;
        return null;
    }

    public static TypeSeat getTypeByName(String typeSeat) {
        return typeSeat.equals(BUSINESS.name()) ? BUSINESS : ECONOMY;
    }

    public static void printEnum(TypeSeat[] typeSeats) {
        System.out.println("*********************************");
        System.out.println("ID\tType seat");
        System.out.println("*********************************");
        for (TypeSeat type : typeSeats) {
            System.out.println(type.getValue() + ".\t" + type.name());
        }
        System.out.println("*********************************");
    }
}
