package org.jan.common.utils.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NullCipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jan.common.utils.lang.ArrayUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.ec.ECKeyFactory;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;
import static org.jan.common.utils.security.Algorithm.KeyPairs;

/**
 * Some of the most common encryption algorithm.
 *
 * @author Jan.Wang
 *
 */
@SuppressWarnings("restriction")
public final class Algorithm {

    private Algorithm() {
    }

    /**
     * Class for Key-Value.
     *
     * @author jan.wang
     *
     * @param <A>
     * @param <B>
     */
    public static class KeyPairs<A, B> {
        private A a;

        private B b;

        public KeyPairs(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getFirst() {
            return a;
        }

        public B getSecond() {
            return b;
        }
    }

    /**
     * Base64 coder
     */
    public enum Coder {
        BASE64;

        /**
         * encode the specified text.
         *
         * @param plaintext
         * @return ciphertext
         */
        public String encode(String plaintext) {
            return EncryptUtils.encodeBASE64(plaintext);
        }

        /**
         * decoder the specified text.
         *
         * @param ciphertext
         * @return plaintext
         */
        public String decoder(String ciphertext) {
            return EncryptUtils.decoderBASE64(ciphertext);
        }

    }

    /**
     * Several one-way encryption algorithm.
     */
    public enum Oneway {
        MD2, MD5, SHA, MD4(true);

        private boolean needBouncyCastleProvider;

        private Oneway() {
        }

        /*
         * external provider
         */
        private Oneway(boolean needBouncyCastleProvider) {
            this.needBouncyCastleProvider = needBouncyCastleProvider;
        }

        /**
         * encrypt the specified text
         *
         * @param plaintext
         * @return ciphertext
         */
        public String encrypt(String plaintext) {
            return EncryptUtils.encryptOneway(plaintext, name(), needBouncyCastleProvider);
        }
    };

    /**
     * Several one-way with HMAC encryption algorithm.
     */
    public enum Oneway_hmac {
        HMACMD5, HMACSHA1, HMACSHA256, HMACSHA384, HMACSHA512;

        /**
         * create key.
         *
         * @return key
         */
        public String generateKey() {
            return EncryptUtils.generateKey(name(), false);
        }

        /**
         * encrypt the specified text
         *
         * @param plaintext
         * @param key
         * @return ciphertext
         */
        public String encrypt(String plaintext, String key) {
            return EncryptUtils.encryptOnewayHMAC(plaintext, key, name());
        }

    }

    /**
     * Several symmetry encryption algorithm.
     */
    public enum Symmetry {
        AES, DES, DESEDE, BLOWFISH, RC2, RC4, IDEA(true), RIJNDAEL(true), SERPENT(true), TWOFISH(true), RC5(true);

        private boolean needBouncyCastleProvider;

        private Symmetry() {
        }

        /*
         * external provider
         */
        private Symmetry(boolean needBouncyCastleProvider) {
            this.needBouncyCastleProvider = needBouncyCastleProvider;
        }

        /**
         * create key.
         *
         * @return key
         */
        public String generateKey() {
            return EncryptUtils.generateKey(name(), needBouncyCastleProvider);
        }

        /**
         * encrypt the specified text
         *
         * @param plaintext
         * @param key
         * @return ciphertext
         */
        public String encrypt(String plaintext, String key) {
            return EncryptUtils.encryptSymmetry(plaintext, key, name());
        }

        /**
         * decrypt the specified text
         *
         * @param ciphertext
         * @param key
         * @return plaintext
         */
        public String decrypt(String ciphertext, String key) {
            return EncryptUtils.decryptSymmetry(ciphertext, key, name());
        }
    }

    /**
     * PBE encryption algorithm.
     */
    public enum Symmetry_pbe {
        PBEWITHMD5ANDDES;

        /**
         * encrypt the specified text.
         *
         * @param plaintext
         * @param password
         * @return {@link KeyPairs} - KeyPairs&lt;salt, ciphertext&gt;[key-value: the key is salt, and the value is
         *         ciphertext].
         */
        public KeyPairs<String, String> encrypt(String plaintext, String password) {
            String salt = EncryptUtils.initSalt();
            String ciphertext = EncryptUtils.encryptPBE(plaintext, Coder.BASE64.encode(password), salt, name());
            return new KeyPairs<String, String>(salt, ciphertext);
        }

        /**
         * decrypt the specified text
         *
         * @param ciphertext
         * @param password
         * @param salt
         * @return plaintext
         */
        public String decrypt(String ciphertext, String password, String salt) {
            return EncryptUtils.decryptPBE(ciphertext, Coder.BASE64.encode(password), salt, name());
        }
    }

