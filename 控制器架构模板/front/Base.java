package controllers.front;

import models.T_user;
import play.Play;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Router;
import play.mvc.Util;
import controllers.baseframe.Base.ResInfo;
import controllers.front.loginreg.Login;
import enums.CacheKeyPrefixEnum;

/**
 * 前台基础控制器 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月3日,上午8:52:29
 */
public class Base extends controllers.Base {

	/**
	 * 公共拦截器 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年9月11日,下午5:12:51
	 */
	@Before(priority = 0)
	private static void commonInterceptor() {
		refreshLoginSessionExpires();
		renderArgs.put("is_front_req", "Y")); // 是否是前台请求
		renderArgs.put("curUser", curUser()); // 当前登录用户
		renderArgs.put("pagingSkin", "#e75a3e"); // 分页皮肤色
	}

	/**
	 * 登录拦截器 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年10月9日,上午10:36:18
	 */
	@Before(unless = {}, priority = 1)
	private static void loginInterceptor() {
		loginInvalidCheck();
		if (curUser() == null) {
			if (request.isAjax()) {
				renderJSON(new ResInfo(-1001, "您暂未登录,请先登录.")); return ;
			}
			Login.goLogin(); return ;
		}
	}

	/**
	 * 首页 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月3日,上午8:52:15
	 */
	public static void home() {
		renderTemplate("/app/views/front/home.html"); return ;
	}

	/**
	 * 渲染至提示页 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年11月24日,上午9:18:43
	 * @param code 响应代码, > 0 to 200.html else to 500.html
	 * @param msg 响应消息
	 */
	protected static void renderTipsHTML(Integer code, String msg) {
		// 若此前没设置过,则设置走默认机制,若此前有设置过,则此次设置无效
    	ResInfo.setRet(null, null);
		ResInfo res_info = new ResInfo(code, msg);
		renderTemplate("/app/views/front/" + (code.intValue() > 0 ? "200" : "500") + ".html", res_info); return ;
	}

	/**
     * 获取当前用户信息
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月21日,下午8:09:51
     * @return the cur user or null
     */
    @Util
    public static T_user curUser() {
    	String key = CacheKeyPrefixEnum._1003.getVal() + session.getId();
    	Object cur_user = Cache.get(key);
    	return cur_user instanceof T_user ? (T_user) cur_user : null;
    }
    
    private static String login_session_expires = Play.configuration.getProperty("bf.cache.user.expires", "30min");
    
    /**
     * 设置当前用户信息
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月21日,下午8:10:05
     * @param user 用户信息
     */
    @Util
    public static void curUser(T_user user) {
    	String key = CacheKeyPrefixEnum._1003.getVal() + session.getId();
    	String key_login_mark = CacheKeyPrefixEnum._1004.getVal() + session.getId();
    	if (user == null) {
    		Cache.delete(key);
    		Cache.delete(key_login_mark);
    		return;
    	}
    	Cache.set(key, user, login_session_expires);
    	Cache.set(key_login_mark, "Y");
    }
    
    /**
     * 刷新登录会话有效期
     * <br /><b>作者 : </b>maodun
     * <br /><b>创建时间 : </b>2015年12月5日,上午9:00:02
     */
    @Util
    public static void refreshLoginSessionExpires() {
    	T_user u = curUser();
    	if (u != null) {
    		String key = CacheKeyPrefixEnum._1003.getVal() + session.getId();
    		Cache.replace(key, u, login_session_expires);
    	}
    }
    
    /**
     * 登录失效检查<br>
     * <b>作者 : </b>maodun<br>
     * <b>创建时间 : </b>2015年12月3日,下午5:29:18
     */
    @Util
    private static void loginInvalidCheck() {
    	T_user cur_user = curUser();
    	String key_login_mark = CacheKeyPrefixEnum._1004.getVal() + session.getId();
    	if (cur_user == null && "Y".equals(String.valueOf(Cache.get(key_login_mark)))) {
    		Cache.delete(key_login_mark);
    		if (request.isAjax()) {
    			renderJSON(new ResInfo(-1004, "您的登录信息已过期，请重新登录")); return;
    		}
    		ResInfo.setRet(Router.getFullUrl("front.loginreg.Login.goLogin"), "现在就去");
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
		// 看当前用户是否有这个权限
	}

}
