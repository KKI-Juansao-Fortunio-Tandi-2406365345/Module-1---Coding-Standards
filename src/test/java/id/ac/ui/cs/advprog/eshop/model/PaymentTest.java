package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentVoucherCodeSuccess() {
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_CODE", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedInvalidLength() {
        paymentData.put("voucherCode", "ESHOP12345678AB"); // 15 characters
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotStartWithEshop() {
        paymentData.put("voucherCode", "NOTHP12345678ABC");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotEightNumbers() {
        paymentData.put("voucherCode", "ESHOP1234567AABC"); // Only 7 numbers
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCodSuccess() {
        paymentData.put("address", "Jalan Liburan No. 5");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "CASH_ON_DELIVERY", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCodRejectedEmptyAddress() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCodRejectedEmptyFee() {
        paymentData.put("address", "Jalan Liburan No. 5");
        paymentData.put("deliveryFee", "");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "CRYPTO", paymentData);
        });
    }
}