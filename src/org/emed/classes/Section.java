package org.emed.classes;

import java.util.ArrayList;
import java.util.List;



public class Section {
	
	private String title;
	private String type;
	private List<Section> seccontent;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Section> getSecContent() {
		if (seccontent == null) {
			seccontent = new ArrayList<Section> ();
		}
		 return this.seccontent;
	}
	
	public void setSecContent(ArrayList<Section> seccontent) {
		this.seccontent = seccontent;
	}
	
}