package org.emed.classes;

import java.util.HashMap;

public class ArticleMeta {
	
	private String journal;
	private String titleEng;
	private String titleUkr;
	private String udc;
	private int volume;
	private int issue;
	private String id;
	private HashMap<Integer, Author> name;
	private HashMap<Integer, Affiliation> institution;
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getTitleEng() {
		return titleEng;
	}
	public void setTitleEng(String titleEng) {
		this.titleEng = titleEng;
	}
	public String getTitleUkr() {
		return titleUkr;
	}
	public void setTitleUkr(String titleUkr) {
		this.titleUkr = titleUkr;
	}
	public String getUdc() {
		return udc;
	}
	public void setUdc(String udc) {
		this.udc = udc;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HashMap<Integer, Author> getName() {
		if (name == null) {
			name = new HashMap<Integer, Author> ();
		}
		return name;
	}
	public HashMap<Integer, Affiliation> getInstitution() {
		if (institution == null) {
			institution = new HashMap<Integer, Affiliation> ();
		}
		return institution;
	}
	
}
