package baseframe.consts;

import play.Play;

/**
 * 公共常量类 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月3日,上午10:37:42
 */
public final class CommonConst {

    /** 默认分页展示个数<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月3日,上午10:37:24 */
    public static final int DEF_PAGING_DISP_COUNT = 10;

    /** 应用基础路径 (硬盘上的路径)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月21日,上午10:35:00 */
    public static final String APP_BASE_PATH = Play.applicationPath.getPath()/*这里替换是因为在LinuxOS下获取的路径会有/./的情况，从而导致路径无法找到，故替换为/则正常。*/.replaceAll("\\/\\.\\/", "/");

    /** 应用基础URL<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月24日,上午11:39:31 */
    public static final String APP_BASE_URL = Play.configuration.getProperty("bf.app_base_url"); 

    /** 预加载图片src<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月9日,上午10:25:11 */
    public static final String PRELOAD_IMG_SRC = Play.configuration.getProperty("bf.preload_img_src");

    /** 数据库备份路径<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月9日,上午10:25:11 */
    public static final String DBBAK_PATH = CommonConst.APP_BASE_PATH + Play.configuration.getProperty("bf.dbbak_path");

    /** 短信服务开关<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月28日,下午6:19:35 */
    public static final boolean SMS_TOGGLE = "on".equals(Play.configuration.getProperty("bf.sms.toggle")) ? true : false;

    /** 电邮服务开关<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年12月28日,下午6:20:51 */
    public static final boolean EMS_TOGGLE = "on".equals(Play.configuration.getProperty("bf.ems.toggle")) ? true : false;

}