    /**
     * Several asymmetry encryption algorithm.
     */
    public enum Asymmetry {
        RSA("MD5withRSA"), DSA(true);

        private String defaultSignAlgorithm;

        private boolean onlySign;

        private Asymmetry() {
        }

        private Asymmetry(String signAlgorithm) {
            this.defaultSignAlgorithm = signAlgorithm;
        }

        private Asymmetry(boolean onlySign) {
            this.onlySign = onlySign;
        }

        /*
         * Gets default sign algorithm.
         */
        private String getDefaultSignAlgorithm() {
            if (null == defaultSignAlgorithm)
                defaultSignAlgorithm = name();
            return defaultSignAlgorithm;
        }

        /**
         * Generates a key pair.
         *
         * @return {@link KeyPairs} &lt; PublicKey, PrivateKey &gt;
         */
        public KeyPairs<String, String> generateKeyPair() {
            return EncryptUtils.generateKeyPair(name(), null);
        }

        /**
         * encrypt the specified text with public key.
         *
         * @param plaintext
         * @param publicKey
         * @return ciphertext
         */
        public String encrypt(String plaintext, String publicKey) {
            if (onlySign)
                EncryptUtils.throwUnsupportedOperationException(name());
            return EncryptUtils.encryptAsymmetry(plaintext, publicKey, name());
        }

        /**
         * decrypt the specified text with private key.
         *
         * @param ciphertext
         * @param privateKey
         * @return plaintext
         */
        public String decrypt(String ciphertext, String privateKey) {
            if (onlySign)
                EncryptUtils.throwUnsupportedOperationException(name());
            return EncryptUtils.decryptAsymmetry(ciphertext, privateKey, name());
        }

        /**
         * sign the specified text.
         *
         * @param plaintext
         * @param privateKey
         * @return sign
         */
        public String sign(String plaintext, String privateKey) {
            return sign(plaintext, privateKey, getDefaultSignAlgorithm());
        }

        /**
         * verify the specified text from the sign.
         *
         * @param plaintext
         * @param publicKey
         * @param sign
         * @return true if validate pass, or false.
         */
        public boolean verify(String plaintext, String publicKey, String sign) {
            return verify(plaintext, publicKey, sign, getDefaultSignAlgorithm());
        }

        /**
         * sign the specified text
         *
         * @param plaintext
         * @param privateKey
         * @param signAlgorithm
         * @return sign
         */
        public String sign(String plaintext, String privateKey, String signAlgorithm) {
            return EncryptUtils.sign(plaintext, privateKey, name(), signAlgorithm);
        }

        /**
         * verify the specified text from the sign.
         *
         * @param plaintext
         * @param publicKey
         * @param sign
         * @param signAlgorithm
         * @return true if validate pass, or false.
         */
        public boolean verify(String plaintext, String publicKey, String sign, String signAlgorithm) {
            return EncryptUtils.verify(plaintext, publicKey, sign, name(), signAlgorithm);
        }
    }

    /**
     * Diffie-Hellman encryption algorithm.
     */
    public enum Asymmetry_dh {
        DH("DES");
        private String defaultSecretAlgorithm;

        private Asymmetry_dh(String secretAlgorithm) {
            this.defaultSecretAlgorithm = secretAlgorithm;
        }

        /**
         * Creates two pairs public key and private key for party a and part b.
         *
         * @return {@link KeyPairs}&lt;PartyA&lt;public, private&gt, PartyB&lt;public, private&gt;&gt;
         */
        public KeyPairs<KeyPairs<String, String>, KeyPairs<String, String>> generateKeyPairs() {
            final KeyPairs<String, String> partyA = EncryptUtils.generateKeyPair(name(), null);
            final KeyPairs<String, String> partyB = EncryptUtils.generatePartyBKeyPair(partyA.getFirst(), name());
            return new KeyPairs<KeyPairs<String, String>, KeyPairs<String, String>>(partyA, partyB);
        }

        /**
         * encrypt the specified text with public key and private key.
         *
         * @param plaintext
         * @param publicKey
         * @param privateKey
         * @return ciphertext
         */
        public String encrypt(String plaintext, String publicKey, String privateKey) {
            return encrypt(plaintext, publicKey, privateKey, defaultSecretAlgorithm);
        }

        /**
         * encrypt the specified text with public key and private key with secret algorithm.
         *
         * @param plaintext
         * @param publicKey
         * @param privateKey
         * @param secretAlgorithm
         * @return ciphertext
         */
        public String encrypt(String plaintext, String publicKey, String privateKey, String secretAlgorithm) {
            return EncryptUtils.encrypt(plaintext, publicKey, privateKey, name(), secretAlgorithm);
        }

