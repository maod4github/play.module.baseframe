package baseframe.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import baseframe.enums.DatetimePatternEnum;

/**
 * 日期时间助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月13日,上午10:19:47
 */
public class DatetimeHelper extends DateUtils {
    
    private DatetimeHelper() {}
    
    /**
     * 当前
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午10:40:07
     * @return
     */
    public static Date cur() {
        return new Date();
    }
    
    /**
     * 格式化
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午10:40:56
     * @param datetime 日期时间,默认当前
     * @param pattern 模式
     * @return 异常返回""
     */
    public static String format(Date datetime, DatetimePatternEnum pattern) {
        if (datetime == null || pattern == null) {
            return "";
        }
        return new SimpleDateFormat(pattern.getVal()).format(datetime);
    }
    
    /**
     * 计算相差天数
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月9日,上午11:30:13
     * @param a 
     * @param b 
     * @return a - b, 异常返回null
     */
    public static Integer calcDiffDays(Date a, Date b) {
    	if (a == null || b == null) {
    		return null;
    	}
    	long time = a.getTime() - b.getTime();
    	return Long.valueOf(time / 1000 / 60 / 60 / 24).intValue();
    }
    
    /**
     * 计算相差小时数
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月9日,上午11:30:13
     * @param a 
     * @param b 
     * @return a - b, 异常返回null
     */
    public static Long calcDiffHours(Date a, Date b) {
    	if (a == null || b == null) {
    		return null;
    	}
    	long time = a.getTime() - b.getTime();
    	return Long.valueOf(time / 1000 / 60 / 60);
    }
    
    /**
     * 计算相差分钟数
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月9日,上午11:30:13
     * @param a 
     * @param b 
     * @return a - b, 异常返回null
     */
    public static Long calcDiffMins(Date a, Date b) {
    	if (a == null || b == null) {
    		return null;
    	}
    	long time = a.getTime() - b.getTime();
    	return Long.valueOf(time / 1000 / 60);
    }
    
    /**
     * 计算相差秒数
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月9日,上午11:30:13
     * @param a 
     * @param b 
     * @return a - b, 异常返回null
     */
    public static Long calcDiffSecs(Date a, Date b) {
    	if (a == null || b == null) {
    		return null;
    	}
    	long time = a.getTime() - b.getTime();
    	return Long.valueOf(time / 1000);
    }

    /**
     * 转换成@datetime当天的开始
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:18:56
     * @param datetime
     * @return the start of day or null
     */
    public static Date toStartOfDay(Date datetime) {
    	if (datetime == null) {
    		return null;
    	}
    	String datetime_str = DatetimeHelper.format(datetime, DatetimePatternEnum.date_1002) + " 00:00:00";
    	return DatetimeHelper.parse(datetime_str, DatetimePatternEnum.datetime_1002);
    }

    /**
     * 转换成@datetime当天的结束
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:19:45
     * @param datetime
     * @return the end of day or null
     */
    public static Date toEndOfDay(Date datetime) {
    	if (datetime == null) {
    		return null;
    	}
    	String datetime_str = DatetimeHelper.format(datetime, DatetimePatternEnum.date_1002) + " 23:59:59";
    	return DatetimeHelper.parse(datetime_str, DatetimePatternEnum.datetime_1002);
    }
    
    /**
     * 转换成@datetime当周的开始
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:18:56
     * @param datetime
     * @return the start of week or null
     */
    public static Date toStartOfWeek(Date datetime) {
    	if (datetime == null) {
    		return null;
    	}
    	Calendar c = Calendar.getInstance();
    	c.setTime(datetime);
    	int a = c.get(Calendar.DAY_OF_WEEK) - 1;
    	a = a == 0 ? 7 : a;
    	Date d = addDays(datetime, - (a - 1));
    	return toStartOfDay(d);
    }
    
    /**
     * 转换成@datetime当周的结束
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:19:45
     * @param datetime
     * @return the end of week or null
     */
    public static Date toEndOfWeek(Date datetime) {
    	if (datetime == null) {
    		return null;
    	}
    	Calendar c = Calendar.getInstance();
    	c.setTime(datetime);
    	int a = c.get(Calendar.DAY_OF_WEEK) - 1;
    	a = a == 0 ? 7 : a;
    	Date d = addDays(datetime, 7 - a);
    	return toEndOfDay(d);
    }
    
    /**
     * 转换成@datetime当月的开始
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:18:56
     * @param datetime
     * @return the start of month or null
     */
    public static Date toStartOfMonth(Date datetime) {
    	if (datetime == null) {
    		return null;
    	}
    	String date_str = DatetimeHelper.format(datetime, DatetimePatternEnum.date_1002);
    	if (!StringHelper.isValidForTrim(date_str)) {
    		return null;
    	}
    	date_str = date_str.substring(0, date_str.length() - 2);
    	String datetime_str = date_str + "01 00:00:00";
    	return DatetimeHelper.parse(datetime_str, DatetimePatternEnum.datetime_1002);
    }
    
    /**
     * 转换成@datetime当月的结束
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:19:45
     * @param datetime
     * @return the end of month or null
     */
    public static Date toEndOfMonth(Date datetime) {
    	if (datetime == null) {
    		return null;
    	}
    	Date d = DateUtils.addMonths(datetime, 1);
    	d = DatetimeHelper.toStartOfMonth(d);
    	return DateUtils.addSeconds(d, -1);
    }
    
    /**
     * 解析
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月23日,下午5:20:02
     * @param datetime_str 日期时间字符串
     * @param pattern 日期时间模式枚举
     * @return 根据@datetime_str与@pattern解析后的Date对象
     */
    public static Date parse(String datetime_str, DatetimePatternEnum pattern) {
    	if (!StringHelper.isValidForTrim(datetime_str) || pattern == null) {
    		return null;
    	}
    	try {
			return new SimpleDateFormat(pattern.getVal()).parse(datetime_str);
		} catch (ParseException e) {
			return null;
		}
    }

}
