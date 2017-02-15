package Interface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: MD5 Class
 */
public class CryptWithMD5 {
    private static MessageDigest instances;

    public static String cryptWithMD5(String pass){
        try {
            instances = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            instances.reset();
            byte[] hashed = instances.digest(passBytes);
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<hashed.length;i++){
                stringBuffer.append(Integer.toHexString(0xff & hashed[i]));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }
}
