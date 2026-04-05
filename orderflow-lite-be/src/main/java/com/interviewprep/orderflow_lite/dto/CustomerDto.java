package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.interviewprep.orderflow_lite.entity.Address;
import com.interviewprep.orderflow_lite.entity.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto implements Serializable{
    private UUID id;
    @NotBlank(message = "Full name is required")
    @Size(max = 120, message = "Full name must not exceed 120 characters")
    private String fullName;
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;
    @Pattern(regexp = "^[0-9+\\-() ]{7,20}$", message = "Phone number is invalid")
    @Size(max = 30, message = "Phone must not exceed 30 characters")
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    List<AddressDto> addresses;

    public static CustomerDto fromEntity(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .createdAt(customer.getCreatedAt() != null ? customer.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(customer.getUpdatedAt() != null ? customer.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .addresses(customer.getAddresses() != null ? customer.getAddresses().stream()
                        .map(AddressDto::fromEntity)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public Customer toEntity() {
        Customer customer = Customer.builder()
                .id(this.id)
                .fullName(this.fullName)
                .email(this.email)
                .phone(this.phone)
                .build();
        if (this.addresses != null) {
            customer.setAddresses(this.addresses.stream()
                    .map(addressDto -> {
                        Address address = addressDto.toEntity();
                        address.setCustomer(customer);
                        return address;
                    })
                    .collect(Collectors.toList()));
        }
        return customer;
    }
}
