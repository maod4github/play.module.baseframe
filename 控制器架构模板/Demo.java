package controllers;

import baseframe.blos.DictCountyBLO;
import baseframe.daos.BaseDAO.PagingBean;
import baseframe.factorys.InstanceFactory;

/**
 * 范例控制器
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月5日,上午11:32:42
 */
public class Demo extends Base {
    
    /**
     * 首页
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月29日,下午3:03:08
     * @param cur_page_num 当前页码
     */
    public static void home(int cur_page_num) {
    	PagingBean paging_bean = InstanceFactory.getBLO(DictCountyBLO.class).pagingDemo(cur_page_num);
        renderTemplate("/app/views/demo/home.html", paging_bean); return ;
    }
    
    /**
     * 表单ajax提交示例
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月5日,上午11:55:12
     */
    public static void ajaxSubmit(String name, Integer sex, String a, String b, String[] c) throws Exception {
        System.out.println("name:" + name);
        System.out.println("sex:" + sex);
        System.out.println("a:" + a);
        System.out.println("b:" + b);
        if (c != null) {
	        for (int i = 0; i < c.length; i++) {
	            System.out.println("c[" + i + "]:" + c[i]);
	        }
        }
        ResInfo res_info = new ResInfo(1, "成功了");
//        if (1 == 1) throw new Exception();
        renderJSON(res_info);
    }

}
