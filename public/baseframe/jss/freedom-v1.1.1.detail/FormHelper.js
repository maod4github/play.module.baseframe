/**
 * 表单助手
 * @author maodun
 */
function FormHelper () {}

/**
 * 初始值签名
 * @author maodun
 * @param {$} form 表单
 */
/*
FormHelper.signForInitVals = function (form) {
	var req_data = form.find('[name]');
Printer.obj(req_data);
	$.each(req_data, function (i, v) {
		var init_val = $.trim($(v).val());
		$(v).data('__od_init_val', init_val);
	});
};
*/

/**
 * 初始值是否有改变
 * @author maodun
 * @param {$} form 表单
 * @returns {boolean} true:有改变，false:没有改变
 */
/*
FormHelper.hasChangeForInitVals = function (form) {
	var result = false;
	var req_data = form.find('[name]');
Printer.obj(req_data);
	$.each(req_data, function (i, v) {
		var val = $.trim($(v).val());
		var init_val = $.trim($(v).data('__od_init_val'));
		if (val != init_val) {
			result = true;
			return false;
		}
	});
	return result;
};
*/

/*
$(function () {
	
	$('form').submit(function () {
		var thiz = this;
		return $(thiz).verify();
	});
	
});
*/