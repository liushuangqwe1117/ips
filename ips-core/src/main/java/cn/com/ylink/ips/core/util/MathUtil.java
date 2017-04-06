/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-8-26
 */
package cn.com.ylink.ips.core.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/** 
 * @author Administrator
 * @date 2013-8-26
 * @description：TODO
 */

public class MathUtil {
	
	private static final double ZERO = 0.0001;
	private static final int SCALE = 2;
	
	/**
	 * 判断金额是否相等, 两数之差小于ZERO 视为相等.
	 * 
     * <pre>
     * AmountUtils.equals(null, null)	= false
     * AmountUtils.equals(1, null)      = false
     * AmountUtils.equals(null, 1)      = false
     * AmountUtils.equals(2, 2)      	= true
     * AmountUtils.equals(2, 2.001)     = false
     * </pre>
     * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean equals(Double d1, Double d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		
		return Math.abs(d1 - d2) < ZERO;
	}
	
	/**
	 * 四舍五入.
	 * 
	 * @param value
	 * @return
	 */
	public static double format(double value) {
		return new BigDecimal(Double.toString(value)).setScale(SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 比较.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compare(double d1, double d2) {
		return new BigDecimal(Double.toString(d1)).setScale(SCALE, BigDecimal.ROUND_HALF_UP).compareTo(
				new BigDecimal(Double.toString(d2)).setScale(SCALE, BigDecimal.ROUND_HALF_UP));
	}
	
	/**
	 * 大于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean gt(double d1, double d2) {
		return compare(d1, d2) > 0;
	}
	
	/**
	 * 等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean et(double d1, double d2) {
		return compare(d1, d2) == 0;
	}
	
	/**
	 * 不等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean ne(double d1, double d2) {
		return compare(d1, d2) != 0;
	}
	
	/**
	 * 小于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean lt(double d1, double d2) {
		return compare(d1, d2) < 0;
	}
	
	/**
	 * 大于等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean ge(double d1, double d2) {
		return compare(d1, d2) >= 0;
	}
	
	/**
	 * 小于等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean le(double d1, double d2) {
		return compare(d1, d2) <= 0;
	}
	
	/**
	 * 加法运算, 结果四舍五入.
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double add(Double d1, Double d2) {
		return new Double(format(d1.doubleValue() + d2.doubleValue()));
	}
	
	/**
	 * 减法运算, 结果四舍五入.
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double subtract(Double d1, Double d2) {
		return new Double(format(d1.doubleValue() - d2.doubleValue()));
	}

	

		// 默认除法运算精度
		private static final int DEFAULT_DIV_SCALE = 10;

	

	
		public static double round(Double num, int point) {
			if (num == null) {
				return 0.00;
			}
			BigDecimal b1 = new BigDecimal(Double.toString(num));
			return b1.setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		// /**
		// *
		// * 提供精确的加法运算。
		// *
		// * @param v1
		// * 被加数
		// *
		// * @param v2
		// * 加数
		// *
		// * @return 两个参数的和
		// *
		// */
		//
		// public static double add(Double v1, Double v2) {
		// BigDecimal b1 = new BigDecimal(doubleValue(v1));
		// BigDecimal b2 = new BigDecimal(doubleValue(v2));
		// return b1.add(b2).doubleValue();
		// }

		public static double add(Double... ds) {
			if (ds == null || ds.length == 0) {
				return 0.0;
			}

			if (ds != null && ds.length == 1) {
				return doubleValue(ds[0]);
			}

			BigDecimal sum = new BigDecimal("0.0");
			for (Double d : ds) {
				BigDecimal one = null;
				if(d==null){
					d = 0.0D;
				}
				one = new BigDecimal(Double.toString(d));
				sum = sum.add(one);
			}

			return sum.doubleValue();
		}

		/**
		 * 提供精确的减法运算。
		 * 
		 * @param v1
		 *            被减数
		 * @param v2
		 *            减数
		 * @return 两个参数的差
		 */

		public static double sub(Double v1, Double v2) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.subtract(b2).doubleValue();
		}

		/**
		 * 
		 * 提供精确的乘法运算。
		 * 
		 * @param v1
		 *            被乘数
		 * 
		 * @param v2
		 *            乘数
		 * 
		 * @return 两个参数的积
		 * 
		 */
		public static double mul(Double v1, Double v2) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.multiply(b2).doubleValue();
		}

		/**
		 * Double转基本类型
		 * 
		 * @param d
		 * @return
		 */
		public static double DoubleTOdouble(Double d) {
			if (d == null || "".equals(d)) {
				return 0;
			}
			return d.doubleValue();
		}

		

