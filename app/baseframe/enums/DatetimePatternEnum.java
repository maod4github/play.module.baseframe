package baseframe.enums;

/**
 * 日期时间模式枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月13日,上午10:23:46
 */
public enum DatetimePatternEnum {
    
    /** yyyyMMdd<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:36:18 */
    date_1001("yyyyMMdd"),
    
    /** yyyy-MM-dd<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:37:03 */
    date_1002("yyyy-MM-dd"),
    
    /** HHmmss<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:37:10 */
    time_1001("HHmmss"),
    
    /** HH:mm:ss<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:37:17 */
    time_1002("HH:mm:ss"),
    
    /** yyyyMMddHHmmss<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:37:23 */
    datetime_1001("yyyyMMddHHmmss"),
    
    /** yyyy-MM-dd HH:mm:ss<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:26:30 */
    datetime_1002("yyyy-MM-dd HH:mm:ss"),
    
    ;
    
    private String pattern;
    
    private DatetimePatternEnum(String pattern) {
        this.pattern = pattern;
    }
    
    /**
     * 获取值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月13日,上午10:34:00
     * @return
     */
    public String getVal() {
        return this.pattern;
    }

}
