package com.senyint.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ComparatorDateUtils implements Comparator<String>{
	
	public  SimpleDateFormat format;

	public ComparatorDateUtils(SimpleDateFormat format) {
		super();
		this.format = format;
	}

	@Override
	public int compare(String o1, String o2) {
		try {
			Date begin = format.parse(o1);
			Date end = format.parse(o2);
			if (begin.after(end)) {
				return 1;
			} else {
				return -1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	

}
