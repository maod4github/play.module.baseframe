/**
 * 省、市、县/区，三级联动插件
 * @author maodun
 */
$(function () {
	
	// B 省份 - init options - 给所有省份select，以异步方式初始化其options（仅适用于含有__otp_provinces自定义标签属性的）
	// 补充说明：
	// 1. 这里不适合于动态追加到DOM中的select，只适合页面一加载就存在的select
	// 2. 对于动态的追加到DOM中的select，需调用$(this).loadProvinces()JQuery插件方法加载其options
	if ($('select[__otp_provinces]').length > 0) {
		$.ajax({
			type : 'get',
	   		url : __ogv.routes.get_provinces,
	   		success : function (provinces) {
	   			$.each($('select[__otp_provinces]'), function (i, v) {
	   				var select = $(v);
	   				var def_code = $.trim(select.attr('__otp_def_code'));
	   				var has_the_def_code = false;
	   				select.children().remove();
	   				select.append('<option value="0">请选择省份</option>');
	   				$.each(provinces, function (i, v) {
	   					if (def_code == v.code) {
	   						has_the_def_code = true;
		   				}
	   					$('<option value="' + v.code + '" __otp_is_zxs="' + v.is_municipality + '">' + v.name + '</option>').appendTo(select);
	   				});
   					select.val(has_the_def_code ? def_code : 0).change();
	   			});
	   		},
	   		error : function() {
	   			layer.msg('服务器繁忙,获取省份失败!');
	   		}
	   	});
	}
	// E
	
	// B 省份live change - find 城市 - 给所有省份select绑定change事件，以异步方式加载其子城市（仅适用于含有__otp_provinces自定义标签属性的）
	$('select[__otp_provinces]').live('change', function () {
		var val = $.trim($(this).val());
		var group_num = $(this).attr('__otp_group_num');
		
		var the_matched_cities_select_tag = $('select[__otp_cities][__otp_group_num="' + group_num + '"]');
		the_matched_cities_select_tag.children().remove();
		the_matched_cities_select_tag.append('<option value="0">请选择城市</option>');
		
		var the_matched_counties_select_tag = $('select[__otp_counties][__otp_group_num="' + group_num + '"]');
		the_matched_counties_select_tag.children().remove();
		the_matched_counties_select_tag.append('<option value="0">请选择区县</option>');
		
		if (6 == val.length) {
		   	$.ajax({
		   		url : __ogv.routes.get_cities,
		   		type : 'get',
		   		data : {'province_code' : val},
		   		success : function (cities) {
		   			var def_code = $.trim(the_matched_cities_select_tag.attr('__otp_def_code'));
		   			var has_the_def_code_with_the_cities = false;
		   			$.each(cities, function (i, v) {
		   				if (def_code == v.code) {
		   					has_the_def_code_with_the_cities = true;
		   				}
		   				$('<option value="' + v.code + '">' + v.name + '</option>').appendTo(the_matched_cities_select_tag);
		   			});
		   			if (has_the_def_code_with_the_cities) {
		   				the_matched_cities_select_tag.val(def_code).change();
		   			}
		   			else {
		   				the_matched_cities_select_tag.removeAttr('__otp_def_code');
		   			}
		   		},
		   		error : function() {
		   			layer.msg('服务器繁忙,获取城市失败!');
		   		}
		   	});
		}
	});
	// E
	
	// S 城市live change - find 区域 - 给所有城市select绑定change事件，以异步方式加载其子区域（仅适用于含有__otp_city_addrs自定义标签属性的）
	$('select[__otp_cities]').live('change', function () {
		var val = $.trim($(this).val());
		var group_num = $(this).attr('__otp_group_num');
		var the_matched_counties_select_tag = $('select[__otp_counties][__otp_group_num="' + group_num + '"]');
		the_matched_counties_select_tag.children().remove();
		the_matched_counties_select_tag.append('<option value="0">请选择区县</option>');
		if (6 == val.length) {
		   	$.ajax({
		   		url : __ogv.routes.get_counties,
		   		type : 'get',
		   		data : {'city_code' : val},
		   		success : function (areas) {
		   			var def_code = $.trim(the_matched_counties_select_tag.attr('__otp_def_code'));
		   			var has_the_def_code_with_the_counties = false;
		   			$.each(areas, function (i, v) {
		   				if (def_code == v.code) {
		   					has_the_def_code_with_the_counties = true;
		   				}
		   				$('<option value="' + v.code + '">' + v.name + '</option>').appendTo(the_matched_counties_select_tag);
		   			});
		   			if (has_the_def_code_with_the_counties) {
		   				the_matched_counties_select_tag.val(def_code).change();
		   			}
		   			else {
		   				the_matched_counties_select_tag.removeAttr('__otp_def_code');
		   			}
		   		},
		   		error : function() {
		   			layer.msg('服务器繁忙,获取区县失败!');
		   		}
		   	});
		}
	});
	// E
	
});