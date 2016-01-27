/**
 * 获取当前日期函数
 * @return {Date} 
 */
Date.curDate = function () {
	return new Date();
};

/**
 * 提取年
 * @return {String}
 */
Date.prototype.extYearStr = function () {
	return this.getFullYear().toStr();
};

/**
 * 提取月
 * @return {String} 
 */
Date.prototype.extMonthStr = function () {
	return (this.getMonth() + 1).toStr();
};

/**
 * 提取日
 * @return {String} 
 */
Date.prototype.extDayStr = function () {
	return this.getDate().toStr();
};

/**
 * 提取时
 * @return {String} 
 */
Date.prototype.extHourStr = function () {
	return this.getHours().toStr();
};

/**
 * 提取分
 * @return {String} 
 */
Date.prototype.extMinuteStr = function () {
	return this.getMinutes().toStr();
};

/**
 * 提取秒
 * @return {String} 
 */
Date.prototype.extSecondStr = function () {
	return this.getSeconds().toStr();
};

/**
 * 提取星期
 * @param {boolean} isChinese 是否中文 - 默认false
 * @return {String} - ['0':'日', '1':'一', '2':'二', '3':'三', '4':'四', '5':'五', '6':'六']
 */
Date.prototype.extDayOfTheWeekStr = function (isChinese) {
	var day = this.getDay().toStr();
	return isChinese ? ConstMgr.chinese.week[day] : day;
};

/**
 * 格式化
 * @param {String}[可空] pattern 格式化模式(缺省'yyyy-MM-dd HH:mm:ss')
 * @return {String}
 * 补充说明：
 * 1. yyyy - 年
 * 2. MM - 月
 * 3. dd - 日
 * 4. E - 星期几[日、一、二、三、四、五、六]
 * 5. hh - 时,12小时制
 * 6. HH - 时,24小时制
 * 7. mm - 分
 * 8. ss - 秒
 */
Date.prototype.format = function (pattern) {
	
	pattern = pattern == null ? 'yyyy-MM-dd HH:mm:ss' : pattern;
	
	var o = {
		"M+": this.getMonth() + 1, //月份           
		"d+": this.getDate(), //日           
		"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时           
		"H+": this.getHours(), //小时           
		"m+": this.getMinutes(), //分           
		"s+": this.getSeconds(), //秒           
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度           
		"S": this.getMilliseconds() //毫秒           
	};
	
	if (/(y+)/.test(pattern)) {
		pattern = pattern.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	
	if (/(E+)/.test(pattern)) {
		pattern = pattern.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "星期" : "周") : "") + this.extDayOfTheWeekStr(true));
	}
	
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(pattern)) {
			pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	
	return pattern;
};

/*
 * 已过时
Date.prototype.format = function (pattern) {
	pattern = (pattern == null || !pattern.isValid() ? 'yyyy-MM-dd HH:mm:ss' : pattern);
	var year = this.extYearStr();
	var month = this.extMonthStr().toNum();
	month = (month < 10 ? ['0', month].join('') : month);
	var day = this.extDayStr().toNum();
	day = (day < 10 ? ['0', day].join('') : day);
	var hour = this.extHourStr().toNum();
	hour = (hour < 10 ? ['0', hour].join('') : hour);
	var minute = this.extMinuteStr().toNum();
	minute = (minute < 10 ? ['0', minute].join('') : minute);
	var second = this.extSecondStr().toNum();
	second = (second < 10 ? ['0', second].join('') : second);
	if ('yyyy-MM-dd HH:mm:ss' == pattern) {
		return [year, '-', month, '-', day, ' ', hour, ':', minute, ':', second].join(''); 
	} else if ('yyyy/MM/dd HH:mm:ss' == pattern) {
		return [year, '/', month, '/', day, ' ', hour, ':', minute, ':', second].join(''); 
	} else if ('yyyy-MM-dd HHmmss' == pattern) {
		return [year, '-', month, '-', day, ' ', hour, minute, second].join(''); 
	} else if ('yyyy/MM/dd HHmmss' == pattern) {
		return [year, '/', month, '/', day, ' ', hour, minute, second].join(''); 
	} else if ('yyyyMMdd HH:mm:ss' == pattern) {
		return [year, month, day, ' ', hour, ':', minute, ':', second].join(''); 
	} else if ('yyyyMMddHH:mm:ss' == pattern) {
		return [year, month, day, hour, ':', minute, ':', second].join(''); 
	} else if ('yyyyMMdd HHmmss' == pattern) {
		return [year, month, day, ' ', hour, minute, second].join(''); 
	} else if ('yyyyMMddHHmmss' == pattern) {
		return [year, month, day, hour, minute, second].join(''); 
	} else if ('yyyy-MM-dd' == pattern) {
		return [year, '-', month, '-', day].join('');
	} else if ('yyyy/MM/dd' == pattern) {
		return [year, '/', month, '/', day].join('');
	} else if ('yyyyMMdd' == pattern) {
		return [year, month, day].join('');
	} else if ('HH:mm:ss' == pattern) {
		return [hour, ':', minute, ':', second].join('');
	} else if ('HHmmss' == pattern) {
		return [hour, minute, second].join('');
	}
};
*/
