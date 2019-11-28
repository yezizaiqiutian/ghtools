package com.gh.commonlib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {
    /**
     * 服务器端验证密码方法
     * @param source
     * @return
     */
    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = {       
           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
         try
         {
          MessageDigest md = MessageDigest.getInstance( "MD5" );
          md.update( source );
          byte tmp[] = md.digest();         
                                                      
          char str[] = new char[16 * 2];   
                                                       
          int k = 0;                                
          for (int i = 0; i < 16; i++) {         
                                                      
           byte byte0 = tmp[i];                 
           str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                                                                
           str[k++] = hexDigits[byte0 & 0xf];           
          }
          s = new String(str);

         }catch( Exception e )
         {
          e.printStackTrace();
         }
         return s;
       }
    
    /**
     * 客户端上传数据用
     * @param content
     * @return
     */
	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }
    public static String encrypt16(String encryptStr) {
        return encrypt32(encryptStr).substring(8, 24);
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
}
