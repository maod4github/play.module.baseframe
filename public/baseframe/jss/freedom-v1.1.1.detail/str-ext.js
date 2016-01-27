
// 生成由数字和字母组成的字符串
String.randomWithNumAndLetter = function (len) {
	var a = '';
	for (; a.length < len; a += Math.random().toString(36).substr(2)) {}
	return a.substr(0, len);
}

String.prototype.matches = function (regexp) {
	return new RegExp(regexp).test(this);
};

String.prototype.trim = function () {
	return $.trim(this);
};

String.prototype.trimLeft = function () {
	return this.replace(/(^\s+)/g, '');
};

String.prototype.trimRight = function () {
	return this.replace(/(\s+$)/g, '');
};

// 简写的toString方法
String.prototype.toStr = function () {
	return this.toString();
};

String.prototype.toNum = function () {
	return Number(this);
};

/**
 * 设置默认值，若该值为无效的，则返回默认值 
 * @return {String}
 */
String.prototype.defVal = function (defVal) {
	return this.isValid() ? this : defVal;
};

/**
 * 比较是否相等
 * @return {boolean}
 */
String.prototype.equals = function (str) {
	return this == str;
};

/**
 * 忽略大小写比较是否相等
 * @return {boolean} 
 */
String.prototype.equalsIgnoreCase = function (str) {
	if (str == null) return false;
	return this.toUpperCase().equals(str.toUpperCase());
};

/**
 * 获取逆序字符串
 * @return {String}
 */
String.prototype.getReversedStr = function () {
	var val = '';
	for (var index = this.length - 1; index >= 0; index--) {
		val += this.charAt(index);
	}
	return val.toString();
};

/**
 * 判断是否以某字符串为前缀
 * @param {String} prefix 前缀
 * @return {boolean}
 */
String.prototype.startsWith = function (prefix) {
	return this.substr(0, prefix.length) == prefix;
};

/**
 * 判断是否以某字符串为后缀
 * @param {String} suffix 后缀
 * @return {boolean}
 */
String.prototype.endsWith = function (suffix) {
	return this.substr(this.length - suffix.length) == suffix;
};

/**
 * 提取数字
 * @return {String}
 * 补充说明：该函数只是单纯的从头至尾提取数字
 */
String.prototype.extNums = function () {
	return this.replace(/\s/g, '').replace(/\D/g, '');
};

/**
 * 提取数值(包括正负整数、正负小数)
 * @return {number}
 * 补充说明：
 * 1.若没有数字，则返回undefined
 * 2.若有'.'字符，则取第一个'.'作为小数分割点
 * 3.若有'-'字符，则首先会截取第一个'-'作为负号，并截取其后的字符作为被提取值，之前的字符将被丢弃
 */
String.prototype.extNumVal = function () {
	var val = new String(this);
	if (!val.hasNum()) return ;
	var retVal = '';
	// 负号下标
	var fhIndex = val.indexOf('-');
	// 过滤负号前面的字符
	if (fhIndex >= 0) {
		val = val.substr(fhIndex, val.length);
	}
	//小数点下标
	var decimalIndex = val.indexOf('.');
	var intVal = 0;
	var decimalVal = 0;
	if (decimalIndex >= 0) {
		intVal = val.substr(0, decimalIndex).extNums();
		decimalVal = val.substr(decimalIndex, val.length).extNums();
		if (fhIndex >= 0) {
			retVal = ["-", intVal, ".", decimalVal].join('');//.toFixed(decimalVal.length);
		} else {
			retVal = [intVal, ".", decimalVal].join('');//.toFixed(decimalVal.length);
		}
	} else {
		intVal = val.extNums();
		if (fhIndex >= 0) {
			retVal = ['-', intVal].join('');
		} else {
			retVal = intVal;
		}
	}
	return retVal.toNum();
};

/**
 * 提取身份证号码中的出生日期
 * @return {String} yyyyMMdd格式的出生日期
 * @补充说明
 * 1. 支持15、18位身份证
 */
String.prototype.extBirthdayOfIdCardNum = function () {
	var nums = this.extNums();
	if (!nums.isIdCardNum()) {
Printer.warn('格式错误，无法提取身份证号码');
		return '';
	}
	if (nums.length == 18) return nums.substr(6, 14);
	if (nums.length == 15) return '19' + nums.substr(6, 12);
	return '';
};

/**
 * 格式化成手机号码
 * @return {String}
 * 补充说明：返回格式形如 - 186 7071 7327
 */
String.prototype.formatToMobileNum = function () {
	return $.trim(this.replace(/\s/g, '').replace(/\D/g, '').replace(/(\d{3})(\d*)/g, '$1 $2').replace(/(\d{4})(\d*)/g, '$1 $2'));
};

