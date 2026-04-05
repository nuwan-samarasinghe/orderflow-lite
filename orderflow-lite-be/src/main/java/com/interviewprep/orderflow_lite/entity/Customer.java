package com.interviewprep.orderflow_lite.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customers",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"email"}, name = "uk_customer_email")
        },
        indexes = {
            @Index(name = "idx_customer_email", columnList = "email")
        }
)
public class Customer extends BaseEntity {

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CustomerOrder> orders = new ArrayList<>();
}

// CascadeType.PERSIST - When you save a Customer, all associated Addresses will also be saved automatically.
// CascadeType.MERGE - When you update a Customer, all associated Addresses will also be updated automatically.
// CascadeType.REMOVE - When you delete a Customer, all associated Addresses will also be deleted automatically.
// CascadeType.REFRESH - When you refresh a Customer, all associated Addresses will also be refreshed automatically.
// CascadeType.DETACH - When you detach a Customer from the persistence context, all associated Addresses will also be detached automatically.
// CadeType.ALL - This is a shorthand for specifying all of the above cascade types. It means that all operations (persist, merge, remove, refresh, detach) will be cascaded to the related entities.