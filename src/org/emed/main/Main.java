package org.emed.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.emed.classes.BibName;
import org.emed.classes.Bibitem;
import org.emed.classes.BibitemJournal;
import org.emed.classes.Italic;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.SecContent;
import org.emed.classes.Section;
import org.emed.classes.ItemList;
import org.emed.classes.Markup;
import org.emed.classes.SubSec;
import org.emed.classes.Xref;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		
		BufferedWriter writer = new BufferedWriter (new FileWriter("output.tex"));
		BufferedWriter wrobj = new BufferedWriter (new FileWriter("output2.tex"));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("burda3.xml");
		
		List<Section> listSections = new ArrayList<Section>();
		XPath xPath =  XPathFactory.newInstance().newXPath();
		NodeList nodeSections = (NodeList) xPath.compile("/article/body/sec").evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodeSections.getLength(); i++) {
			Node nodeSection = nodeSections.item(i);
			Node nodeSectionTitle = (Node) xPath.compile("title").evaluate(nodeSection,  XPathConstants.NODE);
			Section section = new Section();
			listSections.add(section);
			listSections.get(i).setTitle(nodeSectionTitle.getTextContent());
			writer.write(listSections.get(i).getTitle());
			writer.newLine();
			NodeList nodeSecElements = (NodeList) xPath.compile("p|fig|sec|table-wrap|list").evaluate(nodeSection, XPathConstants.NODESET);
			for (int i1 = 0; i1 < nodeSecElements.getLength(); i1++) {
				
				/* First Level of section nodes*/
				Node nodeSecElement = nodeSecElements.item(i1);
				if ((nodeSecElement.getNodeName() == "p") && (nodeSecElement.getTextContent() != null)) {
					ParContent parContent = new ParContent();
					parContent.setTitle("");
					section.getSecContent().add(parContent);
					writer.write(parContent.getTitle());
					//par.setParContent(nodeSecElement.getTextContent());
					NodeList insidePars = nodeSecElement.getChildNodes();
					for (int j = 0; j < insidePars.getLength(); j++) {
						Node insidePar = insidePars.item(j);
						
						if (insidePar.getNodeValue() != null) {
							Par par = new Par();
							par.setContent(insidePar.getNodeValue());
							parContent.getParContentList().add(par);
							writer.write(par.getContent());
							writer.newLine();
						} else if (insidePar.getNodeName() == "italic") {
							Italic italic = new Italic();
							italic.setContent(insidePar.getTextContent());
							//String italic = insidePar.getTextContent();
							//par.setParContent("\\textit{" + italic + "}");
							parContent.getParContentList().add(italic);
							//writer.write(par.getParContent());
							
						} else if (insidePar.getNodeName() == "xref") {
							  if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("bibr")) {
								  Xref xref = new Xref();
								  xref.setBibContent(insidePar.getTextContent().trim());
								  
								  parContent.getParContentList().add(xref);
								
								  writer.write(xref.getBibContent());
					             
							  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("fig")) {
								  Xref xref = new Xref();
								  String xrefFig = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
								  String figDescription = insidePar.getTextContent().trim();
								  xref.setFigDescription(figDescription);
								  xref.setFigContent(xrefFig);
								  parContent.getParContentList().add(xref);
								  writer.write(xref.getFigContent());
							  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("table")) {
								  Xref xref = new Xref();
								  String TableDescription = insidePar.getTextContent();
								  String xrefTable = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
								  xref.setTableDescription(TableDescription);
								  xref.setTableContent(xrefTable);
								  parContent.getParContentList().add(xref);
								  writer.write(xref.getTableContent());
							  }
						}
					}
					//section.getSecContent().add(par);
					
					writer.newLine();
				} else if((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
					NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					Markup markup1 = new Markup();
					markup1.setContent("\\begin{itemize}");
					section.getSecContent().add(markup1);
					for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
						Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
						ItemList itemList = new ItemList();
						itemList.setListContent(nodeSubSecListItem.getTextContent());
						section.getSecContent().add(itemList);
						writer.write(itemList.getListContent());
						writer.newLine();
					}
					Markup markup2 = new Markup();
					markup2.setContent("\\end{itemize}");
					section.getSecContent().add(markup2);
				} else if ((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("ordered"))) {
					NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					Markup markup1 = new Markup();
					markup1.setContent("\\begin{enumerate}");
					section.getSecContent().add(markup1);
					for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
						Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
						ItemList itemList = new ItemList();
						itemList.setListContent(nodeSubSecListItem.getTextContent());
						section.getSecContent().add(itemList);
						writer.write(itemList.getListContent());
						writer.newLine();
					}
					Markup markup2 = new Markup();
					markup2.setContent("\\end{enumerate}");
					section.getSecContent().add(markup2);
					/* Second level - subsections*/
				} else if ((nodeSecElement.getNodeName() == "sec") && (nodeSecElement.getTextContent() != null)) {
	
					SubSec subSec = new SubSec();
					Node nodeSubSecTitle = (Node) xPath.compile("title").evaluate(nodeSecElement, XPathConstants.NODE);
					subSec.setTitle(nodeSubSecTitle.getTextContent());
					section.getSecContent().add(subSec);
					
					writer.write(subSec.getTitle());
					writer.newLine();
					
					NodeList nodeSubSecElements = (NodeList) xPath.compile("p|fig|table-wrap|list").evaluate(nodeSecElement, XPathConstants.NODESET);
					for (int i11 = 0; i11 < nodeSubSecElements.getLength(); i11++) {
						Node nodeSubSecElement = nodeSubSecElements.item(i11);
						if ((nodeSubSecElements.item(i11).getNodeName() == "p") && (nodeSubSecElements.item(i11)) != null) {
							ParContent parContent = new ParContent();
							parContent.setTitle("");
							subSec.getSecContent().add(parContent);
							writer.write(parContent.getTitle());
							//par.setParContent(nodeSecElement.getTextContent());
							NodeList insidePars = nodeSubSecElement.getChildNodes();
							for (int j = 0; j < insidePars.getLength(); j++) {
								Node insidePar = insidePars.item(j);
								
								if (insidePar.getNodeValue() != null) {
									Par par = new Par();
									par.setContent(insidePar.getNodeValue());
									parContent.getParContentList().add(par);
									writer.write(par.getContent());
									writer.newLine();
								} else if (insidePar.getNodeName() == "italic") {
									Italic italic = new Italic();
									italic.setContent(insidePar.getTextContent());
									//String italic = insidePar.getTextContent();
									//par.setParContent("\\textit{" + italic + "}");
									parContent.getParContentList().add(italic);
									//writer.write(par.getParContent());
								} else if (insidePar.getNodeName() == "xref") {
									if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("bibr")) {
										  Xref xref = new Xref();
										  xref.setBibContent(insidePar.getTextContent().trim());
										  
										  parContent.getParContentList().add(xref);
										  writer.write(xref.getBibContent());
							             
									  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("fig")) {
										  Xref xref = new Xref();
										  String xrefFig = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
										  String figDescription = insidePar.getTextContent().trim();
										  xref.setFigDescription(figDescription);
										  xref.setFigContent(xrefFig);
										  parContent.getParContentList().add(xref);
										  writer.write(xref.getFigContent());
									  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("table")) {
										  Xref xref = new Xref();
										  String tableDescription = insidePar.getTextContent();
										  String xrefTable = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
										  xref.setTableDescription(tableDescription);
										  xref.setTableContent(xrefTable);
										  parContent.getParContentList().add(xref);
										  writer.write(xref.getTableContent());
									  }
								}
							}
							writer.newLine();
						} else if ((nodeSubSecElement.getNodeName() == "list") && (nodeSubSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
							NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
							Markup markup1 = new Markup();
							markup1.setContent("\\begin{itemize}");
							subSec.getSecContent().add(markup1);
							for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
								Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
								ItemList subList = new ItemList();
								subList.setListContent(nodeSubSecListItem.getTextContent());
								subSec.getSecContent().add(subList);
								writer.write(subList.getListContent());
								writer.newLine();
							}
							Markup markup2 = new Markup();
							markup2.setContent("\\end{itemize}");
							subSec.getSecContent().add(markup2);
						} else if ((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("ordered"))) {
							NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
							Markup markup1 = new Markup();
							markup1.setContent("\\begin{enumerate}");
							subSec.getSecContent().add(markup1);
							for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
								Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
								ItemList itemList = new ItemList();
								itemList.setListContent(nodeSubSecListItem.getTextContent());
								subSec.getSecContent().add(itemList);
								writer.write(itemList.getListContent());
								writer.newLine();
							}
							Markup markup2 = new Markup();
							markup2.setContent("\\end{enumerate}");
							subSec.getSecContent().add(markup2);
						}
					}  
				}
			}
		} // end of list sections
		
		/* parsing references from xml */
		
		List<Bibitem> bibitems = new ArrayList<Bibitem>();
		NodeList bibXmls = (NodeList) xPath.compile("/article/back/ref-list/ref").evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i<bibXmls.getLength(); i++) {
			Bibitem bibitem = new Bibitem();
			Node bibXml = bibXmls.item(i);
			bibitem.setId(bibXml.getAttributes().getNamedItem("id").getTextContent());
			bibitems.add(bibitem);
			writer.write("\\bibitem{" + bibitem.getId() + "}");
			writer.newLine();
			Node ref = (Node) xPath.compile("element-citation").evaluate(bibXml, XPathConstants.NODE);
			
			if (ref.getAttributes().getNamedItem("publication-type") != null && ref.getAttributes().getNamedItem("publication-type").getTextContent().contains("journal")) {
				BibitemJournal bibJournal = new BibitemJournal();
				NodeList surname = (NodeList) xPath.compile("person-group/name/surname").evaluate(ref, XPathConstants.NODESET);
				if (surname != null) {
					for (int i1 = 0; i1 < surname.getLength(); i1++) {
						//System.out.println(surname.item(i1).getTextContent());
						BibName bibName = new BibName(); 
						bibName.setSurname(surname.item(i1).getTextContent());
						bibJournal.getName().add(bibName);
						writer.write(surname.item(i1).getTextContent() + " ");
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
				
				bibitem.getBibitem().add(bibJournal);
				
			}
			
			writer.newLine();	
		}
		writer.close();
		
		/* Getting elements from object tree 
		 * Object tree Part
		 * 
		 * */
		wrobj.write("\\documentclass[twocolumn]{article}");
		wrobj.newLine();
		wrobj.write("\\usepackage[left=48pt,right=46pt]{geometry}");
		wrobj.newLine();
		wrobj.write("\\date{}");
		wrobj.newLine();
		wrobj.write("\\usepackage{authblk}");
		wrobj.newLine();
		wrobj.write("\\usepackage{indentfirst}");
		wrobj.newLine();
		wrobj.write("\\usepackage{threeparttable}");
		wrobj.newLine();
		wrobj.write("\\usepackage{tablefootnote}");
		wrobj.newLine();
		wrobj.write("\\usepackage{graphicx}");
		wrobj.newLine();
		wrobj.write("\\usepackage{multirow}");
		wrobj.newLine();
		wrobj.write("\\usepackage{wrapfig}");
		wrobj.newLine();
		wrobj.write("\\usepackage{array}");
		wrobj.newLine();
		wrobj.write("\\usepackage{booktabs,siunitx}");
		wrobj.newLine();
		wrobj.write("\\usepackage{mathtext}");
		wrobj.newLine();
		wrobj.write("\\usepackage[T2A]{fontenc}");
		wrobj.newLine();
		wrobj.write("\\usepackage[utf8]{inputenc}");
		wrobj.newLine();
		wrobj.write("\\usepackage[ukrainian]{babel}");
		wrobj.newLine();
		wrobj.write("\\usepackage{lineno,hyperref}");
		wrobj.newLine();
		wrobj.write("\\modulolinenumbers[5]");
		wrobj.newLine();
		wrobj.write("\\usepackage{fancyhdr}");
		wrobj.newLine();
		wrobj.write("\\pagestyle{fancy}");
		wrobj.newLine();
		wrobj.write("\\fancyhead{}");
		wrobj.newLine();
		wrobj.write("\\fancyfoot[C]{\\thepage}");
		wrobj.newLine();
		wrobj.write("\\fancyhead[C]{Психосоматична медицина та загальна практика $\\bullet$ Листопад 2016 $\\bullet$ Т. 1, № 1 $\\bullet$ e010113}");
		wrobj.newLine();
		wrobj.write("\\fancyfoot[RO,LE]{}");
		wrobj.newLine();
		wrobj.write("\\tolerance=10000");
		wrobj.newLine();
		wrobj.write("\\begin{document}");
		wrobj.newLine();
		
		for (int j = 0; j < listSections.size(); j++) {
			Section section = listSections.get(j);
			wrobj.write(section.getTitle());
			wrobj.newLine();
			List<SecContent> secContents = section.getSecContent();
			for (SecContent secContent : secContents) {
				//System.out.println(secContent.getClass().getName());
				/*
				if (secContent.getTitle() != null) {
					System.out.println(secContent.getTitle());
					wrobj.write(secContent.getTitle());
				}
				*/
				if (secContent.getClass().getName() == "org.emed.classes.ParContent") {
					ParContent parContent = (ParContent) secContent;
					List<ParContent> parContents = parContent.getParContentList();
					wrobj.write("\\par ");
					for (ParContent parContentPar : parContents) {
						
						if (parContentPar.getClass().getName() == "org.emed.classes.Par") {
							Par par = (Par) parContentPar;
							wrobj.write(par.getContent().trim());
						} else if (parContentPar.getClass().getName() == "org.emed.classes.Italic") {
							Italic italic = (Italic) parContentPar;
							wrobj.write("\\textit{" + italic.getContent() + "}");
						} else if (parContentPar.getClass().getName() == "org.emed.classes.Xref") {
							Xref xref = (Xref) parContentPar;
							
							if (xref.getBibContent() != null) {
							    wrobj.write(xref.getBibContent());
							} else if (xref.getFigContent() != null) {   
								wrobj.write(xref.getFigDescription() + " " + xref.getFigContent());
							} else if (xref.getTableContent() != null) {
								wrobj.write(xref.getTableDescription() + " " + xref.getTableContent());
							}
							
							
						} 
							
					} // end of Paragraph content
					
				wrobj.newLine();
					//System.out.println(par.getParContent());
				} else if (secContent.getClass().getName() == "org.emed.classes.ItemList") {
					ItemList subList = (ItemList) secContent;
					wrobj.write(subList.getListContent());
					wrobj.newLine();
				} else if (secContent.getClass().getName() == "org.emed.classes.Markup") {
					Markup markup = (Markup) secContent;
					wrobj.write(markup.getContent());
					wrobj.newLine();
				}
				
				if (secContent.getClass().getName() == "org.emed.classes.SubSec") {
					SubSec subSec = (SubSec) secContent;
					wrobj.write(subSec.getTitle());
					wrobj.newLine();
					List<SecContent> subSecContents = subSec.getSecContent();
					for (SecContent subSecContent : subSecContents) {
						//System.out.println(subSecContent.getClass().getName());	
						if (subSecContent.getClass().getName() == "org.emed.classes.ParContent") {
							ParContent subParContent = (ParContent) subSecContent;
							wrobj.write("\\par ");
							List<ParContent> subPars = subParContent.getParContentList();
							for (ParContent subPar : subPars) {
								if (subPar.getClass().getName() == "org.emed.classes.Par") {
									wrobj.write(subPar.getContent());
								} else if (subPar.getClass().getName() == "org.emed.classes.Italic") {
									Italic italic = (Italic) subPar;
									wrobj.write("\\textit{" + italic.getContent() + "}");
								} else if (subPar.getClass().getName() == "org.emed.classes.Xref") {
									Xref xref = (Xref) subPar;
									if (xref.getBibContent() != null) {
										wrobj.write(xref.getBibContent());
									} else if (xref.getFigContent() != null) {
										wrobj.write(xref.getFigDescription() + " " + xref.getFigContent());
									} else if (xref.getTableContent() != null) {
										wrobj.write(xref.getTableDescription() + " " + xref.getTableContent());
									}
								}
							} // end of Paragraph content
						wrobj.newLine();
						} else if (subSecContent.getClass().getName() == "org.emed.classes.ItemList"){
							ItemList subList = (ItemList) subSecContent;
							wrobj.write(subList.getListContent());
							wrobj.newLine();
						} else if (subSecContent.getClass().getName() == "org.emed.classes.Markup") {
							Markup markup = (Markup) subSecContent;
							wrobj.write(markup.getContent());
							wrobj.newLine();
						}
					}
				}
			}
		} //end of Sections
		
		/* Get bibliography */
		wrobj.newLine();
		wrobj.write("\\begin{thebibliography}" + "{" + bibitems.size() + "}");
		wrobj.newLine();
		for (Bibitem bibitem : bibitems) {
			wrobj.write("\\bibitem{" + bibitem.getId() + "}");
			List<Bibitem> bibcontents = bibitem.getBibitem();
			for (Bibitem bibcontent : bibcontents) {
				if (bibcontent.getClass().getName() == "org.emed.classes.BibitemJournal") {
					BibitemJournal bibJournal = (BibitemJournal) bibcontent;
					
					if (bibJournal.getName() != null) {
						List<BibName> bibNames = bibJournal.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							
							 if (bibNames.size() < 4) {
								wrobj.write(bibNames.get(0).getSurname() + " ");
								char[] initials = bibNames.get(0).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
								}
								break;
							}
						}
					} 
					//set article title
					if (bibJournal.getTitle() != null) {	
						wrobj.write(bibJournal.getTitle().trim());
					}
					//set article collab
					if (bibJournal.getCollab() != null) {
						wrobj.write(" / " + bibJournal.getCollab());
					}
					//set authors second time
					if (bibJournal.getName() != null) {
						wrobj.write(" / ");
						List<BibName> bibNames = bibJournal.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							if (i == 0 && bibNames.size() > 1) {
								char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");	
						    } else if (i == 0 && bibNames.size() == 1) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");	
						    } else if (i == 1 && bibNames.size() > 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");
						    } else if (i == 1 && bibNames.size() == 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");
						    } else if (i == 2 && bibNames.size() > 3) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");
						    } else if (i > 2) {
						    	wrobj.write("[et al.]");
						    	break;
						    }
						}
					}
				}
			} //end of bibitem
			
			wrobj.newLine();
		}
		
		  wrobj.write("\\end{thebibliography}");
	      wrobj.newLine();
	      wrobj.write("\\end{document}");
	      wrobj.close();
		
	}

}