/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.utility;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.context.FacesContext;

/**
 *
 * @author hedersb
 */
public class Encrypter {

    private byte[] key;
    private SecretKey aesKey;;
    //private byte[] key = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("key").getBytes();
    //private SecretKey aesKey = new SecretKeySpec(key, "AES");

    private Cipher aesCipher;

    public Encrypter() {
        this.key = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("key").getBytes();
        this.aesKey = new SecretKeySpec(key, "AES");
    }
    
    public Encrypter(String keyParam) {
        this.key = keyParam.getBytes();
        this.aesKey = new SecretKeySpec(key, "AES");
    }

    /**
     * @return the aesCipher
     */
    public Cipher getAesCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (this.aesCipher == null) {
            this.aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            this.aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        }
        return this.aesCipher;
    }

    public byte[] encrypt(String text) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        return this.getAesCipher().doFinal(text.getBytes());

    }

    public boolean compare(String text, byte[] bytes) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] password = this.encrypt(text);

        if (password.length == bytes.length) {
            boolean equals = true;
            int i = 0;
            while (i < password.length && equals) {
                equals &= password[i] == bytes[i];
                i++;
            }
            return equals;
        } else {
            return false;
        }

    }

}
