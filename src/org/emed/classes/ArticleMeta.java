package org.emed.classes;
/**
 * @file /src/org/emed/classes/ArticleMeta.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ArticleMeta {
	
	private String journal;
	private String titleEng;
	private String titleUkr;
	private String udc;
	private int volume;
	private int issue;
	private String id;
	private LinkedHashMap<AffilNumbers, Author> name;
	private LinkedHashMap<Integer, Affiliation> institution;
	private LinkedHashMap<String, String> abstractEng;
	private LinkedHashMap<String, String> abstractUkr;
	private String month;
	private String monthUkr;
	private int year;
	private ArrayList<String> keywords;
	private String doi;
	
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
	public LinkedHashMap<AffilNumbers, Author> getName() {
		if (name == null) {
			name = new LinkedHashMap<AffilNumbers, Author> ();
		}
		return name;
	}
	public LinkedHashMap<Integer, Affiliation> getInstitution() {
		if (institution == null) {
			institution = new LinkedHashMap<Integer, Affiliation> ();
		}
		return institution;
	}
	public LinkedHashMap<String, String> getAbstractEng() {
		if (abstractEng == null) {
			abstractEng = new LinkedHashMap<String, String> ();
		}
		return abstractEng;
	}
	public LinkedHashMap<String, String> getAbstractUkr() {
		if (abstractUkr == null) {
			abstractUkr = new LinkedHashMap<String, String> ();
		}
		return abstractUkr;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthUkr() {
		return monthUkr;
	}
	public void setMonthUkr(String monthUkr) {
		this.monthUkr = monthUkr;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public ArrayList<String> getKeywords() {
		if (keywords == null) {
			keywords = new ArrayList<String> ();
		}
		return keywords;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
}
