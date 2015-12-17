//package com.infinities.nova.response.model;
//
//import java.io.Serializable;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.xml.bind.annotation.XmlElement;
//
//public class Flavors implements Iterable<Flavor>, Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	@XmlElement(name = "flavors")
//	private List<Flavor> list;
//
//	@XmlElement(name = "flavors_links")
//	private List<Link> links;
//
//
//	/**
//	 * @return the list
//	 */
//	public List<Flavor> getList() {
//		return list;
//	}
//
//	@Override
//	public Iterator<Flavor> iterator() {
//		return list.iterator();
//	}
//
//	public List<Link> getLinks() {
//		return links;
//	}
//
//	public void setLinks(List<Link> links) {
//		this.links = links;
//	}
//
//	public void setList(List<Flavor> list) {
//		this.list = list;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "Flavors [list=" + list + "]";
//	}
//
//}
