package com.interviewprep.orderflow_lite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewprep.orderflow_lite.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    
}
