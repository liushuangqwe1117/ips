package cn.com.ylink.ips.web.base.converter.datetime;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cn.com.ylink.ips.core.util.StringUtil;
import cn.com.ylink.ips.web.base.converter.datetime.impl.YDateTimeParser;
import cn.com.ylink.ips.web.base.converter.datetime.impl.YMDDateTimeParser;
import cn.com.ylink.ips.web.base.converter.datetime.impl.YMDHDateTimeParser;
import cn.com.ylink.ips.web.base.converter.datetime.impl.YMDHMDateTimeParser;
import cn.com.ylink.ips.web.base.converter.datetime.impl.YMDHMSDateTimeParser;
import cn.com.ylink.ips.web.base.converter.datetime.impl.YMDateTimeParser;

public class DateTimeConverter extends PropertyEditorSupport {

	private final DateTimeParser dateTimeParser = new YMDHMSDateTimeParser(
			new YMDDateTimeParser(new YMDHMDateTimeParser(
					new YMDHDateTimeParser(new YMDateTimeParser(
							new YDateTimeParser())))));

	private String pattern = "yyyy-MM-dd HH:mm:ss";

	public DateTimeConverter() {
	}

	public DateTimeConverter(String pattern) {
		if (StringUtil.isNotBlank(pattern)) {
			this.pattern = pattern;
		}
	}

	private ThreadLocal<DateFormat> dateformat = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(pattern);
		}
	};

	/**
	 * 对页面以同一种日期格式进行渲染
	 */
	@Override
	public String getAsText() {
		if (getValue() != null) {
			return dateformat.get().format(getValue());
		}
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtil.isBlank(text)) {
			setValue(null);
		} else {
			setValue(dateTimeParser.parse(text));
		}
	}
}
