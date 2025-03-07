package com.pki.demo.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
public class AppConfig {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Bean
    KeyPairGenerator keyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA256");
        generator.initialize(2048);
        return generator;
    }

    @Bean
    CertificateFactory certificateFactory() throws CertificateException, NoSuchProviderException {
        return CertificateFactory.getInstance("X.509", "BC");
    }
}
