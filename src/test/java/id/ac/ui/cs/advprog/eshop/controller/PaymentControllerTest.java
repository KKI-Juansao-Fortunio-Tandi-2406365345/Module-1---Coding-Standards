package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void testAddVoucherPage() throws Exception {
        mockMvc.perform(get("/payment/addVoucher").param("orderId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPaymentVoucher"))
                .andExpect(model().attributeExists("orderId"));
    }

    @Test
    void testAddCodPage() throws Exception {
        mockMvc.perform(get("/payment/addCod").param("orderId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPaymentCod"))
                .andExpect(model().attributeExists("orderId"));
    }

    @Test
    void testAddPaymentPost() throws Exception {
        mockMvc.perform(post("/payment/add")
                        .param("orderId", "1")
                        .param("method", "VOUCHER_CODE")
                        .param("paymentData[voucherCode]", "ESHOP12345678ABC"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/list"));

        verify(paymentService, times(1)).addPayment(any(Payment.class));
    }
}