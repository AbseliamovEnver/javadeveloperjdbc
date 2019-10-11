package com.abseliamov.flyticketservice.dao;

import com.abseliamov.flyticketservice.entity.Order;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderDao extends AbstractDao<Order> {
    private UserDao userDao;
    private TicketDao ticketDao;
    private CurrentUser currentUser;

    private final String FILE_PROPERTIES = "src/main/resources/properties/file.properties";
    private final String ORDER_DIRECTORY = "order.directory";
    //    private final String ORDERS_FILE_HEADER = "ORDER ID, TICKET";
    private final String COMMA_SEPARATOR = ",";

    public OrderDao(UserDao userDao, TicketDao ticketDao, CurrentUser currentUser) {
        this.userDao = userDao;
        this.ticketDao = ticketDao;
        this.currentUser = currentUser;
    }

    @Override
    public void create(Order order) {
        List<Order> orders = getAllOrders();
        long orderId = orders != null ? getId(orders) : 1;
        Order newOrder = Order.newOrderBuilder()
                .setId(orderId)
                .setRouteId(order.getRouteId())
                .setTicket(order.getTicket())
                .setDepartureCity(order.getDepartureCity())
                .setArrivalCity(order.getArrivalCity())
                .setDepartureTime(order.getDepartureTime())
                .setArrivalTime(order.getArrivalTime())
                .build();
        orders.add(newOrder);
        writeOrder(getOrderFile(), orders);
    }

    @Override
    public Order getById(long id) {
        return getAllOrders().stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Order order) {
        List<Order> orders = getAll();
        List<Order> updateList = new ArrayList<>();
        if (!orders.isEmpty()) {
            for (Order orderItem : orders) {
                if (orderItem.getId() == order.getId()) {
                    updateList.add(order);
                    continue;
                }
                updateList.add(orderItem);
            }
            writeToFile(updateList);
        } else {
            System.out.println("Error " + getOrderFile().getName() + " is empty.");
        }
    }

    @Override
    public void delete(Order order) {
        List<Order> orders = getAllOrders();
        List<Order> deleteList = new ArrayList<>();
        if (!orders.isEmpty()) {
            for (Order orderItem : orders) {
                if (orderItem.getId() == order.getId()) {
                    continue;
                }
                deleteList.add(orderItem);
            }
            writeToFile(deleteList);
        } else {
            System.out.println("Error " + getOrderFile().getName() + " is empty.");
        }
    }

    @Override
    Order parseDataFromFile(String[] orderData) {
        return null;
    }

    @Override
    StringBuilder buildDataToFile(List<Order> orders) {
        StringBuilder builder = new StringBuilder();
//        builder.append(ORDERS_FILE_HEADER);
        for (Order orderItem : orders) {
            builder.append("\n");
            builder.append(orderItem.getId());
            builder.append(COMMA_SEPARATOR);
            builder.append(orderItem.getTicket());
        }
        return builder;
    }

    public List<Order> getAll() {
        return getAll(getOrderFile(), "");
    }

    public void writeToFile(List<Order> orders) {
        writeToFile(getOrderFile(), orders);
    }

    long getId(List<Order> orders) {
        final long[] id = {1};
        orders.forEach(order -> {
            if (order.getId() >= id[0]) {
                id[0] = order.getId() + 1;
            }
        });
        return id[0];
    }

    private File getOrderFile() {
        Properties property = new Properties();
        try {
            property.load(new FileReader(FILE_PROPERTIES));
        } catch (IOException e) {
            System.out.println("Error load properties file " + e);
        }
        String regex = "[^A-Za-z0-9]";
        String userDirectory = property.getProperty(ORDER_DIRECTORY)
                + currentUser.getUser().getId() + "_"
                + currentUser.getUser().getFirstName().replaceAll(regex, "") + "_"
                + currentUser.getUser().getLastName().replaceAll(regex, "");

        File directory = new File(userDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File orderFile = new File(directory + "/purchased tickets.csv");
        if (!orderFile.exists()) {
            try {
                orderFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error create order file " + e);
            }
        }

        return orderFile;
    }

    public void writeOrder(File file, List<Order> orders) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file, true))) {
            writer.writeObject(orders);
        } catch (FileNotFoundException e) {
            System.out.println("Error write to order file " + e);
        } catch (IOException e) {
            System.out.println("Error IO write order file " + e);
        }
    }

    public List<Order> readOrderFile(File file) {
        List<Order> orders = new ArrayList<>();
        if (file.length() == 0) {
            return orders;
        } else {
            try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                orders = (List<Order>) reader.readObject();
//            Order order = (Order) reader.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("Error read to order file " + e);
            } catch (IOException e) {
                System.out.println("Error IO read order file " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        return readOrderFile(getOrderFile());
    }
}
