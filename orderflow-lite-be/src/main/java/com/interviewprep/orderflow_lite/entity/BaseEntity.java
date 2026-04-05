package com.interviewprep.orderflow_lite.entity;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** 
 * Base class for all entities in the application.
 */
@MappedSuperclass // This annotation indicates that this class is a base class for other entities and should not be mapped to a database table on its own.
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR) // Store UUID as VARCHAR in the database because mysql does not have UUID type
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;
}
