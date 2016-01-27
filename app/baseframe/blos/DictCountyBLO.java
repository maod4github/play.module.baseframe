package baseframe.blos;

import java.util.List;

import models.baseframe.T_dict_county;
import baseframe.daos.DictCountyDAO;
import baseframe.factorys.InstanceFactory;
import baseframe.groovy.CustomTags.DTOs.PagingBean;
import baseframe.helpers.StringHelper;

/**
 * 区县地址字典BLO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:34:26
 */
public final class DictCountyBLO extends BaseBLO<T_dict_county, DictCountyDAO> {

	private DictCountyBLO() {
	}

	public static final DictCountyBLO getInstance() {
		return InstanceFactory.getBLO(DictCountyBLO.class);
	}

	/**
	 * 分页分页演示 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月29日,下午3:05:54
	 * @param cur_pageno 当前页码
	 * @return
	 */
	public final PagingBean pagingDemo(int cur_pageno) {
		return super.dao.pagingDemo(cur_pageno);
	}

	public final List<T_dict_county> queryAll(String city_code, boolean is_valid) {
		if (!StringHelper.isValidForTrim(city_code)) {
			return null;
		}
		city_code = city_code.trim();
		if (city_code.length() != 6) {
			return null;
		}
		return super.dao.queryAll(city_code, is_valid);
	}

	public final T_dict_county query(String code, boolean is_valid) {
		if (!StringHelper.isValidForTrim(code)) {
			return null;
		}
		code = code.trim();
		if (code.length() != 6) {
			return null;
		}
		return super.dao.query(code, is_valid);
	}

	public final T_dict_county query(String code) {
		return this.query(code, true);
	}

}
