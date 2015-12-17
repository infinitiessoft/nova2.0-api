package com.infinities.keystonemiddleware.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlTransient;

public interface TokenWrapper {

	@XmlTransient
	Calendar getExpire();

	@XmlTransient
	Bind getBind();

}
