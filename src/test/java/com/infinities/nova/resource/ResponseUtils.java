package com.infinities.nova.resource;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;

public class ResponseUtils {

	private ResponseUtils() {

	}

	public static void printResponse(Logger logger, Response response) {
		logger.debug("{} {}", new Object[] { response.getStatus(), response.getStatusInfo().getReasonPhrase() });
		logger.debug("Content-Length: {}", new Object[] { response.getLength() });
		logger.debug("Content-Type: {}", new Object[] { response.getMediaType() });
		logger.debug("X-Compute-Request-Id: {}", new Object[] { response.getHeaderString("X-Compute-Request-Id") });
		logger.debug("Date: {}", response.getDate());
	}

}
