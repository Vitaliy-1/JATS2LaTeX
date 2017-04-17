package org.emed.classes;

import java.util.ArrayList;

public class Table extends SecContent {
	private String id;
	private String label;
	private ArrayList <ParContent> parContent;
	private ArrayList<Row> row; 
	private int columnNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList <ParContent> getParContent() {
		if (parContent == null) {
			parContent = new ArrayList<ParContent>();
		}
		return parContent;
	}

	public ArrayList<Row> getRow() {
		if (row == null) {
			row = new ArrayList<Row>();
		}
		return row;
	}

	public void setRow(ArrayList<Row> row) {
		this.row = row;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
}
