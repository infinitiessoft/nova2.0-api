//package com.infinities.nova.response.model;
//
//import java.io.Serializable;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.xml.bind.annotation.XmlElement;
//
//public class Images implements Iterable<Image>, Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	@XmlElement(name = "images")
//	private List<Image> list;
//
//	@XmlElement(name = "images_links")
//	private List<Link> links;
//
//
//	/**
//	 * @return the list
//	 */
//	public List<Image> getList() {
//		return list;
//	}
//
//	@Override
//	public Iterator<Image> iterator() {
//		return list.iterator();
//	}
//
//	public void setList(List<Image> list) {
//		this.list = list;
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
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "Images [list=" + list + "]";
//	}
//
//}
