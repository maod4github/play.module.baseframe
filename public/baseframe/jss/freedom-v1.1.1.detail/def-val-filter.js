/**
 * 默认值过滤 
 * @author maodun
 */
$(function () {
	
	$('a').live('click', function () {
		var __this = $(this); 
		var href = $.trim(__this.attr('href'));
		if ('' == href || '#' == href) {
			__this.attr('href', 'javascript:;').removeAttr('target');
		}
	});

//	$('img:not([src]), img[src=""]').attr('src', __ogv.img_def_src);
	
});