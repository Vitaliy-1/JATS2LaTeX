package org.emed.classes;

import java.util.ArrayList;
import java.util.List;

public class SecContent extends Section {
	
	public String title;
	public String content;
	public List<SubSec> subsec;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = "\\subsection {" + title + "}";
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent (String content) {
		this.content = content;
	}
	/*
	public List<SubSec> getSecContent() {
		if (subsec == null) {
			subsec = new ArrayList<SubSec> ();
		}
		 return this.subsec;
	}
    */
}
