package cn.com.ylink.ips.web.base.converter.datetime.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.web.base.converter.datetime.AbstractDateTimeParser;

public class YMDHMDateTimeParser extends AbstractDateTimeParser {

	private static Logger _log = LoggerFactory
			.getLogger(YMDHMDateTimeParser.class);

	public YMDHMDateTimeParser() {

	}

	public YMDHMDateTimeParser(AbstractDateTimeParser successor) {
		super(successor);
	}

	private ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
	};

	@Override
	public Date parse0(String str) throws ParseException {
		try {
			return dateFormatThreadLocal.get().parse(str);
		} catch (ParseException e) {
			_log.warn("YMDHMDatetimeParser(yyyy-MM-dd HH:mm) parse time error : "
					+ str);
			throw e;
		}
	}

}
