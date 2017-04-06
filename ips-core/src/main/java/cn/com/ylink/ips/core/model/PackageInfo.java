package cn.com.ylink.ips.core.model;

import java.io.Serializable;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：包中包含扫描注解类信息
 */
public class PackageInfo implements Serializable {
	
	private static final long serialVersionUID = -2896407479428690331L;
	
	private String[] basePackages;
	private String[] annotationTypes;
	
	public String[] getBasePackages() {
		return basePackages;
	}
	public void setBasePackages(String[] basePackages) {
		this.basePackages = basePackages;
	}
	public String[] getAnnotationTypes() {
		return annotationTypes;
	}
	
	public void setAnnotationTypes(String[] annotationTypes) {
		this.annotationTypes = annotationTypes;
	}
	
	
}
