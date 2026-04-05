package com.interviewprep.orderflow_lite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.orderflow_lite.dto.CustomerOrderDto;
import com.interviewprep.orderflow_lite.dto.ErrorDto;
import com.interviewprep.orderflow_lite.dto.OrderItemDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Order management APIs")
public class OrdersController {

    @Operation(
            summary = "Get order by id",
            description = "Returns complete order details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order returned",
                    content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<CustomerOrderDto>> allOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrderDto> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Create order",
            description = "Creates a new order, reserves stock, persists order items, and triggers async post-processing"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "Insufficient stock",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "422", description = "Business rule violation",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping("")
    public ResponseEntity<CustomerOrderDto> createOrder(
        @Parameter(description = "Order data", example = """
        {
            "customerId": "1",
            "items": [
            {
                "productId": "101",
                "quantity": 2
            },
            {
                "productId": "102",
                "quantity": 1
            }
            ]
        }
        """)
        @RequestBody CustomerOrderDto customerOrderDto) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Cancel order",
            description = "Cancels an order if the current state allows cancellation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancelled"),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "422", description = "Invalid order state",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<CustomerOrderDto> cancelOrder(
        @Parameter(description = "Order id", example = "5001")
        @PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Mark order as shipped",
            description = "Marks the order as shipped if state transition is valid"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order shipped"),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "422", description = "Invalid order state",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PatchMapping("/{id}/ship")
    public ResponseEntity<CustomerOrderDto> shipOrder(
        @Parameter(description = "Order id", example = "5001")
        @PathVariable String id) {
        return ResponseEntity.ok(null);
    }

}
