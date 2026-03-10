package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        // Create a list with at least one dummy product to satisfy the Order validation
        List<Product> products = new ArrayList<>();
        Product dummyProduct = new Product();
        products.add(dummyProduct);

        Order order = new Order("placeholder-id", products, System.currentTimeMillis(), "Juansao", "WAITING_PAYMENT");
        model.addAttribute("order", order);
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String orderListPage(Model model) {
        List<Order> orders = orderService.findAllByAuthor("Juansao");
        model.addAttribute("orders", orders);
        return "orderList";
    }
}