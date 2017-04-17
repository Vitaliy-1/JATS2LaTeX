package org.emed.classes;

import java.util.ArrayList;

public class Row {
	private String type;
	private ArrayList<Cell> cell;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Cell> getCell() {
		if (cell == null) {
			cell = new ArrayList<Cell>();
		}
		return cell;
	}

	public void setCell(ArrayList<Cell> cell) {
		this.cell = cell;
	}
}
