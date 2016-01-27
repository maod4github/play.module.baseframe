/**
 * 鼠标
 */
function Mouse () {}

// 页面横坐标(参照当前页面)
Mouse._pageX = 0;

// 页面纵坐标(参照当前页面)
Mouse._pageY = 0;

// 窗口横坐标(参照当前窗口)
Mouse._windowX = 0;

// 窗口纵坐标(参照当前窗口)
Mouse._windowY = 0;

// 是否刷新
Mouse.isRefresh = true;

// 可停止的
Mouse.stoppable = true;

// 是否显示坐标
Mouse.isShowCoordinate = false;

/**
 * 刷新坐标
 * @param {Event} e 事件信息
 */
Mouse.refreshCoordinate = function (e) {
	Mouse._pageX = e.pageX;
	Mouse._pageY = e.pageY;
	Mouse._windowX = e.clientX;
	Mouse._windowY = e.clientY;
	if (Mouse.isShowCoordinate) {
		if ($('#MouseCoordinate').length <= 0) {
			$('body').prepend('<div id="MouseCoordinate" style="position:fixed; top:0px; right:0px; display:block; z-index:99999999; padding:5px; line-height:20px; border:1px solid gray; background-color:#fff; filter:alpha(opacity=50); opacity:0.5;"></div>');
		}
		$('#MouseCoordinate').html(['pageX:', Mouse._pageX, 'px, pageY:', Mouse._pageY, 'px<br/>windowX:', Mouse._windowX, 'px, windowY:', Mouse._windowY, 'px'].join(''));
	} else {
		$('#MouseCoordinate').remove();
	}
};

/**
 * 激活
 */
Mouse.activate = function () {
	if (Mouse.moveHandleFn != null) return ;
Printer.info("mouse activated");
	Mouse.isRefresh = true;
	//鼠标移动处理函数
	Mouse.moveHandleFn = function (e) {
		if (!Mouse.isRefresh) {
			$(document).unbind("mousemove", Mouse.moveHandleFn);
			Mouse.moveHandleFn = null;
			$('#MouseCoordinate').remove();
			return ;
		}
		Mouse.refreshCoordinate(e);
	};
	//给文档绑定鼠标移动处理事件
	$(document).mousemove(Mouse.moveHandleFn);
};

/**
 * 停止
 */
Mouse.stop = function () {
	if (Mouse.stoppable) {
		Mouse.isRefresh = false;
	}
};

/**
 * 鼠标是否超出区域
 * @param {JQueryObj} tarJQueryObj 目标对象
 * @param {number} outOffset 超出偏移，单位(像素)
 * @return {boolean}
 */
Mouse.isOverflow = function (tarJQueryObj, outOffset) {
	// 距窗口左边框偏移
	var xOffset = tarJQueryObj.offset().left;
	// 距窗口顶边框偏移
	var yOffset = tarJQueryObj.offset().top;
	var width = tarJQueryObj.width();
	var height = tarJQueryObj.height();
	// 超出偏移，也就是说，鼠标超出边框outOffset像素才隐藏
	// 若小于0，则初始化为0
	if (outOffset < 0) {
		outOffset = 0;
	}
	return (Mouse._pageX < (xOffset - outOffset) || Mouse._pageX > (xOffset + width + outOffset)|| Mouse._pageY < (yOffset - outOffset) || Mouse._pageY > (yOffset + height + outOffset));
};