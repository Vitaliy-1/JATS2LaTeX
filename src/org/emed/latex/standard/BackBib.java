package org.emed.latex.standard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.emed.classes.BibName;
import org.emed.classes.Bibitem;
import org.emed.classes.BibitemBook;
import org.emed.classes.BibitemChapter;
import org.emed.classes.BibitemConf;
import org.emed.classes.BibitemJournal;

public class BackBib {
	public static void back(BufferedWriter bib, List<Bibitem> bibitems) throws IOException {
		bib.write("%Encoding: UTF - 8");
		bib.newLine();
		bib.newLine();
		
		for (Bibitem bibitem : bibitems) {
			List<Bibitem> bibcontents = bibitem.getBibitem();
			for (Bibitem bibcontent : bibcontents) {
				if (bibcontent.getClass().getName() == "org.emed.classes.BibitemJournal") {
					bib.write("@article{");
					bib.write(bibitem.getId() + ",");
					bib.newLine();
					BibitemJournal bibJournal = (BibitemJournal) bibcontent;
					List<BibName> bibNames = bibJournal.getName();
					if (bibJournal.getName() != null && bibJournal.getCollab() == null) {
						bib.write("author = \"");
						for (int i = 0; i < bibNames.size(); i++) {
							char[] initials = bibNames.get(i).getInitials();
							for (int y = 0; y < initials.length; y++) {
								bib.write(initials[y]);
							}
							if (i < bibNames.size() - 1) {
								bib.write(" " + bibNames.get(i).getSurname() + " and ");
							} else {
								bib.write(" " + bibNames.get(i).getSurname() + " \",");
								bib.newLine();
							}
						}
					} else if (bibJournal.getCollab() != null && bibJournal.getName() == null) {
						bib.write("author = \"{");
						bib.write(bibJournal.getCollab());
						bib.write("}\",");
						bib.newLine();
					} else {
						System.out.println("Author(s) or collab group is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tags in JATS XML. This information is mandatory for compiliation of this reference type (journal article)");
					}
					
					if (bibJournal.getTitle() != null) {
						bib.write("title = \"");
						bib.write(bibJournal.getTitle().trim());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Title is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"article-title\"). This information is mandatory for compiliation of this reference type (journal article)");
					}
					
					if (bibJournal.getSource() != null) {
						bib.write("journal = \"");
						bib.write(bibJournal.getSource());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Source name is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"source\"). This information is mandatory for compiliation of this reference type (journal article)");
					}
					
					if (bibJournal.getYear() != 0) {
						bib.write("year = \"");
						bib.write("" + bibJournal.getYear());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibJournal.getVolume() != 0) {
						bib.write("volume = \"");
						bib.write("" + bibJournal.getVolume());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibJournal.getIssue() != 0) {
						bib.write("number = \"");
						bib.write("" + bibJournal.getIssue());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibJournal.getFpage() != null && bibJournal.getLpage() != 0) {
						bib.write("pages = \"");
						bib.write(bibJournal.getFpage());
						bib.write("--");
						bib.write("" + bibJournal.getLpage());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibJournal.getFpage() != null && bibJournal.getLpage() == 0) {
						bib.write("pages = \"");
						bib.write(bibJournal.getFpage());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibJournal.getDoi() != null) {
						bib.write("doi = \"");
						bib.write(bibJournal.getDoi());
						bib.write("\",");
						bib.newLine();
						if (bibJournal.getDoi().contains("http|https")) {
							bib.write("url = \"");
							bib.write(bibJournal.getDoi());
							bib.write("\",");
							bib.newLine();
						} else {
							bib.write("url = \"");
							bib.write("https://doi.org/" + bibJournal.getDoi());
							bib.write("\",");
							bib.newLine();
						}
					} else if (bibJournal.getDoi() == null && bibJournal.getPmid() != null) {
						bib.write("url = \"");
						bib.write(bibJournal.getPmid());
						bib.write("\",");
						bib.newLine();
					} else if (bibJournal.getDoi() == null && bibJournal.getPmid() == null && bibJournal.getUrl() != null) {
						bib.write("url = \"");
						bib.write(bibJournal.getUrl());
						bib.write("\",");
						bib.newLine();
					}
					
					bib.write("}");
					bib.newLine();
				} else if (bibcontent.getClass().getName() == "org.emed.classes.BibitemBook") {
					bib.write("@book{");
					bib.write(bibitem.getId() + ",");
					bib.newLine();
					BibitemBook bibBook = (BibitemBook) bibcontent;
					List<BibName> bibNames = bibBook.getName();
					if (bibBook.getName() != null && bibBook.getCollab() == null) {
						bib.write("author = \"");
						for (int i = 0; i < bibNames.size(); i++) {
							char[] initials = bibNames.get(i).getInitials();
							for (int y = 0; y < initials.length; y++) {
								bib.write(initials[y]);
							}
							if (i < bibNames.size() - 1) {
								bib.write(" " + bibNames.get(i).getSurname() + " and ");
							} else {
								bib.write(" " + bibNames.get(i).getSurname() + " \",");
								bib.newLine();
							}
						}
					} else if (bibBook.getCollab() != null) {
						bib.write("author = \"{");
						bib.write(bibBook.getCollab());
						bib.write("}\",");
						bib.newLine();
					} else {
						System.out.println("Author(s) or collab group is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tags in JATS XML. This information is mandatory for compiliation of this reference type (book)");
					}
					
					if (bibBook.getSource() != null) {
						bib.write("title = \"");
						bib.write(bibBook.getSource().trim());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Title is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"source\"). This information is mandatory for compiliation of this reference type (book)");
					}
					
					if (bibBook.getPublisherName() != null) {
						bib.write("publisher = \"");
						bib.write(bibBook.getPublisherName());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Publisher name is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"publisher-name\"). This information is mandatory for compiliation of this reference type (book)");
					}
					
