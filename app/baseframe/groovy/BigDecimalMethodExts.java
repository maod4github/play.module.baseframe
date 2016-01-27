package baseframe.groovy;

import java.math.BigDecimal;

import play.templates.JavaExtensions;
import baseframe.helpers.BigDecimalHelper;

/**
 * BigDecimal对象的模版方法扩展集
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年9月21日,下午9:53:08
 */
public class BigDecimalMethodExts extends JavaExtensions {
	
	/**
	 * 除 (若{b}为空或0,则返回null)
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月21日,下午9:52:54
	 * @param a
	 * @param b
	 * @return {a} / {b}
	 */
	public static BigDecimal div(BigDecimal a, BigDecimal b) {
		return BigDecimalHelper.div(a, b);
	}
	
	/**
	 * 乘
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月21日,下午9:53:31
	 * @param a
	 * @param b
	 * @return a x b
	 */
	public static BigDecimal mul(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}

}
