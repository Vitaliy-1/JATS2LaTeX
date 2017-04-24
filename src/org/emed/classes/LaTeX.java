package org.emed.classes;
/**
 * @file /src/org/emed/classes/LaTeX.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.ArrayList;

public class LaTeX {
	private ArrayList<Section> section;
	private ArrayList<Bibitem> bibitem;
	private ArticleMeta articleMeta;
	
	public ArticleMeta getArticleMeta() {
		return articleMeta;
	}
	public void setArticleMeta(ArticleMeta articleMeta) {
		this.articleMeta = articleMeta;
	}
	public ArrayList<Section> getSection() {
		return section;
	}
	public void setSection(ArrayList<Section> section) {
		this.section = section;
	}
	public ArrayList<Bibitem> getBibitem() {
		return bibitem;
	}
	public void setBibitem(ArrayList<Bibitem> bibitem) {
		this.bibitem = bibitem;
	}
}
