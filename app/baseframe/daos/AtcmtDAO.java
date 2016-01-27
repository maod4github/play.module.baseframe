package baseframe.daos;

import models.baseframe.T_atcmt;
import baseframe.factorys.InstanceFactory;

/**
 * 附件DAO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:49:08
 */
public final class AtcmtDAO extends BaseDAO<T_atcmt> {

	private AtcmtDAO() {
	}

	public static final AtcmtDAO getInstance() {
		return InstanceFactory.getDAO(AtcmtDAO.class);
	}

	public final T_atcmt query(String fullname) {
		return super.query(" WHERE t.fullname = ? ", fullname).first();
	}

}
