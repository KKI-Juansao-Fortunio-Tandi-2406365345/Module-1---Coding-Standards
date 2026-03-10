package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository for managing Payment data in memory.
 */
@Repository
public class PaymentRepository {
    private final Map<String, Payment> paymentData = new HashMap<>();

    public Payment save(Payment payment) {
        paymentData.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(String id) {
        return paymentData.get(id);
    }
}