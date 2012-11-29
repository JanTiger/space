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
 * @author Jan.Wang
 *
 */
@SuppressWarnings("restriction")
public final class Algorithm {

    private Algorithm(){}

    static class KeyPairs<A, B> {
        private A a;
        private B b;
        public KeyPairs(A a, B b){
            this.a = a;
            this.b = b;
        }
        public A getFirst(){
            return a;
        }
        public B getSecond(){
            return b;
        }
    }

    /**
     *  OK
     *  Base64 coder
     *
     */
    enum Coder {
        BASE64;

        /**
         *
         * @param plaintext
         * @return
         */
        public String encode(String plaintext) {
            return EncryptUtils.encodeBASE64(plaintext);
        }

        /**
         *
         * @param ciphertext
         * @return
         */
        public String decoder(String ciphertext) {
            return EncryptUtils.decoderBASE64(ciphertext);
        }

    }

    /**
     * OK
     *
     */
    enum Oneway {
        MD2, MD5, SHA, MD4(true);

        private boolean needBouncyCastleProvider;

        private Oneway() {
        }

        private Oneway(boolean needBouncyCastleProvider) {
            this.needBouncyCastleProvider = needBouncyCastleProvider;
        }

        public String encrypt(String plaintext) {
            return EncryptUtils.encrypt(plaintext, name(), needBouncyCastleProvider);
        }
    };

    enum Oneway_hmac {
        HMACMD5, HMACSHA1, HMACSHA256, HMACSHA384, HMACSHA512;

        public byte[] generateKey() {
            return EncryptUtils.generateKey(name(), false);
        }

        public String encrypt(String plaintext, byte[] key) {
            return EncryptUtils.encryptOnewayHMAC(plaintext, key, name());
        }

    }

    enum Symmetry {
        AES, DES, DESEDE, BLOWFISH, RC2, RC4, IDEA(true), RIJNDAEL(true), SERPENT(true), TWOFISH(true), RC5(true);

        private boolean needBouncyCastleProvider;

        private Symmetry() {
        }

        private Symmetry(boolean needBouncyCastleProvider) {
            this.needBouncyCastleProvider = needBouncyCastleProvider;
        }

        public byte[] generateKey() {
            return EncryptUtils.generateKey(name(), needBouncyCastleProvider);
        }

        public String encrypt(String plaintext, byte[] key) {
            return EncryptUtils.encryptSymmetry(plaintext, key, name());
        }

        public String decrypt(String ciphertext, byte[] key) {
            return EncryptUtils.decryptSymmetry(ciphertext, key, name());
        }
    }

    enum Symmetry_pbe {
        PBEWITHMD5ANDDES;

        private byte[] initSalt() {
            byte[] salt = new byte[8];
            Random random = new Random();
            random.nextBytes(salt);
            return salt;
        }

        /**
         *
         * @param src
         * @param password
         * @return
         */
        public KeyPairs<byte[], String> encrypt(String plaintext, String password) {
            final byte[] salt = initSalt();
            final String ciphertext = EncryptUtils.encrypt(plaintext, Coder.BASE64.encode(password), salt, name());
            return new KeyPairs<byte[], String>(salt, ciphertext);
        }

        public String decrypt(String ciphertext, String password, byte[] salt) {
            return EncryptUtils.decrypt(ciphertext, Coder.BASE64.encode(password), salt, name());
        }
    }

    enum Asymmetry {
        RSA("MD5withRSA"), DSA(true);

        private String defaultSignAlgorithm;

        private boolean onlySign;

        private Asymmetry() {
        }

        private Asymmetry(String signAlgorithm) {
            this.defaultSignAlgorithm = signAlgorithm;
        }

        private Asymmetry(boolean onlySign){
            this.onlySign = onlySign;
        }

        private String getDefaultSignAlgorithm() {
            if (null == defaultSignAlgorithm)
                defaultSignAlgorithm = name();
            return defaultSignAlgorithm;
        }

