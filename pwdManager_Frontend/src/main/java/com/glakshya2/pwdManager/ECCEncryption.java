package com.glakshya2.pwdManager;

import lombok.val;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.cert.Certificate;

public class ECCEncryption {

    private static final String ALGORITHM = "EC";
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String KEYSTORE_FILE = "C:\\Users\\glaks\\.keystore\\keystore.jks";
    private static final String KEYSTORE_PASSWORD = "password";
    private static final String KEY_ALIAS = "ecc-key";

    private KeyPair keyPair;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    IESParameterSpec iesParamSpec = new IESParameterSpec(null, null, 256, 256, null, false);

    public ECCEncryption() throws Exception {
        loadOrCreateKeyPair();
    }

    private void loadOrCreateKeyPair() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        keyStore.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        if (keyStore.containsAlias(KEY_ALIAS)) {
            Key key = keyStore.getKey(KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
            if (key instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate(KEY_ALIAS);
                PublicKey publicKey = cert.getPublicKey();
                keyPair = new KeyPair(publicKey, (PrivateKey) key);
            }
        } else {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
            keyPair = keyPairGenerator.generateKeyPair();
            saveKeyPair(keyPair);
        }
    }


    private void saveKeyPair(KeyPair keyPair) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        keyStore.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        // Create X509EncodedKeySpec from encoded public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());

        // Get a CertificateFactory instance
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // Generate a Certificate object
        Certificate cert = cf.generateCertificate(new ByteArrayInputStream(spec.getEncoded()));

        // Store the key pair with the certificate
        keyStore.setKeyEntry(KEY_ALIAS, keyPair.getPrivate(),
                KEYSTORE_PASSWORD.toCharArray(),
                new Certificate[]{cert});
        keyStore.store(null, KEYSTORE_PASSWORD.toCharArray());
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME); // Use Bouncy Castle provider
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic(), iesParamSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME); // Use Bouncy Castle provider
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate(), iesParamSpec
        );
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}