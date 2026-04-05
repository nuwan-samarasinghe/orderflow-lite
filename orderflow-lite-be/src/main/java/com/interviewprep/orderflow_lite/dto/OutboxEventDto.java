package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.OutboxEvent;
import com.interviewprep.orderflow_lite.enums.OutboxStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboxEventDto implements Serializable {
    private UUID id;
    @NotBlank(message = "Aggregate type is required")
    @Size(max = 100, message = "Aggregate type must not exceed 100 characters")
    private String aggregateType;
    @NotNull(message = "Aggregate ID is required")
    private Long aggregateId;
    @NotBlank(message = "Event type is required")
    @Size(max = 100, message = "Event type must not exceed 100 characters")
    private String eventType;
    @NotBlank(message = "Payload is required")
    private String payload;
    @NotNull(message = "Outbox status is required")
    private OutboxStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OutboxEventDto fromEntity(OutboxEvent outboxEvent) {
        return OutboxEventDto.builder()
                .id(outboxEvent.getId())
                .aggregateType(outboxEvent.getAggregateType())
                .aggregateId(outboxEvent.getAggregateId())
                .eventType(outboxEvent.getEventType())
                .payload(outboxEvent.getPayload())
                .status(outboxEvent.getStatus())
                .createdAt(outboxEvent.getCreatedAt() != null ? outboxEvent.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(outboxEvent.getUpdatedAt() != null ? outboxEvent.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public OutboxEvent toEntity() {
        return OutboxEvent.builder()
                .id(this.id)
                .aggregateType(this.aggregateType)
                .aggregateId(this.aggregateId)
                .eventType(this.eventType)
                .payload(this.payload)
                .status(this.status)
                .build();
    }
}