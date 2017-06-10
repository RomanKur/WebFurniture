/**
 * Класс шифрует пароль алгоритмом SHA-256.
 * 
 */
package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author Roman Kurtsanov
 * Java класс обеспечивающий шифрование пароля пользователя приложения.
 */
public class EncriptPass {
    private String encriptPassword;
    
    /**
     * Метод шифрующий пароль пользователя.
     * @param password - шифруемый пароль (String)
     * @param salts - соль (String)
     * @throws NoSuchAlgorithmException 
     */
    public EncriptPass(String password, String salts) throws NoSuchAlgorithmException {
        setEncriptPassword(password,salts);
    }

    public EncriptPass(String password) throws NoSuchAlgorithmException {
        setEncriptPassword(password,"0");
    }

    public EncriptPass() {
    }

    public void setEncriptPassword(String password, String salts) throws NoSuchAlgorithmException {
        password=salts+password;
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        m.update(password.getBytes(),0,password.length());
        this.encriptPassword = new BigInteger(1,m.digest()).toString(16);
    }

    /**
     * 
     * @return - зашифрованный пароль (SHA-256) (String)
     */
    public String getEncriptPassword(){
        return this.encriptPassword;
    }
    /**
     * Метод получения математической переменной для шифрования пароля.
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public String getSalts() throws NoSuchAlgorithmException {
        Date time = new Date(132323L);
        
        String s = Long.toString(time.getTime());
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());
        return  new BigInteger(1,m.digest()).toString(16);
    }
    
}
