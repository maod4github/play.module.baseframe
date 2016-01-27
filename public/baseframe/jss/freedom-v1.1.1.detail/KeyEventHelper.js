/**
 * 键事件助手
 */
function KeyEventHelper() {}

/**
 * 绑定
 * @param {Number} keyCode 键码
 * @param {JQueryObj} tarObj 目标对象
 * @param {String} eventType 事件类型 ==> 取值范围 - ['keyup', 'keydown', 'keypress']
 * @param {Function} handleFn 处理函数
 * 补充说明：
 * 1.若同一JQuery对象重复绑定同一eventType，会自动清除上次绑定的事件
 * 2.若handleFn有参数，可以把参数追加至handleFn参数后，第五个及其以后的参数都将视为handleFn的参数依次传入
 */
KeyEventHelper.bind = function (keyCode, tarObj, eventType, handleFn) {
	var _arguments = arguments;
	handleFn = (handleFn == null ? function () {} : handleFn);
	var handler = function (e) {
		if (e.keyCode == keyCode) {
			if (_arguments.length <= 4) {
				handleFn();
			} else {
				var argArr = [];
				$.each(_arguments, function (k, v) {
					argArr.push(v);
				});
				argArr.splice(0, 4); // 删除数组中前三个元素
				handleFn.apply(null, argArr);
			}
		}
	};
	KeyEventHelper.unbind(keyCode, tarObj, eventType);
	tarObj.data("___KeyEventHelper_" + keyCode + "_handle_" + eventType, handler);
	tarObj.bind(eventType, handler);
};

/**
 * 解绑
 * @param {Number} keyCode 键码
 * @param {JQueryObj} tarObj 目标对象
 * @param {String} eventType 事件类型 ==> 取值范围 - ['keyup', 'keydown', 'keypress']
 */
KeyEventHelper.unbind = function (keyCode, tarObj, eventType) {
	var oldHanlder = tarObj.data("___KeyEventHelper_" + keyCode + "_handle_" + eventType);
	if (oldHanlder != null) {
		tarObj.unbind(eventType, oldHanlder);
		tarObj.removeData("___KeyEventHelper_" + keyCode + "_handle_" + eventType);
	}
};

/**
 * 回车
 * @param {JQueryObj} tarObj 目标对象
 * @param {String} eventType 事件类型 ==> 取值范围 - ['keyup', 'keydown', 'keypress']
 * @param {Function} handleFn 处理函数
 * 补充说明：
 * 1.若同一JQuery对象重复绑定同一eventType，会自动清除上次绑定的事件
 * 2.若_handleFn有参数，可以把参数追加至handleFn参数后，第四个及其以后的参数都将视为handleFn的参数依次传入
 */
KeyEventHelper.enter = function (tarObj, eventType, handleFn) {
	var argArr = [ConstMgr.keyCodes.enter];
	$.each(arguments, function (k, v) {
		argArr.push(v);
	});
	KeyEventHelper.bind.apply(null, argArr);
};

/**
 * Esc
 * @param {JQueryObj} tarObj 目标对象
 * @param {String} eventType 事件类型 ==> 取值范围 - ['keyup', 'keydown', 'keypress']
 * @param {Function} handleFn 处理函数
 * 补充说明：
 * 1.若同一JQuery对象重复绑定同一eventType，会自动清除上次绑定的事件
 * 2.若_handleFn有参数，可以把参数追加至handleFn参数后，第四个及其以后的参数都将视为handleFn的参数依次传入
 */
KeyEventHelper.esc = function (tarObj, eventType, handleFn) {
	var argArr = [ConstMgr.keyCodes.esc];
	$.each(arguments, function (k, v) {
		argArr.push(v);
	});
	KeyEventHelper.bind.apply(null, argArr);
};