package com.csaba79coder.loggingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * responsible for logging the whole application
 * fields: id, timestamp, message, level, serviceName
 */
@Document(value = "logging")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Logging {

    @Id
    private String id;
    private String timestamp;
    private String message;
    private String level;
    private String serviceName;
}
