/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-17
 */

package cn.com.ylink.ips.core.util.support;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-17
 * @description：json属性过滤
 */

public class JsonFilter {
	
	public static JsonConfig getFilter(final String[] s){
		
        JsonConfig config = new JsonConfig();
        config.setJsonPropertyFilter(new PropertyFilter(){ 
            public boolean apply(Object source, String name, Object value) { 
            	if(juge(s,name)) { 
            		return true; 
	            } else {
	            	return false; 
	            } 
	        } 
            
            public boolean juge(String[] arr,String name){ 
            	boolean flag = false; 
            	for(String str : arr){
                    if(name.equals(str)){ 
                    	flag=true;
                    	break;
                    } 
                }
                return flag; 
            } 
        });
        return config; 
    }
}
