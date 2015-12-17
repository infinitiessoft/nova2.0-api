package com.infinities.nova.api.openstack.compute.images;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.infinities.nova.response.model.Image;

@XmlRootElement
public class ImageTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image image;


	public ImageTemplate() {

	}

	public ImageTemplate(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
