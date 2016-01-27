/**
 * laydate-v1.1扩展
 * @author maodun
 */
$(function () {
	
	// 本文单词缩写解释：
	// #. otp = 自己的标签属性 = own tag property
	// #. dtw = 日期时间小部件 = date time widget
	
	$('[__otp_dtw_date], [__otp_dtw_datetime]').addClass('laydate-icon').attr('readonly', 'readonly'); 
	
	$('[__otp_dtw_date]').live('click', function () {
		var __this = $(this);
		laydate({istime:false, format:'YYYY-MM-DD', festival:true, choose:function (dates) {
			__this.focus();
		}});
		// 记录打开者，在用户点击clear和today按钮时聚焦打开者
		$('#laydate_clear, #laydate_today').parent().data('__od_opener', __this);
	});
	
	$('[__otp_dtw_datetime]').live('click', function () {
		var __this = $(this);
		laydate({istime:true, format:'YYYY-MM-DD hh:mm:ss', festival:true, choose:function (dates) {
			__this.focus();
		}});
		// 记录打开者，在用户点击clear和today按钮时聚焦打开者
		$('#laydate_clear, #laydate_today').parent().data('__od_opener', __this);
	});
	
	// 记录打开者，在用户点击clear和today按钮时聚焦打开者 [
	$('#laydate_clear, #laydate_today').live('click', function () {
		var __parent = $(this).parent();
		__parent.data('__od_opener').focus();
	});
	// ]
	
//	更换皮肤
//	laydate.skin('dahong');
	
});