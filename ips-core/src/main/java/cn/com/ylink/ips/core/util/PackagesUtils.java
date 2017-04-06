package cn.com.ylink.ips.core.util;

import java.util.Map;

import cn.com.ylink.ips.core.model.PackageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-26
 * @description：
 */

public final class PackagesUtils {
	
	private static final String COMMA = ",";
	
	public static String getAllBasePackages(ApplicationContext applicationContext){
		
		Map<String, PackageInfo> basePackagesMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
			      PackageInfo.class, true, false);
		
		StringBuilder allBasePackages = new StringBuilder();
		String[] basePackages = null;
		
		for(String key : basePackagesMap.keySet()){
			PackageInfo packageInfo = (PackageInfo)basePackagesMap.get(key);
			basePackages = packageInfo.getBasePackages();
			if ((basePackages != null) && (basePackages.length > 0)){
				for (String basePackage : basePackages){
					if (StringUtils.isNotBlank(basePackage)){
						allBasePackages.append(basePackage);
						allBasePackages.append(COMMA);
					}
				}
			}
		}
		
		String str = allBasePackages.toString();
	    if ((str != null) && (str.endsWith(","))) {
	    	str = str.substring(0, str.length() - 1);
	    }
	    
	    return str;
	}
}
