$(function () {
	
	// 默认值
	$.ajaxSetup({
		
	});
	
	// 前置过滤器
	$.ajaxPrefilter(function (opts, ori_opts, jq_xhr) {
		opts.success = function (res, status, xhr) {
			// 登录或登录失效检查 [
			if (res !== null && (res.code === -1001 || res.code === -1004)) {
				layer.mmsg(res.msg, {icon:0, btn:['现在就去'], btn1:function () {
					window.location.href = __ogv.routes.go_login;
				}});
				return ;
			}
			// ]
			if (ori_opts.success !== undefined) {
				ori_opts.success(res, status, xhr);
			}
		};
		opts.error = function (xhr, status, error) {
			layer.mmsg('抱歉,系统繁忙,请稍候重试.', {icon:5});
			if (ori_opts.error !== undefined) {
				ori_opts.error(xhr, status, error);
			}
		};
	});
	$.ajaxPrefilter = null; // 防止二次配置
	
});