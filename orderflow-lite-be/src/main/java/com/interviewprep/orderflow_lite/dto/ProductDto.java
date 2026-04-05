package com.interviewprep.orderflow_lite.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.Product;

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
public class ProductDto {
    private UUID id;
    @NotBlank(message = "SKU is required")
    @Size(max = 50, message = "SKU must not exceed 50 characters")
    private String sku;
    @NotBlank(message = "Product name is required")
    @Size(max = 150, message = "Product name must not exceed 150 characters")
    private String name;
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 17, fraction = 2, message = "Price must have at most 17 integer digits and 2 fractional digits")
    private BigDecimal price;
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;
    @NotNull(message = "Active status is required")
    private Boolean active;
    private Integer availableQuantity;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .active(product.getActive())
                .availableQuantity(product.getInventory() != null ? product.getInventory().getAvailableQuantity() : null)
                .createdAt(product.getCreatedAt() != null ? product.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public Product toEntity() {
        Product product = Product.builder()
                .id(this.id)
                .sku(this.sku)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .category(this.category)
                .active(this.active)
                .build();
        // Note: Inventory is not set here as it requires additional logic
        return product;
    }
}
