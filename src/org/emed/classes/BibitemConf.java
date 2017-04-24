package org.emed.classes;
/**
 * @file /src/org/emed/classes/BibitemConf.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.ArrayList;
import java.util.List;

public class BibitemConf extends Bibitem {
	private List<BibName> name;
	private String collab;
	private String source;
	private String confname;
	private String confloc;
	private String confdate;
	private int year;
	
	public List<BibName> getName() {
    	if (name == null) {
			name = new ArrayList<BibName>();
		}
		return name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getConfname() {
		return confname;
	}

	public void setConfname(String confname) {
		this.confname = confname;
	}

	public String getConfloc() {
		return confloc;
	}

	public void setConfloc(String confloc) {
		this.confloc = confloc;
	}

	public String getConfdate() {
		return confdate;
	}

	public void setConfdate(String confdate) {
		this.confdate = confdate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCollab() {
		return collab;
	}

	public void setCollab(String collab) {
		this.collab = collab;
	}
}
