package baseframe.helpers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import models.baseframe.T_atcmt;
import play.libs.Codec;
import play.libs.IO;
import baseframe.blos.AtcmtBLO;
import baseframe.consts.CommonConst;
import baseframe.enums.FileSizeUnitEnum;
import baseframe.enums.ImgExtNameEnum;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import controllers.baseframe.Base.ResInfo;

/**
 * 附件助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月10日,上午10:05:46
 */
public class AtcmtHelper {
	
	/** 图片仓库路径<br><b>作者 : </b>Morton<br><b>创建时间 : </b>2015年12月3日,下午10:34:28 */
	public static final String PATH_STORE_IMGS = "/public/atcmt/imgs/";
	
	/** 文档仓库路径<br><b>作者 : </b>Morton<br><b>创建时间 : </b>2015年12月3日,下午10:34:40 */
	public static final String PATH_SOTRE_DOCUS = "/public/atcmt/docus/";
	
	private static final File imgs_store = new File(CommonConst.APP_BASE_PATH + PATH_STORE_IMGS);
	
	private static final File docus_store = new File(CommonConst.APP_BASE_PATH + PATH_SOTRE_DOCUS);
	
	static {
		imgs_store.mkdirs();
		docus_store.mkdirs();
	}
    
    private AtcmtHelper() {}

    /**
     * 上传图片
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月10日,上午10:06:00
     * @param img 图片文件
     * @param res_info 响应信息
     * @return 附件全名
     */
    public static String uploadImg(File img, ResInfo res_info) {
    	String ori_fullname = img.getName();
        if (!FileHelper.isImg(ori_fullname)) {
            res_info.set(-1, "请选择 *.(" + ImgExtNameEnum.joinVals(", ") + ") 类型的图片文件");
            return null;
        }
        try {
        	// 保存原图 [
        	String simpname = Codec.UUID();//DatetimeHelper.format(DatetimeHelper.cur(), DatetimePatternEnum.datetime_1001) + "-" + Codec.UUID();
        	String extname = FileHelper.getExtname(ori_fullname);
	        String fullname = simpname + "." + extname;
	        File ori_file = new File(imgs_store, fullname);
	        IO.write(new FileInputStream(img), ori_file);
	        // ]
	        // 生成压缩图片 [
	        if (!ImgCompressor.compress(ori_file, imgs_store)) {
	        	res_info.set(-1, "图片压缩失败");
	        	return null;
	        }
	        // ]
            // 入库 [
            T_atcmt a = new T_atcmt();
            a.fullname = fullname;
            a.extname = extname;
            a.size = FileHelper.calcSize(img, FileSizeUnitEnum.Byte).longValue();
            a.ori_fullname = ori_fullname;
            a.is_valid = true;
            AtcmtBLO.getInstance().save(a);
            // ]
            return fullname;
        }
        catch (Exception e) {
            e.printStackTrace();
            res_info.set(-1, "图片上传异常");
            return null;
        }
    }
    
    /**
     * 上传文档
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月10日,上午10:06:00
     * @param docu 文档文件
     * @param res_info 响应信息
     * @return 附件全名
     */
    public static String uploadDocu(File docu, ResInfo res_info) {
//        String fullname = DatetimeHelper.format(DatetimeHelper.cur(), DatetimePatternEnum.datetime_1001) + "-" + Codec.UUID() + "." + FileHelper.getExtname(docu);
    	String fullname = Codec.UUID() + "." + FileHelper.getExtname(docu);
        File output_location = new File(docus_store, fullname);
        try {
            IO.write(new FileInputStream(docu), output_location);
            // 入库 [
            T_atcmt a = new T_atcmt();
            a.fullname = fullname;
            a.extname = FileHelper.getExtname(docu);
            a.size = FileHelper.calcSize(docu, FileSizeUnitEnum.Byte).longValue();
            a.ori_fullname = docu.getName();
            a.is_valid = true;
            AtcmtBLO.getInstance().save(a);
            // ]
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            res_info.set(-1, "文件没有找到");
        }
        return fullname;
    }
    
