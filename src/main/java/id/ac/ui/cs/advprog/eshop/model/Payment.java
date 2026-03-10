package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    @Setter
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;

        if ("VOUCHER".equals(method)) {
            String voucher = paymentData.get("voucherCode");
            if (voucher != null && voucher.length() == 16 && voucher.startsWith("ESHOP")) {
                long digitCount = voucher.chars().filter(Character::isDigit).count();
                this.status = (digitCount == 8) ? "SUCCESS" : "REJECTED";
            } else {
                this.status = "REJECTED";
            }
        } else if ("BANK_TRANSFER".equals(method)) {
            String bankName = paymentData.get("bankName");
            String refCode = paymentData.get("referenceCode");
            if (bankName == null || bankName.trim().isEmpty() || refCode == null || refCode.trim().isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "SUCCESS";
            }
        } else {
            this.status = "REJECTED";
        }
    }

}