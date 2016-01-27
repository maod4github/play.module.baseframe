package baseframe.enums;

import baseframe.helpers.StringHelper;

/**
 * 图片扩展名枚举类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月8日,下午12:16:28
 */
public enum ImgExtNameEnum {
    
    png("png"),
    
    jpg("jpg"),
    
    jpeg("jpeg"),
    
    ;
    
    private String ext_name;

    private ImgExtNameEnum(String ext_name) {
        this.ext_name = ext_name;
    }

    /**
     * 获取值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月8日,下午12:15:56
     * @return
     */
    public String getVal() {
        return this.ext_name;
    }

    /**
     * 拼接所有值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月10日,上午9:42:15
     * @param separator 分隔符,默认","
     * @return
     */
    public static String joinVals(String separator) {
        separator = (StringHelper.isValid(separator) ? separator : ",");
        StringBuffer sb = new StringBuffer();
        ImgExtNameEnum[] ext_names = ImgExtNameEnum.values();
        for (int i = 0; i < ext_names.length; i++) {
            sb.append(separator).append(ext_names[i].getVal());
        }
        return sb.substring(separator.length());
    }
    
    /**
     * 是否包含@param.ext_name
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月10日,上午9:52:30
     * @param ext_name 扩展名
     * @return
     */
    public static boolean contains(String ext_name) {
        if (!StringHelper.isValid(ext_name)) {
            return false;
        }
        return joinVals(null).contains(ext_name.toLowerCase());
    }

}
