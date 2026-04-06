package com.interviewprep.orderflow_lite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.interviewprep.orderflow_lite.dto.CustomerDto;
import com.interviewprep.orderflow_lite.dto.CustomerOrderDto;
import com.interviewprep.orderflow_lite.entity.Customer;
import com.interviewprep.orderflow_lite.exception.ResourceNotFoundException;
import com.interviewprep.orderflow_lite.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto getCustomerById(UUID customerId) {
        log.info("Fetching customer by ID: {}", customerId);
        return customerRepository.findById(customerId)
                .map(CustomerDto::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId.toString()));
    }

    public List<CustomerOrderDto> getCustomerOrders(UUID customerId) {
        log.info("Fetching orders for customer: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId.toString()));
        return customer.getOrders().stream().map(CustomerOrderDto::fromEntity).toList();
    }

    public CustomerDto createCustomer(CustomerDto body) {
        Customer customer = body.toEntity();
        customer = customerRepository.saveAndFlush(customer);
        log.info("Created new customer with ID: {}", customer.getId());
        return CustomerDto.fromEntity(customer);
    }

}
