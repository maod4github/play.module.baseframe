/**
 * 定时器
 * @param {number} interval 间隔(单位：毫秒)
 * @param {number} step_size 步长(小于或等于0表示无限循环，直到调用this.stop();才停止)
 * @param {function} loop_fn 循环函数(执行时，会传入loop_fn_params，并增加cur_step属性)
 * @param [可空] {JSON} loop_fn_params 循环函数参数
 * @param [可空] {function} finish_fn 任务结束函数(执行时，会传入finish_fn_params，并增加cur_step属性)
 * @param [可空] {JSON} finish_fn_params 循环函数参数
 */
function Timer(interval, step_size, loop_fn, loop_fn_params, finish_fn, finish_fn_params) {
	this._interval = interval;
	this._step_size = step_size;
	this._loop_fn = $.type(loop_fn) != 'function' ? function () {} : loop_fn;
	this._loop_fn_params = loop_fn_params == null ? {} : loop_fn_params;
	this._finish_fn = $.type(finish_fn) != 'function' ? function () {} : finish_fn;
	this._finish_fn_params = finish_fn_params == null ? {} : finish_fn_params;
	this._toggle = false;
	this._is_dead = false;
	this._cur_step = 0;
}

/**
 * 是否完成
 * @return {boolean}
 */
Timer.prototype.isFinish = function () {
	return this._step_size > 0 && this._step_size == this._cur_step;
};

/**
 * 启动
 */
Timer.prototype.start = function () {
	// this._cur_step = 0;
	if (this._is_dead) return ;
	this._toggle = true;
	var thiz = this;
	window.setTimeout(function () {Timer.exec(thiz);}, this._interval);
};

/**
 * 暂停
 */
Timer.prototype.pause = function () {
	this._toggle = false;
};

/**
 * 停止
 */
Timer.prototype.stop = function () {
	this._toggle = false;
	this._is_dead = true;
	this._step_size = this._cur_step = 1;
};

/**
 * 执行定时器
 * @param {Timer} timer Timer对象
 */
Timer.exec = function (timer) {
	if (!timer._toggle) return ;
	timer._cur_step ++;
	if (timer.isFinish()) {
		timer._finish_fn_params.cur_step = timer._cur_step;
		timer._finish_fn(timer._finish_fn_params);
	}
	else {
		timer._loop_fn_params['cur_step'] = timer._cur_step;
		timer._loop_fn(timer._loop_fn_params);
		window.setTimeout(function () {Timer.exec(timer);}, timer._interval);
	}
};