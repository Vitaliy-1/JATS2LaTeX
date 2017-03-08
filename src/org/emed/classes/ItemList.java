package org.emed.classes;

public class ItemList extends SecContent {
	private String listContent;

	public String getListContent() {
		return listContent;
	}

	public void setListContent(String listcontent) {
		this.listContent = "\\item " + listcontent;
	}
	
}
