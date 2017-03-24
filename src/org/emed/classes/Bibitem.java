package org.emed.classes;

import java.util.ArrayList;
import java.util.List;

public class Bibitem {
    private String type;
    private String id;
    private List<Bibitem> bibitem;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Bibitem> getBibitem() {
		if (bibitem == null) {
			bibitem = new ArrayList<Bibitem> ();
		}
		return bibitem;
	}
}