        /**
         * decrypt the specified text with public key and private key.
         *
         * @param ciphertext
         * @param publicKey
         * @param privateKey
         * @return plaintext
         */
        public String decrypt(String ciphertext, String publicKey, String privateKey) {
            return decrypt(ciphertext, publicKey, privateKey, defaultSecretAlgorithm);
        }

        /**
         * decrypt the specified text with public key and private key with secret algorithm.
         *
         * @param ciphertext
         * @param publicKey
         * @param privateKey
         * @param secretAlgorithm
         * @return plaintext
         */
        public String decrypt(String ciphertext, String publicKey, String privateKey, String secretAlgorithm) {
            return EncryptUtils.decrypt(ciphertext, publicKey, privateKey, name(), secretAlgorithm);
        }
    }

    /**
     * Elliptic Curves Cryptography encryption algorithm.
     */
    public enum Asymmetry_ecc {
        EC;

        /**
         * Generates a key pair.
         *
         * @return {@link KeyPairs} &lt; PublicKey, PrivateKey &gt;
         */
        public KeyPairs<String, String> generateKeyPair() {
            return EncryptUtils.generateECCKeyPair();
        }

        /**
         * encrypt the specified text with public key.
         *
         * @param plaintext
         * @param publicKey
         * @return ciphertext
         */
        public String encrypt(String plaintext, String publicKey) {
            return EncryptUtils.encryptECC(plaintext, publicKey);
        }

        /**
         * decrypt the specified text with private key.
         *
         * @param ciphertext
         * @param privateKey
         * @return plaintext
         */
        public String decrypt(String ciphertext, String privateKey) {
            return EncryptUtils.decryptECC(ciphertext, privateKey);
        }
    }

}

/**
 * Utilities for encryption.
 */
class EncryptUtils {

    private static final String UTF_8 = "UTF-8";

    /**
     * init salt
     *
     * @return salt.
     */
    static String initSalt() {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return encodeBASE64(salt);
    }

