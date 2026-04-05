package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto implements Serializable {
    private UUID id;
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 200, message = "Address line 1 must not exceed 200 characters")
    private String line1;
    @Size(max = 200, message = "Address line 2 must not exceed 200 characters")
    private String line2;
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;
    @NotBlank(message = "Zip code is required")
    @Size(max = 20, message = "Zip code must not exceed 20 characters")
    private String zipCode;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public static AddressDto fromEntity(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .line1(address.getLine1())
                .line2(address.getLine2())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .createdAt(address.getCreatedAt() != null ? address.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(address.getUpdatedAt() != null ? address.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public Address toEntity() {
        return Address.builder()
                .id(this.id)
                .line1(this.line1)
                .line2(this.line2)
                .city(this.city)
                .state(this.state)
                .country(this.country)
                .zipCode(this.zipCode)
                .build();
    }
}
