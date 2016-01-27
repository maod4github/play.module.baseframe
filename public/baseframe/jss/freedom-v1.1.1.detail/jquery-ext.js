/** 
 * 这个文件用来放自定义的jquery插件 
 * @author maodun
 * @created 2015年5月19日 下午14:56:00
 */



/**
 * 实际值（创建原因是由于被公共的默认文案提示JS效果影响，在没有填写内容的时候，通过$('xx').val()拿到的是默认值）
 * @author maodun
 * @created 2015年5月19日 下午14:56:29
 */
$.fn.realVal = function() {
	var realVal = $(this).val();
	
	/*if ($(this).is(':text') || $(this).is('textarea')) {
		var val = $(this)[0].value;
		var defVal = $(this)[0].defaultValue;
		realVal = (val == defVal ? '' : val);
	}*/
	
	return realVal;
};

/**
 * 给所有匹配的元素附加一个事件处理函数，即使这个元素是以后再添加进来的也有效（在使用 .bind() 时，选择器匹配的元素会附加一个事件处理函数，而以后再添加的元素则不会有，
 * 为此需要再使用一次 .bind() 才行。而这个方法是 .bind() 方法的一个变体，它不再需要为以后添加的元素再使用一次 .live()，即使这个元素是以后再添加进来的也有效。）
 * @author maodun
 * @created 2015年5月25日 上午10:04:29
 * @param {String} eventType 事件类型,由空格分隔多个事件类型,必须是有效的事件.
 * @param {Function} fn 处理函数
 * @param [可选] {Object} data 传递给 fn 的附加参数
 * @调用范例：
 * 		$('#id').live('click', function (opts) {
 * 			// 通过 opts.data 获取附加参数
 * 			Printer.info(opts.data);
 *			// 执行完上面一行代码，控制台会输出：这里是处理函数的附加参数
 * 		}, '这里是处理函数的附加参数');
 * 
 */
$.fn.live = function (eventType, fn, data) {
	$(document).delegate(this.selector, eventType, data, fn);
	return this;
};

/**
 * 是否显示了
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 * @return {boolean}
 */
$.fn.isShowed = function () {
	return this.css('display') != 'none';
};

/**
 * 禁用
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 */
$.fn.disable = function () {
	this.attr('disabled', 'disabled');
	return this;
};

/**
 * 启用
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 */
$.fn.enable = function () {
	this.removeAttr('disabled');
	return this;
};

/**
 * 选中；核实；查核；打勾 (适用于单选框、复选框)
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 */
$.fn.check = function () {
	for (var index = 0; index < this.length; index ++) {
		this[index].checked = true; // 对象属性
		$(this[index]).attr('checked', 'checked'); // HTML标签属性
	}
	return this;
};

/**
 * 取消选中；核实；查核；打勾 (适用于单选框、复选框)
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 */
$.fn.uncheck = function () {
	for (var index = 0; index < this.length; index ++) {
		this[index].checked = false; // 对象属性
		$(this[index]).removeAttr('checked'); // HTML标签属性
	}
	return this;
};

/**
 * 强调 (执行2次闪现动画)
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 * @param {Function or 不传} backFn 回调函数 (动画执行完后调用)
 */
$.fn.emphasize = function (backFn) {
	var thiz = this;
	if ('function' != $.type(backFn)) {
		backFn = function () {};
	}
	var _ing = thiz.data('__od_emphasize_ing');
	if (_ing) {
		return ;
	}
	thiz.data('__od_emphasize_ing', 'Y');
	thiz.fadeOut(90).fadeIn(90).fadeOut(90).fadeIn(500, function () {
		backFn();
		thiz.removeData('__od_emphasize_ing');
	});
	return thiz;
};

/**
 * 标记为正在提交中 (防重复提交方法)
 * @author maodun
 * @created 2015年5月26日 上午10:04:29
 */
$.fn.markSing = function () {
	this.data('__sd_mark_submit', new Date());
	return this;
};

/**
 * 标记为已提交完成（防重复提交方法）
 * @author maodun
 * @version 1.0
 * @created 2015年5月26日 上午10:04:29
 */
$.fn.markSed = function () {
	this.removeData('__sd_mark_submit');
	return this;
};

/**
 * 重复提交检查 (防重复提交方法)
 * @author maodun
 * @version 1.0
 * @created 2015年5月26日 上午10:04:29
 * @return {Boolean} true：可以提交；false：正在提交中，不可重复提交；
 */
$.fn.rsCheck = function () {
	return null == $(this).data('__sd_mark_submit');
};

/**
 * 示意 (暂仅支持的HTML标签：text、textarea)
 * @author maodun
 * @created 2015年7月14日 上午10:04:29
 * @param text 示意文本
 * @return {$} this
 */
