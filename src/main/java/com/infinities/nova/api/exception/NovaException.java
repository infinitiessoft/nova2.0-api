package com.infinities.nova.api.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.common.Config;

public abstract class NovaException extends SafeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(NovaException.class);
	private Map<String, List<Object>> headers = new HashMap<String, List<Object>>();
	private boolean safe = false;
	private String message;
	private int code;


	public NovaException(String message, Object... args) {
		this(500, message, args);
	}

	public NovaException(int code, String message, Object... args) {
		super(null, Status.fromStatusCode(code));
		this.code = code;
		if (Strings.isNullOrEmpty(message)) {
			try {
				if (args == null) {
					args = new String[] {};
				}
				this.message = String.format(this.getMsgFmt(), args);
			} catch (Exception e) {
				logger.error("Exception in string format operation", e);
				for (Object entry : args) {
					logger.error("{}", new Object[] { entry });
				}

				boolean errors = Config.Instance.getOpt(Config.Type.DEFAULT, "fatal_exception_format_errors").asBoolean();
				if (errors) {
					throw e;
				} else {
					this.message = this.getMsgFmt();
				}
			}
		}
	}

	public String getMsgFmt() {
		return "An unknown exception occurred.";
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Map<String, List<Object>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<Object>> headers) {
		this.headers = headers;
	}

	@Override
	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
