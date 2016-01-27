package baseframe.daos;

import java.util.List;

import models.baseframe.T_dict_province;
import baseframe.factorys.InstanceFactory;

/**
 * 省份地址字典DAO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:52:43
 */
public final class DictProvinceDAO extends BaseDAO<T_dict_province> {

	private DictProvinceDAO() {
	}

	public static final DictProvinceDAO getInstance() {
		return InstanceFactory.getDAO(DictProvinceDAO.class);
	}

	public final List<T_dict_province> queryAll(boolean is_valid) {
		return super.query(" WHERE t.is_valid = ? ", is_valid).fetch();
	}

	public final T_dict_province query(String code, boolean is_valid) {
		return super.query(" WHERE t.code = ? AND t.is_valid = ? ", code, is_valid).first();
	}

}