    /**
     * 获取附件
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月18日,下午2:43:39
     * @param fullname 附件全名
     * @param res_info 响应信息
     * @return 附件对象
     */
    public static File getAtcmtBy(String fullname, ResInfo res_info) {
        File store = null;
        if (FileHelper.isImg(fullname)) {
            store = imgs_store;
        }
        else if (FileHelper.isDocu(fullname)) {
            store = docus_store;
        }
        if (store == null) {
System.out.println("获取附件:找不到所属仓库,fullname:" + fullname);
            res_info.set(-1, "文件不存在");
            return null;
        }
        File file = new File(store, fullname);
        if (!file.exists()) {
System.out.println("获取附件:找不到文件,fullname:" + fullname);
            res_info.set(-1, "文件不存在");
            return null;
        }
        return file;
    }
    
    /**
     * 获取附件输入流
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月18日,下午12:02:39
     * @param fullname 附件全名
     * @param res_info 响应信息
     * @return 附件输入流
     */
    public static FileInputStream getAtcmtInputStreamBy(String fullname, ResInfo res_info) {
        File file = getAtcmtBy(fullname, res_info);
        if (res_info.code < 0) {
            res_info.set(-1, "文件不存在");
            return null;
        }
        try {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
System.out.println("获取附件输入流:找不到文件,fullname:" + fullname);
            res_info.set(-1, "文件不存在");
            return null;
        }
    }
    
    /**
     * 获取附件原始全名
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月18日,上午11:54:09
     * @param fullname 附件全名
     * @return The original fullname or null
     */
    public static String getOriFullname(String fullname) {
    	T_atcmt att = AtcmtBLO.getInstance().query(fullname);
    	return att == null ? null : att.ori_fullname;
    }
    
    /**
     * 获取附件大小
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月19日,下午4:20:17
     * @param fullname 附件全名
     * @param unit 单位 (默认Byte)
     * @return the size or null
     */
    public static Double getSize(String fullname, FileSizeUnitEnum unit) {
    	T_atcmt att = AtcmtBLO.getInstance().query(fullname);
    	if (att == null) {
    		return 0D;
    	}
    	if (unit == null || FileSizeUnitEnum.Byte.equals(unit)) {
    		return att.size == null ? 0D : att.size.doubleValue();
    	}
    	return BigDecimalHelper.div(new BigDecimal(att.size), unit.getVal(), 2).doubleValue();
    }

	/**
	 * 图片压缩机<br>
	 * <b>作者 : </b>maodun<br>
	 * <b>创建时间 : </b>2015年12月8日,上午10:26:02
	 */
	public static class ImgCompressor {

		private Image img;
		private int width;
		private int height;

		private ImgCompressor(File imgFile) throws Exception {
			this.img = ImageIO.read(imgFile);
			this.width = img.getWidth(null);
			this.height = img.getHeight(null);
		}

		private void generate(File out_file, double wh_ratio, float quality_ratio) throws Exception {
			resizeByPercent(wh_ratio, out_file, quality_ratio);
		}

		/**
		 * 图片压缩，使用默认的压缩比0.75
		 * @param percent
		 * @param file
		 * @throws Exception
		 */
		@SuppressWarnings("unused")
		private void resizeByPercent(double percent, File file) throws Exception {
			int w = (int) (width * percent);
			int h = (int) (height * percent);
			resize(w, h, file);
		}

		/**
		 * 图片压缩，并进行图片质量控制
		 * @param percent
		 * @param file
		 * @param quality
		 * @throws Exception
		 */
		private void resizeByPercent(double percent, File file, float quality) throws Exception {
			int w = (int) (width * percent);
			int h = (int) (height * percent);
			resize(w, h, file, quality);
		}

		/**
		 * 图片压缩处理，不进行质量控制，
		 * @param w
		 * @param h
		 * @param destFile
		 * @throws Exception
		 */
		private void resize(int w, int h, File destFile) throws Exception {
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img, 0, 0, w, h, null);
			FileOutputStream out = new FileOutputStream(destFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		}

		/**
		 * 图片压缩处理并控制压缩的图片质量，
		 * @param w
		 * @param h
		 * @param destFile
		 * @param quality 图片质量比(0~1),1代表无损压缩[甚至图片会变大]。超出质量使用默认的高质量压缩比(0.75)
		 * @throws Exception
		 */
		private void resize(int w, int h, File destFile, float quality) throws Exception {
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img, 0, 0, w, h, null);
			FileOutputStream out = new FileOutputStream(destFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			if (quality > 1 || quality <= 0) {
				quality = 0.75f;
			}
			JPEGEncodeParam encoderParam = encoder.getDefaultJPEGEncodeParam(image);
			encoderParam.setQuality(quality, false);
			encoder.setJPEGEncodeParam(encoderParam);
			encoder.encode(image);
			out.close();
		}

