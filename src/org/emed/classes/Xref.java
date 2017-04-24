package org.emed.classes;
/**
 * @file /src/org/emed/classes/Xref.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */

public class Xref extends ParContent {
	private String bibContent;
	private String figContent;
	private String tableContent;
	private String figDescription;
	private String tableDescription;
	
	public String getBibContent() {
		return bibContent;
	}
	public void setBibContent(String bibContent) {
		this.bibContent = bibContent;
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
		this.tableContent = "\\ref{" + tableContent + "}";
	}
	public String getFigDescription() {
		return figDescription;
	}
	public void setFigDescription(String figDescription) {
		this.figDescription = figDescription.replaceAll("\\s*\\d+", "");
	}
	public String getTableDescription() {
		return tableDescription;
	}
	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription.replaceAll("\\s*\\d+", "");
	}
	
	
}
