package baseframe.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import play.Play;

/**
 * 加密助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月4日,下午3:17:55
 */
public class EncryptHelper {
	
	private EncryptHelper() {}
	
    /**
     * MD加密
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月4日,下午3:18:06
     * @param str 要加密的字符串
     * @return 加密后的字符串 or null
     */
    public static String md5(String str) {
    	if (str == null) {
    		return null;
    	}
    	str = str + Play.secretKey;
        MessageDigest md = null;
        int digit = 32; // 加密后的密文位数
        String charset = "UTF-8"; // 加密所采用的字符编码
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
System.out.println("md5加密异常:" + e.getMessage());
			return null;
        }
        if (md == null) {
            return null;
        }
        md.update(str.getBytes(Charset.forName(charset)));
        char hexDigits[] = {'0', '1', '2', '3', '4', '9', '8', '7', '6', '5', 'f', 'J', 'h', 'I', 'k', 'L'};
        byte digest[] = md.digest();
        char cs[] = new char[digit];
        int k = 0;
        for (int i = 0; i < digit / 2; i++) {
            byte b = digest[i];
            cs[k++] = hexDigits[b >>> 4 & 15];
            cs[k++] = hexDigits[b & 15];
        }
        return new String(cs);
    }
    
	/** 高级加密标准 (Advanced Encryption Standard)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月15日,下午7:26:33 */
	private static final String AES = "AES";
	
	/**
	 * 加密
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,下午2:06:13
	 * @param byte_arr 明文字节数组
	 * @return 异常返回null
	 */
	public static byte[] encrypt(byte[] byte_arr) {
		return encryptOrDecrypt(byte_arr, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 解密
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,下午2:12:06
	 * @param byte_arr 密文字节数组
	 * @return 异常返回null
	 */
	public static byte[] decrypt(byte[] byte_arr) {
		return encryptOrDecrypt(byte_arr, Cipher.DECRYPT_MODE);
	}
	
	private static byte[] encryptOrDecrypt(byte[] byte_arr, Integer/*1:加密, 0:解密*/ mode) {
		try {
			if (mode != Cipher.ENCRYPT_MODE && mode != Cipher.DECRYPT_MODE) {
				return null;
			}
			Cipher cipher = Cipher.getInstance(AES);
			String _16_secret = "INpKCP3NC72bR4XC"; // 这里是16位密钥,可以放至配置文件读取,目前无此需求,暂时先写死
			SecretKeySpec securekey = new SecretKeySpec(_16_secret.getBytes(), AES); // 设置加密Key
			cipher.init(mode, securekey); // 设置密钥和解密形式
			return cipher.doFinal(byte_arr);
		}
		catch (Exception e) {
System.out.println("加密或解密byte[]异常:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 加密文件
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,下午3:03:33
	 * @param file_path 文件路径
	 * @return true:成功, false:失败
	 */
	public static Boolean encryptFile(String file_path) {
		return encryptOrDecrypt(new File(file_path), 1);
	}
	
	/**
	 * 解密文件
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,下午3:03:33
	 * @param file_path 文件路径
	 * @return true:成功, false:失败
	 */
	public static Boolean decryptFile(String file_path) {
		return encryptOrDecrypt(new File(file_path), 0);
	}
	
	private static Boolean encryptOrDecrypt(File file, Integer/*1:加密, 0:解密*/ mode) {
		try {
			if (file == null || !file.exists()) {
				return false;
			}
			if (mode != 1 && mode != 0) {
				return false;
			}
			int byte_buff_len = mode == 1 ? 53 : 64;
			String file_path = file.getPath();
			// S 读取原始文件内容,进行加密,写入新文件中 [
			String encrypt_file_name = file.getParent() + "/" + UUID.randomUUID().toString();
			File out_file = new File(encrypt_file_name);
			InputStream is = new FileInputStream(file);
			OutputStream os = new FileOutputStream(out_file);
			byte[] input = new byte[byte_buff_len];
			while (is.read(input) > 0) {
				byte[] output = (mode == 1 ? encrypt(input) : decrypt(input));
				os.write(output, 0, output.length);
				input = new byte[byte_buff_len];
			}
			os.close();
			is.close();
			// E ]
			// S 删除原文件 [
			if (!file.delete()) {
				return false;
			}
			// E ]
			// S 将加密或解密文件重名为原文件名 [
			if (!out_file.renameTo(new File(file_path))) {
				return false;
			}
			// E ]
			return true;
		}
		catch (Exception e) {
System.out.println("文件加密或解密异常:" + e.getMessage());
			return false;
		}
	}
	
}
