package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.OrderItem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto implements Serializable {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than zero")
    @Digits(integer = 17, fraction = 2, message = "Unit price must have at most 17 integer digits and 2 fractional digits")
    private BigDecimal unitPrice;
    @NotNull(message = "Line total is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Line total must be non-negative")
    @Digits(integer = 17, fraction = 2, message = "Line total must have at most 17 integer digits and 2 fractional digits")
    private BigDecimal lineTotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null)
                .productId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null)
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .lineTotal(orderItem.getLineTotal())
                .createdAt(orderItem.getCreatedAt() != null ? orderItem.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(orderItem.getUpdatedAt() != null ? orderItem.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public OrderItem toEntity() {
        return OrderItem.builder()
                .id(this.id)
                .quantity(this.quantity)
                .unitPrice(this.unitPrice)
                .lineTotal(this.lineTotal)
                .build();
        // Note: Order and Product are not set here as they require additional logic
    }
}