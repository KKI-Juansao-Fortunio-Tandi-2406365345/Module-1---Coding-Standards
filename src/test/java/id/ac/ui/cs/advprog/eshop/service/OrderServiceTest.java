package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).save(any(Order.class));

        Order result = orderService.createOrder(order);

        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testCreateOrderFailed() {
        Order order = orders.get(0);
        doReturn(null).when(orderRepository).save(any(Order.class));

        Order result = orderService.createOrder(order);

        verify(orderRepository, times(1)).save(any(Order.class));
        assertNull(result);
    }

    @Test
    void testUpdateStatus() {
        Order order = orders.get(0);
        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), "SUCCESS");
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(newOrder).when(orderRepository).save(any(Order.class));

        Order result = orderService.updateStatus(order.getId(), "SUCCESS");

        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateStatus(order.getId(), "MEOW");
        });

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testFindByIdSuccess() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());

        Order result = orderService.findById(order.getId());

        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        doReturn(null).when(orderRepository).findById("zec365c2-0631-4c7e-89ca-a200483512f1");

        Order result = orderService.findById("zec365c2-0631-4c7e-89ca-a200483512f1");

        assertNull(result);
    }

    @Test
    void testFindAllByAuthor() {
        doReturn(orders).when(orderRepository).findAllByAuthor("Juansao Fortunio Tandi");

        List<Order> result = orderService.findAllByAuthor("Juansao Fortunio Tandi");

        assertEquals(2, result.size());
    }
}