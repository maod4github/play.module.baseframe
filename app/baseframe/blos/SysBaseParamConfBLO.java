package baseframe.blos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.baseframe.T_sys_base_param_conf;
import models.baseframe.T_sys_base_param_conf.CodeEnum;
import baseframe.daos.SysBaseParamConfDAO;
import baseframe.factorys.InstanceFactory;

/**
 * 系统基本参数配置BLO <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月26日,下午1:36:00
 */
public final class SysBaseParamConfBLO extends BaseBLO<T_sys_base_param_conf, SysBaseParamConfDAO> {

	private static Map<String, T_sys_base_param_conf> cached_conf_kvs;

	private SysBaseParamConfBLO() {
	}

	public static final SysBaseParamConfBLO getInstance() {
		return InstanceFactory.getBLO(SysBaseParamConfBLO.class);
	}

	private static final void loadConfKVsInCache() {
		if (cached_conf_kvs == null) {
			cached_conf_kvs = new HashMap<String, T_sys_base_param_conf>();
		}
		List<T_sys_base_param_conf> confs = SysBaseParamConfBLO.getInstance().queryAll();
		if (confs != null) {
			for (T_sys_base_param_conf conf : confs) {
				cached_conf_kvs.put(conf.code, conf);
			}
		}
	}

	public static final void refreshCachedConfKVs() {
		cached_conf_kvs.clear();
	}

	private static final Map<String, T_sys_base_param_conf> getCachedConfKVs() {
		if (cached_conf_kvs == null || cached_conf_kvs.isEmpty()) {
			loadConfKVsInCache();
		}
		return cached_conf_kvs;
	}

	public static final T_sys_base_param_conf getCachedConf(CodeEnum code) {
		return getCachedConfKVs().get(code.getVal());
	}

	public static final String getCachedConfVal(CodeEnum code) {
		T_sys_base_param_conf conf = getCachedConf(code);
		return conf == null ? null : conf.val;
	}

	public final T_sys_base_param_conf query(CodeEnum code) {
		return super.dao.query(code);
	}

}