/**
 * 格式化成银行卡号码
 * @return {String}
 * 补充说明：返回格式形如 - 6226 6226 0397 3344
 */
String.prototype.formatToBankCardNum = function () {
	return $.trim(this.replace(/\s/g, '').replace(/\D/g, '').replace(/(\d{4})/g, '$1 '));
};

/**
 * 是否有效
 * @return {boolean} - [true:有效, false:无效]
 */
String.prototype.isValid = function () {
	return this !== '' && !this.equalsIgnoreCase('NULL') && !this.equalsIgnoreCase('UNDEFINED');
};

/**
 * 是否为身份证号码
 * @return {boolean or String}
 * @补充说明
 * 		1. 推荐使用该方法来验证身份证号码
 * 		2. 若返回的为String类型，则返回值为错误消息
 */
String.prototype.isIdCardNum = function () {
	var data = new String(this);
	var iSum = 0;
	var info = '';
	if (!/^\d{17}(\d|x)$/i.test(data)) return '证件号码填写错误，请重新核对填写';
	data = data.replace(/x$/i, 'a');
	if (ConstMgr.chinese.citys[parseInt(data.substr(0, 2))] == null) return '你的身份证地区非法';
	var sBirthday = data.substr(6, 4) + '-' + Number(data.substr(10, 2)) + '-' + Number(data.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, '/'));
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) return '身份证上的出生日期非法';
	for ( var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(data.charAt(17 - i), 11);
	if (iSum % 11 != 1) return '你输入的身份证号非法';
	return true;
};

/**
 * 是否为空字符
 * @return {boolean}
 */
String.prototype.isEmptyChar = function () {
	return RegExp.emptyChar.test(this);
};

/**
 * 是否有空字符
 * @return {boolean}
 */
String.prototype.hasEmptyChar = function () {
	return RegExp.hasEmptyChar.test(this);
};

/**
 * 是否是数字
 * @return {boolean}
 * 补充说明：
 * 1.包括正负整数、正负小数
 * 2.支持 '.12' 与 '12.' 格式的匹配
 */
String.prototype.isNum = function () {
	return RegExp.num.test(this);
};

/**
 * 是否有数字
 * @return {boolean}
 * 补充说明：包括正负整数、正负小数
 */
String.prototype.hasNum = function () {
	return RegExp.hasNum.test(this);
};

/**
 * 是否是整数
 * @return {boolean}
 */
String.prototype.isIntNum = function () {
	return RegExp.intNum.test(this);
};

/**
 * 是否有整数
 * @return {boolean}
 */
String.prototype.hasIntNum = function () {
	return RegExp.hasIntNum.test(this);
};

/**
 * 是否是纯数字（无正负号）
 * @return {boolean}
 */
String.prototype.isPureNum = function () {
	return RegExp.pureNum.test(this);
};

/**
 * 是否是字母
 * @return {boolean}
 */
String.prototype.isLetter = function () {
	return RegExp.letter.test(this);
};

/**
 * 是否有字母
 * @return {boolean}
 */
String.prototype.hasLetter = function () {
	return RegExp.hasLetter.test(this);
};

/**
 * 是否是小写字母
 * @return {boolean}
 */
String.prototype.isLowerLetter = function () {
	return RegExp.lowerLetter.test(this);
};

/**
 * 是否有小写字母
 * @return {boolean}
 */
String.prototype.hasLowerLetter = function () {
	return RegExp.hasLowerLetter.test(this);
};

/**
 * 是否是大写字母
 * @return {boolean}
 */
String.prototype.isUpperLetter = function () {
	return RegExp.upperLetter.test(this);
};

/**
 * 是否有大写字母
 * @return {boolean}
 */
String.prototype.hasUpperLetter = function () {
	return RegExp.hasUpperLetter.test(this);
};

/**
 * 是否是中文
 * @return {boolean}
 */
String.prototype.isChinese = function () {
	return RegExp.chinese.test(this);
};

/**
 * 是否有中文
 * @return {boolean}
 */
String.prototype.hasChinese = function () {
	return RegExp.hasChinese.test(this);
};

/**
 * 是否为中文名
 * @return {boolean}
 */
String.prototype.isChineseName = function () {
	var len = this.length;
	
	// B 长度检查
	if (len < 2 || len > 20) return false;// 长度小于2或者大于20
	if (this.isChinese() && len > 10) return false;// 是中文且长度大于10
	// E 长度检查
	
	// B 数字与特殊字符检查
	if (this.replace(/[^\.]+/g, '').length > 1) return false;// 点号个数大于1
	if (this.hasNum()) return false;// 有数字
	if (this.replace(/[\s]+/g, '').isChinese() && this.indexOf(' ') >= 0) return false;// 是中文且有空字符
	if (this.replace(/[\.|\s]+/g, '').hasSpacialChar()) return false;// 有除了点号和空字符外的特殊字符
	// E 数字与特殊字符检查
	
	return true;
};

