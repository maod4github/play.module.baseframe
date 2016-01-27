package baseframe.daos;

import java.util.List;

import models.baseframe.T_dict_county;
import baseframe.factorys.InstanceFactory;
import baseframe.groovy.CustomTags.DTOs.PagingBean;

/**
 * 区县地址字典DAO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:34:26
 */
public final class DictCountyDAO extends BaseDAO<T_dict_county> {

	private DictCountyDAO() {
	}

	public static final DictCountyDAO getInstance() {
		return InstanceFactory.getDAO(DictCountyDAO.class);
	}

	/**
	 * 分页演示 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月29日,下午3:01:15
	 * @param cur_pageno 当前页码
	 * @return
	 */
	public final PagingBean pagingDemo(int cur_pageno) {
		return this.newPagingBean(cur_pageno, "");
	}

	public final T_dict_county query(String code, boolean is_valid) {
		return super.query(" WHERE t.code = ? AND t.is_valid = ? ", code, is_valid).first();
	}

	public final List<T_dict_county> queryAll(String city_code, boolean is_valid) {
		return super.query(" WHERE t.city_code = ? AND t.is_valid = ? ", city_code, is_valid).fetch();
	}

}