        /**
         * Generates a key pair.
         *
         * @return Entry &lt; PublicKey, PrivateKey &gt;
         */
        public KeyPairs<byte[], byte[]> generateKeyPair() {
            return EncryptUtils.generateKeyPair(name(), null);
        }

        public String encrypt(String plaintext, byte[] publicKey) {
            if(onlySign)
                EncryptUtils.throwUnsupportedOperationException(name());
            return EncryptUtils.encryptAsymmetry(plaintext, publicKey, name());
        }

        public String decrypt(String ciphertext, byte[] privateKey) {
            if(onlySign)
                EncryptUtils.throwUnsupportedOperationException(name());
            return EncryptUtils.decryptAsymmetry(ciphertext, privateKey, name());
        }

        public String sign(String plaintext, byte[] privateKey) {
            return sign(plaintext, privateKey, getDefaultSignAlgorithm());
        }

        public boolean verify(String plaintext, byte[] publicKey, String sign) {
            return verify(plaintext, publicKey, sign, getDefaultSignAlgorithm());
        }

        public String sign(String plaintext, byte[] privateKey, String signAlgorithm) {
            return EncryptUtils.sign(plaintext, privateKey, name(), signAlgorithm);
        }

        public boolean verify(String plaintext, byte[] publicKey, String sign, String signAlgorithm) {
            return EncryptUtils.verify(plaintext, publicKey, sign, name(), signAlgorithm);
        }
    }

    enum Asymmetry_dh {
        DH("DES");
        private String defaultSecretAlgorithm;

        private Asymmetry_dh(String secretAlgorithm) {
            this.defaultSecretAlgorithm = secretAlgorithm;
        }

        String getDefaultSecretAlgorithm() {
            return this.defaultSecretAlgorithm;
        }

        public KeyPairs<KeyPairs<byte[], byte[]>, KeyPairs<byte[], byte[]>> generateKeyPairs() {
            final KeyPairs<byte[], byte[]> partyA = EncryptUtils.generateKeyPair(name(), null);
            final KeyPairs<byte[], byte[]> partyB = EncryptUtils.createPartyBKeyPair(partyA.getFirst(), name());
            return new KeyPairs<KeyPairs<byte[], byte[]>, KeyPairs<byte[], byte[]>>(partyA, partyB);
        }

        public String encrypt(String plaintext, byte[] publicKey, byte[] privateKey) {
            return encrypt(plaintext, publicKey, privateKey, defaultSecretAlgorithm);
        }

        public String decrypt(String ciphertext, byte[] publicKey, byte[] privateKey) {
            return decrypt(ciphertext, publicKey, privateKey, defaultSecretAlgorithm);
        }

        public String encrypt(String plaintext, byte[] publicKey, byte[] privateKey, String secretAlgorithm) {
            return EncryptUtils.encrypt(plaintext, publicKey, privateKey, name(), secretAlgorithm);
        }

        public String decrypt(String ciphertext, byte[] publicKey, byte[] privateKey, String secretAlgorithm) {
            return EncryptUtils.decrypt(ciphertext, publicKey, privateKey, name(), secretAlgorithm);
        }
    }

    enum Asymmetry_ecc {
        EC;

        /**
         * Generates a key pair.
         *
         * @return Entry &lt; PublicKey, PrivateKey &gt;
         */
        public KeyPairs<byte[], byte[]> generateKeyPair() {
            return EncryptUtils.generateECCKeyPair();
        }

        public String encrypt(String plaintext, byte[] publicKey) {
            return EncryptUtils.encryptECC(plaintext, publicKey);
        }

        public String decrypt(String ciphertext, byte[] privateKey) {
            return EncryptUtils.decryptECC(ciphertext, privateKey);
        }
    }

}

class EncryptUtils {

