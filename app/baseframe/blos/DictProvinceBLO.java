package baseframe.blos;

import java.util.List;

import models.baseframe.T_dict_province;
import baseframe.daos.DictProvinceDAO;
import baseframe.factorys.InstanceFactory;
import baseframe.helpers.StringHelper;

/**
 * 省份地址字典BLO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:52:43
 */
public final class DictProvinceBLO extends BaseBLO<T_dict_province, DictProvinceDAO> {

	private DictProvinceBLO() {
	}

	public static final DictProvinceBLO getInstance() {
		return InstanceFactory.getBLO(DictProvinceBLO.class);
	}

	public final List<T_dict_province> queryAll(boolean is_valid) {
		return super.dao.queryAll(is_valid);
	}

	public final T_dict_province query(String code, boolean is_valid) {
		if (!StringHelper.isValidForTrim(code)) {
			return null;
		}
		code = code.trim();
		if (code.length() != 6) {
			return null;
		}
		return super.dao.query(code, is_valid);
	}

	public final T_dict_province query(String code) {
		return this.query(code, true);
	}

}
