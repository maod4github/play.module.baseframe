/**
 * 有序数组集合
 */
function ArrayList(arr) {
	if (arr !== undefined) {
		this._elems = arr;
	}
}

/**元素集*/
ArrayList.prototype._elems = [];

/**
 * 获取元素个数
 * @return {number}
 */
ArrayList.prototype.size = function () {
	return this._elems.length;
};

/**
 * 是否为空
 * @return {boolean}
 */
ArrayList.prototype.isEmpty = function () {
	return this._elems.length == 0;
};

/**
 * 根据下标获取元素
 * @param {number} index 下标
 * @return 该下标所指向的元素
 */
ArrayList.prototype.get = function (index) {
	if (!this.isInRange(index)) return null;
	return this._elems[index];
};

/**
 * 获取所有元素
 * @return {Array}
 */
ArrayList.prototype.getAll = function () {
	return this._elems;
};

/**
 * 根据下标设置元素
 * @param {number} index 下标
 * @param {Object} element 新元素
 * @return 老元素
 */
ArrayList.prototype.set = function (index, element) {
	if (!this.isInRange(index)) return null;
	return this._elems.splice(index, 1, element)[0];
};

/**
 * 设置所有元素
 * @param {Array} collection 新元素集
 * @return {Array} 老元素集
 */
ArrayList.prototype.setAll = function (collection) {
	var oldElements = this._elems;
	this._elems = collection;
	return oldElements;
};

/**
 * 添加元素
 * @param {Object} obj 需要添加的元素
 * @return this
 */
ArrayList.prototype.add = function (obj) {
	this._elems.push(obj);
	return this;
};

/**
 * 添加一个元素集
 * @param {Array} arr 需要添加的元素集
 * @return this
 */
ArrayList.prototype.addAll = function (arr) {
	this._elems.pushAll(arr);
	return this;
};

/**
 * 根据下标删除元素
 * @param {number} index 下标
 * @return 被删除的元素
 */
ArrayList.prototype.remove = function (index) {
	if (!this.isInRange(index)) return null;
	return this._elems.splice(index, 1)[0];
};

/**
 * 删除所有元素
 * @return {Array} 被删除的元素集
 */
ArrayList.prototype.removeAll = function () {
	return this._elems.splice(0, this.size());
};

/**
 * 打印当前元素集
 */
ArrayList.prototype.printElements = function () {
	Printer.obj(this._elems);
};

/**
 * 是否在范围里
 * @param (number) index 下标
 * @return {boolean}
 */
ArrayList.prototype.isInRange = function (index) {
	var size = this.size();
	if (index >= size) {
Printer.err('ArrayIndexOutOfBoundsException(index: ' + index + ', size: ' + size + ')');
		return false;
	}
	return true;
};

/**
 * 查看对应元素的位置
 * @param {Object} element 元素
 * @return {number} 对应element的下标，找不到返回-1
 */
ArrayList.prototype.indexOf = function (element) {
	return $.inArray(element, this.getAll());
};