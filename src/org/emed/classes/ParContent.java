package org.emed.classes;

import java.util.ArrayList;
import java.util.List;


public class ParContent extends Section {
	private String title;
	private String type;
	private List<ParContent> parContentList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ParContent> getParContentList() {
		if (parContentList == null) {
			parContentList = new ArrayList<ParContent> ();
		}
		return parContentList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
