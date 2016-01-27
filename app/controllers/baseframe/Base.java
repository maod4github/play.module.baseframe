package controllers.baseframe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;

import org.apache.commons.lang.RandomStringUtils;

import play.cache.Cache;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.libs.WS;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Scope.Flash;
import play.mvc.Scope.Params;
import play.mvc.Scope.RenderArgs;
import play.mvc.Scope.RouteArgs;
import play.mvc.Scope.Session;
import play.mvc.Util;
import baseframe.consts.CommonConst;
import baseframe.enums.CacheKeyPrefixEnum;
import baseframe.helpers.StringHelper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 基础控制器 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月3日,下午6:17:21
 */
public class Base extends Controller {

	@Before(priority = 0)
	private static final void appendRenderArgsInterceptor() {
		renderArgs.put("preloadImgSrc", CommonConst.PRELOAD_IMG_SRC); // 预加载图片src
	}

	public static final class ResInfo {

		public int code;

		public String msg;

		public final Map<String, Object> data = new HashMap<String, Object>(0);

		public ResInfo() {
			this.code = 0;
			this.msg = "";
		}

		public ResInfo(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public final void set(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public final void reset() {
			this.code = 0;
			this.msg = "";
			this.data.clear();
		}

		public final <T> T putDatum(String key, Object val) {
			Object o = this.data.put(key, val);
			return o == null ? null : (T) o;
		}

		public final <T> T getDatum(String key) {
			Object o = this.data.get(key);
			return o == null ? null : (T) o;
		}

		public final void copyFrom(ResInfo ri) {
			this.set(ri.code, ri.msg);
			this.data.clear();
			this.data.putAll(ri.data);
		}

		@Override
		public String toString() {
			return "ResInfo [code=" + code + ", msg=" + msg + ", data=" + data + "]";
		}

		/**
		 * <b>补充说明：</b>
		 * <ul>
		 * <li>1. 若本次会话有设置过,则此次设置无效</li>
		 * </ul>
		 */
		public static final void set(String ret_url, String ret_btn_text) {
			if (renderArgs.get("ret_url") != null || renderArgs.get("ret_btn_text") != null) {
				return;
			}
			if (!StringHelper.isValid(ret_url)) {
				Header referer = request.headers.get("referer");
				if (referer != null && referer.value().startsWith(CommonConst.APP_BASE_URL)) {
					ret_url = "javascript:if (window.history.length > 1) {window.history.back();} else {window.close();}";
				}
				else {
					ret_url = "javascript:if (window.opener == null) {window.history.back();} else {window.close();}";
				}
			}
			ret_btn_text = StringHelper.isValid(ret_btn_text) ? ret_btn_text : "返回";
			renderArgs.put("ret_url", ret_url);
			renderArgs.put("ret_btn_text", ret_btn_text);
		}

		public static final void reset(String ret_url, String ret_btn_text) {
			removeRetInfo();
			set(ret_url, ret_btn_text);
		}

		public static final void removeRetInfo() {
			renderArgs.data.remove("ret_url");
			renderArgs.data.remove("ret_btn_text");
		}

	}
    
	/**
	 * <b>补充说明 : </b>
	 * <ul>
	 * <li>1. 生成验证码图片路由 : @{baseframe.FileOperation.generateVcodeImg()}</li>
	 * </ul>
	 */
	@Util
	public static final boolean verifyImgVcode(String vcode) {
		String key = CacheKeyPrefixEnum._1001.getVal() + session.getId();
		String valid = Cache.get(key) + "";
		Cache.delete(key);
		if (!StringHelper.isValid(valid) || !StringHelper.isValid(vcode) || !vcode.equalsIgnoreCase(valid)) {
			return false;
		}
		return true;
	}
	
	/** 今日的短信验证码获取次数, key - session id, val - times of today<br /><b>作者 : </b>maodun<br /><b>创建时间 : </b>2016年1月20日,上午11:21:25 */
	private static Map<String, Integer> sm_vcode_get_times_of_today = new HashMap<String, Integer>(0);

	@Util
	protected static final boolean smVcodeGetTimesCheck() {
		Integer cur_times = sm_vcode_get_times_of_today.get(session.getId());
		return cur_times == null || cur_times < 10;
	}
	
	@Util
	public static final void resetSMVcodeGetTimesOfToday() {
		sm_vcode_get_times_of_today.clear();
	}
	
	/**
	 * 补充说明：<br>
	 * <ul>
	 * <li>1. cont 内容中的短信验证码占位符为 : {DXYZM}</li>
	 * </ul>
	 */
	@Util
	public static final boolean sendSMVcode(long to_uid, String mobile_no, String cont) {
		if (!StringHelper.isMobileNo(mobile_no)) {
			return false;
		}
		cont = StringHelper.isValidForTrim(cont) ? cont : "您好,晓风众筹短信验证码：{DXYZM}";
		String vcode = CommonConst.SMS_TOGGLE ? RandomStringUtils.randomNumeric(6) : "888888";
System.out.println("sm_vcode: " + mobile_no + " --> " + vcode);
		boolean ok = true; // TODO send...
		if (!ok) {
			return false;
		}
		String session_id = session.getId();
		String key = CacheKeyPrefixEnum._1002.getVal() + session_id + "_" + mobile_no;
		Cache.set(key, vcode, "10min");
		Integer cur_get_times = sm_vcode_get_times_of_today.get(session_id);
		cur_get_times = cur_get_times == null ? 0 : cur_get_times;
		sm_vcode_get_times_of_today.put(session_id, cur_get_times + 1);
		return true;
	}
	
	@Util
	public static final boolean verifySMVcode(String mobile_no, String sm_vcode) {
		String key = CacheKeyPrefixEnum._1002.getVal() + session.getId() + "_" + mobile_no;
		String valid = Cache.get(key) + "";
		if ((sm_vcode + "").length() != 6 || valid.length() != 6 || !sm_vcode.equals(valid)) {
			return false;
		}
		Cache.delete(key);
		return true;
	}

	/** 查询IP所在地URL前缀，此URL后加上IP地址"xxx.xxx.xxx.xxx"即可<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月4日,下午4:35:06 */
	private static final String QUERY_IP_LOCATION_URL_PREFIX = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";
	
	@Util
	public static final String curReqIP() {
		return request.remoteAddress;
	}
    
	@Util
	public static final String lookupIPOfProvince(String ip) {
		try {
			JsonObject jo = WS.url(QUERY_IP_LOCATION_URL_PREFIX + ip).get().getJson().getAsJsonObject();
			JsonElement je = jo == null ? null : jo.get("province");
			return je == null ? "未知" : je.getAsString();
		}
		catch (Exception e) {
			return "未知";
		}
	}
	
	@Util
	public static final String lookupIPOfCity(String ip) {
		try {
			JsonObject jo = WS.url(QUERY_IP_LOCATION_URL_PREFIX + ip).get().getJson().getAsJsonObject();
			JsonElement je = jo == null ? null : jo.get("city");
			return je == null ? "未知" : je.getAsString();
		}
		catch (Exception e) {
			return "未知";
		}
	}
	
	@Util
	public static final String lookupIPOfLocation(String ip, String separator) {
		JsonObject jo = WS.url(QUERY_IP_LOCATION_URL_PREFIX + ip).get().getJson().getAsJsonObject();
		JsonElement je_province = jo.get("province");
		String province = je_province == null ? "未知" : je_province.getAsString();
		JsonElement je_city = jo.get("city");
		String city = je_city == null ? "未知" : je_city.getAsString();
		if (province.equals(city)) {
			return province;
		}
		return province + (separator == null ? "·" : separator) + city;
	}

	protected static interface Jobber {
		ResInfo doJob() throws Exception;
	}
	
	@Util
	private static final ResInfo sync(String[] keys, Jobber jobber) throws Exception {
		synchronized (keys[0]) {
			if (keys.length == 1) {
				// 事务分离 - 1 [
				EntityTransaction et = JPA.em().getTransaction();
				et.commit();
				et.begin();
				// ]
				ResInfo ri = jobber.doJob();
				// 事务分离 - 2 [
				if (ri == null || ri.code < 1) {
					et.rollback();
				}
				else {
					et.commit();
				}
				et.begin();
				// ]
				return ri;
			}
			else {
				return sync(Arrays.copyOfRange(keys, 1, keys.length), jobber); // 同步递归
			}
		}
	}
	
	/**
	 * <b>约定：</b>
	 * <ul>
	 * <li>1. 不能在此方法中调用Action,可根据返回值ResInfo来进行跳转</li>
	 * <li>2. 在此方法的作用域中访问validation/params/routeArgs/flash/request/response/session均为只读对象,不能改变其值和数据,renderArgs对象除外</li>
	 * </ul>
	 * <b>补充说明：</b>
	 * <ul>
	 * <li>1. 此方法内含事务分离,与外部方法不属于同一事务,jobber.doJob方法return的ResInfo其code大于0则事务立即提交,否则事务立即回滚</li>
	 * </ul>
	 */
	@Util
	protected static final ResInfo syncBlock(final String[] keys, final Jobber jobber) {
		// 保留原来的域数据 - 1 [
		final Validation v = validation;
		final Params p = params;
		final RenderArgs ra_n0 = renderArgs;
		final RouteArgs ra_n1 = routeArgs;
		final Flash f = flash;
		final Request req = request;
		final Response res = response;
		final Session s = session;
		// ]
		return await(new Job() {
			@Override
			public ResInfo doJobWithResult() throws Exception {
				// 保留原来的域数据 - 2 [
				Validation.current.set(v);
				Params.current.set(p);
				RenderArgs.current.set(ra_n0);
				RouteArgs.current.set(ra_n1);
				Flash.current.set(f);
				Request.current.set(req);
				Response.current.set(res);
				Session.current.set(s);
				// ]
				return sync(keys, jobber);
			}
		}.now());
	}

	/**
	 * <b>约定：</b>
	 * <ul>
	 * <li>1. 不能在此方法中调用Action,可根据返回值ResInfo来进行跳转</li>
	 * <li>2. 在此方法的作用域中访问validation/params/routeArgs/flash/request/response/session均为只读对象,不能改变其值和数据,renderArgs对象除外</li>
	 * </ul>
	 * <b>补充说明：</b>
	 * <ul>
	 * <li>1. 此方法内含事务分离,与外部方法不属于同一事务,jobber.doJob方法return的ResInfo其code大于0则事务立即提交,否则事务立即回滚</li>
	 * </ul>
	 */
	@Util
	protected static final ResInfo syncBlock(List<String> keys, Jobber jobber) {
		return syncBlock(keys.toArray(new String[keys.size()]), jobber);
	}

	@Util
	protected static final <T> T[] arr(T... vals) {
		return vals;
	}

}
