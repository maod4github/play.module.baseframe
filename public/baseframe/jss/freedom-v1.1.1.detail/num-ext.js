/**
 * 生成一个随机整数
 * @param {Number} places 位数
 * @param {Boolean} isPlusOrNot 正或负(false为负，否则均为正)
 * @return {number}
 */
Number.randomInt = function (places, isPlusOrNot) {
	if (places > 16) {
Printer.warn('最多支持16位整数');
		return ;
	}
	isPlusOrNot = (isPlusOrNot != false ? true : false);
	var beiShu = 1;
	for (var index = 1; index <= places; index ++) {
		beiShu *= 10;
	}
	var result = (Math.random() * beiShu).extIntVal();
	while (result.toStr().length < places) {
		result = (Math.random() * beiShu).extIntVal();
	}
	return isPlusOrNot ? result : -result;
};

Number.randomIntWithRange = function (min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
};

/**
 * String转换的简写函数
 * @return {String}
 */
Number.prototype.toStr = function () {
	return this.toString();
};

/**
 * 格式化成字符串
 * @param {number} keepLen 保留长度(默认全部，最多保留20位小数)
 * @param {boolean} [可空] isRound 是否四舍五入(默认false)[true:是, 其他:否]
 * @return {String}
 */
Number.prototype.format = function (keepLen, isRound) {
	keepLen = (keepLen == null ? 0 : keepLen);
	keepLen = (keepLen < 0 ? 0 : keepLen);
	keepLen = (keepLen > 20 ? 20 : keepLen);
	isRound = (isRound == null ? false : isRound);
	if (isRound || this.toStr().indexOf('.') < 0) return this.toFixed(keepLen);
	if (this.toStr().substr(this.toStr().indexOf('.') + 1).length <= keepLen) return this.toFixed(keepLen);
	return this.toStr().substr(0, this.toStr().indexOf('.') + (keepLen == 0 ? -1 : keepLen) + 1);
};

/**
 * 格式化成金额字符串
 * @param {boolean} isRound 是否四舍五入(默认false)
 * @param {number} retMode 返回模式 [1:返回格式形如 - 1,000,000.00, 其他(默认):返回格式形如 - 1000000.00]
 * @return {String}
 */
Number.prototype.formatToMoney = function (isRound, retMode) {
	isRound = (isRound === undefined ? false : isRound);
	var val = this.format(2, isRound);
	var isFuShu = false;
	if (val.charAt(0) === '-') {
		isFuShu = true;
		val = val.substr(1);
	}
	var dicimalIndex = val.indexOf('.');
	var intVal = val.substr(0, dicimalIndex);
	var decimalVal = val.substr(dicimalIndex + 1);
	val = intVal.getReversedStr().replace(/(\d{3})/g, '$1,');
	val = val.getReversedStr();
	val = (val.charAt(0) === ',' ? val.substr(1) : val);
	if (retMode === 1) {
		//返回格式形如 - 1,000,000.00
		return (isFuShu ? ['-', val, '.', decimalVal] : [val, '.', decimalVal]).join('');
	}
	//返回格式形如 - 1000000.00
	return (isFuShu ? ['-', val, '.', decimalVal] : [val, '.', decimalVal]).join('').replace(/\,/g, '');
};

/**
 * 大写数字
 * @补充说明
 * 1. 支持负数、小数
 * 2. 整数位最多支持16位（千万亿）的最大值-1（9999999999999998）
 */
