package com.pki.demo.service;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

public interface CertificateService {
    X509Certificate generateSelfSignedCertificate(KeyPair keyPair);
}
