package baseframe.enums;

import java.math.BigDecimal;

/**
 * 文件大小单位枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月19日,下午4:10:21
 */
public enum FileSizeUnitEnum {
    
    /** 字节<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月19日,下午4:10:40 */
    Byte(1),
    
    /** 千字节<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月19日,下午4:11:28 */
    KB(1024),
    
    /** 兆字节<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月19日,下午4:11:33 */
    MB(1024 * 1024),
    
    /** 十亿字节<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月19日,下午4:11:57 */
    GB(1024 * 1024 * 1024),
    
    ;
    
    private BigDecimal val;
    
    private FileSizeUnitEnum(long unit) {
        this.val = new BigDecimal(unit);
    }
    
    /**
     * 获取值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月19日,下午4:12:42
     * @return
     */
    public BigDecimal getVal() {
        return this.val;
    }

}
