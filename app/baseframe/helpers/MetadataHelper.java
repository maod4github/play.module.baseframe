package baseframe.helpers;

import java.util.ArrayList;
import java.util.List;

import models.baseframe.BaseModel;
import models.baseframe.MetadataAdaptor;

/**
 * 元数据助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年9月24日,下午8:15:33
 */
public class MetadataHelper {
	
	private MetadataHelper() {}
	
	public static <T extends BaseModel, U extends MetadataAdaptor<T>> U pkg(Class<U> metadata_class, T model) {
		U u = null;
		try {
			u = metadata_class.newInstance();
		}
		catch (Exception e) {
System.out.println("实现了元数据适配器接口的类：<" + metadata_class.getName() + ">，请提供public的无参构造函数");
			e.printStackTrace();
		}
		u.setMetadata(model);
		return u;
	}
	
	public static <T extends BaseModel, U extends MetadataAdaptor<T>> List<U> pkgs(Class<U> metadata_class, List<T> models) {
		List<U> data = new ArrayList<U>();
		for (T model : models) {
			U u = MetadataHelper.pkg(metadata_class, model);
			if (u != null) {
				data.add(u);
			}
		}
		return data;
	}
	
}
