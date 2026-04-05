package com.interviewprep.orderflow_lite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.orderflow_lite.dto.ErrorDto;
import com.interviewprep.orderflow_lite.dto.ProductDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product catalog APIs")
public class ProductsController {

    @Operation(
            summary = "List products",
            description = "Returns paginated products with optional sorting and filtering"
    )
    @Parameter(name = "page", description = "Page number", example = "0")
    @Parameter(name = "size", description = "Page size", example = "10")
    @Parameter(name = "sort", description = "Sort field and direction", example = "name,asc")
    @Parameter(name = "direction", description = "Sort direction", example = "asc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products returned"),
                @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters",
                        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> allProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Get product by id",
            description = "Returns product details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product returned"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
        @Parameter(description = "Product id", example = "101")
        @PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Create product",
            description = "Creates a new product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate SKU",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(
        @Parameter(description = "Product data", example = """
        {
            "name": "Widget",
            "sku": "W123",
            "price": 19.99,
            "description": "A useful widget"
        }
        """)
        @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Update product",
            description = "Updates an existing product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
        @Parameter(description = "Product id", example = "101")
        @PathVariable String id, 
        @Parameter(description = "Product data", example = """
        {
            "name": "Widget",
            "sku": "W123",
            "price": 19.99,
            "description": "A useful widget"
        }
        """)
        @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Patch product",
            description = "Partially updates an existing product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> patchProduct(
        @Parameter(description = "Product id", example = "101")
        @PathVariable String id, 
        @Parameter(description = "Product data", example = """
        {
            "name": "Widget",
            "sku": "W123",
            "price": 19.99,
            "description": "A useful widget"
        }
        """)
        @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(null);
    }

}
