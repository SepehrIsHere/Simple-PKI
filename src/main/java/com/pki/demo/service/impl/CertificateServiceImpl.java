package com.pki.demo.service.impl;

import com.pki.demo.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

@Service
public class CertificateServiceImpl implements CertificateService {
    @Override
    public X509Certificate generateSelfSignedCertificate(KeyPair keyPair) {
        try {
            X500Name subject = new X500Name("CN=test O=demo C=DE");
            X500Name issuer = new X500Name("CN=self signed O=demo C=DE");
            BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
            Date notBefore = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            Date notAfter = new Date(System.currentTimeMillis() + (365 * 24 * 60 * 60 * 1000L));

            X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                    issuer, serial, notBefore, notAfter, subject, SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded())
            );

            ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA")
                    .setProvider(new BouncyCastleProvider())
                    .build(keyPair.getPrivate());
            X509CertificateHolder certificateHolder = certificateBuilder.build(signer);
            return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certificateHolder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
