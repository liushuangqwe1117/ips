package cn.com.ylink.ips.web.base.converter.datetime;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.util.StringUtil;

/**
 * 采用责任链模式进行日期转换处理
 * 
 * @author LS
 * 
 */
public abstract class AbstractDateTimeParser implements DateTimeParser {

	private static Logger _log = LoggerFactory
			.getLogger(AbstractDateTimeParser.class);

	private AbstractDateTimeParser successor;

	public AbstractDateTimeParser() {

	}

	public AbstractDateTimeParser(AbstractDateTimeParser successor) {
		this.successor = successor;
	}

	/**
	 * 利用异常来进行时间转换器选择
	 * 
	 * @param str
	 * @return
	 */
	public Date parse(String str) {
		if (StringUtil.isNotBlank(str)) {
			try {
				return parse0(str);
			} catch (ParseException e) {
				if(successor != null){
					return successor.parse(str);
				}
			}
		}
		_log.warn("date format error : date str is null or '' ");
		return null;
	}

	public abstract Date parse0(String str) throws ParseException;
}
