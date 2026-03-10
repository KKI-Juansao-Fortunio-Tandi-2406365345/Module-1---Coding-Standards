package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;

    @Setter
    String status;

    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        if (method.equals(PaymentMethod.VOUCHER_CODE.getValue())) {
            this.status = validateVoucherCode(paymentData.get("voucherCode")) ?
                    PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
        } else if (method.equals(PaymentMethod.CASH_ON_DELIVERY.getValue())) {
            this.status = validateCashOnDelivery(paymentData) ?
                    PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
        }
    }

    private boolean validateVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        return digitCount == 8;
    }

    private boolean validateCashOnDelivery(Map<String, String> paymentData) {
        if (paymentData == null) return false;
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        return address != null && !address.isEmpty() &&
                deliveryFee != null && !deliveryFee.isEmpty();
    }
}