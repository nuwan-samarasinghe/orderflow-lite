package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.Payment;
import com.interviewprep.orderflow_lite.enums.PaymentStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto implements Serializable {
    private UUID id;
    private UUID orderId;
    @NotBlank(message = "Payment reference is required")
    @Size(max = 100, message = "Payment reference must not exceed 100 characters")
    private String paymentReference;
    @NotNull(message = "Payment status is required")
    private PaymentStatus status;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    @Digits(integer = 17, fraction = 2, message = "Amount must have at most 17 integer digits and 2 fractional digits")
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentDto fromEntity(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrder() != null ? payment.getOrder().getId() : null)
                .paymentReference(payment.getPaymentReference())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .paidAt(payment.getPaidAt())
                .createdAt(payment.getCreatedAt() != null ? payment.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(payment.getUpdatedAt() != null ? payment.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public Payment toEntity() {
        return Payment.builder()
                .id(this.id)
                .paymentReference(this.paymentReference)
                .status(this.status)
                .amount(this.amount)
                .paidAt(this.paidAt)
                .build();
        // Note: Order is not set here as it requires additional logic
    }
}