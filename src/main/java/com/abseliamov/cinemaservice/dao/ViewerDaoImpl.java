package com.abseliamov.cinemaservice.dao;

import com.abseliamov.cinemaservice.exceptions.ConnectionException;
import com.abseliamov.cinemaservice.model.Role;
import com.abseliamov.cinemaservice.model.Viewer;
import com.abseliamov.cinemaservice.utils.ConnectionUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ViewerDaoImpl extends AbstractDao<Viewer> {
    private static final String ERROR_MESSAGE = "Cannot connect to database: ";
    Connection connection = ConnectionUtil.getConnection();

    public ViewerDaoImpl(Connection connection, String tableName) {
        super(connection, tableName);
    }

    @Override
    public void add(Viewer viewer) {
        try (PreparedStatement statement = connection
                .prepareStatement("INSERT INTO viewers VALUES(?,?,?,?,?,?)")) {
            statement.setLong(1, viewer.getId());
            statement.setString(2, viewer.getName());
            statement.setString(3, viewer.getLastName());
            statement.setString(4, viewer.getPassword());
            statement.setDate(5, Date.valueOf(viewer.getBirthday()));
            statement.setLong(6, viewer.getRole().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ERROR_MESSAGE + e);
        }
    }

    @Override
    public Viewer convertToEntity(ResultSet resultSet) throws SQLException {
        Role role = null;
        for (Role roleItem : Role.values()) {
            if (roleItem.getId() == resultSet.getLong("role_id")) {
                role = roleItem;
            }
        }
        return new Viewer(resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("password"),
                role,
                resultSet.getDate("birthday").toLocalDate());
    }

    public Viewer checkUserAuthorization(String name, String password) {
        return getAll().stream()
                .filter(viewer -> viewer.getName().equals(name))
                .filter(viewer -> viewer.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(long id, Viewer viewer) {
        try (PreparedStatement statement = connection
                .prepareStatement("UPDATE viewers SET first_name = ?, last_name = ?, password = ?, " +
                        "birthday = ?, role_id = ? WHERE id = ?")) {
            statement.setString(1, viewer.getName().trim());
            statement.setString(2, viewer.getLastName().trim());
            statement.setString(3, viewer.getPassword().trim());
            statement.setDate(4, Date.valueOf(viewer.getBirthday()));
            statement.setLong(5, viewer.getRole().getId());
            statement.setLong(6, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ERROR_MESSAGE + e);
            throw new ConnectionException(e);
        }
        return true;
    }

    public List<Viewer> searchViewerMovieCountByGenre(long genreId) {
        List<Viewer> viewers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("" +
                "SELECT viewers.id, viewers.first_name, viewers.last_name, viewers.birthday, COUNT(buy_status) FROM (" +
                "    SELECT buy_status FROM tickets WHERE (QUARTER(date_time) = QUARTER(CURDATE())) " +
                "      AND movie_id IN (SELECT id FROM movies WHERE  genre_id = ?)" +
                "      AND buy_status <> 0 " +
                "      GROUP BY buy_status HAVING COUNT(buy_status) > 6) AS filter " +
                "INNER JOIN viewers " +
                "   ON viewers.id = filter.buy_status")) {
            statement.setLong(1, genreId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getLong("id") != 0) {
                    viewers.add(createMovieByRequest(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewers;
    }

    public List<Viewer> searchViewersVisitingMovieInIntervalDaysFromBirthday() {
        List<Viewer> viewers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("" +
                     "SELECT viewers.id, viewers.first_name, viewers.last_name, viewers.birthday " +
                     "FROM viewers " +
                     "INNER JOIN tickets ON viewers.id = tickets.buy_status " +
                     "    AND date_time >= CURRENT_TIME AND buy_status <> 0 " +
                     "    AND (DAYOFYEAR(date_time) BETWEEN (DAYOFYEAR(birthday) - 3) AND DAYOFYEAR(birthday) " +
                     "        OR DAYOFYEAR(date_time) BETWEEN DAYOFYEAR(birthday) AND (DAYOFYEAR(birthday) + 3 ))")) {
            while (resultSet.next()) {
                if (resultSet.getLong("id") != 0) {
                    viewers.add(createMovieByRequest(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewers;
    }

    public List<Viewer> searchViewerByComplexQuery(long genreId, double amount, List<LocalDate> dates) {
        List<Viewer> viewers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("" +
                " SELECT viewers.id, viewers.first_name, viewers.last_name, viewers.birthday FROM ( " +
                "SELECT buy_status FROM tickets WHERE movie_id IN (SELECT id FROM movies WHERE  genre_id = ?) " +
                "AND buy_status <> 0 " +
                "AND date_time BETWEEN ? AND ? " +
                "GROUP BY buy_status HAVING SUM(price) > ?) AS filter " +
                "INNER JOIN viewers " +
                "    ON viewers.id = filter.buy_status;")) {
            statement.setLong(1, genreId);
            statement.setDate(2, Date.valueOf(dates.get(0)));
            statement.setDate(3, Date.valueOf(dates.get(1)));
            statement.setDouble(4, amount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getLong("id") != 0) {
                    viewers.add(createMovieByRequest(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewers;
    }

    public Multimap<String, Viewer> searchDateWithSeveralViewersBirthday() {
        Multimap<String, Viewer> dateListMap = ArrayListMultimap.create();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("" +
                     " SELECT *, COUNT(*), DATE_FORMAT(birthday, '%m-%d') AS date_month FROM viewers " +
                     " GROUP BY date_month HAVING COUNT(date_month) > 5 " +
                     " ")) {
            while (resultSet.next()) {
                if (resultSet.getLong("id") != 0) {
                    dateListMap.put(resultSet.getDate("birthday").toLocalDate()
                                    .format(DateTimeFormatter.ofPattern("EEEE d MMMM")).toUpperCase(),
                            createMovieByRequest(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateListMap;
    }

    private Viewer createMovieByRequest(ResultSet resultSet) throws SQLException {
        return new Viewer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getDate("birthday").toLocalDate());
    }
}