		static public double doubleValue(Double v, double def) {
			if (v == null) {
				// 必须要变成字符串,再转换成 BigDecimal 这样才精确
				return new BigDecimal(Double.toString(def)).doubleValue();
			} else {
				// 必须要变成字符串,再转换成 BigDecimal 这样才精确
				return new BigDecimal(Double.toString(v)).doubleValue();
			}
		}

	

		static public double doubleValue(Double v) {
			return doubleValue(v, 0.0);
		}

		/**
		 * 
		 * 提供精确的加法运算。
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数的和
		 * 
		 */
		public static double add(double v1, double v2) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.add(b2).doubleValue();
		}

		/**
		 * 
		 * 提供精确的加法运算
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数数学加和，以字符串格式返回
		 * 
		 */
		public static String add(String v1, String v2) {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.add(b2).toString();
		}

		/**
		 * 
		 * 提供精确的减法运算。
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数的差
		 * 
		 */
		public static double subtract(double v1, double v2) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.subtract(b2).doubleValue();
		}

		/**
		 * 
		 * 提供精确的减法运算
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数数学差，以字符串格式返回
		 * 
		 */
		public static String subtract(String v1, String v2) {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.subtract(b2).toString();
		}

		/**
		 * 
		 * 提供精确的乘法运算。
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数的积
		 * 
		 */
		public static double multiply(double v1, double v2) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.multiply(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的乘法运算
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数的数学积，以字符串格式返回
		 * 
		 */
		public static String multiply(String v1, String v2) {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.multiply(b2).toString();
		}

		/**
		 * 
		 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
		 * 
		 * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数的商
		 * 
		 */
		public static double divide(double v1, double v2) {
			return divide(v1, v2, DEFAULT_DIV_SCALE);
		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @param scale
		 *            表示需要精确到小数点以后几位。
		 * 
		 * @return 两个参数的商
		 * 
		 */
		public static double divide(double v1, double v2, int scale) {
			return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);
		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @param scale
		 *            表示需要精确到小数点以后几位
		 * 
		 * @param round_mode
		 *            表示用户指定的舍入模式
		 * 
		 * @return 两个参数的商
		 * 
		 */
		public static double divide(double v1, double v2, int scale, int round_mode) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.divide(b2, scale, round_mode).doubleValue();
		}

		/**
		 * 
		 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
		 * 
		 * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @return 两个参数的商，以字符串格式返回
		 * 
		 */
		public static String divide(String v1, String v2) {
			return divide(v1, v2, DEFAULT_DIV_SCALE);
		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @param scale
		 *            表示需要精确到小数点以后几位
		 * 
		 * @return 两个参数的商，以字符串格式返回
		 * 
		 */
		public static String divide(String v1, String v2, int scale) {
			return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN);
		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
		 * 
		 * @param v1
		 * 
		 * @param v2
		 * 
		 * @param scale
		 *            表示需要精确到小数点以后几位
		 * 
		 * @param round_mode
		 *            表示用户指定的舍入模式
		 * 
		 * @return 两个参数的商，以字符串格式返回
		 * 
		 */
		public static String divide(String v1, String v2, int scale, int round_mode) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.divide(b2, scale, round_mode).toString();
		}

		/**
		 * 
		 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * 
		 * @param scale
		 *            小数点后保留几位
		 * 
		 * @return 四舍五入后的结果
		 * 
		 */
		public static double round(double v, int scale) {
			return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
		}

		/**
		 * 
		 * 提供精确的小数位四舍五入处理
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * 
		 * @param scale
		 *            小数点后保留几位
		 * 
		 * @param round_mode
		 *            指定的舍入模式
		 * 
		 * @return 四舍五入后的结果
		 * 
		 */
		public static double round(double v, int scale, int round_mode) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b = new BigDecimal(Double.toString(v));
			return b.setScale(scale, round_mode).doubleValue();
		}

		/**
		 * 
		 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * 
		 * @param scale
		 *            小数点后保留几位
		 * 
		 * @return 四舍五入后的结果，以字符串格式返回
		 * 
		 */
		public static String round(String v, int scale) {
			return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
		}

		/**
		 * 
		 * 提供精确的小数位四舍五入处理
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * 
		 * @param scale
		 *            小数点后保留几位
		 * 
		 * @param round_mode
		 *            指定的舍入模式
		 * 
		 * @return 四舍五入后的结果，以字符串格式返回
		 * 
		 */
		public static String round(String v, int scale, int round_mode) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b = new BigDecimal(v);
			return b.setScale(scale, round_mode).toString();
		}

		
		/**
		 * 将DOUBLE值过大可能会导致出现科学计数法转换成正常的显示形式，以字符串格式返回
		 */
		public static String formatBigDouble(double v){
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			String vv = nf.format(v);
			return vv;
		}
}
