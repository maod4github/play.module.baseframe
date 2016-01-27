package baseframe.daos;

import java.util.List;

import models.baseframe.T_dict_city;
import baseframe.factorys.InstanceFactory;

/**
 * 城市地址字典DAO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:49:08
 */
public final class DictCityDAO extends BaseDAO<T_dict_city> {

	private DictCityDAO() {
	}

	public static final DictCityDAO getInstance() {
		return InstanceFactory.getDAO(DictCityDAO.class);
	}

	public final List<T_dict_city> queryAll(String province_code, boolean is_valid) {
		return super.query(" WHERE t.province_code = ? AND t.is_valid = ? ", province_code, is_valid).fetch();
	}

	public final T_dict_city query(String code, boolean is_valid) {
		return super.query(" WHERE t.code = ? AND t.is_valid = ? ", code, is_valid).first();
	}

	public final List<T_dict_city> dimQueryAll(String name, boolean is_valid) {
		return super.query(" WHERE t.name like ? AND t.is_valid = ? ", "%" + name + "%", is_valid).fetch();
	}

}
