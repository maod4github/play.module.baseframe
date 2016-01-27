
Array.prototype.toArrayList = function () {
	return new ArrayList(this);
};

Array.prototype.pushAll = function (arr) {
	Array.prototype.push.apply(this, arr);
	return this;
};