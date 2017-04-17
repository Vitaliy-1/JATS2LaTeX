package org.emed.main;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.emed.classes.Bold;
import org.emed.classes.Figure;
import org.emed.classes.Italic;
import org.emed.classes.Markup;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.Section;
import org.emed.classes.SubSec;
import org.emed.classes.Table;
import org.emed.classes.Xref;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Body {
	
	protected static ArrayList<Section> body(Document document, XPath xPath) throws XPathExpressionException, DOMException {
		NodeList nodeSections = (NodeList) xPath.compile("/article/body/sec").evaluate(document, XPathConstants.NODESET);
		ArrayList<Section> listSections = new ArrayList<Section>();
		
		for (int i = 0; i < nodeSections.getLength(); i++) {
			Node nodeSection = nodeSections.item(i);
			Node nodeSectionTitle = (Node) xPath.compile("title").evaluate(nodeSection,  XPathConstants.NODE);
			Section section = new Section();
			listSections.add(section);
			listSections.get(i).setTitle(nodeSectionTitle.getTextContent());
			NodeList nodeSecElements = (NodeList) xPath.compile("p|fig|sec|table-wrap|list").evaluate(nodeSection, XPathConstants.NODESET);
			for (int i1 = 0; i1 < nodeSecElements.getLength(); i1++) {
				
				/* First Level of section nodes*/
				Node nodeSecElement = nodeSecElements.item(i1);
				if ((nodeSecElement.getNodeName() == "p") && (nodeSecElement.getTextContent() != null)) {
					ParContent parContent = new ParContent();
					parContent.setTitle("");
					parContent.setType("par");
					section.getSecContent().add(parContent);
					parsingParContent(nodeSecElement, parContent);
					
					
				} else if((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
					NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					Markup markup1 = new Markup();
					markup1.setContent("\\begin{itemize}");
					section.getSecContent().add(markup1);
					for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
						Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
						ParContent itemList = new ParContent();
						itemList.setType("unordered");
						section.getSecContent().add(itemList);
						parsingParContent(nodeSubSecListItem, itemList);
						
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
						ParContent itemList = new ParContent();
						itemList.setType("ordered");
						section.getSecContent().add(itemList);
						parsingParContent(nodeSubSecListItem, itemList);
					}
					Markup markup2 = new Markup();
					markup2.setContent("\\end{enumerate}");
					section.getSecContent().add(markup2);
					/* Second level - subsections*/
				} else if (nodeSecElement.getNodeName() == "fig") {
					Figure figure = new Figure();
					if (nodeSecElement.getAttributes() != null && nodeSecElement.getAttributes().getNamedItem("id") != null) {
						figure.setId(nodeSecElement.getAttributes().getNamedItem("id").getNodeValue());
					}
					
					Node label = (Node) xPath.compile("label").evaluate(nodeSecElement, XPathConstants.NODE);
					if (label != null) {
						figure.setLabel(label.getTextContent());
					}
					NodeList titles = (NodeList) xPath.compile("caption/title").evaluate(nodeSecElement, XPathConstants.NODESET);
					if (titles != null) {
						for (int y = 0; y < titles.getLength(); y++) {
							Node title = titles.item(y);
							ParContent parContent = new ParContent();
							parContent.setType("figureTitle");
							figure.getParContent().add(parContent);
							parsingParContent(title, parContent);
						}
					}
					NodeList captions = (NodeList) xPath.compile("caption/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					if (captions != null) {
						for (int y = 0; y < captions.getLength(); y++) {
							Node caption = captions.item(y);
							ParContent parContent = new ParContent();
							parContent.setType("figureCaption");
							figure.getParContent().add(parContent);
							parsingParContent(caption, parContent);
						}
					}
					Node link = (Node) xPath.compile("graphic").evaluate(nodeSecElement, XPathConstants.NODE);
					if (link != null && link.getAttributes() != null && link.getAttributes().getNamedItem("xlink:href") != null) {
						figure.setLink(link.getAttributes().getNamedItem("xlink:href").getNodeValue());
					}
					section.getSecContent().add(figure);
					
				
			    } else if (nodeSecElement.getNodeName() == "table-wrap") {
			    	Table table = new Table();
			    	if (nodeSecElement.getAttributes() != null && nodeSecElement.getAttributes().getNamedItem("id") != null) {
						table.setId(nodeSecElement.getAttributes().getNamedItem("id").getNodeValue());
					}
			    	Node label = (Node) xPath.compile("label").evaluate(nodeSecElement, XPathConstants.NODE);
					if (label != null) {
						table.setLabel(label.getTextContent());
					}
					NodeList titles = (NodeList) xPath.compile("caption/title").evaluate(nodeSecElement, XPathConstants.NODESET);
					if (titles != null) {
						for (int y = 0; y < titles.getLength(); y++) {
							Node title = titles.item(y);
							ParContent parContent = new ParContent();
							parContent.setType("tableTitle");
							table.getParContent().add(parContent);
							parsingParContent(title, parContent);
						}
					}
					NodeList captions = (NodeList) xPath.compile("caption/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					if (captions != null) {
						for (int y = 0; y < captions.getLength(); y++) {
							Node caption = captions.item(y);
							ParContent parContent = new ParContent();
							parContent.setType("tableCaption");
							table.getParContent().add(parContent);
							parsingParContent(caption, parContent);
						}
					}
					
					// getting number of columns for latex; for tables with head and body
					Node tableHead = (Node) xPath.compile("table/thead").evaluate(nodeSecElement, XPathConstants.NODE);
					if (tableHead != null) {
						TableFunctions.countColumns(xPath, table, tableHead);
					} else {
						Node tableWithoutHead = (Node) xPath.compile("table").evaluate(nodeSecElement, XPathConstants.NODE);
						TableFunctions.countColumns(xPath, table, tableWithoutHead);
					}
					// getting rows from table head
					String rowTypeHead = "head";
					TableFunctions.cellParsing(xPath, table, tableHead, rowTypeHead);
					
					// getting rows from table body
					Node tableBody = (Node) xPath.compile("table/tbody").evaluate(nodeSecElement, XPathConstants.NODE);
					String rowTypeBody = "body";
					TableFunctions.cellParsing(xPath, table, tableBody, rowTypeBody);
					
					section.getSecContent().add(table);
					
			    } else if ((nodeSecElement.getNodeName() == "sec") && (nodeSecElement.getTextContent() != null)) {
	
					subSection(xPath, section, nodeSecElement);  
				}
			}
		} // end of list sections
		return listSections;
	}


	protected static void parsingParContent(Node nodeSecElement, ParContent parContent) throws DOMException {
		NodeList insidePars = nodeSecElement.getChildNodes();
		for (int j = 0; j < insidePars.getLength(); j++) {
			Node insidePar = insidePars.item(j);
			
			if (insidePar.getNodeValue() != null) {
				Par par = new Par();
				par.setContent(insidePar.getNodeValue());
				parContent.getParContentList().add(par);
			} else if (insidePar.getNodeName() == "italic") {
				Italic italic = new Italic();
				italic.setContent(insidePar.getTextContent());					
				parContent.getParContentList().add(italic);
				
			} else if (insidePar.getNodeName() == "bold") {
				Bold bold = new Bold();
				bold.setContent(insidePar.getTextContent());
				parContent.getParContentList().add(bold);
			
		    } else if (insidePar.getNodeName() == "xref") {
				  if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("bibr")) {
					  Xref xref = new Xref();
					  xref.setBibContent(insidePar.getTextContent().trim());
					  parContent.getParContentList().add(xref);
		             
				  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("fig")) {
					  Xref xref = new Xref();
					  String xrefFig = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
					  String figDescription = insidePar.getTextContent().trim();
					  xref.setFigDescription(figDescription);
					  xref.setFigContent(xrefFig);
					  parContent.getParContentList().add(xref);
					
				  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("table")) {
					  Xref xref = new Xref();
					  String TableDescription = insidePar.getTextContent();
					  String xrefTable = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
					  xref.setTableDescription(TableDescription);
					  xref.setTableContent(xrefTable);
					  parContent.getParContentList().add(xref);
				  }
			}
		}
	}

	private static void subSection(XPath xPath, Section section, Node nodeSecElement)
			throws XPathExpressionException, DOMException {
		SubSec subSec = new SubSec();
		Node nodeSubSecTitle = (Node) xPath.compile("title").evaluate(nodeSecElement, XPathConstants.NODE);
		subSec.setTitle(nodeSubSecTitle.getTextContent());
		section.getSecContent().add(subSec);
		
		
		NodeList nodeSubSecElements = (NodeList) xPath.compile("p|fig|table-wrap|list").evaluate(nodeSecElement, XPathConstants.NODESET);
		for (int i11 = 0; i11 < nodeSubSecElements.getLength(); i11++) {
			Node nodeSubSecElement = nodeSubSecElements.item(i11);
			if ((nodeSubSecElements.item(i11).getNodeName() == "p") && (nodeSubSecElements.item(i11)) != null) {
				ParContent parContent = new ParContent();
				parContent.setTitle("");
				parContent.setType("par");
				subSec.getSecContent().add(parContent);
				
				parsingParContent(nodeSubSecElement, parContent);
				
			} else if ((nodeSubSecElement.getNodeName() == "list") && (nodeSubSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
				NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				Markup markup1 = new Markup();
				markup1.setContent("\\begin{itemize}");
				subSec.getSecContent().add(markup1);
				for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
					Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
					ParContent itemList = new ParContent();
					itemList.setType("unordered");
					section.getSecContent().add(itemList);
					parsingParContent(nodeSubSecListItem, itemList);
					
				}
				Markup markup2 = new Markup();
				markup2.setContent("\\end{itemize}");
				subSec.getSecContent().add(markup2);
			} else if ((nodeSubSecElement.getNodeName() == "list") && (nodeSubSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("ordered"))) {
				NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				Markup markup1 = new Markup();
				markup1.setContent("\\begin{enumerate}");
				subSec.getSecContent().add(markup1);
				for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
					Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
					ParContent itemList = new ParContent();
					itemList.setType("unordered");
					section.getSecContent().add(itemList);
					parsingParContent(nodeSubSecListItem, itemList);	
				}
				Markup markup2 = new Markup();
				markup2.setContent("\\end{enumerate}");
				subSec.getSecContent().add(markup2);
			} else if (nodeSubSecElement.getNodeName() == "fig") {
				Figure figure = new Figure();
				if (nodeSubSecElement.getAttributes() != null && nodeSubSecElement.getAttributes().getNamedItem("id") != null) {
					figure.setId(nodeSubSecElement.getAttributes().getNamedItem("id").getNodeValue());
				}
				
				Node label = (Node) xPath.compile("label").evaluate(nodeSubSecElement, XPathConstants.NODE);
				if (label != null) {
					figure.setLabel(label.getTextContent());
				}
				NodeList titles = (NodeList) xPath.compile("caption/title").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				if (titles != null) {
					for (int y = 0; y < titles.getLength(); y++) {
						Node title = titles.item(y);
						ParContent parContent = new ParContent();
						parContent.setType("figureTitle");
						figure.getParContent().add(parContent);
						parsingParContent(title, parContent);
					}
				}
				NodeList captions = (NodeList) xPath.compile("caption/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				if (captions != null) {
					for (int y = 0; y < captions.getLength(); y++) {
						Node caption = captions.item(y);
						ParContent parContent = new ParContent();
						parContent.setType("figureCaption");
						figure.getParContent().add(parContent);
						parsingParContent(caption, parContent);
					}
				}
				Node link = (Node) xPath.compile("graphic").evaluate(nodeSubSecElement, XPathConstants.NODE);
				if (link != null && link.getAttributes() != null && link.getAttributes().getNamedItem("xlink:href") != null) {
					figure.setLink(link.getAttributes().getNamedItem("xlink:href").getNodeValue());
				}
				subSec.getSecContent().add(figure);
			
		    } else if (nodeSubSecElement.getNodeName() == "table-wrap") {
		    	Table table = new Table();
		    	if (nodeSubSecElement.getAttributes() != null && nodeSubSecElement.getAttributes().getNamedItem("id") != null) {
					table.setId(nodeSubSecElement.getAttributes().getNamedItem("id").getNodeValue());
				}
		    	Node label = (Node) xPath.compile("label").evaluate(nodeSubSecElement, XPathConstants.NODE);
				if (label != null) {
					table.setLabel(label.getTextContent());
				}
				NodeList titles = (NodeList) xPath.compile("caption/title").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				if (titles != null) {
					for (int y = 0; y < titles.getLength(); y++) {
						Node title = titles.item(y);
						ParContent parContent = new ParContent();
						parContent.setType("tableTitle");
						table.getParContent().add(parContent);
						parsingParContent(title, parContent);
					}
				}
				NodeList captions = (NodeList) xPath.compile("caption/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				if (captions != null) {
					for (int y = 0; y < captions.getLength(); y++) {
						Node caption = captions.item(y);
						ParContent parContent = new ParContent();
						parContent.setType("tableCaption");
						table.getParContent().add(parContent);
						parsingParContent(caption, parContent);
					}
				}
				subSec.getSecContent().add(table);
			
		    } else if ((nodeSubSecElement.getNodeName() == "sec") && (nodeSubSecElement.getTextContent() != null)) {
				subSection(xPath, section, nodeSubSecElement);  
			}
		}
	}


}
