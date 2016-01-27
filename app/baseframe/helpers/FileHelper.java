package baseframe.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import baseframe.enums.DocuExtNameEnum;
import baseframe.enums.FileSizeUnitEnum;
import baseframe.enums.ImgExtNameEnum;

/**
 * 文件助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月8日,下午12:07:16
 */
public class FileHelper {
    
    private FileHelper () {}
    
    /**
     * 获取文件简单名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月11日,下午6:28:43
     * @param fullname 全名
     * @return The simpname or null
     */
    public static String getSimpname(String fullname) {
        if (!StringHelper.isValid(fullname)) {
            return null;
        }
        int index = fullname.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        return fullname.substring(0, index);
    }
    
    /**
     * 获取文件简单名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月11日,下午6:18:51
     * @param file 文件对象
     * @return The simpname or null
     */
    public static String getSimpname(File file) {
        if (file == null) {
            return null;
        }
        return getSimpname(file.getName());
    }
    
    /**
     * 获取文件扩展名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月11日,下午6:30:04
     * @param fullname 全名
     * @return
     */
    public static String getExtname(String fullname) {
        if (!StringHelper.isValid(fullname)) {
            return null;
        }
        int index = fullname.lastIndexOf(".");
        if (index < 0) {
            return "";
        }
        return fullname.substring(index + 1);
    }
    
    /**
     * 获取文件扩展名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月8日,下午12:07:53
     * @param file 文件对象
     * @return
     */
    public static String getExtname(File file) {
        if (file == null) {
            return null;
        }
        return getExtname(file.getName());
    }
    
    /**
     * 是否是图片
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月12日,上午11:15:29
     * @param fullname 全名
     * @return
     */
    public static boolean isImg(String fullname) {
        String extname = getExtname(fullname);
        return ImgExtNameEnum.contains(extname);
    }
    
    /**
     * 是否是图片
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月8日,下午12:29:12
     * @param file 文件对象
     * @return
     */
    public static boolean isImg(File file) {
        if (file == null) {
            return false;
        }
        return isImg(file.getName());
    }
    
    /**
     * 是否是文档
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月12日,上午11:39:44
     * @param fullname
     * @return
     */
    public static boolean isDocu(String fullname) {
        String extname = getExtname(fullname);
        return DocuExtNameEnum.contains(extname);
    }
    
    /**
     * 是否是文档
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月12日,上午11:43:54
     * @param file
     * @return
     */
    public static boolean isDocu(File file) {
        if (file == null) {
            return false;
        }
        return isDocu(file.getName());
    }
    
    /**
     * 计算大小
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月19日,下午3:58:10
     * @param file 
     * @param unit 单位(默认Byte)
     * @return
     */
    public static Double calcSize(File file, FileSizeUnitEnum unit) {
        if (file == null) {
            return 0D;
        }
        if (unit == null || FileSizeUnitEnum.Byte.equals(unit)) {
            return Double.valueOf(file.length());
        }
        BigDecimal bd = new BigDecimal(file.length());
        return bd.divide(unit.getVal(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static boolean replaceAll(String regex, String replacement, File src, File dest) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(src));
		BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
		String text = null;
		while ((text = br.readLine()) != null) {
			bw.write(text.replaceAll(regex, replacement) + "\n");
		}
		br.close();
		bw.close();
		return true;
    }

}
