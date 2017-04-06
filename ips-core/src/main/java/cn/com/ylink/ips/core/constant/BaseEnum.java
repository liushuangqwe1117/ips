/**===========================================
 *        Copyright (C) 2013 YLINK
 *           All rights reserved
 *
 *  @项目名： wyzf-common
 *  @文件名： BaseEnum.java
 *  @版本信息： V1.0.0 
 *  @作者： hinode
 *  @日期： 2013-8-19-下午04:30:05
 * 
 ============================================*/

package cn.com.ylink.ips.core.constant;

/**
 * @类名称： BaseEnum
 * @类描述：
 * @创建人： hinode
 * @创建时间： 2013-8-19 下午04:30:05
 * 
 * @修改人： hinode
 * @操作时间： 2013-8-19 下午04:30:05
 * @操作原因：
 * 
 */
public interface BaseEnum<E extends Enum<E>> {
	String getValue();

	String getDisplayName();

	E getEnum(String value);
}
