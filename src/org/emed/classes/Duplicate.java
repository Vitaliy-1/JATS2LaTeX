package org.emed.classes;

/* This class created for managing multirows in tables 
 * In rows we set the number of a row, where multirow exists
 * In cell we set the number of cell in the row, where multirow exists
 * So we know where additional "&" symbol in LaTeX tables should be placed (next row(s) after the multirow and the same cell)
 * */

public class Duplicate {
	private int cell;
	private int row;
	private int difference;
	
	public int getCell() {
		return cell;
	}
	public void setCell(int cell) {
		this.cell = cell;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getDifference() {
		return difference;
	}
	public void setDifference(int difference) {
		this.difference = difference;
	}
}