$.fn.hint = function (text) {
	if (!$(this).is(':text') && !$(this).is('textarea')) {
Printer.warn('调用异常，hint暂仅支持文本输入框对象(text、textarea)');
		return ;
	}
	var oldHintText = $(this).data('hint4clear_text'); // 旧暗示文本
	$(this).data('hint4clear_text', text);
	if ($.trim($(this).val()) == '' || $.trim($(this).val()) == oldHintText) {
		$(this).val($(this).data('hint4clear_text'));
		$(this).css('color', '#999');
	}
	if ($(this).data('isInit_hint4clear_Handler') == 'Y') {
		return ;
	}
	var focusHandler = function () {
		if ($(this).val() == $(this).data('hint4clear_text')) {
			$(this).val('');
		}
		$(this).css('color', '#000000');
	};
	var blurHandler = function () {
		if ($(this).val() == '') {
			$(this).val($(this).data('hint4clear_text'));
			$(this).css('color', '#999');
		}
	};
	$(this).bind({
		focus : focusHandler,
		blur : blurHandler
	});
	$(this).data('hint4clear_focusHandler', focusHandler);
	$(this).data('hint4clear_blurHandler', blurHandler);
	$(this).data('isInit_hint4clear_Handler', 'Y');
	return this;
};

/**
 * 加载其选项集 (暂仅适用于含有__otp_provinces自定义标签属性的select)
 * @author maodun
 * @created 2015年5月28日 下午19:11:29
 * @return {$} this
 */
$.fn.loadProvinces = function () {
	var thiz = this;
	if ($(thiz).is('select[__otp_provinces]')) {
		$.ajax({
			type : 'get',
	   		url : __ogv.routes.get_provinces,
	   		success : function (provinces) {
	   			var select = $(thiz);
   				var def_code = $.trim(select.attr('__otp_def_code'));
   				var has_the_def_code = false;
   				select.children().remove();
   				select.append('<option value="0">请选择省份</option>');
   				$.each(provinces, function (i, v) {
   					if (def_code == v.code) {
   						has_the_def_code = true;
	   				}
   					$('<option value="' + v.code + '">' + v.name + '</option>').appendTo(select);
   				});
				select.val(has_the_def_code ? def_code : 0).change();
	   		},
	   		error : function() {
	   			layer.msg('服务器繁忙,获取省份失败!');
	   		}
	   	});
	}
	else {
Printer.warn('不是select[__otp_provinces]的元素不能调用loadProvinces');
	}
	return this;
};

/**
 * 获取上传后的文件全名 (适用于单张图片上传控件、文档上传控件)
 * @author maodun
 * @created 2015年9月7日 上午10:04:29
 * @return {String} 全名(用于保存至数据库)
 */
$.fn.getFullname = function () {
	var $this = $(this);
	if ($this.is('.__oc_uiw.__oc_unfold') || $this.is('.__oc_uiw.__oc_preview') || $this.is('.__oc_udw.__oc_unfold')) {
		return $.trim($this.children(':hidden:first').val());
	}
	return '';
};

/**
 * 获取上传后的文件全名数组 (适用于多张图片上传控件)
 * @author maodun
 * @created 2015年9月7日 上午10:04:29
 * @return {Array(String)} 全名数组 (用于保存至数据库)
 */
$.fn.getFullnameArr = function () {
	var thiz = this;
	if ($(thiz).is('.__oc_uiw.__oc_more')) {
		var group_num = $(thiz).attr('__otp_group_num');
		var displayer = $('[__otp_uiw_more_displayer][__otp_group_num="' + group_num + '"]');
		if (displayer.length != 1) {
			layer.mmsg('找不到匹配的显示器标签,请联系管理员<br />「__otp_group_num=' + group_num + '」', {icon:0});
			return null;
		}
		var imgs = displayer.find('div>img');
		var fullnames = [];
		$.each(imgs, function (i, v) {
			var fullname = $.trim($(v).attr('__otp_fullname'));
			if (fullname != '') {
				fullnames.push(fullname);
			}
		});
		return fullnames;
	}
	return null;
};

/**
 * 验证ajax提交 (该方法将阻止原始的submit事件, 仅适用于form标签)
 * @author maodun
 * @created 2015年9月7日 上午10:04:29
 * @return {void}
 */
$.fn.verifyAjaxSubmit = function (succ_fn, err_fn, complete_fn) {
	var __this = $(this);
	if (!__this.is('form')) {
Printer.warn('validAjaxSubmit方法只适合form标签调用');
		return ;
	}
	if (__this.data('__od_sign_binded_submit_event') != 'Y') {
		__this.data('__od_sign_binded_submit_event', 'Y');
		__this.submit(function () {
			return false;
		});
	}
	var handle_fn = function () {
		if (!__this.rsCheck()) {
			return ;
		}
		if (!__this.verifyInner()) {
			return ;
		}
		var submit_type = $.trim(__this.attr('method'));
		submit_type = submit_type == '' ? 'post' : submit_type;
		__this.ajaxSubmit({
			type : submit_type,
			beforeSubmit : function (xhr, o) {
			},
			beforeSend : function (xhr, o) {
				__this.markSing();
				layer.submitting();
			},
			success : function (res, status, xhr) {
				if (succ_fn != null) {
					succ_fn(res, status, xhr);
				}
			},
			error : function (xhr, status, error) {
				if (err_fn != null) {
					err_fn(xhr, status, error);
				}
			},
			complete : function (xhr, status) {
				if (complete_fn != null) {
					complete_fn(xhr, status);
				}
				__this.markSed();
				layer.submitted();
			}
		});
	};
	__this.find(':submit').mousedown(function () {
		handle_fn();
	});
	KeyEventHelper.enter(__this, 'keyup', handle_fn);
};
