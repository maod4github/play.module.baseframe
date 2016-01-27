package baseframe.groovy;

import java.util.regex.Pattern;

import play.templates.JavaExtensions;
import baseframe.enums.FileSizeUnitEnum;
import baseframe.helpers.AtcmtHelper;
import baseframe.helpers.FileHelper;
import baseframe.helpers.StringHelper;

/**
 * String对象的模版方法扩展集
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年9月7日,下午12:25:24
 */
public class StringExts extends JavaExtensions {
	
	/**
     * 获取附件原始全名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月7日,下午12:31:00
     * @param fullname 全名
     * @return
     */
	public static String getAtcmtOriFullname(String fullname) {
		if (!StringHelper.isValidForTrim(fullname)) {
			return "";
		}
		fullname = fullname.trim();
		return AtcmtHelper.getOriFullname(fullname);
	}
	
	/**
     * 获取附件原始简单名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月7日,下午12:31:00
     * @param fullname 全名
     * @return
     */
	public static String getAtcmtOriSimpname(String fullname) {
		return FileHelper.getSimpname(getAtcmtOriFullname(fullname));
	}
	
	/**
	 * 获取附件原始扩展名
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月26日,上午11:10:28
	 * @param fullname 全名
	 * @return
	 */
	public static String getAtcmtOriExtname(String fullname) {
		return FileHelper.getExtname(fullname);
	}
	
	/**
	 * 根据{fullname}获取附件大小
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月26日,下午12:07:27
	 * @param fullname 附件全名
	 * @return the size or null
	 */
	public static Double getAtcmtSize(String fullname) {
		return AtcmtHelper.getSize(fullname, FileSizeUnitEnum.KB);
	}
	
	/**
	 * 获取附件src<br>
	 * <b>作者 : </b>Morton<br>
	 * <b>创建时间 : </b>2015年12月7日,下午11:36:19
	 * @param fullname
	 * @return
	 */
	public static String getAtcmtSrc(String fullname) {
		return AtcmtHelper.PATH_STORE_IMGS + fullname;
	}
	
	/**
	 * 获取高质量图片全名<br>
	 * <b>作者 : </b>Administrator<br>
	 * <b>创建时间 : </b>2015年12月7日,下午3:26:08
	 * @param fullname
	 * @return
	 */
	public static String getHFullname(String fullname) {
		String simpname = FileHelper.getSimpname(fullname);
		String extname = FileHelper.getExtname(fullname);
		return simpname + "-h." + extname;
	}
	
	/**
	 * 获取中高质量图片全名<br>
	 * <b>作者 : </b>Administrator<br>
	 * <b>创建时间 : </b>2015年12月7日,下午3:28:19
	 * @param fullname
	 * @return
	 */
	public static String getMHFullname(String fullname) {
		String simpname = FileHelper.getSimpname(fullname);
		String extname = FileHelper.getExtname(fullname);
		return simpname + "-mh." + extname;
	}
	
	/**
	 * 获取中质量图片全名<br>
	 * <b>作者 : </b>Administrator<br>
	 * <b>创建时间 : </b>2015年12月7日,下午3:28:48
	 * @param fullname
	 * @return
	 */
	public static String getMFullname(String fullname) {
		String simpname = FileHelper.getSimpname(fullname);
		String extname = FileHelper.getExtname(fullname);
		return simpname + "-m." + extname;
	}
	
	/**
	 * 获取中低质量图片全名<br>
	 * <b>作者 : </b>Administrator<br>
	 * <b>创建时间 : </b>2015年12月7日,下午3:29:07
	 * @param fullname
	 * @return
	 */
	public static String getMLFullname(String fullname) {
		String simpname = FileHelper.getSimpname(fullname);
		String extname = FileHelper.getExtname(fullname);
		return simpname + "-ml." + extname;
	}
	
	/**
	 * 获取低质量图片全名<br>
	 * <b>作者 : </b>Administrator<br>
	 * <b>创建时间 : </b>2015年12月7日,下午3:29:27
	 * @param fullname
	 * @return
	 */
	public static String getLFullname(String fullname) {
		String simpname = FileHelper.getSimpname(fullname);
		String extname = FileHelper.getExtname(fullname);
		return simpname + "-l." + extname;
	}
	
	/**
	 * 获取缩略图全名<br>
	 * <b>作者 : </b>Administrator<br>
	 * <b>创建时间 : </b>2015年12月7日,下午3:29:27
	 * @param fullname
	 * @return
	 */
	public static String getBFullname(String fullname) {
		String simpname = FileHelper.getSimpname(fullname);
		String extname = FileHelper.getExtname(fullname);
		return simpname + "-b." + extname;
	}
	
    /**
     * 提取@url中的参数值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月7日,下午12:25:38
     * @param url
     * @param key 参数key
     * @return
     */
    public static String extrURLParamVal(String url, String key) {
		return StringHelper.extrURLParamVal(url, key);
    }

    /**
     * 移除@html中所有的HTML标签
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月7日,下午12:33:25
     * @param html
     * @return
     */
	public static String removeAllHTMLTag(String html) {
		return StringHelper.removeAllHTMLTag(html);
	}
	
	/**
	 * 截取文本 (截取前,会先移除{str}中所有的HTML标签)
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年11月4日,下午2:08:51
	 * @param str
	 * @param max_len
	 * @return
	 */
	public static String cutText(String str, Integer max_len) {
		if (max_len == null || str == null) {
			return "";
		}
		str = StringHelper.removeAllHTMLTag(str);
		if (max_len >= str.length()) {
			return str;
		}
		return str.substring(0, max_len) + "...";
	}
	
	/**
	 * 隐藏手机号码 (中间五位)
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年11月30日,上午9:42:43
	 * @param str
	 * @return
	 */
	public static String hideMobileNo(String str) {
		if (StringHelper.isMobileNo(str)) {
			return Pattern.compile("(\\d{3})(\\d+)(\\d{3})").matcher(str).replaceAll("$1*****$3");
		}
		return str;
	}

}
