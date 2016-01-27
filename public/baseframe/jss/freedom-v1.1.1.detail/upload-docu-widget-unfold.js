$(function () {
	
	// 本文单词缩写解释：
	// #. oc = 自己的class = own class
	// #. otp = 自己的标签属性 = own tag property
	// #. udw = 上传文档小部件 = upload docu widget
	
	$('.__oc_udw.__oc_unfold>input[type="file"]').live('change', function () {
		var thiz = this;
		var path = $.trim($(thiz).val());
		if(path == '') {
Printer.info('未选择要上传的文档,跳出');
			return ;
		}
		var form = $('<form style="display:none;"></form>');
		$('body').append(form);
		form.attr('action', __ogv.routes.upload_docu);
		var cloned_file_input = $(thiz).clone();
		$(thiz).after(cloned_file_input);
		form.append($(thiz));
		$(thiz).attr('name', 'docu');
		var maxsize = $.trim($(thiz).attr('__otp_maxsize'));
		if (maxsize != '') {
			form.append('<input type="hidden" name="maxsize" value="' + maxsize + '" />');
		}
		var types = $.trim($(thiz).attr('__otp_types'));
		if (types != '') {
			form.append('<input type="hidden" name="types" value="' + types + '" />');
		}
		var layer_index = layer.msg('上传中,请稍候...', {icon:16, shade:[0.00], time:0});
		form.ajaxSubmit({
			type: 'post',
			success: function (res) {
				if (res.code < 0) {
					layer.mmsg(res.msg, {icon:0, time:4000/*, shade:[0.01, '#fff'], shadeClose:true, closeBtn:1*/});
					$(thiz).val('');
					return ;
				}
//				$(thiz).attr('__otp_fullname', res.data.fullname).attr('__otp_size', res.data.size);
				cloned_file_input.next(':hidden').val(res.data.fullname);
				var parent = cloned_file_input.parent();
				parent.next('a.__oc_udw_unfold_btn').remove();
				parent.next('a.__oc_udw_unfold_btn').remove();
				parent.after('<a href="" class="__oc_udw_unfold_btn __oc_del" >删除</a>');
				parent.after('<a href="" class="__oc_udw_unfold_btn" title="点击下载" __otp_ddt __otp_fullname="' + res.data.fullname + '">' + res.data.ori_fullname + '</a>');
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
	
	$('a.__oc_udw_unfold_btn.__oc_del').live('click', function () {
		var $this = $(this);
		var val = $.trim($this.val());
		$.ajax({
			type: 'post',
			url: __ogv.routes.del_file,
			data: {'fullname': val},
			success: function (res) {
				if (res.code < 0) {
					layer.mmsg(res.msg, {icon:0});
					return ;
				}
				$this.prev().prev().children(':file, :hidden').val('');
				$this.prev().remove();
				$this.remove();
			}
		});
	});
	
	/*
	$.each($('.__oc_udw.__oc_unfold>:hidden'), function (i, v) {
		var $v = $(v);
		var val = $.trim($v.val());
		if (val != '') {
			var parent = $v.parent();
			parent.after('<a href="" class="__oc_udw_unfold_btn __oc_del" >删除</a>');
			parent.after('<a href="" class="__oc_udw_unfold_btn" title="点击下载" __otp_ddt __otp_fullname="' + val + '">' + val.substr(val.lastIndexOf('-85076483-') + 10) + '</a>');
		}
	});
	*/
	
	$.each($('.__oc_udw.__oc_unfold>:hidden'), function (i, v) {
		var $v = $(v);
		var val = $.trim($v.val());
		if (val != '') {
			$.ajax({
				type : 'post',
				url : __ogv.routes.query_att,
				data : {'by_fullname':val},
				success : function (res) {
Printer.obj(res);
					if (res.code > 0) {
						var parent = $v.parent();
						if ($.trim($v.attr('__otp_disable_del_btn')).toUpperCase() != 'Y') {
							parent.after('<a href="" class="__oc_udw_unfold_btn __oc_del" >删除</a>');
						}
						parent.after('<a href="" class="__oc_udw_unfold_btn" title="点击下载" __otp_ddt __otp_fullname="' + val + '">' + res.data.att.ori_fullname + '</a>');
					}
				}
			});
		}
	});
	
});