/**
 * 是否是特殊字符
 * @return {boolean}
 */
String.prototype.isSpacialChar = function () {
	return RegExp.spacialChar.test(this);
};

/**
 * 是否有特殊字符
 * @return {boolean}
 */
String.prototype.hasSpacialChar = function () {
	return RegExp.hasSpacialChar.test(this);
};

/**
 * 是否是半角特殊字符
 * @return {boolean}
 */
String.prototype.isBanJiaoSpacialChar = function () {
	return RegExp.banJiaoSpacialChar.test(this);
};

/**
 * 是否有半角特殊字符
 * @return {boolean}
 */
String.prototype.hasBanJiaoSpacialChar = function () {
	return RegExp.hasBanJiaoSpacialChar.test(this);
};

/**
 * 是否是全角特殊字符
 * @return {boolean}
 */
String.prototype.isQuanJiaoSpacialChar = function () {
	return RegExp.quanJiaoSpacialChar.test(this);
};

/**
 * 是否有全角特殊字符
 * @return {boolean}
 */
String.prototype.hasQuanJiaoSpacialChar = function () {
	return RegExp.hasQuanJiaoSpacialChar.test(this);
};

/**
 * 是否为电子邮箱
 * @return {boolean}
 */
String.prototype.isEmail = function () {
	return RegExp.email.test(this);
};

/**
 * 是否是URL
 * @return {boolean}
 */
String.prototype.isURL = function () {
	return this.startsWith('http://') || this.startsWith('https://');
//	return RegExp.url.test(this);
};

/**
 * 是否为手机号码
 * @return {boolean}
 */
String.prototype.isMobileNum = function () {
	return RegExp.mobileNum.test(this);
};

/**
 * 是否为短信验证码
 * @return {boolean}
 */
String.prototype.isMsgValidateCode = function () {
	return RegExp.msgValidateCode.test(this);
};

/**
 * 是否为邮政编码
 * @return {boolean}
 */
String.prototype.isPostCode = function () {
	return RegExp.postCode.test(this);
};

/**
 * 是否为登录密码
 * @return {boolean}
 * 验证规则：
 * 1.长度6~16位
 * 2.由数字、字母组成
 * 3.不能全是数字或全是字母
 */
String.prototype.isLoginPwd = function () {
	return /^[A-Za-z\d]{6,16}$/.test(this) && this.hasNum() && this.hasLetter();
};

/**
 * 是否为交易密码
 * @return {boolean}
 * 验证规则：
 * 1.长度6~16位
 * 2.由数字、字母、半角特殊字符组成
 * 3.不能全是数字、或全是字母、或全是半角特殊字符
 */
String.prototype.isTradePwd = function () {
	return (
				/^[\da-zA-Z\~\!\@\#\$\%\^\&\*\(\)\_\+\{\}\|\:\"\<\>\?\-\=\[\]\\\;\'\,\.\/]{6,16}$/.test(this) // 长度6~16位
				&& 
				(
					(this.hasNum() && this.hasLetter()) // 有数字和字母
					|| (this.hasNum() && this.hasBanJiaoSpacialChar()) // 有数字和半角特殊字符
					|| (this.hasLetter() && this.hasBanJiaoSpacialChar()) // 有字母和半角特殊字符
				)
			);
};

/**
 * 交易密码强度检查
 * @return {String} [L:低, M:中, H:高]
 */
String.prototype.checkStrengthOfTradePwd = function () {
	if (this.length < 6) // 长度小于6位
		return 'L';
	if (this.hasNum() && this.hasLetter() && this.hasBanJiaoSpacialChar()) // 有数字和字符和半角字符
		return 'H';
	if ((this.hasNum() && this.hasLetter()) // 有数字和字母
			|| (this.hasNum() && this.hasBanJiaoSpacialChar()) // 有数字和半角特殊字符
			|| (this.hasLetter() && this.hasBanJiaoSpacialChar()) // 有字母和半角特殊字符
			|| (this.hasLowerLetter() && this.hasUpperLetter())) // 有小写字符和大写字符
		return 'M';
	if (this.isPureNum() // 是纯数字
			|| this.isLowerLetter() // 是小写字母
			|| this.isUpperLetter() // 是大写字母
			|| this.isBanJiaoSpacialChar()) // 是半角特殊字符
		return 'L';
	return 'L';
};