package baseframe.helpers;

import java.io.File;
import java.util.UUID;

import jregex.Matcher;
import jregex.Pattern;
import play.Play;
import baseframe.consts.CommonConst;
import baseframe.enums.DatetimePatternEnum;

/**
 * 数据库助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年10月14日,下午8:53:58
 */
public class DBHelper {
	
	private DBHelper() {}
	
	private static String db_username;
	private static String db_pwd;
	private static String db_host;
	private static String db_port = "3306";
	private static String db_name;
	
    // S 解析当前数据源配置 [
    static {
        String ds_name = Play.configuration.getProperty("db");//DBPlugin.getDatasourceName();
        Matcher m = new Pattern("^mysql:(//)?(({username}[a-zA-Z0-9_]+)(:({pwd}[^@]+))?@)?(({host}[^/]+)/)?({dbname}[^\\s]+)$").matcher(ds_name);
        if (m.matches()) {
        	db_username = m.group("username");
        	db_pwd = m.group("pwd");
            db_host = m.group("host");
            if (db_host == null) {
            	db_host = "127.0.0.1";
            }
            else if (db_host.contains(":")) {
                String[] split = db_host.split("\\:");
                db_host = split[0];
                db_port = split[1];
            }
            db_name = m.group("dbname");
        }
    }
    // E ]
    
	/**
	 * 备份数据
	 * <ul>
	 * 	<li>备份的文件已通过AES加密</li>
	 * 	<li>取应用当前数据源配置进行备份</li>
	 * </ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,上午11:02:06
	 * @return 备份的SQL文件路径,异常返回null
	 */
	public static String bakData() {
		File store = new File(CommonConst.DBBAK_PATH);
        if (!store.exists()) {
            store.mkdirs();
        }
        String cur_datetime = DatetimeHelper.format(DatetimeHelper.cur(), DatetimePatternEnum.datetime_1001);
		String file_path = CommonConst.DBBAK_PATH + cur_datetime + "-" + UUID.randomUUID() + ".sql";
		Boolean is_ok = dumpDataToSQLFileWithMySQL(db_username, db_pwd, db_host, db_port, db_name, file_path);
		is_ok = EncryptHelper.encryptFile(file_path);
		return is_ok ? file_path : null;
	}
	
	/**
	 * 还原数据
	 * <ul>
	 * 	<li>与baseframe.helpers.DBHelper.bakData()方法匹配</li>
	 * 	<li>仅能执行通过baseframe.helpers.DBHelper.bakData()方法备份的SQL文件</li>
	 * 	<li>取应用当前数据源配置进行还原</li>
	 * </ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,下午4:41:14
	 * @param file_path SQL文件路径
	 * @return true:成功, false:失败
	 */
	public static Boolean restoreData(String file_path) {
		if (file_path == null || !file_path.startsWith(CommonConst.DBBAK_PATH)) {
			return false;
		}
		Boolean is_ok = EncryptHelper.decryptFile(file_path);
		is_ok = execSQLFile(file_path);
		is_ok = EncryptHelper.encryptFile(file_path);
		return is_ok;
	}
	
	/**
	 * 执行SQL文件
	 * <ul>
	 * 	<li>取应用当前数据源配置执行SQL</li>
	 * </ul>
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月15日,下午7:09:24
	 * @param file_path 文件路径
	 * @return true:成功, false:失败
	 */
	public static Boolean execSQLFile(String file_path) {
		return execSQLFile(db_username, db_pwd, db_host, db_port, db_name, file_path);
	}
	
    /**
     * 执行sql文件 (该方法暂时定义为private不公开)
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年10月15日,下午4:38:11
	 * @param username 数据库用户名
	 * @param pwd 数据库密码
	 * @param host 数据库主机ip
	 * @param port 数据库端口
	 * @param database 数据库名
     * @param file_path 执行文件路径
     * @return true:成功, false:失败
     */
    private static Boolean execSQLFile(String username, String pwd, String host, String port, String database, String file_path) {
        StringBuffer sb = new StringBuffer();
        sb.append("mysql  -u").append(username).append(" -p").append(pwd).append(" -h").append(host).append(" -P").append(port);
        sb.append(" -D").append(database).append(" --default-character-set=utf8 ").append(" < ").append(file_path);
        return OSHelper.exec(sb.toString());
    }

    /**
	 * 导出数据库数据至SQL文件 (该方法暂时定义为private不公开)
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月14日,下午8:48:40
	 * @param username 数据库用户名
	 * @param pwd 数据库密码
	 * @param host 数据库主机ip
	 * @param port 数据库端口
	 * @param database 数据库名
	 * @param file_path 导出文件路径
	 * @return true:成功, false:失败
	 */
	private static Boolean dumpDataToSQLFileWithMySQL(String username, String pwd, String host, String port, String database, String file_path) {
		StringBuffer sb = new StringBuffer();
		sb.append("mysqldump -u").append(username);
		sb.append(" -p").append(pwd);
		sb.append(" -h").append(host);
		sb.append(" -P").append(port);
		sb.append(" --set-charset --single-transaction --default-character-set=utf8 --disable-keys -c --no-autocommit --triggers -R ");
		sb.append(database).append(" > ").append(file_path);
		return OSHelper.exec(sb.toString());
	}
	
}
