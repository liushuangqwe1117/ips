/**
 * 版权所有(C) 2014 深圳市雁联计算系统有限公司
 * 创建:Administrator 2014-12-17
 */

package cn.com.ylink.ips.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/** 
 * @author Administrator
 * @date 2014-12-17
 * @description：TODO
 */

public class ObjectUtil {
	private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * 
	 * @param
	 * @return 获取对象所有属性名称及值
	 * @throws Exception
	 */
	public static String getPropertyString(Object entityName) throws Exception {
		Class c = entityName.getClass();
		String className = entityName.getClass().getName();
		Field field[] = c.getDeclaredFields();
//		Field field1[] = c.getFields();
		StringBuffer sb = new StringBuffer();
		sb.append("\n" +className+ " 【begin】 \n");
		c.getDeclaredConstructors();
		for (Field f : field) {
			f.setAccessible( true );
			sb.append(f.getName());
			sb.append(" : ");
			sb.append(f.get(entityName));
			sb.append("\n");
		}
		sb.append(className+ " 【end】");
		return sb.toString();
	}

	public static void printPropertyString(String logPrefix, Object object) {
		try {
			if (object==null) {
				logger.info(logPrefix + "对象为NULL.");
				return;
			}
			String beanToString = ObjectUtil.getPropertyString(object);
			logger.info(logPrefix + object.getClass().getName() + "各属性为：" + beanToString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(logPrefix + "读取对象属性出错：", e);
		}
	}
	
	public static String returnPropertyString(String logPrefix, Object object) {
		try {
			if (object==null) {
//				logger.info(logPrefix + "对象为NULL.");
				return logPrefix + "对象为NULL.";
			}
			String beanToString = ObjectUtil.getPropertyString(object);
			return logPrefix + object.getClass().getName() + "各属性为：" + beanToString;
		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info(logPrefix + "读取对象属性出错：", e);
			return logPrefix + "读取对象属性出错.";
		}
	}

	public static Object getMethod(Object owner, String methodName, Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod(methodName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			return " 找不到 " + methodName + " 方法。";
		}
		return method.invoke(owner);
	}

	/**
	 * 得到某个对象的属性
	 * 
	 * @param owner
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static  Object getProperty(Object owner, String fieldName) throws Exception {
		Class ownerClass = owner.getClass();// 得到该对象的Class
		Field field = ownerClass.getField(fieldName);// 通过Class得到类声明的属性 //获得类的所有的公共（public）的字段，包括父类
		Object property = field.get(owner);// 通过对象得到该属性的实例，如果这个属性是非公有的，这里会报IllegalAccessException。
		return property;
	}

	/**
	 * 得到某个类的静态属性
	 * 
	 * @param className
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static  Object getStaticProperty(String className, String fieldName) throws Exception {
		Class ownerClass = Class.forName(className);// 首先得到这个类的Class。
		Field field = ownerClass.getField(fieldName);// 通过Class得到类声明的属性。
		Object property = field.get(ownerClass);// 因为该属性是静态的，所以直接从类的Class里取。
		return property;
	}

	/**
	 * 执行某对象的方法
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static  Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
		Class ownerClass = owner.getClass();// 首先得到这个对象的Class
		// 配置参数的Class数组，作为寻找Method的条件
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);// 通过Method名和参数的Class数组得到要执行的Method
		return method.invoke(owner, args);// 执行该Method，invoke方法的参数是执行这个方法的对象，和参数数组。返回值是Object，也既是该方法的返回值
	}

	/**
	 * 执行某个类的静态方法
	 * 
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static  Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
		Class ownerClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(null, args);//invoke的一个参数是null，因为这是静态方法，不需要借助实例运行
	}

	/**
	 * 新建实例
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	//这里说的方法是执行带参数的构造函数来新建实例的方法。如果不需要参数，可以直接使用newoneClass.newInstance()来实现。
	public static  Object newInstance(String className, Object[] args) throws Exception {
		Class newoneClass = Class.forName(className);//得到要构造的实例的Class
		//得到参数的Class数组
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = newoneClass.getConstructor(argsClass);//得到构造子
		return cons.newInstance(args); //新建实例
	}

	/**
	 * 判断是否为某个类的实例
	 * @param obj
	 * @param cls
	 * @return
	 */
	public static  boolean isInstance(Object obj, Class cls) {
		return cls.isInstance(obj);
	}

	/**
	 * 得到数组中的某个元素
	 * @param array
	 * @param index
	 * @return
	 */
	public static  Object getByArray(Object array, int index) {
		return Array.get(array, index);
	}

}

