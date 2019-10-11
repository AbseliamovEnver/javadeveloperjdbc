package com.abseliamov.flyticketservice.service;

import java.util.List;

public interface ServiceInterface<T> {

    void add(T item);

    T getById(long id);

    List<T> getAll();

    void update(T item);

    void delete(T item);
}
