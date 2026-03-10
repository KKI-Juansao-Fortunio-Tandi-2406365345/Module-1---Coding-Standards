package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/addVoucher")
    public String addVoucherPage(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "addPaymentVoucher";
    }

    @GetMapping("/addCod")
    public String addCodPage(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "addPaymentCod";
    }

    @PostMapping("/add")
    public String addPayment(@RequestParam String orderId,
                             @RequestParam String method,
                             @RequestParam Map<String, String> allRequestParams) {
        // Spring packs all params into the map; we just need the paymentData parts
        Payment payment = new Payment(orderId, method, allRequestParams);
        paymentService.addPayment(payment);
        return "redirect:/order/list";
    }
}