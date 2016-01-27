/**
 * 浏览器助手
 */
function BrowserHelper() {}

/**
 * 是否是IE浏览器
 */
BrowserHelper.isIE = function () {
	return 'Microsoft Internet Explorer' == navigator.appName;
};

/**
 * 获取本地仓库
 * @return {JSON} localStorage
 */
BrowserHelper.getLocalStorage = function () {
	return localStorage;
};

/**
 * 设置本地仓库项
 * @param {String} key 键
 * @param {String} val 值
 */
BrowserHelper.setLocalItem = function (key, val) {
	BrowserHelper.getLocalStorage().setItem(key, val);
};

/**
 * 获取本地仓库项
 * @param {String} key 键
 * @return {String} 匹配key键的项值
 */
BrowserHelper.getLocalItem = function (key) {
	return BrowserHelper.getLocalStorage().getItem(key);
};

/**
 * 移除本地仓库项
 * @param {String} key 键
 */
BrowserHelper.removeLocalItem = function (key) {
	BrowserHelper.getLocalStorage().removeItem(key);
};

/**
 * 清除本地仓库
 */
BrowserHelper.clearLocalItem = function () {
	BrowserHelper.getLocalStorage().clear();
};

/**
 * 获取会话仓库
 */
BrowserHelper.getSessionStorage = function () {
	return sessionStorage;
};

/**
 * 设置会话仓库项
 * @param {String} key 键
 * @param {String} val 值
 */
BrowserHelper.setSessionItem = function (key, val) {
	BrowserHelper.getSessionStorage().setItem(key, val);
};

/**
 * 获取会话仓库项
 * @param {String} key 键
 * @return {String} 匹配key键的项值
 */
BrowserHelper.getSessionItem = function (key) {
	return BrowserHelper.getSessionStorage().getItem(key);
};

/**
 * 移除会话仓库项
 * @param {String} key 键
 */
BrowserHelper.removeSessionItem = function (key) {
	BrowserHelper.getSessionStorage().removeItem(key);
};

/**
 * 清除会话仓库
 */
BrowserHelper.clearSessionItem = function () {
	BrowserHelper.getSessionStorage().clear();
};