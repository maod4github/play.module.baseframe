package models.baseframe;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 消息通知
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月25日,下午9:53:42
 */
@Entity(name="t_msg_notice")
public class T_msg_notice extends BaseModel {

	/** 类型 (10:未知, 11:短信, 12:电子邮箱, 13:站内信)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:08 */
	public Integer type = TypeEnum._wz.getVal();
	
	/**
	 * 类型枚举
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年9月10日,上午11:31:16
	 */
	public enum TypeEnum {

		/** 10, 未知<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月10日,上午11:30:27 */
		_wz(10, "未知"),
		
		/** 11, 短信<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月10日,上午11:30:27 */
		_dx(11, "短信"),
		
		/** 12, 电子邮件<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月10日,上午11:30:27 */
		_dzyj(12, "电子邮件"),
		
		/** 13, 站内信<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月10日,上午11:30:27 */
		_znx(13, "站内信"),

		;

		private Integer val;
		private String text;

		private TypeEnum(int val, String text) {
			this.val = val;
			this.text = text;
		}

		public Integer getVal() {
			return this.val;
		}

		public String getText() {
			return this.text;
		}
		
	}
	
	/** 发送用户id (0:管理员)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月18日,下午5:25:20 */
	public Long from_uid = 0L;

	/** 发送管理员id (0:系统)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月26日,下午9:27:12 */
	public Long from_admin_id = 0L;
	
	/** 接收用户id<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:18 */
	public Long to_uid = 0L;

	/** 接收用户手机号码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:28 */
	public String to_mobile_no;

	/** 接收用户电子邮箱地址<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:35 */
	public String to_email_addr;

	/** 标题<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:42 */
	public String title;

	/** 内容<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:49 */
	public String cont;

	/** 是否已发送 (1:true:是, 0:false:否)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:54:56 */
	public Boolean is_sent = false;
	
	/** 是否已读 (1:true:是, 0:false:否)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月4日,下午1:59:06 */
	public Boolean is_read = false;

	/** 插入时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:55:59 */
	public Date insert_time;
	
	/** 更新时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:56:05 */
	public Date update_time;
	
	/** 上次发送时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:55:51 */
	public Date last_sent_time;
	
	/** 上次发送失败时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:55:44 */
	public Date last_sent_fail_time;

	/** 上次发送失败原因<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:55:37 */
	public String last_sent_fail_reason;

	/** 失败次数<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月25日,下午9:55:29 */
	public Integer fail_count = 0;

}