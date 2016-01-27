package baseframe.daos;

import models.baseframe.T_sys_base_param_conf;
import models.baseframe.T_sys_base_param_conf.CodeEnum;
import baseframe.factorys.InstanceFactory;

/**
 * 系统基本参数配置DAO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月26日,下午1:04:48
 */
public final class SysBaseParamConfDAO extends BaseDAO<T_sys_base_param_conf> {

	private SysBaseParamConfDAO() {
	}

	public static final SysBaseParamConfDAO getInstance() {
		return InstanceFactory.getDAO(SysBaseParamConfDAO.class);
	}

	public final T_sys_base_param_conf query(CodeEnum code) {
		return super.query(" WHERE t.code = ? ", code.getVal()).first();
	}

}