package IRCLibrary.SharedClasses;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {    
    public static final String ALGORITHM = "RSA";
    private PublicKey mPublicKey;
    private PrivateKey mPrivateKey;
    
    public void generateKey() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();
            mPublicKey = key.getPublic();
            mPrivateKey = key.getPrivate();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] encrypt(String text, PublicKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = null;
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipherText = cipher.doFinal(text.getBytes());
        return cipherText;
    }
    
    public static byte[] encrypt(byte[] text, PublicKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = null;
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipherText = cipher.doFinal(text);
        return cipherText;
    }
    
    public static String decryptS(byte[] text, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] dectyptedText;
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        dectyptedText = cipher.doFinal(text);
        return new String(dectyptedText);
    }
    
    public static byte[] decryptB(byte[] text, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] dectyptedText;
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        dectyptedText = cipher.doFinal(text);
        return dectyptedText;
    }
    
    public static PublicKey byteArrayToPublicKey(byte[] array) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(array));
    }

    public PublicKey getPublicKey() {
        return mPublicKey;
    }

    public byte[] getPublicKeyB() {
        return mPublicKey.getEncoded();
    }

    public PrivateKey getPrivateKey() {
        return mPrivateKey;
    }
}