/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-15
 */

package cn.com.ylink.ips.core.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-15
 * @description：集合工具类
 */

public class CollectionUtil {
	
	/**
	 * @description 转换集合，将一个集合中对象属性转到另一个指定对象的集合中
	 * 				两个中要转的属性必须相同
	 * @param srcList 源集合
	 * @param desClass 目的集合中实体对象类
	 * @return 目的集合
	 * @author Samisen
	 * @date 2012-8-15
	 */
	public static <T> List<T> getConvertList(List<?> srcList, Class<?> desClass) {
		if (srcList != null && !srcList.isEmpty()) {
			try {
				List<T> desList = new ArrayList<T>();
				Class<?> desEntity = Class.forName(desClass.getName());
				for (Object object : srcList) {
					@SuppressWarnings("unchecked")
					T desObject = (T) desEntity.newInstance();
					BeanUtils.copyProperties(object, desObject);
					desList.add(desObject);
				}
				return desList;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
