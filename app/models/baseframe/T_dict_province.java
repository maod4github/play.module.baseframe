package models.baseframe;

import javax.persistence.Entity;

/**
 * 省份地址字典
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月17日,上午11:58:39
 */
@Entity(name="t_dict_province")
public class T_dict_province extends BaseModel {

    /** 代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,上午11:59:11 */
    public String code;
    
    /** 名称<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,上午11:59:17 */
    public String name;
    
    /** 是否是直辖市 (1:true:是, 0:false:否)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年9月28日,下午8:42:58 */
    public Boolean is_municipality = false;
    
    /** 是否有效 (1=true=有效, 0=false=无效)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:49:06 */
    public Boolean is_valid;
    
}
