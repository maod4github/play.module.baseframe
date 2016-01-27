package baseframe.enums;

/**
 * 性别枚举
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月3日,下午6:18:09
 */
public enum SexEnum {
    
	/** 0, 未知<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月3日,上午11:35:52 */
	_wz(0, "未知"),
	
    /** 10, 男<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月3日,上午11:35:40 */
    _n(10, "男"),
    
    /** 11, 女<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月3日,上午11:35:52 */
    _nv(11, "女"),
    
    ;
    
    private Integer val;
    private String text;
    
    private SexEnum(Integer val, String text) {
        this.val = val;
        this.text = text;
    }
    
    public Integer getVal() {
    	return this.val;
    }
    
    public String getText() {
        return this.text;
    }
    
    public static String getText(Integer val) {
    	SexEnum e = SexEnum.enumOf(val);
    	return e == null ? null : e.getText();
    }
    
    public static SexEnum enumOf(Integer val) {
    	for (SexEnum e : SexEnum.values()) {
    		if (e.getVal().equals(val)) {
    			return e;
    		}
    	}
    	return null;
    }
    
}
