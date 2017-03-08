package org.emed.classes;

public class Italic extends ParContent {
	private String itContent;

	public String getItContent() {
		return itContent;
	}

	public void setItContent(String itContent) {
		this.itContent = "\\textit {" + itContent + "}";
	}

}
