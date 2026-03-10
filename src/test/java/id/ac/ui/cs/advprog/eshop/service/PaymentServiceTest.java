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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("order-1", new ArrayList<>(List.of(new Product())), 1708560000L, "Christopher Evan Tanuwidjaja");
    }

    @Test
    void testAddPayment() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertNotNull(payment);
        assertEquals("SUCCESS", payment.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = new Payment("pay-1", "VOUCHER", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).findById("pay-1");
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment updated = paymentService.setStatus(payment, "SUCCESS");

        assertNotNull(updated);
        assertEquals("SUCCESS", updated.getStatus());
        assertEquals("SUCCESS", updated.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Payment payment = new Payment("pay-1", "VOUCHER", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).findById("pay-1");
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment updated = paymentService.setStatus(payment, "REJECTED");

        assertNotNull(updated);
        assertEquals("REJECTED", updated.getStatus());
        assertEquals("FAILED", updated.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }
}