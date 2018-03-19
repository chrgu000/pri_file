

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Encoder;

public class EncoderPwd {

	 public final static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

	        try {
	            byte[] btInput = s.getBytes();
	            // 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // 使用指定的字节更新摘要
	            mdInst.update(btInput);
	            // 获得密文
	            byte[] md = mdInst.digest();
	            // 把密文转换成十六进制的字符串形式
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	  //算法名称 
	    public static final String KEY_ALGORITHM = "DES";
	    //算法名称/加密模式/填充方式 
	    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

	    /**
	     *   
	     * 生成密钥key对象
	     * @param KeyStr 密钥字符串 
	     * @return 密钥对象 
	     * @throws InvalidKeyException   
	     * @throws NoSuchAlgorithmException   
	     * @throws InvalidKeySpecException   
	     * @throws Exception 
	     */
	    private static SecretKey keyGenerator(String keyStr) throws Exception {
	        byte input[] = HexString2Bytes(keyStr);
	        DESKeySpec desKey = new DESKeySpec(input);
	        //创建一个密匙工厂，然后用它把DESKeySpec转换成
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey securekey = keyFactory.generateSecret(desKey);
	        return securekey;
	    }

	    private static int parse(char c) {
	        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
	        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
	        return (c - '0') & 0x0f;
	    }

	    // 从十六进制字符串到字节数组转换 
	    public static byte[] HexString2Bytes(String hexstr) {
	        byte[] b = new byte[hexstr.length() / 2];
	        int j = 0;
	        for (int i = 0; i < b.length; i++) {
	            char c0 = hexstr.charAt(j++);
	            char c1 = hexstr.charAt(j++);
	            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
	        }
	        return b;
	    }

	    /** 
	     * 加密数据
	     * @param data 待加密数据
	     * @param key 密钥
	     * @return 加密后的数据 
	     */
	    public static String encrypt(String data, String key) throws Exception {
	        Key deskey = keyGenerator(key);
	        // 实例化Cipher对象，它用于完成实际的加密操作
	        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	        SecureRandom random = new SecureRandom();
	        // 初始化Cipher对象，设置为加密模式
	        cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
	        byte[] results = cipher.doFinal(data.getBytes());
	        // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
	        for (int i = 0; i < results.length; i++) {
	            System.out.print(results[i] + " ");
	        }
	        System.out.println();
	        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
	        return new  BASE64Encoder().encode(results);
	    }

	   
}
