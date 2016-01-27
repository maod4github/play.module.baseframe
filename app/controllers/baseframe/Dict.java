package controllers.baseframe;

import java.util.List;

import models.baseframe.T_dict_city;
import models.baseframe.T_dict_county;
import models.baseframe.T_dict_province;
import baseframe.blos.DictCityBLO;
import baseframe.blos.DictCountyBLO;
import baseframe.blos.DictProvinceBLO;

/**
 * 字典控制器 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月3日,下午6:17:31
 */
public class Dict extends Base {

	/**
	 * 获取省份集(仅适用于ajax请求) <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月5日,下午12:39:20
	 */
	public static void getProvinces() {
		if (!request.isAjax()) {
			return;
		}
		final List<T_dict_province> provinces = DictProvinceBLO.getInstance().queryAll(true);
		renderJSON(provinces);
		return;
	}

	/**
	 * 获取城市集(根据省份id)(仅适用于ajax请求) <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月5日,下午12:39:38
	 * @param province_code 所属省份代码
	 */
	public static void getCities(final String province_code) {
		if (!request.isAjax()) {
			return;
		}
		final List<T_dict_city> cities = DictCityBLO.getInstance().queryAll(province_code, true);
		renderJSON(cities);
		return;
	}

	/**
	 * 获取区县集(根据城市id)(仅适用于ajax请求) <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月5日,下午12:39:46
	 * @param city_code 所属城市代码
	 */
	public static void getCounties(final String city_code) {
		if (!request.isAjax()) {
			return;
		}
		final List<T_dict_county> counties = DictCountyBLO.getInstance().queryAll(city_code, true);
		renderJSON(counties);
		return;
	}

}
