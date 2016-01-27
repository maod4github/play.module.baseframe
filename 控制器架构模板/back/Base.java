package controllers.back;

import java.util.HashMap;
import java.util.Map;

import models.T_admin;
import play.Play;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Router;
import play.mvc.Util;
import controllers.back.loginreg.Login;
import controllers.baseframe.Base.ResInfo;
import baseframe.enums.CacheKeyPrefixEnum;

/**
 * 后台基础控制器 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月3日,下午6:17:46
 */
public class Base extends controllers.Base {
	
	/** 权限URL键值对<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月2日,上午11:50:51 */
	public static final Map<String, String> AUTH_URL_KVS = new HashMap<String, String>() {
		{
			// 顶部菜单 [
//			put("auth_code", Router.getFullUrl("back.home.Home.index")); // 首页管理
			// ]
		}
	};

	/**
	 * 公共拦截器 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年9月11日,下午5:12:51
	 */
	@Before(priority = 0)
	private static void commonInterceptor() {
		curAdmin(curAdmin()); // 更新当前登录管理员的会话有效时间
		renderArgs.put("is_back_req", "Y")); // 是否是后台请求
		renderArgs.put("curAdmin", curAdmin()); // 当前登录管理员
		renderArgs.put("pagingSkin", "#f18a52"); // 分页皮肤色
	}

	/**
	 * 登录拦截器 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年11月16日,上午11:02:56
	 */
	@Before(unless = {}, priority = 1)
	private static void loginInterceptor() {
		loginInvalidCheck();
		if (curAdmin() == null) {
			if (request.isAjax()) {
    			renderJSON(new ResInfo(-1001, "您暂未登录,请先登录")); return;
    		}
			Login.goLogin(); return;
		}
	}

	/**
	 * 首页 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月5日,下午12:14:54
	 */
	public static void home() {
		renderTemplate("/app/views/back/home.html"); return ;
	}
    
	/**
     * 渲染至提示页
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年11月24日,上午9:18:43
     * @param code 响应代码, > 0 to 200.html else to 500.html
     * @param msg 响应消息
     */
    protected static void renderTipsHTML(Integer code, String msg) {
    	// 若此前没设置过,则设置走默认机制,若此前有设置过,则此次设置无效
    	ResInfo.setRet(null, null);
    	ResInfo res_info = new ResInfo(code, msg);
		renderTemplate("/app/views/back/" + (code.intValue() > 0 ? "200" : "500") + ".html", res_info); return ;
    }
    
    /**
     * 获取当前管理员信息
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月21日,下午8:09:51
     * @return the cur user or null
     */
    @Util
    public static T_admin curAdmin() {
    	String key = CacheKeyPrefixEnum._1005.getVal() + session.getId();
    	Object cur_admin = Cache.get(key);
    	return cur_admin instanceof T_admin ? (T_admin) cur_admin : null;
    }
    
    private static String login_session_expires = Play.configuration.getProperty("bf.cache.admin.expires", "30min");
    
    /**
     * 设置当前管理员信息
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月21日,下午8:10:05
     * @param admin 管理员信息
     */
    @Util
    public static void curAdmin(T_admin admin) {
    	String key = CacheKeyPrefixEnum._1005.getVal() + session.getId();
    	String key_login_mark = CacheKeyPrefixEnum._1006.getVal() + session.getId();
    	if (admin == null) {
    		Cache.delete(key);
    		Cache.delete(key_login_mark);
    		return ;
    	}
    	Cache.set(key, admin, login_session_expires);
    	Cache.set(key_login_mark, "Y");
    }
    
    /**
     * 刷新登录会话有效期
     * <br /><b>作者 : </b>maodun
     * <br /><b>创建时间 : </b>2015年12月5日,上午9:00:02
     */
    @Util
    public static void refreshLoginSessionExpires() {
    	T_admin a = curAdmin();
    	if (a != null) {
    		String key = CacheKeyPrefixEnum._1005.getVal() + session.getId();
    		Cache.replace(key, a, login_session_expires);
    	}
    }
    
    /**
     * 登录失效检查<br>
     * <b>作者 : </b>maodun<br>
     * <b>创建时间 : </b>2015年12月3日,下午5:29:18
     */
    @Util
    private static void loginInvalidCheck() {
    	T_admin cur_admin = curAdmin();
    	String key_login_mark = CacheKeyPrefixEnum._1006.getVal() + session.getId();
    	if (cur_admin == null && "Y".equals(String.valueOf(Cache.get(key_login_mark)))) {
    		Cache.delete(key_login_mark);
    		if (request.isAjax()) {
    			renderJSON(new ResInfo(-1004, "您的登录信息已过期，请重新登录")); return;
    		}
    		ResInfo.setRet(Router.getFullUrl("back.loginreg.Login.goLogin"), "现在就去");
    		renderTipsHTML(-1, "您的登录信息已过期，请重新登录"); return;
    	}
    }
    
    /**
	 * 异常句柄
	 * <br /><b>作者 : </b>maodun
	 * <br /><b>创建时间 : </b>2015年12月1日,下午5:54:02
	 * @param e
	 */
	@Catch
	public static void exceptionHandle(Throwable e) {
		if (e != null && Play.mode.isProd()) {
			renderTipsHTML(-1, "抱歉,系统繁忙,请稍后重试,或联系管理员,谢谢."); return ;
		}
	}
    
	/**
	 * 权限检查 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年11月11日,下午6:10:19
	 * @param auth_code
	 */
	@Util
	public static void authCheck(String auth_code) {
		// 看当前管理员是否有这个权限
	}

}
