package org.emed.classes;
/**
 * @file /src/org/emed/classes/Figure.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.ArrayList;

public class Figure extends Section {
	private String title;
	private String label;
	private String link;
	private String id;
	private ArrayList <ParContent> parContent;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList <ParContent> getParContent() {
		if (parContent == null) {
			parContent = new ArrayList<ParContent>();
		}
		return parContent;
	}
}
