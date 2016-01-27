package models.baseframe;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 消息通知模板
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月9日,下午10:31:04
 */
@Entity(name="t_msg_notice_template")
public class T_msg_notice_template extends BaseModel {
	
	/** 代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午10:34:51 */
	public String code;
	
	/** 类别 (10:未知, 11:系统, 12:自定义)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午9:03:01 */
	public Integer category;
	
	/**
	 * 类别枚举
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月10日,上午11:14:28
	 */
	public enum Category {
		
		/** 10, 未知, WZ_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_wz(10, "未知", "WZ_"),
		
		/** 11, 系统, S_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_xt(11, "系统", "S_"),
		
		/** 12, 自定义, C_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_zdy(12, "自定义", "C_"),
		
		;
		
		private Integer val;
		private String text;
		private String prefix;
		
		private Category(Integer val, String text, String prefix) {
			this.val = val;
			this.text = text;
			this.prefix = prefix;
		}
		
		public Integer getVal() {
			return this.val;
		}
		
		public String getText() {
			return this.text;
		}
		
		public String getPrefix() {
			return this.prefix;
		}
		
	}
	
	/** 类型 (10:未知, 11:短信, 12:邮件, 13:站内信)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午8:03:23 */
	public Integer type;
	
	/**
	 * 类型枚举
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月10日,上午11:14:28
	 */
	public enum Type {
		
		/** 10, 未知, WZ_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_wz(10, "未知", "WZ_"),
		
		/** 11, 短信, SM_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_dx(11, "短信", "SM_"),
		
		/** 12, 电子邮件, EM_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_dzyj(12, "电子邮件", "EM_"),
		
		/** 13, 站内信, IL_<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月10日,上午11:13:28 */
		_znx(13, "站内信", "IL_"),
		
		;
		
		private Integer val;
		private String text;
		private String prefix;
		
		private Type(Integer val, String text, String prefix) {
			this.val = val;
			this.text = text;
			this.prefix = prefix;
		}
		
		public Integer getVal() {
			return this.val;
		}
		
		public String getText() {
			return this.text;
		}

		public String getPrefix() {
			return this.prefix;
		}

	}
	
	/** 标题<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午8:07:24 */
	public String title;
	
	/** 内容<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午8:07:36 */
	public String cont;
	
	/** 应用场景<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午8:07:41 */
	public String use_scene;
	
	/** 是否有效 (1:true:是, 0:false:否)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午10:28:35 */
	public Boolean is_valid = true;
	
	/** 插入时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午8:07:55 */
	public Date insert_time;
	
	/** 更新时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月9日,下午8:08:02 */
	public Date update_time;

}
