package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Getter;
import java.util.List;

@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this(id, products, orderTime, author, OrderStatus.WAITING_PAYMENT.getValue());
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.id = id;
            this.products = products;
            this.orderTime = orderTime;
            this.author = author;
            this.setStatus(status);
        }
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}