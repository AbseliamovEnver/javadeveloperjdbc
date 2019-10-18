package com.abseliamov.cinemaservice.dao;

import com.abseliamov.cinemaservice.model.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDaoImpl extends AbstractDao<Genre> {

    public GenreDaoImpl(Connection connection, String tableName) {
        super(connection, tableName);
    }

    @Override
    public Genre createEntity(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
    }

    @Override
    public boolean update(long id, Genre item) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
