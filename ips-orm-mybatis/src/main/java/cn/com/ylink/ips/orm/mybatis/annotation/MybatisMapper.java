package cn.com.ylink.ips.orm.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Repository;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-26
 * @description：mapper自定义注解
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface MybatisMapper {
	public String value();
}
