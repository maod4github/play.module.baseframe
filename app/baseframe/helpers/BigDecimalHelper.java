package baseframe.helpers;

import java.math.BigDecimal;

/**
 * BigDecimal助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年9月24日,下午8:48:44
 */
public class BigDecimalHelper {
	
	private BigDecimalHelper() {}
	
	/**
	 * 加<ul><li>欲做减法,{b}可传入负数</li><li>若{b}为空或不是数字,则返回{a}</li></ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月19日,下午2:24:41
	 * @param a
	 * @param b
	 * @return {a} + {b}
	 */
	public static BigDecimal add(BigDecimal a, Object b) {
		if (b == null) {
			return a;
		}
		String c = b.toString();
		if (!StringHelper.isNum(c)) {
			return a;
		}
		return a.add(new BigDecimal(c));
	}
	
	/**
	 * 乘<ul><li>若{b}为空或不是数字,则返回{a}</li></ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月19日,下午2:37:53
	 * @param a
	 * @param b
	 * @return {a} x {b}
	 */
	public static BigDecimal multiply(BigDecimal a, Object b) {
		if (b == null) {
			return a;
		}
		String c = b.toString();
		if (!StringHelper.isNum(c)) {
			return a;
		}
		return a.multiply(new BigDecimal(c));
	}
	
	/**
	 * 除<ul><li>若除不尽,则四舍五入保留两位小数</li></ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月21日,下午9:52:54
	 * @param a
	 * @param b
	 * @return {a} / {b}, Exception is null, Exception : {a}为空、{b}为空、{b}不是数字、{b}为零
	 */
	public static BigDecimal div(BigDecimal a, Object b) {
		try {
			return a.divide(new BigDecimal(b.toString()));
		}
		catch (Exception e) {
			return div(a, b, 2);
		}
	}
	
	/**
	 * 除<ul><li>舍去模式:四舍五入保留</li></ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月21日,下午9:52:54
	 * @param a
	 * @param b
	 * @param scale 保留小数位
	 * @return {a} / {b}, Exception is null, Exception : {a}为空、{b}为空、{b}不是数字、{b}为零
	 */
	public static BigDecimal div(BigDecimal a, Object b, Integer scale) {
		if (a == null || b == null) {
			return null;
		}
		String c = b.toString();
		if (!StringHelper.isNum(c)) {
			return null;
		}
		BigDecimal d = new BigDecimal(c);
		if (d.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return a.divide(d, scale, BigDecimal.ROUND_HALF_UP);
	}
	
}