					if (bibBook.getPublisherLoc() != null) {
						bib.write("address = \"");
						bib.write(bibBook.getPublisherLoc());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibBook.getYear() != 0) {
						bib.write("year = \"");
						bib.write("" + bibBook.getYear());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Year is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"year\"). This information is mandatory for compiliation of this reference type (book)");
					}
					bib.write("}");
					bib.newLine();
				} else if (bibcontent.getClass().getName() == "org.emed.classes.BibitemChapter") {
					bib.write("@inbook{");
					bib.write(bibitem.getId() + ",");
					bib.newLine();
					BibitemChapter bibChapter = (BibitemChapter) bibcontent;
					List<BibName> bibNames = bibChapter.getName();
					if (bibChapter.getName() != null && bibChapter.getCollabAuthor() == null) {
						bib.write("author = \"");
						for (int i = 0; i < bibNames.size(); i++) {
							char[] initials = bibNames.get(i).getInitials();
							for (int y = 0; y < initials.length; y++) {
								bib.write(initials[y]);
							}
							if (i < bibNames.size() - 1) {
								bib.write(" " + bibNames.get(i).getSurname() + " and ");
							} else {
								bib.write(" " + bibNames.get(i).getSurname() + " \",");
								bib.newLine();
							}
						}
					} else if (bibChapter.getCollabAuthor() != null) {
						bib.write("author = \"{");
						bib.write(bibChapter.getCollabAuthor());
						bib.write("}\",");
						bib.newLine();
				    } 	
					
					if (bibChapter.getChapterTitle() != null) {
						bib.write("chapter = \"{");
						bib.write(bibChapter.getChapterTitle());
						bib.write("}\",");
						bib.newLine();
					}
				   
					List<BibName> bibEditors = bibChapter.getEditor();
					if (bibChapter.getEditor() != null && bibChapter.getCollabEditor() == null) {
						bib.write("editor = \"");
						for (int i = 0; i < bibEditors.size(); i++) {
							char[] initials = bibEditors.get(i).getInitials();
							for (int y = 0; y < initials.length; y++) {
								bib.write(initials[y]);
							}
							if (i < bibEditors.size() - 1) {
								bib.write(" " + bibEditors.get(i).getSurname() + " and ");
							} else {
								bib.write(" " + bibEditors.get(i).getSurname() + " \",");
								bib.newLine();
							}
						}
					} else if (bibChapter.getCollabEditor() != null) {
						bib.write("editor = \"{");
						bib.write(bibChapter.getCollabEditor());
						bib.write("}\",");
						bib.newLine();
					}
					
					if (bibChapter.getName() == null && bibChapter.getEditor() == null) {
						System.out.println("Author(s) or collab group or editor is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tags in JATS XML. This information is mandatory for compiliation of this reference type (book chapter)");
				    }
					
					if (bibChapter.getSource() != null) {
						bib.write("title = \"");
						bib.write(bibChapter.getSource().trim());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Title is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"source\"). This information is mandatory for compiliation of this reference type (book chapter)");
					}
					
					if (bibChapter.getPublisherName() != null) {
						bib.write("publisher = \"");
						bib.write(bibChapter.getPublisherName());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Publisher name is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"publisher-name\"). This information is mandatory for compiliation of this reference type (book chapter)");
					}
					
					if (bibChapter.getPublisherLoc() != null) {
						bib.write("address = \"");
						bib.write(bibChapter.getPublisherLoc());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibChapter.getYear() != 0) {
						bib.write("year = \"");
						bib.write("" + bibChapter.getYear());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Year is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"year\"). This information is mandatory for compiliation of this reference type (book)");
					}
					bib.write("}");
					bib.newLine();
				} else if (bibcontent.getClass().getName() == "org.emed.classes.BibitemConf") {
					bib.write("@inproceedings{");
					bib.write(bibitem.getId() + ",");
					bib.newLine();
					BibitemConf bibConf = (BibitemConf) bibcontent;
					List<BibName> bibNames = bibConf.getName();
					if (bibConf.getName() != null && bibConf.getCollab() == null) {
						bib.write("author = \"");
						for (int i = 0; i < bibNames.size(); i++) {
							char[] initials = bibNames.get(i).getInitials();
							for (int y = 0; y < initials.length; y++) {
								bib.write(initials[y]);
							}
							if (i < bibNames.size() - 1) {
								bib.write(" " + bibNames.get(i).getSurname() + " and ");
							} else {
								bib.write(" " + bibNames.get(i).getSurname() + " \",");
								bib.newLine();
							}
						}
					} else if (bibConf.getCollab() != null) {
						bib.write("author = \"{");
						bib.write(bibConf.getCollab());
						bib.write("}\",");
						bib.newLine();
					} else {
						System.out.println("Author(s) or collab group is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tags in JATS XML. This information is mandatory for compiliation of this reference type (inproceedings ≈ conference)");
					}
					
					if (bibConf.getSource() != null) {
						bib.write("title = \"");
						bib.write(bibConf.getSource().trim());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Title is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"source\"). This information is mandatory for compiliation of this reference type (inproceedings ≈ conference)");
					}
					
					if (bibConf.getConfname() != null) {
						bib.write("booktitle = \"");
						bib.write(bibConf.getConfname());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Conference name is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"conf-name\"). This information is mandatory for compiliation of this reference type (inproceedings ≈ conference)");
					}
					
					if (bibConf.getConfloc() != null) {
						bib.write("address = \"");
						bib.write(bibConf.getConfloc());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibConf.getConfdate() != null) {
						bib.write("month = \"");
						bib.write(bibConf.getConfdate());
						bib.write("\",");
						bib.newLine();
					}
					
					if (bibConf.getYear() != 0) {
						bib.write("year = \"");
						bib.write("" + bibConf.getYear());
						bib.write("\",");
						bib.newLine();
					} else {
						System.out.println("Year is not indicated for the reference with id: " + bibitem.getId());
						System.out.println("Please check the corresponding tag in JATS XML (\"year\"). This information is mandatory for compiliation of this reference type (inproceedings ≈ conference)");
					}
					bib.write("}");
					bib.newLine();
				}
			}
		}
		bib.close();
	}
}
