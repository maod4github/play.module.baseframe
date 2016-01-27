
// 						支持的验证列表
// #. --------------------------------------------------------------------------------
// #.	验证名			取值类型		      	取值范例				说明
// #. --------------------------------------------------------------------------------
// #. required			Boolean		  		true				必填
// #. mobileNo			Boolean		  		true				手机号码
// #. emailAddr			Boolean		  		true				必须输入正确格式的电子邮件
// #. num				Boolean		  		true				数字 (1.包括正负整数、正负小数;  2.支持'.12'与'12.'格式的匹配)
// #. url				Boolean		  		true				URL
// #. max				Number			 	5					最大值
// #. min				Number				10					最小值
// #. range				Array<Number>		[5,10]				值范围
// #. maxlen			Number				5					最大长度 (汉字算一个字符)
// #. minlen			Number				10					最小长度 (汉字算一个字符)
// #. rangelen			Array<Number>		[5,10]				长度范围 (汉字算一个字符)
// #. suffix			String				'.jpg'				后缀
// #. equals			String				'#id'				等于 (输入值必须与$('#id')的值相同)
// #. regexp			RegExp				/^\d$/				正则表达式

$(function () {
	
	$.metadata.setType('attr', '__otp_metadata');
	
	$.validator = {
		methods : {
			required : function ($elem, param) {
				if (param != true) {
					return true;
				}
				if ($elem.is(':radio') || $elem.is(':checkbox')) {
					return $elem.is(':checked');
				}
				if ($elem.is('radiogroup')) {
					return $elem.find(':radio:checked').length != 0
				}
				if ($elem.is('checkboxgroup')) {
					return $elem.find(':checkbox:checked').length != 0
				}
				var val = $elem.enhVal();
				return val != '';
			},
			mobileNo : function (elem, param) {
				if (param != true) {
					return true;
				}
				var val = elem.enhVal();
				return val == '' || val.isMobileNum();
			},
			emailAddr : function (elem, param) {
				if (param != true) {
					return true;
				}
				var val = elem.enhVal();
				return val == '' || val.isEmail();
			},
			num : function (elem, param) {
				if (param != true) {
					return true;
				}
				var val = elem.enhVal();
				return val == '' || val.isNum();
			},
			url : function (elem, param) {
				if (param != true) {
					return true;
				}
				var val = elem.enhVal();
				return val == '' || val.isURL();
			},
			suffix : function ($elem, param) {
				var val = $elem.enhVal();
				return val == '' || val.endsWith(param);
			},
			equals : function (elem, param) {
				var val = elem.enhVal();
				return val == '' || val == $(param).enhVal();
			},
			max : function ($elem, param) {
				var val = $elem.enhVal();
				return val = '' || Number(val) <= param;
			},
			min : function ($elem, param) {
				var val = $elem.enhVal();
				return val = '' || Number(val) >= param;
			},
			range : function ($elem, param) {
				var val = $elem.enhVal();
				return val = '' || (Number(val) >= param[0] && Number(val) <= param[1]);
			},
			maxlen : function ($elem, param) {
				if ($elem.is('radiogroup')) {
					return $elem.find(':radio:checked').length <= param;
				}
				if ($elem.is('checkboxgroup')) {
					return $elem.find(':checkbox:checked').length <= param;
				}
				var val = $elem.enhVal();
				return val == '' || val.length <= param;
			},
			minlen : function ($elem, param) {
				if ($elem.is('radiogroup')) {
					return $elem.find(':radio:checked').length >= param;
				}
				if ($elem.is('checkboxgroup')) {
					return $elem.find(':checkbox:checked').length >= param;
				}
				var val = $elem.enhVal();
				return val == '' || val.length >= param;
			},
			rangelen : function (elem, param) {
				var val = elem.enhVal();
				return val == '' || (val.length >= param[0] && val.length <= param[1]);
			},
			regexp : function (elem, param) {
				var val = elem.enhVal();
				return val == '' || param.test(val);
			},
			loginPwd : function (elem, param) {
				if (param != true) {
					return true;
				}
				var val = elem.enhVal();
				return val == '' || val.isLoginPwd();
			}
		},
		msgs : {
			required : '必填',
			mobileNo : '请输入有效的手机号码',
			emailAddr : '请输入有效的电子邮件地址',
			num : '请输入有效的数字',
			url : '请输入有效的网址',
			suffix : '请以{0}结尾',
			equals : '请输入相同的值',
			max : '最大值为{0}',
			min : '最小值为{0}',
			range : '取值范围为{0}-{1}之间',
			maxlen : '最多可以输入{0}个字符',
			minlen : '最少要输入{0}个字符',
			rangelen : '请输入{0}到{1}个字符',
			regexp : '请匹配如下正则表达式<br />{0}',
			loginPwd : '请输入6~16位字母和数字'
		}
	};
	
	$.isValid = function (val) {
		return (val + '').isValid();
	};
	
});

$.fn.verify = function () {
	var $this = $(this);
	var opts = $this.metadata();
	opts.msgs = opts.msgs == null ? {} : opts.msgs;
	var err_msg = '';
	$.each(opts, function (k, v) {
		if (k == 'msgs') {
			return true;
		}
		var result = true;
		eval('result = $.validator.methods["' + k + '"]($this, v);');
		if (!result) {
			err_msg = opts.msgs[k];
			if (!$.isValid(err_msg)) {
				eval('err_msg = $.validator.msgs["' + k + '"]');
			}
			$.each([].concat(v), function (i, v) {
				err_msg = err_msg.replace(RegExp('\\{' + i + '\\}', 'g'), v);
			});
			return false;
		}
	});
	if (err_msg != '') {
		// layer.tips(err_msg, $this, {tips:1, area:['auto']});
		layer.mmsg(err_msg, {icon:0, shift:6, time:1500});
		if ($this.is('radiogroup')) {
			$this.find(':radio:first').focus();
			return false;
		}
		else if ($this.is('checkboxgroup')) {
			$this.find(':checkbox:first').focus();
			return false;
		}
		$this.focus();
		return false;
	}
	return true;
};

$.fn.verifyInner = function () {
	var $this = $(this);
	var elems = $this.find('[__otp_validate]');
	var result = true;
	$.each(elems, function (i, v) {
		result = $(v).verify();
		return result;
	});
	return result;
};

$.fn.enhVal = function (val) {
	var $this = $(this);
	if ($.isValid(val)) {
		$this.attr2('value', val);
	}
	var val = $this.attr2('value');
	return val == null ? '' : val;
};



