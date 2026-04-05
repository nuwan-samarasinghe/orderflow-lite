package com.interviewprep.orderflow_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.orderflow_lite.dto.ErrorDto;
import com.interviewprep.orderflow_lite.dto.InventoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory", description = "Inventory management APIs")
public class InventoryController {

    @Operation(
            summary = "Get inventory by product id",
            description = "Returns inventory details for a product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory returned"),
            @ApiResponse(responseCode = "404", description = "Inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDto> getInventryDataForProductId(
        @Parameter(description = "Product id", example = "101")
        @PathVariable String productId) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Adjust inventory",
            description = "Increases or decreases available inventory for a product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory adjusted"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "Inventory conflict",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PatchMapping("/{productId}/adjust")
    public ResponseEntity<InventoryDto> adjustInventory(
        @Parameter(description = "Product id", example = "101")
        @PathVariable String productId, 
        @Parameter(description = "Inventory data", example = """
        {
            "quantity": 10
        }
        """)
        @RequestBody InventoryDto inventoryDto) {
        return ResponseEntity.ok(null);
    }

}
