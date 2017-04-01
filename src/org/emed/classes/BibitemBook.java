package org.emed.classes;

import java.util.ArrayList;
import java.util.List;

public class BibitemBook extends Bibitem {
	private List<BibName> name;
	private String collab;
	private String source;
	private String publisherLoc;
	private String publisherName;
	private int year;

	public List<BibName> getName() {
    	if (name == null) {
			name = new ArrayList<BibName> ();
		}
		return name;
	}

	public String getCollab() {
		return collab;
	}

	public void setCollab(String collab) {
		this.collab = collab;
	}

	public String getPublisherLoc() {
		return publisherLoc;
	}

	public void setPublisherLoc(String publisherLoc) {
		this.publisherLoc = publisherLoc;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