    /**
     * create key for HMAC and Symmetry.
     *
     * @param algorithm
     * @param needBouncyCastleProvider
     * @return key
     */
    static String generateKey(String algorithm, boolean needBouncyCastleProvider) {
        try {
            if (needBouncyCastleProvider)
                Security.addProvider(new BouncyCastleProvider());
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            if (needBouncyCastleProvider)
                generator.init(128);
            return encodeBASE64(generator.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * Generates a key pair.
     *
     * @param algorithm
     * @param algorithmParameterSpecs
     * @return {@link KeyPairs} &lt; PublicKey, PrivateKey &gt;
     */
    static KeyPairs<String, String> generateKeyPair(String algorithm, AlgorithmParameterSpec algorithmParameterSpecs) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
            if (null != algorithmParameterSpecs)
                keyPairGen.initialize(algorithmParameterSpecs);
            else
                keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            final PublicKey publicKey = keyPair.getPublic();
            final PrivateKey privateKey = keyPair.getPrivate();
            return new KeyPairs<String, String>(encodeBASE64(publicKey.getEncoded()), encodeBASE64(privateKey
                    .getEncoded()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates public key and private key for part b according to public key of party a.
     *
     * @param partyAPublicKey
     * @param algorithm
     * @return {@link KeyPairs}&lt;{@link String}, {@link String}&gt;
     */
    static KeyPairs<String, String> generatePartyBKeyPair(String partyAPublicKey, String algorithm) {
        PublicKey pubKey = initPublicKey(partyAPublicKey, algorithm);
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
        return generateKeyPair(algorithm, dhParamSpec);
    }

    /**
     * Generates a key pair.
     *
     * @return {@link KeyPairs}&lt;{@link String}, {@link String}&gt;
     */
    static KeyPairs<String, String> generateECCKeyPair() {
        ECPoint ecPoint = new ECPoint(new BigInteger("2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8", 16), new BigInteger(
                "289070fb05d38ff58321f2e800536d538ccdaa3d9", 16));
        EllipticCurve ellipticCurve = new EllipticCurve(new ECFieldF2m(163, new int[] { 7, 6, 3 }), new BigInteger("1",
                2), new BigInteger("1", 2));
        ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, ecPoint, new BigInteger(
                "5846006549323611672814741753598448348329118574063", 10), 2);
        try {
            final ECPublicKey publicKey = new ECPublicKeyImpl(ecPoint, ecParameterSpec);
            final ECPrivateKey privateKey = new ECPrivateKeyImpl(new BigInteger(
                    "1234006549323611672814741753598448348329118574063", 10), ecParameterSpec);
            return new KeyPairs<String, String>(encodeBASE64(publicKey.getEncoded()), encodeBASE64(privateKey
                    .getEncoded()));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * create local secret key
     */
    private static SecretKey createLocalSecretKey(String publicKey, String privateKey, String algorithm,
            String secretAlgorithm) {
        try {
            PublicKey publickey = initPublicKey(publicKey, algorithm);
            PrivateKey privatekey = initPrivateKey(privateKey, algorithm);
            KeyAgreement keyAgree = KeyAgreement.getInstance(algorithm);
            keyAgree.init(privatekey);
            keyAgree.doPhase(publickey, true);
            return keyAgree.generateSecret(secretAlgorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt the specified text with public key.
     *
     * @param plaintext
     * @param publicKey
     * @return ciphertext
     */
    static String encryptECC(String plaintext, String publicKey) {
        try {
            ECPublicKey pubKey = (ECPublicKey) ECKeyFactory.INSTANCE.generatePublic(new X509EncodedKeySpec(
                    decoderBASE64ToByteArray(publicKey)));
            ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(), pubKey.getParams());
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());
            return ArrayUtils.bytes2Hex(cipher.doFinal(plaintext.getBytes(UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * decrypt the specified text with private key.
     *
     * @param ciphertext
     * @param privateKey
     * @return plaintext
     */
    static String decryptECC(String ciphertext, String privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decoderBASE64ToByteArray(privateKey));
            KeyFactory keyFactory = ECKeyFactory.INSTANCE;
            ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(), priKey.getParams());
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams());
            return new String(cipher.doFinal(ArrayUtils.hex2Bytes(ciphertext)), UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt the specified text
     *
     * @param plaintext
     * @param password
     * @param salt
     * @param algorithm
     * @return ciphertext
     */
    static String encryptPBE(String plaintext, String password, String salt, String algorithm) {
        try {
            byte[] bytes = encrypt(plaintext.getBytes(UTF_8), password, salt, algorithm, Cipher.ENCRYPT_MODE);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * decrypt the specified text
     *
     * @param ciphertext
     * @param password
     * @param salt
     * @param algorithm
     * @return plaintext
     */
    static String decryptPBE(String ciphertext, String password, String salt, String algorithm) {
        try {
            byte[] bytes = encrypt(ArrayUtils.hex2Bytes(ciphertext), password, salt, algorithm, Cipher.DECRYPT_MODE);
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt the specified text with public key and private key with secret algorithm.
     *
     * @param plaintext
     * @param publicKey
     * @param privateKey
     * @param algorithm
     * @param secretAlgorithm
     * @return ciphertext
     */
    static String encrypt(String plaintext, String publicKey, String privateKey, String algorithm,
            String secretAlgorithm) {
        return encrypt(plaintext, createLocalSecretKey(publicKey, privateKey, algorithm, secretAlgorithm));
    }

    /**
     * decrypt the specified text with public key and private key with secret algorithm.
     *
     * @param ciphertext
     * @param publicKey
     * @param privateKey
     * @param algorithm
     * @param secretAlgorithm
     * @return plaintext
     */
    static String decrypt(String ciphertext, String publicKey, String privateKey, String algorithm,
            String secretAlgorithm) {
        return decrypt(ciphertext, createLocalSecretKey(publicKey, privateKey, algorithm, secretAlgorithm));
    }

    /*
     * encrypt the specified byte array with password and salt.
     */
    private static byte[] encrypt(byte[] bytes, String password, String salt, String algorithm, int mode) {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        PBEParameterSpec paramSpec = new PBEParameterSpec(decoderBASE64ToByteArray(salt), 100);
        try {
            SecretKey secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
            return encrypt(bytes, secretKey, paramSpec, mode);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the public key.
     *
     * @param publicKey
     * @param algorithm
     * @return
     */
    static PublicKey initPublicKey(String publicKey, String algorithm) {
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(
                    new X509EncodedKeySpec(decoderBASE64ToByteArray(publicKey)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the private key.
     *
     * @param privateKey
     * @param algorithm
     * @return {@link PrivateKey}
     */
    static PrivateKey initPrivateKey(String privateKey, String algorithm) {
        try {
            return KeyFactory.getInstance(algorithm).generatePrivate(
                    new PKCS8EncodedKeySpec(decoderBASE64ToByteArray(privateKey)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt the specified text, supports only MD2, MD5, SHA and MD4 so far.
     *
     * @param plaintext
     * @param algorithm
     * @param needBouncyCastleProvider
     * @return ciphertext
     */
    static String encryptOneway(String plaintext, String algorithm, boolean needBouncyCastleProvider) {
        if (needBouncyCastleProvider)
            Security.addProvider(new BouncyCastleProvider());
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(plaintext.getBytes(UTF_8));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * throw {@link UnsupportedOperationException}.
     *
     * @param message
     */
    static void throwUnsupportedOperationException(String message) {
        throw new UnsupportedOperationException(message);
    }

    /**
     * encrypt the specified text for asymmetry algorithm.
     *
     * @param plaintext
     * @param key
     * @param algorithm
     * @return ciphertext
     */
    public static String encryptAsymmetry(String plaintext, String key, String algorithm) {
        return encrypt(plaintext, initPublicKey(key, algorithm));
    }

    /**
     * decrypt the specified text for asymmetry algorithm.
     *
     * @param ciphertext
     * @param key
     * @param algorithm
     * @return plaintext
     */
    public static String decryptAsymmetry(String ciphertext, String key, String algorithm) {
        return decrypt(ciphertext, initPrivateKey(key, algorithm));
    }

    /*
     * encrypt the specified byte text.
     */
    private static String encrypt(String plaintext, Key key) {
        try {
            byte[] bytes = encrypt(plaintext.getBytes(UTF_8), key, null, Cipher.ENCRYPT_MODE);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * encrypt the specified byte array.
     */
    private static byte[] encrypt(byte[] bytes, Key key, AlgorithmParameterSpec param, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            if (null == param)
                cipher.init(mode, key);
            else
                cipher.init(mode, key, param);
            return cipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * decrypt the specified byte array.
     */
    private static String decrypt(String ciphertext, Key key) {
        try {
            byte[] bytes = encrypt(ArrayUtils.hex2Bytes(ciphertext), key, null, Cipher.DECRYPT_MODE);
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encode the specified text.
     *
     * @param plaintext
     * @return ciphertext - if {@link UnsupportedEncodingException} occur it will throws runtime exception.
     */
    public static String encodeBASE64(String plaintext) {
        try {
            return encodeBASE64(plaintext.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * encode the specified byte array.
     */
    private static String encodeBASE64(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /*
     * decoder the specified text. if IOException occur it will throws runtime exception.
     */
    private static byte[] decoderBASE64ToByteArray(String ciphertext) {
        try {
            return new BASE64Decoder().decodeBuffer(ciphertext);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * decoder the specified text.
     *
     * @param ciphertext
     * @return plaintext - if {@link IOException} or occur it will throws runtime exception.
     */
    public static String decoderBASE64(String ciphertext) {
        try {
            return new String(decoderBASE64ToByteArray(ciphertext), UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt the specified text.
     *
     * @param plaintext
     * @param key
     * @param algorithm
     * @return
     */
    public static String encryptOnewayHMAC(String plaintext, String key, String algorithm) {
        try {
            SecretKey secretKey = new SecretKeySpec(decoderBASE64ToByteArray(key), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return ArrayUtils.bytes2Hex(mac.doFinal(plaintext.getBytes(UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt the specified text
     *
     * @param plaintext
     * @param key
     * @param algorithm
     * @return ciphertext
     */
    public static String encryptSymmetry(String plaintext, String key, String algorithm) {
        return encrypt(plaintext, new SecretKeySpec(decoderBASE64ToByteArray(key), algorithm));
    }

    /**
     * decrypt the specified text
     *
     * @param ciphertext
     * @param key
     * @param algorithm
     * @return plaintext
     */
    public static String decryptSymmetry(String ciphertext, String key, String algorithm) {
        return decrypt(ciphertext, new SecretKeySpec(decoderBASE64ToByteArray(key), algorithm));
    }

    /**
     * sign the specified text
     *
     * @param plaintext
     * @param privateKey
     * @param algorithm
     * @param signAlgorithm
     * @return sign
     */
    public static String sign(String plaintext, String privateKey, String algorithm, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(initPrivateKey(privateKey, algorithm));
            signature.update(plaintext.getBytes(UTF_8));
            return ArrayUtils.bytes2Hex(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * verify the specified text from the sign.
     *
     * @param plaintext
     * @param publicKey
     * @param sign
     * @param algorithm
     * @param signAlgorithm
     * @return true if validate pass, or false.
     */
    public static boolean verify(String plaintext, String publicKey, String sign, String algorithm, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(initPublicKey(publicKey, algorithm));
            signature.update(plaintext.getBytes(UTF_8));
            return signature.verify(ArrayUtils.hex2Bytes(sign));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return false;
    }
}
