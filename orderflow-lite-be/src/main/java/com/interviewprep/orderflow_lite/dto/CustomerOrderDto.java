package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.interviewprep.orderflow_lite.entity.CustomerOrder;
import com.interviewprep.orderflow_lite.enums.OrderStatus;

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
public class CustomerOrderDto implements Serializable {
    private UUID id;
    @NotBlank(message = "Order number is required")
    @Size(max = 50, message = "Order number must not exceed 50 characters")
    private String orderNumber;
    private UUID customerId;
    @NotNull(message = "Order status is required")
    private OrderStatus status;
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be non-negative")
    @Digits(integer = 17, fraction = 2, message = "Total amount must have at most 17 integer digits and 2 fractional digits")
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    List<OrderItemDto> items;
    PaymentDto payment;
    List<NotificationLogDto> notifications;

    public static CustomerOrderDto fromEntity(CustomerOrder order) {
        return CustomerOrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(order.getUpdatedAt() != null ? order.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .items(order.getItems() != null ? order.getItems().stream()
                        .map(OrderItemDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .payment(order.getPayment() != null ? PaymentDto.fromEntity(order.getPayment()) : null)
                .notifications(order.getNotifications() != null ? order.getNotifications().stream()
                        .map(NotificationLogDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public CustomerOrder toEntity() {
        CustomerOrder order = CustomerOrder.builder()
                .id(this.id)
                .orderNumber(this.orderNumber)
                .status(this.status)
                .totalAmount(this.totalAmount)
                .build();
        // Note: Customer, items, payment, notifications are not set here as they require additional logic
        return order;
    }
}