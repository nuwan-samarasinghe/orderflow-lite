package com.interviewprep.orderflow_lite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewprep.orderflow_lite.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
    Optional<CustomerOrder> findByOrderNumber(String orderNumber);
}
