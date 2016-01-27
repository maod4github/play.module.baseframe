package baseframe.enums;

import play.libs.Codec;

/**
 * 缓存键前缀枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年11月26日,上午9:42:34
 */
public enum CacheKeyPrefixEnum {
	
	/** 图片验证码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月26日,上午9:42:00 */
	_1001(Codec.UUID()),
	
	/** 短信验证码<br /><b>作者 : </b>Administrator<br /><b>创建时间 : </b>2015年11月29日,下午9:10:24 */
	_1002(Codec.UUID()),
	
	/** 当前用户<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月26日,上午9:42:00 */
	_1003(Codec.UUID()),
	
	/** 当前用户登录标记<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月3日,下午4:39:14 */
	_1004(Codec.UUID()),
	
	/** 当前管理员<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月26日,上午9:42:00 */
	_1005(Codec.UUID()),
	
	/** 当前管理员登录标记<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月3日,下午4:39:14 */
	_1006(Codec.UUID()),
	
	;
	
	private String val;
	
	private CacheKeyPrefixEnum(String val) {
		this.val = val;
	}

	public String getVal() {
		return this.val;
	}
	
}
