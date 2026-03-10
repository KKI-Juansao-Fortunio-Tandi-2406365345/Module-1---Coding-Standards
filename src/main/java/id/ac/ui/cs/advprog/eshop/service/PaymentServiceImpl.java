package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);
        updateOrderStatus(savedPayment);
        return savedPayment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        payment.setStatus(status);
        Payment savedPayment = paymentRepository.save(payment);
        updateOrderStatus(savedPayment);
        return savedPayment;
    }

    private void updateOrderStatus(Payment payment) {
        String orderStatus = payment.getStatus().equals(PaymentStatus.SUCCESS.getValue()) ?
                OrderStatus.SUCCESS.getValue() : OrderStatus.FAILED.getValue();
        orderService.updateStatus(payment.getId(), orderStatus);
    }

    @Override
    public Payment getPayment(String id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        // Since we didn't implement a specific list method in the repo yet,
        // this can be implemented if you add a findAll() to PaymentRepository later.
        // For now, let's keep it simple.
        return null;
    }
}