package models.baseframe;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 附件
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月17日,下午12:02:31
 */
@Entity(name="t_atcmt")
public class T_atcmt extends BaseModel {

    /** 全名<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:03:05 */
    public String fullname;
    
    /** 扩展名<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:03:11 */
    public String extname;
    
    /** 大小 (单位:Byte)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:03:17 */
    public Long size;
    
    /** 原始全名<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年8月17日,下午12:49:06 */
    public String ori_fullname;
    
    /** 是否有效 (1:true:是, 0:false:否)<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月12日,下午7:58:15 */
    public Boolean is_valid;
    
    /** 插入时间<br><b>作者 : </b>maodun<br><b>创建时间 : </b>2015年11月12日,下午5:59:54 */
    public Date insert_time;
    
}
