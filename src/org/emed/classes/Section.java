package org.emed.classes;

import java.util.ArrayList;
import java.util.List;



public class Section {
	
	private String title;
	private List<SecContent> seccontent;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = "\\section {" + title + "}";
	}
	
	public List<SecContent> getSecContent() {
		if (seccontent == null) {
			seccontent = new ArrayList<SecContent> ();
		}
		 return this.seccontent;
	}
	
}