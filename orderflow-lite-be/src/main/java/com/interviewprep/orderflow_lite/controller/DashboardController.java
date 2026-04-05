package com.interviewprep.orderflow_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Dashboard and analytics APIs")
public class DashboardController {

    @GetMapping("/summary")
    @Operation(
            summary = "Get dashboard summary",
            description = "Returns summary metrics such as order count, revenue, and inventory alerts"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard summary returned")
    })
    public ResponseEntity<Object> getSummary() {
        return ResponseEntity.ok().build();
    }

}
