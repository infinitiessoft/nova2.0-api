package com.infinities.keystonemiddleware.model.v3;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "service")
public class Service {

	private String id;

	private String type;

	private String name;

	private String description;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
