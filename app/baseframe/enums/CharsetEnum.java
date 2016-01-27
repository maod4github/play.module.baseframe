package baseframe.enums;

/**
 * 字符集枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月30日,下午4:42:34
 */
public enum CharsetEnum {

	_GBK("GBK"),
	
	_UTF_8("UTF-8"), 
	
	_GB2312("GB2312"), 
	
	_ISO_8859_1("ISO-8859-1"),
	
	;

	private String val;

	private CharsetEnum(String val) {
		this.val = val;
	}

	public String getVal() {
		return this.val;
	}

}