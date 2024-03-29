package com.csis3175.fleamart.model;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Encrypt {
    //https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
    //https://stackoverflow.com/questions/27843642/store-and-validate-hashed-password-with-salt

    /**Hashing password with salt*/
    public static String hashPassword(String textPassword){
        try{
            StringBuilder textHash = new StringBuilder();
//            SecureRandom random = new SecureRandom();
//            byte[] saltValue = new byte[16];
//            random.nextBytes(saltValue);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            digest.update(saltValue);
            byte[] hash = digest.digest(textPassword.getBytes(StandardCharsets.UTF_8));

            //Create text of hash+salt
            for (byte b : hash ){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length()==1){
                    textHash.append('0');
                }
                textHash.append(hex);
            }
            return textHash.toString();

        }
        catch(NoSuchAlgorithmException e){

            return null;
        }
    }

    public static boolean validatePassword(String textPassword, String hashPassword){
        String hashedInputPassword = hashPassword(textPassword);
        Log.d("DecryptTest","hashconversion = "+hashedInputPassword);

        return hashedInputPassword != null && hashedInputPassword.equals(hashPassword);


    }


}
