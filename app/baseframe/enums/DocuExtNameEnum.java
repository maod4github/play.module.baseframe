package baseframe.enums;

import baseframe.helpers.StringHelper;

/**
 * 文档扩展名枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月12日,上午11:34:42
 */
public enum DocuExtNameEnum {
    
    txt("txt"),
    
    pdf("pdf"),
    
    doc("doc"),
    
    docx("docx"),
    
    xls("xls"),
    
    xlsx("xlsx"),
    
    ppt("ppt"),
    
    pptx("pptx"),
    
    ;
    
    private String ext_name;
    
    private DocuExtNameEnum(String ext_name) {
        this.ext_name = ext_name;
    }
    
    /**
     * 获取值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月12日,上午11:29:58
     * @return
     */
    public String getVal() {
        return this.ext_name;
    }

    /**
     * 拼接所有值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月10日,上午9:42:15
     * @param separator 分隔符,无效默认","
     * @return
     */
    public static String joinVals(String separator) {
        separator = (StringHelper.isValid(separator) ? separator : ",");
        StringBuffer sb = new StringBuffer();
        for (DocuExtNameEnum e : DocuExtNameEnum.values()) {
			sb.append(separator).append(e.getVal());
		}
        return sb.substring(separator.length());
    }
    
    /**
     * 是否包含{ext_name}
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月10日,上午9:52:30
     * @param ext_name 扩展名
     * @return
     */
    public static boolean contains(String ext_name) {
        if (!StringHelper.isValid(ext_name)) {
            return false;
        }
        return joinVals(",").contains(ext_name);
    }
    
}
