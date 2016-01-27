$(function () {
	
	// 本文单词缩写解释：
	// #. oc = 自己的class = own class
	// #. otp = 自己的标签属性 = own tag property
	// #. uiw = 上传图片小部件 = upload img widget
	
	$('.__oc_uiw.__oc_more>input[type="file"]').live('click', function () {
		var thiz = this;
		$(thiz).attr('multiple', 'multiple');
	});
	
	$('.__oc_uiw.__oc_more>input[type="file"]').live('change', function () {
		var thiz = this;
		var parent = $(thiz).parent();
		var group_num = parent.attr('__otp_group_num');
		var displayer = $('[__otp_uiw_more_displayer][__otp_group_num="' + group_num + '"]');
		if (displayer.length != 1) {
			layer.mmsg('找不到匹配的显示器标签<br />「__otp_group_num="' + group_num + '"」', {icon:0});
			$(thiz).val('');
			return ;
		}
		var cur_count = displayer.find('img').length;
		var max_count = displayer.attr('__otp_max_count');
		if (max_count != null && cur_count >= max_count) {
			layer.mmsg('抱歉,最多只能上传「' + max_count + '张」图片', {shift:6, icon:0});
			$(thiz).val('');
			return ;
		}
		var name = $.trim($(thiz).attr('name'));
		if (name != '') {
			$(thiz).attr('__otp_ori_name', name);
		}
		var form = $('<form style="display:none;"></form>');
		$('body').append(form);
		form.attr('action', __ogv.routes.upload_imgs);
		var cloned_file_input = $(thiz).clone();
		$(thiz).after(cloned_file_input);
		form.append($(thiz));
		$(thiz).attr('name', 'imgs');
		var maxsize = $.trim($(thiz).attr('__otp_maxsize'));
		if (maxsize != '') {
			form.append('<input type="hidden" name="maxsize" value="' + maxsize + '" />');
		}
		var layer_index = layer.msg('上传中,请稍候...', {icon:16, shade:[0.00], time:0});
		form.ajaxSubmit({
			type: 'post',
			success: function (res) {
				var ori_name = $.trim($(thiz).attr('__otp_ori_name'));
				$.each(res, function (i, v) {
					if (max_count != null && displayer.find('img').length >= max_count) {
						layer.mmsg('抱歉,最多只能上传「' + max_count + '张」图片', {shift:6, icon:0});
						return false;
					}
					if (v.code < 0) {
						layer.mmsg(v.msg, {icon:0});
						return true;
					}
					var html = '';
					html += '<div>';
					html += '<input type="hidden" name="' + ori_name + '" value="' + v.data.fullname + '" />';
					html += '<img src="' + __ogv.prefixes.img_atcmt_src + v.data.fullname + '" __otp_fullname="' + v.data.fullname + '" __otp_size="' + v.data.size + '" />';
					html += '<span __otp_fullname="' + v.data.fullname + '" title="删除"></span>';
					html += '</div>';
					displayer.append(html);
					displayer.attr('__otp_cur_count', displayer.find('img').length);
				});
			},
			complete: function () {
				$(thiz).val('');
				$(thiz).removeAttr('name');
				cloned_file_input.before($(thiz));
				cloned_file_input.remove();
				form.remove();
				layer.close(layer_index);
			}
		});
	});
	// E
	
	$('[__otp_uiw_more_displayer]>div>img').live('mouseenter', function () {
		var thiz = this;
		$('[__otp_uiw_more_displayer]>div>span').mouseleave();
		$(thiz).css({'opacity':'0.3', 'filter':'alpha(opacity=30)'});
		$(thiz).next().show();
	});
	
	$('[__otp_uiw_more_displayer]>div>span').live('mouseleave', function () {
		var thiz = this;
		$(thiz).prev().css({'opacity':'1', 'filter':'alpha(opacity=100)'});
		$(thiz).hide();
	});
	
	$('[__otp_uiw_more_displayer]>div>span').live('click', function () {
		var thiz = this;
		var fullname = $(thiz).attr('__otp_fullname');
		$.ajax({
			type: 'post',
			url: __ogv.routes.del_file,
			data: {'fullname': fullname},
			success: function (res) {
				if (res.code < 0) {
					layer.mmsg(res.msg, {icon:0});
					return ;
				}
				$(thiz).prev().remove();
				var displayer = $(thiz).parents('[__otp_uiw_more_displayer]');
				displayer.attr('__otp_cur_count', displayer.find('img').length);
				$(thiz).parent().remove();
			}
		});
	});
	
});