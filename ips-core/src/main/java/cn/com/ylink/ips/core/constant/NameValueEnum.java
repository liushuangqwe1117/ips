package cn.com.ylink.ips.core.constant;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-7
 * @description：常量枚举类，包括值和对应信息
 */

public interface NameValueEnum {
	
	/**
	 * @description 得到展示信息
	 * @return  String
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	public String getDisplayName();

	/**
	 * @description 得到值
	 * @return  String
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	public String getParam();
}
