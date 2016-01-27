/**
 * 图片验证码生成
 */


$(function () {
	
	$('img[__otp_vcode_img]').attr('src', __ogv.routes.generate_vcode_img + '?sec=' + Date.curDate().extSecondStr());
	
	$('img[__otp_vcode_img]').click(function () {
		$(this).attr('src', __ogv.routes.generate_vcode_img + '?sec=' + Date.curDate().extSecondStr());
	});
	
});