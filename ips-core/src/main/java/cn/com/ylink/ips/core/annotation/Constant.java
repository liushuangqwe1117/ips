package cn.com.ylink.ips.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：自定义注解常量类
 */

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Constant {
	public String namespace();
}
