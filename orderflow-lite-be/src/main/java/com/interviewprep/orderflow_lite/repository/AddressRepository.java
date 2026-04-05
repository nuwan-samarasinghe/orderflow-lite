package com.interviewprep.orderflow_lite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interviewprep.orderflow_lite.entity.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    
}
