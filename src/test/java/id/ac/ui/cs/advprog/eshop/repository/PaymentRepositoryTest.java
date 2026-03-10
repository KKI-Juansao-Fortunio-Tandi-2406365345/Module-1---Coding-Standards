package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_CODE", paymentData);

        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment findResult = paymentRepository.findById("non-existent-id");
        assertNull(findResult);
    }
}