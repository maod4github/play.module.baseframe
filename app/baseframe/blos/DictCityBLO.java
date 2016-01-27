package baseframe.blos;

import java.util.List;

import models.baseframe.T_dict_city;
import baseframe.daos.DictCityDAO;
import baseframe.factorys.InstanceFactory;
import baseframe.helpers.StringHelper;

/**
 * 城市地址字典BLO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:49:08
 */
public final class DictCityBLO extends BaseBLO<T_dict_city, DictCityDAO> {

	private DictCityBLO() {
	}

	public static final DictCityBLO getInstance() {
		return InstanceFactory.getBLO(DictCityBLO.class);
	}

	public final List<T_dict_city> queryAll(String province_code, boolean is_valid) {
		if (!StringHelper.isValidForTrim(province_code)) {
			return null;
		}
		province_code = province_code.trim();
		if (province_code.length() != 6) {
			return null;
		}
		return super.dao.queryAll(province_code, is_valid);
	}

	public final T_dict_city query(String code, boolean is_valid) {
		if (!StringHelper.isValidForTrim(code)) {
			return null;
		}
		code = code.trim();
		if (code.length() != 6) {
			return null;
		}
		return super.dao.query(code, is_valid);
	}

	public final T_dict_city query(String code) {
		return this.query(code, true);
	}

	public List<T_dict_city> dimQueryAll(String name, boolean is_valid) {
		if (!StringHelper.isValidForTrim(name)) {
			return null;
		}
		return super.dao.dimQueryAll(name, is_valid);
	}

}
