package models.baseframe;

import javax.persistence.Entity;

/**
 * 区县地址字典
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月17日,下午12:03:59
 */
@Entity(name="t_dict_county")
public class T_dict_county extends BaseModel {

    /** 代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:04:31 */
    public String code;
    
    /** 名称<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:04:35 */
    public String name;
    
    /** 所属城市代码<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:04:41 */
    public String city_code;
    
    /** 是否有效 (1=true=有效, 0=false=无效)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:49:06 */
    public Boolean is_valid;
    
}
