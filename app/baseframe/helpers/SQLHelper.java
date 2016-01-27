package baseframe.helpers;

import javax.persistence.Query;

/**
 * SQL助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月4日,下午3:24:26
 */
public class SQLHelper {
    
    private SQLHelper() {}
    
    /**
     * 设置参数值集
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月4日,下午3:23:40
     * @param query
     * @param param_vals
     */
    public static void set(Query query, Object ... param_vals) {
        for (int i = 0; i < param_vals.length; i++) {
            Object param_val = param_vals[i];
            query.setParameter(i + 1, param_val);
        }
    }
    
    /**
     * 设置参数值集
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年10月13日,下午2:17:22
     * @param sql
     * @param param_vals
     * @return 处理过占位符"?"后的可执行SQL
     */
    public static String set(String sql, Object ... param_vals) {
    	sql = sql.replaceAll("\\?", "'?'");
    	Object obj = null;
    	for (int i = 0; i < param_vals.length; i++) {
    		obj = param_vals[i];
    		if (obj instanceof Boolean) {
    			obj = Boolean.TRUE.equals(obj) ? 1 : 0;
    		}
    		sql = sql.replaceFirst("\\?", String.valueOf(obj));
        }
    	return sql;
    }

}
