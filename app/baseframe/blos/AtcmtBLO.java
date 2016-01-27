package baseframe.blos;

import models.baseframe.T_atcmt;
import baseframe.daos.AtcmtDAO;
import baseframe.factorys.InstanceFactory;
import baseframe.helpers.StringHelper;

/**
 * 附件BLO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月17日,下午6:49:08
 */
public final class AtcmtBLO extends BaseBLO<T_atcmt, AtcmtDAO> {

	private AtcmtBLO() {
	}

	public static final AtcmtBLO getInstance() {
		return InstanceFactory.getBLO(AtcmtBLO.class);
	}

	public final T_atcmt query(String fullname) {
		if (!StringHelper.isValid(fullname)) {
			return null;
		}
		return super.dao.query(fullname);
	}

}
