package controllers.baseframe;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.baseframe.T_atcmt;
import play.cache.Cache;
import play.libs.Images;
import play.libs.Images.Captcha;
import baseframe.blos.AtcmtBLO;
import baseframe.enums.CacheKeyPrefixEnum;
import baseframe.enums.FileSizeUnitEnum;
import baseframe.enums.ImgExtNameEnum;
import baseframe.enums.NumPatternEnum;
import baseframe.helpers.AtcmtHelper;
import baseframe.helpers.FileHelper;
import baseframe.helpers.NumHelper;
import baseframe.helpers.StringHelper;

/**
 * 文件操作控制器 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月3日,下午6:17:31
 */
public final class FileOperation extends Base {

	/**
	 * 上传图片 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月10日,下午5:10:08
	 * @param img 图片文件
	 * @param maxsize 最大大小(单位KB)
	 */
	public static final void uploadImg(final File img, final Double maxsize) {
		final ResInfo ri = new ResInfo(1, "操作成功");
		if (img == null) {
			ri.set(-1, "请选择图片");
			renderJSON(ri);
			return;
		}
		final Double size = FileHelper.calcSize(img, FileSizeUnitEnum.KB);
		if (maxsize != null && maxsize.doubleValue() > 0 && size > maxsize) {
			ri.set(-1, "图片大小请控制在「" + NumHelper.format(maxsize, NumPatternEnum.num_1001) + "KB」以内");
			renderJSON(ri);
			return;
		}
		final String fullname = AtcmtHelper.uploadImg(img, ri);
		if (ri.code > 0) {
			ri.putDatum("fullname", fullname); // 上传后的文件全名
			ri.putDatum("size", size); // 文件大小
			ri.putDatum("ori_fullname", img.getName()); // 原始的文件全名
		}
		renderJSON(ri);
		return;
	}

	/**
	 * 上传图片集 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月10日,下午5:10:08
	 * @param img 图片文件集
	 * @param maxsize 最大大小(单位KB)
	 */
	public static final void uploadImgs(final File[] imgs, final Double maxsize) {
		final List<ResInfo> ris = new ArrayList<ResInfo>();
		if (imgs == null || imgs.length < 1) {
			ris.add(new ResInfo(-1, "请选择图片"));
			renderJSON(ris);
			return;
		}
		boolean has_failed = false;
		for (File img : imgs) {
			final Double size = FileHelper.calcSize(img, FileSizeUnitEnum.KB);
			if (maxsize != null && maxsize.doubleValue() > 0 && size > maxsize) {
				has_failed = true;
				continue;
			}
			final ResInfo ri = new ResInfo(1, "操作成功");
			final String fullname = AtcmtHelper.uploadImg(img, ri);
			if (ri.code < 1) {
				has_failed = true;
				continue;
			}
			ri.putDatum("fullname", fullname); // 上传后的文件全名
			ri.putDatum("size", size); // 文件大小
			ri.putDatum("ori_fullname", img.getName()); // 原始的文件全名
			ris.add(ri);
		}
		if (has_failed) {
			final StringBuffer sb = new StringBuffer();
			sb.append("成功:").append(ris.size()).append("张, 失败:").append(imgs.length - ris.size()).append("张<br>");
			sb.append("温馨提示:<br>");
			sb.append("#. 请选择 *.(").append(ImgExtNameEnum.joinVals(", ")).append(") 类型的图片<br>");
			if (maxsize != null && maxsize.doubleValue() > 0) {
				sb.append("#. 图片大小请控制在「").append(NumHelper.format(maxsize, NumPatternEnum.num_1001)).append("KB」以内");
			}
			ris.add(new ResInfo(-1, sb.toString()));
		}
		renderJSON(ris);
		return;
	}

