package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/addVoucher")
    public String addVoucherPage() { return null; }

    @GetMapping("/addCod")
    public String addCodPage() { return null; }

    @PostMapping("/add")
    public String addPayment() { return null; }
}