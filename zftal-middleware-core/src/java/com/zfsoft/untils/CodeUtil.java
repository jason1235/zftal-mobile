package com.zfsoft.untils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;  
import java.util.Date;

import javax.crypto.Cipher;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESedeKeySpec;  
import javax.crypto.spec.IvParameterSpec;  

import org.junit.Test;


public class CodeUtil {
	
    // 向量  
    private final static String iv = "76543210";  
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8";
    
    /**
     * 3DES加密
     * @param plainText	  待加密内容
     * @param secretKey  密钥
     */
    public static String encode(String plainText,String secretKey) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
        return Base64.encode(encryptData);  
    } 
    
    /**
     * 3DES解密
     * @param plainText	  待解密内容
     * @param secretKey  密钥
     */
    public static String decode(String encryptText,String secretKey) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
  
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));  
  
        return new String(decryptData, encoding);  
    }  
    
    public static void main(String[] aaa){
    	String apptoken = "d83afd86c2a08dbb231d2138223afc87";
    	try {
    		String username = null;
    		String start = null;
    		String size = null;
    		String status = null;
    		String haveEvaluate = null;
    		String problem = "";
    		String telephone = "";
    		/*String voteTitle = null;
    		String voteType = null;
    		String voteIsMultiSelect = null;
    		String voteIsAnonymous = null;
    		String voteResultOnlySee = null;
    		String voteEndDate = null;
    		String voteMaxScore = null;*/
    		
    		//System.out.println(java.net.URLEncoder.encode("http://www.baidu.com", "utf-8"));
    		
			username = encode("20141001", apptoken);
			start  = encode("1", apptoken);
			size   = encode("10", apptoken);
			status = encode("0", apptoken);
			haveEvaluate = encode("", apptoken);
			
			problem = encode("你有问题", apptoken);
			telephone = encode("15168443924", apptoken);
			/*voteTitle = encode("我最喜欢的运动", apptoken);
			voteType = encode("0", apptoken);
			voteIsMultiSelect = encode("0", apptoken);
			voteIsAnonymous = encode("0", apptoken);
			voteResultOnlySee = encode("0", apptoken);
			voteEndDate = encode(System.currentTimeMillis()+"", apptoken);
			voteMaxScore = encode("0", apptoken);
			String mineVoteFlag = encode("1", apptoken);*/
			
            //System.out.println("aaa:"  + java.net.URLDecoder.decode(java.net.URLEncoder.encode(test, "utf-8"), "utf-8"));
			System.out.print("?username="+java.net.URLEncoder.encode(username, "utf-8"));
			System.out.print("&start="+java.net.URLEncoder.encode(start, "utf-8"));
			System.out.print("&size="+java.net.URLEncoder.encode(size, "utf-8"));
			System.out.print("&status="+java.net.URLEncoder.encode(status, "utf-8"));
			System.out.print("&haveEvaluate="+java.net.URLEncoder.encode(haveEvaluate, "utf-8"));
			System.out.print("&apptoken="+apptoken);
			/*System.out.println(java.net.URLEncoder.encode(voteTitle, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(voteType, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(voteIsMultiSelect, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(voteIsAnonymous, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(voteResultOnlySee, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(voteEndDate, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(voteMaxScore, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(mineVoteFlag, "utf-8"));
			System.out.println(java.net.URLEncoder.encode(encode("60605DE241EC1C90E0538513470ABA49", apptoken), "utf-8"));*/
			System.out.println();
			String ttt =  URLEncoder.encode("紫金港", "UTF-8");
			System.out.println(decode("tsmf92e/7b7bhYFjt+jQ7w==", "97cd574121a665cc5308690a98c37fcf"));
			System.out.println(java.net.URLEncoder.encode("tsmf92e/7b7bhYFjt+jQ7w==", "utf-8"));
			
			//System.out.println(decode("9sJ2qag3wxkuPfJ7yTDViJ+Y60A4RnMl", "ZF"));
			System.out.println(ttt);
			System.out.println(java.net.URLEncoder.encode(CodeUtil.encode("459", "54bcd0a3f2f1bc366a352d0e05f88990"),"utf-8"));
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void test() throws Exception{
    	String apptoken = "ff76110045b149c5239ad318361bcdba";
    	String a = "";
    	a = encode("1053", apptoken);
    	System.out.println("a=" + a);
    	
    	String b = "";
    	a = encode("yang620", apptoken);
    	System.out.println("a=" + a);
    	
    }
    
}
