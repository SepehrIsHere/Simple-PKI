package com.pki.demo.entities;

import com.pki.demo.entities.enumerations.LogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class LogEntity {
    private String message;
    private LogType logType;
    private String classPath;
    private String methodName;
    private String exceptionMessage;
    private String stackTrace;
}
