package org.emed.classes;

import java.util.ArrayList;
import java.util.List;

public class BibitemJournal extends Bibitem {
    private List<BibName> name;
    private String collab;
    private String title;
    private String source;
    private int year;
    private int volume;
    private int issue;
    private int fpage;
    private int lpage;
    private String doi;
    private String pmid;
    private String url;

    public List<BibName> getName() {
    	if (name == null) {
			name = new ArrayList<BibName> ();
		}
		return name;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCollab() {
		return collab;
	}

	public void setCollab(String collab) {
		this.collab = collab;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public int getFpage() {
		return fpage;
	}

	public void setFpage(int fpage) {
		this.fpage = fpage;
	}

	public int getLpage() {
		return lpage;
	}

	public void setLpage(int lpage) {
		this.lpage = lpage;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
   
}
