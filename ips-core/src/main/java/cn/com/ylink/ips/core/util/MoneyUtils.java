/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建: Iquil 2012-8-23
 */

package cn.com.ylink.ips.core.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Iquil
 * @date 2012-8-23
 * @description：金额工具类
 */

public class MoneyUtils {

	/**
	 * @description 将以元为单位的String转为以分为单位的long，支持带千位分隔符，比如“1,045.34”转为104534
	 * @param sMoneyYuan
	 * @return lMoneyFen
	 * @author Iquil
	 * @date 2012-8-23
	 */
	public static long stringYuanToLongFen(String sMoneyYuan) {
		sMoneyYuan = sMoneyYuan.replaceAll(",", "");
		double dMoneyYuan = Double.parseDouble(sMoneyYuan);
		long lMoneyFen = Math.round(dMoneyYuan * 100);
		return lMoneyFen;
	}

	/**
	 * @description 将以分为单位的long转为以元为单位（带千位分隔符）的String，比如104534转为“1,045.34”
	 * @param lMoneyFen
	 * @return sMoneyYuan
	 * @author Iquil
	 * @date 2012-8-23
	 */
	public static String longFenToStringYuanWithSplit(long lMoneyFen) {
		double dMoneyYuan = (double) lMoneyFen / 100;
		NumberFormat formatter = new DecimalFormat("#,##0.00");
		String sMoneyYuan = formatter.format(dMoneyYuan);
		return sMoneyYuan;
	}

	/**
	 * @description 将以分为单位的long转为以元为单位（不带千位分隔符）的String，比如104534转为“1045.34”
	 * @param lMoneyFen
	 * @return sMoneyYuan
	 * @author Iquil
	 * @date 2012-8-23
	 */
	public static String longFenToStringYuanWithNoSplit(long lMoneyFen) {
		double dMoneyYuan = (double) lMoneyFen / 100;
		NumberFormat formatter = new DecimalFormat("#0.00");
		String sMoneyYuan = formatter.format(dMoneyYuan);
		return sMoneyYuan;
	}

	public static void main(String[] args) {
		System.out.println(stringYuanToLongFen("12345678901234.99"));
		System.out.println(longFenToStringYuanWithSplit(stringYuanToLongFen("100.1")));
		System.out.println(longFenToStringYuanWithNoSplit(stringYuanToLongFen("100.1")));

	}

}
