package org.emed.classes;
/**
 * @file /src/org/emed/classes/AffilNumbers.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.ArrayList;

public class AffilNumbers {
	private ArrayList<String> affilnumbers;

	public ArrayList<String> getAffilnumbers() {
		if (affilnumbers == null) {
			affilnumbers = new ArrayList<String> ();
		}
		return affilnumbers;
	}

	public void setAffilnumbers(ArrayList<String> affilnumbers) {
		this.affilnumbers = affilnumbers;
	}
}
