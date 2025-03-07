package com.pki.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(KeyStoreException.class)
    public ResponseEntity<String> handleKeyStoreException(final KeyStoreException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchProviderException.class)
    public ResponseEntity<String> handleNoSuchProviderException(final NoSuchProviderException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<String> handleNoSuchAlgorithmException(final NoSuchAlgorithmException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CertificateException.class)
    public ResponseEntity<String> handleCertificateException(final CertificateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(final IOException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
