package com.interviewprep.orderflow_lite.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.orderflow_lite.dto.CustomerDto;
import com.interviewprep.orderflow_lite.dto.CustomerOrderDto;
import com.interviewprep.orderflow_lite.dto.ErrorDto;
import com.interviewprep.orderflow_lite.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description = "Customer management APIs")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @Operation(
            summary = "Get all customers",
            description = "Returns a list of all customers"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customers found"),
        @ApiResponse(responseCode = "404", description = "Customers not found",
                content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }


    @Operation(
            summary = "Get customer by id",
            description = "Returns customer details by unique id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found"),
        @ApiResponse(responseCode = "404", description = "Customer not found",
                content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(
            @Parameter(description = "Customer id", example = "5e952c73-cbfd-40c2-8349-d4b6a27b8f66")
            @PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @Operation(
            summary = "Get customer orders",
            description = "Returns all orders belonging to a customer"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders returned"),
        @ApiResponse(responseCode = "404", description = "Customer not found",
                content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<CustomerOrderDto>> getCustomerOrders(
            @Parameter(description = "Customer id", example = "5e952c73-cbfd-40c2-8349-d4b6a27b8f66")
            @PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerOrders(customerId));
    }

    @Operation(
            summary = "Create customer",
            description = "Creates a new customer profile"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Customer created"),
        @ApiResponse(responseCode = "400", description = "Invalid request",
                content = @Content(schema = @Schema(implementation = ErrorDto.class))),
        @ApiResponse(responseCode = "409", description = "Duplicate customer",
                content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping("")
    public ResponseEntity<CustomerDto> createCustomer(
            @Parameter(description = "Customer data", example = """
        {
            "name": "John Doe",
            "email": "john.doe@example.com"
        }
        """)
            @RequestBody CustomerDto body) {
        return ResponseEntity.ok(customerService.createCustomer(body));
    }

}
