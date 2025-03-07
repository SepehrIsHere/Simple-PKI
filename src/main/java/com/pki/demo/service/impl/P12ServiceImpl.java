package com.pki.demo.service.impl;

import com.pki.demo.DTO.P12DTO;
import com.pki.demo.entities.LogEntity;
import com.pki.demo.service.CertificateService;
import com.pki.demo.service.P12Service;
import com.pki.demo.util.LogMessageCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;


@Service
@RequiredArgsConstructor
public class P12ServiceImpl implements P12Service {
    private final String CLASS_NAME = P12ServiceImpl.class.getSimpleName();

    private final KeyPairGenerator keyPairGenerator;
    private final CertificateService certificateService;
    private final MongoTemplate mongoTemplate;
    private final LogMessageCreator messageCreator;

    @Override
    public KeyPair generateKeyPair() {
        try {
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            if (keyPair != null) {
                mongoTemplate.save(
                        messageCreator.createSaveMessageEntity(CLASS_NAME, Thread.currentThread().getName()));
                return keyPair;
            } else {
                throw new RuntimeException("An error occured while creating a new key pair");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mongoTemplate.save(messageCreator.createExceptionMessageEntity
                    (CLASS_NAME,
                            Thread.currentThread().getStackTrace()[1].getMethodName(),
                            Arrays.toString(e.getStackTrace()),
                            e.getMessage()));
            throw new RuntimeException(e.getMessage(), e.getCause());

        }
    }

    @Override
    public String generateP12Pem(P12DTO request) {
        try {
            KeyPair keyPair = generateKeyPair();
            X509Certificate certificate = certificateService.generateSelfSignedCertificate(keyPair);
            KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
            keyStore.load(null, null);
            keyStore.load(null, request.getPassword().toCharArray());
            keyStore.setKeyEntry(request.getAlias(), keyPair.getPrivate(), request.getPassword().toCharArray(), new Certificate[]{certificate});
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            keyStore.store(outputStream, request.getPassword().toCharArray());
            byte[] pemBytes = outputStream.toByteArray();
            return convertToPEM(pemBytes);
        } catch (Exception e) {
            mongoTemplate.save(messageCreator.createExceptionMessageEntity
                    (CLASS_NAME,
                            Thread.currentThread().getStackTrace()[1].getMethodName(),
                            Arrays.toString(e.getStackTrace()),
                            e.getMessage()));
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    private String convertToPEM(byte[] data) {
        String base64Encoded = Base64.getEncoder().encodeToString(data);
        StringBuilder pemFormat = new StringBuilder();
        pemFormat.append("-----BEGIN PKCS12-----\n");
        for (int i = 0; i < base64Encoded.length(); i += 64) {
            pemFormat.append(base64Encoded, i, Math.min(i + 64, base64Encoded.length())).append("\n");
        }
        pemFormat.append("-----END PKCS12-----\n");
        return pemFormat.toString();
    }

}
