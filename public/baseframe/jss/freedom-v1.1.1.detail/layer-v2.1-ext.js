$(function () {
	
	/**
	 * 提交中 (该方法可防止表单重复提交,注意与layer.submitted配合使用)
	 */
	layer.submitting = function (msg) {
		msg = msg == null ? '数据处理中,请稍候...' : msg;
		window.sessionStorage.__osi_cur_submitting_layer_index = layer.mmsg(msg, {
			icon : 16,
			time : 0,
			shade : 0.01
		});
	};
	
	/**
	 * 已提交完成 (该方法可防止表单重复提交,注意与layer.submitting配合使用)
	 */
	layer.submitted = function () {
		layer.close(window.sessionStorage.__osi_cur_submitting_layer_index);
	};
	
	/**
	 * my msg,对一些默认值重设
	 */
	layer.mmsg = function (msg, opt_kvs, end_fn) {
		opt_kvs = opt_kvs == null ? {} : opt_kvs;
		opt_kvs.area = opt_kvs.area == null ? ['auto'] : opt_kvs.area;
		if (opt_kvs.icon > -1) {
			msg = '<span style="color:#6a6a6a;">' + msg + '</span>';
		}
		var index = layer.msg(msg, opt_kvs, end_fn);
		// 由于layer.msg高度样式不兼容火狐、IE,总是需要加这句
		$('#layui-layer' + index + '>.layui-layer-content').css('height', 'auto');
		return index;
	}
	
});