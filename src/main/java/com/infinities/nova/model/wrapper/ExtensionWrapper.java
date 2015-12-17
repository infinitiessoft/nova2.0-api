package com.infinities.nova.model.wrapper;

import com.infinities.nova.response.model.Extension;

public class ExtensionWrapper {

	private Extension extension;


	public ExtensionWrapper() {

	}

	public ExtensionWrapper(Extension extension) {
		this.extension = extension;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

}