		@SuppressWarnings("unused")
		private static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {
			if (quality > 1) {
				throw new IllegalArgumentException("Quality has to be between 0 and 1");
			}
			ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
			Image i = ii.getImage();
			Image resizedImage = null;
			int iWidth = i.getWidth(null);
			int iHeight = i.getHeight(null);
			if (iWidth > iHeight) {
				resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
			}
			else {
				resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
			}
			// This code ensures that all the pixels in the image are loaded.
			Image temp = new ImageIcon(resizedImage).getImage();
			// Create the buffered image.
			BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// Copy image to buffered image.
			Graphics g = bufferedImage.createGraphics();
			// Clear background and paint the image.
			g.setColor(Color.white);
			g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
			g.drawImage(temp, 0, 0, null);
			g.dispose();
			// Soften.
			float softenFactor = 0.05f;
			float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
			Kernel kernel = new Kernel(3, 3, softenArray);
			ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
			bufferedImage = cOp.filter(bufferedImage, null);
			// Write the jpeg to a file.
			FileOutputStream out = new FileOutputStream(resizedFile);
			// Encodes image as a JPEG data stream
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
			param.setQuality(quality, true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bufferedImage);
		}
		
		/**
		 * 压缩<br>
		 * <b>作者 : </b>maodun<br>
		 * <b>创建时间 : </b>2015年12月8日,上午10:41:21
		 * @param img
		 * @param dest_folder
		 * @return
		 * @throws Exception
		 */
		private static boolean compress(File img, File dest_folder) {
			String fullname = img.getName();
			if (!FileHelper.isImg(fullname)) {
				return false;
			}
			String simpname = FileHelper.getSimpname(fullname);
			if (simpname.endsWith("-h") || simpname.endsWith("-mh") || simpname.endsWith("-m") || simpname.endsWith("-ml") || simpname.endsWith("-l") || simpname.endsWith("-b")) {
				return false;
			}
			String extname = FileHelper.getExtname(fullname);
			// 压缩处理,针对原图生成(高/中高/中/中低/低/缩略)六种压缩比例的图片 [
			File h_img = new File(dest_folder, simpname + "-h." + extname);
			File mh_img = new File(dest_folder, simpname + "-mh." + extname);
			File m_img = new File(dest_folder, simpname + "-m." + extname);
			File ml_img = new File(dest_folder, simpname + "-ml." + extname);
			File l_img = new File(dest_folder, simpname + "-l." + extname);
			File b_img = new File(dest_folder, simpname + "-b." + extname);
			try {
				ImgCompressor ic = new ImgCompressor(img);
				ic.generate(h_img, 1, 0.85f);
				ic.generate(mh_img, 0.75, 0.85f);
				ic.generate(m_img, 0.5, 0.85f);
				ic.generate(ml_img, 0.3, 0.85f);
				ic.generate(l_img, 0.2, 0.85f);
				ic.generate(b_img, 0.5, 0.01f);
			}
			// 压缩异常则生成原图
			catch (Exception e) {
				try {
					IO.write(new FileInputStream(img), h_img);
					IO.write(new FileInputStream(img), mh_img);
					IO.write(new FileInputStream(img), m_img);
					IO.write(new FileInputStream(img), ml_img);
					IO.write(new FileInputStream(img), l_img);
					IO.write(new FileInputStream(img), b_img);
				}
				catch (FileNotFoundException fne) {
					return false;
				}
			}
			// ]
			return true;
		}
		
		/**
		 * 压缩全部<br>
		 * <b>作者 : </b>maodun<br>
		 * <b>创建时间 : </b>2015年12月8日,上午10:41:30
		 * @param src_folder
		 * @throws Exception
		 */
		public static void compressAll(File src_folder) {
			File[] files = src_folder.listFiles();
System.out.println("img compress start.");
			long st = new Date().getTime();
			for (int i = 0; i < files.length; i ++) {
				compress(files[i], imgs_store);
			}
			long et = new Date().getTime();
System.out.println("over, spend time:" + (et - st) + "ms.");
		}

	}
    
}
