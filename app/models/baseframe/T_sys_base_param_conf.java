package models.baseframe;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 系统基本参数配置
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月26日,上午11:07:14
 */
@Entity(name="t_sys_base_param_conf")
public class T_sys_base_param_conf extends BaseModel {

	/** 代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月26日,上午11:07:31 */
	public String code;
	
	/**
	 * 代码枚举
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月18日,上午10:59:57
	 */
	public enum CodeEnum {

		/** sms_acco, 短信通道账号<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:03:06 */
		_dxtdzh("sms_acco"),

		/** sms_pwd, 短信通道密码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:03:14 */
		_dxtdmm("sms_pwd"),
		
		/** ems_website, 电子邮件服务站点<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:40:40 */
		_dzyjfwzd("ems_website"),
		
		/** ems_acco, 电子邮件服务账号<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:41:08 */
		_dzyjfwzh("ems_acco"),

		/** ems_pwd, 电子邮件服务密码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:41:08 */
		_dzyjfwmm("ems_pwd"),
		
		/** ems_pop3, 电子邮件POP3服务器<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:41:08 */
		_dzyjpop3fwq("ems_pop3"),
		
		/** ems_smtp, 电子邮件SMTP服务器<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,上午11:41:08 */
		_dzyjsmtpfwq("ems_smtp"),
		
		;

		private String val;

		private CodeEnum(String val) {
			this.val = val;
		}

		public String getVal() {
			return this.val;
		}
		
		public static CodeEnum enumOf(String val) {
			for (CodeEnum e : CodeEnum.values()) {
				if (e.getVal().equals(val)) {
					return e;
				}
			}
			return null;
		}

	}

	/** 值<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月26日,上午11:07:38 */
	public String val;

	/** 描述<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月26日,上午11:08:09 */
	public String description;
	
	/** 插入时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月26日,上午11:09:35 */
	public Date insert_time;
	
	/** 更新时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月26日,上午11:09:41 */
	public Date update_time;

}