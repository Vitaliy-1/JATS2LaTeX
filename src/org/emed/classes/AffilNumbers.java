package org.emed.classes;

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
