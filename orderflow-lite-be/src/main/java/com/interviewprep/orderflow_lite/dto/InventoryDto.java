package com.interviewprep.orderflow_lite.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.Inventory;

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
public class InventoryDto {
    private UUID productId;
    private String productName;
    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity must be non-negative")
    private Integer availableQuantity;
    @NotNull(message = "Reserved quantity is required")
    @Min(value = 0, message = "Reserved quantity must be non-negative")
    private Integer reservedQuantity;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public static InventoryDto fromEntity(Inventory inventory) {
        return InventoryDto.builder()
                .productId(inventory.getProduct() != null ? inventory.getProduct().getId() : null)
                .productName(inventory.getProduct() != null ? inventory.getProduct().getName() : null)
                .availableQuantity(inventory.getAvailableQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .createdAt(inventory.getCreatedAt() != null ? inventory.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(inventory.getUpdatedAt() != null ? inventory.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public Inventory toEntity() {
        return Inventory.builder()
                .availableQuantity(this.availableQuantity)
                .reservedQuantity(this.reservedQuantity)
                .build();
        // Note: Product is not set here as it requires additional logic
    }
}