	/**
	 * 上传文档 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月10日,下午5:10:08
	 * @param docu 文档文件
	 * @param maxsize 最大大小(单位KB)
	 * @param types 限定类型集(多个用逗号","分隔)
	 */
	public static final void uploadDocu(final File docu, final Double maxsize, final String types) {
		final ResInfo ri = new ResInfo(1, "操作成功");
		if (docu == null) {
			ri.set(-1, "请不要上传无意义的文件.");
			renderJSON(ri);
			return;
		}
		String extname = FileHelper.getExtname(docu);
		if (!FileHelper.isDocu(docu)) {
			extname = "".equals(extname) ? "无扩展名" : extname;
			final StringBuffer msg = new StringBuffer();
			msg.append("抱歉,暂不支持「").append(extname).append("」类型的文件上传.<br>");
//        	msg.append("暂仅支持「").append(DocuExtNameEnum.joinVals(",")).append("」类型的文件.<br>");
/* [
        	msg.append("暂仅支持「");
        	for (DocuExtNameEnum e : DocuExtNameEnum.values()) {
				msg.append("*.").append(e.getVal()).append(", ");
			}
        	msg.delete(msg.length() - 2, msg.length()).append("」类型的文件.<br>");
] */
			msg.append("若有上传「").append(extname).append("」类型文件的需求,请联系客服,谢谢!");
			ri.set(-1, msg.toString());
			renderJSON(ri);
			return;
		}
		if (StringHelper.isValid(types) && (!StringHelper.isValid(extname) || !types.contains(extname))) {
			ri.set(-1, "请选择「" + types + "」类型的文件.");
			renderJSON(ri);
			return;
		}
		final Double size = FileHelper.calcSize(docu, FileSizeUnitEnum.KB);
		if (maxsize != null && maxsize.doubleValue() > 0 && size > maxsize) {
			ri.set(-1, "文档大小请控制在「" + NumHelper.format(maxsize, NumPatternEnum.num_1001) + "KB」以内.");
			renderJSON(ri);
			return;
		}
		final String fullname = AtcmtHelper.uploadDocu(docu, ri);
		if (ri.code > 0) {
			ri.putDatum("fullname", fullname); // 上传后的文件全名
			ri.putDatum("size", size); // 文件大小
			ri.putDatum("ori_fullname", docu.getName()); // 原始的文件全名
		}
		renderJSON(ri);
		return;
	}

	/**
	 * 下载文件 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月12日,下午12:03:32
	 * @param fullname 文件全名
	 */
	public static final void downloadFile(final String fullname, final String downname) {
		final ResInfo ri = new ResInfo(1, "下载成功");
		final InputStream is = AtcmtHelper.getAtcmtInputStreamBy(fullname, ri);
		if (ri.code < 0) {
			renderHtml("<head><title>文件不存在</title></head><script type=\"text/javascript\">alert('文件不存在'); window.history.back();</script>");
			return;
		}
		renderBinary(is, StringHelper.isValid(downname) ? downname + "." + FileHelper.getExtname(fullname) : AtcmtHelper.getOriFullname(fullname));
		return;
	}

	/**
	 * 删除文件 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月12日,下午4:57:10
	 * @param fullname
	 */
	public static final void delFile(final String fullname) {
		final ResInfo ri = new ResInfo(1, "删除成功");
		// 跳出方法,暂时不允许删除附件 [
		if (true) {
			renderJSON(ri);
			return;
		}
		// ]
		@SuppressWarnings("unused")
		final File file = AtcmtHelper.getAtcmtBy(fullname, ri);
		if (ri.code < 0) {
			ri.set(-1, "文件不存在或早已删除,请刷新后重试");
		}
		else if (!file.delete()) {
			ri.set(-1, "文件不存在或早已删除,请刷新后重试");
		}
		renderJSON(ri);
		return;
	}

	/**
	 * 上传图片 (仅适用于编辑器) <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年9月9日,下午3:13:37
	 * @param imgFile 图片文件对象
	 */
	public static final void uploadImgForEditer(final File imgFile) {
		final ResInfo ri = new ResInfo(1, "操作成功");
		if (imgFile == null) {
			ri.set(-1, "请选择图片");
			renderJSON(ri);
			return;
		}
		final String fullname = AtcmtHelper.uploadImg(imgFile, ri);
		if (ri.code > 0) {
			ri.putDatum("error", 0); // 上传后的文件全名
			ri.putDatum("url", AtcmtHelper.PATH_STORE_IMGS + fullname); // 上传后的文件全名
		}
		renderJSON(ri.data);
		return;
	}

	/**
	 * 生成验证码图片 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年9月17日,下午5:16:36 <br>
	 * <b>补充说明 : </b>验证图片验证码方法 : controllers.baseframe.Base.verifyImgCaptcha(String)
	 */
	public static final void generateVcodeImg() {
		final Captcha vcode_img = Images.captcha(138, 50);
		final String vcode = vcode_img.setBackground("#DEF1F8", "#DEF1F8").getText("#0056A0", 4);
		Cache.set(CacheKeyPrefixEnum._1001.getVal() + session.getId(), vcode, "10min");
		renderBinary(vcode_img);
	}

	/**
	 * 查询附件 (仅对于Ajax请求) <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年11月12日,下午8:38:33
	 * @param by_fullname
	 */
	public static final void queryAtt(final String by_fullname) {
		final ResInfo ri = new ResInfo(1, "成功");
		if (!request.isAjax()) {
			ri.set(-1, "非法请求");
			renderJSON(ri);
			return;
		}
		final T_atcmt att = AtcmtBLO.getInstance().query(by_fullname);
		if (att == null) {
			ri.set(-1, "找不到该附件:" + by_fullname);
			renderJSON(ri);
			return;
		}
		ri.putDatum("att", att);
		renderJSON(ri);
		return;
	}

}
