package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.List;

public interface PaymentService {
    Payment addPayment(Payment payment);
    Payment setStatus(Payment payment, String status);
    Payment getPayment(String id);
    List<Payment> getAllPayments();
}