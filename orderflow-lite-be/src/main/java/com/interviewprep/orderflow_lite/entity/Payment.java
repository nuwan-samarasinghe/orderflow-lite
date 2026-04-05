package com.interviewprep.orderflow_lite.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.interviewprep.orderflow_lite.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "payments",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_payment_order", columnNames = "order_id"),
           @UniqueConstraint(name = "uk_payment_reference", columnNames = "payment_reference")
       },
       indexes = {
           @Index(name = "idx_payment_status", columnList = "status")
       })
public class Payment extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_order"))
    private CustomerOrder order;

    @Column(name = "payment_reference", nullable = false, length = 100)
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private PaymentStatus status;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    
}
