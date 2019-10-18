package com.abseliamov.cinemaservice.dao;

import com.abseliamov.cinemaservice.model.Seat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatDaoImpl extends AbstractDao<Seat> {
    private SeatTypesDao seatTypesDao;

    public SeatDaoImpl(Connection connection, SeatTypesDao seatTypesDao, String tableName) {
        super(connection, tableName);
        this.seatTypesDao = seatTypesDao;
    }

    @Override
    public Seat createEntity(ResultSet resultSet) throws SQLException {
        return new Seat(
                resultSet.getLong("id"),
                seatTypesDao.getById(resultSet.getLong("seat_type_id")),
                resultSet.getLong("number"));
    }

    @Override
    public boolean update(long id, Seat item) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
