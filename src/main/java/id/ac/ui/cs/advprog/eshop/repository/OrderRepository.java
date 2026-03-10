package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<String, Order> orderData = new HashMap<>();

    public Order save(Order order) {
        orderData.put(order.getId(), order);
        return order;
    }

    public Order findById(String id) {
        return orderData.get(id);
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> result = new ArrayList<>();
        for (Order order : orderData.values()) {
            if (order.getAuthor().equals(author)) {
                result.add(order);
            }
        }
        return result;
    }
}