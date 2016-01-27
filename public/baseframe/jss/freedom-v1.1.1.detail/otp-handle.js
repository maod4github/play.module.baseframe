/**
 * 自定义标签属性把手
 */
$(function () {
	
	// 仅允许输入纯数字(半角)
	$('[__otp_only_pure_num]').live('keyup blur', function () {
		var thiz = $(this);
		var val = thiz.val();
		if (!val.isPureNum()) {
			thiz.val(val.extNums())
		}
	});
	
	// 预览图片弹层触发器
	$('[__otp_pil_trigger]').live('click', function () {
		var thiz = this;
		var fullname = $.trim($(thiz).attr('__otp_fullname'));
		if (!fullname.isValid()) {
			return ;
		}
		if (sessionStorage[fullname] != 'Y') {
			layer.submitting('预览效果生成中,请稍候...');
		}
		var cur_time = Date.curDate().getTime();
		var index = layer.open({
			type : 1,
		    title : false,
		    shade : [0],
		    shadeClose : true,
		    move : '#' + cur_time,
		    moveType : 1,
		    moveOut : true,
		    area : ['auto', 'auto'],
	    	content : 
	    		'<style type="text/css">' +
	    		'.__oc_pi_img {cursor:default !important;}' +
	    		'</style>' +
	    		'<img id="' + cur_time + '" src="' + __ogv.prefixes.img_atcmt_src + fullname + '" class="__oc_pi_img" >',
	    	success : function (layero) {
	    		if (sessionStorage[fullname] == 'Y') {
	    			return ;
	    		}
	    		var __img_pi = $('#' + cur_time);
	    		__img_pi.parent().css({'height':'auto', 'overflow':'hidden'});
	    		__img_pi.parent().parent().hide();
	    		__img_pi.parent().parent().prev().hide();
	    		// S 定时器 [
	    		new Timer(1200, 1, null, null, function () {
	    			layer.submitted();
	    			sessionStorage[fullname] = 'Y';
	    			__img_pi.parent().parent().show();
	    			__img_pi.parent().parent().prev().show();
	    			__img_pi.resize();
	    		}).start();
	    		// E ]
	    	}
		});
		//layer.style(index, {height:'500px'});
	});
	
});