Number.prototype.toUpperNum = function () {
	
	if (this > 9999999999999998) {
		return '正数的整数位最多支持16位（千万亿）的最大值-1（9999999999999998）';
	}
	if (this < -9999999999999998) {
		return '负数的整数位最多支持16位（千万亿）的最小值+1（-9999999999999998）';
	}
	
	var val = this.toStr();
	
	// 小数转换者
	var decimalTransverter = function (val, decimalIndex) {
		var intVal = val.substr(0, decimalIndex);
		var decimalVal = val.substr(decimalIndex + 1);
		var intValUpper = eval(['_', intVal.length, 'weiTransverter(intVal);'].join(''));
		var result = '' + intValUpper + ConstMgr.chinese.nums['.'];
		for (var index = 0; index < decimalVal.length; index ++) {
			result += ConstMgr.chinese.nums[decimalVal.charAt(index)];
		}
		return result.toStr();
	};
	// 万位转换者
	var wanweiIntTransverter = function (val, wei) {
		val = val.toNum();
		val = val.toStr();
		if (val.length != wei) return [ConstMgr.chinese.nums[0], eval(['_', val.length, 'weiTransverter(val);'].join(''))].join('');
		var result = '';
		var wanwei = eval(['_', (wei - 4), 'weiTransverter(val.substr(0, ', (wei - 4), '));'].join(''));
		var qianResult = _4weiTransverter(val.substr(wei - 4));
		result = [wanwei, ConstMgr.chinese.nums[13], (qianResult == [ConstMgr.chinese.nums[0], ConstMgr.chinese.nums[0]].join('') ? '' : qianResult)].join('');
		return result;
	};
	// 亿位转换者
	var yiweiIntTransverter = function (val, wei) {
		val = val.toNum();
		val = val.toStr();
		if (val.length != wei) return [ConstMgr.chinese.nums[0], eval(['_', val.length, 'weiTransverter(val);'].join(''))].join('');
		var result = '';
		var yiwei = eval(['_', (wei - 8), 'weiTransverter(val.substr(0, ', (wei - 8), '));'].join(''));
		var qianwanResult = _8weiTransverter(val.substr(wei - 8));
		result = [yiwei, ConstMgr.chinese.nums[14], (qianwanResult == [ConstMgr.chinese.nums[0], ConstMgr.chinese.nums[0]].join('') ? '' : qianwanResult)].join('');
		return result;
	};
	// 个
	var _1weiTransverter = function (val) {
		return ConstMgr.chinese.nums[val];
	};
	// 拾
	var _2weiTransverter = function (val) {
		var gewei = ConstMgr.chinese.nums[val.charAt(1)];
		var shiwei = ConstMgr.chinese.nums[val.charAt(0)];
		return [((shiwei == ConstMgr.chinese.nums[0] || shiwei == ConstMgr.chinese.nums[1]) ? '' : shiwei), ConstMgr.chinese.nums[10], (gewei == ConstMgr.chinese.nums[0] ? '' : gewei)].join('');
	};
	// 佰
	var _3weiTransverter = function (val) {
		val = val.toNum();
		val = val.toStr();
		if (val.length != 3) return [ConstMgr.chinese.nums[0], eval(['_', val.length, 'weiTransverter(val);'].join(''))].join('');
		var result = '';
		var gewei = ConstMgr.chinese.nums[val.charAt(2)];
		var shiwei = ConstMgr.chinese.nums[val.charAt(1)];
		var baiwei = ConstMgr.chinese.nums[val.charAt(0)];
		result = [baiwei, ConstMgr.chinese.nums[11], (shiwei == ConstMgr.chinese.nums[0] ? '' : shiwei), (shiwei == ConstMgr.chinese.nums[0] ? '' : ConstMgr.chinese.nums[10]), (gewei == ConstMgr.chinese.nums[0] ? '' : gewei)].join('');
		result = ((shiwei == ConstMgr.chinese.nums[0] && gewei != ConstMgr.chinese.nums[0]) ? [result.substr(0, 2), ConstMgr.chinese.nums[0], result.substr(2)].join('') : result);
		return result
	};
	// 仟
	var _4weiTransverter = function (val) {
		val = val.toNum();
		val = val.toStr();
		if (val.length != 4) return [ConstMgr.chinese.nums[0], eval(['_', val.length, 'weiTransverter(val);'].join(''))].join('');
		var result = '';
		var qianwei = ConstMgr.chinese.nums[val.charAt(0)];
		var baiResult = _3weiTransverter(val.substr(1));
		result = [qianwei, ConstMgr.chinese.nums[12], (baiResult == [ConstMgr.chinese.nums[0], ConstMgr.chinese.nums[0]].join('') ? '' : baiResult)].join('');
		return result;
	};
	// 万
	var _5weiTransverter = function (val) {
		return wanweiIntTransverter(val, 5);
	};
	// 拾万
	var _6weiTransverter = function (val) {
		return wanweiIntTransverter(val, 6);
	};
	// 佰万
	var _7weiTransverter = function (val) {
		return wanweiIntTransverter(val, 7);
	};
	// 仟万
	var _8weiTransverter = function (val) {
		return wanweiIntTransverter(val, 8);
	};
	// 亿
	var _9weiTransverter = function (val) {
		return yiweiIntTransverter(val, 9);
	};
	// 拾亿
	var _10weiTransverter = function (val) {
		return yiweiIntTransverter(val, 10);
	};
	// 佰亿
	var _11weiTransverter = function (val) {
		return yiweiIntTransverter(val, 11);
	};
	// 仟亿
	var _12weiTransverter = function (val) {
		return yiweiIntTransverter(val, 12);
	};
	// 万亿
	var _13weiTransverter = function (val) {
		return yiweiIntTransverter(val, 13);
	};
	// 拾万亿
	var _14weiTransverter = function (val) {
		return yiweiIntTransverter(val, 14);
	};
	// 佰万亿
	var _15weiTransverter = function (val) {
		return yiweiIntTransverter(val, 15);
	};
	// 仟万亿
	var _16weiTransverter = function (val) {
		return yiweiIntTransverter(val, 16);
	};
	
	var result = '';
	if (val.charAt(0) == '-') {
		result += ConstMgr.chinese.nums['-'];
		val = val.substr(1);
	}
	var decimalIndex = val.indexOf('.');
	if (decimalIndex >= 0) {
		result += decimalTransverter(val, decimalIndex);
	} else {
		result += eval(['_', val.length, 'weiTransverter(val);'].join(''));
	}
	return result.replace(/零拾/g, '零壹拾').toStr();
};

/**
 * 提取整数值
 * @return {number}
 */
Number.prototype.extIntVal = function () {
	var thisStr = this.toStr();
	var decimalIndex = thisStr.indexOf('.');
	if (decimalIndex >= 0) return Number(thisStr.substr(0, decimalIndex));
	else return Number(this);
};

/**
 * 提取小数值
 * @return {number} - null or 0.*****
 */
Number.prototype.extDecimalVal = function () {
	var thisStr = this.toStr();
	var decimalIndex = thisStr.indexOf('.');
	if (decimalIndex >= 0) return thisStr.substr(decimalIndex).toNum();
	else return null;
};

/**
 * 是否在范围内
 * @param {Number} floor 下限，最低0 - 默认0
 * @param {Number} ceiling 上限，最高无 - 默认无
 */
Number.prototype.isInRange = function (floor, ceiling) {
	floor = (floor < 0 ? 0 : floor);
	if (ceiling == null) return this >= floor;
	return this >= floor && this <= ceiling;
};