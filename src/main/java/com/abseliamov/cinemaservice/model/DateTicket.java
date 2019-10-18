package com.abseliamov.cinemaservice.model;

public class DateTicket extends GenericModel {
//    private LocalDate date;
//
//    public DateTicket(long id, LocalDate date) {
//        super(id);
//        this.date = date;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        DateTicket that = (DateTicket) o;
//
//        return date != null ? date.equals(that.date) : that.date == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return date != null ? date.hashCode() : 0;
//    }
//
//    @Override
//    public String toString() {
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        DateTimeFormatter weekdayFormatter = DateTimeFormatter.ofPattern("EEEE").withLocale(Locale.ENGLISH);
//        return String.format("%-2s%-8s%-15s%-1s\n%-1s",
//                " ", getId(), getDate().format(dateFormatter), getDate().format(weekdayFormatter).toUpperCase(),
//                "|-------|-------------|--------------|");
//    }
}
