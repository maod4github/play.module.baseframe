package baseframe.helpers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import baseframe.enums.NumPatternEnum;

/**
 * 数字助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月13日,上午11:22:16
 */
public class NumHelper {

    private NumHelper() {}
    
    /**
     * 四舍五入
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:30:37
     * @param num
     * @param scale 精度
     * @return
     */
    public static float round(float num, int scale) {
        return round(new BigDecimal(num), scale).floatValue();
    }
    
    /**
     * 四舍五入
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:30:37
     * @param num
     * @param scale 精度
     * @return
     */
    public static double round(double num, int scale) {
        return round(new BigDecimal(num), scale).doubleValue();
    }
    
    /**
     * 四舍五入
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:30:37
     * @param num
     * @param scale 精度
     * @return
     */
    public static BigDecimal round(BigDecimal num, int scale) {
        if (num == null) {
            return null;
        }
        return num.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 格式化
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:54:36
     * @param num
     * @param pattern 模式
     * @return
     */
    public static String format(int num, NumPatternEnum pattern) {
        return format(new BigDecimal(num), pattern);
    }
    
    /**
     * 格式化
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:54:36
     * @param num
     * @param pattern 模式
     * @return
     */
    public static String format(long num, NumPatternEnum pattern) {
        return format(new BigDecimal(num), pattern);
    }
    
    /**
     * 格式化
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:54:36
     * @param num
     * @param pattern 模式
     * @return
     */
    public static String format(float num, NumPatternEnum pattern) {
        return format(new BigDecimal(num), pattern);
    }
    
    /**
     * 格式化
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:54:36
     * @param num
     * @param pattern 模式
     * @return
     */
    public static String format(double num, NumPatternEnum pattern) {
        return format(new BigDecimal(num), pattern);
    }
    
    /**
     * 格式化
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午11:54:36
     * @param num
     * @param pattern 模式
     * @return
     */
    public static String format(BigDecimal num, NumPatternEnum pattern) {
        if (num == null) {
            return null;
        }
        return new DecimalFormat(pattern.getVal()).format(num);
    }
    
}
