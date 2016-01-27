package baseframe.helpers;

import java.io.IOException;

/**
 * 操作系统助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月14日,下午8:37:54
 */
public class OSHelper {
	
    /** 操作系统名称<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年10月14日,下午8:43:51 */
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

	private OSHelper() {}
	
	/**
	 * 是否是Windows系统
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月14日,下午8:43:41
	 * @return
	 */
	private static Boolean isWindows() {
        return OS_NAME.indexOf("windows") > -1;
    }
	
    /**
     * 执行命令
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年10月14日,下午8:42:42
     * @param cmd
     * @return true:成功, false:失败
     */
    public static Boolean exec(String cmd) {
        try {
            Process p;
            if (isWindows()) {
                p = Runtime.getRuntime().exec(new String[] {"cmd", "/c", cmd});
            }
            else {
                p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", cmd});
            }
            p.waitFor();
        }
        catch (InterruptedException e) {
System.out.println("执行数据库备份/还原中断异常:" + e.getMessage());
            return false;
        }
        catch (IOException e) {
System.out.println("执行数据库备份/还原连接异常:" + e.getMessage());
            return false;
        }
        return true;
    }
	
}
