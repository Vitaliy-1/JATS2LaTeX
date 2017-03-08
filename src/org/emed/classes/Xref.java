package org.emed.classes;

public class Xref extends ParContent {
	private String bibContent;
	private String figContent;
	private String tableContent;
	
	public String getBibContent() {
		return bibContent;
	}
	public void setBibContent(String bibContent) {
		this.bibContent = "\\cite{" + bibContent + "}";
	}
	public String getFigContent() {
		return figContent;
	}
	public void setFigContent(String figContent) {
		this.figContent = "\\ref{" + figContent + "}";
	}
	public String getTableContent() {
		return tableContent;
	}
	public void setTableContent(String tableContent) {
		this.tableContent = tableContent;
	}

}
