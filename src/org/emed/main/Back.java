package org.emed.main;
/**
 * @file /src/org/emed/main/Back.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.emed.classes.BibName;
import org.emed.classes.Bibitem;
import org.emed.classes.BibitemBook;
import org.emed.classes.BibitemChapter;
import org.emed.classes.BibitemConf;
import org.emed.classes.BibitemJournal;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Back {
	protected static ArrayList<Bibitem> back(Document document, XPath xPath)
			throws XPathExpressionException, DOMException, NumberFormatException {
		ArrayList<Bibitem> bibitems = new ArrayList<Bibitem>();
		NodeList bibXmls = (NodeList) xPath.compile("/article/back/ref-list/ref").evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i<bibXmls.getLength(); i++) {
			Bibitem bibitem = new Bibitem();
			Node bibXml = bibXmls.item(i);
			bibitem.setId(bibXml.getAttributes().getNamedItem("id").getTextContent());
			bibitems.add(bibitem);
			
			/* checking Nodes specific to journal, book, chapter or conference */
			Node articleTitleCheck = (Node) xPath.compile("element-citation/article-title").evaluate(bibXml, XPathConstants.NODE);
			Node bookTitleCheck = (Node) xPath.compile("element-citation/source").evaluate(bibXml, XPathConstants.NODE);
			Node chapterTitleCheck = (Node) xPath.compile("element-citation/chapter-title").evaluate(bibXml, XPathConstants.NODE);
			Node conferenceTitleCheck = (Node) xPath.compile("element-citation/conf-name").evaluate(bibXml, XPathConstants.NODE);
			
			/* checking element citation element atribute */
			Node ref = (Node) xPath.compile("element-citation").evaluate(bibXml, XPathConstants.NODE);
			if ((ref.getAttributes().getNamedItem("publication-type") != null && ref.getAttributes().getNamedItem("publication-type").getTextContent().contains("journal")) || articleTitleCheck != null) {
				BibitemJournal bibJournal = new BibitemJournal();
				NodeList surname = (NodeList) xPath.compile("person-group/name/surname").evaluate(ref, XPathConstants.NODESET);
				if (surname != null) {
					for (int i1 = 0; i1 < surname.getLength(); i1++) {
						//System.out.println(surname.item(i1).getTextContent());
						BibName bibName = new BibName(); 
						bibName.setSurname(surname.item(i1).getTextContent());
						bibJournal.getName().add(bibName);
						Node given = (Node) xPath.compile("following-sibling::given-names[1]").evaluate(surname.item(i1), XPathConstants.NODE);
					    if (given != null) {
					    	char[] initials = given.getTextContent().toCharArray();
					    	bibName.setInitials(initials);
					    }
					}
				}
				Node collab = (Node) xPath.compile("person-group/collab").evaluate(ref, XPathConstants.NODE);
				if (collab != null) {
					bibJournal.setCollab(collab.getTextContent().trim());
				}
				Node title = (Node) xPath.compile("article-title").evaluate(ref, XPathConstants.NODE);
				if (title != null) {
					bibJournal.setTitle(title.getTextContent());
				}
				Node source = (Node) xPath.compile("source").evaluate(ref, XPathConstants.NODE);
				if (source != null) {
					bibJournal.setSource(source.getTextContent());
				}
				Node year = (Node) xPath.compile("year").evaluate(ref, XPathConstants.NODE);
				if (year != null) {
					int yearInt = Integer.parseInt(year.getTextContent());
					bibJournal.setYear(yearInt);
				}
				Node volume = (Node) xPath.compile("volume").evaluate(ref, XPathConstants.NODE);
				if (volume != null) {
					int volumeInt = Integer.parseInt(volume.getTextContent());
					bibJournal.setVolume(volumeInt);
				}
				Node issue = (Node) xPath.compile("issue").evaluate(ref, XPathConstants.NODE);
				if (issue != null) {
					int issueInt = Integer.parseInt(issue.getTextContent());
					bibJournal.setIssue(issueInt);
				}
				Node fpage = (Node) xPath.compile("fpage").evaluate(ref, XPathConstants.NODE);
				if (fpage != null) {
					bibJournal.setFpage(fpage.getTextContent());
				}
				Node lpage = (Node) xPath.compile("lpage").evaluate(ref, XPathConstants.NODE);
				if (lpage != null) {
					int pageInt = Integer.parseInt(lpage.getTextContent());
					bibJournal.setLpage(pageInt);
				}
				Node doipmid = (Node) xPath.compile("pub-id").evaluate(ref, XPathConstants.NODE);
				if (doipmid != null && doipmid.getAttributes().getNamedItem("pub-id-type").getTextContent().contains("doi")) {
					bibJournal.setDoi(doipmid.getTextContent());
				} else if (doipmid != null && doipmid.getAttributes().getNamedItem("pub-id-type").getTextContent().contains("pmid")) {
					bibJournal.setPmid(doipmid.getTextContent());
				}
				Node urlLink = (Node) xPath.compile("ext-link").evaluate(ref, XPathConstants.NODE);
				if (urlLink != null) {
					bibJournal.setUrl(urlLink.getTextContent());
				}
				bibitem.getBibitem().add(bibJournal);	
			} else if ((ref.getAttributes().getNamedItem("publication-type") != null && ref.getAttributes().getNamedItem("publication-type").getTextContent().contains("book")) || (bookTitleCheck != null && chapterTitleCheck == null && conferenceTitleCheck == null)) {
				BibitemBook bibitemBook = new BibitemBook();
				NodeList surname = (NodeList) xPath.compile("person-group/name/surname").evaluate(ref, XPathConstants.NODESET);
				if (surname != null) {
					for (int i1 = 0; i1 < surname.getLength(); i1++) {
						BibName bibName = new BibName(); 
						bibName.setSurname(surname.item(i1).getTextContent());
						bibitemBook.getName().add(bibName);
						Node given = (Node) xPath.compile("following-sibling::given-names[1]").evaluate(surname.item(i1), XPathConstants.NODE);
					    if (given != null) {
					    	char[] initials = given.getTextContent().toCharArray();
					    	bibName.setInitials(initials);
					    }
					}
				}
				Node collab = (Node) xPath.compile("person-group/collab").evaluate(ref, XPathConstants.NODE);
				if (collab != null) {
					bibitemBook.setCollab(collab.getTextContent().trim());
				}
				Node source = (Node) xPath.compile("source").evaluate(ref, XPathConstants.NODE);
				if (source != null) {
					bibitemBook.setSource(source.getTextContent());
				}
				Node publisherLoc = (Node) xPath.compile("publisher-loc").evaluate(ref, XPathConstants.NODE);
				if (publisherLoc != null) {
					bibitemBook.setPublisherLoc(publisherLoc.getTextContent());
				}
				Node publisherName = (Node) xPath.compile("publisher-name").evaluate(ref, XPathConstants.NODE);
				if (publisherName != null) {
					bibitemBook.setPublisherName(publisherName.getTextContent());
				}
				Node year = (Node) xPath.compile("year").evaluate(ref, XPathConstants.NODE);
				int yearInt = Integer.parseInt(year.getTextContent());
				if (yearInt != 0) {
					bibitemBook.setYear(yearInt);
				}
				
				bibitem.getBibitem().add(bibitemBook);	
			} else if ((ref.getAttributes().getNamedItem("publication-type") != null && ref.getAttributes().getNamedItem("publication-type").getTextContent().contains("chapter")) || chapterTitleCheck != null) {
				BibitemChapter bibitemChapter = new BibitemChapter();
				NodeList personGroups = (NodeList) xPath.compile("person-group").evaluate(ref, XPathConstants.NODESET);
				for (int pp = 0; pp < personGroups.getLength(); pp++) {
					Node personGroup = personGroups.item(pp);
					if (personGroup.getAttributes().getNamedItem("person-group-type") != null && personGroup.getAttributes().getNamedItem("person-group-type").getTextContent().contains("author")) {
						NodeList surname = (NodeList) xPath.compile("name/surname").evaluate(personGroup, XPathConstants.NODESET);
						if (surname != null) {
							for (int i1 = 0; i1 < surname.getLength(); i1++) {
								//System.out.println(surname.item(i1).getTextContent());
								BibName bibName = new BibName(); 
								bibName.setSurname(surname.item(i1).getTextContent());
								bibitemChapter.getName().add(bibName);
								Node given = (Node) xPath.compile("following-sibling::given-names[1]").evaluate(surname.item(i1), XPathConstants.NODE);
							    if (given != null) {
							    	char[] initials = given.getTextContent().toCharArray();
							    	bibName.setInitials(initials);
							    }
							}
						}
						Node collab = (Node) xPath.compile("collab").evaluate(personGroup, XPathConstants.NODE);
						if (surname == null && collab != null) {
							bibitemChapter.setCollabAuthor(collab.getTextContent());
						}
					} else if (personGroup.getAttributes().getNamedItem("person-group-type") != null && personGroup.getAttributes().getNamedItem("person-group-type").getTextContent().contains("editor")) {
						NodeList surname = (NodeList) xPath.compile("name/surname").evaluate(personGroup, XPathConstants.NODESET);
						if (surname != null) {
							for (int i1 = 0; i1 < surname.getLength(); i1++) {
								//System.out.println(surname.item(i1).getTextContent());
								BibName bibName = new BibName(); 
								bibName.setSurname(surname.item(i1).getTextContent());
								bibitemChapter.getEditor().add(bibName);
								Node given = (Node) xPath.compile("following-sibling::given-names[1]").evaluate(surname.item(i1), XPathConstants.NODE);
							    if (given != null) {
							    	char[] initials = given.getTextContent().toCharArray();
							    	bibName.setInitials(initials);
							    }
							}
						}
						Node collab = (Node) xPath.compile("collab").evaluate(personGroup, XPathConstants.NODE);
						if (surname == null && collab != null) {
							bibitemChapter.setCollabEditor(collab.getTextContent());
						}
					}
				}
				Node chapterTitle = (Node) xPath.compile("chapter-title").evaluate(ref,  XPathConstants.NODE);
				if (chapterTitle != null) {
					bibitemChapter.setChapterTitle(chapterTitle.getTextContent());
				}
				
				Node source = (Node) xPath.compile("source").evaluate(ref, XPathConstants.NODE);
				if (source != null) {
					bibitemChapter.setSource(source.getTextContent());
				}
				Node publisherLoc = (Node) xPath.compile("publisher-loc").evaluate(ref, XPathConstants.NODE);
				if (publisherLoc != null) {
					bibitemChapter.setPublisherLoc(publisherLoc.getTextContent());
				}
				Node publisherName = (Node) xPath.compile("publisher-name").evaluate(ref, XPathConstants.NODE);
				if (publisherName != null) {
					bibitemChapter.setPublisherName(publisherName.getTextContent());
				}
				Node year = (Node) xPath.compile("year").evaluate(ref, XPathConstants.NODE);
				int yearInt = Integer.parseInt(year.getTextContent());
				if (yearInt != 0) {
					bibitemChapter.setYear(yearInt);
				}
				Node fpage = (Node) xPath.compile("fpage").evaluate(ref, XPathConstants.NODE);
				if (fpage != null) {
					bibitemChapter.setFpage(fpage.getTextContent());
				}
				Node lpage = (Node) xPath.compile("lpage").evaluate(ref, XPathConstants.NODE);
				if (lpage != null) {
					int pageInt = Integer.parseInt(lpage.getTextContent());
					bibitemChapter.setLpage(pageInt);
				}
						
				bibitem.getBibitem().add(bibitemChapter);	
				
				
			} else if (ref.getAttributes().getNamedItem("publication-type") != null && ref.getAttributes().getNamedItem("publication-type").getTextContent().contains("conference")) {
				BibitemConf bibitemConf = new BibitemConf();
				NodeList surname = (NodeList) xPath.compile("person-group/name/surname").evaluate(ref, XPathConstants.NODESET);
				if (surname != null) {
					for (int i1 = 0; i1 < surname.getLength(); i1++) {
						BibName bibName = new BibName(); 
						bibName.setSurname(surname.item(i1).getTextContent());
						bibitemConf.getName().add(bibName);
						Node given = (Node) xPath.compile("following-sibling::given-names[1]").evaluate(surname.item(i1), XPathConstants.NODE);
					    if (given != null) {
					    	char[] initials = given.getTextContent().toCharArray();
					    	bibName.setInitials(initials);
					    }
					}
				}
				Node collab = (Node) xPath.compile("collab").evaluate(ref, XPathConstants.NODE);
				if (surname == null && collab != null) {
					bibitemConf.setCollab(collab.getTextContent());
				}
				Node source = (Node) xPath.compile("source").evaluate(ref, XPathConstants.NODE);
				if (source != null) {
					bibitemConf.setSource(source.getTextContent());
				}
				Node confloc = (Node) xPath.compile("conf-loc").evaluate(ref, XPathConstants.NODE);
				if (confloc != null) {
					bibitemConf.setConfloc(confloc.getTextContent());
				}
				Node confname = (Node) xPath.compile("conf-name").evaluate(ref, XPathConstants.NODE);
				if (confname != null) {
					bibitemConf.setConfname(confname.getTextContent());
				}
				Node year = (Node) xPath.compile("year").evaluate(ref, XPathConstants.NODE);
				int yearInt = Integer.parseInt(year.getTextContent());
				if (yearInt != 0) {
					bibitemConf.setYear(yearInt);
				}
				Node confdate = (Node) xPath.compile("conf-date").evaluate(ref, XPathConstants.NODE);
				if (confdate != null) {
					bibitemConf.setConfdate(confdate.getTextContent());
				}
				
				bibitem.getBibitem().add(bibitemConf);	
				
				/* if publication type is not explicitly indicated
				 * treat as journal article
				 */
			} else {
				BibitemJournal bibJournal = new BibitemJournal();
				NodeList surname = (NodeList) xPath.compile("person-group/name/surname").evaluate(ref, XPathConstants.NODESET);
				if (surname != null) {
					for (int i1 = 0; i1 < surname.getLength(); i1++) {
						BibName bibName = new BibName(); 
						bibName.setSurname(surname.item(i1).getTextContent());
						bibJournal.getName().add(bibName);
						Node given = (Node) xPath.compile("following-sibling::given-names[1]").evaluate(surname.item(i1), XPathConstants.NODE);
					    if (given != null) {
					    	char[] initials = given.getTextContent().toCharArray();
					    	bibName.setInitials(initials);
					    }
					}
				}
				Node collab = (Node) xPath.compile("person-group/collab").evaluate(ref, XPathConstants.NODE);
				if (collab != null) {
					bibJournal.setCollab(collab.getTextContent().trim());
				}
				Node title = (Node) xPath.compile("article-title").evaluate(ref, XPathConstants.NODE);
				if (title != null) {
					bibJournal.setTitle(title.getTextContent());
				}
				Node source = (Node) xPath.compile("source").evaluate(ref, XPathConstants.NODE);
				if (source != null) {
					bibJournal.setSource(source.getTextContent());
				}
				Node year = (Node) xPath.compile("year").evaluate(ref, XPathConstants.NODE);
				if (year != null) {
					int yearInt = Integer.parseInt(year.getTextContent());
					bibJournal.setYear(yearInt);
				}
				Node volume = (Node) xPath.compile("volume").evaluate(ref, XPathConstants.NODE);
				if (volume != null) {
					int volumeInt = Integer.parseInt(volume.getTextContent());
					bibJournal.setVolume(volumeInt);
				}
				Node issue = (Node) xPath.compile("issue").evaluate(ref, XPathConstants.NODE);
				if (issue != null) {
					int issueInt = Integer.parseInt(issue.getTextContent());
					bibJournal.setIssue(issueInt);
				}
				Node fpage = (Node) xPath.compile("fpage").evaluate(ref, XPathConstants.NODE);
				if (fpage != null) {
					bibJournal.setFpage(fpage.getTextContent());
				}
				Node lpage = (Node) xPath.compile("lpage").evaluate(ref, XPathConstants.NODE);
				if (lpage != null) {
					int pageInt = Integer.parseInt(lpage.getTextContent());
					bibJournal.setLpage(pageInt);
				}
				Node doipmid = (Node) xPath.compile("pub-id").evaluate(ref, XPathConstants.NODE);
				if (doipmid != null && doipmid.getAttributes().getNamedItem("pub-id-type").getTextContent().contains("doi")) {
					bibJournal.setDoi(doipmid.getTextContent());
				} else if (doipmid != null && doipmid.getAttributes().getNamedItem("pub-id-type").getTextContent().contains("pmid")) {
					bibJournal.setPmid(doipmid.getTextContent());
				}
				Node urlLink = (Node) xPath.compile("ext-link").evaluate(ref, XPathConstants.NODE);
				if (urlLink != null) {
					bibJournal.setUrl(urlLink.getTextContent());
				}
				bibitem.getBibitem().add(bibJournal);	
			}
		}
		return bibitems;
		
	}
}
