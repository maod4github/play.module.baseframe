package models.baseframe;

import javax.persistence.Entity;

/**
 * 城市地址字典
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月17日,下午12:02:31
 */
@Entity(name="t_dict_city")
public class T_dict_city extends BaseModel {

    /** 代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:03:05 */
    public String code;
    
    /** 名称<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:03:11 */
    public String name;
    
    /** 所属省份代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:03:17 */
    public String province_code;
    
    /** 是否有效 (1=true=有效, 0=false=无效)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:49:06 */
    public Boolean is_valid;
    
}
