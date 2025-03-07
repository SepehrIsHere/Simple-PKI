package com.pki.demo.service;

import com.pki.demo.DTO.P12DTO;

import java.security.KeyPair;

public interface P12Service {
    KeyPair generateKeyPair();

    String generateP12Pem(P12DTO request);
}
