/**
 * 下载文件
 * @author maodun
 */
$(function () {
	
	// 单词缩写解释：
	// 1. dit --下载图片触发器-> download img trigger
	// 2. ddt --下载文档触发器-> download docu trigger
	
	$('[__otp_dit], [__otp_ddt]').live('click', function () {
		var thiz = this;
		var fullname = $.trim($(thiz).attr('__otp_fullname'));
		var downname = $.trim($(thiz).attr('__otp_downname'));
		window.location.href = __ogv.routes.download_file + '?fullname=' + fullname + '&downname=' + downname;
	});
	
});