package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    @BeforeEach
    void setUp() {}

    @Test
    void testAddPaymentSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = new Payment("1", "VOUCHER_CODE", paymentData);

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.addPayment(payment);

        verify(paymentRepository, times(1)).save(payment);
        verify(orderService, times(1)).updateStatus(payment.getId(), "SUCCESS");
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID");
        Payment payment = new Payment("1", "VOUCHER_CODE", paymentData);

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.addPayment(payment);

        verify(paymentRepository, times(1)).save(payment);
        verify(orderService, times(1)).updateStatus(payment.getId(), "FAILED");
        assertEquals("REJECTED", result.getStatus());
    }
}