package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // Skeleton - Methods are empty or returning null
    @GetMapping("/create")
    public String createOrderPage() { return null; }

    @PostMapping("/create")
    public String createOrderPost() { return null; }

    @GetMapping("/list")
    public String orderListPage() { return null; }
}