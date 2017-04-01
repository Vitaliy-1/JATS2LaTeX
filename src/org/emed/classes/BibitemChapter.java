package org.emed.classes;

import java.util.ArrayList;
import java.util.List;

public class BibitemChapter extends Bibitem {
	private List<BibName> name;
	private String chapterTitle;
	private List<BibName> editor;
	private String collabAuthor;
	private String collabEditor;
	private String source;
	private String publisherLoc;
	private String publisherName;
	private int year;
	private String fpage;
	private int lpage;
	
	public List<BibName> getName() {
    	if (name == null) {
			name = new ArrayList<BibName>();
		}
		return name;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String source) {
		this.chapterTitle = source;
	}

	public List<BibName> getEditor() {
		if (editor == null) {
			editor = new ArrayList<BibName>();
		}
		return editor;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getCollabAuthor() {
		return collabAuthor;
	}

	public void setCollabAuthor(String collabAuthor) {
		this.collabAuthor = collabAuthor;
	}

	public String getCollabEditor() {
		return collabEditor;
	}

	public void setCollabEditor(String collabEditor) {
		this.collabEditor = collabEditor;
	}

	public String getFpage() {
		return fpage;
	}

	public void setFpage(String fpage) {
		this.fpage = fpage;
	}

	public int getLpage() {
		return lpage;
	}

	public void setLpage(int lpage) {
		this.lpage = lpage;
	}
	
}
