package com.infinities.keystonemiddleware.model.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IssueDateAdapter extends XmlAdapter<String, Calendar> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");


	@Override
	public String marshal(Calendar v) throws Exception {
		return dateFormat.format(v.getTime());
	}

	@Override
	public Calendar unmarshal(String v) throws Exception {
		Date date = dateFormat.parse(v);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}