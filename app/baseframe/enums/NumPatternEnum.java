package baseframe.enums;

/**
 * 数字模式枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月13日,上午10:23:46
 */
public enum NumPatternEnum {
    
    /** #0.00<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:36:18 */
    num_1001("#0.00"),
    
    /** #,##0.00<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月13日,上午10:37:03 */
    num_1002("#,##0.00"),
    
    ;
    
    private String pattern;
    
    private NumPatternEnum(String pattern) {
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