    static KeyPairs<byte[], byte[]> generateECCKeyPair() {
        ECPoint ecPoint = new ECPoint(new BigInteger("2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8", 16), new BigInteger("289070fb05d38ff58321f2e800536d538ccdaa3d9", 16));
        EllipticCurve ellipticCurve = new EllipticCurve(new ECFieldF2m(163, new int[]{ 7, 6, 3 }), new BigInteger("1", 2), new BigInteger("1", 2));
        ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, ecPoint, new BigInteger("5846006549323611672814741753598448348329118574063", 10), 2);
        try {
            final ECPublicKey publicKey = new ECPublicKeyImpl(ecPoint, ecParameterSpec);
            final ECPrivateKey privateKey = new ECPrivateKeyImpl(new BigInteger("1234006549323611672814741753598448348329118574063", 10), ecParameterSpec);
            return new KeyPairs<byte[], byte[]>(publicKey.getEncoded(), privateKey.getEncoded());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String decryptECC(String dest, byte[] privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = ECKeyFactory.INSTANCE;
            ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(), priKey.getParams());
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams());
            return new String(cipher.doFinal(ArrayUtils.hex2Bytes(dest)), UTF_8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String encryptECC(String src, byte[] publicKey) {
        try {
            ECPublicKey pubKey = (ECPublicKey) ECKeyFactory.INSTANCE.generatePublic(new X509EncodedKeySpec(publicKey));
            ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(), pubKey.getParams());
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());
            return ArrayUtils.bytes2Hex(cipher.doFinal(src.getBytes(UTF_8)));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String UTF_8 = "UTF-8";

    static String encrypt(String src, String password, byte[] salt, String algorithm) {
        try {
            byte[] bytes = encrypt(src.getBytes(UTF_8), password, salt, algorithm, Cipher.ENCRYPT_MODE);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String encrypt(String src, byte[] publicKeyBytes, byte[] privateKeyBytes, String algorithm, String secretAlgorithm) {
        SecretKey secretKey = createLocalSecretKey(publicKeyBytes, privateKeyBytes, algorithm, secretAlgorithm);
        try {
            byte[] bytes = encrypt(src.getBytes(UTF_8), secretKey, Cipher.ENCRYPT_MODE);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }

    static String decrypt(String dest, byte[] publicKeyBytes, byte[] privateKeyBytes, String algorithm, String secretAlgorithm) {
        SecretKey secretKey = createLocalSecretKey(publicKeyBytes, privateKeyBytes, algorithm, secretAlgorithm);
        try {
            byte[] bytes = encrypt(ArrayUtils.hex2Bytes(dest), secretKey, Cipher.DECRYPT_MODE);
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKey createLocalSecretKey(byte[] publicKeyBytes, byte[] privateKeyBytes, String algorithm,
            String secretAlgorithm) {
        try {
            PublicKey publicKey = initPublicKey(publicKeyBytes, algorithm);
            PrivateKey privateKey = initPrivateKey(privateKeyBytes, algorithm);
            KeyAgreement keyAgree = KeyAgreement.getInstance(algorithm);
            keyAgree.init(privateKey);
            keyAgree.doPhase(publicKey, true);
            return keyAgree.generateSecret(secretAlgorithm);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String decrypt(String dest, String password, byte[] salt, String algorithm) {
        try {
            byte[] bytes = encrypt(ArrayUtils.hex2Bytes(dest), password, salt, algorithm, Cipher.DECRYPT_MODE);
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(byte[] bytes, String password, byte[] salt, String algorithm, int mode) {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        try {
            SecretKey secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(mode, secretKey, paramSpec);
            bytes = cipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //HMAC,Symmetry
    static byte[] generateKey(String algorithm, boolean needBouncyCastleProvider) {
        try {
            if (needBouncyCastleProvider)
                Security.addProvider(new BouncyCastleProvider());
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            if (needBouncyCastleProvider)
                generator.init(128);
            return generator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    static PublicKey initPublicKey(byte[] publicKeyBytes, String algorithm) {
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    static PrivateKey initPrivateKey(byte[] privateKeyBytes, String algorithm) {
        try {
            return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    static KeyPairs<byte[], byte[]> createPartyBKeyPair(byte[] partyAPublicKeyBytes, String algorithm) {
        PublicKey pubKey = initPublicKey(partyAPublicKeyBytes, algorithm);
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
        return generateKeyPair(algorithm, dhParamSpec);
    }

    static KeyPairs<byte[], byte[]> generateKeyPair(String algorithm, AlgorithmParameterSpec algorithmParameterSpecs) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
            if (null != algorithmParameterSpecs)
                keyPairGen.initialize(algorithmParameterSpecs);
            else
                keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            final PublicKey publicKey = keyPair.getPublic();
            final PrivateKey privateKey = keyPair.getPrivate();
            return new KeyPairs<byte[], byte[]>(publicKey.getEncoded(), privateKey.getEncoded());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    //MD2, MD5, SHA, MD4
    static String encrypt(String plaintext, String algorithm, boolean needBouncyCastleProvider) {
        if(needBouncyCastleProvider)
            Security.addProvider(new BouncyCastleProvider());
        return encrypt(plaintext, algorithm);
    }

    static void throwUnsupportedOperationException(String message) {
        throw new UnsupportedOperationException(message);
    }

    private static String encrypt(String str, String method) {
        MessageDigest md = null;
        String dstr = null;
        try {
            md = MessageDigest.getInstance(method);
            md.update(str.getBytes(UTF_8));
            dstr = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dstr;
    }

    public static String encryptAsymmetry(String src, byte[] key, String algorithm) {
        try {
            byte[] bytes = encrypt(src.getBytes(UTF_8), initPublicKey(key, algorithm), Cipher.ENCRYPT_MODE);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }

    public static String decryptAsymmetry(String dest, byte[] key, String algorithm) {
        try {
            byte[] bytes = encrypt(ArrayUtils.hex2Bytes(dest), initPrivateKey(key, algorithm), Cipher.DECRYPT_MODE);
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dest;
    }

    private static byte[] encrypt(byte[] bytes, byte[] key, String algorithm) {
        SecretKey secretKey = new SecretKeySpec(key, algorithm);
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static byte[] encrypt(byte[] bytes, Key key, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(mode, key);
            return cipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * BASE64
     *
     * @param src
     * @return
     * @throws Exception
     */
    public static String encodeBASE64(String src) {
        try {
            return new BASE64Encoder().encode(src.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }

    /**
     * BASE64
     *
     * @param dest
     * @return
     * @throws Exception
     */
    public static String decoderBASE64(String dest) {
        try {
            return new String(new BASE64Decoder().decodeBuffer(dest), UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }

    //hmac
    public static String encryptOnewayHMAC(String src, byte[] key, String algorithm) {
        try {
            byte[] bytes = encrypt(src.getBytes(UTF_8), key, algorithm);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }

    //Symmetry
    public static String encryptSymmetry(String src, byte[] key, String algorithm) {
        try {
            byte[] bytes = encrypt(src.getBytes(UTF_8), new SecretKeySpec(key, algorithm), Cipher.ENCRYPT_MODE);
            return ArrayUtils.bytes2Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }

    //Symmetry
    public static String decryptSymmetry(String dest, byte[] key, String algorithm) {
        try {
            byte[] bytes = encrypt(ArrayUtils.hex2Bytes(dest), new SecretKeySpec(key, algorithm), Cipher.DECRYPT_MODE);
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dest;
    }

    //ASymmetry
    public static String sign(String src, byte[] privateKey, String algorithm, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(initPrivateKey(privateKey, algorithm));
            signature.update(src.getBytes(UTF_8));
            return ArrayUtils.bytes2Hex(signature.sign());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return src;
    }

    //ASymmetry
    public static boolean verify(String src, byte[] publicKey, String sign, String algorithm, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(initPublicKey(publicKey, algorithm));
            signature.update(src.getBytes(UTF_8));
            return signature.verify(ArrayUtils.hex2Bytes(sign));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return false;
    }
}
