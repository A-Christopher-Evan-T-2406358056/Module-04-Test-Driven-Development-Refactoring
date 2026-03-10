package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Order order = new Order("order-1", new ArrayList<>(List.of(new Product())), 1708560000L, "Christopher Evan Tanuwidjaja");
        payment = new Payment("pay-1", "VOUCHER", new HashMap<>(), order);
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);
        Payment found = paymentRepository.findById("pay-1");

        assertNotNull(found);
        assertEquals(payment.getId(), found.getId());
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);
        assertEquals(1, paymentRepository.getAllPayments().size());
    }
}