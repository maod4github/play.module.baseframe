package baseframe.groovy;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.baseframe.BaseModel;
import models.baseframe.MetadataAdaptor;
import play.libs.Codec;
import play.templates.FastTags;
import play.templates.FastTags.Namespace;
import play.templates.GroovyTemplate.ExecutableTemplate;
import play.templates.JavaExtensions;
import baseframe.consts.CommonConst;
import baseframe.groovy.CustomTags.DTOs.Banner;
import baseframe.groovy.CustomTags.DTOs.PagingBean;
import baseframe.helpers.MetadataHelper;
import baseframe.helpers.StringHelper;

@Namespace(value = "bf")
public final class CustomTags extends FastTags {
	
	/**
	 * banner箱子1(宽度自适应)<br>
	 * <b>作者 : </b>maodun<br>
	 * <b>创建时间 : </b>2016年1月4日,下午5:56:41<br>
	 * 
	 	参数列表:
				参数名			必填					      取值类型								默认值			    描述
		----------------------------------------------------------------------------------------------------------------
				(缺省)  			是	List<baseframe.groovy.CustomTags.DTOs.Banner>		    无			 	Banner集
				height	  		否					 {String}				  		   '330px'		                   高
		----------------------------------------------------------------------------------------------------------------
		调用范例:
		#{bf.bannerBox1 banners, height:'330px' /}
	 */
	public static final void _bannerBox1(Map<String, Object> args, Closure body, PrintWriter pw, ExecutableTemplate et, int from_line_num) {
		
		List<Banner> banners = (List<Banner>) args.get("arg");
		
		String height = String.valueOf(args.get("height"));
		height = StringHelper.isValid(height) ? height : "330px";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div style=\"position:relative; width:100%; min-width:1080px; height:").append(height).append("; overflow:hidden;\">");
			sb.append("<div class=\"__oc_b_box_m1\" style=\"height:").append(height).append(";\">");
				sb.append("<ul class=\"__oc_b_box_m1_mul\" style=\"height:").append(height).append(";\">");
				for (Banner b : banners) {
					sb.append("<li class=\"__oc_b_box_m1_mli\">");
					if (b.enable_href) {
						sb.append("<a href=\"").append(b.href).append("\" target=\"").append(b.target.getVal()).append("\" style=\"cursor:").append(StringHelper.isValid(b.href) ? "pointer" : "default").append(";\">");
					}
					else {
						sb.append("<a href=\"\" style=\"cursor:default;\">");
					}
							sb.append("<img style=\"width:1920px; height:").append(height).append(";\" src=\"").append(StringExts.getAtcmtSrc(b.fullname)).append("\">");
						sb.append("</a>");
					sb.append("</li>");
				}
				sb.append("</ul>");
				sb.append("<ul class=\"__oc_b_box_m1_cul\"></ul>");
			sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<script type=\"text/javascript\">");
			sb.append("bBoxScrollMode1($('.__oc_b_box_m1_mul'), $('.__oc_b_box_m1_cul'), $('.__oc_b_box_m1'));");
		sb.append("</script>");
		
		pw.println(sb.toString());
	}
	
	/**
	 * banner箱子2(固定宽高)<br>
	 * <b>作者 : </b>maodun<br>
	 * <b>创建时间 : </b>2016年1月4日,下午6:19:00
	 * 
	 	参数列表:
				参数名			必填					      取值类型								默认值			    描述
		----------------------------------------------------------------------------------------------------------------
				(缺省)  			是	List<baseframe.groovy.CustomTags.DTOs.Banner>		    无			 	Banner集
				width	  		否					 {String}				  		   '1080px'		                   宽
				height	  		否					 {String}				  		   '330px'		                   高
		----------------------------------------------------------------------------------------------------------------
		调用范例:
		#{bf.bannerBox2 banners, width:'1080px', height:'330px' /}
	 */
	public static final void _bannerBox2(Map<String, Object> args, Closure body, PrintWriter pw, ExecutableTemplate et, int from_line_num) {
		
		List<Banner> banners = (List<Banner>) args.get("arg");
		
		String width = String.valueOf(args.get("width"));
		width = StringHelper.isValid(width) ? width : "1080px";
		
		String height = String.valueOf(args.get("height"));
		height = StringHelper.isValid(height) ? height : "330px";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div class=\"__oc_b_box_m2\" style=\"width:").append(width).append("; height:").append(height).append(";\">");
			sb.append("<ul class=\"__oc_b_box_m2_ul\">");
			for (Banner b : banners) {
				sb.append("<li style=\"width:").append(width).append("; height:").append(height).append(";\">");
				if (b.enable_href) {
					sb.append("<a href=\"").append(b.href).append("\" target=\"").append(b.target.getVal()).append("\" style=\"cursor:").append(StringHelper.isValid(b.href) ? "pointer" : "default").append(";\">");
				}
				else {
					sb.append("<a href=\"\" style=\"cursor:default;\">");
				}
						sb.append("<img src=\"").append(StringExts.getAtcmtSrc(b.fullname)).append("\" style=\"width:").append(width).append("; height:").append(height).append(";\">");
					sb.append("</a>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("<div class=\"__oc_b_box_m2_btn_bar\">");
				sb.append("<a href=\"\" class=\"__oc_b_box_m2_prev __oc_b_box_m2_btn_bg\"></a>");
				sb.append("<a href=\"\" class=\"__oc_b_box_m2_next __oc_b_box_m2_btn_bg\"></a>");
			sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<script type=\"text/javascript\">");
			sb.append("bBoxScrollMode2($('.__oc_b_box_m2_prev'), $('.__oc_b_box_m2_next'), $('.__oc_b_box_m2'), $('.__oc_b_box_m2_ul'));");
		sb.append("</script>");
		
		pw.println(sb.toString());
	}
	
	/**
	 * 分页<br>
	 * <b>作者 : </b>maodun<br>
	 * <b>创建时间 : </b>2016年1月4日,下午5:47:12
	 	参数列表:
				参数名			必填					      取值类型								默认值					      描述
		--------------------------------------------------------------------------------------------------------------------------------
				bean(缺省)  		是	{baseframe.groovy.CustomTags.DTOs.PagingBean}		    无			  	 		分页Bean对象
				skin	  		否					 {String}				  			'#e75a3e'		     		皮肤颜色
				skip	  		否					 {boolean}							false		     		是否开启跳页
		   	   groups	  		否					   {int}					 		5			   			连续显示分页数
		    	first	  		否				 {String}{boolean}						'首页'	 		首页按钮文案,若想不显示首页按钮,设置false即可
		    	last	  		否				 {String}{boolean}						'末页'	 		末页按钮文案,若想不显示首页按钮,设置false即可
		    	prev	  		否				 {String}{boolean}						'<'	 			上一页按钮文案,若想不显示上一页按钮,设置false即可
		    	next	  		否				 {String}{boolean}						'>'	 			下一页按钮文案,若想不显示上一页按钮,设置false即可
		--------------------------------------------------------------------------------------------------------------------------------
		调用范例:
		#{bf.paging pbean, skip:true, prev:'上一页', next:'下一页'}
			layer.msg(
				'这里是点击分页按钮的事件函数体' +
				'当前页码:' + bean.cur + ', ' +
				'总页码:' + bean.pages + ', ' + 
				'连续分页数:' + bean.groups
			);
		#{/bf.paging}
	 */
	public static final void _paging(Map<String, Object> args, Closure body, PrintWriter pw, ExecutableTemplate et, int from_line_num) {
		
		String container_id = "paging-container-" + new Date().getTime();

		Object tmp = args.get("bean");
		PagingBean pb = ((PagingBean) (tmp == null ? args.get("arg") : tmp));
		
		tmp = args.get("skin");
		String skin = (tmp == null ? "#e75a3e" : (String) tmp);
		
		tmp = args.get("skip");
		boolean skip = (tmp == null ? false : (Boolean) tmp);
		
		tmp = args.get("groups");
		int groups = (tmp == null ? 5 : (Integer) tmp);
		groups = groups < 1 ? 0 : groups;
		
		tmp = args.get("first");
		String first = tmp == null ? "首页" : String.valueOf(tmp);
		first = "false".equals(first) ? first : "'" + first + "'";
		
		tmp = args.get("last");
		String last = tmp == null ? "末页" : String.valueOf(tmp);
		last = "false".equals(last) ? last : "'" + last + "'";
		
		tmp = args.get("prev");
		String prev = tmp == null ? "<" : String.valueOf(tmp);
		prev = "false".equals(prev) ? prev : "'" + prev + "'";
		
		tmp = args.get("next");
		String next = tmp == null ? ">" : String.valueOf(tmp);
		next = "false".equals(next) ? next : "'" + next + "'";
		
		StringBuffer html = new StringBuffer();
		html.append("<style type=\"text/css\">");
			html.append("#").append(container_id).append(">.laypage_main {}");
			html.append("#").append(container_id).append(">.laypage_main>a {border-radius:1px; width:auto; height:22px; line-height:22px;}");
			html.append("#").append(container_id).append(">.laypage_main>a:HOVER {border-color:").append(skin).append("; background-color:").append(skin).append("; color:#fff; transition:0.4s;}");
			/* 
			html.append(".laypage_main>a.laypage_prev:HOVER {border-color:#ccc; background-color:#fff; color:#666;}");
			html.append(".laypage_main>a.laypage_next:HOVER {border-color:#ccc; background-color:#fff; color:#666;}");
			 */
			html.append("#").append(container_id).append(">.laypage_main>span {width:auto; height:24px; line-height:24px;}");
			html.append("#").append(container_id).append(">.laypage_main>span.laypage_curr {border-radius:1px; background-color:").append(skin).append("; color:#fff; font-weight:normal; cursor:default; height:24px; line-height:24px;}");
			html.append("#").append(container_id).append(">.laypage_main>span>.laypage_skip {height:22px; line-height:22px;}");
			html.append("#").append(container_id).append(">.laypage_main>span>.laypage_btn {border-radius:1px; height:24px; line-height:22px; cursor:pointer;}");
			html.append("#").append(container_id).append(">.laypage_main>span>.laypage_btn:HOVER {border-color:").append(skin).append("; background-color:").append(skin).append("; color:#fff; transition:0.4s;}");
			html.append("#").append(container_id).append(">.laypage_main a, .laypage_main span {margin:0 3px 6px;}");
			html.append("#").append(container_id).append(">.laypage_main a {padding:0 10px;}");
			html.append("#").append(container_id).append(">.laypage_main span {padding:0 11px;}");
		html.append("</style>");
		
		html.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
			html.append("$(function () {");
				html.append("$('#").append(container_id).append(">.laypage_main>.laypage_total>.laypage_btn').live('click', function () {");
					html.append("var thiz = this;");
					html.append("var val = $.trim($(thiz).siblings('.laypage_skip').val());");
					html.append("if (val == '') {");
						html.append("return ;");
					html.append("}");
					html.append("val = Number(val);");
					html.append("var pages = Number('").append(pb.pages).append("');");
					html.append("if (val > pages) {");
						html.append("layer.msg('最多' + pages + '页');");
					html.append("}");
					html.append("else if (val < 1) {");
						html.append("layer.msg('最小1页');");
					html.append("}");
				html.append("});");
				html.append("$('#").append(container_id).append(">.laypage_main>.laypage_total>.laypage_skip').live('keyup', function (e) {");
					html.append("var thiz = this;");
					html.append("if (e.keyCode == ConstMgr.keyCodes.enter) {");
						html.append("$(thiz).siblings('.laypage_btn').click();");
					html.append("}");
				html.append("});");
				html.append("$('#").append(container_id).append(">.laypage_main>.laypage_last, .laypage_main>.laypage_first').live('mouseenter', function () {");
					html.append("$(this).attr('title', $(this).attr('data-page'));");
				html.append("});");
				html.append("laypage({");
					html.append("cont : $('#").append(container_id).append("'),");
					html.append("pages : '").append(pb.pages).append("',"); // 总页数
					html.append("curr : '").append(pb.cur).append("',"); // 当前页码
//					html.append("skin : 'yahei',"); // 加载内置皮肤，支持'yahei'、'molv'，也可以直接赋值16进制颜色值，如:#E14339
					html.append("skip : ").append(skip).append(","); // 是否开启跳页
					html.append("groups : ").append(groups).append(","); // 连续显示分页数
					html.append("first : ").append(first).append(","); // 首页按钮文案,默认为页码数字,若想不显示首页按钮,设置false即可
					html.append("last : ").append(last).append(","); // 末页按钮文案,默认为页码数字,若想不显示末页按钮,设置false即可
					html.append("prev : ").append(prev).append(","); // 上一页按钮文案,默认为'上一页',若想不显示上一页按钮,设置false即可
					html.append("next : ").append(next).append(","); // 下一页按钮文案,默认为'下一页',若想不显示下一页按钮,设置false即可
					html.append("jump : function (bean, is_first) {");
						html.append("bean.cur = bean.curr;");
						html.append("if (!is_first) {");
							html.append(JavaExtensions.toString(body));
						html.append("}");
					html.append("}");
				html.append("});");
			html.append("});");
		html.append("</script>");
		
		html.append("<div style=\"float:left; height:26px; line-height:26px; color:#999; padding:0px 12px; font-size:12px;\">每页").append(pb.disp_count).append("条, 共").append(pb.total_count).append("条</div>");
		html.append("<div id=\"").append(container_id).append("\" style=\"float:left; font-size:12px;\"></div>");
		html.append("<div style=\"clear:both;\"></div>");

		pw.println(html.toString());
	}
	
	/**
	 * 省\市\区县地址联动自定义标签页<br>
	 * <b>作者 : </b>maodun<br>
	 * <b>创建时间 : </b>2016年1月7日,下午2:59:41
		参数列表:
				参数名			必填					        取值类型					默认值					      描述
		--------------------------------------------------------------------------------------------------------------------------------
			  name_arr	  		 否					  {Array}<String>  		["", "", ""]		  	 name数组,依次对应省、市、区县select
		    def_code_arr	  	 否					  {Array}<String>		["", "", ""]	     	   默认代码数组,依次对应省、市、区县select
		--------------------------------------------------------------------------------------------------------------------------------
		调用范例1:
		#{bf.addrLinkage /}
		调用范例2:
		#{bf.addrLinkage name_arr:['province', 'city', 'county'], def_code_arr:['430000', '430600', '430623'] /}
	 */
	public static final void _addrLinkage(Map<String, Object> args, Closure body, PrintWriter pw, ExecutableTemplate et, int from_line_num) {
		
		Object tmp = args.get("name_arr");
		String[] name_arr = null;
		if (tmp == null) {
			name_arr = new String[] {"", "", ""};
		}
		else if (tmp instanceof String[]) {
			name_arr = (String[]) tmp;
		}
		else {
			List tmp_list = (List) tmp;
			name_arr = Arrays.copyOf(tmp_list.toArray(), tmp_list.size(), String[].class);
		}
		
		tmp = args.get("def_code_arr");
		String[] def_code_arr = null;
		if (tmp == null) {
			def_code_arr = new String[] {"", "", ""};
		}
		else if (tmp instanceof String[]) {
			def_code_arr = (String[]) tmp;
		}
		else {
			List tmp_list = (List) tmp;
			def_code_arr = Arrays.copyOf(tmp_list.toArray(), tmp_list.size(), String[].class);
		}
		
		String group_num = Codec.UUID();
		
		StringBuffer html = new StringBuffer();
		
		html.append("<select name=\"").append(name_arr[0]).append("\" __otp_provinces __otp_group_num=\"").append(group_num).append("\" __otp_def_code=\"").append(def_code_arr[0]).append("\" style=\"margin-right:5px;\"></select>");
		html.append("<select name=\"").append(name_arr[1]).append("\" __otp_cities __otp_group_num=\"").append(group_num).append("\" __otp_def_code=\"").append(def_code_arr[1]).append("\" style=\"margin-right:5px;\"></select>");
		html.append("<select name=\"").append(name_arr[2]).append("\" __otp_counties __otp_group_num=\"").append(group_num).append("\" __otp_def_code=\"").append(def_code_arr[2]).append("\"></select>");
		
		pw.print(html.toString());
	}

	/**
	 * 上传多张图片控件展示自定义标签页<br>
	 * <b>作者 : </b>maodun<br>
	 * <b>创建时间 : </b>2016年1月7日,上午9:39:07
	 * 
		参数列表:
		参数名			必填					        取值类型					默认值					      描述
		--------------------------------------------------------------------------------------------------------------------
		(缺省)			 否			  		  {Array}<String>			null		     	   	展示图片fullname数组
		group_num		 否					  {String}					null					组号,用于匹配上传按钮
		max	  	 	 	 否			  		  {Number}			 	    null	     	   		最多展示图片数
		--------------------------------------------------------------------------------------------------------------------
		调用范例:
		#{bf.uiwMoreDisp img_arr, group_num:'uiw_more_101', max:5 /}
	 */
	public static final void _uiwMoreDisp(Map<String, Object> args, Closure body, PrintWriter pw, ExecutableTemplate et, int from_line_num) {
		
		Object tmp = args.get("group_num");
		String group_num = (String) tmp;
		
		tmp = args.get("arg");
		String[] fullname_arr = null;
		if (tmp == null) {
			fullname_arr = new String[0];
		}
		else if (tmp instanceof String[]) {
			fullname_arr = (String[]) tmp;
		}
		else {
			List tmp_list = (List) tmp;
			fullname_arr = Arrays.copyOf(tmp_list.toArray(), tmp_list.size(), String[].class);
		}
		
		tmp = args.get("max");
		Integer max = (Integer) tmp;
		
		int cur_count = fullname_arr.length;
		if (max != null && cur_count > max) {
			cur_count = max;
		}
		
		StringBuffer html = new StringBuffer();
		
		html.append("<div __otp_uiw_more_displayer ");
		if (group_num != null) {
			html.append(" __otp_group_num=\"").append(group_num).append("\" ");
		}
		if (max != null) {
			html.append(" __otp_max_count=\"").append(max).append("\" ");
		}
		html.append(" __otp_cur_count=\"").append(cur_count).append("\">");
		for (int i = 0; i < cur_count; i++) {
			String fullname = fullname_arr[i];
			html.append("<div>");
				html.append("<input type=\"hidden\" value=\"").append(fullname).append("\" >");
				html.append("<img src=\"\" __otp_fullname=\"").append(fullname).append("\" >");
				html.append("<span __otp_fullname=\"").append(fullname).append("\" title=\"删除\" style=\"display: none;\"></span>");
			html.append("</div>");
		}
		html.append("</div>");
		
		if (group_num != null) {
			html.append("<script type=\"text/javascript\">");
			html.append("$(function () {");
				html.append("var __file = $('.__oc_uiw.__oc_more[__otp_group_num=\"").append(group_num).append("\"]>input[type=\"file\"]');");
				html.append("if (__file === undefined) {");
					html.append("return;");
				html.append("}");
				html.append("var name = __file.attr('name');");
				html.append("__file.removeAttr('name').attr('__otp_ori_name', name);");
				html.append("$('[__otp_uiw_more_displayer][__otp_group_num=\"").append(group_num).append("\"]>div>:hidden').attr('name', name);");
			html.append("});");
			html.append("</script>");
		}
		
		pw.println(html.toString());
	}

	public static class DTOs {

		public static class Banner {

			private String fullname;

			private String href;
			
			private boolean enable_href;

			private Target target;
			
			public enum Target {
				
				/** _self<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月14日,上午9:00:59 */
				_1001("_self"),
				
				/** _blank<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月14日,上午9:01:03 */
				_1002("_blank"),
				
				;
				
				private String val;
				
				public String getVal() {
					return this.val;
				}
				
				private Target(String val) {
					this.val = val;
				}
				
			}

			public Banner(String fullname) {
				this(fullname, "", false, Target._1001);
			}

			public Banner(String fullname, String href) {
				this(fullname, href, true, Target._1001);
			}
			
			public Banner(String fullname, String href, Target target) {
				this(fullname, href, true, target);
			}

			public Banner(String fullname, String href, boolean enable_href, Target target) {
				this.fullname = fullname;
				this.href = href;
				this.enable_href = enable_href;
				this.target = target;
			}

		}

		public static class PagingBean {

			public PagingBean() {
			}

			public int cur = 1;

			public void setCur(int cur) {
				if (cur < 1) {
					cur = 1;
				}
				else if (cur > this.pages) {
					cur = this.pages;
				}
				this.cur = cur;
			}

			public int pages = 1;

			public int getPages() {
				if (this.total_count < 1) {
					this.pages = 1;
				}
				else if (this.total_count % this.disp_count == 0) {
					this.pages = (int) (this.total_count / this.disp_count);
				}
				else {
					this.pages = (int) ((this.total_count / this.disp_count) + 1);
				}
				return pages;
			}

			public int disp_count = CommonConst.DEF_PAGING_DISP_COUNT;

			public void setDisp_count(int disp_count) {
				if (disp_count < 1) {
					disp_count = CommonConst.DEF_PAGING_DISP_COUNT;
				}
				this.disp_count = disp_count;
			}

			public long total_count = 0L;

			public long getTotal_count() {
				if (this.total_count < 0) {
					this.total_count = 0L;
				}
				return this.total_count;
			}

			public List data;

			/**
			 * 将数据转换成{metadata_class}集合 <br>
			 * <b>作者 : </b>maodun <br>
			 * <b>创建时间 : </b>2015年9月24日,下午8:24:13
			 * @param adaptor_class 元数据适配器class
			 */
			public <T extends BaseModel, U extends MetadataAdaptor<T>> void dataTransitionTo(Class<U> adaptor_class) {
				this.data = MetadataHelper.pkgs(adaptor_class, this.data);
			}

			/**
			 * 数据完整SQL (可执行的,替换过占位符"?"后的)<br>
			 * <b>作者 : </b>maodun<br>
			 * <b>创建时间 : </b>2015年10月13日,下午2:21:04
			 */
			public String data_full_sql;

		}

	}
	
	private static final <T> T[] arr(T... vals) {
		return vals;
	}

}
