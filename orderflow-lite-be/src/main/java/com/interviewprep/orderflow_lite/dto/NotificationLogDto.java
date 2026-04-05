package com.interviewprep.orderflow_lite.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.interviewprep.orderflow_lite.entity.NotificationLog;
import com.interviewprep.orderflow_lite.enums.NotificationStatus;
import com.interviewprep.orderflow_lite.enums.NotificationType;

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
public class NotificationLogDto implements Serializable {
    private UUID id;
    private UUID orderId;
    @NotNull(message = "Notification type is required")
    private NotificationType type;
    @NotBlank(message = "Recipient is required")
    @Size(max = 150, message = "Recipient must not exceed 150 characters")
    private String recipient;
    @NotNull(message = "Notification status is required")
    private NotificationStatus status;
    private LocalDateTime sentAt;
    @Size(max = 1000, message = "Error message must not exceed 1000 characters")
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NotificationLogDto fromEntity(NotificationLog notificationLog) {
        return NotificationLogDto.builder()
                .id(notificationLog.getId())
                .orderId(notificationLog.getOrder() != null ? notificationLog.getOrder().getId() : null)
                .type(notificationLog.getType())
                .recipient(notificationLog.getRecipient())
                .status(notificationLog.getStatus())
                .sentAt(notificationLog.getSentAt())
                .errorMessage(notificationLog.getErrorMessage())
                .createdAt(notificationLog.getCreatedAt() != null ? notificationLog.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .updatedAt(notificationLog.getUpdatedAt() != null ? notificationLog.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null)
                .build();
    }

    public NotificationLog toEntity() {
        return NotificationLog.builder()
                .id(this.id)
                .type(this.type)
                .recipient(this.recipient)
                .status(this.status)
                .sentAt(this.sentAt)
                .errorMessage(this.errorMessage)
                .build();
        // Note: Order is not set here as it requires additional logic
    }
}