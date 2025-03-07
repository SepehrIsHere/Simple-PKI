package com.pki.demo.util;

import com.pki.demo.entities.LogEntity;
import com.pki.demo.entities.enumerations.LogType;
import org.springframework.stereotype.Component;

@Component
public class LogMessageCreator {

    public LogEntity createSaveMessageEntity(String classPath, String methodName) {
        return LogEntity.builder()
                .logType(LogType.SAVE)
                .message("Saving log")
                .classPath(classPath)
                .methodName(methodName)
                .build();
    }

    public LogEntity createDeleteMessageEntity(String classPath, String methodName) {
        return LogEntity.builder()
                .logType(LogType.DELETE)
                .message("Deleting log")
                .classPath(classPath)
                .methodName(methodName)
                .build();
    }

    public LogEntity createExceptionMessageEntity(String classPath, String methodName, String stackTrace, String exceptionMessage) {
        return LogEntity.builder()
                .logType(LogType.EXCEPTION)
                .message("Deleting log")
                .classPath(classPath)
                .methodName(methodName)
                .stackTrace(stackTrace)
                .exceptionMessage(exceptionMessage)
                .build();
    }


}
