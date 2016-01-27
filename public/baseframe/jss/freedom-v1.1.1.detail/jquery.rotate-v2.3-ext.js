/**
 * jquery.rotate-v2.3扩展
 * @author maodun
 */
$(function () {
	
	/** 左旋转 */
	$('[__otp_left_rotate_trigger]').live('click', function () {
		var groupNum = $(this).attr('__otp_group_num');
		var es = $(['[__otp_rotatable][__otp_group_num="', groupNum, '"]'].join(''));
		$.each(es, function (i, v) {
			var deg = $.trim($(v).attr('__otp_rotate_deg'));
			deg = ('' == deg ? 45 : new Number(deg));
			var curDeg = $(v).data('__od_cur_deg');
			curDeg = (null == curDeg ? 0 : curDeg);
			$(v).data('__od_cur_deg', curDeg - deg);
			$(v).rotate($(v).data('__od_cur_deg'));
		});
	});
	
	/** 右旋转 */
	$('[__otp_right_rotate_trigger]').live('click', function () {
		var groupNum = $(this).attr('__otp_group_num');
		var es = $(['[__otp_rotatable][__otp_group_num="', groupNum, '"]'].join(''));
		$.each(es, function (i, v) {
			var deg = $.trim($(v).attr('__otp_rotate_deg'));
			deg = ('' == deg ? 45 : new Number(deg));
			var curDeg = $(v).data('__od_cur_deg');
			curDeg = (null == curDeg ? 0 : curDeg);
			$(v).data('__od_cur_deg', curDeg + deg);
			$(v).rotate($(v).data('__od_cur_deg'));
		});
	});

});