$(function () {
	
	// 本文单词缩写解释：
	// #. oc = 自己的class = own class
	// #. otp = 自己的标签属性 = own tag property
	// #. uiw = 上传图片小部件 = upload img widget
	
	$('.__oc_uiw.__oc_preview>input[type="file"]').live('change', function () {
		var thiz = this;
		var val = $.trim($(thiz).val());
		if(val == '') {
Printer.info('未选择要上传的图片,跳出');
			return ;
		}
		var parent = $(thiz).parent();
		var group_num = $.trim(parent.attr('__otp_group_num')); 
		if (group_num == '') {
			layer.mmsg('未标明组号,自定义标签属性:__otp_group_num', {icon:0});
			return ;
		}
		var matched_img = $('img[__otp_group_num="' + group_num + '"]');
		if (matched_img.length < 1) {
			layer.mmsg('找不到匹配的预览img标签<br />「__otp_group_num="' + group_num + '"」', {icon:0});
			return ;
		}
		var form = $('<form style="display:none;"></form>');
		$('body').append(form);
		form.attr('action', __ogv.routes.upload_img);
		var cloned_file_input = $(thiz).clone();
		$(thiz).after(cloned_file_input);
		form.append($(thiz));
		$(thiz).attr('name', 'img');
		var maxsize = $.trim($(thiz).attr('__otp_maxsize'));
		if (maxsize != '') {
			form.append('<input type="hidden" name="maxsize" value="' + maxsize + '" />');
		}
		var layer_index = layer.msg('上传中,请稍候...', {icon:16, shade:[0.00], time:0});
		form.ajaxSubmit({
			type: 'post',
			success: function (res) {
				if (res.code < 0) {
					layer.mmsg(res.msg, {icon:0});
					$(thiz).val('');
					return ;
				}
//				$(thiz).attr('__otp_fullname', res.data.fullname).attr('__otp_size', res.data.size);
				cloned_file_input.next(':hidden').val(res.data.fullname);
				matched_img.attr('src', __ogv.prefixes.img_atcmt_src + res.data.fullname);
//				matched_img.attr('__otp_pil_trigger', '');
				matched_img.attr('__otp_fullname', res.data.fullname);
			},
			error: function () {
				$(thiz).val('');
			}, 
			complete: function () {
				$(thiz).removeAttr('name');
				cloned_file_input.before($(thiz));
				cloned_file_input.remove();
				form.remove();
				layer.close(layer_index);
			}
		});
	});
	
	$.each($('.__oc_uiw.__oc_preview>:hidden'), function (i, v) {
		var $v = $(v);
		var val = $.trim($v.val());
		var group_num = $.trim($v.parent().attr('__otp_group_num'));
		if (val != '' && group_num != '') {
			$('img[__otp_group_num="' + group_num + '"]').attr('src', __ogv.prefixes.img_atcmt_src + val).attr('__otp_fullname', val);
		}
	});
	
});