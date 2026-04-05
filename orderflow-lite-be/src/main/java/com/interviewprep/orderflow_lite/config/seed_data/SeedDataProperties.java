package com.interviewprep.orderflow_lite.config.seed_data;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.seed")
public class SeedDataProperties {
    private boolean enabled = true;
    private String mode = "latest-per-entity";
    private String classpathLocation = "seed-data";
    private String externalLocation = "./seed-data";
}
