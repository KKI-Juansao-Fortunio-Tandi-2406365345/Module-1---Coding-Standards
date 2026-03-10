package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        orders = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Juansao Fortunio Tandi");
        orders.add(order1);

        Order order2 = new Order("7f9e15ad-87c1-4f81-90c6-d0947a772622",
                products, 1708560001L, "Juansao Fortunio Tandi");
        orders.add(order2);

        Order order3 = new Order("e3340255-6af8-4523-a2db-677a283332f3",
                products, 1708560002L, "Not Juansao");
        orders.add(order3);
    }

    @Test
    void testSaveSuccess() {
        Order order = orders.get(0);
        Order result = orderRepository.save(order);

        Order findResult = orderRepository.findById(orders.get(0).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getAuthor(), findResult.getAuthor());
    }

    @Test
    void testSaveUpdate() {
        Order order = orders.get(0);
        orderRepository.save(order);

        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), "SUCCESS");
        Order result = orderRepository.save(newOrder);

        Order findResult = orderRepository.findById(order.getId());
        assertEquals(order.getId(), result.getId());
        assertEquals("SUCCESS", findResult.getStatus());
    }

    @Test
    void testFindByIdSuccess() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(orders.get(1).getId(), findResult.getId());
    }

    @Test
    void testFindByIdNotFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order findResult = orderRepository.findById("non-existent-id");
        assertNull(findResult);
    }

    @Test
    void testFindAllByAuthor() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        List<Order> orderList = orderRepository.findAllByAuthor("Juansao Fortunio Tandi");
        assertEquals(2, orderList.size());
    }
}